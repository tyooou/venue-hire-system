package nz.ac.auckland.se281;

public class Music extends Service {

  public Music() {
    super("Music", "Default", 500);
  }

  @Override
  public void addCost(Booking booking) {
    // Apply service to booking.
    booking.setCost(2, 500);
  }
}