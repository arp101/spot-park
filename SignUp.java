package com.example.arpit.spotpark;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    Button btnSignup;
    TextView btnLogin, btnForgotPass;
    EditText input_email, input_pass;
    RelativeLayout activity_sign_up;
    //
    EditText editTextUsername;
    EditText editTextname;
    EditText editTextpt;

    private FirebaseAuth auth;
    Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //View
        btnSignup= (Button)findViewById(R.id.signup_btn_register);
        btnLogin=(TextView)findViewById(R.id.signup_btn_login);
        btnForgotPass=(TextView)findViewById(R.id.signup_btn_forgot_pass);
        input_email=(EditText)findViewById(R.id.signup_email);
        input_pass=(EditText)findViewById(R.id.signup_password);
        activity_sign_up=(RelativeLayout)findViewById(R.id.activity_sign_up);

        //
        editTextUsername = (EditText) findViewById(R.id.signup_email);
        editTextname = (EditText) findViewById(R.id.signup_name);
        editTextpt=(EditText)findViewById(R.id.signup_pt) ;

        Firebase.setAndroidContext(this);
        //

        btnSignup.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnForgotPass.setOnClickListener(this);

        //Inti Firebase
        auth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.signup_btn_login){
            startActivity(new Intent(SignUp.this,MainActivity.class));
            finish();
        }

        else if(view.getId()==R.id.signup_btn_forgot_pass){
            startActivity(new Intent(SignUp.this,ForgotPassword.class));
            finish();
        }

        else if(view.getId()==R.id.signup_btn_register){

            if (input_email.getText().toString()!= null && !input_email.getText().toString().isEmpty()&& input_pass.getText().toString().length()>5)
            {

                signUpUser(input_email.getText().toString(),input_pass.getText().toString());
               // startActivity(new Intent(SignUp.this,MainActivity.class));
               // finish();
                //snackbar= Snackbar.make(activity_sign_up,"Registration Successful!",Snackbar.LENGTH_SHORT);
               // snackbar.show();
            }

            else
            {
                Snackbar snackBar = Snackbar.make(activity_sign_up, "Enter valid data!", Snackbar.LENGTH_SHORT);
                snackBar.show();
            }


        }

    }

    private void signUpUser(String email, String password) {
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful())
                        {
                            snackbar= Snackbar.make(activity_sign_up,"Error: "+task.getException(),Snackbar.LENGTH_SHORT);
                            snackbar.show();
                        }
                        else{
                            Firebase ref = new Firebase(Config.FIREBASE_URL);

                            //Getting values to store
                            String name = editTextname.getText().toString().trim();
                            String username = editTextUsername.getText().toString().trim();
                            String pt=  editTextpt.getText().toString().trim();

                            //Creating Person object
                            User user = new User();

                            //Adding values
                            user.setName(name);
                            user.setUsername(username);
                            user.setpt(pt);

                            //Storing values to firebase
                            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            ref.child("users").child(uid).setValue(user);


                            startActivity(new Intent(SignUp.this,MainActivity.class));
                            finish();
                            snackbar= Snackbar.make(activity_sign_up,"Registration Successful!",Snackbar.LENGTH_SHORT);
                            snackbar.show();

                        }
                    }
                });
    }
}
