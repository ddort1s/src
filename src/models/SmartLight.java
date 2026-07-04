package models;

// Наследуемся от Device. Получаем все его поля и методы бесплатно.
public class SmartLight extends Device {
    // Уникальные поля только для Лампы. Доступ только через геттер/сеттер (инкапсуляция).
    private int brightness;
    private String color;

    public SmartLight(String id, String name, double power, int brightness, String color) {
        // super вызывает конструктор родителя, чтобы заполнить общие поля (id, name, power)
        super(id, name, power);
        this.brightness = brightness;
        this.color = color;
    }

    public int getBrightness() { return brightness; }
    public void setBrightness(int brightness) { this.brightness = brightness; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    // Переопределяем абстрактный метод. Используем простое сложение строк (+).
    @Override
    public String getDetails() {
        return "Лампа " + name + " | Яркость: " + brightness + " | Цвет: " + color + " | Потребление: " + powerConsumption;
    }
}
