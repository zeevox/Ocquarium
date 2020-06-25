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

package com.zeevox.octo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.Objects;

public class FeedbackActivity extends Activity {

    private final DialogInterface.OnClickListener autoDismissDialogClickListener =
            (dialog, which) -> dialog.dismiss();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        Objects.requireNonNull(getActionBar()).setDisplayHomeAsUpEnabled(true);

        final EditText issueTitleInput = findViewById(R.id.feedback_issue_title);
        final EditText issueDescriptionInput = findViewById(R.id.feedback_issue_description);

        issueTitleInput.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        validateIssueTitle();
                    }
                });

        issueDescriptionInput.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        validateDescription();
                    }
                });

        // FEEDBACK BUTTON
        final Button feedbackButton = findViewById(R.id.feedback_send_button);
        feedbackButton.setOnClickListener(
                v -> {
                    // Set who to send email to
                    Uri uri = Uri.parse("mailto:zeevox.dev@gmail.com");
                    // Set up email intent
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, uri);

                    // Init feedback message
                    String feedbackMessage = getString(R.string.feedback_prefill_content);

                    // Set email title
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, issueTitleInput.getText().toString());

                    // Replace placeholder with data
                    feedbackMessage =
                            feedbackMessage.replace(
                                    "ISSUE_DESCRIPTION", issueDescriptionInput.getText().toString());

                    // Replace device info placeholders with data
                    feedbackMessage =
                            feedbackMessage.replace("ocquarium_version_name", BuildConfig.VERSION_NAME);
                    feedbackMessage = feedbackMessage.replace("device_fingerprint", Build.FINGERPRINT);

                    // Set email intent
                    emailIntent.putExtra(Intent.EXTRA_TEXT, feedbackMessage);

                    CheckBox checkLatestVersion = findViewById(R.id.check_latest_version);
                    CheckBox checkDeviceInfo = findViewById(R.id.check_device_info);

                    if (checkLatestVersion.isChecked()
                            && checkDeviceInfo.isChecked()
                            && validateIssueTitle()
                            && validateDescription()) {
                        try {
                            // Start default email client
                            startActivity(emailIntent);
                            finish();
                        } catch (ActivityNotFoundException activityNotFoundException) {
                            dialogNoEmailApp();
                        }
                    } else {
                        if (!checkLatestVersion.isChecked() || !checkDeviceInfo.isChecked()) {
                            dialogInvalid();
                        }
                    }
                });
    }

    public boolean validateIssueTitle() {
        final EditText issueTitleInput = findViewById(R.id.feedback_issue_title);
        return !issueTitleInput.getText().toString().isEmpty();
    }

    public boolean validateDescription() {
        final EditText issueReproduceInput = findViewById(R.id.feedback_issue_description);
        if (issueReproduceInput.getText().toString().isEmpty()) {
            issueReproduceInput.setError(getString(R.string.feedback_error_empty));
            return false;
        } else {
            issueReproduceInput.setError(null);
            return true;
        }
    }

    public void dialogInvalid() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder
                .setTitle(R.string.feedback_dialog_title_error)
                .setMessage(R.string.feedback_dialog_invalid_checkboxes)
                .setPositiveButton(R.string.action_ok, autoDismissDialogClickListener);

        builder.create().show();
    }

    public void dialogNoEmailApp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder
                .setTitle(R.string.feedback_dialog_title_error)
                .setMessage(R.string.feedback_dialog_email_app_error)
                .setPositiveButton(R.string.action_ok, autoDismissDialogClickListener);

        builder.create().show();
    }

    public boolean onNavigateUp() {
        onBackPressed();
        return super.onNavigateUp();
    }
}
