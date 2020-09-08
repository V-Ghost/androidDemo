package com.example.bryan.stork;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.firebase.auth.FirebaseAuth;

public class Home_screen extends AppCompatActivity {

    private BottomNavigationView mainNav ;
    private FrameLayout mainFrame ;
    private home homefrag;
   ;


    private int items = 0;



    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        homefrag = new home();




        mainNav = (BottomNavigationView)findViewById(R.id.nav);
        mainFrame=(FrameLayout)findViewById(R.id.frame);

        setFragment(homefrag);
        mainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                                                        @Override
                                                        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                                                            switch(item.getItemId()){
                                                                case R.id.nav_home:
                                                                    setFragment(homefrag);

                                                                    return true;
                                                                case R.id.nav_air:
                                                                    startActivity(new Intent(Home_screen.this, Option.class));
                                                                    return true;




                                                                default:
                                                                    return false;
                                                            }



                                                        }
                                                    }
        );


    }



    public  void setFragment(Fragment f){
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frame,f);
        ft.commit();


    }

    public void logout(View view){

        Intent i=new Intent(Home_screen.this,Loginup.class);

        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity( i );
        FirebaseAuth.getInstance().signOut();



        finish();


    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        if (requestCode == homefrag.MY_PERMISSIONS_REQUEST_LOCATION){
            homefrag.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }


        else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}



