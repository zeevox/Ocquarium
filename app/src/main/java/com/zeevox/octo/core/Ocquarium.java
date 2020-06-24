/*
 * Copyright (C) 2017 The Android Open Source Project
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

package com.zeevox.octo.core;

import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.GradientDrawable;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import com.zeevox.octo.R;
import com.zeevox.octo.settings.SettingsActivityV2;
import com.zeevox.octo.util.ColorUtils;

public class Ocquarium {

  public static void start(
      @NonNull final ContextWrapper context, @NonNull Window window, @NonNull Resources resources) {
    start(context, window, resources, false);
  }

  public static void start(
      @NonNull final ContextWrapper context,
      @NonNull Window window,
      @NonNull Resources resources,
      boolean showSettingsButton) {
    // Initialize preferences
    final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

    // Get the display density
    final float dp = resources.getDisplayMetrics().density;

    // Set the background to be the gradient with user defined colors
    // See bgGradient(ContextWrapper, Resources) for more info about this
    window.setBackgroundDrawable(bgGradient(context, resources));

    // Hide the navigation bar if set by the user
    if (!preferences.getBoolean("show_navigation_bar", true)) {
      View decorView = window.getDecorView();
      int uiOptions =
          View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
              | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
              | View.SYSTEM_UI_FLAG_FULLSCREEN;
      decorView.setSystemUiVisibility(uiOptions);
    }

    FrameLayout bg = new FrameLayout(context);
    window.setContentView(bg);

    bg.setAlpha(0f);
    bg.animate()
        .setStartDelay(500)
        .setDuration(
            Integer.parseInt(
                preferences.getString(
                    "octo_fade_in_duration", resources.getString(R.string.anim_even_longer))))
        .alpha(1f)
        .start();

    ImageView mImageView = new ImageView(context);
    bg.addView(
        mImageView,
        new FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

    /* START Settings button */
    if (showSettingsButton) {
      ImageButton mImageButton = new ImageButton(context);
      mImageButton =
          (ImageButton)
              LayoutInflater.from(context).inflate(R.layout.ocquarium_settings_button, bg, false);
      // Set a transparent icon if user set in preferences
      if (preferences.getBoolean("transparent_settings_icon", false)) {
        mImageButton.setAlpha(0f);
        // If it's a light background make sure the icon is contrasting
      } else if (ColorUtils.isColorLight(
          preferences.getInt(
              "gradient_start_color", resources.getColor(R.color.octo_bg_default_start_color)))) {
        mImageButton.setImageDrawable(resources.getDrawable(R.drawable.ic_settings_dark));
      }
      mImageButton.setOnClickListener(
          new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              context.startActivity(new Intent(context, SettingsActivityV2.class));
            }
          });
      bg.addView(
          mImageButton,
          new FrameLayout.LayoutParams(
              ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }
    /* END Settings button */

    // float octoMinSize = Float.parseFloat(preferences.getString("octopus_min_size", "40"));
    // float octoMaxSize = Float.parseFloat(preferences.getString("octopus_max_size", "180"));
    final OctopusDrawable octo = new OctopusDrawable(context);
    // octo.setSizePx((int) (OctopusDrawable.randfrange(octoMinSize, octoMaxSize) * dp));
    int averageOctopusSize = Integer.parseInt(preferences.getString("octopus_average_size", "110"));
    octo.setSizePx(
        (int) OctopusDrawable.randfrange(averageOctopusSize - 70, averageOctopusSize + 70)
            * (int) dp);
    mImageView.setImageDrawable(octo);
    octo.startDrift();

    mImageView.setOnTouchListener(
        new View.OnTouchListener() {
          boolean touching;

          @Override
          public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getActionMasked()) {
              case MotionEvent.ACTION_DOWN:
                if (octo.hitTest(motionEvent.getX(), motionEvent.getY())) {
                  touching = true;
                  octo.stopDrift();
                }
                break;
              case MotionEvent.ACTION_MOVE:
                if (touching) {
                  octo.moveTo(motionEvent.getX(), motionEvent.getY());
                }
                break;
              case MotionEvent.ACTION_UP:
              case MotionEvent.ACTION_CANCEL:
                touching = false;
                octo.startDrift();
                break;
              default:
                break;
            }
            return true;
          }
        });
  }

  public static GradientDrawable bgGradient(ContextWrapper context, Resources resources) {
    // Initialize preferences
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
    // Recreate octo_bg.xml programmatically
    GradientDrawable backgroundGradient = new GradientDrawable();
    // Set the background colors / fetch them from the preferences menu
    backgroundGradient.setColors(
        new int[] {
          preferences.getInt(
              "gradient_start_color", resources.getColor(R.color.octo_bg_default_start_color)),
          preferences.getInt(
              "gradient_end_color", resources.getColor(R.color.octo_bg_default_end_color))
        });
    // Linear gradient
    backgroundGradient.setGradientType(GradientDrawable.LINEAR_GRADIENT);
    // Set the gradient angle to -90 (same as 270)
    backgroundGradient.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
    // Return the final result
    return backgroundGradient;
  }
}
