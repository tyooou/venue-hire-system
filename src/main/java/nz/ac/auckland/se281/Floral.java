package nz.ac.auckland.se281;

public class Floral extends Service {

  public Floral(Types.FloralType type) {
    super("Floral (" + type.getName() + ")", type.getName(), type.getCost());
  }

  @Override
  public void addCost(Booking booking) {
    // Apply service to booking.
    booking.setCost(3, super.cost);
    booking.setFloral(super.type);
  }
}