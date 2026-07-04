package models;

import interfaces.Controllable;

// Абстрактный класс - это заготовка. Мы не можем создать просто "Устройство", только конкретное (Лампу, Розетку).
// implements Controllable означает, что мы обещаем реализовать методы включения/выключения.
public abstract class Device implements Controllable {

    // Поля protected доступны этому классу и всем его наследникам (детям).
    protected String id;
    protected String name;
    protected boolean status;           // true = включено, false = выключено
    protected double powerConsumption;  // Потребление в Ваттах

    // Конструктор задает начальные значения. Статус по умолчанию false (выключено).
    public Device(String id, String name, double powerConsumption) {
        this.id = id;
        this.name = name;
        this.powerConsumption = powerConsumption;
        this.status = false;
    }

    // Реализация методов интерфейса. Пишем здесь, чтобы не дублировать в каждом наследнике.
    @Override
    public void turnOn() {
        this.status = true;
    }

    @Override
    public void turnOff() {
        this.status = false;
    }

    @Override
    public boolean isOn() {
        return this.status;
    }

    // Геттеры и сеттеры для доступа к полям
    public String getId() { return id; }
    public String getName() { return name; }
    public double getPowerConsumption() { return powerConsumption; }

    public void setPowerConsumption(double powerConsumption) {
        this.powerConsumption = powerConsumption;
    }

    // Абстрактный метод. Наследники ОБЯЗАНЫ написать свою версию этого метода.
    public abstract String getDetails();
}
