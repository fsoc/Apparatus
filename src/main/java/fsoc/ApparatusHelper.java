/**
 * Helper functions for the Apparatus class.
 */

package fsoc;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

public class ApparatusHelper {
  static final int MOD = 1000003;

  /**
   * Multiplies the factorials of the different sets based of the number of them found.
   */
  public static int multiply(Map<String, Integer> setCounter) {
    // Make an error check of the pictures, if an error is found 0 wirings match all pictures.
    int wirings = 1;

    Iterator<Map.Entry<String, Integer>> it = setCounter.entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry<String, Integer> pair = it.next();
      // Only add values >1 since 0! = 1! = 1
      if (pair.getValue() > 1) {
        wirings *= moduloFactorial(pair.getValue(), MOD);
      }
    }

    return wirings;
  }

  /**
   * Factorial using modulo.
   */
  private static int moduloFactorial(int n, int modulo) {
    int result = 1;

    while (n != 0) {
      result = (result * n) % modulo;
      n--;
    }

    return result;
  }

  /**
   * Only flips the first n bits of the number
   */
  public static BigInteger flippedBits(BigInteger number, int n) {
    for (int i = 0; i < n; i++) {
      number = number.flipBit(i);
    }
    return number;
  }
}
