package com.padaliya.phv1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    EditText username, fullname , email , password ;
    Button register ;
    TextView txt_login ;

    FirebaseAuth auth ;
    DatabaseReference refernces ;

    ProgressDialog pd ;

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Order","At the Register!");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.username) ;
        fullname = findViewById(R.id.fullname) ;
        email = findViewById(R.id.email);
        password = findViewById(R.id.password) ;
        register = findViewById(R.id.register) ;
        txt_login = findViewById(R.id.text_login) ;

        auth = FirebaseAuth.getInstance() ;


        txt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this , LoginActivity.class));
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String str_username = username.getText().toString() ;
                String str_fullname = fullname.getText().toString() ;
                String str_email = email.getText().toString() ;
                String str_password = password.getText().toString() ;

                if(TextUtils.isEmpty(str_username) ||
                        TextUtils.isEmpty(str_fullname) ||
                        TextUtils.isEmpty(str_email) ||
                        TextUtils.isEmpty(str_email))
                {
                    Toast.makeText(RegisterActivity.this, "All fields are compulsory",Toast.LENGTH_SHORT).show();
                }else if(str_password.length() < 6){
                    Toast.makeText(RegisterActivity.this, "Password must contain 6 characters",Toast.LENGTH_SHORT).show();
                } else if(str_username.length() < 3 || str_fullname.length() < 3 ){
                    Toast.makeText(RegisterActivity.this, "Username & Full name have more than 2 characters",Toast.LENGTH_SHORT).show();
                }else if(!validate(str_email)){
                    Toast.makeText(RegisterActivity.this, "Email should be in format abc@xyz.pqr",Toast.LENGTH_SHORT).show();


                }else {
                    pd = new ProgressDialog(RegisterActivity.this) ;
                    pd.setMessage("Please wait...");
                    pd.show();
                    register(str_username,str_fullname,str_email,str_password);
                }

            }



        });


    }

    public void register (final String usernname, final String fullname , String email , String password)
    {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                FirebaseUser firebaseUser = auth.getCurrentUser() ;

                                String userId = firebaseUser.getUid() ;

                                refernces = FirebaseDatabase.getInstance().getReference().child("Users").child(userId) ;

                                HashMap<String, Object> hashMap = new HashMap<>() ;
                                hashMap.put("id", userId) ;
                                hashMap.put("username", usernname.toLowerCase()) ;
                                hashMap.put("fullname",fullname) ;
                                hashMap.put("bio","");
                                hashMap.put("imageurl","https://firebasestorage.googleapis.com/v0/b/phv1-5af1e.appspot.com/o/Screen%20Shot%202020-04-22%20at%202.19.10%20AM.png?alt=media&token=efa4aeaf-ffd6-48db-b437-51a45955c9df");

                                refernces.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if(task.isSuccessful())
                                        {
                                            pd.dismiss();
                                            Toast.makeText(RegisterActivity.this, "Successful registration, please log in. ", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(RegisterActivity.this , LoginActivity.class) ;
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                        }else {
                                            pd.dismiss();
                                            Toast.makeText(RegisterActivity.this, "You can't register with this email or password. ", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }
                );
    }

    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }
}
