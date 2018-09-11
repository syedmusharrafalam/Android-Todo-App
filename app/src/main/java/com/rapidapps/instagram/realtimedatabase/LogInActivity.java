package com.rapidapps.instagram.realtimedatabase;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {
FirebaseAuth nAuth;
    EditText editTextEmail,editTextPassword;
    ProgressBar progressBar;
    String admin="admin@gmail.com";
    String pass="123456";
    SignUpActiviy signUpActiviy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
         signUpActiviy=new SignUpActiviy();
       nAuth=FirebaseAuth.getInstance();
        editTextEmail= (EditText) findViewById(R.id.editTextEmail);
        editTextPassword= (EditText) findViewById(R.id.editTextPassword);
        progressBar=(ProgressBar)findViewById(R.id.progressbarLogin);
        findViewById(R.id.textViewSignup).setOnClickListener(this);
        findViewById(R.id.buttonLogin).setOnClickListener(this);
    }

    private void userlogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty()) {
            editTextEmail.setError("email is required");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("please enter valid email");
            editTextEmail.requestFocus();
            return;

        }

        if (password.isEmpty()) {
            editTextPassword.setError("password is required");
            editTextPassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            editTextPassword.setError("please enter 6 digits");
            editTextPassword.requestFocus();
            return;

        }

        if (email.equals(admin)) {
            if (password.equals(pass)) {
                Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                startActivity(intent);
            }

        } else {


            progressBar.setVisibility(View.VISIBLE);
            nAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        finish();
                        Intent intent = new Intent(LogInActivity.this, UserView.class);
                        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(nAuth.getCurrentUser()!=null)
        {
            finish();
            startActivity(new Intent(this,UserView.class));
        }

    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.textViewSignup:
                finish();
                startActivity(new Intent(this,SignUpActiviy.class));
                break;
            case R.id.buttonLogin:
                userlogin();
                break;

        }

    }
}
