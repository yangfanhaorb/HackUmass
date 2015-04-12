package edu.amherst.fyang17.carpool;


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
    @Override
    protected String doInBackground(URL...param) {
        BufferedReader br = null;
        InputStream is = null;
        StringBuffer sb = null;
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

        } catch (Exception e) {
            //handle errors
            Log.w("Failed ", " to get information from designated server thing");
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

        return sb.toString();
    }


    @Override
    protected void onProgressUpdate(Integer...progress){

    }


    @Override
    protected void onPostExecute(String result){
        parseJSON(result);
    }

    public ArrayList<Listings> parseJSON(String result){
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONObject subObject = null;
            //interior is above converted to string
            String interior,
                    fName, lName, origin, dest, time;
            Listings listing;

            //Return this arraylist
            ArrayList<Listings> alListings = new ArrayList<Listings>();
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

                    listing = new Listings(fName, lName, origin, dest, time);

                    alListings.add(listing);
                }
            }

            return alListings;
        }
        catch(Exception ex){
            Log.w("Parse errors ", ex.getMessage());
        }
    }

}
