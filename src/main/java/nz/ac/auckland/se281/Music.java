package nz.ac.auckland.se281;

// Music extension of the Service object.
public class Music extends Service {

  // Create Service object with Music extension.
  public Music() {
    super("Music", "Default", 500);
  }

  // Apply music service to booking.
  public void addCost(Booking booking) {
    booking.setCost(2, 500);
  }
}
