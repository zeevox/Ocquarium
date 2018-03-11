package com.zeevox.octo.settings;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.EditTextPreference;
import android.util.AttributeSet;

import com.zeevox.octo.R;

@SuppressWarnings("unused")
public class EditNumberPreference extends EditTextPreference {
    private Integer maximum;
    private Integer minimum;

    public EditNumberPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public EditNumberPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public EditNumberPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (attrs != null) {
            TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.EditNumberPreference, 0, 0);
            maximum = a.getInteger(R.styleable.EditNumberPreference_maximum, 100);
            minimum = a.getInteger(R.styleable.EditNumberPreference_minimum, 0);
        }
        else {
            maximum = 100;
            minimum = 0;
        }
    }

    public EditNumberPreference(Context context) {
        super(context);
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        boolean valid = this.getEditText().getText().toString().length() > 0 // check that it is not a blank value
                && Integer.parseInt(this.getEditText().getText().toString()) <= maximum // check maximum value
                && Integer.parseInt(this.getEditText().getText().toString()) >= minimum; // check minimum value
        super.onDialogClosed(valid);
    }
}
