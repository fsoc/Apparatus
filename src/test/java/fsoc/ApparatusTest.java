package fsoc;

import org.junit.*;
import static org.junit.Assert.*;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.File;
import java.io.IOException;
import java.lang.StringBuilder;

public class ApparatusTest {
  @Test
  public void kestSample0() throws IOException {
    executeTextFiles("samples/apparatus.00.in", "samples/apparatus.00.ans");
  }

  public void executeTextFiles(String indata, String answer) throws IOException {
    InputStream input = new FileInputStream(indata);
    InputStream answerStream = new FileInputStream(answer);
    String answerString = streamToString(answerStream);

    assertEquals(answerString, Apparatus.processPictures(input));

    input.close();
    answerStream.close();
  }

  static String streamToString(java.io.InputStream is) throws IOException {
    StringBuilder builder = new StringBuilder();
    int ch;
    while ((ch = is.read()) != -1){
      if (ch != '\n')
        builder.append((char)ch);
    }
    return builder.toString();
  }

}