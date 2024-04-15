package nz.ac.auckland.se281;

import nz.ac.auckland.se281.Types.CateringType;
import nz.ac.auckland.se281.Types.FloralType;

import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class VenueHireSystem {
  // Venue database variables.
  ArrayList<Venue> venueDatabase = new ArrayList<Venue>();
  ArrayList<String> venueCodeList = new ArrayList<String>();
  int venueDatabaseSize;

  // System date variable.
  LocalDate systemDate;
  boolean dateSet = false;

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
    String setQuantity = "are";
    String setPlural = "s";

    // Check if there are venues.
    if (venueDatabaseSize <= 0) {
      MessageCli.NO_VENUES.printMessage();
    } else {
      String venueListSizeString = Integer.toString(venueDatabaseSize);

      // If update grammar according to number.
      if (venueDatabaseSize == 1) { 
        setQuantity = "is"; 
        setPlural = ""; 
        venueListSizeString = "one"; 
      } else if (venueDatabaseSize == 2) { 
        venueListSizeString = "two"; 
      } else if (venueDatabaseSize == 3) { 
        venueListSizeString = "three"; 
      } else if (venueDatabaseSize == 4) { 
        venueListSizeString = "four"; 
      } else if (venueDatabaseSize == 5) { 
        venueListSizeString = "five"; 
      } else if (venueDatabaseSize == 6) { 
        venueListSizeString = "six"; 
      } else if (venueDatabaseSize == 7) { 
        venueListSizeString = "seven"; 
      } else if (venueDatabaseSize == 8) { 
        venueListSizeString = "eight"; 
      } else if (venueDatabaseSize == 9) { 
        venueListSizeString = "nine"; 
      }

      // Print the header for the list of available venues.
      MessageCli.NUMBER_VENUES.printMessage(setQuantity, venueListSizeString, setPlural);

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
              availableDate = convertLocalDateToString(convertStringToLocalDate(availableDate).plusDays(1)); 
            }
          } while (!flag);
          MessageCli.VENUE_ENTRY.printMessage(venue.venueName, venue.venueCode, venue.capacity, venue.hireFee, availableDate);
        } else {
          MessageCli.VENUE_ENTRY.printMessage(venue.venueName, venue.venueCode, venue.capacity, venue.hireFee);
        }
      }
    }
  }

  public void createVenue(String venueName, String venueCode, String capacityInput, String hireFeeInput) {
      // Get current size of venue list.
      venueDatabaseSize = venueDatabase.size();

      // Initialize boolean variables for checking if capacity and hire fee are numbers.
      boolean isCapacityNumber, isHireFeeNumber;
      isCapacityNumber = isHireFeeNumber = true;
      String numberMessage = "";

      try { // Checking if the capacity is a positive integer.
        int capacityInputInteger = Integer.parseInt(capacityInput); 
        if (capacityInputInteger <= 0) { // Check if capacity is not positive.
          numberMessage = " positive"; 
          isCapacityNumber = false; 
        }
      } catch (NumberFormatException nfe) { // Check if capacity is not an integer.
        isCapacityNumber = false; 
      }

      try { // Checking if the hire fee is a positive integer.
        int hireFeeInteger = Integer.parseInt(hireFeeInput); 
        if (hireFeeInteger <= 0) { // Check is hire fee is not positive.
          numberMessage = " positive"; 
          isHireFeeNumber = false;
        }
      } catch (NumberFormatException nfe) { // Check if hire fee is not an integer.
        isHireFeeNumber = false; 
      }
      
      if (venueName.length() == 0) { // If venue name input is empty.
        MessageCli.VENUE_NOT_CREATED_EMPTY_NAME.printMessage();
      } else if (venueCodeList.contains(venueCode)) { // If there are duplicate venue codes.
        String venueNameOfMatch = venueDatabase.get(venueCodeList.indexOf(venueCode)).getVenueName();
        MessageCli.VENUE_NOT_CREATED_CODE_EXISTS.printMessage(venueCode, venueNameOfMatch);
      } else if (!isCapacityNumber) { // If the capacity input is invalid.
        MessageCli.VENUE_NOT_CREATED_INVALID_NUMBER.printMessage("capacity", numberMessage);
      } else if (!isHireFeeNumber) { // If the hire fee input is invalid.
        MessageCli.VENUE_NOT_CREATED_INVALID_NUMBER.printMessage("hire fee", numberMessage);
      } else { // Add new venue to database upon successful venue input.
        venueDatabase.add(new Venue(venueName, venueCode, capacityInput, hireFeeInput)); // (note: current venue list size does not increase).
        venueCodeList.add(venueDatabase.get(venueDatabaseSize).getVenueCode());
        MessageCli.VENUE_SUCCESSFULLY_CREATED.printMessage(venueName, venueCode);
      }
  }

  public void setSystemDate(String date) {
    // Set system date.
    systemDate = convertStringToLocalDate(date);
    dateSet = true;

    // Print system date set message.
    MessageCli.DATE_SET.printMessage(date);
  }

  public void printSystemDate() {
    // Check if date is set.
    if (!dateSet) {
      MessageCli.CURRENT_DATE.printMessage("not set");
    } else {
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
      boolean bookDateBoolean = (convertStringToLocalDate(options[1]).isAfter(systemDate) || convertStringToLocalDate(options[1]).isEqual(systemDate));

      if (venueDatabaseSize <= 0) { // If there are no venues.
        MessageCli.BOOKING_NOT_MADE_NO_VENUES.printMessage();
      } else if (!bookDateBoolean) { // If the date is in the past.
        MessageCli.BOOKING_NOT_MADE_PAST_DATE.printMessage(options[1], convertLocalDateToString(systemDate));
      } else if (!venueCodeList.contains(options[0])) {
        MessageCli.BOOKING_NOT_MADE_VENUE_NOT_FOUND.printMessage(options[0]);
      } else {

        Venue venue = venueDatabase.get(venueCodeList.indexOf(options[0]));
        String venueName = venue.getVenueName();
        ArrayList<String> bookedDates = venue.getBookedDates();

        if (bookedDates.contains(options[1])) {
          MessageCli.BOOKING_NOT_MADE_VENUE_ALREADY_BOOKED.printMessage(venueName, options[1]);
        } else {
          int bookedCapacity = Integer.parseInt(options[3], 10);
          int targetCapacity = Integer.parseInt(venue.getCapacity());
          String bookedCapacityString = options[3];
          String bookedReference = BookingReferenceGenerator.generateBookingReference();

          if (bookedCapacity > targetCapacity) {
            bookedCapacityString = venue.getCapacity();
            MessageCli.BOOKING_ATTENDEES_ADJUSTED.printMessage(options[3], bookedCapacityString, venue.getCapacity());
          } else if (bookedCapacity <  targetCapacity / 4) {
            bookedCapacityString = String.valueOf(targetCapacity / 4);
            MessageCli.BOOKING_ATTENDEES_ADJUSTED.printMessage(options[3], bookedCapacityString, venue.getCapacity());
          }
          venue.bookVenue(options[1], bookedReference, bookedCapacityString);
          MessageCli.MAKE_BOOKING_SUCCESSFUL.printMessage(bookedReference, venueName, options[1], bookedCapacityString);
        }
      }
    }
    
  }

  public void printBookings(String venueCode) {
    if (!venueCodeList.contains(venueCode)) {
      MessageCli.PRINT_BOOKINGS_VENUE_NOT_FOUND.printMessage(venueCode);
    } else {
      Venue venue = venueDatabase.get(venueCodeList.indexOf(venueCode));
      MessageCli.PRINT_BOOKINGS_HEADER.printMessage(venue.getVenueName());


      ArrayList<Booking> bookings = venue.getBookings();

      if (bookings.isEmpty()) {
        MessageCli.PRINT_BOOKINGS_NONE.printMessage(venue.getVenueName());
      } else {
        for (Booking booking : bookings) {
          MessageCli.PRINT_BOOKINGS_ENTRY.printMessage(booking.getReference(), booking.getDate());
        }
      }
    }
  }

  public boolean checkReference(String reference, String serviceType) {
    // Search if reference exists.
    for (Venue venue : venueDatabase) {
      ArrayList<Booking> bookings = venue.getBookings();
      for (Booking booking : bookings) {
        if (booking.getReference() == reference) {
          return true;
        }
      }
    }
    
    // Print specific prompt based on service type.
    if (serviceType == "Invoice") {
      MessageCli.VIEW_INVOICE_BOOKING_NOT_FOUND.printMessage(reference);
    } else { 
      MessageCli.SERVICE_NOT_ADDED_BOOKING_NOT_FOUND.printMessage(serviceType, reference);
    }

    return false;
  }

  public void addCateringService(String bookingReference, CateringType cateringType) {
    if (checkReference(bookingReference, "Catering")) {
      
    }
  }

  public void addServiceMusic(String bookingReference) {
    if (checkReference(bookingReference, "Music")) {
      MessageCli.ADD_SERVICE_SUCCESSFUL.printMessage("Music", bookingReference);
    }
  }

  public void addServiceFloral(String bookingReference, FloralType floralType) {
    if (checkReference(bookingReference, "Floral")) {
      
    }
  }

  public void viewInvoice(String bookingReference) {
    if (checkReference(bookingReference, "Invoice")) {
      MessageCli.INVOICE_CONTENT_TOP_HALF.printMessage(bookingReference);

      
    }
    
  }
}