package edu.amherst.fyang17.carpool;

/**
 * Created by Administrator on 4/11/2015.
 */
public class Item {

    public String name;
    public String trip;

    public Item(String name, String trip) {
        super();
        this.name = name;
        this.trip = trip;
    }

    public String getName(){
        return this.name;
    }

    public String getTrip(){
        return this.trip;
    }

}