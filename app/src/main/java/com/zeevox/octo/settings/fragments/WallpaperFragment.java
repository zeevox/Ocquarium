package com.zeevox.octo.settings.fragments;

import android.app.WallpaperInfo;
import android.app.WallpaperManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceManager;

import com.zeevox.octo.R;
import com.zeevox.octo.settings.BasePreferenceFragment;
import com.zeevox.octo.wallpaper.OcquariumWallpaperService;

public class WallpaperFragment extends BasePreferenceFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_wallpaper);

        // Remove live wallpaper preferences if the device doesn't support them
        PackageManager pm = getActivity().getPackageManager();
        if (!pm.hasSystemFeature(PackageManager.FEATURE_LIVE_WALLPAPER)) {
            getPreferenceScreen().removePreference(findPreference("set_live_wallpaper"));
            getPreferenceScreen().removePreference(findPreference("restart_live_wallpaper"));
        } else {
            findPreference("set_live_wallpaper")
                    .setOnPreferenceClickListener(
                            new Preference.OnPreferenceClickListener() {
                                @Override
                                public boolean onPreferenceClick(Preference preference) {
                                    Intent intent = new Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
                                    intent.putExtra(
                                            WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                                            new ComponentName(getActivity(), OcquariumWallpaperService.class));
                                    startActivity(intent);
                                    return false;
                                }
                            });

            findPreference("restart_live_wallpaper")
                    .setOnPreferenceClickListener(
                            new Preference.OnPreferenceClickListener() {
                                @Override
                                public boolean onPreferenceClick(Preference preference) {
                                    Toast.makeText(
                                            getActivity(), R.string.prefs_restarting_wallpaper, Toast.LENGTH_SHORT)
                                            .show();
                                    restartLiveWallpaper();
                                    return false;
                                }
                            });
        }

        findPreference("set_screensaver")
                .setOnPreferenceClickListener(
                        new Preference.OnPreferenceClickListener() {
                            @Override
                            public boolean onPreferenceClick(Preference preference) {
                                try {
                                    startActivity(new Intent(Settings.ACTION_DREAM_SETTINGS));
                                } catch (ActivityNotFoundException e) {
                                    Toast.makeText(
                                            getActivity(), R.string.prefs_screensaver_error, Toast.LENGTH_SHORT)
                                            .show();
                                }
                                return false;
                            }
                        });
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
            setLiveWallpaper.setSummary(R.string.prefs_wallpaper_disabled_desc);
            restartLiveWallpaper.setEnabled(true);
        }
    }

    private boolean isLiveWallpaperSet() {
        WallpaperInfo info = WallpaperManager.getInstance(getActivity()).getWallpaperInfo();
        return info != null && info.getPackageName().equals(getActivity().getPackageName());
    }

    private void restartLiveWallpaper() {
        if (isLiveWallpaperSet()) {
            SharedPreferences.Editor editor =
                    PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();
            editor.putBoolean("restart_live_wallpaper", true);
            editor.apply();
        }
    }
}
