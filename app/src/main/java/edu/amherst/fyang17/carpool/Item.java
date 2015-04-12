package edu.amherst.fyang17.carpool;

/**
 * Created by Administrator on 4/11/2015.
 */
public class Item {

    public String name;
    public String trip;
    public String date;
    public int seats;
    public int available;

    public Item(String name, String trip, String date, int seats) {
        super();
        this.name = name;
        this.trip = trip;
        this.date = date;
        this.seats = seats;
        this.available = seats;
    }

    public String getName(){
        return this.name;
    }

    public String getTrip(){
        return this.trip;
    }

    public String getDate() { return this.date; }

    public int getSeats() { return this.seats; }

    public int getAvailable() { return this.available; }
}