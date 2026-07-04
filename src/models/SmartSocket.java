package models;

public class SmartSocket extends Device {
    private double currentLoad; // Нагрузка подключенного прибора

    public SmartSocket(String id, String name, double power, double currentLoad) {
        super(id, name, power);
        this.currentLoad = currentLoad;
    }

    public double getCurrentLoad() { return currentLoad; }
    public void setCurrentLoad(double currentLoad) { this.currentLoad = currentLoad; }

    @Override
    public String getDetails() {
        return "Розетка " + name + " | Нагрузка: " + currentLoad + "W | Потребление: " + powerConsumption;
    }
}
