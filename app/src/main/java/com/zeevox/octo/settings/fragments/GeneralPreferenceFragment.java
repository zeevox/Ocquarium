package com.zeevox.octo.settings.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceScreen;
import androidx.preference.SwitchPreferenceCompat;

import com.zeevox.octo.R;
import com.zeevox.octo.settings.BasePreferenceFragment;

public class GeneralPreferenceFragment extends BasePreferenceFragment {

    /**
     * A preference value change listener that updates the preference's summary to reflect its new
     * value.
     */
    private static final Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener =
            new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object value) {
                    String stringValue = value.toString();

                    if (preference instanceof ListPreference) {
                        // For list preferences, look up the correct display value in
                        // the preference's 'entries' list.
                        ListPreference listPreference = (ListPreference) preference;
                        int index = listPreference.findIndexOfValue(stringValue);

                        // Set the summary to reflect the new value.
                        preference.setSummary(index >= 0 ? listPreference.getEntries()[index] : null);

                    } else {
                        // For all other preferences, set the summary to the value's
                        // simple string representation.
                        preference.setSummary(stringValue);
                    }
                    return true;
                }
            };

    /**
     * Binds a preference's summary to its value. More specifically, when the preference's value is
     * changed, its summary (line of text below the preference title) is updated to reflect the value.
     * The summary is also immediately updated upon calling this method. The exact display format is
     * dependent on the type of preference.
     *
     * @see #sBindPreferenceSummaryToValueListener
     */
    private static void bindPreferenceSummaryToValue(Preference preference) {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

        // Trigger the listener immediately with the preference's
        // current value.
        sBindPreferenceSummaryToValueListener.onPreferenceChange(
                preference,
                PreferenceManager.getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_general);

        // Bind the summaries of EditText/List/Dialog/Ringtone preferences
        // to their values. When their values change, their summaries are
        // updated to reflect the new value, per the Android Design
        // guidelines.
        bindPreferenceSummaryToValue(findPreference("octo_fade_in_duration"));

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            ((SwitchPreferenceCompat) findPreference("platlogo_v2")).setChecked(false);
            findPreference("platlogo_v2").setEnabled(false);
            ((PreferenceScreen) findPreference("category_general"))
                    .removePreference(findPreference("platlogo_v2"));
        } else {
            findPreference("platlogo_v2")
                    .setEnabled(((SwitchPreferenceCompat) findPreference("show_platlogo")).isChecked());
            findPreference("show_platlogo")
                    .setOnPreferenceChangeListener(
                            new Preference.OnPreferenceChangeListener() {
                                @Override
                                public boolean onPreferenceChange(Preference preference, Object newValue) {
                                    findPreference("platlogo_v2").setEnabled(((boolean) newValue));
                                    return true;
                                }
                            });
        }
    }
}
