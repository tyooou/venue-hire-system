package nz.ac.auckland.se281;

// Catering extension of the Service object.
public class Catering extends Service {

  // Create Service object with Catering extension.
  public Catering(Types.CateringType type) {
    super("Catering (" + type.getName() + ")", type.getName(), type.getCostPerPerson());
  }

  // Apply catering service to booking.
  public void addCost(Booking booking) {
    int costOverall = Integer.parseInt(booking.getCapacity()) * super.cost;
    booking.setCost(1, costOverall);
    booking.setCatering(super.type);
  }
}
