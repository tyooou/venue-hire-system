package nz.ac.auckland.se281;

import static nz.ac.auckland.se281.Main.Command.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
  MainTest.Task1.class,
  MainTest.Task2.class,
  MainTest.Task3.class,
  // MainTest.YourTests.class, // Uncomment this line to run your own tests
})
public class MainTest {

  @FixMethodOrder(MethodSorters.NAME_ASCENDING)
  public static class Task1 extends CliTest {

    public Task1() {
      super(Main.class);
    }

    @Test
    public void T1_01_no_venues() throws Exception {
      runCommands(PRINT_VENUES);

      assertContains("There are no venues in the system. Please create a venue first.");
    }

    @Test
    public void T1_02_first_venue_created() throws Exception {
      runCommands(CREATE_VENUE, "'Frugal Fiesta Hall'", "FFH", "80", "150");

      assertContains("Successfully created venue 'Frugal Fiesta Hall' (FFH).");
    }

    @Test
    public void T1_03_invalid_venue_empty_name() throws Exception {
      runCommands(CREATE_VENUE, "''", "NA", "80", "150");

      assertContains("Venue not created: venue name must not be empty.");
      assertDoesNotContain("Successfully created venue", true);
    }

    @Test
    public void T1_04_invalid_capacity_negative() throws Exception {
      runCommands(CREATE_VENUE, "'Frugal Fiesta Hall'", "FFH", "-1", "150");

      assertContains("Venue not created: capacity must be a positive number.");
      assertDoesNotContain("Successfully created venue", true);
    }

    @Test
    public void T1_05_invalid_base_hire_fee_not_number() throws Exception {
      runCommands(CREATE_VENUE, "'Frugal Fiesta Hall'", "FFH", "80", "fifty");

      assertContains("Venue not created: hire fee must be a number.");
      assertDoesNotContain("Successfully created venue", true);
    }

    @Test
    public void T1_06_one_venue_saved() throws Exception {
      runCommands(
          CREATE_VENUE,
          "'Frugal Fiesta Hall'",
          "FFH",
          "80",
          "150", //
          PRINT_VENUES);

      assertContains("There is one venue in the system:");
      assertContains("Successfully created venue 'Frugal Fiesta Hall' (FFH).");
      assertDoesNotContain("Please create a venue first", true);
    }

    @Test
    public void T1_07_ten_venues_saved() throws Exception {
      runCommands(unpack(CREATE_TEN_VENUES, PRINT_VENUES));

      assertContains("Successfully created venue 'Frugal Fiesta Hall' (FFH).");
      assertContains("Successfully created venue 'Comfy Corner Events Centre' (CCEC).");
      assertContains("Successfully created venue 'Cozy Comforts Venue' (CCV).");
      assertContains("Successfully created venue 'Charming Charm Hall' (CCH).");
      assertContains("Successfully created venue 'Refined Radiance Venue' (RRV).");
      assertContains("Successfully created venue 'Classy Celebration Venue' (TGB).");
      assertContains("Successfully created venue 'Grand Gala Gardens' (GGG).");
      assertContains("Successfully created venue 'Exclusive Elegance Venue' (EEV).");
      assertContains("Successfully created venue 'Luxurious Legacy Hall' (LLH).");
      assertContains("Successfully created venue 'Majestic Monarch Mansion' (MMM).");

      assertContains("There are 10 venues in the system:");
      assertContains("Frugal Fiesta Hall (FFH) - 80 people - $250 base hire fee");
      assertContains("Comfy Corner Events Centre (CCEC) - 120 people - $500 base hire fee");
      assertContains("Cozy Comforts Venue (CCV) - 200 people - $500 base hire fee");
      assertContains("Charming Charm Hall (CCH) - 220 people - $500 base hire fee");
      assertContains("Refined Radiance Venue (RRV) - 200 people - $500 base hire fee");
      assertContains("Classy Celebration Venue (TGB) - 150 people - $1000 base hire fee");
      assertContains("Grand Gala Gardens (GGG) - 260 people - $1500 base hire fee");
      assertContains("Exclusive Elegance Venue (EEV) - 350 people - $1500 base hire fee");
      assertContains("Luxurious Legacy Hall (LLH) - 800 people - $2500 base hire fee");
      assertContains("Majestic Monarch Mansion (MMM) - 1000 people - $2500 base hire fee");

      assertDoesNotContain("There is", true);
      assertDoesNotContain("ten venues", true);
    }

    @Test
    public void T1_08_reject_duplicate_venue() throws Exception {
      runCommands(
          CREATE_VENUE,
          "'Frugal Fiesta Hall'",
          "FFH",
          "80",
          "150", //
          CREATE_VENUE,
          "'Frugal Fiesta Hall'",
          "FFH",
          "80",
          "150", //
          PRINT_VENUES);

      assertContains("Venue not created: code 'FFH' is already used for 'Frugal Fiesta Hall'.");
      assertContains("There is one venue in the system:");
      assertContains("* Frugal Fiesta Hall (FFH) - 80 people - $150 base hire fee");
      assertDoesNotContain("two venue", true);
      assertDoesNotContain("2 venue", true);
    }

    public static class YourTests extends CliTest {
      public YourTests() {
        super(Main.class);
      }
    }
  }

  @FixMethodOrder(MethodSorters.NAME_ASCENDING)
  public static class Task2 extends CliTest {

    public Task2() {
      super(Main.class);
    }

    @Override
    public void reset() {
      BookingReferenceGenerator.reset();
    }

    @Test
    public void T2_01_system_date_not_set() throws Exception {
      runCommands(PRINT_DATE);

      assertContains("Current system date is not set.");
    }

    @Test
    public void T2_02_set_system_date() throws Exception {
      runCommands(SET_DATE, "26/02/2024");

      assertContains("System date set to 26/02/2024.");
      assertDoesNotContain("Current system date is", true);
      assertDoesNotContain("not set", true);
    }

    @Test
    public void T2_03_set_system_date_saved() throws Exception {
      runCommands(SET_DATE, "26/02/2024", PRINT_DATE);

      assertContains("System date set to 26/02/2024.");
      assertContains("Current system date is 26/02/2024.");

      assertDoesNotContain("not set", true);
    }

    @Test
    public void T2_04_make_booking_no_date_set() throws Exception {
      runCommands(MAKE_BOOKING, options("FFH", "26/02/2024", "client001@email.com", "70"));

      assertContains("Booking not made: system date not set. Set the date first.");
      assertDoesNotContain("Successfully created booking", true);
    }

    @Test
    public void T2_05_make_booking_no_venues() throws Exception {
      runCommands(
          SET_DATE,
          "26/02/2024", //
          MAKE_BOOKING,
          options("FFH", "27/02/2024", "client001@email.com", "70"));

      assertContains("Booking not made: there are no venues in the system. Create one first.");
      assertDoesNotContain("Successfully created booking", true);
    }

    @Test
    public void T2_06_make_booking_venue_found() throws Exception {
      runCommands(
          unpack(
              CREATE_TEN_VENUES,
              SET_DATE,
              "26/02/2024", //
              MAKE_BOOKING,
              options("GGG", "27/02/2024", "client001@email.com", "230")));

      assertContains(
          "Successfully created booking 'HUD14D8O' for 'Grand Gala Gardens' on 27/02/2024 for 230"
              + " people.");
      assertDoesNotContain("Booking not made", true);
    }

    @Test
    public void T2_07_make_booking_venue_already_booked() throws Exception {
      runCommands(
          unpack(
              CREATE_TEN_VENUES,
              SET_DATE,
              "26/02/2024", //
              MAKE_BOOKING,
              options("GGG", "27/02/2024", "client001@email.com", "230"), // OK
              MAKE_BOOKING,
              options("GGG", "27/02/2024", "client999@email.com", "215"))); // Not OK

      assertContains("Successfully created booking 'HUD14D8O'");
      assertContains(
          "Booking not made: venue 'Grand Gala Gardens' is already booked on 27/02/2024.");
      assertDoesNotContain("Successfully created booking 'ZP4HRCZ4'", true);
    }

    @Test
    public void T2_08_make_booking_for_current_date() throws Exception {
      runCommands(
          unpack(
              CREATE_TEN_VENUES,
              SET_DATE,
              "03/02/2024", //
              MAKE_BOOKING,
              options("GGG", "03/02/2024", "client001@email.com", "230")));

      assertContains("Successfully created booking 'HUD14D8O'");
      assertDoesNotContain("Booking not made", true);
    }

    @Test
    public void T2_09_make_booking_too_few_attendees() throws Exception {
      runCommands(
          unpack(
              CREATE_TEN_VENUES,
              SET_DATE,
              "26/02/2024", //
              MAKE_BOOKING,
              options("GGG", "28/05/2024", "client999@email.com", "20")));

      assertContains("Number of attendees adjusted from 20 to 65, as the venue capacity is 260.");
      assertContains("Successfully created booking 'HUD14D8O'");
      assertDoesNotContain("Booking not made", true);
    }

    @Test
    public void T2_10_booking_next_available_date() throws Exception {
      runCommands(
          unpack(
              CREATE_TEN_VENUES, //
              SET_DATE,
              "03/02/2024", //
              PRINT_VENUES));

      assertContains("There are 10 venues in the system:");
      assertContains(
          "Frugal Fiesta Hall (FFH) - 80 people - $250 base hire fee. Next available on"
              + " 03/02/2024");
      assertContains(
          "Comfy Corner Events Centre (CCEC) - 120 people - $500 base hire fee. Next available on"
              + " 03/02/2024");
      assertContains(
          "Cozy Comforts Venue (CCV) - 200 people - $500 base hire fee. Next available on"
              + " 03/02/2024");
      assertContains(
          "Charming Charm Hall (CCH) - 220 people - $500 base hire fee. Next available on"
              + " 03/02/2024");
      assertContains(
          "Refined Radiance Venue (RRV) - 200 people - $500 base hire fee. Next available on"
              + " 03/02/2024");
      assertContains(
          "Classy Celebration Venue (TGB) - 150 people - $1000 base hire fee. Next available on"
              + " 03/02/2024");
      assertContains(
          "Grand Gala Gardens (GGG) - 260 people - $1500 base hire fee. Next available on"
              + " 03/02/2024");
      assertContains(
          "Exclusive Elegance Venue (EEV) - 350 people - $1500 base hire fee. Next available on"
              + " 03/02/2024");
      assertContains(
          "Luxurious Legacy Hall (LLH) - 800 people - $2500 base hire fee. Next available on"
              + " 03/02/2024");
      assertContains(
          "Majestic Monarch Mansion (MMM) - 1000 people - $2500 base hire fee. Next available on"
              + " 03/02/2024");
    }

    @Test
    public void T2_11_booking_next_available_date_after_making_bookings() throws Exception {
      runCommands(
          unpack(
              CREATE_TEN_VENUES, //
              SET_DATE,
              "03/02/2024", //
              MAKE_BOOKING,
              options("GGG", "03/02/2024", "client001@email.com", "230"),
              MAKE_BOOKING,
              options("GGG", "04/02/2024", "client001@email.com", "230"),
              PRINT_VENUES));

      assertContains(
          "Frugal Fiesta Hall (FFH) - 80 people - $250 base hire fee. Next available on"
              + " 03/02/2024");
      assertContains(
          "Grand Gala Gardens (GGG) - 260 people - $1500 base hire fee. Next available on"
              + " 05/02/2024");
      assertContains(
          "Majestic Monarch Mansion (MMM) - 1000 people - $2500 base hire fee. Next available on"
              + " 03/02/2024");
    }

    @Test
    public void T2_12_print_bookings_venue_not_exist() throws Exception {
      runCommands(
          unpack(
              CREATE_TEN_VENUES, //
              PRINT_BOOKINGS,
              "ABC"));

      assertContains("Nothing to print: there is no venue with code 'ABC'.");
      assertDoesNotContain("Bookings for '", true);
      assertDoesNotContain("No bookings for", true);
      assertDoesNotContain("s' on ", true);
    }

    @Test
    public void T2_13_print_bookings_none_exist() throws Exception {
      runCommands(
          unpack(
              CREATE_TEN_VENUES, //
              PRINT_BOOKINGS,
              "GGG"));

      assertContains("Bookings for 'Grand Gala Gardens':");
      assertContains("* No bookings for 'Grand Gala Gardens'.");
      assertDoesNotContain("Nothing to print", true);
      assertDoesNotContain("for 'Luxurious Legacy Hall'", true);
      assertDoesNotContain("for 'LLH'", true);
      assertDoesNotContain("for 'Majestic Monarch Mansion'", true);
      assertDoesNotContain("for 'MMM'", true);
    }

    @Test
    public void T2_14_print_bookings_same_venue() throws Exception {
      runCommands(
          unpack(
              CREATE_TEN_VENUES,
              SET_DATE,
              "26/02/2024", //
              MAKE_BOOKING,
              options("GGG", "27/03/2024", "client001@email.com", "230"), //
              MAKE_BOOKING,
              options("GGG", "28/05/2024", "client999@email.com", "215"), //
              PRINT_BOOKINGS,
              "GGG"));

      assertContains("Bookings for 'Grand Gala Gardens':");
      assertContains("* 'HUD14D8O' on 27/03/2024");
      assertContains("* 'ZP4HRCZ4' on 28/05/2024");
      assertDoesNotContain("No bookings for 'Grand Gala Gardens'", true);
      assertDoesNotContain("Nothing to print", true);
    }

    @Test
    public void T2_15_print_bookings_venue_without_bookings() throws Exception {
      runCommands(
          unpack(
              CREATE_TEN_VENUES,
              SET_DATE,
              "26/02/2024", //
              MAKE_BOOKING,
              options("GGG", "27/03/2024", "client001@email.com", "230"), //
              MAKE_BOOKING,
              options("LLH", "28/05/2024", "client999@email.com", "500"), //
              PRINT_BOOKINGS,
              "EEV"));

      assertContains("Bookings for 'Exclusive Elegance Venue':");
      assertContains("* No bookings for 'Exclusive Elegance Venue'.");
      assertDoesNotContain("Nothing to print", true);
      assertDoesNotContain("Bookings for 'Grand Gala Gardens'", true);
      assertDoesNotContain("for 'GGG'", true);
      assertDoesNotContain("Bookings for 'Luxurious Legacy Hall'", true);
      assertDoesNotContain("for 'LLH'", true);
      assertDoesNotContain("'HUD14D8O' on 27/03/2024", true);
      assertDoesNotContain("'ZP4HRCZ4' on 28/05/2024", true);
    }
  }

  @FixMethodOrder(MethodSorters.NAME_ASCENDING)
  public static class Task3 extends CliTest {

    public Task3() {
      super(Main.class);
    }

    @Override
    public void reset() {
      BookingReferenceGenerator.reset();
    }

    @Test
    public void T3_01_add_catering_service_no_bookings() throws Exception {
      runCommands(
          unpack(
              CREATE_TEN_VENUES, //
              SET_DATE,
              "03/02/2024", //
              ADD_CATERING,
              "HUD14D8O",
              options("B")));

      assertContains(
          "Catering service not added: there is no booking with reference 'HUD14D8O' in the"
              + " system.");
      assertDoesNotContain("Successfully added", true);
    }

    @Test
    public void T3_02_add_catering_service_booking_does_not_exist() throws Exception {
      runCommands(
          unpack(
              CREATE_TEN_VENUES,
              SET_DATE,
              "26/02/2024", //
              MAKE_BOOKING,
              options("GGG", "27/03/2024", "client001@email.com", "230"), //
              ADD_CATERING,
              "ZP4HRCZ4",
              options("B")));

      assertContains(
          "Catering service not added: there is no booking with reference 'ZP4HRCZ4' in the"
              + " system.");
      assertDoesNotContain("Successfully added", true);
    }

    @Test
    public void T3_03_add_catering_service_booking_exists() throws Exception {
      runCommands(
          unpack(
              CREATE_TEN_VENUES,
              SET_DATE,
              "26/02/2024", //
              MAKE_BOOKING,
              options("GGG", "27/03/2024", "client001@email.com", "230"), //
              ADD_CATERING,
              "HUD14D8O",
              options("B")));

      assertContains("Successfully added Catering (Breakfast) service to booking 'HUD14D8O'.");
      assertDoesNotContain("* Catering ", true);
      assertDoesNotContain("not added", true);
    }

    @Test
    public void T3_04_add_catering_service_entry_printed() throws Exception {
      runCommands(
          unpack(
              CREATE_TEN_VENUES,
              SET_DATE,
              "26/02/2024", //
              MAKE_BOOKING,
              options("GGG", "27/03/2024", "client001@email.com", "230"), //
              ADD_CATERING,
              "HUD14D8O",
              options("B"), //
              VIEW_INVOICE,
              "HUD14D8O"));

      assertContains("Successfully added Catering (Breakfast) service to booking 'HUD14D8O'.");
      assertContains("* Catering (Breakfast) - $3450");
      assertDoesNotContain("not added", true);
    }

    @Test
    public void T3_05_add_catering_service_total_printed() throws Exception {
      runCommands(
          unpack(
              CREATE_TEN_VENUES,
              SET_DATE,
              "26/02/2024", //
              MAKE_BOOKING,
              options("GGG", "27/03/2024", "client001@email.com", "230"), //
              ADD_CATERING,
              "HUD14D8O",
              options("B"), //
              VIEW_INVOICE,
              "HUD14D8O"));

      assertContains("Successfully added Catering (Breakfast) service to booking 'HUD14D8O'.");
      assertContains("* Catering (Breakfast) - $3450");
      assertContains("Total Amount: $4950");
      assertDoesNotContain("not added", true);
    }

    @Test
    public void T3_06_add_music_service_no_bookings() throws Exception {
      runCommands(
          unpack(
              CREATE_TEN_VENUES, //
              SET_DATE,
              "03/02/2024", //
              ADD_MUSIC,
              "HUD14D8O"));

      assertContains(
          "Music service not added: there is no booking with reference 'HUD14D8O' in the"
              + " system.");
      assertDoesNotContain("Successfully added", true);
    }

    @Test
    public void T3_07_add_music_service_booking_exists() throws Exception {
      runCommands(
          unpack(
              CREATE_TEN_VENUES,
              SET_DATE,
              "26/02/2024", //
              MAKE_BOOKING,
              options("GGG", "27/03/2024", "client001@email.com", "230"), //
              ADD_MUSIC,
              "HUD14D8O"));

      assertContains("Successfully added Music service to booking 'HUD14D8O'.");
      assertDoesNotContain("* Music ", true);
      assertDoesNotContain("not added", true);
    }

    @Test
    public void T3_08_add_music_service_entry_printed() throws Exception {
      runCommands(
          unpack(
              CREATE_TEN_VENUES,
              SET_DATE,
              "26/02/2024", //
              MAKE_BOOKING,
              options("GGG", "27/03/2024", "client001@email.com", "230"), //
              ADD_MUSIC,
              "HUD14D8O", //
              VIEW_INVOICE,
              "HUD14D8O"));

      assertContains("Successfully added Music service to booking 'HUD14D8O'.");
      assertContains("* Music - $500");
      assertDoesNotContain("not added", true);
    }

    @Test
    public void T3_09_add_floral_service_no_bookings() throws Exception {
      runCommands(
          unpack(
              CREATE_TEN_VENUES, //
              SET_DATE,
              "03/02/2024", //
              ADD_FLORAL,
              "HUD14D8O",
              options("n")));

      assertContains(
          "Floral service not added: there is no booking with reference 'HUD14D8O' in the"
              + " system.");
      assertDoesNotContain("Successfully added", true);
    }

    @Test
    public void T3_10_add_floral_service_booking_exists() throws Exception {
      runCommands(
          unpack(
              CREATE_TEN_VENUES,
              SET_DATE,
              "26/02/2024", //
              MAKE_BOOKING,
              options("GGG", "27/03/2024", "client001@email.com", "230"), //
              ADD_FLORAL,
              "HUD14D8O",
              options("n")));

      assertContains("Successfully added Floral (Standard) service to booking 'HUD14D8O'.");
      assertDoesNotContain("* Catering ", true);
      assertDoesNotContain("not added", true);
    }

    @Test
    public void T3_11_add_floral_service_total_printed() throws Exception {
      runCommands(
          unpack(
              CREATE_TEN_VENUES,
              SET_DATE,
              "26/02/2024", //
              MAKE_BOOKING,
              options("GGG", "27/03/2024", "client001@email.com", "230"), //
              ADD_FLORAL,
              "HUD14D8O",
              options("n"), //
              VIEW_INVOICE,
              "HUD14D8O"));

      assertContains("Successfully added Floral (Standard) service to booking 'HUD14D8O'.");
      assertContains("* Floral (Standard) - $550");
      assertContains("Total Amount: $2050");
      assertDoesNotContain("not added", true);
    }

    @Test
    public void T3_12_invoice_details_using_top_and_bottom_halves() throws Exception {
      runCommands(
          unpack(
              CREATE_TEN_VENUES,
              SET_DATE,
              "26/02/2024", //
              MAKE_BOOKING,
              options("GGG", "27/03/2024", "client001@email.com", "230"), //
              VIEW_INVOICE,
              "HUD14D8O"));

      assertContains("INVOICE");
      assertContains("===============================================================");
      assertContains("-------------------------------------");
      assertContains("Booking Details:");
      assertContains("Event Details:");
      assertContains("Cost Breakdown:");
      assertContains("Total Amount:");
      assertContains("Thank you for choosing 281 Venue Hire!");
      assertContains("For any inquiries, please contact support@281venuehire.co.nz.");
    }

    @Test
    public void T3_13_invoice_details_show_venue_fee() throws Exception {
      runCommands(
          unpack(
              CREATE_TEN_VENUES,
              SET_DATE,
              "26/02/2024", //
              MAKE_BOOKING,
              options("GGG", "27/03/2024", "client001@email.com", "230"), //
              VIEW_INVOICE,
              "HUD14D8O"));

      assertContains("* Venue hire - $1500");
      assertContains("Total Amount: $1500");
    }

    @Test
    public void T3_14_invoice_details_show_date_of_booking() throws Exception {
      runCommands(
          unpack(
              CREATE_TEN_VENUES,
              SET_DATE,
              "18/02/2024", //
              MAKE_BOOKING,
              options("GGG", "27/10/2024", "client001@email.com", "230"), //
              VIEW_INVOICE,
              "HUD14D8O"));

      assertContains("Date of Booking: 18/02/2024");
      assertContains("Party Date: 27/10/2024");
    }

    @Test
    public void T3_15_invoice_details_show_customer_emails() throws Exception {
      runCommands(
          unpack(
              CREATE_TEN_VENUES,
              SET_DATE,
              "26/02/2024", //
              MAKE_BOOKING,
              options("FFH", "27/07/2024", "client001@email.com", "20"), //
              VIEW_INVOICE,
              "HUD14D8O"));

      assertContains("Booking Reference: #HUD14D8O");
      assertContains("Customer Email: client001@email.com");
      assertContains("Date of Booking: 26/02/2024");
      assertContains("Party Date: 27/07/2024");
      assertContains("Number of Guests: 20");
      assertContains("Venue: Frugal Fiesta Hall");
    }
  }

  @FixMethodOrder(MethodSorters.NAME_ASCENDING)
  public static class YourTests extends CliTest {

    public YourTests() {
      super(Main.class);
    }

    @Override
    public void reset() {
      BookingReferenceGenerator.reset();
    }

    @Test
    public void T4_01_add_your_own_tests_as_needed() throws Exception {
      runCommands(PRINT_VENUES);
      assertContains("There are no venues in the system. Please create a venue first.");
    }
  }

  private static final Object[] CREATE_NINE_VENUES =
      new Object[] {
        CREATE_VENUE,
        "'Frugal Fiesta Hall'",
        "FFH",
        "80",
        "250", //
        CREATE_VENUE,
        "'Comfy Corner Events Centre'",
        "CCEC",
        "120",
        "500", //
        CREATE_VENUE,
        "'Cozy Comforts Venue'",
        "CCV",
        "200",
        "500", //
        CREATE_VENUE,
        "'Charming Charm Hall'",
        "CCH",
        "220",
        "500", //
        CREATE_VENUE,
        "'Refined Radiance Venue'",
        "RRV",
        "200",
        "500", //
        CREATE_VENUE,
        "'Classy Celebration Venue'",
        "TGB",
        "150",
        "1000", //
        CREATE_VENUE,
        "'Grand Gala Gardens'",
        "GGG",
        "260",
        "1500", //
        CREATE_VENUE,
        "'Exclusive Elegance Venue'",
        "EEV",
        "350",
        "1500", //
        CREATE_VENUE,
        "'Luxurious Legacy Hall'",
        "LLH",
        "800",
        "2500", //
      };

  private static final Object[] CREATE_TEN_VENUES =
      unpack(CREATE_NINE_VENUES, CREATE_VENUE, "'Majestic Monarch Mansion'", "MMM", "1000", "2500");

  private static Object[] unpack(Object[] commands, Object... more) {
    List<Object> all = new ArrayList<Object>();
    all.addAll(List.of(commands));
    all.addAll(List.of(more));
    return all.toArray(new Object[all.size()]);
  }

  private static String[] options(String... options) {
    List<String> all = new ArrayList<String>();
    all.addAll(List.of(options));
    return all.toArray(new String[all.size()]);
  }
}
