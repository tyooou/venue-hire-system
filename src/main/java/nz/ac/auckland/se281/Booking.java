package nz.ac.auckland.se281;
import java.util.stream.IntStream;

public class Booking {

  // Initialise booking variables.
  String date, reference, capacity;
  int[] cost = new int[4];

  public Booking(String date, String reference, String capacity, String hireFee) {
    // Set booking variables.
    this.date = date;
    this.reference = reference;
    this.capacity = capacity;

    // Set hire fee cost.
    cost[3] = Integer.parseInt(hireFee);
  }

  // Add service to cost ("type" represents a service in "cost", [Catering, Music, Floral, Hire Fee]).
  public void addService(int type, int cost) {
    this.cost[type] = cost;
  }

  // Get booking variables.
  public String getDate() { return date; }
  public String getReference() { return reference; }
  public String getCapacity() { return capacity; }
  public int[] getCost() { return cost; }

  // Get total cost.
  public int getTotalCost() { 
    return IntStream.of(cost).sum();
  }

}