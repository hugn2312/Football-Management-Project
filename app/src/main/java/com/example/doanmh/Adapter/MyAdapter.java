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
//import com.example.doanmh.ui.ImportTablesActivity;
import com.example.doanmh.model.Clubs;
import com.example.doanmh.ui.TablesAdmin;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter <MyAdapter.MyViewHolder>{
    private List<Clubs> lstClubs;
    private IClickListener iClickListener;
    private TablesAdmin tablesActivity;
    FirebaseFirestore firestore;
    boolean valid = true;
    private DocumentSnapshot documentSnapshot;
    private DatabaseReference databaseReference;
    private DocumentReference reference;
    private AuthResult authResult;
    private String uid;


    public interface IClickListener{
        void onCLickUpdateItem(Clubs clubs);
        void OnClickDeleteItem(Clubs clubs);
    }

    public MyAdapter(List <Clubs> lstClubs,IClickListener listener) {
        this.lstClubs = lstClubs;
        this.iClickListener = listener;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bxh_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
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


            holder.ivEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iClickListener.onCLickUpdateItem(clubs);
                }
            });


            holder.ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iClickListener.OnClickDeleteItem(clubs);
                }
            });

    }

//    private boolean checkUserAccessLevel(String uid) {
//        reference = firestore.collection("Users").document(uid);
//        //
//        reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                Log.d("TAG","onSuccess: " + documentSnapshot.getData() );
//                //
//
//                if(documentSnapshot.getString("isAdmin") != null){
//                    valid = true;
//                }
//                if (documentSnapshot.getString("isUser") != null){
//                    valid = false;
//                }
//            }
//        });
//        return valid;
//    }

//    private boolean checkUserAccessLevel(String uid,MyViewHolder holder) {
//        firestore = FirebaseFirestore.getInstance();
//        reference = firestore.collection("Users").document(uid);
//        reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                Log.d("TAG","onSuccess: " + documentSnapshot.getData() );
//                //
//
//                if(documentSnapshot.getString("isAdmin") != null){
//                    valid = true;
//                }
//                if (documentSnapshot.getString("isUser") != null){
//                    valid = false;
//                }
//            }
//        });
//        return valid;
//    }

    @Override
    public int getItemCount() {
        if (lstClubs != null){
            return lstClubs.size();
        }
        return 0;
    }
//    public void GetIDUser(String uid){
//        reference = firestore.collection("Users").document(uid);
//        reference.get().get
//
//    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivEdit, ivDelete;
        ImageView imLogo;
        TextView tvStt, tvName, tvPl, tvGd, tvPts;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imLogo = itemView.findViewById(R.id.imLogo);
            tvStt = itemView.findViewById(R.id.tvSTT);
            tvName = itemView.findViewById(R.id.tvClub);
            tvPl = itemView.findViewById(R.id.tvPL);
            tvGd = itemView.findViewById(R.id.tvGD);
            tvPts = itemView.findViewById(R.id.tvPts);
            ivEdit = itemView.findViewById(R.id.ivEdit);
            ivDelete = itemView.findViewById(R.id.ivDelete);
        }
    }
}
