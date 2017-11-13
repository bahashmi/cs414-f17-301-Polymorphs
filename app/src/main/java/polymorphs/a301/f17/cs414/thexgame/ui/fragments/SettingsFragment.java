package polymorphs.a301.f17.cs414.thexgame.ui.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import polymorphs.a301.f17.cs414.thexgame.R;
import polymorphs.a301.f17.cs414.thexgame.ui.activities.StartupScreenActivity;

/**
 * Created by steve-0 on 10/15/17.
 */

public class SettingsFragment extends Fragment implements GoogleApiClient.OnConnectionFailedListener {


    private StartupScreenActivity startupScreenActivity; // a copy of the startup screen that we need to work with.

    // sets the copy of the startup screen activity that we need to work with.
    public void setStartupScreenActivity(StartupScreenActivity startupScreenActivity)
    {
        this.startupScreenActivity = startupScreenActivity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View settingUI = inflater.inflate(R.layout.settings, container, false);

        Button signout = (Button) settingUI.findViewById(R.id.signOut);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startupScreenActivity.signOutNow(); // sign the user out of the app.
            }
        });


        Button deleteUnregister = (Button) settingUI.findViewById(R.id.delUnreg);
        deleteUnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //TODO: unregister the user from the database, Roger you need to do this from oAuth, Team, we need to decide what to do with the history.
            }
        });

        Switch invertColors = (Switch) settingUI.findViewById(R.id.invertC);
        deleteUnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //TODO: Invert the colors
            }
        });

        Switch winLossRatio = (Switch) settingUI.findViewById(R.id.winLossR);
        winLossRatio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //TODO: This will either turn on or off the win Ratio on the screen
            }
        });




        return settingUI;
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}