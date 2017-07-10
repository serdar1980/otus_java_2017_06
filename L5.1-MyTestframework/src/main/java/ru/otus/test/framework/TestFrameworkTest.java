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
    public Start(){
        after =1;
        before =1;
    }
    @After
    public End(){
        before=0
        after=0;
    }

    @Test
    public firstTest(){
        test=1;
    }
    @Test
    public secondTest(){
        test=1;
    }
    @Test
    public thirdTest(){
        test=1;
    }
}
