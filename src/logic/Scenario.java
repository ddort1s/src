package logic;

import models.Device;
import java.util.function.Consumer;
import java.util.function.Predicate;

// Обобщенный класс, строго как в задании
public class Scenario<T extends Device> {

    // Только 3 свойства, как написано в PDF
    private String name;
    private Predicate<T> condition;
    private Consumer<T> action;

    // Конструктор принимает 3 параметра
    public Scenario(String name, Predicate<T> condition, Consumer<T> action) {
        this.name = name;
        this.condition = condition;
        this.action = action;
    }

    // Метод строго как в PDF: public void apply(T device)
    public void apply(T device) {
        // Логика из PDF: проверяем условие и выполняем действие
        if (condition.test(device)) {
            action.accept(device);
        }
    }

    // Геттер для имени (нужен для красивого вывода)
    public String getName() { return name; }
}
