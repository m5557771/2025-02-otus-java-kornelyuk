package homework;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class CustomerService {
    private final Comparator<Customer> byScore = Comparator.comparing(Customer::getScores);
    private final TreeMap<Customer, String> map = new TreeMap<>(byScore);

    // todo: 3. надо реализовать методы этого класса
    // важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны

    public Map.Entry<Customer, String> getSmallest() {
        // Возможно, чтобы реализовать этот метод, потребуется посмотреть как Map.Entry сделан в jdk
        return getCopyOfEntry(map.firstEntry());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        return getCopyOfEntry(map.higherEntry(customer));
    }

    public void add(Customer customer, String data) {
        map.put(customer.clone(), data);
    }

    private Map.Entry<Customer, String> getCopyOfEntry(Map.Entry<Customer, String> entry) {
        if (entry == null) {
            return null;
        }
        var tmpMap = new TreeMap<Customer, String>(byScore);
        tmpMap.put(entry.getKey().clone(), entry.getValue());

        return tmpMap.firstEntry();
    }
}
