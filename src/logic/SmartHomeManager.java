package logic;

import models.Device;
import java.util.*;
import java.util.stream.Stream;

public class SmartHomeManager {
    private Map<String, List<Device>> rooms = new HashMap<>();
    private List<Scenario<?>> scenarios = new ArrayList<>();

    public void addDevice(String room, Device device) {
        if (!rooms.containsKey(room)) rooms.put(room, new ArrayList<>());
        rooms.get(room).add(device);
    }

    public void addScenario(Scenario<?> scenario) {
        scenarios.add(scenario);
    }

    public Device getDeviceById(String id) {
        for (List<Device> list : rooms.values()) {
            for (Device d : list) {
                if (d.getId().equals(id)) return d;
            }
        }
        return null;
    }

    // ТРЕБОВАНИЕ ИЗ PDF: "проходит по всем устройствам... необходимо проверять совместимость типов"
    public void executeAllScenarios() {
        for (List<Device> list : rooms.values()) {
            for (Device d : list) {
                for (Scenario<?> s : scenarios) {
                    try {
                        // Пытаемся применить сценарий к устройству.
                        // Мы приводим s к Scenario<Device>, чтобы передать туда d.
                        ((Scenario<Device>) s).apply(d);
                    } catch (ClassCastException e) {
                        // ПРОВЕРКА СОВМЕСТИМОСТИ ТИПОВ!
                        // Если сценарий был для SmartSocket, а d - это SmartLight,
                        // возникнет ClassCastException. Мы её ловим и просто идем дальше.
                        // Никаких действий не требуется, устройство просто пропускается.
                    }
                }
            }
        }
    }

    public Stream<Device> getAnalyticsStream() {
        return rooms.values().stream().flatMap(List::stream);
    }
}
