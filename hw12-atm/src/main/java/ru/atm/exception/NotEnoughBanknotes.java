package ru.atm.exception;

public class NotEnoughBanknotes extends RuntimeException {
    public NotEnoughBanknotes(String message) {
        super(message);
    }
}
