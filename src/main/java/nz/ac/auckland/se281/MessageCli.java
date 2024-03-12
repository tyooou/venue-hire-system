package nz.ac.auckland.se281;

/** You cannot modify this class! */
public enum MessageCli {
  COMMAND_NOT_FOUND(
      "Error! Command not found! (run 'help' for the list of available commands): \"%s\""),
  WRONG_ARGUMENT_COUNT(
      "Error! Incorrect number of arguments provided. Expected %s argument%s for the \"%s\""
          + " command"),

  // Task 1 messages
  NO_VENUES("There are no venues in the system. Please create a venue first."),
  NUMBER_VENUES("There %s %s venue%s in the system:"),
  VENUE_SUCCESSFULLY_CREATED("Successfully created venue '%s' (%s)."),
  VENUE_NOT_CREATED_CODE_EXISTS("Venue not created: code '%s' is already used for '%s'."),
  VENUE_NOT_CREATED_EMPTY_NAME("Venue not created: venue name must not be empty."),
  VENUE_NOT_CREATED_INVALID_NUMBER("Venue not created: %s must be a%s number."),
  VENUE_ENTRY("  * %s (%s) - %s people - $%s base hire fee. Next available on %s"),

  // Task 2 messages
  DATE_SET("System date set to %s."),
  CURRENT_DATE("Current system date is %s."),
  BOOKING_NOT_MADE_DATE_NOT_SET("Booking not made: system date not set. Set the date first."),
  BOOKING_NOT_MADE_NO_VENUES(
      "Booking not made: there are no venues in the system. Create one first."),
  BOOKING_NOT_MADE_VENUE_NOT_FOUND("Booking not made: there is no venue with code '%s'."),
  BOOKING_NOT_MADE_VENUE_ALREADY_BOOKED("Booking not made: venue '%s' is already booked on %s."),
  BOOKING_NOT_MADE_PAST_DATE("Booking not made: '%s' is in the past (system date is %s)."),
  BOOKING_ATTENDEES_ADJUSTED(
      "Number of attendees adjusted from %s to %s, as the venue capacity is %s."),
  MAKE_BOOKING_SUCCESSFUL("Successfully created booking '%s' for '%s' on %s for %s people."),
  PRINT_BOOKINGS_VENUE_NOT_FOUND("Nothing to print: there is no venue with code '%s'."),
  PRINT_BOOKINGS_HEADER("Bookings for '%s':"),
  PRINT_BOOKINGS_NONE("  * No bookings for '%s'."),
  PRINT_BOOKINGS_ENTRY("  * '%s' on %s"),

  // Task 3 messages
  SERVICE_NOT_ADDED_BOOKING_NOT_FOUND(
      "%s service not added: there is no booking with reference '%s' in the system."),
  ADD_SERVICE_SUCCESSFUL("Successfully added %s service to booking '%s'."),
  VIEW_INVOICE_BOOKING_NOT_FOUND(
      "Invoice not printed: there is no booking with reference '%s' in the system."),

  // Invoice content
  INVOICE_CONTENT_TOP_HALF(
      "\n===============================================================\n"
          + "                          INVOICE\n"
          + "           -------------------------------------\n\n"
          + "Booking Reference: #%s\n\n"
          + "Booking Details:\n"
          + "Customer Email: %s\n"
          + "Date of Booking: %s\n\n"
          + "Event Details:\n"
          + "Party Date: %s\n"
          + "Number of Guests: %s\n"
          + "Venue: %s\n\n"
          + "Cost Breakdown:"),

  INVOICE_CONTENT_VENUE_FEE("  * Venue hire - $%s"),
  INVOICE_CONTENT_CATERING_ENTRY("  * Catering (%s) - $%s"),
  INVOICE_CONTENT_MUSIC_ENTRY("  * Music - $%s"),
  INVOICE_CONTENT_FLORAL_ENTRY("  * Floral (%s) - $%s"),

  INVOICE_CONTENT_BOTTOM_HALF(
      "Total Amount: $%s\n\n"
          + "Thank you for choosing 281 Venue Hire!\n"
          + "For any inquiries, please contact support@281venuehire.co.nz.\n"
          + "===============================================================\n"),

  END("You closed the terminal. Goodbye.");

  private final String msg;

  private MessageCli(final String msg) {
    this.msg = msg;
  }

  public String getMessage(final String... args) {
    String tmpMessage = msg;

    for (final String arg : args) {
      tmpMessage = tmpMessage.replaceFirst("%s", arg);
    }

    return tmpMessage;
  }

  public void printMessage(final String... args) {
    System.out.println(getMessage(args));
  }

  @Override
  public String toString() {
    return msg;
  }
}
