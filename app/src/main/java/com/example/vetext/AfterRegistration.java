package com.example.vetext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import hotchemi.android.rate.AppRate;

public class AfterRegistration extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    FirebaseAuth fauth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_registration);
        drawerLayout = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        fauth=FirebaseAuth.getInstance();
        navigationView = findViewById(R.id.navigationview);
        Menu nav_Menu = navigationView.getMenu();
        // make navigation option invisible
        nav_Menu.findItem(R.id.Vetdetails).setVisible(false);
        nav_Menu.findItem(R.id.Extensiondetails).setVisible(false);
        nav_Menu.findItem(R.id.publish).setVisible(false);
        nav_Menu.findItem(R.id.Schedule).setVisible(false);
        nav_Menu.findItem(R.id.call).setVisible(false);
        nav_Menu.findItem(R.id.sendmail).setVisible(false);



        //to enable clicking
        navigationView.bringToFront();

        navigationView.setNavigationItemSelectedListener(this);
        // initialize apening of the drawer
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        //make the fragment to be deafualt
        if(savedInstanceState==null) {
           getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,new HomeFragment()).commit();
        navigationView.setCheckedItem(R.id.home);
        }


    }
//if you back press it removes the navigation drawer
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);

        } else {
            super.onBackPressed();
        }



    }

    @Override
    //// on select of navigation option
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();
                navigationView.setCheckedItem(R.id.home);
            break;
            case R.id.share:
             Toast.makeText(this, "Share", Toast.LENGTH_LONG).show();
            try{
            Intent intent=new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT,"VETEXT");
            intent.putExtra(Intent.EXTRA_TEXT,"http://firebase.google.com/"+getApplicationContext());
            startActivity(Intent.createChooser(intent,"share with"));
            }catch (Exception e){
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }

            break;
            case R.id.rate:
            Toast.makeText(this, "rate this Doctor or officer", Toast.LENGTH_LONG).show();
             try {
            Uri uri=Uri.parse("https://play.goggle.com/store/apps/details?id="+getApplicationContext().getPackageName());
            Intent intent=new Intent(Intent.ACTION_VIEW,uri);
            startActivity(intent);

            }catch (Exception e){
                 Toast.makeText(this, "Error"+e.getMessage(), Toast.LENGTH_SHORT).show();
             }

            break;
            case R.id.search:
            Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();

            break;
            case R.id.famerdetails:
            Toast.makeText(this, "get farmers details", Toast.LENGTH_LONG).show();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AfterRegistrationfarmerdetailFrag()).commit();
            break;
            case R.id.book:
                Toast.makeText(this, "Book Vetinary doctor", Toast.LENGTH_LONG).show();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new AfterregistrationDoctorFrag()).commit();

            break;
            case R.id.bookofficer:
            Toast.makeText(this, "Book Extension Officer", Toast.LENGTH_LONG).show();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new AfterRegistrationbookofficerFrag()).commit();
            break;
            case R.id.Seekadvice:
            Toast.makeText(this, "seek advice", Toast.LENGTH_SHORT).show();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new SeekAdviceFragment()).commit();
            case R.id.logout:
                Toast.makeText(this, "signing out", Toast.LENGTH_SHORT).show();
                fauth.signOut();
                Intent logout= new Intent(this,MainActivity.class);
                startActivity(logout);
            break;



    }

    //close drawer on select of item
    drawerLayout.closeDrawer(GravityCompat.START);
    return true;
    }
}