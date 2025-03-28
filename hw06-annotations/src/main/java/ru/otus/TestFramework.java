package ru.otus;

import ru.otus.annotation.After;
import ru.otus.annotation.Before;
import ru.otus.annotation.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestFramework {
    private static List<Method> getAnnotatedMethods(Class<TestClass> testClass, Class<? extends Annotation> targetAnnotation) {
        List<Method> methodsList = new ArrayList<>();

        var methods = testClass.getDeclaredMethods();
        for (Method method : methods) {
            for (Annotation annotation : method.getAnnotations()) {
                if (annotation.annotationType() == targetAnnotation) {
                    methodsList.add(method);
                }
            }
        }
        return methodsList;
    }

    public static void runTestClass(Class<TestClass> testClass) {
        List<Method> beforeMethods = getAnnotatedMethods(testClass, Before.class);
        List<Method> testMethods = getAnnotatedMethods(testClass, Test.class);
        List<Method> afterMethods = getAnnotatedMethods(testClass, After.class);

        var success = 0;
        var failed = 0;
        for (Method method : testMethods) {
            try {
                System.out.println("BEGIN: " + method.getName());
                var instance = testClass.getDeclaredConstructor().newInstance();
                for (Method beforeMethod : beforeMethods) {
                    beforeMethod.invoke(instance);
                }

                method.invoke(instance);

                for (Method afterMethod : afterMethods) {
                    afterMethod.invoke(instance);
                }

                System.out.println("SUCCESS");

                success++;
            } catch (Exception e) {
                System.out.println("FAILED");

                failed++;
            }
            System.out.println("END: " + method.getName() + "\n");
        }

        System.out.println("\n\nTotal: " + (success + failed) + " Success: " + success + " Failed: " + failed);
    }
}
