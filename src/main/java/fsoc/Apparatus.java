/**
 * https://spotify.kattis.com/problems/apparatus
 */

package fsoc;
import java.io.InputStream;
import java.io.OutputStream;

public class Apparatus {
  public static void main(String[] args) {
    Kattio io = new Kattio(System.in, System.out);
    io.println("hello");
    io.flush();
    io.close();
  }

  public static String processPictures(InputStream in) {
    return "2";
  }
}
