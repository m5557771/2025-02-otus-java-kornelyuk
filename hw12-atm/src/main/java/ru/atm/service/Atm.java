package ru.atm.service;

import java.util.Map;
import org.jetbrains.annotations.NotNull;
import ru.atm.model.Banknote;

public interface Atm {
    void pushBanknotes(@NotNull Map<Banknote, Long> banknotes);

    @NotNull
    Map<Banknote, Long> getMoney(long amount);

    @NotNull
    Map<Banknote, Long> getAllMoney();
}
