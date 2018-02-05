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
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.zeevox.octo.R;
import com.zeevox.octo.wallpaper.OcquariumWallpaperService;

public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences_ocquarium);

        Preference setLiveWallpaper = findPreference("set_live_wallpaper");
        setLiveWallpaper.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
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
        WallpaperManager wpm = WallpaperManager.getInstance(getActivity());
        WallpaperInfo info = wpm.getWallpaperInfo();

        if (info != null && info.getPackageName().equals(getActivity().getPackageName())) {
            setLiveWallpaper.setEnabled(false);
            setLiveWallpaper.setSummary("Ocquarium live wallpaper already set.");
        }
    }
}
