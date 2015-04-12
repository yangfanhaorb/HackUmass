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
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        String[] split = message.split(",");
        String name = "Host of the event is: "+split[0];
        String trip = "Route: "+split[1];
        String date = "Leaves on: "+split[2];
        String seats = "Number of seats available: "+split[3]+"/"+split[4];
        TextView textView1 = (TextView) findViewById(R.id.name);
        TextView textView2 = (TextView) findViewById(R.id.trip);
        TextView textView3 = (TextView) findViewById(R.id.date);
        TextView textView4 = (TextView) findViewById(R.id.seats);
        textView1.setText(name);
        textView2.setText(trip);
        textView3.setText(date);
        textView4.setText(seats);
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
