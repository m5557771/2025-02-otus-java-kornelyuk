package ru.atm;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.atm.model.Banknote;
import ru.atm.service.Atm;
import ru.atm.service.AtmImpl;

@SuppressWarnings({"java:S2629"})
public class AtmApp {
    private static final Logger log = LoggerFactory.getLogger(AtmApp.class);

    public static void main(String[] args) {
        log.info("Start");

        Atm atm = new AtmImpl();

        log.info("push 150 rub");
        atm.pushBanknotes(Map.of(
                Banknote.FIFTY_RUB, 1L,
                Banknote.TEN_RUB, 10L));

        log.info("getting 60 rub");
        var result = atm.getMoney(60);
        log.info(result.toString());

        log.info("getting 1 rub");
        try {
            atm.getMoney(1);
        } catch (Exception e) {
            log.info(e.getMessage());
        }

        log.info("getting all");
        result = atm.getAllMoney();
        log.info(result.toString());

        log.info("getting 1 rub");
        try {
            atm.getMoney(1);
        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }
}
