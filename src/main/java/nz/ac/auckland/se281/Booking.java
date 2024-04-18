package nz.ac.auckland.se281;

import java.util.stream.IntStream;

public class Booking {

  // Initialise booking variables.
  private String venue, date, reference, capacity, email, dateMade, catering, floral;
  private int[] cost = new int[4];

  public Booking(
      String venue,
      String date,
      String reference,
      String capacity,
      String hireFee,
      String email,
      String dateMade) {
    // Set booking variables.
    this.venue = venue;
    this.date = date;
    this.reference = reference;
    this.capacity = capacity;
    this.email = email;
    this.dateMade = dateMade;

    // Set hire fee cost.
    cost[0] = Integer.parseInt(hireFee);
  }

  // Add service to cost ("type" represents a service in "cost", [Hire Fee, Catering, Music,
  // Floral]).
  public void addService(int type, int cost) {
    this.cost[type] = cost;
  }

  // Get booking variables.
  public String getVenue() {
    return venue;
  }

  public String getDate() {
    return date;
  }

  public String getReference() {
    return reference;
  }

  public String getCapacity() {
    return capacity;
  }

  public String getEmail() {
    return email;
  }

  public String getDateMade() {
    return dateMade;
  }

  public String getCatering() {
    return catering;
  }

  public String getFloral() {
    return floral;
  }

  public int[] getCost() {
    return cost;
  }

  // Allow cost to be set.
  public void setCost(int index, int fee) {
    cost[index] = fee;
  }

  public void setCatering(String type) {
    catering = type;
  }

  public void setFloral(String type) {
    floral = type;
  }

  // Get total cost.
  public int getTotalCost() {
    return IntStream.of(cost).sum();
  }
}
