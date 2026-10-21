/**
 * Person stores and manages the state of a human survivor.
 * SRP: Person only handles state. It does NOT decide combat rules or modify zombies.
 * 
 * Instance vs. local variables:
 *  - In the constructor, 'name' is a LOCAL variable (only exists in the constructor).
 *  - 'this.name' is the INSTANCE variable (belongs to the object for its lifetime).
 * The constructor copies the local value into the instance variable.
 */

public class Person {

    // Instance variables - all private
    private String name;
    private int food;
    private int ammo;
    private int shells;
    private int health;
    private boolean isLiving;
    private int medkits; 
    private int armor; 
    private int dodge; 
    private boolean hasKnife; 

    // Stats tracking
    private int shotsFired;
    private int shotsHit;
    private int shotsMissed;
    private int shellsFired;
    private int shellsMissed;
    private int shellsHit;
    private int knifeHits;
    private int knifeMisses;
    private int knifeKills;
    private int damageTakenTotal;
    private int damageTakenByGun;
    private int damageTakenByMelee;
    private int hitsAsorbByArmor;
    private int dodgeHits;
    private int foodEatenForConsumption;
    private int foodEatenForHealing;
    private int starvationDamageTaken;
    private String deathCause;
    private int damageAtDeath;

    /**
     * Constructs a Person with the given name and all starting stats from GameConfig.
     * 
     * @param name the survivor's name
     */

    public Person(String name) {
        this.name = name;
        this.food = GameConfig.PERSON_STARTING_FOOD;
        this.ammo = GameConfig.PERSON_STARTING_AMMO;
        this.shells = GameConfig.PERSON_STARTING_SHELLS;
        this.health = GameConfig.PERSON_STARTING_HEALTH;
        this.isLiving = true;
        this.medkits = GameConfig.PERSON_STARTING_MEDKITS;
        this.armor = GameConfig.PERSON_STARTING_ARMOR;
        this.dodge = GameConfig.PERSON_STARTING_DODGE;
        this.hasKnife = true;
        this.deathCause = null;
    }

    // Getters
    public String getName()     { return name; }
    public int getFood()        { return food; }
    public int getAmmo()        { return ammo; }
    public int getShells()      { return shells; }
    public int getHealth()      { return health; }
    public boolean isLiving()   { return isLiving; }
    public int getMedkits()     { return medkits; }
    public int getArmor()       { return armor; }
    public int getDodge()       { return dodge; }
    public boolean hasKnife()   { return hasKnife; }

    // Getters (Stats)
    public int getShotsFired()              { return shotsFired; }
    public int getShotsHit()                { return shotsHit; }
    public int getShotsMissed()             { return shotsMissed; }
    public int getShellsFired()             { return shellsFired; }
    public int getShellsMissed()            { return shellsMissed; }
    public int getShellsHit()               { return shellsHit; }
    public int getKnifeHits()               { return knifeHits; }
    public int getKnifeMisses()             { return knifeMisses; }
    public int getKnifeKills()              { return knifeKills; }
    public int getDamageTakenTotal()        { return damageTakenTotal; }
    public int getDamageTakenByGun()        { return damageTakenByGun; }
    public int getDamageTakenByMelee()      { return damageTakenByMelee; }
    public int getHitsAbsorbByArmor()       { return hitsAsorbByArmor; }
    public int getDodgeHits()               { return dodgeHits; }
    public int getFoodEatenForConsumption() { return foodEatenForConsumption; }
    public int getFoodEatenForHealing()     { return foodEatenForHealing; }
    public int getStarvationDamageTaken()   { return starvationDamageTaken; }
    public String getDeathCause()           { return deathCause; }
    public int getDamageAtDeath()           { return damageAtDeath; }

    // Setters
    public void setName(String name)        { this.name = name; }
    public void setFood(int food)           { this.food = food; }
    public void setAmmo(int ammo)           { this.ammo = ammo; }
    public void setShells(int shells)       { this.shells = shells; }
    public void setHealth(int health)       { this.health = health; }
    public void setLiving(boolean living)   { this.isLiving = living; }
    public void setMedkits(int medkits)     { this.medkits = medkits; }
    public void setArmor(int armor)         { this.armor = armor; }
    public void setDodge(int dodge)         { this.dodge = dodge; }
    public void setHasKnife(boolean hasKnife) { this.hasKnife = hasKnife; }

    // Stat-recording helpers
    /**
     * Records a pistol shot and updates hit/miss counters.
     * 
     * @param hit true if the shot hit, false if it missed
     */

    public void recordShot(boolean hit) {
        shotsFired++;
        if (hit) shotsHit++;
        else     shotsMissed++;
    }

    /**
     * Records a shotgun shell fired and updates hit/miss counters.
     * 
     * @param hit true if the shell hit, false if it missed
     */
    public void recordShells(boolean hit) {
        shellsFired++;
        if (hit) shellsHit++; else shellsMissed++;
    }

    /**
     * Records a knife swing and updates hit/miss counters.
     * 
     * @param hit true if the swing hit, false if it missed
     */
    public void recordKnifeSwing(boolean hit) {
        if (hit) knifeHits++;
        else     knifeMisses++;
    }

    // Increments the knife-kill counter
    public void recordKnifeKill() {
        knifeKills++;
    }

    // Increments the dodge counter
    public void recordDodge() {
        dodgeHits++;
    }


    /**
     * Records damage taken. If amount is 0, it was fully absorbed by armor.
     * If source is "gun", adds to gun-phase damage; "melee" adds to melee-phase.
     * 
     * @param amount HP damage dealt (0 means fully absorbed)
     * @param source "gun" or "melee"
     */
    public void recordDamageTaken(int amount, String source) {
        if (amount == 0) {
            hitsAsorbByArmor++;
            return;
        }
        damageTakenTotal += amount;
        if ("gun".equals(source))           damageTakenByGun += amount;
        else if ("melee".equals(source))    damageTakenByMelee += amount;
    }

    // Increments the normal-consumption food counter
    public void recordFoodConsumption()     { foodEatenForConsumption++; }

    // Increments the healing food counter
    public void recordFoodHealed()          { foodEatenForHealing++; }

    /**
     * Records starvation damage taken.
     * 
     * @param amount HP lost to starvation
     */
    public void recordStarvationDamage(int amount) { starvationDamageTaken += amount; }

    /**
     * Kills the survivor and records the cause and damage at death.
     * 
     * @param cause description of what killed them
     * @param damage HP damage recorded at death
     */
    public void die(String cause, int damage) {
        this.health = 0;
        this.isLiving = false;
        this.deathCause = cause;
        this.damageAtDeath = damage;
    }

    // Food logic
    /**
     * Consumes 1 food for normal survival. If no food is available, deals STARVATION_DAMAGE and may kill the survivor.
     * 
     * 
     * @return true if food was eaten, false if the survivor starved this round
     */

    public boolean consumeFood() {
        if (!isLiving) return false;
        if (food > 0) {
            food--;
            recordFoodConsumption();
            return true;
        }
        health -= GameConfig.STARVATION_DAMAGE;
        recordStarvationDamage(GameConfig.STARVATION_DAMAGE);
        if (health <= 0) {
            health = 0;
            isLiving = false;
            deathCause = "starved to death";
            damageAtDeath = GameConfig.STARVATION_DAMAGE;
        }
        return false;
    }

    /**
     * Returns true if the survivor is alive but out of food.
     * 
     * @return true if starving
     */
    public boolean isStarving() {
        return isLiving && food <= 0;
    }

    /**
     * Consumes 1 food to heal FOOD_HEAL_AMOUNT HP.
     * Only works if alive, has food, and is injured.
     * 
     * @return true if a food-heal happened, false otherwise.
     */
    public boolean eatForHealing() {
        if (!isLiving) return false;
        if (food <= 0) return false;
        if (health >= GameConfig.PERSON_STARTING_HEALTH) return false;
        food--;
        health = Math.min(health + GameConfig.FOOD_HEAL_AMOUNT, GameConfig.PERSON_STARTING_HEALTH);
        recordFoodHealed();
        return true;
    }

    // Medkit logic

    /**
     * Uses 1 medkit to heal MEDKIT_HEAL_AMOUNT HP.
     * Only works if alive, has a medkit, and is injured.
     * 
     * @return true if a medkit was used, false otherwise
     */
    public boolean useMedkit() {
        if (medkits > 0 && health < GameConfig.PERSON_STARTING_HEALTH) {
            medkits--;
            health = Math.min(health + GameConfig.MEDKIT_HEAL_AMOUNT, GameConfig.PERSON_STARTING_HEALTH);
            return true;
        }
        return false;
    }


    /**
     * Returns a string representation of the Person's state
     * 
     * @return formatted string with name, health, ammo, food, etc.
     */
    @Override 
    public String toString() {
        return String.format("%s [Health: %d, Ammo: %d, Food: %d, Medkits: %d, Armor: %d, Knife: %s, %s]",
                name, health, ammo, food, medkits, armor, (hasKnife ? "YES" : "NO"), (isLiving ? "ALIVE" : "DEAD"));
    }
}