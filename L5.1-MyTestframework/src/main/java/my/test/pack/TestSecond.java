package my.test.pack;

import ru.otus.test.framework.annotation.After;
import ru.otus.test.framework.annotation.Before;
import ru.otus.test.framework.annotation.Test;

/**
 * Created by skomorokhov on 10.07.2017.
 */
public class TestSecond {
    private int before=0;
    private int after=0;
    private int test=0;
    @Before
    public void Start(){
        System.out.println("TestSecond Before");
        after =1;
        before =1;
    }
    @After
    public void End(){
        System.out.println("TestSecond After");
        before=0;
        after=0;
    }

    @Test
    public void firstTest(){
        System.out.println("Test1");
        test=1;
    }
    @Test(expected =NullPointerException.class)
    public void secondTest(){
        System.out.println("NPE");
        throw new NullPointerException("test");
    }

    @Test
    public void thirdTest(){
        System.out.println("Test3");
        test=1;
    }
}
