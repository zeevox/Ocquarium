package com.zeevox.octo.settings.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;

import com.rarepebble.colorpicker.ColorPreference;
import com.zeevox.octo.R;
import com.zeevox.octo.settings.BasePreferenceFragment;
import com.zeevox.octo.util.ColorUtils;

import java.util.Objects;
import java.util.Random;

public class BackgroundPreferenceFragment extends BasePreferenceFragment {

    private boolean wasGradientStartDefault = false;
    private boolean wasGradientEndDefault = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_background);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        findPreference("gradient_start_color")
                .setOnPreferenceChangeListener(
                        new Preference.OnPreferenceChangeListener() {
                            @Override
                            public boolean onPreferenceChange(Preference preference, Object newValue) {
                                wasGradientStartDefault =
                                        Objects.equals(
                                                newValue, getResources().getColor(R.color.octo_bg_default_start_color));
                                if (wasGradientStartDefault && wasGradientEndDefault) {
                                    findPreference("reset_background_colors").setEnabled(false);
                                } else {
                                    findPreference("reset_background_colors").setEnabled(true);
                                }
                                return true;
                            }
                        });

        findPreference("gradient_end_color")
                .setOnPreferenceChangeListener(
                        new Preference.OnPreferenceChangeListener() {
                            @Override
                            public boolean onPreferenceChange(Preference preference, Object newValue) {
                                wasGradientEndDefault =
                                        Objects.equals(
                                                newValue, getResources().getColor(R.color.octo_bg_default_end_color));
                                if (wasGradientStartDefault && wasGradientEndDefault) {
                                    findPreference("reset_background_colors").setEnabled(false);
                                } else {
                                    findPreference("reset_background_colors").setEnabled(true);
                                }
                                return true;
                            }
                        });

        findPreference("swap_background_colors")
                .setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                    @Override
                    public boolean onPreferenceClick(Preference preference) {
                        ColorPreference startColorPreference = findPreference("gradient_start_color");
                        ColorPreference endColorPreference = findPreference("gradient_end_color");
                        int startColor = startColorPreference.getColor();
                        int endColor = endColorPreference.getColor();
                        startColorPreference.setColor(endColor);
                        endColorPreference.setColor(startColor);
                        return true;
                    }
                });

        findPreference("reset_background_colors")
                .setOnPreferenceClickListener(
                        new Preference.OnPreferenceClickListener() {
                            @Override
                            public boolean onPreferenceClick(Preference preference) {
                                ColorPreference startColor = findPreference("gradient_start_color");
                                startColor.setColor(getResources().getColor(R.color.octo_bg_default_start_color));
                                ColorPreference endColor = findPreference("gradient_end_color");
                                endColor.setColor(getResources().getColor(R.color.octo_bg_default_end_color));
                                preference.setEnabled(false);
                                return true;
                            }
                        });

        findPreference("random_ui_gradients")
                .setOnPreferenceClickListener(
                        new Preference.OnPreferenceClickListener() {
                            @Override
                            public boolean onPreferenceClick(Preference preference) {
                                final String[][] randomGradientDetails = {
                                        ColorUtils.getGradientDetails(getRandomInt(ColorUtils.gradients.size()))
                                };

                                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                                builder
                                        .setTitle(R.string.prefs_uigradients_dialog_title)
                                        .setMessage(
                                                String.format(
                                                        getResources()
                                                                .getString(R.string.prefs_uigradients_dialog_description),
                                                        ColorUtils.getGradientName(randomGradientDetails[0])))
                                        .setNegativeButton(
                                                android.R.string.cancel,
                                                new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        dialogInterface.dismiss();
                                                    }
                                                })
                                        .setPositiveButton(
                                                R.string.action_apply,
                                                new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        ColorPreference startColor =
                                                                (ColorPreference) findPreference("gradient_start_color");
                                                        startColor.setColor(
                                                                ColorUtils.getGradientStartColor(randomGradientDetails[0]));
                                                        ColorPreference endColor =
                                                                (ColorPreference) findPreference("gradient_end_color");
                                                        endColor.setColor(
                                                                ColorUtils.getGradientEndColor(randomGradientDetails[0]));
                                                    }
                                                });

                                builder.create().show();
                                return true;
                            }

                            private int getRandomInt(int max) {
                                return new Random().nextInt(max);
                            }
                        });
    }

    @Override
    public void onDisplayPreferenceDialog(Preference preference) {
        if (preference instanceof ColorPreference) {
            ((ColorPreference) preference).showDialog(this, 0);
        } else super.onDisplayPreferenceDialog(preference);
    }
}
