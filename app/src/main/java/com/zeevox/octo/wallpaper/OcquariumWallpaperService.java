package com.zeevox.octo.wallpaper;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.SurfaceHolder;
import android.widget.ImageView;

import com.zeevox.octo.bhs.Ocquarium;
import com.zeevox.octo.bhs.OctopusDrawable;

public class OcquariumWallpaperService extends LiveWallpaperService {
    private final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private ContextWrapper mContextWrapper;
    private SharedPreferences preferences;
    private float dp;

    @Override
    public Engine onCreateEngine() {
        mContext = this;
        mContextWrapper = this;
        // Initialize preferences here to prevent creating a new preferences for each frame
        preferences = PreferenceManager.getDefaultSharedPreferences(mContextWrapper);
        // get it once to prevent getting it for each frame [performance]
        dp = getResources().getDisplayMetrics().density;
        return new OcquariumWallpaperEngine();
    }

    private class OcquariumWallpaperEngine extends LiveWallpaperEngine {

        int offsetX;
        int offsetY;
        int height;
        int width;
        int visibleWidth;
        ImageView mImageView = null;
        OctopusDrawable octo = null;

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format,
                                     int width, int height) {

            this.height = height;
            if (this.isPreview()) {
                this.width = width;
            } else {
                this.width = 2 * width;
            }
            this.visibleWidth = width;

            super.onSurfaceChanged(holder, format, width, height);
        }

        @Override
        public void onOffsetsChanged(float xOffset, float yOffset,
                                     float xOffsetStep, float yOffsetStep, int xPixelOffset,
                                     int yPixelOffset) {
            // store the offsets
            this.offsetX = xPixelOffset;
            this.offsetY = yPixelOffset;

            super.onOffsetsChanged(xOffset, yOffset, xOffsetStep, yOffsetStep,
                    xPixelOffset, yPixelOffset);
        }

        @Override
        public Bundle onCommand(String action, int x, int y, int z,
                                Bundle extras, boolean resultRequested) {
            if ("android.wallpaper.tap".equals(action)) {
                //createCircle(x - this.offsetX, y - this.offsetY);
            }
            return super.onCommand(action, x, y, z, extras, resultRequested);
        }

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);

            // By default we don't get touch events, so enable them.
            // TODO disabled until properly implement touch on the wallpaper
            setTouchEventsEnabled(false);
        }

        @Override
        protected void drawFrame() {
            SurfaceHolder holder = getSurfaceHolder();

            Canvas c = null;
            try {
                c = holder.lockCanvas();
                if (c != null) {
                    draw(c);
                }
            } finally {
                if (c != null)
                    holder.unlockCanvasAndPost(c);
            }
        }


        void draw(Canvas c) {
            // Recreate octo_bg.xml programmatically
            GradientDrawable backgroundGradient = Ocquarium.bgGradient(mContextWrapper, getResources());
            // Set the size of the gradient background
            backgroundGradient.setBounds(c.getClipBounds());
            // Display the gradient background
            backgroundGradient.draw(c);

            // Create the ImageView which will contain the octopus
            mImageView = new ImageView(mContextWrapper);
            // Set boundaries / size for the octopus
            mImageView.setClipBounds(c.getClipBounds());

            if (octo == null) {
                // Display the octopus
                float octoMinSize = Float.parseFloat(preferences.getString("octopus_min_size", "40"));
                float octoMaxSize = Float.parseFloat(preferences.getString("octopus_max_size", "180"));
                octo = new OctopusDrawable(mContextWrapper);
                octo.setSizePx((int) (OctopusDrawable.randfrange(octoMinSize, octoMaxSize) * dp));
                mImageView.setImageDrawable(octo);
                octo.setBounds(mImageView.getClipBounds());
                octo.startDrift();
            } else {
                //octo.stopDrift();
                mImageView.setImageDrawable(octo);
                octo.startDrift();
            }
            // Draw to the canvas
            mImageView.draw(c);
        }
    }
}

