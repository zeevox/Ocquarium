/*
 * Copyright (C) 2020 Timothy "ZeevoX" Langer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zeevox.octo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.zeevox.octo.core.Ocquarium;
import com.zeevox.octo.settings.SettingsActivityV2;

import java.util.Collections;

import androidx.annotation.Nullable;

public class OcquariumActivity extends Activity {

  /* We place the code in onResume() so that the activity is redrawn after visiting settings. */
  @Override
  protected void onResume() {
    super.onResume();
    Ocquarium.start(this, getWindow(), getResources(), true);

    PackageManager packageManager = this.getPackageManager();
    boolean isPieInstalled = false;

    try {
      packageManager.getPackageInfo("com.zeevox.pie", 0);
      isPieInstalled = true;
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    }

    if (!isPieInstalled
        && Math.random() <= 0.02) { // 2% chance of showing info about Pie easter egg app

      AlertDialog.Builder builder = new AlertDialog.Builder(this);

      builder.setMessage(R.string.dialog_pie_info_message).setTitle(R.string.dialog_pie_info_title);

      builder.setPositiveButton(
          R.string.dialog_pie_info_positive,
          new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
              startActivity(
                  new Intent(Intent.ACTION_VIEW)
                      .setData(Uri.parse(getString(R.string.dialog_pie_info_url))));
              dialogInterface.dismiss();
            }
          });

      builder.setNeutralButton(
          R.string.dialog_pie_info_neutral,
          new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
              dialogInterface.dismiss();
            }
          });

      AlertDialog dialog = builder.create();

      dialog.show();
    }
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (savedInstanceState == null && PreferenceManager.getDefaultSharedPreferences(this).getBoolean("show_platlogo", false)) {
      startActivity(new Intent(OcquariumActivity.this, PlatLogoActivity.class));
    }

    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N_MR1) {
      ShortcutManager shortcutManager = getSystemService(ShortcutManager.class);

      Intent settingsShortcut = new Intent(this, SettingsActivityV2.class);
      settingsShortcut.setAction(Intent.ACTION_VIEW);

      ShortcutInfo shortcut =
          new ShortcutInfo.Builder(this, "id1")
              .setShortLabel(getResources().getString(R.string.action_settings))
              .setLongLabel(getResources().getString(R.string.shortcut_settings_long))
              .setDisabledMessage(getResources().getString(R.string.shortcut_settings_disabled))
              .setIcon(Icon.createWithResource(this, R.drawable.ic_shortcut_settings))
              .setIntent(settingsShortcut)
              .build();

      if (shortcutManager != null) {
        shortcutManager.setDynamicShortcuts(Collections.singletonList(shortcut));
      }
    }
  }
}
