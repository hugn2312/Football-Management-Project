package com.example.doanmh.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.doanmh.MyApplication;
import com.example.doanmh.R;
import com.example.doanmh.model.FavCLubs;
import com.example.doanmh.model.Team;
import com.example.doanmh.ui.DetailActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.FavViewHolder> {
    private Context context;
    private ArrayList<Team> lstTeam;
    private static final String TAG = "FAV_CLUB_TAG";

    public FavAdapter(Context context, ArrayList<Team> lstTeam ) {
        this.context = context;
        this.lstTeam = lstTeam;
    }

    @NonNull
    @Override
    public FavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fav_clubs, parent, false);
        return new FavAdapter.FavViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavViewHolder holder, int position) {
        Team team = lstTeam.get(position);
        loadClubDetail(team,holder);

        holder.nameFav.setText(team.getName());
        holder.detailFav.setText(team.getDetail());
        Glide.with(holder.imView.getContext())
                .load(team.getSurl())
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.imView);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("name", team.getName());
                context.startActivity(intent);
            }
        });
        holder.Favbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyApplication.RemoveFromFavList(context, team.getName());

            }
        });

    }

    private void loadClubDetail(Team team, FavViewHolder holder) {
        String nameClub = team.getName();
        Log.d(TAG,"loadClubDetail: CLub detail of name club "+nameClub);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Teams");
        ref.child(nameClub)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String name = ""+snapshot.child("name").getValue();
                        String detail = ""+snapshot.child("detail").getValue();
                        String surl = ""+snapshot.child("surl").getValue();

                        team.setFavourite(true);
                        team.setName(name);
                        team.setDetail(detail);
                        team.setSurl(surl);

                        holder.nameFav.setText(name);
                        holder.detailFav.setText(detail);
                        Glide.with(holder.imView.getContext())
                                .load(surl)
                                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                                .circleCrop()
                                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal)
                                .into(holder.imView);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    @Override
    public int getItemCount() {
        if (lstTeam != null){
            return lstTeam.size();
        }
        return 0;
    }

    class FavViewHolder extends RecyclerView.ViewHolder {
        CardView layout;
        CircleImageView imView;
        TextView nameFav,detailFav;
        ImageButton Favbtn;
        public FavViewHolder(@NonNull View itemView) {
            super(itemView);
            imView = itemView.findViewById(R.id.imgFav);
            nameFav = itemView.findViewById(R.id.nameFav);
            Favbtn = itemView.findViewById(R.id.Favbtn);
            layout = itemView.findViewById(R.id.lay_fav);
            detailFav = itemView.findViewById(R.id.detailFav);

        }
    }
}
