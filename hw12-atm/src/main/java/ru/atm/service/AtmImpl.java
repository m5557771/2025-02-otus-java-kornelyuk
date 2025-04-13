package ru.atm.service;

import java.util.Map;
import org.jetbrains.annotations.NotNull;
import ru.atm.exception.NotEnoughBanknotes;
import ru.atm.exception.NotEnoughMoney;
import ru.atm.model.Banknote;
import ru.atm.util.Calc;

public class AtmImpl implements Atm {
    private volatile long amountOfMoney = 0L;

    private final Sdk sdk;

    public AtmImpl(Sdk sdk) {
        this.sdk = sdk;
    }

    public AtmImpl() {
        this.sdk = new SdkImpl();
    }

    @Override
    public synchronized void pushBanknotes(@NotNull Map<Banknote, Long> banknotes) {
        sdk.pushBanknotes(banknotes);
        amountOfMoney += Calc.calculateSum(banknotes);
    }

    @Override
    public synchronized @NotNull Map<Banknote, Long> getMoney(long sum) {
        if (sum > amountOfMoney) {
            throw new NotEnoughMoney("Not enough money");
        }
        Map<Banknote, Long> banknotes;
        try {
            banknotes = sdk.getMoney(sum);
            sdk.openDispenser();
        } catch (Exception e) {
            throw new NotEnoughBanknotes(e.getMessage());
        }
        amountOfMoney -= sum;

        return banknotes;
    }

    @Override
    public @NotNull Map<Banknote, Long> getAllMoney() {
        return getMoney(amountOfMoney);
    }
}
