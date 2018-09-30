/*
 * Copyright (C) 2018 Timothy "ZeevoX" Langer
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

package com.zeevox.octo.wallpaper;

import android.annotation.TargetApi;
import android.app.WallpaperColors;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import android.view.SurfaceHolder;
import android.widget.ImageView;

import com.zeevox.octo.core.Ocquarium;
import com.zeevox.octo.core.OctopusDrawable;

public class OcquariumWallpaperService extends LiveWallpaperService {
   private final String TAG = this.getClass().getSimpleName();
   private Context mContext;
   private ContextWrapper mContextWrapper;
   private SharedPreferences preferences;
   private float dp;

   @Override
   public Engine onCreateEngine()
   {
      mContext        = this;
      mContextWrapper = this;
      // Initialize preferences here to prevent creating a new preferences for each frame
      preferences = PreferenceManager.getDefaultSharedPreferences(mContextWrapper);
      // get it once to prevent getting it for each frame [performance]
      dp = getResources().getDisplayMetrics().density;
      return(new OcquariumWallpaperEngine());
   }

   private class OcquariumWallpaperEngine extends LiveWallpaperEngine {
      int offsetX;
      int offsetY;
      ImageView mImageView = null;
      OctopusDrawable octo = null;
      GradientDrawable backgroundGradient = null;
      private WallpaperColors wallpaperColors;

      @Override
      public void onSurfaceChanged(SurfaceHolder holder, int format,
                                   int width, int height)
      {
         super.onSurfaceChanged(holder, format, width, height);
         backgroundGradient = null;
         octo       = null;
         mImageView = null;
      }

      @Override
      public void onOffsetsChanged(float xOffset, float yOffset,
                                   float xOffsetStep, float yOffsetStep, int xPixelOffset,
                                   int yPixelOffset)
      {
         // store the offsets
         this.offsetX = xPixelOffset;
         this.offsetY = yPixelOffset;

         super.onOffsetsChanged(xOffset, yOffset, xOffsetStep, yOffsetStep,
                                xPixelOffset, yPixelOffset);
      }

      @Override
      public Bundle onCommand(String action, int x, int y, int z,
                              Bundle extras, boolean resultRequested)
      {
         if ("android.wallpaper.tap".equals(action))
         {
            //createCircle(x - this.offsetX, y - this.offsetY);
         }
         return(super.onCommand(action, x, y, z, extras, resultRequested));
      }

      @Override
      public void onCreate(SurfaceHolder surfaceHolder)
      {
         super.onCreate(surfaceHolder);

         // By default we don't get touch events, so enable them.
         // TODO disabled until properly implement touch on the wallpaper
         //setTouchEventsEnabled(true);
      }

      @Override
      protected void drawFrame()
      {
         SurfaceHolder holder = getSurfaceHolder();

         Canvas c = null;

         try {
            c = holder.lockCanvas();
            if (c != null)
            {
               draw(c);
            }
         } finally {
            if (c != null)
            {
               holder.unlockCanvasAndPost(c);
            }
         }
      }

      @Nullable
      @Override
      @RequiresApi(Build.VERSION_CODES.O_MR1)
      @TargetApi(Build.VERSION_CODES.O_MR1)
      public WallpaperColors onComputeColors()
      {
         super.onComputeColors();
         return(wallpaperColors);
      }

      void draw(Canvas c)
      {
         c.save();
         if (preferences.getBoolean("restart_live_wallpaper", false))
         {
            backgroundGradient = null;
            mImageView         = null;
            octo = null;
            preferences.edit().putBoolean("restart_live_wallpaper", false).apply();
         }
         if (backgroundGradient == null)
         {
            // Recreate octo_bg.xml programmatically
            backgroundGradient = Ocquarium.bgGradient(mContextWrapper, getResources());
            // Only on Android 8.1+ - see https://developer.android.com/about/versions/oreo/android-8.1.html#wallpaper
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1)
            {
               // Report to the WallpaperColors API
               wallpaperColors = WallpaperColors.fromDrawable(backgroundGradient);
               notifyColorsChanged();
            }
            // Set the size of the gradient background
            backgroundGradient.setBounds(c.getClipBounds());
         }
         else
         {
            // Display the gradient background
            backgroundGradient.draw(c);
         }

         if (mImageView == null)
         {
            // Create the ImageView which will contain the octopus
            mImageView = new ImageView(mContextWrapper);
            // Set boundaries / size for the octopus
            mImageView.setClipBounds(c.getClipBounds());
         }
         else
         {
            if (octo == null)
            {
               // Display the octopus
               float octoMinSize = Float.parseFloat(preferences.getString("octopus_min_size", "40"));
               float octoMaxSize = Float.parseFloat(preferences.getString("octopus_max_size", "180"));
               octo = new OctopusDrawable(mContextWrapper);
               octo.setSizePx((int)(OctopusDrawable.randfrange(octoMinSize, octoMaxSize) * dp));
               mImageView.setImageDrawable(octo);
               octo.setBounds(mImageView.getClipBounds());
               octo.startDrift();
            }
            else
            {
               mImageView.setImageDrawable(octo);
            }
            // Draw to the canvas
            mImageView.draw(c);
         }
         c.restore();
      }
   }
}
