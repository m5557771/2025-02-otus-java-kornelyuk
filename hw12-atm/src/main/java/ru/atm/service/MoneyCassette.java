package ru.atm.service;

import java.util.Map;
import org.jetbrains.annotations.NotNull;
import ru.atm.model.Banknote;

public interface MoneyCassette {
    void pushBanknotes(Map<Banknote, Long> banknotes);

    @NotNull
    Map<Banknote, Long> getMoney(long sum);

    void openDispenser();
}
