package com.example.vehicles_booking;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private static int SPLASH_TIME = 2000;
    DBconnection dBconnection;

   ImageView imageView;
   Animation LeftToRight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isConnected(MainActivity.this))buildDialog(MainActivity.this).show();
        else {
            setContentView(R.layout.activity_main);

            dBconnection=new DBconnection(this);
            SQLiteDatabase sqLiteDatabase=dBconnection.getWritableDatabase();

            imageView=findViewById(R.id.imageMain);
            LeftToRight= AnimationUtils.loadAnimation(this,R.anim.left_to_right);
            imageView.setAnimation(LeftToRight);
            new Handler().postDelayed(new Runnable() {

                public void run() {

                    Intent mySuperIntent = new Intent(MainActivity.this, AdminSearchPageActivity.class);
                    startActivity(mySuperIntent);

                    finish();
                }
            }, SPLASH_TIME);

        }
    }

    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting())) return true;
            else return false;
        } else
            return false;
    }

    public AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet Connection");
        builder.setMessage("You need to have Mobile Data or WiFi to access this APP . Press ok to Exit");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();
            }
        });

        return builder;
    }
}
