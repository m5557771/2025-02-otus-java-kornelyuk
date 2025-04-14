package ru.proxy;

public class TestLogging implements TestLoggingInterface {
    @Override
    public void calculation(int param1) {
        // empty
    }

    @Log
    @Override
    public void calculation(int param1, int param2) {
        // empty
    }

    @Log
    @Override
    public void calculation(int param1, int param2, String param3) {
        // empty
    }
}
