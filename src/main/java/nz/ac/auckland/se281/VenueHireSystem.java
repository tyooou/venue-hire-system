package nz.ac.auckland.se281;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import nz.ac.auckland.se281.Types.CateringType;
import nz.ac.auckland.se281.Types.FloralType;

public class VenueHireSystem {
  // Venue database variables.
  private ArrayList<Venue> venueDatabase = new ArrayList<Venue>();
  private ArrayList<String> venueCodeList = new ArrayList<String>();
  private int venueDatabaseSize;

  // Initialise system date variables.
  private LocalDate systemDate;
  private boolean dateSet = false;

  public VenueHireSystem() {}

  public String convertLocalDateToString(LocalDate date) {
    // Intialise date formatter.
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    // Return LocalDate type as String type.
    return date.format(formatter);
  }

  public LocalDate convertStringToLocalDate(String date) {
    // Grab numerical values of String type date.
    String[] dateSplit = date.split("/");
    // // Intialise date formatter.
    String dateFormat = dateSplit[2] + "-" + dateSplit[1] + "-" + dateSplit[0];
    // Return String type as LocalDate type.
    return LocalDate.parse(dateFormat);
  }

  public void printVenues() {
    // Get current size of venue list.
    venueDatabaseSize = venueDatabase.size();

    // Set default grammar values.
    String quantityG = "are";
    String pluralG = "s";

    // Check if there are venues.
    if (venueDatabaseSize <= 0) {
      // Print error message.
      MessageCli.NO_VENUES.printMessage();
    } else {
      String numberOfVenues = Integer.toString(venueDatabaseSize);

      // If update grammar according to number.
      if (venueDatabaseSize == 1) {
        quantityG = "is";
        pluralG = "";
        numberOfVenues = "one";
      } else if (venueDatabaseSize == 2) {
        numberOfVenues = "two";
      } else if (venueDatabaseSize == 3) {
        numberOfVenues = "three";
      } else if (venueDatabaseSize == 4) {
        numberOfVenues = "four";
      } else if (venueDatabaseSize == 5) {
        numberOfVenues = "five";
      } else if (venueDatabaseSize == 6) {
        numberOfVenues = "six";
      } else if (venueDatabaseSize == 7) {
        numberOfVenues = "seven";
      } else if (venueDatabaseSize == 8) {
        numberOfVenues = "eight";
      } else if (venueDatabaseSize == 9) {
        numberOfVenues = "nine";
      }

      // Print the header for the list of available venues.
      MessageCli.NUMBER_VENUES.printMessage(quantityG, numberOfVenues, pluralG);

      // Print the list of available venues.
      for (Venue venue : venueDatabase) {

        // Check if the date has been set.
        if (dateSet) {

          // Intialise booking variables.
          ArrayList<String> bookedDates = venue.getBookedDates();
          String availableDate = convertLocalDateToString(systemDate);
          boolean flag = false;

          // Check if the today (system date) and future days until availability found.
          do {
            if (!bookedDates.contains(availableDate)) {
              flag = true;
            } else {
              availableDate =
                  convertLocalDateToString(convertStringToLocalDate(availableDate).plusDays(1));
            }
          } while (!flag);

          // Print venue.
          MessageCli.VENUE_ENTRY.printMessage(
              venue.getVenueName(),
              venue.getVenueCode(),
              venue.getCapacity(),
              venue.getHireFee(),
              availableDate);

        } else {
          // Print venue.
          MessageCli.VENUE_ENTRY.printMessage(
              venue.getVenueName(), venue.getVenueCode(), venue.getCapacity(), venue.getHireFee());
        }
      }
    }
  }

  public void createVenue(String venueName, String venueCode, String capacity, String hireFee) {
    // Get current size of venue list.
    venueDatabaseSize = venueDatabase.size();

    // Initialize boolean variables.
    boolean capacityFlag, hireFeeFlag;
    capacityFlag = hireFeeFlag = true;
    String numberMessage = "";

    try { // Checking if the capacity is a positive integer.
      int capacityInputInteger = Integer.parseInt(capacity);
      if (capacityInputInteger <= 0) { // Check if capacity is not positive.
        numberMessage = " positive";
        capacityFlag = false;
      }
    } catch (NumberFormatException nfe) { // Check if capacity is not an integer.
      capacityFlag = false;
    }

    try { // Checking if the hire fee is a positive integer.
      int hireFeeInteger = Integer.parseInt(hireFee);
      if (hireFeeInteger <= 0) { // Check is hire fee is not positive.
        numberMessage = " positive";
        hireFeeFlag = false;
      }
    } catch (NumberFormatException nfe) { // Check if hire fee is not an integer.
      hireFeeFlag = false;
    }

    if (venueName.length() == 0) { // If venue name input is empty.
      // Print "empty venue name" error message.
      MessageCli.VENUE_NOT_CREATED_EMPTY_NAME.printMessage();
    } else if (venueCodeList.contains(venueCode)) { // If there are duplicate venue codes.
      String venueNameOfMatch = venueDatabase.get(venueCodeList.indexOf(venueCode)).getVenueName();
      // Print "duplicate venue" error message.
      MessageCli.VENUE_NOT_CREATED_CODE_EXISTS.printMessage(venueCode, venueNameOfMatch);
    } else if (!capacityFlag) { // If the capacity input is invalid.
      // Print "invalid capacity" error message.
      MessageCli.VENUE_NOT_CREATED_INVALID_NUMBER.printMessage("capacity", numberMessage);
    } else if (!hireFeeFlag) { // If the hire fee input is invalid.
      // Print "invalid hire fee" error message.
      MessageCli.VENUE_NOT_CREATED_INVALID_NUMBER.printMessage("hire fee", numberMessage);
    } else {

      // Add new venue to database upon successful venue input.
      venueDatabase.add(
          new Venue(
              venueName, venueCode, capacity,
              hireFee)); // (note: current venue list size does not increase).
      venueCodeList.add(venueCode);

      // Print "venue added" confirmation message.
      MessageCli.VENUE_SUCCESSFULLY_CREATED.printMessage(venueName, venueCode);
    }
  }

  public void setSystemDate(String date) {
    // Initialise date variables.
    systemDate = convertStringToLocalDate(date);
    dateSet = true;

    // Print confirmation message.
    MessageCli.DATE_SET.printMessage(date);
  }

  public void printSystemDate() {
    // Check if date is set.
    if (!dateSet) {
      // Print error message.
      MessageCli.CURRENT_DATE.printMessage("not set");
    } else {
      // Print system date.
      MessageCli.CURRENT_DATE.printMessage(convertLocalDateToString(systemDate));
    }
  }

  public void makeBooking(String[] options) {
    // Get current size of venue list.
    venueDatabaseSize = venueDatabase.size();

    if (!dateSet) { // Check if system date is not set.
      MessageCli.BOOKING_NOT_MADE_DATE_NOT_SET.printMessage();
    } else {
      // Initialize boolean variable to check if date is in the past.
      boolean bookDateBoolean =
          (convertStringToLocalDate(options[1]).isAfter(systemDate)
              || convertStringToLocalDate(options[1]).isEqual(systemDate));

      if (venueDatabaseSize <= 0) { // If there are no venues.
        // Print "no venues" error message.
        MessageCli.BOOKING_NOT_MADE_NO_VENUES.printMessage();
      } else if (!bookDateBoolean) { // If the date is in the past.
        // Print "invalid date" error message.
        MessageCli.BOOKING_NOT_MADE_PAST_DATE.printMessage(
            options[1], convertLocalDateToString(systemDate));
      } else if (!venueCodeList.contains(options[0])) { // If venue doesn't exist.
        // Print "venue not found" error message.
        MessageCli.BOOKING_NOT_MADE_VENUE_NOT_FOUND.printMessage(options[0]);
      } else {

        // Get venue variables.
        Venue venue = venueDatabase.get(venueCodeList.indexOf(options[0]));
        String venueName = venue.getVenueName();
        ArrayList<String> bookedDates = venue.getBookedDates();

        if (bookedDates.contains(options[1])) { // If date already booked for venue.
          // Print "venue already booked" error message.
          MessageCli.BOOKING_NOT_MADE_VENUE_ALREADY_BOOKED.printMessage(venueName, options[1]);
        } else {
          String reference = BookingReferenceGenerator.generateBookingReference();
          int venueCapacity = Integer.parseInt(venue.getCapacity());
          int capacityInt = Integer.parseInt(options[3], 10);
          String capacityStr = options[3];

          if (capacityInt
              > venueCapacity) { // If booking capacity is greater than 100% of the venue capacity.
            capacityStr = venue.getCapacity(); // Adjust to 100% of the venue capacity.
            // Print "capacity adjusted" message.
            MessageCli.BOOKING_ATTENDEES_ADJUSTED.printMessage(
                options[3], capacityStr, venue.getCapacity());
          } else if (capacityInt
              < (venueCapacity
                  / 4)) { // If booking capacity is less than 25% of the venue capacity.
            capacityStr = String.valueOf(venueCapacity / 4); // Adjust to 25% of the venue capacity.
            // Print "capacity adjusted" message.
            MessageCli.BOOKING_ATTENDEES_ADJUSTED.printMessage(
                options[3], capacityStr, venue.getCapacity());
          }

          // Create booking.
          venue.bookVenue(
              options[1], reference, capacityStr, options[2], convertLocalDateToString(systemDate));

          // Print "booking made" confirmation message.
          MessageCli.MAKE_BOOKING_SUCCESSFUL.printMessage(
              reference, venueName, options[1], capacityStr);
        }
      }
    }
  }

  public void printBookings(String venueCode) {
    if (!venueCodeList.contains(venueCode)) { // If venue does not exist.
      // Print "venue not found" error message.
      MessageCli.PRINT_BOOKINGS_VENUE_NOT_FOUND.printMessage(venueCode);
    } else {
      // Get venue.
      Venue venue = venueDatabase.get(venueCodeList.indexOf(venueCode));
      MessageCli.PRINT_BOOKINGS_HEADER.printMessage(venue.getVenueName());

      // Get venue's bookings.
      ArrayList<Booking> bookings = venue.getBookings();

      if (bookings.isEmpty()) { // If there are no bookings.
        // Print "no bookings" error message.
        MessageCli.PRINT_BOOKINGS_NONE.printMessage(venue.getVenueName());
      } else {
        // Print venue's bookings.
        for (Booking booking : bookings) {
          MessageCli.PRINT_BOOKINGS_ENTRY.printMessage(booking.getReference(), booking.getDate());
        }
      }
    }
  }

  public Booking findReference(String reference, String serviceType) {
    // Search if reference exists.
    for (Venue venue : venueDatabase) {
      ArrayList<Booking> bookings = venue.getBookings();
      for (Booking booking : bookings) {
        if (booking.getReference().equals(reference)) {
          // Return the booking if found.
          return booking;
        }
      }
    }

    // Print error message based on service type.
    if (serviceType == "Invoice") {
      MessageCli.VIEW_INVOICE_BOOKING_NOT_FOUND.printMessage(reference);
    } else {
      MessageCli.SERVICE_NOT_ADDED_BOOKING_NOT_FOUND.printMessage(serviceType, reference);
    }

    // Return null if booking not found.
    return null;
  }

  public void addCateringService(String reference, CateringType cateringType) {
    // Get booking by reference.
    Booking booking = findReference(reference, "Catering");

    // If booking is found;
    if (booking != null) {
      CateringService cateringService = new CateringService(cateringType);
      cateringService.addCost(booking);
      cateringService.printConfirmation(booking);
    }
  }

  public void addServiceMusic(String reference) {
    // Get booking by reference.
    Booking booking = findReference(reference, "Music");

    // If booking is found.
    if (booking != null) {
      MusicService musicService = new MusicService();
      musicService.addCost(booking);
      musicService.printConfirmation(booking);
    }
  }

  public void addServiceFloral(String reference, FloralType floralType) {
    // Get booking by reference.
    Booking booking = findReference(reference, "Floral");

    if (booking != null) {
      FloralService floralService = new FloralService(floralType);
      floralService.addCost(booking);
      floralService.printConfirmation(booking);
    }
  }

  public void viewInvoice(String reference) {
    // Get booking by reference.
    Booking booking = findReference(reference, "Invoice");

    // If booking is found.
    if (booking != null) {
      int[] cost = booking.getCost();

      // Print top-half of invoice.
      MessageCli.INVOICE_CONTENT_TOP_HALF.printMessage(
          reference,
          booking.getEmail(),
          booking.getDateMade(),
          booking.getDate(),
          booking.getCapacity(),
          booking.getVenue());
      MessageCli.INVOICE_CONTENT_VENUE_FEE.printMessage(String.valueOf(cost[0]));

      if (cost[1] > 0) { // If catering service added.
        // Print catering service fee.
        MessageCli.INVOICE_CONTENT_CATERING_ENTRY.printMessage(
            booking.getCatering(), String.valueOf(cost[1]));
      }

      if (cost[2] > 0) { // If music service added.
        // Print music service fee.
        MessageCli.INVOICE_CONTENT_MUSIC_ENTRY.printMessage(String.valueOf(cost[2]));
      }

      if (cost[3] > 0) { // If floral service added.
        // Print floral service fee.
        MessageCli.INVOICE_CONTENT_FLORAL_ENTRY.printMessage(
            booking.getFloral(), String.valueOf(cost[3]));
      }

      // Print bottom-half of invoice.
      MessageCli.INVOICE_CONTENT_BOTTOM_HALF.printMessage(String.valueOf(booking.getTotalCost()));
    }
  }
}
