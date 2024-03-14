package nz.ac.auckland.se281;
public class Venue {
  String venueName, venueCode, capacityInput, hireFeeInput;

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

  

}
