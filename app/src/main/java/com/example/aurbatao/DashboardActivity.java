package com.example.aurbatao;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;
import org.jitsi.meet.sdk.JitsiMeetUserInfo;

import java.net.MalformedURLException;
import java.net.URL;

public class DashboardActivity extends AppCompatActivity {

    EditText codebox;
    Button joinBtn,shareBtn,logoutBtn;
    private boolean doubleBackToExitPressedOnce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        codebox=findViewById(R.id.codeBox);
        joinBtn=findViewById(R.id.joinBtn);
        shareBtn=findViewById(R.id.share);
        logoutBtn=findViewById(R.id.logout);

        URL serverUrl;

        try {
            serverUrl=new URL("https://meet.jit.si");
            JitsiMeetConferenceOptions defaultOptions=new JitsiMeetConferenceOptions.Builder()
                    .setServerURL(serverUrl).setFeatureFlag("meeting-name.enabled",false).setFeatureFlag("pip.enabled",false).build();
            JitsiMeet.setDefaultConferenceOptions(defaultOptions);

        }catch (MalformedURLException e){
            e.printStackTrace();
        }



        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JitsiMeetConferenceOptions options=new JitsiMeetConferenceOptions.Builder().setRoom(codebox.getText().toString()).setFeatureFlag("meeting-name.enabled",false).setFeatureFlag("pip.enabled",false).build();

                JitsiMeetActivity.launch(DashboardActivity.this,options);
            }
        });

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String string=codebox.getText().toString();
                Intent intent=new Intent();
                intent.setAction(intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT,string);
                intent.setType("text/plain");
                startActivity(intent);
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(DashboardActivity.this,LoginActivity.class));
                finish();
            }
        });


    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder alertDialog=new AlertDialog.Builder(DashboardActivity.this);
        alertDialog.setCancelable(false);
        alertDialog.setTitle("Exit App");
        alertDialog.setMessage("Do you want to really exit app?");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finishAffinity();

            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
            }
        });
        alertDialog.show();
    }
}