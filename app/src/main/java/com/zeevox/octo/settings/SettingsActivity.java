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

import android.app.AlertDialog;
import android.app.WallpaperInfo;
import android.app.WallpaperManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceScreen;
import androidx.preference.SwitchPreferenceCompat;

import com.rarepebble.colorpicker.ColorPreference;
import com.zeevox.octo.BuildConfig;
import com.zeevox.octo.FeedbackActivity;
import com.zeevox.octo.R;
import com.zeevox.octo.core.Ocquarium;
import com.zeevox.octo.core.OctopusDrawable;
import com.zeevox.octo.util.ColorUtils;
import com.zeevox.octo.wallpaper.OcquariumWallpaperService;

import java.util.Objects;
import java.util.Random;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On handset devices,
 * settings are presented as a single list. On tablets, settings are split by category, with
 * category headers shown in a panel on the left.
 *
 * <p>See <a href="http://developer.android.com/design/patterns/settings.html">Android Design:
 * Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings API Guide</a> for more
 * information on developing a Settings UI.
 */
@SuppressWarnings("ConstantConditions")
public class SettingsActivity extends AppCompatActivity
    implements PreferenceFragmentCompat.OnPreferenceStartScreenCallback, PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {

  /**
   * A preference value change listener that updates the preference's summary to reflect its new
   * value.
   */
  private static final Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener =
      (preference, value) -> {
        String stringValue = value.toString();

        if (preference instanceof ListPreference) {
          // For list preferences, look up the correct display value in
          // the preference's 'entries' list.
          ListPreference listPreference = (ListPreference) preference;
          int index = listPreference.findIndexOfValue(stringValue);

          // Set the summary to reflect the new value.
          preference.setSummary(index >= 0 ? listPreference.getEntries()[index] : null);

        } else {
          // For all other preferences, set the summary to the value's
          // simple string representation.
          preference.setSummary(stringValue);
        }
        return true;
      };

  /**
   * Binds a preference's summary to its value. More specifically, when the preference's value is
   * changed, its summary (line of text below the preference title) is updated to reflect the value.
   * The summary is also immediately updated upon calling this method. The exact display format is
   * dependent on the type of preference.
   *
   * @see #sBindPreferenceSummaryToValueListener
   */
  private static void bindPreferenceSummaryToValue(Preference preference) {
    // Set the listener to watch for value changes.
    preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

    // Trigger the listener immediately with the preference's
    // current value.
    sBindPreferenceSummaryToValueListener.onPreferenceChange(
        preference,
        PreferenceManager.getDefaultSharedPreferences(preference.getContext())
            .getString(preference.getKey(), ""));
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getSupportActionBar() != null)
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    if (savedInstanceState == null)
      getSupportFragmentManager().beginTransaction().replace(android.R.id.content, new TopLevelPreferenceFragment()).commit();
  }

  @Override
  public boolean onPreferenceStartScreen(@NonNull PreferenceFragmentCompat caller, PreferenceScreen pref) {
    Bundle args = new Bundle();
    args.putString(PreferenceFragmentCompat.ARG_PREFERENCE_ROOT, pref.getKey());

    TopLevelPreferenceFragment fragment = new TopLevelPreferenceFragment();
    fragment.setArguments(args);

    getSupportFragmentManager().beginTransaction().add(android.R.id.content, fragment, pref.getKey()).addToBackStack(pref.getKey()).commit();
    return true;
  }

  @Override
  public boolean onPreferenceStartFragment(@NonNull PreferenceFragmentCompat caller, Preference pref) {
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

  public void share(View view) {
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

  public void githubUrl(View view) {
    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/ZeevoX/Ocquarium")));
  }

  public void playStoreUrl(View view) {
    startActivity(
        new Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://play.google.com/store/apps/details?id=com.zeevox.octo")));
  }

  public void telegramAlpha(View view) {
    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/OcquariumAlpha")));
  }

  public void sendEmail(View view) {
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

  public void feedback(View view) {
    startActivity(new Intent(this, FeedbackActivity.class));
  }

  public static class TopLevelPreferenceFragment extends BasePreferenceFragment {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
      super.onCreatePreferences(savedInstanceState, rootKey);
      setPreferencesFromResource(R.xml.pref_headers, rootKey);
    }
  }

  public static class GeneralPreferenceFragment extends BasePreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      addPreferencesFromResource(R.xml.pref_general);

      // Bind the summaries of EditText/List/Dialog/Ringtone preferences
      // to their values. When their values change, their summaries are
      // updated to reflect the new value, per the Android Design
      // guidelines.
      bindPreferenceSummaryToValue(findPreference("octo_fade_in_duration"));

      if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
        ((SwitchPreferenceCompat) findPreference("platlogo_v2")).setChecked(false);
        findPreference("platlogo_v2").setEnabled(false);
        ((PreferenceScreen) findPreference("category_general"))
            .removePreference(findPreference("platlogo_v2"));
      } else {
        findPreference("platlogo_v2")
            .setEnabled(((SwitchPreferenceCompat) findPreference("show_platlogo")).isChecked());
        findPreference("show_platlogo")
            .setOnPreferenceChangeListener(
                (preference, newValue) -> {
                  findPreference("platlogo_v2").setEnabled(((boolean) newValue));
                  return true;
                });
      }
    }
  }

  public static class BackgroundPreferenceFragment extends BasePreferenceFragment {

    private boolean wasGradientStartDefault = false;
    private boolean wasGradientEndDefault = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      addPreferencesFromResource(R.xml.pref_background);

      ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
      if (actionBar != null) {
        actionBar.setDisplayHomeAsUpEnabled(true);
      }

      findPreference("gradient_start_color")
          .setOnPreferenceChangeListener(
              (preference, newValue) -> {
                wasGradientStartDefault =
                    Objects.equals(
                        newValue, getResources().getColor(R.color.octo_bg_default_start_color));
                  findPreference("reset_background_colors").setEnabled(!wasGradientStartDefault || !wasGradientEndDefault);
                return true;
              });

      findPreference("gradient_end_color")
          .setOnPreferenceChangeListener(
              (preference, newValue) -> {
                wasGradientEndDefault =
                    Objects.equals(
                        newValue, getResources().getColor(R.color.octo_bg_default_end_color));
                  findPreference("reset_background_colors").setEnabled(!wasGradientStartDefault || !wasGradientEndDefault);
                return true;
              });

      findPreference("swap_background_colors")
          .setOnPreferenceClickListener(preference -> {
            ColorPreference startColorPreference = findPreference("gradient_start_color");
            ColorPreference endColorPreference = findPreference("gradient_end_color");
            int startColor = startColorPreference.getColor();
            int endColor = endColorPreference.getColor();
            startColorPreference.setColor(endColor);
            endColorPreference.setColor(startColor);
            return true;
          });

      findPreference("reset_background_colors")
          .setOnPreferenceClickListener(
              preference -> {
                ColorPreference startColor = findPreference("gradient_start_color");
                startColor.setColor(getResources().getColor(R.color.octo_bg_default_start_color));
                ColorPreference endColor = findPreference("gradient_end_color");
                endColor.setColor(getResources().getColor(R.color.octo_bg_default_end_color));
                preference.setEnabled(false);
                return true;
              });

      findPreference("random_ui_gradients")
          .setOnPreferenceClickListener(
              new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(@NonNull Preference preference) {
                  final String[][] randomGradientDetails = {
                      ColorUtils.getGradientDetails(getRandomInt(ColorUtils.gradients.size()))
                  };

                  final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                  builder
                      .setTitle(R.string.prefs_uigradients_dialog_title)
                      .setMessage(
                          String.format(
                              getResources()
                                  .getString(R.string.prefs_uigradients_dialog_description),
                              ColorUtils.getGradientName(randomGradientDetails[0])))
                      .setNegativeButton(
                          android.R.string.cancel,
                          (dialogInterface, i) -> dialogInterface.dismiss())
                      .setPositiveButton(
                          R.string.action_apply,
                          (dialogInterface, i) -> {
                            ColorPreference startColor =
                                findPreference("gradient_start_color");
                            startColor.setColor(
                                ColorUtils.getGradientStartColor(randomGradientDetails[0]));
                            ColorPreference endColor =
                                findPreference("gradient_end_color");
                            endColor.setColor(
                                ColorUtils.getGradientEndColor(randomGradientDetails[0]));
                          });

                  builder.create().show();
                  return true;
                }

                private int getRandomInt(int max) {
                  return new Random().nextInt(max);
                }
              });
    }

    @Override
    public void onDisplayPreferenceDialog(@NonNull Preference preference) {
      if (preference instanceof ColorPreference) {
        ((ColorPreference) preference).showDialog(this, 0);
      } else super.onDisplayPreferenceDialog(preference);
    }
  }

  public static class OctopusFragment extends BasePreferenceFragment implements PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      addPreferencesFromResource(R.xml.pref_octopus);

      findPreference("experimental_options_expand")
          .setOnPreferenceClickListener(
              preference -> {
                PreferenceScreen prefOctopus = findPreference("pref_octopus");
                // Hide the option to expand experimental options
                prefOctopus.removePreference(preference);
                // Add the experimental options
                addPreferencesFromResource(R.xml.pref_experimental);
                return true;
              });
    }

    @Override
    public boolean onPreferenceStartFragment(@NonNull PreferenceFragmentCompat caller, Preference pref) {
      if (pref.getFragment().equals(OctopusFragmentV2.class.getName())) {
        Fragment fragment = Fragment.instantiate(caller.getActivity(), pref.getFragment(), pref.getExtras());
        fragment.setTargetFragment(caller, 0);
        caller.getActivity().getSupportFragmentManager().beginTransaction().replace(android.R.id.content, fragment).addToBackStack(null).commit();
        return true;
      }
      return false;
    }
  }

  public static class WallpaperFragment extends BasePreferenceFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      addPreferencesFromResource(R.xml.pref_wallpaper);

      // Remove live wallpaper preferences if the device doesn't support them
      PackageManager pm = getActivity().getPackageManager();
      if (!pm.hasSystemFeature(PackageManager.FEATURE_LIVE_WALLPAPER)) {
        getPreferenceScreen().removePreference(findPreference("set_live_wallpaper"));
        getPreferenceScreen().removePreference(findPreference("restart_live_wallpaper"));
      } else {
        findPreference("set_live_wallpaper")
            .setOnPreferenceClickListener(
                preference -> {
                  Intent intent = new Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
                  intent.putExtra(
                      WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                      new ComponentName(getActivity(), OcquariumWallpaperService.class));
                  startActivity(intent);
                  return false;
                });

        findPreference("restart_live_wallpaper")
            .setOnPreferenceClickListener(
                preference -> {
                  Toast.makeText(
                      getActivity(), R.string.prefs_restarting_wallpaper, Toast.LENGTH_SHORT)
                      .show();
                  restartLiveWallpaper();
                  return false;
                });
      }

      findPreference("set_screensaver")
          .setOnPreferenceClickListener(
              preference -> {
                try {
                  startActivity(new Intent(Settings.ACTION_DREAM_SETTINGS));
                } catch (ActivityNotFoundException e) {
                  Toast.makeText(
                      getActivity(), R.string.prefs_screensaver_error, Toast.LENGTH_SHORT)
                      .show();
                }
                return false;
              });
    }

    @Override
    public void onPause() {
      super.onPause();
      restartLiveWallpaper();
    }

    @Override
    public void onResume() {
      super.onResume();
      Preference setLiveWallpaper = findPreference("set_live_wallpaper");
      Preference restartLiveWallpaper = findPreference("restart_live_wallpaper");
      if (isLiveWallpaperSet()) {
        setLiveWallpaper.setEnabled(false);
        setLiveWallpaper.setSummary(R.string.prefs_wallpaper_disabled_desc);
        restartLiveWallpaper.setEnabled(true);
      }
    }

    private boolean isLiveWallpaperSet() {
      WallpaperInfo info = WallpaperManager.getInstance(getActivity()).getWallpaperInfo();
      return info != null && info.getPackageName().equals(getActivity().getPackageName());
    }

    private void restartLiveWallpaper() {
      if (isLiveWallpaperSet()) {
        SharedPreferences.Editor editor =
            PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();
        editor.putBoolean("restart_live_wallpaper", true);
        editor.apply();
      }
    }
  }

  public static class AboutFragment extends Fragment {
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
      super.onViewCreated(view, savedInstanceState);

      setHasOptionsMenu(true);

      TextView textView = requireView().findViewById(R.id.about_app_version);
      textView.setText(BuildConfig.VERSION_NAME);

      TextView alphaBuilds = getView().findViewById(R.id.about_action_alpha);
      //noinspection ConstantConditions
      if (!BuildConfig.BUILD_TYPE.equals("travis")
          && isAlphaVersionInstalled(getActivity().getPackageManager())) {
        alphaBuilds.setText(getString(R.string.prefs_open_ocquarium_alpha));
        alphaBuilds.setOnClickListener(
            view1 -> startActivity(
                getActivity()
                    .getPackageManager()
                    .getLaunchIntentForPackage("com.zeevox.octo.alpha")));
      }
    }

    @Override
    public View onCreateView(
        LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      return inflater.inflate(R.layout.activity_about, container, false);
    }

    private boolean isAlphaVersionInstalled(PackageManager packageManager) {
      try {
        packageManager.getPackageInfo("com.zeevox.octo.alpha", 0);
        return true;
      } catch (PackageManager.NameNotFoundException e) {
        return false;
      }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
      if (item.getItemId() == android.R.id.home)
        getActivity().onBackPressed();
      return super.onOptionsItemSelected(item);
    }
  }

  public static class OctopusFragmentV2 extends Fragment {
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
      super.onViewCreated(view, savedInstanceState);

      setHasOptionsMenu(true);

      final int OCTOPUS_MIN_SIZE = 20;
      final int OCTOPUS_MAX_SIZE = 500;
      final int OCTOPUS_SIZE_VARIATION = 70;
      final int OCTOPUS_RANGE =
          (OCTOPUS_MAX_SIZE - OCTOPUS_SIZE_VARIATION) - (OCTOPUS_MIN_SIZE + OCTOPUS_SIZE_VARIATION);

      // Initialize preferences
      final SharedPreferences preferences =
          PreferenceManager.getDefaultSharedPreferences(getActivity());

      // Get the display density
      final float dp = getResources().getDisplayMetrics().density;

      int averageOctopusSize =
          Integer.parseInt(preferences.getString("octopus_average_size", "110"));

      // Set the background to be the gradient with user defined colors
      // See bgGradient(ContextWrapper, Resources) for more information about this
      requireView()
          .findViewById(R.id.prefs_octopus_bg)
          .setBackground(Ocquarium.bgGradient(getActivity(), getResources()));

      if (!ColorUtils.isColorLight(
          preferences.getInt(
              "gradient_end_color", getResources().getColor(R.color.octo_bg_default_end_color)))) {
        requireView()
            .findViewById(R.id.prefs_octopus_v2_bottom_gradient)
            .setBackground(null);
      }

      final OctopusDrawable octo = new OctopusDrawable(getActivity());
      octo.setSizePx(averageOctopusSize * (int) dp);
      ((ImageView) requireView().findViewById(R.id.pref_octopus_image_view))
          .setImageDrawable(octo);
      octo.startDrift();

      SeekBar octopusSizeSeekBar =
          requireView().findViewById(R.id.prefs_octopus_average_size_seekbar);
      int progress =
          (int) Math.round((double) averageOctopusSize / (double) OCTOPUS_RANGE * (double) 100);
      octopusSizeSeekBar.setProgress(progress);
      octopusSizeSeekBar.setOnSeekBarChangeListener(
          new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
              octo.stopDrift();
              octo.moveTo(
                  getResources().getDisplayMetrics().widthPixels >> 1,
                  (getResources().getDisplayMetrics().heightPixels >> 1) - 100 * (int) dp);
              octo.setSizePx((i * OCTOPUS_RANGE / 100 + OCTOPUS_MIN_SIZE) * (int) dp);
              octo.startDrift();
              SharedPreferences.Editor editor = preferences.edit();
              editor.putString(
                  "octopus_average_size",
                  Integer.toString(i * OCTOPUS_RANGE / 100 + OCTOPUS_MIN_SIZE));
              editor.apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
          });
    }

    @Override
    public View onCreateView(
        LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      return inflater.inflate(R.layout.pref_octopus, container, false);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
      if (item.getItemId() == android.R.id.home)
        getActivity().onBackPressed();
      return super.onOptionsItemSelected(item);
    }
  }

  /**
   * This is the base class that enables the UI 'back' button in all child fragments. Prevents
   * unnecessary code duplication.
   */
  public static class BasePreferenceFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
      setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
      super.onActivityCreated(savedInstanceState);
      setDivider(null);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
      if (item.getItemId() == android.R.id.home)
        getActivity().onBackPressed();
      return super.onOptionsItemSelected(item);
    }
  }
}