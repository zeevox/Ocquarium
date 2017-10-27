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

package com.zeevox.octo;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class Ocquarium extends Activity {

  ImageView mImageView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Initialize preferences
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

    final float dp = getResources().getDisplayMetrics().density;

    // Recreate octo_bg.xml programmatically
    GradientDrawable backgroundGradient = new GradientDrawable();
    // Set the background colors / fetch them from the preferences menu
    backgroundGradient.setColors(new int[]{
        Color.parseColor(preferences.getString("gradient_start_color", "#FF205090")),
        Color.parseColor(preferences.getString("gradient_end_color", "#FF001040"))
    });
    // Linear gradient
    backgroundGradient.setGradientType(GradientDrawable.LINEAR_GRADIENT);
    // Set the gradient angle to -90 (same as 270)
    backgroundGradient.setOrientation(Orientation.TOP_BOTTOM);
    // Set the background to the new drawable we've created
    getWindow().setBackgroundDrawable(backgroundGradient);

    FrameLayout bg = new FrameLayout(this);
    setContentView(bg);
    bg.setAlpha(0f);
    bg.animate().setStartDelay(500).setDuration(5000).alpha(1f).start();

    mImageView = new ImageView(this);
    bg.addView(mImageView, new FrameLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

    final com.zeevox.octo.OctopusDrawable octo = new com.zeevox.octo.OctopusDrawable(
        getApplicationContext());
    octo.setSizePx((int) (com.zeevox.octo.OctopusDrawable.randfrange(40f, 180f) * dp));
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
