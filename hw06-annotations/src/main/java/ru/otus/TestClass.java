package ru.otus;

import ru.otus.annotation.After;
import ru.otus.annotation.Before;
import ru.otus.annotation.Test;

public class TestClass {
    @Test
    public void test1() {
        System.out.println("Test1 OK");
    }

    @Test
    public void test2() {
        System.out.println("Test2 - exception");
        throw new RuntimeException();
    }

    @Before
    public void before1() {
        System.out.println("Before1");
    }

    @Before
    public void before2() {
        System.out.println("Before2");
    }

    @After
    public void after() {
        System.out.println("After");
    }
}
