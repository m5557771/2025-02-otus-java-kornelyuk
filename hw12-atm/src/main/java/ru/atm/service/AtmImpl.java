package ru.atm.service;

import java.util.Map;
import org.jetbrains.annotations.NotNull;
import ru.atm.exception.NotEnoughBanknotes;
import ru.atm.exception.NotEnoughMoney;
import ru.atm.model.Banknote;
import ru.atm.util.Calc;

public class AtmImpl implements Atm {
    private volatile long amountOfMoney = 0L;

    private final MoneyCassette moneyCassette;

    public AtmImpl(MoneyCassette moneyCassette) {
        this.moneyCassette = moneyCassette;
    }

    public AtmImpl() {
        this.moneyCassette = new MoneyCassetteImpl();
    }

    @Override
    public synchronized void pushBanknotes(@NotNull Map<Banknote, Long> banknotes) {
        moneyCassette.pushBanknotes(banknotes);
        amountOfMoney += Calc.calculateSum(banknotes);
    }

    @Override
    public synchronized @NotNull Map<Banknote, Long> getMoney(long sum) {
        if (sum > amountOfMoney) {
            throw new NotEnoughMoney("Not enough money. Try to get " + sum + ", but available " + amountOfMoney);
        }
        Map<Banknote, Long> banknotes;
        try {
            banknotes = moneyCassette.getMoney(sum);
            moneyCassette.openDispenser();
        } catch (Exception e) {
            throw new NotEnoughBanknotes("Not enough banknotes. Try to get " + sum);
        }
        amountOfMoney -= sum;

        return banknotes;
    }

    @Override
    public @NotNull Map<Banknote, Long> getAllMoney() {
        return getMoney(amountOfMoney);
    }
}
