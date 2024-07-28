package com.example.doanmh.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.doanmh.R;
import com.example.doanmh.model.Clubs;

import java.util.List;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.myViewHolder> {
    private List<Clubs> lstClubs;
    public MyAdapter2(List <Clubs> lstClubs) {
        this.lstClubs = lstClubs;


    }
    @NonNull
    @Override
    public MyAdapter2.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item2, parent, false);
        return new MyAdapter2.myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter2.myViewHolder holder, int position) {
        Clubs clubs = lstClubs.get(position);
        holder.tvStt.setText((clubs.getStt()));
        holder.tvName.setText(clubs.getName());
        holder.tvPl.setText((clubs.getPl()));
        holder.tvGd.setText((clubs.getGd()));
        holder.tvPts.setText((clubs.getPts()));
        Glide.with(holder.imLogo.getContext())
                .load(clubs.getSurl())
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.imLogo);
    }

    @Override
    public int getItemCount() {
        if (lstClubs != null){
            return lstClubs.size();
        }
        return 0;
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        ImageView imLogo;
        TextView tvStt, tvName, tvPl, tvGd, tvPts;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            imLogo = itemView.findViewById(R.id.imLogo);
            tvStt = itemView.findViewById(R.id.tvSTT);
            tvName = itemView.findViewById(R.id.tvClub);
            tvPl = itemView.findViewById(R.id.tvPL);
            tvGd = itemView.findViewById(R.id.tvGD);
            tvPts = itemView.findViewById(R.id.tvPts);
        }
    }
}
