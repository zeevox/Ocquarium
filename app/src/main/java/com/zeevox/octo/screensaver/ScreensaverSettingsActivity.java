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

package com.zeevox.octo.screensaver;

import android.app.Activity;
import android.os.Bundle;
import com.zeevox.octo.R;

public class ScreensaverSettingsActivity extends Activity {

  @Override
  public void onCreate(Bundle savedInstanceState) {
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
