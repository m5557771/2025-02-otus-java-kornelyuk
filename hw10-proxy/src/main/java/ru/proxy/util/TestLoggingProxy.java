package ru.proxy.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.proxy.Log;
import ru.proxy.TestLogging;
import ru.proxy.TestLoggingInterface;

public class TestLoggingProxy {
    private static final Logger log = LoggerFactory.getLogger(TestLoggingProxy.class);

    private TestLoggingProxy() {}

    public static TestLoggingInterface createTestLogging() {
        InvocationHandler handler = new TestInvocationHandler(new TestLogging(), getLogMethods());

        return (TestLoggingInterface) Proxy.newProxyInstance(
                TestLoggingProxy.class.getClassLoader(), new Class<?>[] {TestLoggingInterface.class}, handler);
    }

    private static Set<Method> getLogMethods() {
        Set<Method> result = new HashSet<>();
        var methods = TestLogging.class.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Log.class)) {
                try {
                    result.add(
                            TestLoggingInterface.class.getDeclaredMethod(method.getName(), method.getParameterTypes()));
                } catch (Exception ignored) {
                    // empty
                }
            }
        }
        return result;
    }

    static class TestInvocationHandler implements InvocationHandler {
        private final TestLoggingInterface testLogging;
        private final Set<Method> shouldLogMethods;

        TestInvocationHandler(TestLoggingInterface testLogging, Set<Method> shouldLogMethods) {
            this.testLogging = testLogging;
            this.shouldLogMethods = shouldLogMethods;
        }

        @SuppressWarnings({"java:S3457", "java:S2629"})
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (shouldLogMethods.contains(method)) {
                log.info("invoking method: {}", method.getName());
                log.info("parameters: " + "{} ".repeat(args.length), args);
            }
            return method.invoke(testLogging, args);
        }

        @Override
        public String toString() {
            return "DemoInvocationHandler{" + "myClass=" + testLogging + '}';
        }
    }
}
