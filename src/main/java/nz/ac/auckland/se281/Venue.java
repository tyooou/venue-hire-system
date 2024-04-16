package nz.ac.auckland.se281;
import java.util.ArrayList;

public class Venue {
  // Intialise venue variables.
  private String venueName, venueCode, capacity, hireFee;
  private ArrayList<Booking> bookings = new ArrayList<Booking>();
  private ArrayList<String> bookedDates = new ArrayList<String>();

  // Set venue variables.
  public Venue(String venueName, String venueCode, String capacity, String hireFee) {
    this.venueName = venueName;
    this.venueCode = venueCode;
    this.capacity = capacity;
    this.hireFee = hireFee;
  }

  // Get venue variables.
  public String getVenueName() { return venueName; }
  public String getVenueCode() { return venueCode; }
  public String getCapacity() { return capacity; }
  public String getHireFee() { return hireFee; }

  // Get booking variables.
  public ArrayList<Booking> getBookings() { return bookings; }
  public ArrayList<String> getBookedDates() { return bookedDates; }

  // Create booking for venue.
  public void bookVenue(String date, String reference, String capacity, String email, String dateMade) { 
    bookedDates.add(date);
    bookings.add(new Booking(venueName, date, reference, capacity, hireFee, email, dateMade));
  }
}
