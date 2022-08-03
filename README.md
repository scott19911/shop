# Lab Task

Задание 2.

1. Посмотреть, как реализована защита от искажения возвращаемых данных, если во время итерирования содержимое контейнера меняется извне.

1.1. Придумать и реализовать итератор, который будет устойчив к изменению данных извне. (схема copy-on-write). Коллекцию и итератор делать на основе контейнера из задания 1 часть 2. (PS. Это должен быть отдельный класс, старый контейнер должен остаться как он был)

2. Подумать, зачем могут понадобиться read-only(unmodifiable) wrapperы для коллекций.

2.1. Реализовать список (List), состоящий из неизменяемого (unmodifiable) и изменяемого (modifiable) списков. Не забыть написать итератор для его просмотра.

Нельзя копировать данные из этих двух списков
