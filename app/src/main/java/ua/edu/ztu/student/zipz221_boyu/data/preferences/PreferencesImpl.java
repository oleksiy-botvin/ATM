package ua.edu.ztu.student.zipz221_boyu.data.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import ua.edu.ztu.student.zipz221_boyu.component_provider.components.Preferences;

public class PreferencesImpl implements Preferences {

    private final SharedPreferences preferences;

    public PreferencesImpl(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }
}
