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

package com.zeevox.octo.settings;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.preference.EditTextPreference;
import androidx.preference.Preference;

import com.zeevox.octo.R;

@SuppressWarnings("unused")
public class EditNumberPreference extends EditTextPreference {
  private Integer maximum;
  private Integer minimum;

  public EditNumberPreference(
      Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  public EditNumberPreference(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public EditNumberPreference(Context context, AttributeSet attrs) {
    super(context, attrs);
    if (attrs != null) {
      TypedArray a =
          context.getTheme().obtainStyledAttributes(attrs, R.styleable.EditNumberPreference, 0, 0);
      maximum = a.getInteger(R.styleable.EditNumberPreference_maximum, 100);
      minimum = a.getInteger(R.styleable.EditNumberPreference_minimum, 0);
    } else {
      maximum = 100;
      minimum = 0;
    }

    this.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
      @Override
      public boolean onPreferenceChange(Preference preference, Object newValue) {
        String preferenceValue = (String) newValue;
        return preferenceValue.length() > 0 // check that it is not a blank value
                        && Integer.parseInt(preferenceValue)
                        <= maximum // check maximum value
                        && Integer.parseInt(preferenceValue)
                        >= minimum;
      }
    });
  }

  public EditNumberPreference(Context context) {
    super(context);
  }
}
