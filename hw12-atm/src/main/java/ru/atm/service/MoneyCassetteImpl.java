package ru.atm.service;

import java.util.EnumMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.atm.model.Banknote;

public class MoneyCassetteImpl implements MoneyCassette {
    private static final Logger log = LoggerFactory.getLogger(MoneyCassetteImpl.class);
    private final Map<Banknote, Long> amountOfBanknotes = new EnumMap<>(Banknote.class);

    @Override
    public void pushBanknotes(@NotNull Map<Banknote, Long> banknotes) {
        for (var entry : banknotes.entrySet()) {
            amountOfBanknotes.put(
                    entry.getKey(), amountOfBanknotes.getOrDefault(entry.getKey(), 0L) + entry.getValue());
        }
    }

    @Override
    public @NotNull Map<Banknote, Long> getMoney(long sum) {
        Map<Banknote, Long> banknotes = new EnumMap<>(Banknote.class);

        var keys = amountOfBanknotes.keySet().stream().sorted().toList().reversed();
        long collected = 0;
        int index = 0;
        while (collected < sum && index < keys.size()) {
            var banknote = keys.get(index);
            var currCount = Math.min((sum - collected) / banknote.value, amountOfBanknotes.get(banknote));
            if (currCount > 0) {
                collected += banknote.value * currCount;
                amountOfBanknotes.put(banknote, amountOfBanknotes.get(banknote) - currCount);
                if (amountOfBanknotes.get(banknote) == 0) {
                    amountOfBanknotes.remove(banknote);
                }
                banknotes.put(banknote, currCount);
            }
            index++;
        }
        if (collected != sum) {
            pushBanknotes(banknotes);
            throw new IllegalArgumentException("Not enough banknotes");
        }

        return banknotes;
    }

    @Override
    public void openDispenser() {
        log.info("Dispenser is opened");
    }
}
