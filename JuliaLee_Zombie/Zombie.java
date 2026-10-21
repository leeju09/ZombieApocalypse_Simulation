/**
 * Zombie store and manages the state of the zombie.
 * SRP: Zombie only stores zombie state. Combat logic is NOT inside this class.
 */
public class Zombie {
    
    // Instance variables - all private
    private int health;
    private boolean  isLiving;

    /**
     *  Constructs a zombie with default starting health from GameConfig.
     */
    public Zombie() {
        this.health = GameConfig.ZOMBIE_STARTING_HEALTH;
        this.isLiving = true;
    }

    /**
     * Returns the zombie's current HP
     * 
     * @return current health
     */
    public int getHealth()      { return health; }
    public boolean isLiving()   { return isLiving; }

    /**
     * Set the zombie's HP
     * 
     * @param health the new HP value
     */
    public void setHealth(int health)    { this.health = health; }
    
    /**
     * Sets the zombie's living status.
     * 
     * @param living true for alive, false for dead.
     */
    public void setLiving(boolean living){ this.isLiving = living; }

    /**
     * Return a string representation of the zombie's state
     * 
     * @return formatted string showing HP and living status
     */
    @Override
    public String toString() {
        return String.format("Zombie [Health: %d, %s]",
                health, (isLiving ? "ALIVE" : "DEAD"));
    }
}