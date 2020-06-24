package com.zeevox.octo.settings.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import com.zeevox.octo.R;
import com.zeevox.octo.core.Ocquarium;
import com.zeevox.octo.core.OctopusDrawable;
import com.zeevox.octo.util.ColorUtils;

public class OctopusFragmentV2 extends Fragment {
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
        // See bgGradient(ContextWrapper, Resources) for more info about this
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
                    public void onStartTrackingTouch(SeekBar seekBar) {}

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {}
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