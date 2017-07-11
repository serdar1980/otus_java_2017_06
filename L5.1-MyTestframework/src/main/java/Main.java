import my.test.pack.TestFirst;
import ru.otus.test.framework.analyzer.TestAnnotationAnalyzer;

/**
 * Created by skomorokhov on 10.07.2017.
 */
public class Main {
  public static void main(String ... args){
    TestAnnotationAnalyzer testAnnotationAnalyzer = new TestAnnotationAnalyzer();
    try {
      System.out.println("Before test one class");
      testAnnotationAnalyzer.analyz(TestFirst.class);
      System.out.println("Before test package");
      testAnnotationAnalyzer.analyzPackage("my.test.pack");

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
