package edu.amherst.fyang17.carpool;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class TripDetail extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_detail);
        Intent intent = getIntent();
        String[] message = intent.getStringArrayExtra(MainActivity.EXTRA_MESSAGE);
        TextView name = (TextView) findViewById(R.id.name);
        TextView trip = (TextView) findViewById(R.id.trip);
        TextView time = (TextView) findViewById(R.id.date);
        TextView seats = (TextView) findViewById(R.id.seats);
        name.setText("Host of Trip is: "+message[0].split(" ")[0]+" "+message[0].split(" ")[0]);
        trip.setText("Itinerary: "+message[1]);
        String[] temp = message[0].split(" ");
        time.setText("Date of Trip: "+temp[temp.length-1]);
        seats.setText("Seats Available: "+message[2]+"/"+message[3]);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_trip_detail, menu);
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
}
