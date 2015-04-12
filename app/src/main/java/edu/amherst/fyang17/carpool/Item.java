package edu.amherst.fyang17.carpool;

/**
 * Created by Administrator on 4/11/2015.
 */
public class Item {

    public String name;
    public String trip;
    public int seats;
    public int available;

    public Item(String name, String trip, int seats, int available) {
        super();
        this.name = name;
        this.trip = trip;
        this.seats = seats;
        this.available = available;
    }

    public Item(String name, String trip) {
        super();
        this.name = name;
        this.trip = trip;
        this.seats = 4;
        this.available = 4;
    }
    public int getSeats() { return this.seats; }

    public int getAvailable() { return this.available; }

    public String getName(){
        return this.name;
    }

    public String getTrip(){
        return this.trip;
    }

}