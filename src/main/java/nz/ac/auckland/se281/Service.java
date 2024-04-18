package nz.ac.auckland.se281;

public abstract class Service {
  // Initialise service variables
  protected String name, type;
  protected int cost;

  // Initialise service variables with Service object.
  public Service(String name, String type, int cost) {
    this.name = name;
    this.type = type;
    this.cost = cost;
  }

  // Intialise cost and service confirmation methods.
  public abstract void addCost(Booking booking);

  public void printConfirmation(Booking booking) {
    MessageCli.ADD_SERVICE_SUCCESSFUL.printMessage(name, booking.getReference());
  }
}
