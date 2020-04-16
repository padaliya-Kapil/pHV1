package com.padaliya.phv1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    Button login , register ;
    FirebaseUser firebaseUser ;

@Override
protected  void  onStart()
{
    super.onStart();

    Log.d("Main","Main 24");
    firebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

    if(firebaseUser!=null)
    {

        Log.d("Order","Goin to  Home!");
        startActivity(new Intent(MainActivity.this , HomeActivity.class));
        finish();
    }

}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Order","At the Main!");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.login);
        register = findViewById(R.id.register);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
            }
        });
    }
}
