package ru.otus;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.annotation.After;
import ru.otus.annotation.Before;
import ru.otus.annotation.Test;

public final class TestFramework {
    private static final Logger log = LoggerFactory.getLogger(TestFramework.class);

    private TestFramework() {}

    private static List<Method> getAnnotatedMethods(
            Class<TestClass> testClass, Class<? extends Annotation> targetAnnotation) {
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
            TestClass instance = null;
            try {
                log.debug("BEGIN: {}", method.getName());
                instance = testClass.getDeclaredConstructor().newInstance();
                for (Method beforeMethod : beforeMethods) {
                    beforeMethod.invoke(instance);
                }

                method.invoke(instance);

                log.debug("SUCCESS");

                success++;
            } catch (Exception e) {
                log.debug("FAILED");
                failed++;
            } finally {
                for (Method afterMethod : afterMethods) {
                    try {
                        afterMethod.invoke(instance);
                    } catch (Exception e) {
                        log.debug("failed: {}", afterMethod.getName());
                    }
                }
            }
            log.debug("END: {}", method.getName());
        }

        log.debug("Total: {} Success: {} Failed: {}", (success + failed), success, failed);
    }
}
