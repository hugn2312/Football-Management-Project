package com.example.doanmh.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;

import com.example.doanmh.Adapter.MyAdapter2;
import com.example.doanmh.R;
import com.example.doanmh.model.Clubs;
import com.example.doanmh.news.NewsActivity;
import com.example.doanmh.ui.FixturesActivity;
import com.example.doanmh.ui.TablesAdmin;
import com.example.doanmh.ui.TablesUser;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LastestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LastestFragment extends Fragment{
    Button btJesus;
    Button bt_FullTB;
    Button btAll_Fx;
    private FirebaseAuth auth;
    private RecyclerView recyclerView;
    private MyAdapter2 adapter2;
    private List<Clubs> lstClubs;
    private FirebaseDatabase database;
    private DatabaseReference reference;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ScrollView scrollView;
    public static final int SCROLL_DELTA = 15;
    public LastestFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LastestFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LastestFragment newInstance(String param1, String param2) {
        LastestFragment fragment = new LastestFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         //return inflater.inflate(R.layout.fragment_lastest, container, false);
          View view = inflater.inflate(R.layout.fragment_lastest,container,false);
         recyclerView = view.findViewById(R.id.rvtable);

         recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

         lstClubs = new ArrayList<>();
         adapter2 = new MyAdapter2(lstClubs);
         database = FirebaseDatabase.getInstance();
         reference = database.getReference("Club");
         recyclerView.setAdapter(adapter2);
         getListClubsFromRealtimeDatabase();
          btJesus = view.findViewById(R.id.btJesus);
          btAll_Fx = view.findViewById(R.id.btAll_Fx);
          bt_FullTB = view.findViewById(R.id.btAlltable);
          btJesus.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  Intent intent = new Intent(view.getContext(), NewsActivity.class);
                  startActivity(intent);
              }
          });
        btAll_Fx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), FixturesActivity.class);
                startActivity(intent);
            }
        });
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser()==null){
            bt_FullTB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(view.getContext(), TablesUser.class));
                }
            });
            return view;
        }
        else {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
            reference.child(auth.getUid()).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {
                    String role = ""+dataSnapshot.child("Role").getValue();
                    if (role == "")
                    {
                        bt_FullTB.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent(view.getContext(), TablesUser.class));
                            }
                        });


                    }else {
                        bt_FullTB.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent(view.getContext(), TablesAdmin.class));
                            }
                        });
                    }
                }
            });
        }
          return view;
    }

    private void getListClubsFromRealtimeDatabase() {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Club");
        Query query = reference.orderByChild("pts");
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Clubs clubs = snapshot.getValue(Clubs.class);
                if (clubs != null){
                    lstClubs.add(0, clubs);
                    adapter2.notifyDataSetChanged();
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}