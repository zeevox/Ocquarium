/*
 * Copyright (C) 2017 Timothy "ZeevoX" Langer
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package com.zeevox.octo.settings;

import android.app.WallpaperInfo;
import android.app.WallpaperManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceScreen;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.provider.Settings;
import android.widget.Toast;

import com.rarepebble.colorpicker.ColorPreference;
import com.zeevox.octo.BuildConfig;
import com.zeevox.octo.FeedbackActivity;
import com.zeevox.octo.R;
import com.zeevox.octo.wallpaper.OcquariumWallpaperService;

import java.util.Objects;

public class SettingsFragment extends PreferenceFragment {

    private boolean wasGradientStartDefault = false;
    private boolean wasGradientEndDefault = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences_ocquarium);

        findPreference("set_live_wallpaper").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(
                        WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
                intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                        new ComponentName(getActivity(), OcquariumWallpaperService.class));
                startActivity(intent);
                return false;
            }
        });

        findPreference("restart_live_wallpaper").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Toast.makeText(getActivity(), "Restarting live wallpaper...", Toast.LENGTH_SHORT).show();
                restartLiveWallpaper();
                return false;
            }
        });

        wasGradientStartDefault = Objects.equals(((ColorPreference) findPreference("gradient_start_color")).getColor(),
                getResources().getColor(R.color.octo_bg_default_start_color));
        wasGradientEndDefault = Objects.equals(((ColorPreference) findPreference("gradient_end_color")).getColor(),
                getResources().getColor(R.color.octo_bg_default_end_color));

        if (wasGradientStartDefault && wasGradientEndDefault) {
            findPreference("reset_background_colors").setEnabled(false);
        } else {
            findPreference("reset_background_colors").setEnabled(true);
        }

        findPreference("gradient_start_color").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                wasGradientStartDefault = Objects.equals(newValue,
                        getResources().getColor(R.color.octo_bg_default_start_color));
                if (wasGradientStartDefault && wasGradientEndDefault) {
                    findPreference("reset_background_colors").setEnabled(false);
                } else {
                    findPreference("reset_background_colors").setEnabled(true);
                }
                return true;
            }
        });

        findPreference("gradient_end_color").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                wasGradientEndDefault = Objects.equals(newValue,
                        getResources().getColor(R.color.octo_bg_default_end_color));
                if (wasGradientStartDefault && wasGradientEndDefault) {
                    findPreference("reset_background_colors").setEnabled(false);
                } else {
                    findPreference("reset_background_colors").setEnabled(true);
                }
                return true;
            }
        });

        findPreference("reset_background_colors").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                ColorPreference startColor = (ColorPreference) findPreference("gradient_start_color");
                startColor.setColor(getResources().getColor(R.color.octo_bg_default_start_color));
                ColorPreference endColor = (ColorPreference) findPreference("gradient_end_color");
                endColor.setColor(getResources().getColor(R.color.octo_bg_default_end_color));
                preference.setEnabled(false);
                return true;
            }
        });

        findPreference("set_screensaver").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                try {
                    startActivity(new Intent(Settings.ACTION_DREAM_SETTINGS));
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getActivity(), "Your device doesn't support setting a screensaver, sorry.", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            ((SwitchPreference) findPreference("platlogo_v2")).setChecked(false);
            findPreference("platlogo_v2").setEnabled(false);
            ((PreferenceScreen) findPreference("category_general")).removePreference(findPreference("platlogo_v2"));
        } else {
            findPreference("platlogo_v2").setEnabled(((SwitchPreference) findPreference("show_platlogo")).isChecked());
            findPreference("show_platlogo").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    findPreference("platlogo_v2").setEnabled(((boolean) newValue));
                    return true;
                }
            });
        }

        findPreference("send_feedback").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                startActivity(new Intent(getActivity(), FeedbackActivity.class));
                return true;
            }
        });

        if (!BuildConfig.DEBUG) {
            PreferenceScreen rootPreferenceScreen = (PreferenceScreen) findPreference("root_preferences_ocquarium");
            PreferenceScreen devOptionsPrefScreen = (PreferenceScreen) findPreference("dev_settings");
            rootPreferenceScreen.removePreference(devOptionsPrefScreen);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        restartLiveWallpaper();
    }

    @Override
    public void onResume() {
        super.onResume();
        Preference setLiveWallpaper = findPreference("set_live_wallpaper");
        Preference restartLiveWallpaper = findPreference("restart_live_wallpaper");
        if (isLiveWallpaperSet()) {
            setLiveWallpaper.setEnabled(false);
            setLiveWallpaper.setSummary("Ocquarium live wallpaper already set.");
            restartLiveWallpaper.setEnabled(true);
        }
    }

    private boolean isLiveWallpaperSet() {
        WallpaperInfo info = WallpaperManager.getInstance(getActivity()).getWallpaperInfo();
        return info != null && info.getPackageName().equals(getActivity().getPackageName());
    }

    private void restartLiveWallpaper() {
        if (isLiveWallpaperSet()) {
            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();
            editor.putBoolean("restart_live_wallpaper", true);
            editor.apply();
        }
    }
}
