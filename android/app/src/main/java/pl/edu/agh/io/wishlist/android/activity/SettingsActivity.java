package pl.edu.agh.io.wishlist.android.activity;


import android.app.ActionBar;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import pl.edu.agh.io.wishlist.android.R;
import pl.edu.agh.io.wishlist.android.dagger.DaggerApplication;
import pl.edu.agh.io.wishlist.android.rest.ServerCredentials;

import javax.inject.Inject;

public class SettingsActivity extends AppCompatActivity {

    @Inject
    ServerCredentials serverCredentials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Dagger injection
        DaggerApplication.inject(this);

        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        getFragmentManager().beginTransaction().replace(android.R.id.content, new PrefFragment(serverCredentials)).commit();
    }

    @SuppressWarnings("WeakerAccess")
    public static class PrefFragment extends PreferenceFragment {
        private ServerCredentials serverCredentials;

        public PrefFragment() { }

        public PrefFragment(ServerCredentials serverCredentials) {
            this.serverCredentials = serverCredentials;
        }

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);

            findPreference("reset").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    SharedPreferences.Editor editor = PreferenceManager.
                            getDefaultSharedPreferences(getActivity())
                            .edit().clear();
                    PreferenceManager.setDefaultValues(getActivity(), R.xml.preferences, true);
                    editor.apply();

                    serverCredentials.setHost(getActivity().getString(R.string.server_host));
                    serverCredentials.setPort(Integer.parseInt(getActivity().getString(R.string.server_port)));

                    getActivity().finish();

                    return true;
                }
            });

            findPreference("server_host").setOnPreferenceChangeListener(new OnServerCredentialChanges("server_host"));
            findPreference("server_port").setOnPreferenceChangeListener(new OnServerCredentialChanges("server_port"));
        }

        class OnServerCredentialChanges implements Preference.OnPreferenceChangeListener {

            private final String id;

            public OnServerCredentialChanges(String id) {
                this.id = id;
            }

            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                String value = (String) newValue;

                switch (id) {
                    case "server_host":
                        serverCredentials.setHost(value);
                        return true;
                    case "server_port":
                        serverCredentials.setPort(Integer.parseInt(value));
                        return true;
                    default:
                        return false;
                }
            }
        }


    }


}
