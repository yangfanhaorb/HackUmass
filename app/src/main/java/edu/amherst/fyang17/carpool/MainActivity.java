package edu.amherst.fyang17.carpool;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import android.widget.TextView;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends ActionBarActivity {

    public final static String EXTRA_MESSAGE = "edu.amherst.fyang17.fyang17.MESSAGE";
    public static final String PREFS_NAME = "MyPrefsFile";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] message = getIntent().getStringArrayExtra(AddNew.EXTRA_MESSAGE);

        final Activity activity = this;
        ArrayList<Item> tripList = new ArrayList<>();
        tripList.add(new Item("Fanhao","New York-Boston"));
        tripList.add(new Item("Thomas","Boston-New York"));
        tripList.add(new Item("Thomas","Boston-New York"));
        tripList.add(new Item("Thomas","Boston-New York"));
        tripList.add(new Item("Thomas","Boston-New York"));
        tripList.add(new Item("Thomas","Boston-New York"));
        tripList.add(new Item("Thomas","Boston-New York"));
        tripList.add(new Item("Thomas","Boston-New York"));
        tripList.add(new Item("Thomas","Boston-New York"));
        if (message!=null) {
            tripList.add(new Item(message[2], message[0] + "-" + message[1]));
        }

        // 1. pass context and data to the custom adapter
        final MyAdapter adapter = new MyAdapter(this,tripList );

        // 2. Get ListView from activity_main.xml
        ListView listView = (ListView) findViewById(R.id.listview);

        // 3. setListAdapter
        listView.setAdapter(adapter);

        AdapterView.OnItemClickListener mMessageClickedHandler = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                Intent intent = new Intent(activity,TripDetail.class);
                Item item = adapter.getItem(position);
                String toDisplay = item.getName()+","+item.getTrip();
                intent.putExtra(EXTRA_MESSAGE,toDisplay);
                startActivity(intent);
            }
        };

        listView.setOnItemClickListener(mMessageClickedHandler);
        Log.w("called", "Shit");
        callShit();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
        String date = DatePickerFragment.date;
        EditText editText = (EditText) findViewById(R.id.date);
        editText.setText(date);
    }


    public void addNewRide(MenuItem menuItem){
        Intent intent = new Intent(this,AddNew.class);
        startActivity(intent);
    }

    public void seeNotifications(MenuItem menuItem){
        Intent intent = new Intent(this,Notifications.class);
        startActivity(intent);
    }

    public void update(View view){
        EditText editText1 = (EditText) findViewById(R.id.origin);
        String origin = editText1.getText().toString();
        EditText editText2 = (EditText) findViewById(R.id.destination);
        String destination = editText2.getText().toString();
        EditText editText3 = (EditText) findViewById(R.id.date);
        String date = editText3.getText().toString();
        String[] message = {origin,destination,date};
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra(EXTRA_MESSAGE,message);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    public void seeHistory(MenuItem menuItem){
        Intent intent = new Intent(this,RideHistory.class);
        startActivity(intent);
    }

    public String readFromServer() {
        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet("");
        try {
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();

            //Implement this later into the ListPopulator
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
            } else {
                //Log.e(ParseJSON.class.toString(), "Failed to download file");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    public void callShit() {
        ListPopulator lp = new ListPopulator();
        try {
            lp.execute(new URL("http://ec2-54-148-117-26.us-west-2.compute.amazonaws.com/query1.php"));
        }
        catch(Exception e){
            //handle errors
            Log.w("error", "Web thing doesn't exist");
        }
    }

}
