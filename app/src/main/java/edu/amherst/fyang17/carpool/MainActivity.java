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

public class MainActivity extends ActionBarActivity implements AsyncResponse{

    public final static String EXTRA_MESSAGE = "edu.amherst.fyang17.fyang17.MESSAGE";
    public static final String PREFS_NAME = "MyPrefsFile";
    public static ArrayList<Listings> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Activity activity = this;
        ArrayList<Item> tripList = new ArrayList<>();
//        tripList.add(new Item("Fanhao","New York-Boston"));
//        tripList.add(new Item("Thomas","Boston-New York"));
//        tripList.add(new Item("Thomas","Boston-New York"));
//        tripList.add(new Item("Thomas","Boston-New York"));
//        tripList.add(new Item("Thomas","Boston-New York"));
//        tripList.add(new Item("Thomas","Boston-New York"));
//        tripList.add(new Item("Thomas","Boston-New York"));
//        tripList.add(new Item("Thomas","Boston-New York"));
//        tripList.add(new Item("Thomas","Boston-New York"));

        // 1. pass context and data to the custom adapter
        final MyAdapter adapter = new MyAdapter(this,tripList );
        callShit(this, tripList, adapter);
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


    }


    public void processFinish(ArrayList<Listings> output){
        //this you will received result fired from async class of onPostExecute(result) method.
        list = output;
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

        // 1. pass context and data to the custom adapter
        ArrayList<Item> tripList = new ArrayList<>();
        MyAdapter adapter = new MyAdapter(this,tripList);
        select(this,tripList,adapter,origin,destination,date);



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

    public void callShit(Activity activity, ArrayList<Item> tripList, MyAdapter adapter) {
        ListPopulator lp = new ListPopulator(activity, tripList, adapter);
        lp.delegate = this;
        try {
            lp.execute(new URL("http://ec2-54-148-117-26.us-west-2.compute.amazonaws.com/query1.php"));
        }
        catch(Exception e){
            //handle errors
            Log.w("error", "Web thing doesn't exist");
        }
    }

    public void select(Activity activity,ArrayList<Item> tripList, MyAdapter adapter,String origin,String destination,String date){
        ListPopulator lp = new ListPopulator(activity, tripList, adapter);
        lp.delegate = this;
        try {
            lp.execute(new URL("http://ec2-54-148-117-26.us-west-2.compute.amazonaws.com/query1.php"));
        }
        catch(Exception e){
            //handle errors
            Log.w("error", "Web thing doesn't exist");
        }
        ArrayList<Item> filteredList = new ArrayList<>();
        for (int i=0;i<list.size();i++){
            boolean toAdd = true;
            if ((!origin.equals(""))&&(!list.get(i).getOrigin().equals(origin))){
                toAdd = false;
            }
            if ((!destination.equals(""))&&(!list.get(i).getDest().equals(destination))){
                toAdd = false;
            }
            String temp = list.get(i).getTime().split(" ")[0];
            String[] temp1 = temp.split("-");
            if (temp1[1].charAt(0)=='0'){
                temp1[1] = temp1[1].substring(1);
            }
            if (temp1[2].charAt(0)=='0'){
                temp1[2] = temp1[2].substring(1);
            }
            String dateComp = temp1[1]+"/"+temp1[2]+"/"+temp1[0];
            if ((!date.equals(""))&&(!date.equals(dateComp))){
                toAdd = false;
            }
            if (toAdd==true){
                filteredList.add(new Item(list.get(i).getFirstName()+" "+list.get(i).getLastName()+" is travelling on "+dateComp,list.get(i).getOrigin()+"-"+list.get(i).getDest()));
            }
        }
        adapter = new MyAdapter(this,filteredList);
        // 2. Get ListView from activity_main.xml
        ListView listView = (ListView) findViewById(R.id.listview);
        // 3. setListAdapter
        listView.setAdapter(adapter);
    }

}
