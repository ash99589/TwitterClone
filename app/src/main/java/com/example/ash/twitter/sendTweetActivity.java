package com.example.ash.twitter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class sendTweetActivity extends AppCompatActivity {
    private EditText edtTweet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        edtTweet = findViewById(R.id.edtSendTweet);

    }
    public void sendTweet(View view){
        ParseObject parseObject = new ParseObject("MyTweet");
        parseObject.put("tweet", edtTweet.getText().toString());
        parseObject.put("user", ParseUser.getCurrentUser().getUsername());
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        parseObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {

                    FancyToast.makeText(sendTweetActivity.this, ParseUser.getCurrentUser().getUsername() + "'s tweet" + "(" + edtTweet.getText().toString() + ")" + " is saved!",
                            Toast.LENGTH_LONG, FancyToast.SUCCESS, true).show();

                } else {

                    FancyToast.makeText(sendTweetActivity.this, e.getMessage(),
                            Toast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                }
                progressDialog.dismiss();
            }
        });

    }
}
