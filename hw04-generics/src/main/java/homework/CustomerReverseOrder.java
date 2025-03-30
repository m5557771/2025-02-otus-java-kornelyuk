package homework;

import java.util.*;

@SuppressWarnings({"java:S2975", "java:S1135", "java:S1488"})
public class CustomerReverseOrder {
    private final Deque<Customer> deque = new ArrayDeque<>();

    // todo: 2. надо реализовать методы этого класса
    // надо подобрать подходящую структуру данных, тогда решение будет в "две строчки"

    public void add(Customer customer) {
        deque.add(customer);
    }

    public Customer take() {
        return deque.pollLast();
    }
}
