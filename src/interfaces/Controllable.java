package interfaces;

// Интерфейс - это контракт. Любое устройство ОБЯЗАНО уметь включаться, выключаться и отвечать на вопрос "ты включено?".
public interface Controllable {
    void turnOn();
    void turnOff();
    boolean isOn();
}
