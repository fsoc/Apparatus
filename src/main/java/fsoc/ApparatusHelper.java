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
  public static int moduloFactorial(int n, int modulo) {
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

  /**
   * Transpose picture data to make it easier to find if the n-th bit is set in which pictures. We
   * do this in order to keep track of how many bits each distinct "set" in order to later multiply
   * the factorials of each number of sets together to reach the answer.
   */
  public static char[][] transposeMatrix(char[][] arr) {
    char[][] transposed = new char[arr[0].length][arr.length];

    for (int i = 0; i < arr.length; i++) {
      for (int j = 0; j < arr[0].length; j++) {
        transposed[j][i] = arr[i][j];
      }
    }
    return transposed;
  }
}
