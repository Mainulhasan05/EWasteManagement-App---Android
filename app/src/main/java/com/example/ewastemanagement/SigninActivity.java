package com.example.ewastemanagement;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class SigninActivity extends AppCompatActivity {
    EditText inputEmail,inputPassword;
    Button btnSignin;
    String email,password;
    ProgressBar progressBar;
    TextView createNewAccount,forgetPasswordTV;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        initialize();
        setListeners();



//startActivity(new Intent(this,HomeUser.class));

    }

    public void initialize(){

        inputEmail=findViewById(R.id.inputEmail);
        inputPassword=findViewById(R.id.inputPassword);
        btnSignin=findViewById(R.id.btnSignin);
        createNewAccount=findViewById(R.id.createNewAccount);
        forgetPasswordTV=findViewById(R.id.forgetPasswordTV);
        progressBar=findViewById(R.id.signInProgressBar);
    }
    public void loading(boolean load){
        if(load){
            progressBar.setVisibility(View.VISIBLE);
            btnSignin.setVisibility(View.INVISIBLE);
        }
        else{
            btnSignin.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
    public void setListeners(){
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loading(true);
                email=inputEmail.getText().toString();
                password=inputPassword.getText().toString();
                firebaseAuth.signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    firebaseUser=firebaseAuth.getCurrentUser();
                                    if(firebaseUser.isEmailVerified()){
                                        DocumentReference documentReference =firebaseFirestore.collection("users").document(firebaseAuth.getCurrentUser().getUid());

                                        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                            @Override
                                            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                                String role=value.getString("role");

                                                if(role.equals("user")){
                                                    startActivity(new Intent(getApplicationContext(),HomeUser.class));
                                                }
                                                else{
                                                    startActivity(new Intent(getApplicationContext(),CompanyActivity.class));
                                                }


                                            }
                                        });
                                        Toast.makeText(getApplicationContext(),"Login Successfull",Toast.LENGTH_SHORT).show();
                                        loading(false);

                                        finish();
                                    }
                                    else{
                                        loading(false);
                                        Toast.makeText(getApplicationContext(),"Please Verify Your Email",Toast.LENGTH_SHORT).show();
                                        firebaseAuth.signOut();
                                    }

                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                loading(false);
                                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });

        createNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(),SignupActivity.class));
            }
        });

        forgetPasswordTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ForgetPasswordActiivity.class));
            }
        });
    }
}