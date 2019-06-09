package com.example.ash.twitter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogOutCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TwitterUsers extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView listView;
    private ArrayList<String> tUsers;
    private ArrayAdapter adapter;
    private String followedUser = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter_users);
        FancyToast.makeText(this, "welcome " + ParseUser.getCurrentUser().getUsername(),
                Toast.LENGTH_LONG, FancyToast.INFO, true).show();
        listView = findViewById(R.id.listView);
        tUsers = new ArrayList<>();
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_checked, tUsers);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        listView.setOnItemClickListener(this);


        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (objects.size() > 0 && e == null) {
                    for (ParseUser twitterUser : objects) {
                        tUsers.add(twitterUser.getUsername());
                    }
                    listView.setAdapter(adapter);
                    for (String twitterUser:tUsers){
                        if (ParseUser.getCurrentUser().getList("followeOf") != null){
                        if (ParseUser.getCurrentUser().getList("followeOf").contains(twitterUser)){
                            followedUser += twitterUser + "\n";
                            listView.setItemChecked(tUsers.indexOf(twitterUser),true);

                            FancyToast.makeText(TwitterUsers.this, ParseUser.getCurrentUser().getUsername() + " follows" + "\n" + followedUser,
                                    Toast.LENGTH_LONG, FancyToast.INFO, true).show();


                        }
                        }
                    }
                }


            }


        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.logout_item:
                ParseUser.getCurrentUser().logOutInBackground(new LogOutCallback() {
                    @Override
                    public void done(ParseException e) {
                        Intent intent = new Intent(TwitterUsers.this, signUp.class);
                        startActivity(intent);
                        finish();

                    }
                });
                break;
            case R.id.sendTweetItem:
                Intent intent = new Intent(TwitterUsers.this, sendTweetActivity.class);
                startActivity(intent);
                break;


        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        CheckedTextView checkedTextView = (CheckedTextView) view;
        if (checkedTextView.isChecked()){
            FancyToast.makeText(this, tUsers.get(position) + " is now followed!",
                    Toast.LENGTH_SHORT, FancyToast.INFO, true).show();
            ParseUser.getCurrentUser().add("followeOf",tUsers.get(position));

        }else{
            FancyToast.makeText(this, tUsers.get(position) + " is not followed!",
                    Toast.LENGTH_SHORT, FancyToast.INFO, true).show();
            ParseUser.getCurrentUser().getList("followeOf").remove(tUsers.get(position));
            List currentUserFollowerOfList = ParseUser.getCurrentUser().getList("followeOf");
            ParseUser.getCurrentUser().remove("followeOf");
            ParseUser.getCurrentUser().put("followeOf", currentUserFollowerOfList);

        }
        ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null){
                    FancyToast.makeText(TwitterUsers.this, "saved",
                            Toast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();

                }
            }
        });

    }
}
