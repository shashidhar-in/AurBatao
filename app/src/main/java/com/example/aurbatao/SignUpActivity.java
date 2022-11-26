package com.example.aurbatao;

import static android.content.ContentValues.TAG;

import android.app.ProgressDialog;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    FirebaseAuth auth;

    FirebaseFirestore database;

    EditText nameinput,emailInput,passwordInput;
    TextView signInBtn;
    Button signUpBtn;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth= FirebaseAuth.getInstance();
        database=FirebaseFirestore.getInstance();

        progressDialog=new ProgressDialog(SignUpActivity.this);
        progressDialog.setTitle("Signing Up");
        progressDialog.setMessage("Please wait...\nDo not press back!");
        progressDialog.setCancelable(false);

        signUpBtn=findViewById(R.id.signUpBtn);
        signInBtn=findViewById(R.id.loginLink);

        nameinput=findViewById(R.id.nameBox);
        emailInput=findViewById(R.id.emailBox);
        passwordInput=findViewById(R.id.passwordBox);


        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();

                String email,password,name;
                name=nameinput.getText().toString();
                email=emailInput.getText().toString();
                password=passwordInput.getText().toString();

                Map<String,Object> user=new HashMap<>();
                user.put("name",name);
                user.put("email",email);
                user.put("password",password);



//                User user=new User();
//                user.setEmail(email);
//                user.setPassword(password);
//                user.setName(name);


                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if(task.isSuccessful()){
                          database.collection("Users").document().set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                              @Override
                              public void onSuccess(Void aVoid) {
                                  startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
                                  finish();

                              }
                          }).addOnFailureListener(new OnFailureListener() {
                              @Override
                              public void onFailure(@NonNull Exception e) {
                                  Log.w(TAG, "Error writing document", e);
                                  Toast.makeText(SignUpActivity.this,"Cannot create user",Toast.LENGTH_SHORT).show();
                              }
                          });

                            Toast.makeText(SignUpActivity.this,"Signed Up Successfully",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(SignUpActivity.this,task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();

                        }
                    }
                });

            }
        });

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
                finish();
            }
        });
    }
}