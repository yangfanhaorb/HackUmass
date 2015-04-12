package edu.amherst.fyang17.carpool;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


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
        String[] message = {origin,destination,description};
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra(EXTRA_MESSAGE,message);
        startActivity(intent);

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
}
