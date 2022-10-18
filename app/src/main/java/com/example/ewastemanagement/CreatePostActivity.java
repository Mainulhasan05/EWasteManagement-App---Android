package com.example.ewastemanagement;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class CreatePostActivity extends AppCompatActivity {
    DatePicker datePicker;
    TimePicker timePicker;
    Button postsaveBtn,uploadPostImageBtn;
    EditText noteET,addressET;
    CheckBox ch1,ch2,ch3,ch4,ch5;
    Spinner spinner;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    StorageReference storageReference;
    ImageView imageView;
    Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        firebaseFirestore=FirebaseFirestore.getInstance();
        storageReference= FirebaseStorage.getInstance().getReference();
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        initialize();
        setListeners();
        ArrayList<String> reward=new ArrayList<>();
        reward.add("Donate");
        reward.add("Cash");
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,reward);
        spinner.setAdapter(adapter);

    }
    public void initialize(){
        addressET=findViewById(R.id.addressET);
        noteET=findViewById(R.id.noteET);
        datePicker=findViewById(R.id.datePicker);
        timePicker=findViewById(R.id.timePicker);
        postsaveBtn=findViewById(R.id.postsaveBtn);
        spinner=findViewById(R.id.rewardspinner);
        ch1=findViewById(R.id.checkBox);
        ch2=findViewById(R.id.checkBox2);
        ch3=findViewById(R.id.checkBox3);
        ch4=findViewById(R.id.checkBox4);
        ch5=findViewById(R.id.checkBox5);
        uploadPostImageBtn=findViewById(R.id.uploadPostImageBtn);
        imageView=findViewById(R.id.wasteImage);
    }
    public void setListeners(){
        uploadPostImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
        postsaveBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                String wastetype=checkWaste();
                String rewardType=spinner.getSelectedItem().toString();
                String address=addressET.getText().toString();
                String notes=noteET.getText().toString();

                String day = "" + datePicker.getDayOfMonth();
                String month = "" + (datePicker.getMonth() + 1);
                String year = "" + datePicker.getYear();
                String hour=""+timePicker.getHour();
                String minute=""+timePicker.getMinute();
                String date=day+"-"+month+"-"+year;
                String time=hour+":"+minute;

                if(timePicker.getHour()>12){
                    hour=""+(timePicker.getHour()-12);
                    time=hour+":"+minute+" PM";
                }
                else if(timePicker.getHour()==0){
                    time=12+":"+minute+" AM";
                }
                else if(timePicker.getHour()==12){
                    time=12+":"+minute+" PM";
                }
                else{
                    time=hour+":"+minute+" AM";
                }
                if(imageUri!=null){
                    storageReference.child("image").putFile(imageUri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                    Task<Uri> downloadUri = taskSnapshot.getStorage().getDownloadUrl();

                                    if(downloadUri.isSuccessful()){
                                        String generatedFilePath = downloadUri.getResult().toString();
                                        showToast(generatedFilePath);
                                        Log.d("aaaaaa",generatedFilePath);
                                    }}
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });

                }


                PostModel postModel=new PostModel(firebaseUser.getUid(),wastetype,date,time,address,notes,rewardType,"abc");
                showToast(firebaseUser.getUid());
                firebaseFirestore.collection("posts")
                        .add(postModel)
                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                if(task.isSuccessful()){
                                    showToast("Post Added Successfully");
                                    clearForm();
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                showToast(e.getMessage());
                            }
                        });


            }
        });

    }

    private void selectImage() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100 && resultCode==RESULT_OK && data!=null){
            imageUri=data.getData();
            imageView.setImageURI(imageUri);
        }
    }

    public void showToast(String message){
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }

    public void clearForm(){
        addressET.setText("");
        noteET.setText("");
        ch1.setChecked(false);
        ch2.setChecked(false);
        ch3.setChecked(false);
        ch4.setChecked(false);
        ch5.setChecked(false);
    }

    public String checkWaste(){
        String waste="";
        if(ch1.isChecked()){
            waste+="Computer,";
        }
        if(ch2.isChecked()){
            waste+="Television,";
        }
        if(ch3.isChecked()){
            waste+="Mobile Phones,";
        }
        if(ch4.isChecked()){
            waste+="Printer,";
        }
        if(ch5.isChecked()){
            waste+="Others,";
        }
        return waste;
    }
}