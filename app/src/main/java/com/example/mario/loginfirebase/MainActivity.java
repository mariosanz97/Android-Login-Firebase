package com.example.mario.loginfirebase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.LoginFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignip;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser()!=null){
            finish();
            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
        }


        progressDialog = new ProgressDialog(this);

        btnRegister = (Button) findViewById(R.id.buttonregister);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPasword);

        textViewSignip = (TextView) findViewById(R.id.textViewSingin);

        btnRegister.setOnClickListener(this);
        textViewSignip.setOnClickListener(this);
    }

    private void registerUser(){
        String email =editTextEmail.getText().toString().trim();
        String password =editTextPassword.getText().toString().trim();


        if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Registering user...");
        progressDialog.show();


        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                        finish();
                        startActivity(new Intent(getApplicationContext(),ProfileActivity.class));


                    Toast.makeText(MainActivity.this, "Registered Succescfull", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "Couldnt register, pls try again", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v == btnRegister){
            registerUser();
        }
        if (v == textViewSignip){
startActivity(new Intent(this, LoginActivity.class));
        }
    }
}
