//package main;
//
//import logic.SmartHomeManager;
//import logic.Scenario;
//import models.Device;
//import models.SmartLight;
//import models.SmartSocket;
//import models.SmartThermostat;
//
//import java.util.Comparator;
//import java.util.Scanner;
//
//public class Main {
//    static Scanner sc = new Scanner(System.in);
//    static SmartHomeManager manager = new SmartHomeManager();
//
//    public static void main(String[] args) {
//        while (true) {
//            System.out.println("\n--- МЕНЮ УМНЫЙ ДОМ ---");
//            System.out.println("1. Добавить устройство");
//            System.out.println("2. Включить/Выключить");
//            System.out.println("3. Базовый сценарий (Сброс фантомов)");
//            System.out.println("4. Сценарий Варианта 1");
//            System.out.println("5. Запустить все сценарии");
//            System.out.println("6. Аналитика (ТОП-3)");
//            System.out.println("7. Выход");
//            System.out.print("Выбор: ");
//
//            String choice = sc.nextLine().trim();
//
//            // Классический switch (работает в ANY версии Java)
//            switch (choice) {
//                case "1":
//                    addDevice();
//                    break;
//                case "2":
//                    toggleDevice();
//                    break;
//                case "3":
//                    addBaseScenario();
//                    break;
//                case "4":
//                    addVariantScenario();
//                    break;
//                case "5":
//                    manager.executeAllScenarios();
//                    System.out.println("Все сценарии отработали.");
//                    break;
//                case "6":
//                    analytics();
//                    break;
//                case "7":
//                    System.out.println("Выход из программы.");
//                    return;
//                default:
//                    System.out.println("Неверный ввод. Введите цифру от 1 до 7.");
//                    break;
//            }
//        }
//    }
//
//    static void addDevice() {
//        System.out.println("Тип: 1-Розетка, 2-Лампа, 3-Термостат");
//        String type = sc.nextLine().trim();
//
//        if (!type.equals("1") && !type.equals("2") && !type.equals("3")) {
//            System.out.println("Неверный тип.");
//            return;
//        }
//
//        System.out.print("ID: ");
//        String id = sc.nextLine().trim();
//        System.out.print("Имя: ");
//        String name = sc.nextLine().trim();
//
//        System.out.print("Потребление (Вт): ");
//        double power = 0;
//        try {
//            // ЗАЩИТА ОТ ЗАПЯТЫХ: заменяем "," на "." перед парсингом
//            String powerStr = sc.nextLine().trim().replace(",", ".");
//            power = Double.parseDouble(powerStr);
//        } catch (Exception e) {
//            System.out.println("Нужно ввести число.");
//            return;
//        }
//
//        System.out.print("Комната: ");
//        String room = sc.nextLine().trim();
//
//
//        if (type.equals("1")) {
//            System.out.print("Нагрузка прибора (Вт): ");
//            try {
//                String loadStr = sc.nextLine().trim().replace(",", ".");
//                double load = Double.parseDouble(loadStr);
//                SmartSocket s = new SmartSocket(id, name, power, load);
//                manager.addDevice(room, s);
//                System.out.println("Добавлено: " + s.getDetails());
//            } catch (Exception e) {
//                System.out.println("Ошибка ввода нагрузки.");
//            }
//        }
//        else if (type.equals("2")) {
//            System.out.print("Яркость (0-100): ");
//            try {
//                int bright = Integer.parseInt(sc.nextLine().trim());
//                System.out.print("Цвет: ");
//                String color = sc.nextLine().trim();
//                SmartLight l = new SmartLight(id, name, power, bright, color);
//                manager.addDevice(room, l);
//                System.out.println("Добавлено: " + l.getDetails());
//            } catch (Exception e) {
//                System.out.println("Ошибка ввода яркости.");
//            }
//        }
//        else {
//            System.out.print("Целевая температура: ");
//            try {
//                String tempStr = sc.nextLine().trim().replace(",", ".");
//                double temp = Double.parseDouble(tempStr);
//                SmartThermostat t = new SmartThermostat(id, name, power, temp);
//                manager.addDevice(room, t);
//                System.out.println("Добавлено: " + t.getDetails());
//            } catch (Exception e) {
//                System.out.println("Ошибка ввода температуры.");
//            }
//        }
//    }
//
//    static void toggleDevice() {
//        System.out.print("ID устройства: ");
//        String id = sc.nextLine().trim();
//        Device d = manager.getDeviceById(id);
//
//        if (d == null) {
//            System.out.println("Устройство не найдено.");
//            return;
//        }
//
//        if (d.isOn()) {
//            d.turnOff();
//        } else {
//            d.turnOn();
//        }
//        System.out.println("Статус изменен. Сейчас включено: " + d.isOn());
//    }
//
//    static void addBaseScenario() {
//        // ЯВНО указываем <Device>, чтобы компилятор не тупил
//        manager.addScenario(new Scenario<Device>(
//                "Сброс фантомного потребления",
//                (Device d) -> !d.isOn() && d.getPowerConsumption() > 0,
//                (Device d) -> d.setPowerConsumption(0.0)
//        ));
//        System.out.println("Базовый сценарий добавлен.");
//    }
//
//    static void addVariantScenario() {
//        // ЯВНО указываем <SmartSocket>, иначе будет ошибка компиляции
//        manager.addScenario(new Scenario<SmartSocket>(
//                "Энергосбережение",
//                (SmartSocket s) -> s.isOn() && s.getCurrentLoad() > 2000,
//                (SmartSocket s) -> s.turnOff()
//        ));
//        System.out.println("Сценарий Варианта 1 добавлен.");
//    }
//
//    static void analytics() {
//        System.out.println("--- ТОП-3 по потреблению ---");
//        manager.getAnalyticsStream()
//                .sorted(Comparator.comparingDouble(Device::getPowerConsumption).reversed())
//                .limit(3)
//                .forEach(d -> System.out.println(d.getName() + ": " + d.getPowerConsumption() + " Вт"));
//    }
//}
//
//
//## Готовые методы аналитики
//
//---
//
//        ### 1. ТОП-3 по убыванию (наибольшее потребление)
//
//
//static void analytics() {
//    List<Transport> top3 = manager.getAnalyticsStream()
//            .sorted(Comparator.comparingDouble(Transport::getBatteryLevel).reversed())
//            .limit(3)
//            .collect(Collectors.toList());
//
//    System.out.println("=== ТОП-3 наибольший заряд ===");
//    for (Transport t : top3) {
//        System.out.println(t.getName() + " | " + t.getBatteryLevel() + "%");
//    }
//}
//
//
//---
//
//        ### 2. ТОП-3 по возрастанию (наименьший заряд)
//
//
//static void analytics() {
//    List<Transport> top3 = manager.getAnalyticsStream()
//            .sorted(Comparator.comparingDouble(Transport::getBatteryLevel))
//            .limit(3)
//            .collect(Collectors.toList());
//
//    System.out.println("=== ТОП-3 наименьший заряд ===");
//    for (Transport t : top3) {
//        System.out.println(t.getName() + " | " + t.getBatteryLevel() + "%");
//    }
//}
//
//
//---
//
//        ### 3. Сумма значений (общий заряд на базе)
//
//
//static void analytics() {
//    double total = manager.getAnalyticsStream()
//            .filter(t -> !t.isInTransit())
//            .mapToDouble(Transport::getBatteryLevel)
//            .sum();
//
//    System.out.println("Общий заряд на базе: " + total + "%");
//}
//
//
//---
//
//        ### 4. Список зон где есть транспорт в пути
//
//
//static void analytics() {
//    List<String> result = manager.getHubs().entrySet().stream()
//            .filter(entry -> entry.getValue().stream()
//                    .anyMatch(t -> t.isInTransit()))
//            .map(entry -> entry.getKey())
//            .collect(Collectors.toList());
//
//    System.out.println("Хабы с транспортом в пути: " + result);
//}
//
//
//Для этого нужен геттер в LogisticManager:
//
//public Map<String, List<Transport>> getHubs() { return habs; }
//
//
//---
//
//        ### 5. Группировка по статусу
//
//
//static void analytics() {
//    Map<Boolean, List<Transport>> grouped = manager.getAnalyticsStream()
//            .collect(Collectors.groupingBy(Transport::isInTransit));
//
//    System.out.println("В пути: " + grouped.get(true));
//    System.out.println("На базе: " + grouped.get(false));
//}
//
//
//---
//
//        ### 6. Минимум через Optional
//
//
//static void analytics() {
//    Optional<Transport> min = manager.getAnalyticsStream()
//            .filter(t -> t.isInTransit())
//            .min(Comparator.comparingDouble(Transport::getBatteryLevel));
//
//    if (min.isPresent()) {
//        System.out.println("Минимальный заряд в пути: "
//                + min.get().getName() + " | " + min.get().getBatteryLevel() + "%");
//    } else {
//        System.out.println("Нет транспорта в пути.");
//    }
//}
//
//
//---
//
//        ### 7. Количество по классам
//
//
//static void analytics() {
//    Map<String, Long> countByClass = manager.getAnalyticsStream()
//            .collect(Collectors.groupingBy(
//                    t -> t.getClass().getSimpleName(),
//                    Collectors.counting()
//            ));
//
//    System.out.println("Количество по классам: " + countByClass);
//}
//
//
//---
//
//        ### 8. Строка через запятую
//
//
//static void analytics() {
//    String result = manager.getAnalyticsStream()
//            .filter(t -> !t.isInTransit())
//            .map(Transport::getName)
//            .collect(Collectors.joining(", "));
//
//    System.out.println("Транспорт на базе: " + result);
//}
//
//
//---
//
//        ### 9. anyMatch — проверка boolean
//
//
//static void analytics() {
//    boolean hasLowBattery = manager.getAnalyticsStream()
//            .anyMatch(t -> t.getBatteryLevel() < 5.0);
//
//    if (hasLowBattery) {
//        System.out.println("Есть транспорт с зарядом ниже 5%!");
//    } else {
//        System.out.println("Весь транспорт заряжен нормально.");
//    }
//}
//
//
//---
//
//        ### Шпаргалка — что импортировать
//
//
//import java.util.Comparator;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//import java.util.stream.Collectors;
