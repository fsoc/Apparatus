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

public class Apparatus {

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
    int pictureAmount = io.getInt() * 2;

    // A datastructure containing pictures in the form of a switches and lights
    BigInteger[][] pictures = new BigInteger[pictureAmount][2];

    int p = 0; //Picture counter
    // Get all the pictures
    while (io.hasMoreTokens()) {
      String switches = io.getWord();
      String lights = io.getWord();

      pictures[p][0] = new BigInteger(switches, 2);
      pictures[p][1] = new BigInteger(lights, 2);

      int offset = pictureAmount / 2;
      pictures[offset + p][0] = ApparatusHelper.flippedBits(new BigInteger(switches, 2), bits);
      pictures[offset + p][1] = ApparatusHelper.flippedBits(new BigInteger(lights, 2), bits);
      p++;

    }

    // In the special case where there is no pictures we add a picture that gives no information
    if (pictureAmount == 0) {
      pictureAmount = 2;
      pictures = new BigInteger[pictureAmount][2];

      pictures[0][0] = BigInteger.ZERO;
      pictures[0][1] = BigInteger.ZERO;

      pictures[1][0] = ApparatusHelper.flippedBits(BigInteger.ZERO, bits);
      pictures[1][1] = ApparatusHelper.flippedBits(BigInteger.ZERO, bits);

    }

    return analyzePictures(pictures, bits);
  }

  /**
   * The theoretical limit of different union combinations is 2^bits but we will only have 
   * around 2000 of those. The analysis iterates for every bit in bits and checks how many
   * pictures have it set, those pictures form a set, and the number of bits that set sets
   * is counted and multiplied with an factorial to give the answer.
   */
  private static int analyzePictures(BigInteger[][] pictures, int bits) {
    int pictureAmount = pictures.length;

    // Make an error check of the pictures, if an error is found 0 wirings match all pictures.
    if (errorCheck(pictures, pictureAmount/2)) {
      return 0;
    }

    Map<String, Integer> setCounter = new HashMap<String, Integer>(pictureAmount);

    // Examine the bits of all pictures one, by one
    for (int i = 0; i < bits; i++) {

      // Investigate the pictures that has this bit set to see the set containing it
      String key = "";
      for (int j = 0; j < pictureAmount; j++) {
        if (pictures[j][0].testBit(i)) {
          key += j + ",";
        }
      }

      if (key.length() > 0) {
        // Store the number of findings of bits of this set in a hashmap
        Integer findings = setCounter.get(key);
        if (findings != null) {
          setCounter.put(key, findings + 1);
        } else {
          setCounter.put(key, 1);
        }
      }
    }
    return ApparatusHelper.multiply(setCounter);
  }

  private static boolean errorCheck(BigInteger[][] pictures, int pictureAmount) {
    // Make an error check of the pictures, if an error is found 0 wirings match all pictures.
    // Check if any combination of two pictures does not make sense by checking the amount
    // of switches and lights set on each picture and all combinations.
    int jstart = 0;

    for (int i = 0; i < pictureAmount; i++) {
      for (int j = jstart; j < pictureAmount; j++) {
        if (pictures[i][0].and(pictures[j][0]).bitCount() != pictures[i][1].and(pictures[j][1]).bitCount()) {
          return true;
        }
      }
      // The order of the and operation does not matter, increase j when we can.
      jstart++;
    }

    return false;
  }

}
