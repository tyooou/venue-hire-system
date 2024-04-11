package nz.ac.auckland.se281;
import java.util.ArrayList;

public class Venue {
  String venueName, venueCode, capacityInput, hireFeeInput;
  ArrayList<String> venueBookedDates = new ArrayList<String>();
  ArrayList<String> venueBookedCapacity = new ArrayList<String>();
  ArrayList<String> venueBookedReferences = new ArrayList<String>();

  public Venue(String venueName, String venueCode, String capacityInput, String hireFeeInput) {
    this.venueName = venueName;
    this.venueCode = venueCode;
    this.capacityInput = capacityInput;
    this.hireFeeInput = hireFeeInput;
  }

  public String getVenueName() { return venueName; }
  public String getVenueCode() { return venueCode; }
  public String getCapacityInput() { return capacityInput; }
  public String getHireFeeInput() { return hireFeeInput; }
  public ArrayList<String> getVenueBookedDates() { return venueBookedDates; }
  public ArrayList<String> getVenueBookedReferences() { return venueBookedReferences; }
  public void bookVenue(String bookDate, String bookCapacity, String bookReference) { 
    venueBookedDates.add(bookDate);
    venueBookedCapacity.add(bookCapacity);
    venueBookedReferences.add(bookReference);

  }

  
  

}
