## Выводы
Тесты проводил с 8Гб до 16Мб. При уменьшении памяти под хип в 2 раза примерно в 2 раза
увеличивалась активность GC (GC activity в VisualVM)

После оптимизации данных использование хипа уменьшилось, вызовы GC уменьшились, программа отработала быстрее.
Т.е. программа на 16Мб хипе отработала как и при безразмерном хипе (4-8Гб).

## Замеры

counter = 200_000_000

16mb - GC activity: 10.8%
16:03:18.025 [main] INFO ru.calculator.CalcDemo -- spend msec:74523, sec:74

32mb - GC activity: 5%
16:01:33.087 [main] INFO ru.calculator.CalcDemo -- spend msec:39465, sec:39

64mb - GC activity: 2.5%
16:00:25.386 [main] INFO ru.calculator.CalcDemo -- spend msec:34629, sec:34

128mb
15:59:10.829 [main] INFO ru.calculator.CalcDemo -- spend msec:32913, sec:32

256mb
15:51:53.589 [main] INFO ru.calculator.CalcDemo -- spend msec:33577, sec:33

1024mb
15:53:52.919 [main] INFO ru.calculator.CalcDemo -- spend msec:31068, sec:31

2048mb
15:55:02.549 [main] INFO ru.calculator.CalcDemo -- spend msec:29887, sec:29

4096mb
15:56:27.371 [main] INFO ru.calculator.CalcDemo -- spend msec:27159, sec:27

8192mb
15:49:53.328 [main] INFO ru.calculator.CalcDemo -- spend msec:28342, sec:28

AFTER DATA OPTIMIZING
16mb - GC activity: 8% - data with int
16:05:27.446 [main] INFO ru.calculator.CalcDemo -- spend msec:60079, sec:60

16mb - GC activity: 4% - data+summator with int
16:06:48.611 [main] INFO ru.calculator.CalcDemo -- spend msec:27361, sec:27

