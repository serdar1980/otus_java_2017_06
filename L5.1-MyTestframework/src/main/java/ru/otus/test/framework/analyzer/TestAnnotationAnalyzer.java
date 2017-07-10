package ru.otus.test.framework.analyzer;

import ru.otus.test.framework.annotation.After;
import ru.otus.test.framework.annotation.Before;
import ru.otus.test.framework.annotation.Test;

import java.lang.reflect.Method;

/**
 * Created by skomorokhov on 10.07.2017.
 */
public class TestAnnotationAnalyzer {
    public void analyz(Class<?> clazz) throws Exception {
        Method[] methods = clazz.getMethods();
        Method methodBefore =null;
        Method methodsAfter=null;
        Method methodTmp=null;
        int pass = 0;
        int fail = 0;

        for (Method method : methods) {
            if (method.isAnnotationPresent(Before.class)) {
                methodBefore= method;
            }
            if (method.isAnnotationPresent(After.class)) {
                methodsAfter= method;
            }
        }

        for (Method method : methods) {
            if (method.isAnnotationPresent(Test.class)) {
                // Получаем доступ к атрибутам
                Object  testObject =  Class.forName(clazz.getCanonicalName()).cast(clazz.newInstance());
                method.getAnnotation(Test.class);
                try {
                    if(methodBefore != null){
                        methodTmp = testObject.getClass().getMethod(methodBefore.getName());
                        methodTmp.invoke(testObject, null);
                    }
                        methodTmp = testObject.getClass().getMethod(method.getName());
                        methodTmp.invoke(testObject, null);
                    if(methodsAfter != null){
                        methodTmp = testObject.getClass().getMethod(methodsAfter.getName());
                        methodTmp.invoke(testObject, null);
                    }
                    pass++;
                } catch (Exception e) {
                        fail++;
                }
            }
        }
    }
}
