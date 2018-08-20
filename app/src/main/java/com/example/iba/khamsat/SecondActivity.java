package com.example.iba.khamsat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

;

public class SecondActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = SecondActivity.class.getSimpleName();
    Bitmap image = null;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private LinearLayout mLinearLayoutDepartments;
    private ImageView mImageViewDepartmentArrow;
    private ImageView mImageViewCubic;
    private TextView mTextViewDepartmentBrowse;
    private LinearLayout mLinearLayoutKhamsatCommunity;
    private ImageView mImageViewKhmsatCommunityArrow;
    private ImageView mImageViewChat;
    private TextView mTextViewKhamsatCommunity;
    private ImageView mImageViewHomeBackground;

    private JSONObject jsonObjectData;
    private String imageUrl = "https://mohamed-sherif.000webhostapp.com/images/";
    private String imageName = "";
    ArrayList<MyImage> images = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        initializeComponents();

        displayImages();
    }


    public void displayImages() {
        class GetImage extends AsyncTask<String, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(SecondActivity.this, "Downloading images...PLZ wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                loading.dismiss();

                ImageArrayAdapter imageArrayAdapter = new ImageArrayAdapter(SecondActivity.this, images);
                ListView listView = findViewById(R.id.list_view_image);
                listView.setAdapter(imageArrayAdapter);
            }

            @Override
            protected String doInBackground(String... params) {
                String result = "";
                URL url = null;
                String stringUrl = "https://mohamed-sherif.000webhostapp.com/getImage.php";
                try {
                    url = new URL(stringUrl);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        result += line;
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    Log.v(TAG, result);
                    JSONParser jsonParser = new JSONParser();
                    try {
                        jsonObjectData = (JSONObject) jsonParser.parse(result);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    getImage();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return result;
            }
        }

        GetImage gi = new GetImage();
        gi.execute("image_logo.jpg");
    }

    public void initializeComponents() {
        Toolbar mToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(mToolbar);

        // Departments browse
        mLinearLayoutDepartments = (LinearLayout) findViewById(R.id.linear_layout_departments);
        mLinearLayoutDepartments.setVisibility(View.GONE);

        mImageViewDepartmentArrow = findViewById(R.id.image_department_browse_arrow);
        mImageViewDepartmentArrow.setBackground(getResources().getDrawable(R.drawable.left_arrow));

        mImageViewCubic = findViewById(R.id.image_department_browse_cubic);
        mImageViewCubic.setBackgroundResource(R.drawable.black_cubic);

        mTextViewDepartmentBrowse = findViewById(R.id.textview_department_browse);

        //Khamsat Community
        mLinearLayoutKhamsatCommunity = (LinearLayout) findViewById(R.id.linear_layout_Khamsat_community);
        mLinearLayoutKhamsatCommunity.setVisibility(View.GONE);

        mImageViewKhmsatCommunityArrow = findViewById(R.id.image_khamsat_community_arrow);
        mImageViewKhmsatCommunityArrow.setBackground(getResources().getDrawable(R.drawable.left_arrow));

        mImageViewChat = findViewById(R.id.image_khamsat_community_chat);
        mImageViewChat.setBackgroundResource(R.drawable.chat_icon_black);

        mTextViewKhamsatCommunity = findViewById(R.id.textview_khamsat_community);

        mImageViewHomeBackground = findViewById(R.id.image_home_background);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void getImage() throws IOException {

        for (int i = 0; i <= 10; i++){
            imageName = jsonObjectData.get( "1" ).toString();
            Log.v(TAG, "kkkkkkkkkkkkkkkkkkk  "+imageUrl);
            image = BitmapFactory.decodeStream((InputStream) new URL(imageUrl + imageName).getContent());
            images.add(new MyImage(imageName, image));
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item != null && item.getItemId() == android.R.id.home) {
            if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                mDrawerLayout.closeDrawer(Gravity.RIGHT);
            } else {
                mDrawerLayout.openDrawer(Gravity.RIGHT);
            }
        }
        return false;
    }

    public void showHideDepartments(View view) {
        if (mLinearLayoutDepartments.getVisibility() == View.GONE)
            mLinearLayoutDepartments.setVisibility(View.VISIBLE);
        else
            mLinearLayoutDepartments.setVisibility(View.GONE);

        String imageArrowTag = String.valueOf(mImageViewDepartmentArrow.getTag());
        if (imageArrowTag.equals("left_arrow")) {
            mImageViewDepartmentArrow.setTag("down_arrow");
            mImageViewDepartmentArrow.setBackground(getResources().getDrawable(R.drawable.down_arrow));
            mImageViewCubic.setBackgroundResource(R.drawable.yellow_cubic);
            mTextViewDepartmentBrowse.setTextColor(getResources().getColor(R.color.yellow));
        } else {
            mImageViewDepartmentArrow.setTag("left_arrow");
            mImageViewDepartmentArrow.setBackground(getResources().getDrawable(R.drawable.left_arrow));
            mImageViewCubic.setBackgroundResource(R.drawable.black_cubic);
            mTextViewDepartmentBrowse.setTextColor(getResources().getColor(R.color.title_text_color));
        }
    }

    public void showHideKhamsatCommunity(View view) {
        if (mLinearLayoutKhamsatCommunity.getVisibility() == View.GONE)
            mLinearLayoutKhamsatCommunity.setVisibility(View.VISIBLE);
        else
            mLinearLayoutKhamsatCommunity.setVisibility(View.GONE);

        String imageArrowTag = String.valueOf(mImageViewKhmsatCommunityArrow.getTag());
        if (imageArrowTag.equals("left_arrow")) {
            mImageViewKhmsatCommunityArrow.setTag("down_arrow");
            mImageViewKhmsatCommunityArrow.setBackground(getResources().getDrawable(R.drawable.down_arrow));
            mImageViewChat.setBackgroundResource(R.drawable.chat_icon_yellow);
            mTextViewKhamsatCommunity.setTextColor(getResources().getColor(R.color.yellow));
        } else {
            mImageViewKhmsatCommunityArrow.setTag("left_arrow");
            mImageViewKhmsatCommunityArrow.setBackground(getResources().getDrawable(R.drawable.left_arrow));
            mImageViewChat.setBackgroundResource(R.drawable.chat_icon_black);
            mTextViewKhamsatCommunity.setTextColor(getResources().getColor(R.color.title_text_color));
        }
    }

    public void login(View view) {
        Intent intent = new Intent(SecondActivity.this, LogIn.class);
        startActivity(intent);
    }

    public void openWorksActivity(View view) {
        Intent intent = new Intent(SecondActivity.this, WorksActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.END)) {
            mDrawerLayout.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }

    public void openProgrammingActivity(View view) {
        Intent intent = new Intent(SecondActivity.this, ProgrammingActivity.class);
        startActivity(intent);
    }

    public void openElectonicMarketingActivity(View view) {
        Intent intent = new Intent(SecondActivity.this, ElectronicMarketing.class);
        startActivity(intent);
    }

    public void openWritingTranslationActivity(View view) {
        Intent intent = new Intent(SecondActivity.this, WritingTranslation.class);
        startActivity(intent);
    }

    public void openDesignActivity(View view) {
        Intent intent = new Intent(SecondActivity.this, DesignActivity.class);
        startActivity(intent);
    }

    public void signUp(View view) {
        Intent intent = new Intent(SecondActivity.this, SignUp.class);
        startActivity(intent);
    }
}
