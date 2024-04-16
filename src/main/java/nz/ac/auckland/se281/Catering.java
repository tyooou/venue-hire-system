package nz.ac.auckland.se281;

public class Catering extends Service {

  public Catering(Types.CateringType type) {
    super("Catering (" + type.getName() + ")", type.getName(), type.getCostPerPerson());
  }

  @Override
  public void addCost(Booking booking) {
    // Find catering service fee.
    int costOverall = Integer.parseInt(booking.getCapacity()) * super.cost;

    // Apply service to booking.
    booking.setCost(1, costOverall);
    booking.setCatering(super.type);
  }
}