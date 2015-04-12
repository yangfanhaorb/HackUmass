package edu.amherst.fyang17.carpool;


import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;

/**
 * Created by thoma_000 on 4/11/2015.
 */
public class ListPopulator extends AsyncTask<URL, Integer, Long> {

    @Override
    protected Long doInBackground(URL...param) {
        BufferedReader br = null;
        StringBuffer sb = null;
        try {
            URL u = param[0];
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI(u.toString()));
            HttpResponse response = client.execute(request);

            br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            sb = new StringBuffer("");

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                break;
            }
            Log.w("string: ", sb.toString());
        } catch (Exception e) {
            //handle errors
            Log.w("Failed ", " to get information from designated server thing");
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (sb != null) {

                }
            } catch (Exception e) {
                //handle exceptions
            }

        }

        return new Long(0);
    }


    @Override
    protected void onProgressUpdate(Integer...progress){

    }


    @Override
    protected void onPostExecute(Long result){}

}
