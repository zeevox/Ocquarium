package com.zeevox.octo.settings.fragments;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.zeevox.octo.BuildConfig;
import com.zeevox.octo.R;

public class AboutFragment extends Fragment {
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
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(
                                    getActivity()
                                            .getPackageManager()
                                            .getLaunchIntentForPackage("com.zeevox.octo.alpha"));
                        }
                    });
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
