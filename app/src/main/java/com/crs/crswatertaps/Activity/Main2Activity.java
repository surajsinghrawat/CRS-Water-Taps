package com.crs.crswatertaps.Activity;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.crs.crswatertaps.CommonAction.PermissionCheck;
import com.crs.crswatertaps.Fragment.CartFragment;
import com.crs.crswatertaps.Fragment.CategoryFragment;
import com.crs.crswatertaps.Fragment.MoreOptionFragment;
import com.crs.crswatertaps.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;

public class Main2Activity extends AppCompatActivity {
    //private TextView mTextMessage;


    private TextView title;
    private int current_position=0;
    private int[] images={R.drawable.crs,R.drawable.crs_taps,R.drawable.t_taps,
                        R.drawable.taps,R.drawable.w_taps,R.drawable.water};
    private static final String url="http://dummy.restapicrs.com/api/v1/employees";

    private RecyclerView programingList;
    private RecyclerView.LayoutManager layoutManager;
    String userID;
    FirebaseAuth mAuth;

    private static int bottomTabIndexNo;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    changeFragment(0);
                    bottomTabIndexNo = 0;
                    setupActionBar(getNavTitle(bottomTabIndexNo));
                    return true;

                case R.id.navigation_cart:
      //              mTextMessage.setText(R.string.title_Cart);
                    changeFragment(1);
                    bottomTabIndexNo = 1;
                    setupActionBar(getNavTitle(bottomTabIndexNo));
                    return true;
                case R.id.navigation_more:
        //            mTextMessage.setText(R.string.title_Checkout);
                    changeFragment(2);
                    bottomTabIndexNo=2;
                    setupActionBar(getNavTitle(bottomTabIndexNo));
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        if (!PermissionCheck.checkPermission(this)){
            PermissionCheck.requestPermission(this);
        }

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);



        bottomTabIndexNo = 0;
        changeFragment(bottomTabIndexNo);
        setupActionBar(getNavTitle(bottomTabIndexNo));

//api

        StringRequest stringRequest=new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("test bdata",response);

            }
            }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

    });
        RequestQueue queue= Volley.newRequestQueue(this);
        queue.add(stringRequest);

    }


    private void changeFragment(int position) {

        Fragment newFragment = null;

        if (position == 0) {
            newFragment = new CategoryFragment();
        } else if (position == 1) {
            newFragment = new CartFragment();
        }
        else if(position==2){
            newFragment = new MoreOptionFragment();
       }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, newFragment);
        fragmentTransaction.commit();

    }


    private String getNavTitle(int index) {
        switch (index) {
            case 0:
                return "Model";
            case 1:
                return "Cart";
            case 2:
                return "More Option";

            default:
                return "Not Available";
        }
    }

    private void setupActionBar(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

}
