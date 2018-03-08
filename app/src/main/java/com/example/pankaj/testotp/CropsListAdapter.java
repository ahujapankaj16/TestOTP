package com.example.pankaj.testotp;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

/**
 * Created by PANKAJ on 3/5/2018.
 */

public class CropsListAdapter extends RecyclerView.Adapter<CropsListAdapter.ViewHolder> {
    public List<Crop> cropsList;
    public CropsListAdapter(List<Crop> cropsList)

    {
        this.cropsList=cropsList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.title.setText(cropsList.get(position).getTitle());
            holder.description.setText(cropsList.get(position).getDescription_short());
            String s= cropsList.get(position).getImage();

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                // item clicked
                Intent intent = new Intent(MainActivity.getContext(),CropDetails.class);
                //intent.putExtra("title",cropsList.get(position).getTitle());
                intent.putExtra("obj",cropsList.get(position));

                MainActivity.getContext().startActivity(intent);

               // Toast.makeText(MainActivity.getContext(),"Clicked",Toast.LENGTH_LONG).show();


            }
        });
        Glide.with(MainActivity.getContext())
                .load(s)
                .into(holder.image);



    }

    @Override
    public int getItemCount() {
        return cropsList.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        View mview;
        public TextView title;
        public TextView description;
        public ImageView image;
        public CardView cv;
        public ViewHolder(View itemView)
        {
            super(itemView);
            mview=itemView;
            title=(TextView)mview.findViewById(R.id.Title);
            description=(TextView)mview.findViewById(R.id.Description);

            image=(ImageView) mview.findViewById(R.id.image);
            cv=(CardView)mview.findViewById(R.id.cv);
            /*cv.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    // item clicked
                    Toast.makeText(MainActivity.getContext(),"Clicked",Toast.LENGTH_LONG);

                }
            });*/
        }

        }
    }

