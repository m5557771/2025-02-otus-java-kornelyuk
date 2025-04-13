package ru.atm.util;

import java.util.Map;
import ru.atm.model.Banknote;

public class Calc {
    private Calc() {}

    public static long calculateSum(Map<Banknote, Long> banknotes) {
        long result = 0;
        if (banknotes != null) {
            for (var entry : banknotes.entrySet()) {
                result += entry.getKey().value * entry.getValue();
            }
        }
        return result;
    }
}
