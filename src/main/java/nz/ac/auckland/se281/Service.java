package nz.ac.auckland.se281;

public abstract class Service {
  String name, type;
  int cost;

  public Service(String name, String type, int cost) {
    this.name = name;
    this.type = type;
    this.cost = cost;
  }

  public abstract void addCost(Booking booking);
  public void printConfirmation(Booking booking) {
    MessageCli.ADD_SERVICE_SUCCESSFUL.printMessage(name, booking.getReference());
  };
  




  
}
