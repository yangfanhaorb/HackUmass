package edu.amherst.fyang17.carpool;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Daniel on 4/12/2015.
 */
public class Listings implements Comparable{

    private String FirstName;
    private String LastName;
    private String Origin;
    private String Dest;
    private String Time;


    public Listings (String f, String l, String o, String d, String t){
        super();
        FirstName = f;
        LastName = l;
        Origin = o;
        Dest = d;
        Time = t;
    }

    public int compareTo(Object comparesto){
        String temp =((Listings)comparesto).getTime().split(" ")[0];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            date = sdf.parse(temp);
        }
        catch(Exception e){
            System.out.println("exception");
        }
        Date date2 = new Date();
        String temp2 =this.getTime().split(" ")[0];
        try {
            date2 = sdf.parse(temp);
        }
        catch(Exception e){
            System.out.println("exception");
        }
        return date2.compareTo(date);
    }

    public String getFirstName(){
        return this.FirstName;
    }

    public String getLastName(){
        return this.LastName;
    }

    public String getOrigin(){
        return this.Origin;
    }

    public String getDest(){
        return this.Dest;
    }

    public String getTime(){
        return this.Time;
    }

    public String[] getAll(){
        String[] all = new String[5];
        all[0] = FirstName;
        all[1] = LastName;
        all[2] = Origin;
        all[3] = Dest;
        all[4] = Time;

        return all;
    }

    @Override
    public String toString(){
        String fuck = this.FirstName + this.LastName + this.Dest + this.Origin + this.Time;
        return fuck;
    }

}
