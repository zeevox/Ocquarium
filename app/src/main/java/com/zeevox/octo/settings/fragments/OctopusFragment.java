package com.zeevox.octo.settings.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;

import com.zeevox.octo.R;
import com.zeevox.octo.settings.BasePreferenceFragment;

public class OctopusFragment extends BasePreferenceFragment implements PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_octopus);

        findPreference("experimental_options_expand")
                .setOnPreferenceClickListener(
                        new Preference.OnPreferenceClickListener() {
                            @Override
                            public boolean onPreferenceClick(Preference preference) {
                                PreferenceScreen prefOctopus = (PreferenceScreen) findPreference("pref_octopus");
                                // Hide the option to expand experimental options
                                prefOctopus.removePreference(preference);
                                // Add the experimental options
                                addPreferencesFromResource(R.xml.pref_experimental);
                                return true;
                            }
                        });
    }

    @Override
    public boolean onPreferenceStartFragment(PreferenceFragmentCompat caller, Preference pref) {
        if (pref.getFragment().equals(com.zeevox.octo.settings.fragments.OctopusFragmentV2.class.getName())) {
            Fragment fragment = Fragment.instantiate(caller.getActivity(), pref.getFragment(), pref.getExtras());
            fragment.setTargetFragment(caller, 0);
            caller.getActivity().getSupportFragmentManager().beginTransaction().replace(android.R.id.content, fragment).addToBackStack(null).commit();
            return true;
        }
        return false;
    }
}
