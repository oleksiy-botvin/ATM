package ua.edu.ztu.student.zipz221_boyu.component_provider.components;

public interface Preferences {
    int getATMBalance();
    void setATMBalance(int value);

    int getAttemptsEnterPIN();
    int incrementAttemptsEnterPIN();
    void clearAttemptsEnterPIN();
}
