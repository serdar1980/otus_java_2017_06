package ru.otus.test.framework;

import ru.otus.test.framework.annotation.After;
import ru.otus.test.framework.annotation.Before;
import ru.otus.test.framework.annotation.Test;

/**
 * Created by skomorokhov on 10.07.2017.
 */
public class TestFrameworkTest {
    private int before=0;
    private int after=0;
    private int test=0;
    @Before
    public void Start(){
        after =1;
        before =1;
    }
    @After
    public void End(){
        before=0;
        after=0;
    }

    @Test
    public void firstTest(){
        test=1;
    }
    @Test
    public void secondTest(){
        test=1;
    }
    @Test
    public void thirdTest(){
        test=1;
    }
}
