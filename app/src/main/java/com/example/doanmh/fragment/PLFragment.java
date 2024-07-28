package com.example.doanmh.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.doanmh.MainActivity;
import com.example.doanmh.R;
import com.example.doanmh.model.Clubs;
import com.example.doanmh.ui.FixturesActivity;
import com.example.doanmh.ui.TablesAdmin;
import com.example.doanmh.ui.TablesUser;
import com.example.doanmh.ui.TeamActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PLFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PLFragment extends Fragment {
    Button btTables;
    Button btFixtures, btSearch;
    boolean valid = true;
    String uid  ;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private AuthResult authResult;
    private FirebaseFirestore firestore;

    private FirebaseAuth auth;
    private CollectionReference users;


    public PLFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PLFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PLFragment newInstance(String param1, String param2) {
        PLFragment fragment = new PLFragment();
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
//        Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_p_l, container, false);
        View   view = inflater.inflate(R.layout.fragment_p_l,container,false);
        btSearch = view.findViewById(R.id.btSearch);
        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), TeamActivity.class));
            }
        });
        btTables = view.findViewById(R.id.btTables);
        btFixtures = view.findViewById(R.id.btFixtures);

        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser()==null){
            btTables.setOnClickListener(new View.OnClickListener() {
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
                        btTables.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent(view.getContext(), TablesUser.class));
                            }
                        });
                    }else {
                        btTables.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent(view.getContext(), TablesAdmin.class));
                            }
                        });
                    }
                }
            });
        }
        btFixtures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), FixturesActivity.class);
                startActivity(i);
            }
        });
        return view;
    }
}