package ua.edu.ztu.student.zipz221_boyu.data.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import java.util.concurrent.atomic.AtomicInteger;

import ua.edu.ztu.student.zipz221_boyu.component_provider.components.Preferences;

public class PreferencesImpl implements Preferences {

    private final SharedPreferences preferences;
    private AtomicInteger attemptsEnterPIN;

    public PreferencesImpl(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    public int getATMBalance() {
        return preferences.getInt("atm_balance", 0);
    }

    @Override
    public void setATMBalance(int value) {
        preferences.edit().putInt("atm_balance", value).apply();
    }

    @Override
    public int getAttemptsEnterPIN() {
        if (attemptsEnterPIN == null) attemptsEnterPIN = new AtomicInteger(0);
        return attemptsEnterPIN.get();
    }

    @Override
    public int incrementAttemptsEnterPIN() {
        if (attemptsEnterPIN == null) attemptsEnterPIN = new AtomicInteger(0);
       return attemptsEnterPIN.incrementAndGet();
    }

    @Override
    public void clearAttemptsEnterPIN() {
        if (attemptsEnterPIN != null) attemptsEnterPIN.set(0);
    }
}
