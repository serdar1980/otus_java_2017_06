import ru.otus.test.framework.TestFrameworkTest;
import ru.otus.test.framework.analyzer.TestAnnotationAnalyzer;

/**
 * Created by skomorokhov on 10.07.2017.
 */
public class Main {
  public static void main(String ... args){
    TestAnnotationAnalyzer testAnnotationAnalyzer = new TestAnnotationAnalyzer();
    try {
      testAnnotationAnalyzer.analyz(TestFrameworkTest.class);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
