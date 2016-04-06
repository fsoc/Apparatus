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
  static final int MOD = 1000003;

  public static void main(String[] args) {
    Kattio io = new Kattio(System.in, System.out);

    io.println(processPictures(System.in));

    io.flush();
    io.close();
  }

  public static int processPictures(InputStream in) {
    Kattio io = new Kattio(in, System.out);

    // Read the first line with the number of bits and number of pictures for the problem
    int bits = io.getInt();
    int pictureAmount = io.getInt();

    // A datastructure containing pictures in the form of a switches and lights
    BigInteger[][] pictures = new BigInteger[pictureAmount][2];

    int p = 0; //Picture counter
    // Get all the pictures
    while (io.hasMoreTokens()) {

      pictures[p][0] = new BigInteger(io.getWord(), 2);
      pictures[p][1] = new BigInteger(io.getWord(), 2);
      p++;

    }

    // In the special case where there is no pictures we add a picture that gives no information
    if (pictureAmount == 0) {
      pictureAmount = 1;
      pictures = new BigInteger[pictureAmount][2];

      String input = "";
      for (int i = 0; i<bits; i++) {
        input += "1";

      }
      pictures[p][0] = new BigInteger(input, 2);
      pictures[p][1] = new BigInteger(input, 2);
    }


    Map<String, Integer> setCounter = new HashMap<String, Integer>(pictureAmount);

    // Examine the bits of all pictures one, by one
    BigInteger two = new BigInteger("2");
    for (int i = 0; i < bits; i++) {
      BigInteger oneBitSet = two.pow(i);

      // Investigate the pictures that has this bit set to see the set containing it
      String key = "";
      for (int j = 0; j < pictureAmount; j++) {
        if (!pictures[j][0].and(oneBitSet).equals(BigInteger.ZERO)) {
          key += j + ",";
        }
      }

      if (key.length() > 0) {
        // Store the number of findings of bits of this set in a hashmap
        if (setCounter.containsKey(key)) {
          setCounter.put(key, setCounter.get(key) + 1);
        } else {
          setCounter.put(key, 1);
        }
      }
    }

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

  public static int moduloFactorial(int n, int modulo) {
    int result = 1;

    while (n != 0) {
      result = (result * n) % modulo;
      n--;
    }

    return result;
  }
}
