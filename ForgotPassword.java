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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity implements View.OnClickListener {

    private EditText input_email;
    private Button btnResetPass;
    private TextView btnBack;
    private RelativeLayout activity_forgot;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        //view
        input_email = (EditText)findViewById(R.id.forgot_email);
        btnResetPass = (Button) findViewById(R.id.forgot_btn_reset);
        btnBack = (TextView)findViewById(R.id.forgot_btn_back);
        activity_forgot = (RelativeLayout)findViewById(R.id.activity_forgot_password);
        btnResetPass.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        // int firbase
        auth= FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {

        if(view.getId()==R.id.forgot_btn_back)
        {
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }
        else if (view.getId()==R.id.forgot_btn_reset)
        {
            if (input_email.getText().toString()!= null && !input_email.getText().toString().isEmpty())
            {

                resetPassword(input_email.getText().toString());
            }

            else
            {
                Snackbar snackBar = Snackbar.make(activity_forgot, "Invalid a valid username!", Snackbar.LENGTH_SHORT);
                snackBar.show();
            }


        }

    }

    private void resetPassword(final String email) {
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Snackbar snackbar= Snackbar.make(activity_forgot,"Reset link sent to email: "+email,Snackbar.LENGTH_SHORT);
                            snackbar.show();
                        }

                        else{
                            Snackbar snackbar= Snackbar.make(activity_forgot,"Failed to send Password/Invalid Username",Snackbar.LENGTH_SHORT);
                            snackbar.show();
                        }

                    }
                });

}}
