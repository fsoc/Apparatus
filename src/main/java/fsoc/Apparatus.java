/**
 * https://spotify.kattis.com/problems/apparatus
 */

package fsoc;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Set;

public class Apparatus {
  static final int MOD = 1000003;

  public static void main(String[] args) {
    Kattio io = new Kattio(System.in, System.out);

    io.println(processPictures(System.in));

    io.flush();
    io.close();
  }

  /**
   * Process and load the pictures from the input into a datastructure. Also make
   * sure that the inverse of each picture is added so we add all possible wirings.
   */
  public static int processPictures(InputStream in) {
    Kattio io = new Kattio(in, System.out);

    // Read the first line with the number of bits and number of pictures for the problem
    int bits = io.getInt();
    int pictureAmount = io.getInt();

    // A datastructure containing pictures in the form of a switches and lights
    BigInteger[][] pictures = new BigInteger[pictureAmount][2];

    // A datastructure containing pictures and their flipped bit versions as an array of char arrays
    // it is only used temporarily for reading in the string data.
    char[][] switchArray = new char[pictureAmount*2][bits];
    char[][] lightArray = new char[pictureAmount*2][bits];

    int p = 0; //Picture counter
    // Get all the pictures
    while (io.hasMoreTokens()) {
      String switches = io.getWord();
      String lights = io.getWord();

      pictures[p][0] = new BigInteger(switches, 2);
      pictures[p][1] = new BigInteger(lights, 2);

      switchArray[p] = switches.toCharArray();
      lightArray[p] = lights.toCharArray();

      switchArray[pictureAmount + p] = ApparatusHelper.flippedBits(new BigInteger(switches, 2), bits).toCharArray();
      lightArray[pictureAmount + p]= ApparatusHelper.flippedBits(new BigInteger(lights, 2), bits).toCharArray();

      p++;
    }

    // In the special case where there is no pictures then one set with n! possibilities is returned 
    if (pictureAmount == 0) {
      return ApparatusHelper.moduloFactorial(bits, MOD);
    }

    return analyzePictures(ApparatusHelper.transposeMatrix(switchArray), ApparatusHelper.transposeMatrix(lightArray));
  }

  /**
   * The theoretical limit of different union combinations is 2^bits but we will only have 
   * around 2000 of those. The analysis iterates for every bit in bits and checks how many
   * pictures have it set, those pictures form a set, and the number of bits that set sets
   * is counted and multiplied with an factorial to give the answer.
   */
  private static int analyzePictures(char[][] switches, char[][] lights) {
    int pictureAmount = switches.length;

    Map<String, Integer> switchesCounter = new HashMap<String, Integer>(pictureAmount);
    Map<String, Integer> lightsCounter = new HashMap<String, Integer>(pictureAmount);

    for (int i = 0; i < switches.length; i++) {
      incrementOrCreate(switchesCounter, new String(switches[i]));
      incrementOrCreate(lightsCounter, new String(lights[i]));
    }

    // Check for errors in pictures by checking if the set occurances are the same
    // they should be since the switches and lights are linearly mapped.
    if (!switchesCounter.equals(lightsCounter))
      return 0;
    return ApparatusHelper.multiply(switchesCounter);
  }

  /** Appends 1 or creates an integer with the value of 1 for the key if it didnt exist
   * and if the key contains the value 1, symbolizing a set bit.
   */
  private static void incrementOrCreate(Map<String, Integer> hashMap, String key) {
    // If this array contains bits that are set, then there are atleast some pictures
    // that can create a set that we care about counting
    if (key.indexOf('1') > -1) {
      // Store the number of findings of bits of this set in a hashmap
      Integer findings = hashMap.get(key);
      if (findings != null) {
        hashMap.put(key, findings + 1);
      } else {
        hashMap.put(key, 1);
      }
    }
  }

}
