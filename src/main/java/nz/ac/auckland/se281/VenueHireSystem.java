package nz.ac.auckland.se281;

import nz.ac.auckland.se281.Types.CateringType;
import nz.ac.auckland.se281.Types.FloralType;
import java.util.ArrayList;

public class VenueHireSystem {
  // Venue database variables.
  ArrayList<Venue> venueDatabase = new ArrayList<Venue>();
  ArrayList<String> venueCodeList = new ArrayList<String>();
  int venueDatabaseSize;

  // System date variables.
  String systemDate = "not set";
  String[] splitSystemDate = new String[3];

  public VenueHireSystem() {}

  public void printVenues() {
    // Get current size of venue list.
    venueDatabaseSize = venueDatabase.size();

    // Set default grammar values for multiple venues in the venue list.
    String setQuantity = "are";
    String setPlural = "s";

    if (venueDatabaseSize <= 0) { // If there are no venues listed.
      MessageCli.NO_VENUES.printMessage();
    } else { // If there are venues.
      String venueListSizeString = Integer.toString(venueDatabaseSize);

      // If one venue, update grammar.
      if (venueDatabaseSize == 1) { 
        setQuantity = "is"; 
        setPlural = ""; 
        venueListSizeString = "one"; 
      }

      // Print list of available venues.
      MessageCli.NUMBER_VENUES.printMessage(setQuantity, venueListSizeString, setPlural);

      for (Venue item : venueDatabase) {
        MessageCli.VENUE_ENTRY.printMessage(item.venueName, item.venueCode, item.capacityInput, item.hireFeeInput);
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

  public void setSystemDate(String dateInput) {
    // Set system date to input date.
    systemDate = dateInput;

    // Format system date for printing.
    splitSystemDate = dateInput.split("/");

    // Print system date set message.
    MessageCli.DATE_SET.printMessage(dateInput);
  }

  public void printSystemDate() {
    // Print system date.
    MessageCli.CURRENT_DATE.printMessage(systemDate);
  }

  public void makeBooking(String[] options) {
    // Get current size of venue list.
    venueDatabaseSize = venueDatabase.size();

    // Initialize boolean variable for date validation.
    boolean dateValid = true;

    if (systemDate == "not set") { // Check if system date is not set.
      MessageCli.BOOKING_NOT_MADE_DATE_NOT_SET.printMessage();
    } else {

      // Split date input for comparison.
      String[] splitDate = options[1].split("/");

      for (int index = 2; index > -1; index--) { // Check if booking date is in the past.
        if (Integer.parseInt(splitDate[index], 10) < Integer.parseInt(splitSystemDate[index], 10)) {
         dateValid = false; 
         break;
        }
      }
      

      if (venueDatabaseSize <= 0) {
        MessageCli.BOOKING_NOT_MADE_NO_VENUES.printMessage();
      } else if (!dateValid) {
        MessageCli.BOOKING_NOT_MADE_PAST_DATE.printMessage(options[1], systemDate);
      } else if (!venueCodeList.contains(options[0])) {
          MessageCli.BOOKING_NOT_MADE_VENUE_NOT_FOUND.printMessage(options[0]);
      } else {
          Venue targetVenue = venueDatabase.get(venueCodeList.indexOf(options[0]));
          String bookedVenueName = targetVenue.getVenueName();

          if (targetVenue.getVenueBookedDates().contains(options[1])) {
            MessageCli.BOOKING_NOT_MADE_VENUE_ALREADY_BOOKED.printMessage(bookedVenueName, options[1]);
          } else {
            targetVenue.bookVenue(options[1]);
            MessageCli.MAKE_BOOKING_SUCCESSFUL.printMessage(BookingReferenceGenerator.generateBookingReference(), bookedVenueName, options[1], options[3]);
          }


      }
      
    }
    
  }

  public void printBookings(String venueCode) {
    // TODO implement this method
  }

  public void addCateringService(String bookingReference, CateringType cateringType) {
    // TODO implement this method
  }

  public void addServiceMusic(String bookingReference) {
    // TODO implement this method
  }

  public void addServiceFloral(String bookingReference, FloralType floralType) {
    // TODO implement this method
  }

  public void viewInvoice(String bookingReference) {
    // TODO implement this method
  }
}