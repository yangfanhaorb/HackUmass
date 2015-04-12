package edu.amherst.fyang17.carpool;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.net.URI;
import java.net.URL;


public class AddNew extends ActionBarActivity {

    public final static String EXTRA_MESSAGE = "edu.amherst.fyang17.fyang17.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);
    }

    public void submit(View view){
        EditText editText1 = (EditText) findViewById(R.id.editText5);
        String origin = editText1.getText().toString();
        EditText editText2 = (EditText) findViewById(R.id.editText6);
        String destination = editText2.getText().toString();
        EditText editText3 = (EditText) findViewById(R.id.editText4);
        String description = editText3.getText().toString();
        EditText editText4 = (EditText) findViewById(R.id.editText2);
        String date = editText4.getText().toString();
        String[] message = {origin,destination,description};

        //dummy names for now, remember to change this after we do account related things
        String fname = "Dave";
        String lname = "Grohl";
        if (!origin.equals("")&&!destination.equals("")&&!date.equals("")) {
            String url = "http://ec2-54-148-117-26.us-west-2.compute.amazonaws.com/updatedb.php";
            updateDatabase(url, fname, lname, origin, destination, date);

            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(EXTRA_MESSAGE, message);
            startActivity(intent);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_new, menu);
        return true;
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

    public void updateDatabase(String...params){
        DB_Call dbc = new DB_Call();
        dbc.execute(params[0], params[1], params[2], params[3], params[4], params[5]);
    }

}
//i'd be super dope if we made it into some seperate class
class DB_Call extends AsyncTask<String, Integer, String> {
    @Override
    protected String doInBackground(String...params) {
        try {
            String url = params[0];
            String fname = params[1];
            String lname = params[2];
            String origin = params[3];
            String dest = params[4];
            String date = params[5];
            //create request url and change to URL Itme
            String reqURL = url + "?" + "fname=" + fname + "&lname=" + lname + "&origin=" + origin + "&dest=" + dest + "&date=" + date;

            Log.w("reqURL " , reqURL);
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI(reqURL));
            HttpResponse response = client.execute(request);
        }
        catch(Exception e){
            //handle exception
        }
        return "Successfully added item";
    }

    @Override
    protected void onProgressUpdate(Integer...progress){

    }


    @Override
    protected void onPostExecute(String result){
        Log.w("Done: ", "Done doing that thing for tha thting s");
    }
}