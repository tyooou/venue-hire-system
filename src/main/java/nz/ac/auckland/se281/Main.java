package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import nz.ac.auckland.se281.Types.CateringType;
import nz.ac.auckland.se281.Types.FloralType;

/** You cannot modify this class! */
public class Main {

  public enum Command {
    // Task 1 commands
    PRINT_VENUES(0, "Print details of all the venues"), //
    CREATE_VENUE(
        4,
        "Create a new venue with the given <VENUE_NAME>, <VENUE_CODE>, <VENUE_CAPACITY>, and"
            + " <HIRE_FEE>"), //

    // Task 2 commands
    SET_DATE(1, "Set the system's date to the specified date in DD/MM/YYYY format"), //
    PRINT_DATE(0, "Print the system's current date"), //
    MAKE_BOOKING(
        0,
        "Request a new booking",
        "Venue code",
        "Requested date in DD/MM/YYYY format",
        "Customer email",
        "Number of attendees"), //

    PRINT_BOOKINGS(1, "Print all bookings for the specified <VENUE_CODE>"), //

    // Task 3 commands
    ADD_CATERING(
        1,
        "Add catering service to the specified <BOOKING_REFERENCE>",
        "Select type (B/L/D/X/BL/LD/BLD)"), //
    ADD_MUSIC(1, "Add music service to the specified <BOOKING_REFERENCE>"), //
    ADD_FLORAL(
        1,
        "Add floral service to the specified <BOOKING_REFERENCE>",
        "Upgrade to deluxe? (y/n)"), //
    VIEW_INVOICE(1, "View full invoice details for the specified <BOOKING_REFERENCE>"), //

    // System commands
    HELP(0, "Print usage"),
    EXIT(0, "Exit the application");

    private final int numArgs;
    private final String message;
    private final String[] optionPrompts;

    private Command(final int numArgs, final String message) {
      this(numArgs, message, new String[] {});
    }

    private Command(final int numArgs, final String message, final String... optionPrompts) {
      this.numArgs = numArgs;
      this.message = message;
      this.optionPrompts = optionPrompts;
    }

    public boolean hasArguments() {
      return numArgs > 0;
    }

    public int getNumArgs() {
      return numArgs;
    }

    public boolean hasOptions() {
      return optionPrompts.length > 0;
    }

    public int getNumOptions() {
      return optionPrompts.length;
    }

    public String getOptionPrompt(final int index) {
      return optionPrompts[index];
    }
  }

  private static final String COMMAND_PREFIX = "281-venue-hire> ";

  public static void main(final String[] args) {
    new Main(new Scanner(System.in), new VenueHireSystem()).start(false);
  }

  public static String help() {
    final StringBuilder sb = new StringBuilder();

    for (final Command command : Command.values()) {
      sb.append(command).append("\t");

      // Add extra padding to align the argument counts
      // if the command name is less than the tab width.
      if (command.toString().length() < 8) {
        sb.append("\t");
      }

      if (command.numArgs > 0) {
        sb.append("[")
            .append(command.numArgs)
            .append(" argument" + (command.numArgs > 1 ? "s" : "") + "]");
      } else {
        sb.append("[no args]");
      }

      sb.append("\t").append(command.message).append(System.lineSeparator());
    }

    return sb.toString();
  }

  public static void printBanner() {
    // https://patorjk.com/software/taag/
    // https://www.freeformatter.com/java-dotnet-escape.html#before-output

    StringBuilder buf = new StringBuilder();
    buf.append(
        "\r\n"
            + "  .oooo.    .ooooo.     .o       oooooo     oooo                                    "
            + "              ooooo   ooooo  o8o                     \r\n"
            + ".dP\"\"Y88b  d88'   `8. o888        `888.     .8'                                   "
            + "                `888'   `888'  `\"'                     \r\n"
            + "      ]8P' Y88..  .8'  888         `888.   .8'    .ooooo.  ooo. .oo.   oooo  oooo  "
            + " .ooooo.        888     888  oooo  oooo d8b  .ooooo.  \r\n"
            + "    .d8P'   `88888b.   888          `888. .8'    d88' `88b `888P\"Y88b  `888  `888 "
            + " d88' `88b       888ooooo888  `888  `888\"\"8P d88' `88b \r\n"
            + "  .dP'     .8'  ``88b  888           `888.8'     888ooo888  888   888   888   888 "
            + " 888ooo888       888     888   888   888     888ooo888 \r\n"
            + ".oP     .o `8.   .88P  888            `888'      888    .o  888   888   888   888 "
            + " 888    .o       888     888   888   888     888    .o \r\n"
            + "8888888888  `boood8'  o888o            `8'       `Y8bod8P' o888o o888o  `V88V\"V8P'"
            + " `Y8bod8P'      o888o   o888o o888o d888b    `Y8bod8P' \r\n"
            + "                                                                                    "
            + "                                                     \r\n");
    System.out.println(buf.toString());
  }

  private final Scanner scanner;

  private final VenueHireSystem greetings;

  public Main(final Scanner scanner, final VenueHireSystem greetings) {
    this.scanner = scanner;
    this.greetings = greetings;
  }

  public void start() {
    start(true);
  }

  public void start(boolean debug) {
    printBanner();
    System.out.println(help());

    String command;

    // Prompt and process commands until the exit command.
    do {
      System.out.print(COMMAND_PREFIX);
      command = scanner.nextLine().trim();
      if (debug) {
        System.out.println(command);
      }
    } while (processCommand(command, debug));
  }

  private static String[] splitWithQuotes(String input) {
    List<String> items = new ArrayList<>();

    // Match based on spaces, while respecting words surrounded by single quotes
    Matcher matcher = Pattern.compile("('(?:[^']+|'')*'\\S*|\\S+)").matcher(input);

    while (matcher.find()) {
      String matched = matcher.group(1);

      // Remove the surrounding quotes
      if (matched.startsWith("'") && matched.endsWith("'")) {
        matched = matched.substring(1, matched.length() - 1);
      }

      items.add(matched);
    }

    return items.toArray(new String[0]);
  }

  private boolean processCommand(String input, boolean debug) {
    // Remove whitespace at the beginning and end of the input.
    input = input.trim();

    final String[] args = splitWithQuotes(input);

    // In case the user pressed "Enter" without typing anything
    if (args.length == 0) {
      return true;
    }

    // Allow any case, and dashes to be used instead of underscores.
    final String commandStr = args[0].toUpperCase().replaceAll("-", "_");

    final Command command;

    try {
      // Command names correspond to the enum names.
      command = Command.valueOf(commandStr);
    } catch (final Exception e) {
      MessageCli.COMMAND_NOT_FOUND.printMessage(commandStr);
      return true;
    }

    // Check if the number of arguments provided don't match up as required for that command
    if (!checkArgs(command, args)) {
      MessageCli.WRONG_ARGUMENT_COUNT.printMessage(
          String.valueOf(command.getNumArgs()), command.getNumArgs() != 1 ? "s" : "", commandStr);
      return true;
    }

    switch (command) {
      case PRINT_VENUES:
        greetings.printVenues();
        break;
      case CREATE_VENUE:
        greetings.createVenue(args[1], args[2], args[3], args[4]);
        break;
      case SET_DATE:
        greetings.setSystemDate(args[1]);
        break;
      case PRINT_DATE:
        greetings.printSystemDate();
        break;
      case MAKE_BOOKING:
        greetings.makeBooking(processOptions(command, debug));
        break;
      case PRINT_BOOKINGS:
        greetings.printBookings(args[1]);
        break;
      case ADD_CATERING:
        greetings.addCateringService(args[1], processCateringOptions(command, debug));
        break;
      case ADD_MUSIC:
        greetings.addServiceMusic(args[1]);
        break;
      case ADD_FLORAL:
        greetings.addServiceFloral(args[1], processFloralOptions(command, debug));
        break;
      case VIEW_INVOICE:
        greetings.viewInvoice(args[1]);
        break;
      case EXIT:
        MessageCli.END.printMessage();
        // Signal that the program should exit.
        return false;
      case HELP:
        System.out.println(help());
        break;
    }

    // Signal that another command is expected.
    return true;
  }

  private String[] processOptions(final Command command, boolean debug) {
    final String[] options = new String[command.getNumOptions()];

    // Prompt the user for each option
    for (int i = 0; i < command.getNumOptions(); i++) {
      System.out.print("\t" + command.getOptionPrompt(i) + ": ");
      options[i] = scanner.nextLine().trim();

      // Print out the response in case we are in automated testing mode
      if (debug) {
        System.out.println(options[i]);
      }
    }
    return options;
  }

  private CateringType processCateringOptions(final Command command, boolean debug) {

    // Ask the user for the options needed
    String[] options = processOptions(command, debug);

    // Return the enum version of that option
    switch (options[0].toUpperCase()) {
      case "B":
        return CateringType.BREAKFAST;
      case "L":
        return CateringType.LUNCH;
      case "D":
        return CateringType.DINNER;
      case "X":
        return CateringType.DRINKS;
      case "BL":
        return CateringType.TWO_COURSE_BL;
      case "LD":
        return CateringType.TWO_COURSE_LD;
      case "BLD":
        return CateringType.THREE_COURSE;
      default:
        return null;
    }
  }

  private FloralType processFloralOptions(final Command command, boolean debug) {
    String[] options = processOptions(command, debug);

    switch (options[0].toUpperCase()) {
      case "Y":
        return FloralType.DELUXE;
      default:
        return FloralType.STANDARD;
    }
  }

  private boolean checkArgs(final Command command, final String[] args) {
    return command.getNumArgs() == args.length - 1;
  }
}
