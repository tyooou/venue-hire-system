package nz.ac.auckland.se281;

// Floral extension of the Service object.
public class FloralService extends Service {

  // Create Service object with Floral extension.
  public FloralService(Types.FloralType type) {
    super("Floral (" + type.getName() + ")", type.getName(), type.getCost());
  }

  // Apply floral service to booking.
  public void addCost(Booking booking) {
    booking.setCost(3, super.cost);
    booking.setFloral(super.type);
  }
}
