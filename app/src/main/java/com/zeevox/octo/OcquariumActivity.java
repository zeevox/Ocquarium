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

package com.zeevox.octo;

import android.app.Activity;

import com.zeevox.octo.bhs.Ocquarium;

public class OcquariumActivity extends Activity {

    /* We place the code in onResume() so that the activity is redrawn after visiting settings. */
    @Override
    protected void onResume() {
        super.onResume();
        Ocquarium.start(this, getWindow(), getResources(), true);
    }
}
