/*
 * Copyright (C) 2017 The Android Open Source Project
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

package com.zeevox.octo.bhs;

import android.content.ContextWrapper;
import android.support.annotation.NonNull;
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

import com.zeevox.octo.R;
import com.zeevox.octo.settings.SettingsActivity;
import com.zeevox.octo.util.ColorUtils;

public class Ocquarium {

    public static void start(@NonNull final ContextWrapper context, @NonNull Window window, @NonNull Resources resources) {
        start(context, window, resources, false);
    }

    public static void start(@NonNull final ContextWrapper context, @NonNull Window window, @NonNull Resources resources, boolean showSettingsButton) {
        // Initialize preferences
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        final float dp = resources.getDisplayMetrics().density;

        // Recreate octo_bg.xml programmatically
        GradientDrawable backgroundGradient = new GradientDrawable();
        // Set the background colors / fetch them from the preferences menu
        backgroundGradient.setColors(new int[]{
                preferences.getInt("gradient_start_color", resources.getColor(R.color.octo_bg_default_start_color)),
                preferences.getInt("gradient_end_color", resources.getColor(R.color.octo_bg_default_end_color))
        });
        // Linear gradient
        backgroundGradient.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        // Set the gradient angle to -90 (same as 270)
        backgroundGradient.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
        // Set the background to the new drawable we've created
        window.setBackgroundDrawable(backgroundGradient);

        FrameLayout bg = new FrameLayout(context);
        //FrameLayout settings_button = (FrameLayout) LayoutInflater.from(getApplicationContext()).inflate(R.layout.ocquarium_settings_button, bg);
        //bg.addView(LayoutInflater.from(getApplicationContext()).inflate(R.layout.ocquarium_settings_button, null));
        window.setContentView(bg);

        bg.setAlpha(0f);
        bg.animate().setStartDelay(500).setDuration(Integer.valueOf(preferences.getString(
                "octo_fade_in_duration", resources.getString(R.string.anim_even_longer)))).alpha(1f).start();

        ImageView mImageView = new ImageView(context);
        bg.addView(mImageView, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        /* START Settings button */
        if (showSettingsButton) {
            ImageButton mImageButton = new ImageButton(context);
            mImageButton = (ImageButton) LayoutInflater.from(context).inflate(R.layout.ocquarium_settings_button, bg, false);
            // If it's a light background make sure the icon is contrasting
            if (ColorUtils.isColorLight(preferences.getInt("gradient_start_color", resources.getColor(R.color.octo_bg_default_start_color)))) {
                mImageButton.setImageDrawable(resources.getDrawable(R.drawable.ic_settings_dark));
            }
            mImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context, SettingsActivity.class));
                }
            });
            bg.addView(mImageButton, new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
        /* END Settings button */

        float octoMinSize = Float.parseFloat(preferences.getString("octopus_min_size", "40"));
        float octoMaxSize = Float.parseFloat(preferences.getString("octopus_max_size", "180"));
        final OctopusDrawable octo = new OctopusDrawable(context);
        octo.setSizePx((int) (OctopusDrawable.randfrange(octoMinSize, octoMaxSize) * dp));
        mImageView.setImageDrawable(octo);
        octo.startDrift();

        mImageView.setOnTouchListener(new View.OnTouchListener() {
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
                }
                return true;
            }
        });
    }
}
