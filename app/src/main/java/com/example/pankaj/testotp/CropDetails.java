package com.example.pankaj.testotp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class CropDetails extends AppCompatActivity {
    private TextView title;
    private TextView Description;
    private ImageView imageView;
    private TextView counter;
    private TextView time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_details);
        title = findViewById(R.id.crop_title);
        Description = findViewById(R.id.crop_description);
        imageView = findViewById(R.id.crop_image);
        counter=findViewById(R.id.crop_counter);
        time=findViewById(R.id.crop_time);
        Intent intent = getIntent();
        Crop c = (Crop) intent.getSerializableExtra("obj");
        title.setText(c.getTitle());
        counter.setText(""+c.getCounter()+" are planting");
        time.setText(""+c.getTime()+" Month/s is time required");
        Description.setText(c.getDescription_long());
        Glide.with(this)
                .load(c.getImage())
                .into(imageView);

    }
}
