package com.example.ewastemanagement;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignupActivity extends AppCompatActivity {
    EditText inputName,inputEmail,inputPassword,inputConfirmPassword;
    Button signupBtn;
    CheckBox checkBox;
    TextView signin;
    ProgressBar progressBar;
    String email,password,name,confirmPass;
    private FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        firebaseFirestore=FirebaseFirestore.getInstance();
        initialize();
        SigninActivity obj=new SigninActivity();
        setListeners();
    }
    public void initialize(){
        inputName=findViewById(R.id.inputName);
        inputEmail=findViewById(R.id.inputEmail1);
        inputPassword=findViewById(R.id.inputPassword1);
        inputConfirmPassword=findViewById(R.id.inputConfirmPassword);
        signupBtn=findViewById(R.id.btnSignup);
        signin=findViewById(R.id.textSignin);
        firebaseAuth=FirebaseAuth.getInstance();
        checkBox=findViewById(R.id.companyCheckbox);
        progressBar=findViewById(R.id.singupProgressBar);
    }
    public void loading(boolean load){
        if(load){
            progressBar.setVisibility(View.VISIBLE);
            signupBtn.setVisibility(View.INVISIBLE);
        }
        else{
            signupBtn.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
    public void setListeners(){
        signupBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                loading(true);
                name=inputName.getText().toString();
                email=inputEmail.getText().toString();
                password=inputPassword.getText().toString();
                if(!email.isEmpty() && !password.isEmpty()){
                    firebaseAuth.createUserWithEmailAndPassword(email,password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){

                                        UserObject obj;
                                        if(checkBox.isChecked()){
                                            obj=new UserObject(name,email,"company");
                                        }
                                        else{
                                            obj=new UserObject(name,email,"user");
                                        }

                                        firebaseFirestore.collection("users")
                                                .document(firebaseAuth.getCurrentUser().getUid())
                                                        .set(obj).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()){
                                                            Toast.makeText(getApplicationContext(),"Account Created Successfully",Toast.LENGTH_SHORT).show();
                                                            sendEmailVerification1();
                                                            loading(false);
                                                            startActivity(new Intent(getApplicationContext(),SigninActivity.class));
                                                        }
                                                    }
                                                });


                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            });
                }

            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),SigninActivity.class));
            }
        });
    }

    public void sendEmailVerification1(){
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        firebaseUser.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Email Verification Sent",Toast.LENGTH_SHORT).show();

                            finish();
                        }
                    }
                });
    }
}
