package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.annotation.After;
import ru.otus.annotation.Before;
import ru.otus.annotation.Test;

@SuppressWarnings({"java:S112"})
public class TestClass {
    private static final Logger log = LoggerFactory.getLogger(TestClass.class);

    @Test
    public void test1() {
        log.debug("Test1 OK");
    }

    @Test
    public void test2() {
        log.debug("Test2 - exception");
        throw new RuntimeException("Test2 - exception");
    }

    @Before
    public void before1() {
        log.debug("Before1");
    }

    @Before
    public void before2() {
        log.debug("Before2");
    }

    @After
    public void after() {
        log.debug("After");
    }
}
