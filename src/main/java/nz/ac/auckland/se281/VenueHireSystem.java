package nz.ac.auckland.se281;

import nz.ac.auckland.se281.Types.CateringType;
import nz.ac.auckland.se281.Types.FloralType;
import java.util.ArrayList;
import java.util.HashSet;

public class VenueHireSystem {
  ArrayList<Venue> venueDatabase = new ArrayList<Venue>();

  public VenueHireSystem() {}

  public void printVenues() {



    MessageCli.NO_VENUES.printMessage();



  }

  public void createVenue(
      String venueName, String venueCode, String capacityInput, String hireFeeInput) {
      ArrayList<String> venueCodeList = new ArrayList<String>();
      boolean isCapacityNumber, isHireFeeNumber;
      isCapacityNumber = isHireFeeNumber = true;
      String numberMessage = "";

      for (Venue item : venueDatabase) { // 
        venueCodeList.add(item.getVenueCode());
      }

      try { int capacityInputInteger = Integer.parseInt(capacityInput); if (capacityInputInteger <= 0) {numberMessage = " positive"; isCapacityNumber = false; }} catch (NumberFormatException nfe) { isCapacityNumber = false; }
      try { int hireFeeInteger = Integer.parseInt(hireFeeInput); if (hireFeeInteger <= 0) {numberMessage = " positive"; isCapacityNumber = false;}} catch (NumberFormatException nfe) { isHireFeeNumber = false; }
      
      if (venueName.length() == 0) {
        MessageCli.VENUE_NOT_CREATED_EMPTY_NAME.printMessage();
      } else if (venueCodeList.contains(venueCode)) {
        String venueNameOfMatch = venueDatabase.get(venueCodeList.indexOf(venueCode)).getVenueName();
        MessageCli.VENUE_NOT_CREATED_CODE_EXISTS.printMessage(venueCode, venueNameOfMatch);
      } else if (!isCapacityNumber) {
        MessageCli.VENUE_NOT_CREATED_INVALID_NUMBER.printMessage("capacity", numberMessage);
      } else if (!isHireFeeNumber) {
        MessageCli.VENUE_NOT_CREATED_INVALID_NUMBER.printMessage("hire fee", numberMessage);
      } else {
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