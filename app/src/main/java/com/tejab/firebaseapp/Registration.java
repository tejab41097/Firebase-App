package com.tejab.firebaseapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity {

    EditText id, pass;
    Button create;

    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthStateListener;

    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        id=(EditText) findViewById(R.id.editText);
        pass=(EditText) findViewById(R.id.editText2);

        create=(Button) findViewById(R.id.button);

        mProgressDialog= new ProgressDialog(this);

        mAuth= FirebaseAuth.getInstance();

        mAuthStateListener= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user= firebaseAuth.getCurrentUser();
                if(user!=null){
                    Intent i= new Intent(Registration.this,HomePage.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }

            }
        };

        mAuth.addAuthStateListener(mAuthStateListener);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mProgressDialog.setTitle("Create Account");
                mProgressDialog.setMessage("Wait while account get created");
                mProgressDialog.show();
                createAccount();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthStateListener);
    }

    private void createAccount() {
        final String email, password;
        email= id.getText().toString().trim();
        password= pass.getText().toString().trim();

        if(!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)){
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()){
                        Toast.makeText(Registration.this,"Account created successfully",Toast.LENGTH_LONG).show();
                        mProgressDialog.dismiss();

                        Intent i= new Intent(Registration.this,HomePage.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }else if(password.length()<=6){
                        Toast.makeText(Registration.this,"Please Enter Password having length more than 6 char",Toast.LENGTH_LONG).show();
                        mProgressDialog.dismiss();
                    }else{
                        Toast.makeText(Registration.this,"Account creation Failed",Toast.LENGTH_LONG).show();
                        mProgressDialog.dismiss();
                    }
                }
            });
        }
    }
}
