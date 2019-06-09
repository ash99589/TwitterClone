package com.example.ash.twitter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtLoginEmail , edtLoginP;
    private Button btnLogin2 , btnSignUp2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        setTitle("Log In");
        edtLoginEmail = findViewById(R.id.edtLoginEnail);
        edtLoginP = findViewById(R.id.edtLoginP);

        edtLoginP.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCOde, KeyEvent event) {
                if (keyCOde == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
                    onClick(btnLogin2);
                }
                return false;
            }
        });
        btnLogin2 = findViewById(R.id.btnLogin2);
        btnSignUp2 = findViewById(R.id.btnSignUp2);

        btnLogin2.setOnClickListener(this);
        btnSignUp2.setOnClickListener(this);

        if (ParseUser.getCurrentUser()!=null){
           //  ParseUser.getCurrentUser().logOut();
            TransitionToAnotherActivity();

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnLogin2:
                if ( edtLoginEmail.getText().toString().equals("") ||
                        edtLoginP.getText().toString().equals("")) {
                    FancyToast.makeText(LogInActivity.this, "email and password are required",
                            Toast.LENGTH_SHORT, FancyToast.INFO, true).show();

                } else {
                    ParseUser.logInInBackground(edtLoginEmail.getText().toString(), edtLoginP.getText().toString(), new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException e) {
                            if (user != null && e == null) {
                                FancyToast.makeText(LogInActivity.this, user.getUsername() + " is logged in",
                                        Toast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                                TransitionToAnotherActivity();

                            }

                        }
                    });
                }
                break;
            case R.id.btnSignUp2:
                Intent intent = new Intent(LogInActivity.this , signUp.class);
                startActivity(intent);
                break;
        }
    }
    public void rootLayoutTapped(View view){
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

        } catch (Exception e){
            e.printStackTrace();
        }
    }
    private void TransitionToAnotherActivity() {
        Intent intent = new Intent(LogInActivity.this, TwitterUsers.class);
        startActivity(intent);
    }

}
