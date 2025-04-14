package ru.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.proxy.util.TestLoggingProxy;

public class App {
    private static final Logger log = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        log.info("Start");

        var testLogging = TestLoggingProxy.createTestLogging();
        testLogging.calculation(1);
        testLogging.calculation(1, 2);
        testLogging.calculation(1, 2, "test");
    }
}
