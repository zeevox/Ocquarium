/*
 * Copyright (C) 2018 Timothy "ZeevoX" Langer
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package com.zeevox.octo.settings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.zeevox.octo.R;

public class SettingsActivity extends Activity {

    public static boolean ENABLE_V2_SETTINGS = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (ENABLE_V2_SETTINGS || !PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean("dev_settings_v2", false)) {
            this.startActivity(new Intent(this, SettingsActivityV2.class));
            finish();
        }

        super.onCreate(savedInstanceState);

        // Inflate layout
        setContentView(R.layout.activity_screensaver_settings);

        // Enable back button in Toolbar
        try {
            //noinspection ConstantConditions
            getActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }
    }

    // Handle clicking on the back button
    @Override
    public boolean onNavigateUp() {
        finish();
        return true;
    }
}
