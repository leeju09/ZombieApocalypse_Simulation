/**
 * GameConfig holds all constants used throughout the program.
 * SRP: Only contains static constants. No methods, no logic.
 * One reason to change - when game rules change.
 * 
 * Values fall into two categories:
 * - gun hit chance, gun crit chance, gun damage, zombie HP, melee probabilities, zombie array size and range, ammo per shot.
 * Project extension:
 * - person health/ammo/food/medkits/armor/dodge, shotgun, knife, food healing, starvation damage.
 */

public class GameConfig { 

    // Person starting stats
    public static final int PERSON_STARTING_HEALTH = 15; 
    public static final int PERSON_STARTING_AMMO = 25; 
    public static final int PERSON_STARTING_FOOD = 10;
    public static final int PERSON_STARTING_MEDKITS = 2;
    public static final int PERSON_STARTING_ARMOR = 3;
    public static final int PERSON_STARTING_DODGE = 3;

    // Shotgun mechanics
    public static final int PERSON_STARTING_SHELLS = 12;
    public static final int SHOTGUN_HIT_CHANCE = 3;
    public static final int SHOTGUN_MISS_DAMAGE = 5;

    // Food mechanics
    public static final int FOOD_CONSUMPTION_PER_ROUND = 1;
    public static final int STARVATION_DAMAGE = 1;
    public static final int FOOD_HEAL_AMOUNT = 2;

    // Medkit mechanics
    public static final int MEDKIT_HEAL_AMOUNT = 5;

    // Zombie starting stats
    // 2 non-critical gun hits (2 damg each) = 4 HP
    public static final int ZOMBIE_STARTING_HEALTH = 4;

    // Zombie array size
    public static final int MIN_ZOMBIES = 25;
    public static final int MAX_ZOMBIES = 60;

    // Gun battle probabilities
    public static final int GUN_HIT_CHANCE = 5; 
    public static final int GUN_CRITICAL_CHANCE = 3;
    public static final int GUN_DAMAGE = 2;
    public static final int GUN_ZOMBIE_CRIT_CHANCE = 2;
    public static final int GUN_ZOMBIE_CRIT_DAMAGE = 1; 
    public static final int GUN_ZOMBIE_DAMAGE = 1; 

    // Melee (unarmed) battle probabilities
    public static final int MELEE_HIT_CHANCE = 3;
    public static final int MELEE_CRITICAL_CHANCE = 10;
    public static final int MELEE_DAMAGE = 1;
    public static final int MELEE_MISS_DEATH_CHANCE = 2;

    // Knife Melee battle probabilities
    public static final int KNIFE_HIT_CHANCE = 2;

    // knife miss follows the same death rul as fists

}
