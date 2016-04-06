package fsoc;

import org.junit.*;
import static org.junit.Assert.*;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.File;
import java.io.IOException;
import java.lang.StringBuilder;
import java.util.Collection;
import java.util.ArrayList;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ApparatusTest {

  @Parameters
  public static Collection<Object[]> getFiles() {
    Collection<Object[]> params = new ArrayList<Object[]>();
    for (File f : new File("./samples").listFiles()) {
      if (f.toString().contains(".in")) {
        Object[] arr = new Object[] { f };
        params.add(arr);
      }
    }
    return params;
  }

  private  File file;
  public ApparatusTest(File file) {
    this.file = file;
  }

  @Test
  public void testY() throws IOException {
    executeTextFiles(file.toString(), file.toString().replace("in","ans"));
  }

  public void executeTextFiles(String indata, String answer) throws IOException {
    InputStream input = new FileInputStream(indata);
    InputStream answerStream = new FileInputStream(answer);
    String answerString = streamToString(answerStream);

    assertEquals(answerString, "" +Apparatus.processPictures(input));

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
