package com.tejab.firebaseapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity {

    EditText username,status;
    ImageView dp;
    Button save;

    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mStateListener;

    DatabaseReference mFirebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

    username=(EditText) findViewById(R.id.username);
    status=(EditText) findViewById(R.id.userstatus);

    dp=(ImageView) findViewById(R.id.dp);

    save=(Button) findViewById(R.id.button);

    mAuth=FirebaseAuth.getInstance();
    mStateListener=new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user=firebaseAuth.getCurrentUser();
            if(user!=null){
                finish();
                Intent i=new Intent(ProfileActivity.this,HomePage.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        }
    };
    mAuth.addAuthStateListener(mStateListener);

    mFirebaseDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

    save.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    });
    dp.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    });
    }
}
