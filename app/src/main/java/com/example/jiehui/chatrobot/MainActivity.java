package com.example.jiehui.chatrobot;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ShareCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.Scheduler;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        SharedPreferences.OnSharedPreferenceChangeListener{


    private EditText editText;

    private ImageView imageView;

    private NavigationView navigationView;

    private DrawerLayout drawerLayout;

    private ActionBarDrawerToggle actionBarDrawerToggle;

    private Toolbar toolbar;

    private Button button;

    private static List<ListData> list;

    private static RecyclerView recyclerView;

    private static RecyclerViewAdapter recyclerViewAdapter;

    private String[] welcome_array;

    private String content_str;

    private String key = "62e81e7de14044dca3b10799cb0cce55";

    private Subscription subscription;

    private Gson gson;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initView();
    }


    private void initView(){

        setUpSharedPreference();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        RelativeLayout relativeLayout = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.leftitem,null);
        imageView = (ImageView) relativeLayout.findViewById(R.id.image_left);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        gson = new GsonBuilder().create();
        toolbar.setTitle(R.string.app_title);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle =  new ActionBarDrawerToggle(this,drawerLayout,R.string.Open,R.string.Close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        editText = (EditText) findViewById(R.id.edit_queryApi);
        button = (Button) findViewById(R.id.button_send);
        recyclerView = (RecyclerView) findViewById(R.id.list_item);

        list = new ArrayList<ListData>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerViewAdapter = new RecyclerViewAdapter(this,list);
        recyclerView.setAdapter(recyclerViewAdapter);
        ListData listData = new ListData(getWelcomeTips());
        listData.setFlag(ListData.RECEIVER);
        recyclerViewAdapter.addMessage(listData);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                content_str = editText.getText().toString();
                Log.i("text","string length is: " + content_str.length());
                if (content_str.trim().isEmpty()) {
                    editText.setText("");
                    return;
                }
                editText.setText("");
                ListData listData = new ListData(content_str);
                listData.setFlag(ListData.SEND);
                list.add(listData);
                recyclerViewAdapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(list.size()-1);
                getChatMessage(content_str);

            }
        });
    }

    private void setUpSharedPreference(){
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);

        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    private void loadImageFromPreference_left(String str){
        if(str.equals(getString(R.string.string_name1))){
           PreferenceUtils.putString(this,getString(R.string.key),getString(R.string.string_name1));
        } else if(str.equals(getString(R.string.string_name2)) ){
            PreferenceUtils.putString(this,getString(R.string.key),getString(R.string.string_name2));
        } else if(str.equals(getString(R.string.string_name3))){
            PreferenceUtils.putString(this,getString(R.string.key),getString(R.string.string_name3));
        }
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();
    }

    private void loadImageFromPreference_right(String str){
        if(str.equals(getString(R.string.string_name1_right))){
            PreferenceUtils.putString(this,getString(R.string.key_right),getString(R.string.string_name1_right));
        } else if(str.equals(getString(R.string.string_name2_right)) ){
            PreferenceUtils.putString(this,getString(R.string.key_right),getString(R.string.string_name2_right));
        } else if(str.equals(getString(R.string.string_name3_right))){
            PreferenceUtils.putString(this,getString(R.string.key_right),getString(R.string.string_name3_right));
        }

        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();
    }


    private String getWelcomeTips(){
        String[] welcome = null;
        welcome = this.getResources().getStringArray(R.array.Welcome_Words);
        int index = (int) (Math.random()*(welcome.length-1));
        String word = welcome[index];
        return word;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_Robphoto){
            Intent intent = new Intent(this,SettingActivity_Robot.class);
            startActivity(intent);

        } else if (id == R.id.nav_about){

            Intent intent = new Intent(this,AboutThis.class);
            startActivity(intent);

        } else if (id == R.id.nav_share){

            final String PackName = getPackageName();
            String mimeType = "text/plain";
            String title = getString(R.string.share_title);
            String suggest = "快来看看好玩的聊天助手小灵";
            String TextToShare = "https://play.google.com/store/apps/details?id=";
            TextToShare += PackName;
            suggest += " "+ TextToShare;

            ShareCompat.IntentBuilder.from(this)
                    .setType(mimeType)
                    .setChooserTitle(title)
                    .setText(suggest)
                    .startChooser();

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals(getString(R.string.key))){
            loadImageFromPreference_left(sharedPreferences.getString(getString(R.string.key),getString(R.string.string_name1)));
        }
        if(key.equals(getString(R.string.key_right))){
            loadImageFromPreference_right(sharedPreferences.getString(getString(R.string.key_right),getString(R.string.string_name1_right)));
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).
                unregisterOnSharedPreferenceChangeListener(this);
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    public void getChatMessage(String text) {
        subscription = new NetworkUtils().getTuLingChat(key, text)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ListData>() {
                    @Override
                    public void onCompleted() {
                        Log.i("TAG", "In onCompleted()");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.i("TAG", "something is wrong");
                    }

                    @Override
                    public void onNext(ListData listData) {
                        listData.setFlag(ListData.RECEIVER);
                        recyclerViewAdapter.addMessage(listData);
                        recyclerViewAdapter.notifyDataSetChanged();
                        recyclerView.scrollToPosition(list.size()-1);
                    }
                });
    }

}
