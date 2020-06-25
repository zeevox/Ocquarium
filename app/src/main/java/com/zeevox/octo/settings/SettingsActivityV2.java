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

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;

import com.zeevox.octo.FeedbackActivity;
import com.zeevox.octo.R;
import com.zeevox.octo.settings.fragments.AboutFragment;
import com.zeevox.octo.settings.fragments.BackgroundPreferenceFragment;
import com.zeevox.octo.settings.fragments.GeneralPreferenceFragment;
import com.zeevox.octo.settings.fragments.OctopusFragment;
import com.zeevox.octo.settings.fragments.WallpaperFragment;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On handset devices,
 * settings are presented as a single list. On tablets, settings are split by category, with
 * category headers shown to the left of the list of settings.
 *
 * <p>See <a href="http://developer.android.com/design/patterns/settings.html">Android Design:
 * Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings API Guide</a> for more
 * information on developing a Settings UI.
 */
@SuppressWarnings("ConstantConditions")
public class SettingsActivityV2 extends AppCompatActivity
        implements PreferenceFragmentCompat.OnPreferenceStartScreenCallback, PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (savedInstanceState == null)
            getSupportFragmentManager().beginTransaction().replace(android.R.id.content, new TopLevelPreferenceFragment()).commit();
    }

    @Override
    public boolean onPreferenceStartScreen(PreferenceFragmentCompat caller, PreferenceScreen pref) {
        Bundle args = new Bundle();
        args.putString(PreferenceFragmentCompat.ARG_PREFERENCE_ROOT, pref.getKey());

        TopLevelPreferenceFragment fragment = new TopLevelPreferenceFragment();
        fragment.setArguments(args);

        getSupportFragmentManager().beginTransaction().add(android.R.id.content, fragment, pref.getKey()).addToBackStack(pref.getKey()).commit();
        return true;
    }

    @Override
    public boolean onPreferenceStartFragment(PreferenceFragmentCompat caller, Preference pref) {
        if (!isValidFragment(pref.getFragment())) return false;
        Fragment fragment = Fragment.instantiate(this, pref.getFragment(), pref.getExtras());
        fragment.setTargetFragment(caller, 0);
        getSupportFragmentManager().beginTransaction().replace(android.R.id.content, fragment).addToBackStack(null).commit();
        return true;
    }

    /**
     * This method stops fragment injection in malicious applications. Make sure to deny any unknown
     * fragments here.
     */
    protected boolean isValidFragment(String fragmentName) {
        return PreferenceFragmentCompat.class.getName().equals(fragmentName)
                || GeneralPreferenceFragment.class.getName().equals(fragmentName)
                || BackgroundPreferenceFragment.class.getName().equals(fragmentName)
                || WallpaperFragment.class.getName().equals(fragmentName)
                || OctopusFragment.class.getName().equals(fragmentName)
                || AboutFragment.class.getName().equals(fragmentName);
    }

    public void share() {
        Intent sendIntent = new Intent();
        sendIntent
                .setAction(Intent.ACTION_SEND)
                .putExtra(
                        Intent.EXTRA_TEXT,
                        String.format(
                                getResources().getString(R.string.share_text), getString(R.string.app_name)))
                .setType("text/plain");
        startActivity(
                Intent.createChooser(
                        sendIntent,
                        String.format(
                                getResources().getString(R.string.share_with), getString(R.string.app_name))));
    }

    public void githubUrl() {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/ZeevoX/Ocquarium")));
    }

    public void playStoreUrl() {
        startActivity(
                new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=com.zeevox.octo")));
    }

    public void telegramAlpha() {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/OcquariumAlpha")));
    }

    public void sendEmail() {
        // Set who to send email to
        Uri uri = Uri.parse("mailto:zeevox.dev@gmail.com");
        // Set up email intent
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, uri);
        // Set email title
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.about_action_email_title));
        // Set email intent
        emailIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.about_action_email_text));
        try {
            // Start default email client
            startActivity(emailIntent);
        } catch (ActivityNotFoundException activityNotFoundException) {
            Toast.makeText(this, R.string.about_action_email_error, Toast.LENGTH_SHORT).show();
        }
    }

    public void feedback() {
        startActivity(new Intent(this, FeedbackActivity.class));
    }

    public static class TopLevelPreferenceFragment extends BasePreferenceFragment {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            super.onCreatePreferences(savedInstanceState, rootKey);
            setPreferencesFromResource(R.xml.pref_headers, rootKey);
        }
    }
}