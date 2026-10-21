/** Unit 6: Real-World Modeling with Custom Class
 * 
 * Main runs the zombie apocalypse simulation.
 * SRP: Main only controls the flow of the program. It does not store state for individual characters or contain combat logic.
 * 
 * Flow per round (called "DAY"):
 *  1. Daily food phase (eat or starve)
 *  2. Medkit healing
 *  3. Combat: each living survivor fights one zombie via Battle.fight()
 *  4. Print status
 * Loop ends when either all people or all zombies are dead.
 */

public class Main {

    /**
     * Entry point. Sets up people and zombies, runs the battle loop, prints the final result and detailed per-person report.
     * 
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        
        System.out.println("=====================");
        System.out.println(" Zombie Apocalypse - Simulation");
        System.out.println("=====================\n");

        // 1. Array of names for the Person objects
        String[] names = {"Julia", "Diamond", "Amy", "Bron", "Chris", "Leo", "Kong", "Peter", "Chue", "Billy"};

        // 2. Array of Person objects using the names array
        Person[] people = new Person[names.length];
        for (int i = 0; i < names.length; i++) {
            people[i] = new Person(names[i]);
        }

        // 3. Array of Zombie objects with a random length (15-45)
        int zombieCount = RandomUtil.randomInRange(GameConfig.MIN_ZOMBIES, GameConfig.MAX_ZOMBIES);
        Zombie[] zombies = new Zombie[zombieCount];
        for (int i = 0; i < zombies.length; i++) {
            zombies[i] = new Zombie();
        }

        System.out.println("Survivors: " + people.length);
        System.out.println("Zombies: " + zombies.length);
        System.out.println("\n--- Battle Begins ---\n");

        // 4. Battle Loop - runs until one side is wiped out
        int round = 1;
        while (countLivingPeople(people) > 0 && countLivingZombies(zombies) > 0) {
            System.out.println(">>> DAY " + round + " <<<");

            dailyFoodPhase(people);
            healWithMedkits(people);

            for (Person p : people) {
                if (!p.isLiving()) continue;
                if (countLivingZombies(zombies) == 0) break;

                Zombie target = firstLivingZombie(zombies);
                if (target == null) break;

                System.out.println(p.getName() + " vs Zombie (HP " + target.getHealth() + ")");

                boolean zombieKilled = Battle.fight(p, target);
                
                if (!p.isLiving()) {
                    System.out.println(" " + p.getName() + " has died!");
                }
                if (zombieKilled) {
                    System.out.println(" Zombie defeated!");
                    System.out.println(" " + formatStatus(p));
                }
                System.out.println();
            }

            
            System.out.println("Status after Day " + round + ":");
            System.out.println(" Living people: " + countLivingPeople(people));
            System.out.println(" Living zombies: " + countLivingZombies(zombies));
            System.out.println();
            round++;
        }

        // 5. Final Result
        System.out.println("========================");
        System.out.println(" FINAL RESULT ");
        System.out.println("========================\n");

        int survivors = countLivingPeople(people);
        int remainingZombies = countLivingZombies(zombies);

        if (survivors > 0 && remainingZombies == 0) {
            System.out.println("THE PEOPLE SURVIVED!");
            System.out.println("Survivors remaining: " + survivors);
            for (Person p : people) {
                if (p.isLiving()) System.out.println(" -" + p);
            }
        } else if (survivors == 0) {
            System.out.println("ALL PEOPLE HAVE DIED. The zombies prevail...");
            System.out.println("Zombies remaining: " + remainingZombies);
        }

        printDetailedReport(people);
    }


    /**
     * Returns a one-line status string for a person
     * Used after a kill in the round log.
     * 
     * @param p the person to describe
     * @return formatted status line
     */
    private static String formatStatus(Person p) {
        return String.format("HP: %d/%d | Shells: %d | Ammo:%d | Food:%d | Armor: %d | Medkits:%d | Knife:%s",
            p.getHealth(), GameConfig.PERSON_STARTING_HEALTH,
            p.getShells(), p.getAmmo(), p.getFood(), p.getArmor(), p.getMedkits(),
            (p.hasKnife() ? "yes" : "no"));
    }

    /**
     * Prints a detailed per-person report at the end of the simulation.
     * Includes survival status, final stats, weapon usage, damage taken, food eaten, and (for the dead) death cause and damage at death.
     * 
     * @param people the array of survivors to report on
     */
    private static void printDetailedReport(Person[] people) {
        System.out.println();
        System.out.println("========================");
        System.out.println(" DETAILED PERSON REPORT");
        System.out.println("========================");


        for (Person p : people) {
            System.out.println();
            System.out.println("--- " + p.getName() + " ---");
            System.out.println(" Status: " + (p.isLiving() ? "SURVIVED" : "DEAD"));
            System.out.println(" Final HP: " + p.getHealth() + "/" + GameConfig.PERSON_STARTING_HEALTH);
            System.out.println(" Final Shells: " + p.getShells());
            System.out.println(" Final Ammo: " + p.getAmmo());
            System.out.println(" Final Food: " + p.getFood());
            System.out.println(" Final Armor: " + p.getArmor());
            System.out.println(" Final Medkits: " + p.getMedkits());
            System.out.println(" Knife: " + (p.hasKnife() ? "YES" : "NO"));
            System.out.println(" Shotgun shells fired: " + p.getShellsFired()
                + " (hit: " + p.getShellsHit()
                + " missed: " + p.getShellsMissed() + ")");
            System.out.println(" Shots fired: " + p.getShotsFired() 
                + " (hit: " + p.getShotsHit() 
                + " missed: " + p.getShotsMissed() + ")");
            System.out.println(" Knife swings: " + (p.getKnifeHits() + p.getKnifeMisses())
                + " (hit: " + p.getKnifeHits()
                + " missed: " + p.getKnifeMisses()
                + ", kills: " + p.getKnifeKills() + ")");
            System.out.println(" Damage taken: " + p.getDamageTakenTotal()
                + " (while shooting:" + p.getDamageTakenByGun()
                + ",  while in melee: " + p.getDamageTakenByMelee() + ")");
            System.out.println(" Food eaten: " + p.getFoodEatenForConsumption() + " (normal)"
                + " + " + p.getFoodEatenForHealing() + " (healing)");
            if (p.getStarvationDamageTaken() > 0) {
                System.out.println(" Starvation: " + p.getStarvationDamageTaken() + " damage taken");
            }

            if (!p.isLiving()) {
                System.out.println(" Death cause: " + p.getDeathCause());
                System.out.println(" Damage at death: " + p.getDamageAtDeath());
            } else {
                System.out.println(" Outcome:  Made it out alive!");
            }
        }
        System.out.println();
        System.out.println("=====================");

    }

    // Survival-phase helpers
    private static void dailyFoodPhase(Person[] people) {
        for (Person p : people) {
            if (!p.isLiving()) continue;

            // Trying healing first - this consumes 1 food and heals 2 HP
            if (p.eatForHealing()) {
                System.out.println(" " + p.getName() + " eats for healing HP: " + p.getHealth());
            } else {
                // Otherwise normal consumption - also consumes 1 food
                boolean ate = p.consumeFood();
                if (!ate) {
                    System.out.println(" " + p.getName() + " has NO FOOD! takes "
                            + GameConfig.STARVATION_DAMAGE + " starvation damage. HP: " + p.getHealth());

                    if (!p.isLiving()) {
                        System.out.println(" " + p.getName() + " has starved to death!");
                    }
                }
            }
        }
    }

    private static void healWithMedkits(Person[] people) {
        for (Person p : people) {
            if (p.isLiving() && p.getHealth() < GameConfig.PERSON_STARTING_HEALTH) {
                if (p.useMedkit()) {
                    System.out.println(" " + p.getName() + " uses a medkit! HP: " + p.getHealth());
                }
            }
        }
    }

    // Helper methods (SRP: Main controls flow)

    private static int countLivingPeople(Person[] people) {
        int n = 0;
        for (Person p : people) if (p.isLiving()) n++;
        return n;
    }

    private static int countLivingZombies(Zombie[] zombies) {
        int n = 0;
        for (Zombie z : zombies) if (z.isLiving()) n++;
        return n;
    }

    private static Zombie firstLivingZombie(Zombie[] zombies) {
        for (Zombie z : zombies) if (z.isLiving()) return z;
        return null;
    }
}