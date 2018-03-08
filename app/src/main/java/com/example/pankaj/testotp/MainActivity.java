
package com.example.pankaj.testotp;
        import android.content.Context;
        import android.content.Intent;
        import android.os.Bundle;
        import android.support.annotation.NonNull;
        import android.support.annotation.Nullable;
        import android.support.design.widget.Snackbar;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.bumptech.glide.Glide;
        import com.firebase.ui.auth.AuthUI;
        import com.firebase.ui.auth.ErrorCodes;
        import com.firebase.ui.auth.IdpResponse;
        import com.firebase.ui.auth.ResultCodes;
        import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
        import com.firebase.ui.firestore.FirestoreRecyclerOptions;
        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.database.ChildEventListener;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.firestore.DocumentChange;
        import com.google.firebase.firestore.DocumentReference;
        import com.google.firebase.firestore.DocumentSnapshot;
        import com.google.firebase.firestore.EventListener;
        import com.google.firebase.firestore.FirebaseFirestore;
        import com.google.firebase.firestore.FirebaseFirestoreException;
        import com.google.firebase.firestore.Query;
        import com.google.firebase.firestore.QuerySnapshot;
        import com.google.firebase.storage.FirebaseStorage;
        import com.google.firebase.storage.StorageReference;


        import org.w3c.dom.Document;

        import java.lang.reflect.Array;
        import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 123;
    private StorageReference storageReference;
    private FirebaseFirestore firestore;
    private RecyclerView rcv;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference fireRef;

    LinearLayoutManager linearLayoutManager;
    private FirestoreRecyclerAdapter adapter;
    List<Crop> cropsList;
    private CropsListAdapter cropsListAdapter;
    public static Context mcontext;
    DocumentReference d;
    public static Context getContext()
    {
        return mcontext;

    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cropsList=new ArrayList<>();
        cropsListAdapter = new CropsListAdapter(cropsList);
        mcontext=getApplicationContext();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        firestore = FirebaseFirestore.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        rcv =findViewById(R.id.rcv);
        rcv.setHasFixedSize(true);
        rcv.setLayoutManager(new LinearLayoutManager(this));
        rcv.setAdapter(cropsListAdapter);
        fireRef=firebaseDatabase.getReference();



        if (auth.getCurrentUser() != null) {
            // already signed in

            Toast.makeText(getApplicationContext(), "Logged in", Toast.LENGTH_LONG).show();
            // TVAC Code
            firestore.collection("Location")
                    .document("Gujrat")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>(){
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document != null && document.exists()) {
                                    DocumentReference d=document.getReference();
                                    d.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful()) {
                                                DocumentSnapshot document = task.getResult();
                                                if (document != null && document.exists()) {
                                                    List<String> a=(ArrayList)document.getData().get(ArrayList.class);
                                                    Toast.makeText(MainActivity.this,a.get(0), Toast.LENGTH_SHORT).show();
                                                    Log.d("Last", "DocumentSnapshot data: " + document.getData().get(ArrayList.class));
                                                } else {
                                                    Log.d("Last", "No such document");
                                                }
                                            } else {
                                                Log.d("Last", "get failed with ", task.getException());
                                            }
                                        }
                                    });
                                    Toast.makeText(MainActivity.this, d.toString(), Toast.LENGTH_SHORT).show();
                                    Log.d("Pan", "DocumentSnapshot data: " + document.getData());
                                } else {
                                    Log.d("TAG", "No such document");
                                }
                            } else {
                                Log.d("TAG", "get failed with ", task.getException());
                            }
                        }
                    });
             firestore.collection("Crop_info").addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                    if(e!=null){
                        Log.d("Erroe",e.toString());
                    }
                    else

                    {
                        for(DocumentChange doc: documentSnapshots.getDocumentChanges()){
                            if(doc.getType()==DocumentChange.Type.ADDED)
                            {
                                Crop crop = doc.getDocument().toObject(Crop.class);
                                cropsList.add(crop);
                                cropsListAdapter.notifyDataSetChanged();
                                String title =doc.getDocument().getString("Title");

                            }
                        }
                    }
                }
            });
            /*DocumentReference docRef = firestore.collection("Location").document("Mumbai");
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {

                        DocumentSnapshot document = task.getResult();
                        if (document != null && document.exists()) {

                            Log.d("Panks", "DocumentSnapshot data: " + document.getData());
                        } else {
                            Log.d("TAG", "No such document");
                        }
                    } else {
                        Log.d("TAG", "get failed with ", task.getException());
                    }
                }
            });*/
            // //  Realtime database code
                        /*fireRef.addChildEventListener(new ChildEventListener() {


                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            String var = dataSnapshot.getValue(String.class);
                            Toast.makeText(MainActivity.this,var, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });*/


        }else {
            // not signed in
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(
                                    Arrays.asList(
                                            new AuthUI.IdpConfig.Builder(AuthUI.PHONE_VERIFICATION_PROVIDER).build()
                                    ))
                            .build(),
                    RC_SIGN_IN);
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            // Successfully signed in
            if (resultCode == ResultCodes.OK) {
                Toast.makeText(MainActivity.this,"Logged in",Toast.LENGTH_LONG);

                firestore.collection("Crop_info").addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                        if(e!=null){
                            Log.d("Erroe",e.toString());
                        }
                        else
                        {
                            for(DocumentChange doc: documentSnapshots.getDocumentChanges()){
                                if(doc.getType()==DocumentChange.Type.ADDED)
                                {
                                    Crop crop = doc.getDocument().toObject(Crop.class);
                                    cropsList.add(crop);
                                    cropsListAdapter.notifyDataSetChanged();
                                    String title =doc.getDocument().getString("Title");
                                    Log.d("Title",title);
                                }
                            }
                        }
                    }
                });


            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    Log.e("Login","Login canceled by User");
                    return;
                }
                if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
                    Log.e("Login","No Internet Connection");
                    return;
                }
                if (response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    Log.e("Login","Unknown Error");
                    return;
                }
            }
            Log.e("Login","Unknown sign in response");
        }
    }
    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }
}