package com.example.mythaikitchen;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.arch.core.executor.TaskExecutor;
import com.chaos.view.PinView;
import com.example.mythaikitchen.databinding.ActivityMainRelayBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthMissingActivityForRecaptchaException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import org.jetbrains.annotations.NotNull;



import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class authentication extends AppCompatActivity {
    String phoneNumber;
    String verificationID;
    FirebaseAuth mAuth;
    String codeip;
    ImageButton verifyOtpButton;
    EditText otpEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        retrievePhoneNumber();

        otpEditText = findViewById(R.id.code);
        verifyOtpButton = findViewById(R.id.confirmBt);
        ImageButton resendOtpButton = findViewById(R.id.retryBt);

        mAuth = FirebaseAuth.getInstance();

        sendVerificationCode();

        verifyOtpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(otpEditText.getText().toString())){
                    Toast.makeText(authentication.this, "Missing Information", Toast.LENGTH_SHORT).show();
                }else{
                    verifyCode(otpEditText.getText().toString());
                }
            }
        });

        resendOtpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendVerificationCode();
            }
        });
    }

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationID, code);
        signinbyCredentials(credential);
    }

    private void signinbyCredentials(PhoneAuthCredential credential) {
        //compares the code entered by the user with the code sent to device
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            //code matches
                            Toast.makeText(authentication.this, "Success", Toast.LENGTH_SHORT).show();
                            openUser();
                        }
                        else {
                            //code does not match
                            Toast.makeText(authentication.this, "Please Retry", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void sendVerificationCode() {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // (optional) Activity for callback binding
                        // If no activity is passed, reCAPTCHA verification can not be used.
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
    //checks when there is a change of state for code request
    mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
            final String code = credential.getSmsCode();
            if (code != null){
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            //code sent fail
            Toast.makeText(authentication.this, "Fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCodeSent(@NonNull String s,
                @NonNull PhoneAuthProvider.ForceResendingToken token) {
            //code sent successfully
            super.onCodeSent(s, token);
            verificationID = s;
            Toast.makeText(authentication.this, "Code Sent", Toast.LENGTH_SHORT).show();
        }
    };

    private void retrievePhoneNumber(){
        SharedPreferences address = getSharedPreferences("MyUser", Context.MODE_PRIVATE);
        phoneNumber = address.getString("PHONENUMBER", "");
    }
    public void openUser(){
        Intent intent = new Intent(this, userSettings.class);
        startActivity(intent);
        overridePendingTransition(0,0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    }
}
