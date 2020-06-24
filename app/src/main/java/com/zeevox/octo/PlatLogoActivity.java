/*
 * Copyright (C) 2010 The Android Open Source Project
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
import android.content.res.ColorStateList;
import android.graphics.Outline;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class PlatLogoActivity extends Activity {
  public static final boolean FINISH = true;

  FrameLayout mLayout;
  int mTapCount;
  int mKeyCount;
  final PathInterpolator mInterpolator = new PathInterpolator(0f, 0f, 0.5f, 1f);

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mLayout = new FrameLayout(this);
    setContentView(mLayout);
  }

  @Override
  public void onAttachedToWindow() {
    final DisplayMetrics dm = getResources().getDisplayMetrics();
    final float dp = dm.density;
    final int size =
        (int) (Math.min(Math.min(dm.widthPixels, dm.heightPixels), 600 * dp) - 100 * dp);

    final ImageView im = new ImageView(this);
    final int pad = (int) (40 * dp);
    im.setPadding(pad, pad, pad, pad);
    im.setTranslationZ(20);
    im.setScaleX(0.5f);
    im.setScaleY(0.5f);
    im.setAlpha(0f);

    if (PreferenceManager.getDefaultSharedPreferences(this)
            .getBoolean(
                "platlogo_v2",
                // New OMR1 platlogo only supported on SDK 24+
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        && Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      im.setBackground(
          new RippleDrawable(
              ColorStateList.valueOf(0xFF776677), getDrawable(R.drawable.platlogo_oreo_mr1), null));
      im.setOutlineProvider(
          new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
              final int w = view.getWidth();
              final int h = view.getHeight();
              outline.setOval((int) (w * .125), (int) (h * .125), (int) (w * .96), (int) (h * .96));
            }
          });
      im.setElevation(12f * dp);
    } else {
      im.setBackground(
          new RippleDrawable(
              ColorStateList.valueOf(0xFFFFFFFF), getDrawable(R.drawable.platlogo_oreo), null));
    }
    im.setClickable(true);
    im.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            im.setOnLongClickListener(
                new View.OnLongClickListener() {
                  @Override
                  public boolean onLongClick(View v) {
                    if (mTapCount < 5) return false;
                    im.post(
                        new Runnable() {
                          @Override
                          public void run() {
                            if (FINISH) finish();
                          }
                        });
                    return true;
                  }
                });
            mTapCount++;
          }
        });

    // Enable hardware keyboard input for TV compatibility.
    im.setFocusable(true);
    im.requestFocus();
    im.setOnKeyListener(
        new View.OnKeyListener() {
          @Override
          public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode != KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
              ++mKeyCount;
              if (mKeyCount > 2) {
                if (mTapCount > 5) {
                  im.performLongClick();
                } else {
                  im.performClick();
                }
              }
              return true;
            } else {
              return false;
            }
          }
        });

    mLayout.addView(im, new FrameLayout.LayoutParams(size, size, Gravity.CENTER));

    im.animate()
        .scaleX(1f)
        .scaleY(1f)
        .alpha(1f)
        .setInterpolator(mInterpolator)
        .setDuration(500)
        .setStartDelay(800)
        .start();
  }
}
