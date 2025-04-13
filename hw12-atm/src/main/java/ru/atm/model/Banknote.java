package ru.atm.model;

public enum Banknote {
    TEN_RUB(10),
    FIFTY_RUB(50),
    ONE_HUNDRED_RUB(100),
    FIVE_HUNDRED_RUB(500),
    ONE_THOUSAND_RUB(1000),
    TWO_THOUSAND_RUB(2000),
    FIVE_THOUSAND_RUB(5000);

    public final long value;

    Banknote(long value) {
        this.value = value;
    }
}
