/**
 * RandomUtil handles random number generation.
 * SRP: One reason to change - when randomness logic changes.
 * Keeps randomness serparate from combat logic.
 * 
 */

public class RandomUtil {
   
    
    /**
     * Returns a random integer between min and max (inclusive).
     * 
     * @param min the lowest possible value (inclusive)
     * @param max the highest possible value (inclusive)
     * @return a random int in [min, max]
     *  @throws IllegalArgumentException if max < min
     */

    public static int randomInRange(int min, int max) {
        if (max < min) {
            throw new IllegalArgumentException("max < min");
        }
        return (int) (Math.random() * (max - min + 1)) + min;
    }

    /**
     * Returns true with probability 1/outof.
     * Example: chance(5) -> true 1 in 5 times.
     * If outof is 0 or negative, returns false (0-in-N means "never").
     * 
     * @param outOf the denominator of the probability (1 in N)
     *@return true with probability 1/outOf, false otherwise
     */
    public static boolean chance(int outof) {
        if (outof <= 0) return false;
        return randomInRange( 1, outof) == 1;
    }
}
