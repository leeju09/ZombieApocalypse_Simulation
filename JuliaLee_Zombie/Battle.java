/** Requirement:
 * Battle resolves combat between a Person and a Zombie.
 * SRP: Applies combat rules. Modifies Person and Zombie state, but does NOT store state itself.
 * 
 * Gun rules:
 *  - 1 in 5 chance to hit zombie.
 *  - If hit: 1 in 3 chance instant kill, else 2 damage.
 *  - 2 normal hits or 1 critical = zombie dead
 *  - If miss: 1 in 2 chance zombie instant-kills person, else 1 damage
 *  - Each shot uses 1 ammo
 * 
 * Melee rules:
 *  - 1 in 3 chance to hit zombie
 *  - If hit: 1 in 10 chance instant kill, else 1 damage
 *  - If miss: person dies
 * 
 * Added
 *  - Knife: 1-in-2 hit, instant kill on hit.
 *  - Shotgun: 1-in-3 hit, instant kill on hit, damage on miss.
 *  - Dodge: May cancel any non-critical hit before it lands.
 *  - Armor: reduces non-critical damage (floor 0).
 * 
 * public entry point:
 *  -   fight(Person, Zombie) : boolean - dispatches to the best available weapon
 *      (shotgun -> pistol -> knife -> fists) and returns true if the person wins.
 */


public class Battle {

    /**
     * Main combat dispatcher. Picks the best available weapon and runs the first fight loop until the zombie or the person dies.
     * 
     * Priority:
     * 1. Shotgun (if shells > 0)
     * 2. Pistol  (if ammo > 0)
     * 3. Knife   ( if hasKnife)
     * 4. Fists   (fallback)
     * 
     * @param p the person fighting
     * @param z the zombie being founght
     * @return true if the person kills the zombie (and survives), false if the person dies
     */
    public static boolean fight(Person p, Zombie z) {
        // Priority: 1st: Shotgun 2nd: Pistol 3rd: Knife 4th: Fist
        while (p.isLiving() && z.isLiving()) {
            if (p.getShells() > 0) {
                shotgunBattle(p, z);
            } else if (p.getAmmo() > 0) {
                gunBattle(p, z);
            } else if (p.hasKnife()) {
                knifeBattle(p, z);
            } else {
                fistBattle(p, z);
            }
        }
        return p.isLiving();
    }

    /**
     * Resolves a shotgun battle. Instantly kills the zombie on hit.
     * On miss: dodge check first, then SHOTGUN_MISS_DAMAGE (reduced by armor).
     * Each shot consumes one shell.
     * 
     * @param p the person firing
     * @param z the zombie being fought
     */
    // Shotgun - instant kill on hit
    private static void shotgunBattle(Person p, Zombie z) {
        while (p.isLiving() && z.isLiving() && p.getShells() > 0) {
            p.setShells(p.getShells() - 1);

            boolean hit = RandomUtil.chance(GameConfig.SHOTGUN_HIT_CHANCE);
            p.recordShells(hit);

            if (hit) {
                // Shotgun instantly kills the zombie
                z.setHealth(0);
                z.setLiving(false);
            } else {
                // miss - dodge first
                if (RandomUtil.chance(p.getDodge())) {
                    p.recordDodge();
                    continue; // dodge - no retaliation
                } 
                int dealt = applyDamageToPerson(p, GameConfig.SHOTGUN_MISS_DAMAGE, "gun");
                if (!p.isLiving()) {
                        p.die("killed by zombie (shotgun miss)", dealt);
                }  
            }
        }
    }

    /**
     * Resolves a pistol battle using rules
     * Loops until one side dies or the person runs out of ammo.
     * Each shot consumes one round(ammo).
     * 
     * @param p the person firing
     * @param z the zombie being fought
     */
    // Pistol
    private static void gunBattle(Person p, Zombie z) {
        while (p.isLiving() && z.isLiving() && p.getAmmo() > 0) {
            p.setAmmo(p.getAmmo() - 1);

            boolean hit = RandomUtil.chance(GameConfig.GUN_HIT_CHANCE);
            p.recordShot(hit);

            if (hit) {
                if (RandomUtil.chance(GameConfig.GUN_CRITICAL_CHANCE)) {
                    z.setHealth(0);
                    z.setLiving(false);
                } else {
                    z.setHealth(z.getHealth() - GameConfig.GUN_DAMAGE);
                    if (z.getHealth() <= 0) {
                        z.setHealth(0);
                        z.setLiving(false);
                    }
                }
            } else {
                // dodge
                if (RandomUtil.chance(p.getDodge())) {
                    p.recordDodge();
                    continue;
                }
                if (RandomUtil.chance(GameConfig.GUN_ZOMBIE_CRIT_CHANCE)) {
                    int dmg = p.getHealth();
                    p.recordDamageTaken(dmg, "gun");
                    p.die("killed by zombie (gun-miss critical)", dmg);
                } else {
                    int dealt = applyDamageToPerson(p, GameConfig.GUN_ZOMBIE_DAMAGE, "gun");
                    if (!p.isLiving()) {
                        p.die("killed by zombie (gun miss)", dealt);
                    }
                }
            }
        }
    }

    /**
     * Resolves a knife battle. Instantly kills the zombie on hit.
     * On miss: dodge first, then MELEE_MISS_DEATH_CHANCE (1-in-2 instant death).
     * 
     * @param p the person swinging
     * @param z the zombie being fought
     */
    // knife melee
    private static void knifeBattle(Person p, Zombie z) {
        while (p.isLiving() && z.isLiving()) {
            boolean hit = RandomUtil.chance(GameConfig.KNIFE_HIT_CHANCE);
            p.recordKnifeSwing(hit);
        

            if (hit) {
                // knife instantly kills the zombie
                z.setHealth(0);
                z.setLiving(false);
                p.recordKnifeKill();
                
            } else {
                // Dodge first
                if (RandomUtil.chance(p.getDodge())) {
                    p.recordDodge();
                    continue;
                }
                if (RandomUtil.chance(GameConfig.MELEE_MISS_DEATH_CHANCE)) {
                    int dmg = p.getHealth();
                    p.recordDamageTaken(dmg, "melee");
                    p.die("killed by zombie (knife miss)", dmg);
                }
            }
        }

    }

    /**
     * Resolves a fist battle using the melee rules
     * On hit: 1-in-10 chance instant kill, otherwise MELEE_DAMAGE.
     * On miss: dodge first, then MELEE_MISS_DEATH_CHANCE(1-in-2 instant death).
     * 
     * @param p the person swinging
     * @param z the zombie being fought
     */
    // Fist Melee
    private static void fistBattle(Person p, Zombie z) {
        while (p.isLiving() && z.isLiving()) {
            if (RandomUtil.chance(GameConfig.MELEE_HIT_CHANCE)) {
                if (RandomUtil.chance(GameConfig.MELEE_CRITICAL_CHANCE)) {
                    z.setHealth(0);
                    z.setLiving(false);
                } else {
                    z.setHealth(z.getHealth() - GameConfig.MELEE_DAMAGE);
                    if (z.getHealth() <= 0) {
                        z.setHealth(0);
                        z.setLiving(false);
                    }
                }
            } else {
                // Dodge first
                if (RandomUtil.chance(p.getDodge())) {
                    p.recordDodge();
                    continue;
                }
                if (RandomUtil.chance(GameConfig.MELEE_MISS_DEATH_CHANCE)) {
                    int dmg = p.getHealth();
                    p.recordDamageTaken(dmg, "melee");
                    p.die("killed by zombie (melee miss)", dmg);
                }
            }
        }
        
    }

    /**
     * Applies damage to a person, after armor.
     * Dodge must be handled by the caller BEFORE calling this method.
     * 
     * @param p the person taking damage
     * @param rawDamage raw damage before armor
     * @param source "gun" or "melee" - used for stat tracking
     * @return actual damage dealt after dodge and armor (0 if dodged or absorbed)
     */


    private static int applyDamageToPerson(Person p, int rawDamage, String source) {

        // Dodge is handled by the caller BEFORE calling this method.
        
        int actual = Math.max(0, rawDamage - p.getArmor());
        p.recordDamageTaken(actual, source);
        p.setHealth(p.getHealth() - actual);
        if (p.getHealth() <= 0) {
            p.setHealth(0);
            p.setLiving(false);
        }
        return actual;

    }
}
