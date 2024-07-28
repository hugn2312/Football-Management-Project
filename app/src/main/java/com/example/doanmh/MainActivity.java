package com.example.doanmh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.example.doanmh.fragment.FantasyFragment;
import com.example.doanmh.fragment.LastestFragment;
import com.example.doanmh.fragment.MoreFragment;
import com.example.doanmh.fragment.PLFragment;
import com.example.doanmh.fragment.StatsFragment;
import com.example.doanmh.fragment.ViewPagerAdapter;
import com.example.doanmh.ui.LogInActivity;
import com.example.doanmh.ui.ProfileActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ViewPager viewPager;
    private ImageView imAvatar;
    private TextView tvNameUser, tvGmail;
    private NavigationView navigationView;

    private static  final  int FRAGMENT_LATEST = 0;
    private static  final  int FRAGMENT_PL = 1;
    private static  final  int FRAGMENT_FANTASY = 2;
    private static  final  int FRAGMENT_STATS = 3;
    private static  final  int FRAGMENT_MORE = 4;
    private int CurrentFragment = FRAGMENT_LATEST;
    private DrawerLayout drawerLayout;
    FirebaseAuth auth;
    private static final String TAG = "PROFILE_TAG";
    private BottomNavigationView bottomNavigationView;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawer_layout);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        auth = FirebaseAuth.getInstance();
        viewPager = findViewById(R.id.view_pager);
        anhxa();

        // ViewPager
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position)
                {
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.bottom_latest).setChecked(true);
                        navigationView.getMenu().findItem(R.id.nav_latest).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.bottom_pl).setChecked(true);
                        navigationView.getMenu().findItem(R.id.nav_pl).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.bottom_fantasy).setChecked(true);
                        navigationView.getMenu().findItem(R.id.nav_fantasy).setChecked(true);
                        break;
                    case 3:
                        bottomNavigationView.getMenu().findItem(R.id.bottom_stats).setChecked(true);
                        navigationView.getMenu().findItem(R.id.nav_stats).setChecked(true);
                        break;
                    case 4:
                        bottomNavigationView.getMenu().findItem(R.id.bottom_more).setChecked(true);
                        navigationView.getMenu().findItem(R.id.nav_more).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Mo app lan dau tien vao trang home , set chon home
        replaceFragment(new LastestFragment());
        navigationView.getMenu().findItem(R.id.nav_latest).setChecked(true);
        bottomNavigationView.getMenu().findItem((R.id.bottom_latest)).setChecked(true);
        if (auth.getCurrentUser() == null){
            return ;
        }else {
            UserInfo();
        }


        // bottom menu
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.bottom_latest){
                    viewPager.setCurrentItem(0);
                    openLatestFragment();
                    navigationView.getMenu().findItem(R.id.nav_latest).setChecked(true);
                }else if(id == R.id.bottom_pl){
                    viewPager.setCurrentItem(1);
                    openPLFragment();
                    navigationView.getMenu().findItem(R.id.nav_pl).setChecked(true);
                }else if(id == R.id.bottom_fantasy) {
                    viewPager.setCurrentItem(2);
                    openFantasyFragment();
                    navigationView.getMenu().findItem(R.id.nav_fantasy).setChecked(true);
                }
                else if(id == R.id.bottom_stats) {
                    viewPager.setCurrentItem(3);
                    openStatsFragment();
                    navigationView.getMenu().findItem(R.id.nav_stats).setChecked(true);
                }
                else if(id == R.id.bottom_more) {
                    viewPager.setCurrentItem(4);
                    openMoreFragment();
                    navigationView.getMenu().findItem(R.id.nav_more).setChecked(true);
                }
                return true;
            }
        });
    }
    private void anhxa(){
        navigationView = findViewById(R.id.navigation_view);
        tvNameUser = navigationView.getHeaderView(0).findViewById(R.id.tvNameUser);
        tvGmail = navigationView.getHeaderView(0).findViewById(R.id.tvGmail);
    }

    // Drawer
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.nav_latest){
            viewPager.setCurrentItem(0);
            openLatestFragment();
            bottomNavigationView.getMenu().findItem(R.id.bottom_latest).setChecked(true);
        }else if(id == R.id.nav_pl) {
            viewPager.setCurrentItem(1);
            openPLFragment();
            bottomNavigationView.getMenu().findItem(R.id.bottom_pl).setChecked(true);
        }
        else if(id == R.id.nav_fantasy) {
            viewPager.setCurrentItem(2);
            openFantasyFragment();
            bottomNavigationView.getMenu().findItem(R.id.bottom_fantasy).setChecked(true);
        }
        else if(id == R.id.nav_stats) {
            viewPager.setCurrentItem(3);
            openStatsFragment();
            bottomNavigationView.getMenu().findItem(R.id.bottom_stats).setChecked(true);
        }
        else if(id == R.id.nav_more) {
            viewPager.setCurrentItem(4);
            openMoreFragment();
            bottomNavigationView.getMenu().findItem(R.id.bottom_more).setChecked(true);
        }
        else if (id == R.id.nav_signout){
            FirebaseAuth.getInstance().signOut();
            Intent i = new Intent(this, LogInActivity.class);
            startActivity(i);
            finish();
        }
        else if (id == R.id.nav_myprofile){
            Intent i = new Intent(this, ProfileActivity.class);
            startActivity(i);
            finish();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void openLatestFragment(){
        if (CurrentFragment != FRAGMENT_LATEST) {
            replaceFragment(new LastestFragment());
            CurrentFragment = FRAGMENT_LATEST;
        }
    }

    private void openPLFragment()
    {
        if (CurrentFragment != FRAGMENT_PL) {
            replaceFragment(new PLFragment());
            CurrentFragment = FRAGMENT_PL;
        }
    }

    private void openFantasyFragment()
    {
        if (CurrentFragment != FRAGMENT_FANTASY) {
            replaceFragment(new FantasyFragment());
            CurrentFragment = FRAGMENT_FANTASY;
        }
    }

    private void openStatsFragment()
    {
        if (CurrentFragment != FRAGMENT_STATS) {
            replaceFragment(new StatsFragment());
            CurrentFragment = FRAGMENT_STATS;
        }
    }

    private void openMoreFragment()
    {
        if (CurrentFragment != FRAGMENT_MORE) {
            replaceFragment(new MoreFragment());
            CurrentFragment = FRAGMENT_MORE;
        }
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    private void replaceFragment(Fragment fragment)
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();
    }
    private void UserInfo(){
        Log.d(TAG, "loadUserInfo: Loading user info of user"+ auth.getUid());
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(auth.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String name = ""+snapshot.child("name").getValue();
                        String email = ""+snapshot.child("email").getValue();
                        tvNameUser.setText(name);
                        tvGmail.setText(email);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}