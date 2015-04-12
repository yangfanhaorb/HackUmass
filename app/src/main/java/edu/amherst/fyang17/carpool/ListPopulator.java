package edu.amherst.fyang17.carpool;


import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.*;

/**
 * Created by thoma_000 on 4/11/2015.
 */
public class ListPopulator extends AsyncTask<URL, Integer, String> {
    //All the overrides are the things that call the php stuff
    Activity activity;
    ArrayList<Item> tripList;
    MyAdapter adapter;
    public ListPopulator(){
        super();
    }

    ListPopulator(Activity activity, ArrayList<Item> tripList, MyAdapter adapter){
        super();
        this.activity = activity;
        this.tripList = tripList;
        this.adapter = adapter;
    }


    @Override
    protected String doInBackground(URL...param) {
        BufferedReader br = null;
        InputStream is = null;
        StringBuffer sb = null;

        String result ="";
        try {
            URL u = param[0];
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI(u.toString()));
            HttpResponse response = client.execute(request);

            br = new BufferedReader(new InputStreamReader(is = response.getEntity().getContent(), "UTF-8") );
            sb = new StringBuffer("");

            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line);
                break;
            }
            if(sb != null) {
                result = sb.toString();
            }
        } catch (Exception e) {
            //handle errors
            Log.w("Failed ", e.getMessage());
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (Exception e) {
                //handle exceptions
                Log.w("Closed", " Failed to close buffer and input streams");
            }

        }
        Log.w("Near end execution ", result);
        return result;
    }


    @Override
    protected void onProgressUpdate(Integer...progress){

    }


    @Override
    protected void onPostExecute(String result){
        ArrayList<Listings> update = new ArrayList<Listings>();
        update = parseJSON(result);

        //modify the view
        modify(update);
    }

    public ArrayList<Listings> parseJSON(String result){
        //Return this arraylist 
        ArrayList<Listings> alListings = new ArrayList<Listings>();
        Log.w("Test: ", result);
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONObject subObject = null;
            //interior is above converted to string
            String interior,
                    fName, lName, origin, dest, time, seats;
            Listings listing;

            Iterator<?> keys = jsonObject.keys();
            while(keys.hasNext()){
                String key = (String) keys.next();
                if(jsonObject.get(key) instanceof JSONObject){
                    interior = jsonObject.get(key).toString();
                    subObject = new JSONObject(interior);

                    fName = subObject.get("FirstName").toString();
                    lName = subObject.get("LastName").toString();
                    origin = subObject.get("Origin").toString();
                    dest = subObject.get("Dest").toString();
                    time = subObject.get("Time").toString();
                    seats = subObject.get("Seats").toString();


                    listing = new Listings(fName, lName, origin, dest, time, seats);

                    alListings.add(listing);
                }
            }

        }
        catch(Exception ex){
            Log.w("Parse errors ", ex.getMessage());
        }

        return alListings;
    }

    //Provisional method to modify the lists
    //TODO: the update array doesn't guarantee new elements...revise implementation so that it does
    public void modify(ArrayList<Listings> update){
        for(int i=0; i<update.size(); i++){
            //Temporary thing to test
            String tempString = update.get(i).getOrigin() + "-" + update.get(i).getDest();
            Item temp = new Item(update.get(i).getFirstName(), tempString, update.get(i).getTime(), update.get(i).getSeats());
            this.tripList.add(temp);
        }

        adapter.notifyDataSetChanged();
    }

}
