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

    private DialogInterface.OnClickListener autoDismissDialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    };

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

        //FEEDBACK BUTTON
        final Button feedbackButton = findViewById(R.id.feedback_send_button);
        feedbackButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Set who to send email to
                        Uri uri = Uri.parse("mailto:zeevox.dev@gmail.com");
                        //Set up email intent
                        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, uri);

                        //Init feedback message
                        String feedbackMessage = getString(R.string.feedback_prefill_content);

                        //Set email title
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, issueTitleInput.getText().toString());

                        //Replace placeholder with data
                        feedbackMessage = feedbackMessage.replace("ISSUE_DESCRIPTION",
                                issueDescriptionInput.getText().toString());

                        //Replace device info placeholders with data
                        feedbackMessage = feedbackMessage.replace("ocquarium_version_name", BuildConfig.VERSION_NAME);
                        feedbackMessage = feedbackMessage.replace("device_fingerprint", Build.FINGERPRINT);

                        //Set email intent
                        emailIntent.putExtra(Intent.EXTRA_TEXT, feedbackMessage);

                        CheckBox checkLatestVersion = findViewById(R.id.check_latest_version);
                        CheckBox checkDeviceInfo = findViewById(R.id.check_device_info);

                        if (checkLatestVersion.isChecked()
                                && checkDeviceInfo.isChecked()
                                && validateIssueTitle()
                                && validateDescription()) {
                            try {
                                //Start default email client
                                startActivity(emailIntent);
                                finish();
                            } catch (ActivityNotFoundException activityNotFoundException) {
                                dialogNoEmailApp();
                            }
                        } else {
                            if (!checkLatestVersion.isChecked()
                                    || !checkDeviceInfo.isChecked()) {
                                dialogInvalid();
                            } else if (!validateIssueTitle()
                                    || !validateDescription()) {
                                //dialogNoField();
                            }
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

        builder.setTitle("Error")
                .setMessage("Please make sure you have checked all the boxes.")
                .setPositiveButton("OK", autoDismissDialogClickListener);

        builder.create().show();
    }

    public void dialogNoField() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Error")
                .setMessage("Please make sure you have filled in all the input fields.")
                .setPositiveButton("OK", autoDismissDialogClickListener);

        builder.create().show();
    }

    public void dialogNoEmailApp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Error")
                .setMessage("No email application found on your device.\nPlease install a supported email app and try again.")
                .setPositiveButton("OK", autoDismissDialogClickListener);

        builder.create().show();
    }

    public boolean onNavigateUp() {
        onBackPressed();
        return super.onNavigateUp();
    }
}
