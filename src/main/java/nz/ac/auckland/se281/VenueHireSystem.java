package nz.ac.auckland.se281;

import nz.ac.auckland.se281.Types.CateringType;
import nz.ac.auckland.se281.Types.FloralType;
import java.util.ArrayList;

public class VenueHireSystem {
  ArrayList<Venue> venueDatabase = new ArrayList<Venue>();
  int venueListSize = venueDatabase.size();

  String systemDate = "not set";
  String[] splitSystemDate;

  public VenueHireSystem() {}

  public void printVenues() {
    String setQuantity = "are";
    String setPlural = "s";

    if (venueListSize <= 0) { // If there are no venues listed.
      MessageCli.NO_VENUES.printMessage();
    } else {
      String venueListSizeString = Integer.toString(venueListSize);

      // If one venue, update grammar.
      if (venueListSize == 1) { setQuantity = "is"; setPlural = ""; venueListSizeString = "one"; }
      MessageCli.NUMBER_VENUES.printMessage(setQuantity, venueListSizeString, setPlural);

      for (Venue item : venueDatabase) { // Print list of available venues.
        MessageCli.VENUE_ENTRY.printMessage(item.venueName, item.venueCode, item.capacityInput, item.hireFeeInput);
      }
    }
  }

  public void createVenue(String venueName, String venueCode, String capacityInput, String hireFeeInput) {
      ArrayList<String> venueCodeList = new ArrayList<String>();
      boolean isCapacityNumber, isHireFeeNumber;
      isCapacityNumber = isHireFeeNumber = true;
      String numberMessage = "";

      for (Venue item : venueDatabase) { // Create list of venue codes (to check if duplicates).
        venueCodeList.add(item.getVenueCode());
      }

      // Check is capacity and hire fee inputs are valid.
      try { int capacityInputInteger = Integer.parseInt(capacityInput); if (capacityInputInteger <= 0) {numberMessage = " positive"; isCapacityNumber = false; }} catch (NumberFormatException nfe) { isCapacityNumber = false; }
      try { int hireFeeInteger = Integer.parseInt(hireFeeInput); if (hireFeeInteger <= 0) {numberMessage = " positive"; isHireFeeNumber = false;}} catch (NumberFormatException nfe) { isHireFeeNumber = false; }
      
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
        venueDatabase.add(new Venue(venueName, venueCode, capacityInput, hireFeeInput));
        venueListSize = venueDatabase.size();
        MessageCli.VENUE_SUCCESSFULLY_CREATED.printMessage(venueName, venueCode);
      }
  }

  public void setSystemDate(String dateInput) {
    systemDate = dateInput;
    splitSystemDate = dateInput.split("/");
    MessageCli.DATE_SET.printMessage(dateInput);
  }

  public void printSystemDate() {
    MessageCli.CURRENT_DATE.printMessage(systemDate);
  }

  public void makeBooking(String[] options) {
    ArrayList<String> venueCodeList = new ArrayList<String>();
    String[] splitDate = options[1].split("/");
    boolean dateValid = true;

    for (Venue item : venueDatabase) {
      venueCodeList.add(item.getVenueCode());
    }

    if (systemDate == "not set") {
      MessageCli.BOOKING_NOT_MADE_DATE_NOT_SET.printMessage();
    } else {

      for (int index = 2; index > -1; index--) {
        if (Integer.parseInt(splitDate[index], 10) < Integer.parseInt(splitSystemDate[index], 10)) {
         dateValid = false; 
         break;
        }
      }

      if (venueListSize <= 0) {
        MessageCli.BOOKING_NOT_MADE_NO_VENUES.printMessage();
      } else if (!dateValid) {
        MessageCli.BOOKING_NOT_MADE_PAST_DATE.printMessage(options[1], systemDate);
      } else if (!venueCodeList.contains(options[0])) {
          MessageCli.BOOKING_NOT_MADE_VENUE_NOT_FOUND.printMessage(options[0]);
      } else {
          String bookedVenueName = venueDatabase.get(venueCodeList.indexOf(options[0])).getVenueName();
          
          MessageCli.MAKE_BOOKING_SUCCESSFUL.printMessage(BookingReferenceGenerator.generateBookingReference(), bookedVenueName, options[1], options[3]);
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