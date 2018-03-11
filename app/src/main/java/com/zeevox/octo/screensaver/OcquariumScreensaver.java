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

package com.zeevox.octo.screensaver;

import android.service.dreams.DreamService;
import android.util.Log;

import com.zeevox.octo.core.Ocquarium;

public class OcquariumScreensaver extends DreamService {


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDreamingStarted() {
        super.onDreamingStarted();
    }

    @Override
    public void onDreamingStopped() {
        super.onDreamingStopped();
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        setInteractive(true);
        setFullscreen(true);
        Log.d(getClass().getSimpleName(), "Dreaming octopi...");
        Ocquarium.start(this, getWindow(), getResources());
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        finish();
    }
}
