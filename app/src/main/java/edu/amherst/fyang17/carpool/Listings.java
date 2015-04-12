package edu.amherst.fyang17.carpool;

/**
 * Created by Daniel on 4/12/2015.
 */
public class Listings {

    private String FirstName;
    private String LastName;
    private String Origin;
    private String Dest;
    private String Time;
    private int Seats;

    public Listings (String f, String l, String o, String d, String t, String s){
        super();
        FirstName = f;
        LastName = l;
        Origin = o;
        Dest = d;
        Time = t;
        Seats = Integer.parseInt(s);
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

    public int getSeats() { return this.Seats; }

    public String[] getAll(){
        String[] all = new String[6];
        all[0] = FirstName;
        all[1] = LastName;
        all[2] = Origin;
        all[3] = Dest;
        all[4] = Time;
        all[5] = String.valueOf(Seats);

        return all;
    }

    @Override
    public String toString(){
        String fuck = this.FirstName + this.LastName + this.Dest + this.Origin + this.Time + String.valueOf(Seats);
        return fuck;
    }

}
