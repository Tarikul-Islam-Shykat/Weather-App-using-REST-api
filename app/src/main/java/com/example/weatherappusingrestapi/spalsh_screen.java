package com.example.weatherappusingrestapi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;

public class spalsh_screen extends AppCompatActivity {
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        setContentView(R.layout.activity_spalsh_screen);

        dialog = new Dialog(this);

        if (!CheckNetwork.isInternetAvailable(this)) //returns true if internet available
        {
            // checkInternetEntering();
            alert_dialog();
            setContentView(R.layout.back);
        } else {
            int secondsDelayed = 1;
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    startActivity(new Intent(spalsh_screen.this, MainActivity.class));
                    finish();
                }
            }, secondsDelayed * 1700);
        }


    }

    private void alert_dialog() {
        dialog.setContentView(R.layout.check_internet_alert);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageView image = dialog.findViewById(R.id.image_error);
        Button btn_finish = dialog.findViewById(R.id.btn_error);

        dialog.setOnDismissListener( new DialogInterface.OnDismissListener() // this will finish the app if the user click on any other area rather than dialog
        {
            @Override
            public void onDismiss(DialogInterface dialog)
            {
                finish();
            }
        });

        dialog.show();
        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /*void checkInternetEntering()
    {
        //if there is no internet do this
        setContentView(R.layout.activity_main);

        new AlertDialog.Builder(this)
                .setIcon(R.mipmap.ic_launcher_round)//alert the person knowing they are about to close
                .setTitle("404 Error")
                .setMessage("Please Check you're Mobile data or Wifi network.")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .show();
    }
}*/
    static class CheckNetwork {

        private static final String TAG = CheckNetwork.class.getSimpleName();

        public static boolean isInternetAvailable(Context context) {
            NetworkInfo info = (NetworkInfo) ((ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();

            if (info == null) {
                Log.d(TAG, "no internet connection");
                return false;
            } else {
                if (info.isConnected()) {
                    Log.d(TAG, " internet connection available...");
                    return true;
                } else {
                    Log.d(TAG, " internet connection");
                    return true;
                }

            }
        }
    }
}