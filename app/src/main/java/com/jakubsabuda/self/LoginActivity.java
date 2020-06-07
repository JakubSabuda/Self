/*
 * Copyright (c) 2020.
 * Created by Jakub Sabuda.
 * App is created only for personal use .
 */

package com.jakubsabuda.self;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.jakubsabuda.self.model.Journal;
import com.jakubsabuda.self.util.JournalApi;

public class LoginActivity extends AppCompatActivity {

    //Widgets
    private Button loginButton;
    private Button createAccButton;

    private AutoCompleteTextView emailAdress;
    private EditText password;
    private ProgressBar progressBar;

    //Firebase variables
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("Users");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Animation
        animation();

        progressBar = findViewById(R.id.login_progress);
        emailAdress = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.email_sign_in_button);
        createAccButton = findViewById(R.id.email_create_account);

        firebaseAuth = FirebaseAuth.getInstance();

        //CREATE ACCOUNT BUTOTN
        createAccButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, CreateAccountActivity.class));
            }
        });

        //LOGIN BUTTON
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginEmailPasswordUser(emailAdress.getText().toString().trim(),
                        password.getText().toString().trim());
            }
        });
    }

    private void animation() {
        LinearLayout linearLayout = findViewById(R.id.activity_login_id);
        linearLayout.setBackgroundResource(R.drawable.gradient_animation);
        AnimationDrawable animationDrawable = (AnimationDrawable) linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(10);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();
        //Allowing app to take the whole view
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

    }

    private void loginEmailPasswordUser(String em, String pass) {
        progressBar.setVisibility(View.VISIBLE);

        if(!TextUtils.isEmpty(em) && !TextUtils.isEmpty(pass)){
            firebaseAuth.signInWithEmailAndPassword(em, pass)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            assert user != null;
                            String currnetUserId = user.getUid();


                            collectionReference.whereEqualTo("userId", currnetUserId)
                                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                @Override
                                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                                    assert queryDocumentSnapshots != null;
                                    if (!queryDocumentSnapshots.isEmpty()){
                                        progressBar.setVisibility(View.INVISIBLE);

                                        for(QueryDocumentSnapshot snapshot : queryDocumentSnapshots){
                                            JournalApi journalApi = JournalApi.getInstance();
                                            journalApi.setUsername(snapshot.getString("username"));
                                            journalApi.setUserId(snapshot.getString("userId"));

                                            startActivity(new Intent(LoginActivity.this, JournalListActivity.class));
                                        }

                                }
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(LoginActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            });

        }else{
            Toast.makeText(this, "Fill all the Fields", Toast.LENGTH_SHORT).show();
        }
    }
}
