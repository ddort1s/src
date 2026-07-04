package models;

public class SmartThermostat extends Device {
    private double targetTemperature;

    public SmartThermostat(String id, String name, double power, double targetTemperature) {
        super(id, name, power);
        this.targetTemperature = targetTemperature;
    }

    public double getTargetTemperature() { return targetTemperature; }
    public void setTargetTemperature(double targetTemperature) { this.targetTemperature = targetTemperature; }

    @Override
    public String getDetails() {
        return "Термостат " + name + " | Цель: " + targetTemperature + "C | Потребление: " + powerConsumption;
    }
}
