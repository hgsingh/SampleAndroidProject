package com.restart.stocklistener;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Button.OnClickListener {

    private final String TAG = "com.restart.stocklisten";
    private Button button;

    /**
     * Create and assign widgets to ones in the layout
     * @param savedInstanceState on create method
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        button = (Button) findViewById(R.id.button);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    /**
     * We will update any main menu stock values, when the activity resumes.
     */
    @Override
    protected void onResume() {
        super.onResume();
        parseJSON();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void parseJSON() {
        AsyncTask.execute(new Runnable() {
            public void run() {
                String strContent = "";

                try {
                    URL urlHandle = new URL("http://query.yahooapis.com/v1/public/yql?q=select%20%" +
                            "2a%20from%20yahoo.finance.quotes%20where%20symbol%20in%20%28%22" +
                            "aapl" + // <-- Company stock goes here
                            "%22%29%0A%09%09&env=http%3A%2F%2Fdatatables.org%2Falltables.env&" +
                            "format=json");
                    URLConnection urlconnectionHandle = urlHandle.openConnection();
                    InputStream inputstreamHandle = urlconnectionHandle.getInputStream();

                    try {
                        int intRead;
                        byte[] byteBuffer = new byte[1024];

                        do {
                            intRead = inputstreamHandle.read(byteBuffer);

                            if (intRead == 0) {
                                break;

                            } else if (intRead == -1) {
                                break;
                            }

                            strContent += new String(byteBuffer, 0, intRead, "UTF-8");
                        } while (true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    inputstreamHandle.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    JSONObject results = new JSONObject(strContent)
                            .getJSONObject("query")
                            .getJSONObject("results")
                            .getJSONObject("quote");

                    final String symbol = results.getString("symbol");
                    final String bid = results.getString("Bid");
                    final String change = results.getString("Change");
                    Log.d(TAG, "Here are the values " + symbol + ", " + bid + ", " + change);
                    final String buttonS = symbol + " " + bid  + " " + change;

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            button.setText(buttonS);
                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, StockDataActivity.class);
        startActivity(intent);
        Log.d(TAG, "Button was clicked.");
    }








/*    //test cases for the json file view
    //starts here
    //version 1 @8:51 PM by harsukh singh
    public void create_file()
    {
        String externDir = Environment.getExternalStorageDirectory().toString();
        File directory = new File(externDir, "stock_data");
        directory.mkdir();
        File file = new File(directory, "stock_data.json" );
        try
        {
            file.createNewFile();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        FileGet getter = new FileGet();
        getter.get_file("http://www.bloomberg.com/markets/chart/data/1D/AAPL:US", file);
        showFile("stock_data/stock_data.json");
    }
    public void showFile(String to_show)
    {
        File file = new File(Environment.getExternalStorageDirectory()+to_show);
        PackageManager packageManager = getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/stock_data");
        List list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/stock_data");
        startActivity(intent);
    }
    public void sendStockData(View view)
    {
        Intent intent = new Intent(this, StockDataActivity.class);
        startActivity(intent);
    }*/
}
