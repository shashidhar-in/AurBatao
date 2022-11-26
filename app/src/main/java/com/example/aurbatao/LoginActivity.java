package com.example.aurbatao;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth auth;

    EditText emailInput,passwordInput;
    Button loginBtn;
    TextView signupBtn;

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //input feilds
        emailInput=findViewById(R.id.emailBox);
        passwordInput=findViewById(R.id.passwordBox);

        //Buttons
        loginBtn=findViewById(R.id.loginBtn);
        signupBtn=findViewById(R.id.signupLink);

        auth=FirebaseAuth.getInstance();

        progressDialog=new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("Loging In");
        progressDialog.setMessage("Please wait...\nDo not press back!");
        progressDialog.setCancelable(false);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();

                String email,password;

                email=emailInput.getText().toString();
                password=passwordInput.getText().toString();

                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if (task.isSuccessful()){
                            startActivity(new Intent(LoginActivity.this,DashboardActivity.class));
                            finish();

                            Toast.makeText(LoginActivity.this,"Logged In",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(LoginActivity.this,task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });


        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
                finish();
            }
        });
    }
}