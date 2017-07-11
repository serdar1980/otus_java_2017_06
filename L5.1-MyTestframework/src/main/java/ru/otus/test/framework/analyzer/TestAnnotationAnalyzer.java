package ru.otus.test.framework.analyzer;

import ru.otus.test.framework.annotation.After;
import ru.otus.test.framework.annotation.Before;
import ru.otus.test.framework.annotation.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;

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
                Test test = method.getAnnotation(Test.class);
                Class expected = test.expected();
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
                    if(((InvocationTargetException) e).getTargetException().getClass() != expected) {
                        fail++;
                    }else{
                        pass++;
                    }
                }
            }
        }
        System.out.println("Pass test"+pass);
        System.out.println("Fail test"+fail);
    }

    public void analyzPackage(String pack ) throws Exception{
        ClassLoader cl = TestAnnotationAnalyzer.class.getClassLoader();
        String packageDir = pack.replaceAll("[.]", "/");
        URL upackage = cl.getResource(packageDir);
        InputStream in = (InputStream) upackage.getContent();
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line = null;
        while ((line = br.readLine()) != null) {
            if(line.endsWith(".class")){
                this.analyz(Class.forName( pack+"."+line.substring(0,line.lastIndexOf('.'))));
            }
        }
    }
}
