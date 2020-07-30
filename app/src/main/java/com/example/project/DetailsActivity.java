package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firestore.v1.Value;
import androidx.appcompat.app.AppCompatActivity;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.opencensus.tags.Tag;

//this class is designed to display the detail of a specific friend
public class DetailsActivity extends AppCompatActivity {
    TextView name,u_age,u_email,u_profession;
    ArrayList<String> f_list=new ArrayList<String>();


    private Button dele;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    String n, profession="", email="";
    Long age;
    private  CollectionReference noteRef;
    private static final String TAG = MainActivity.class.getSimpleName();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Friend Personal Info");
        setContentView(R.layout.activity_details);
        name = findViewById(R.id.u_name);

        dele=findViewById(R.id.button2);
        u_age = findViewById(R.id.u_age);
        u_profession= findViewById(R.id.u_profession);
        u_email = findViewById(R.id.u_email);

        Intent intent = getIntent();

        final String user_name = intent.getStringExtra("User_Name");
        final int  a=intent.getIntExtra("number",0);
        f_list=intent.getStringArrayListExtra("list");

        n = getIntent().getStringExtra("name");
        name.setText("Name: " + n);

        final DocumentReference noteRef= db.collection("Users").document(n);
        //Query the database according the name of the friend
        noteRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        //get email address
                        email=(String) documentSnapshot.getData().get("emailAddress");
                        u_email.setText("Email address: "+email);

                        //get profession
                        profession=(String) documentSnapshot.getData().get("type");
                        u_profession.setText("Profession: "+profession);

                        //get age
                        age=(Long) documentSnapshot.getData().get("age");
                        u_age.setText("Age:"+age);
                    }
                });


        dele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get request

//DocumentReference noteRef1=db.collection("Users").document(user_name);

//                noteRef1.get()
//                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                            @Override
//                            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                                //check the value of friend_list_from
//                                f_list = (ArrayList<String>) documentSnapshot.get("friend_list");
//                                Toast.makeText(DetailsActivity.this, "successfully delete"+f_list.get(0)
//                                        , Toast.LENGTH_SHORT).show();
//                            }});
//
//                 //get index from search name
//                      int a=  f_list.indexOf(n);
//
               final DocumentReference update=FirebaseFirestore.getInstance().collection("Users").document(user_name);
//

                Map<String, Object> map = new HashMap<>();
                // remove the one
                map.put("friend_list", FieldValue.arrayRemove(f_list.get(a)));
//
                update.update(map)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(DetailsActivity.this, "successfully delete"
                                        , Toast.LENGTH_SHORT).show();
//                                final Intent cc=new Intent(DetailsActivity.this, DetailsActivity.class);
//                                startActivity(cc);
                            }
                        })


                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(DetailsActivity.this, "Fail"
                                        , Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });

}
        }