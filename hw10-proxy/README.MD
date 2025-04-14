Создаем класс через прокси в App и вызываем 3 метода:
```
var testLogging = TestLoggingProxy.createTestLogging();
testLogging.calculation(1);
testLogging.calculation(1, 2);
testLogging.calculation(1, 2, "test");

```
Аннотацией @Log помечен второй и третий метод.

Вывод программы:
```
22:03:44.134 [main] INFO ru.proxy.App -- Start
22:03:44.154 [main] INFO ru.proxy.util.TestLoggingProxy -- invoking method: calculation
22:03:44.155 [main] INFO ru.proxy.util.TestLoggingProxy -- parameters: 1 2 
22:03:44.157 [main] INFO ru.proxy.util.TestLoggingProxy -- invoking method: calculation
22:03:44.157 [main] INFO ru.proxy.util.TestLoggingProxy -- parameters: 1 2 test 
```