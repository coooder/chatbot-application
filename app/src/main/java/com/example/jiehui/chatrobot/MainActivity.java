package com.example.jiehui.chatrobot;

import android.app.Activity;
import android.content.DialogInterface;
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
import android.os.Handler;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,NavigationView.OnNavigationItemSelectedListener,
        SharedPreferences.OnSharedPreferenceChangeListener{


    private EditText editText;

    private ImageView imageView;

    private NavigationView navigationView;

    private DrawerLayout drawerLayout;

    private ActionBarDrawerToggle actionBarDrawerToggle;

    private Toolbar toolbar;

    private Button button;

    private List<ListData> list;

    private RecyclerView recyclerView;

    private RecyclerViewAdapter recyclerViewAdapter;

    private String[] welcome_array;

    private String content_str;

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
        //relativeLayout.setVisibility(View.INVISIBLE);
        //imageView.setVisibility(View.INVISIBLE);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        toolbar.setTitle(R.string.app_title);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle =  new ActionBarDrawerToggle(this,drawerLayout,R.string.Open,R.string.Close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        editText = (EditText) findViewById(R.id.edit_queryApi);
        button = (Button) findViewById(R.id.button_send);
        recyclerView = (RecyclerView) findViewById(R.id.list_item);

        list = new ArrayList<ListData>();
        button.setOnClickListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerViewAdapter = new RecyclerViewAdapter(this,list);
        recyclerView.setAdapter(recyclerViewAdapter);
        ListData listData = new ListData(getWelcomeTips(),ListData.RECEIVER);
        recyclerViewAdapter.addMessage(listData);
       // imageView.setImageResource(R.mipmap.ic_pretty);
        //imageView.invalidate();

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
        if(str.equals(getString(R.string.string_name1_rig))){
            PreferenceUtils.putString(this,getString(R.string.key_right),getString(R.string.string_name1_rig));
        } else if(str.equals(getString(R.string.string_name2_rig)) ){
            PreferenceUtils.putString(this,getString(R.string.key_right),getString(R.string.string_name2_rig));
        } else if(str.equals(getString(R.string.string_name3_rig))){
            PreferenceUtils.putString(this,getString(R.string.key_right),getString(R.string.string_name3_rig));
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
    public void onClick(View v) {
        content_str = editText.getText().toString();
        editText.setText("");
        URL url = NetworkUtils.buildUrl(content_str);
        ListData listData = new ListData(content_str,ListData.SEND);
        list.add(listData);
        new tuLingQueryTask().execute(url);
        recyclerViewAdapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(list.size()-1);
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
            loadImageFromPreference_right(sharedPreferences.getString(getString(R.string.key_right),getString(R.string.string_name1_rig)));
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).
                unregisterOnSharedPreferenceChangeListener(this);
    }


    public class tuLingQueryTask extends AsyncTask<URL,Void,String>{

        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String tulingSearchResult = null;
            try{
                tulingSearchResult = NetworkUtils.getResponseFromHttp(searchUrl);
            } catch (IOException e){
                e.printStackTrace();
            }
            return tulingSearchResult;
        }


        @Override
        protected void onPostExecute(String s) {
            if(s !=null && !s.equals("")){
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String json_str = jsonObject.getString("text");
                    ListData listData = new ListData(json_str,ListData.RECEIVER);
                    list.add(listData);
                    recyclerViewAdapter.notifyDataSetChanged();
                    recyclerView.scrollToPosition(list.size()-1);

                } catch (JSONException e){
                    e.printStackTrace();
                }

            }
        }
    }


}
