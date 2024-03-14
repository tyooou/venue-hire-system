package nz.ac.auckland.se281;

import nz.ac.auckland.se281.Types.CateringType;
import nz.ac.auckland.se281.Types.FloralType;
import java.util.ArrayList;

public class VenueHireSystem {
  ArrayList<Venue> venueDatabase = new ArrayList<Venue>();

  public VenueHireSystem() {}

  public void printVenues() {
    int venueListSize = venueDatabase.size();
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

  public void createVenue(
      String venueName, String venueCode, String capacityInput, String hireFeeInput) {
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
        MessageCli.VENUE_SUCCESSFULLY_CREATED.printMessage(venueName, venueCode);
      }
  }

  public void setSystemDate(String dateInput) {
    // TODO implement this method
  }

  public void printSystemDate() {
    // TODO implement this method
  }

  public void makeBooking(String[] options) {
    // TODO implement this method
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