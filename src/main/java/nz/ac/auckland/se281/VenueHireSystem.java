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

      // Print list of available venues.
      MessageCli.NUMBER_VENUES.printMessage(setQuantity, venueListSizeString, setPlural);

      for (Venue item : venueDatabase) {
        ArrayList<String> bookedDates = item.getVenueBookedDates();
        String availableDate = convertLocalDateToString(systemDate);
        boolean availableDateValid = false;
        do {
          if (!bookedDates.contains(availableDate)) {
            availableDateValid = true;
          } else {
            availableDate = convertLocalDateToString(convertStringToLocalDate(availableDate).plusDays(1));
          }
        } while (!availableDateValid);
        MessageCli.VENUE_ENTRY.printMessage(item.venueName, item.venueCode, item.capacityInput, item.hireFeeInput, availableDate);
      }
    }
  }

  public String convertLocalDateToString(LocalDate date) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    return date.format(formatter);
  }

  public LocalDate convertStringToLocalDate(String date) {
    String[] dateSplit = date.split("/");
    String dateFormat = dateSplit[2] + "-" + dateSplit[1] + "-" + dateSplit[0];
    return LocalDate.parse(dateFormat);
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
    if (!dateSet) {
      MessageCli.CURRENT_DATE.printMessage("not set");
    } else {
      MessageCli.CURRENT_DATE.printMessage(convertLocalDateToString(systemDate));
    }
  }

  public void makeBooking(String[] options) {
    // Get current size of venue list.
    venueDatabaseSize = venueDatabase.size();

    // Initialize boolean variable for date validation.
    boolean dateValid = true;

    if (!dateSet) { // Check if system date is not set.
      MessageCli.BOOKING_NOT_MADE_DATE_NOT_SET.printMessage();
    } else {

      // Split date input for comparison.
      String[] splitDate = options[1].split("/");
      String[] splitSystemDate = convertLocalDateToString(systemDate).split("/");

      for (int index = 2; index > -1; index--) { // Check if booking date is in the past.
        if (Integer.parseInt(splitDate[index], 10) < Integer.parseInt(splitSystemDate[index], 10)) {
         dateValid = false; 
         break;
        }
      }

      if (venueDatabaseSize <= 0) {
        MessageCli.BOOKING_NOT_MADE_NO_VENUES.printMessage();
      } else if (!dateValid) {
        MessageCli.BOOKING_NOT_MADE_PAST_DATE.printMessage(options[1], convertLocalDateToString(systemDate));
      } else if (!venueCodeList.contains(options[0])) {
        MessageCli.BOOKING_NOT_MADE_VENUE_NOT_FOUND.printMessage(options[0]);
      } else {
        Venue targetVenue = venueDatabase.get(venueCodeList.indexOf(options[0]));
        String bookedVenueName = targetVenue.getVenueName();

        if (targetVenue.getVenueBookedDates().contains(options[1])) {
          MessageCli.BOOKING_NOT_MADE_VENUE_ALREADY_BOOKED.printMessage(bookedVenueName, options[1]);
        } else {
          int bookedCapacity = Integer.parseInt(options[3], 10);
          int targetCapacity = Integer.parseInt(targetVenue.getCapacityInput());
          String bookedCapacityString = options[3];
          String bookedReference = BookingReferenceGenerator.generateBookingReference();

          if (bookedCapacity > targetCapacity) {
            bookedCapacityString = targetVenue.getCapacityInput();
            MessageCli.BOOKING_ATTENDEES_ADJUSTED.printMessage(options[3], bookedCapacityString, targetVenue.getCapacityInput());
          } else if (bookedCapacity <  targetCapacity / 4) {
            bookedCapacityString = String.valueOf(targetCapacity / 4);
            MessageCli.BOOKING_ATTENDEES_ADJUSTED.printMessage(options[3], bookedCapacityString, targetVenue.getCapacityInput());
          }
          targetVenue.bookVenue(options[1], bookedCapacityString, bookedReference);
          MessageCli.MAKE_BOOKING_SUCCESSFUL.printMessage(bookedReference, bookedVenueName, options[1], bookedCapacityString);
        }
      }
    }
    
  }

  public void printBookings(String venueCode) {
    if (!venueCodeList.contains(venueCode)) {
      MessageCli.PRINT_BOOKINGS_VENUE_NOT_FOUND.printMessage(venueCode);
    } else {
      Venue venue = venueDatabase.get(venueCodeList.indexOf(venueCode));
      ArrayList<String> bookedDatesList = venue.getVenueBookedDates();
      ArrayList<String> bookedReferenceList = venue.getVenueBookedReferences();

      MessageCli.PRINT_BOOKINGS_HEADER.printMessage(venue.getVenueName());
      if (bookedDatesList.isEmpty()) {
        MessageCli.PRINT_BOOKINGS_NONE.printMessage(venue.getVenueName());
      } else {
        for (int index = 0; index < bookedDatesList.size(); index++) {
          MessageCli.PRINT_BOOKINGS_ENTRY.printMessage(bookedReferenceList.get(index), bookedDatesList.get(index));
        }
      }
    }


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