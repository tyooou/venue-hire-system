package nz.ac.auckland.se281;

/** You cannot modify this class! */
public class Types {

  public enum CateringType {
    BREAKFAST(15, "Breakfast"),
    LUNCH(20, "Lunch"),
    DINNER(30, "Dinner"),
    DRINKS(10, "Drinks"),
    TWO_COURSE_BL(45, "Two Course Breakfast/Lunch"),
    TWO_COURSE_LD(60, "Two Course Lunch/Dinner"),
    THREE_COURSE(75, "Three Course");

    private final int costPerPerson;
    private final String name;

    CateringType(int cost, String name) {
      this.costPerPerson = cost;
      this.name = name;
    }

    public int getCostPerPerson() {
      return this.costPerPerson;
    }

    public String getName() {
      return this.name;
    }
  }

  public enum FloralType {
    STANDARD(550, "Standard"),
    DELUXE(1000, "Deluxe");

    private final int cost;
    private final String name;

    FloralType(int cost, String name) {
      this.cost = cost;
      this.name = name;
    }

    public int getCost() {
      return this.cost;
    }

    public String getName() {
      return this.name;
    }
  }
}
