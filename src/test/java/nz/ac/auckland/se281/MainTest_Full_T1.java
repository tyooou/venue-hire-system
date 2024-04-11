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
  MainTest.Task1.class
})
public class MainTest_Full_T1 {

  @FixMethodOrder(MethodSorters.NAME_ASCENDING)
  public static class Task1 extends CliTest {

    public Task1() {
      super(Main.class);
    }

    @Test
    public void T1_xx_no_venues() throws Exception {
      runCommands(PRINT_VENUES);

      assertContains("There are no venues in the system. Please create a venue first.");
    }

    @Test
    public void T1_xx_first_venue_created() throws Exception {
      runCommands(CREATE_VENUE, "'Frugal Fiesta Hall'", "FFH", "80", "150");

      assertContains("Successfully created venue 'Frugal Fiesta Hall' (FFH).");
    }

    @Test
    public void T1_xx_invalid_venue_empty_name() throws Exception {
      runCommands(CREATE_VENUE, "''", "NA", "80", "150");

      assertContains("Venue not created: venue name must not be empty.");
      assertDoesNotContain("Successfully created venue", true);
    }

    @Test
    public void T1_xx_invalid_capacity_not_number() throws Exception {
      runCommands(CREATE_VENUE, "'Frugal Fiesta Hall'", "FFH", "twenty", "150");

      assertContains("Venue not created: capacity must be a number.");
      assertDoesNotContain("Successfully created venue", true);
    }

    @Test
    public void T1_xx_invalid_capacity_negative() throws Exception {
      runCommands(CREATE_VENUE, "'Frugal Fiesta Hall'", "FFH", "-1", "150");

      assertContains("Venue not created: capacity must be a positive number.");
      assertDoesNotContain("Successfully created venue", true);
    }

    @Test
    public void T1_xx_invalid_base_hire_fee_not_number() throws Exception {
      runCommands(CREATE_VENUE, "'Frugal Fiesta Hall'", "FFH", "80", "fifty");

      assertContains("Venue not created: hire fee must be a number.");
      assertDoesNotContain("Successfully created venue", true);
    }

    @Test
    public void T1_xx_invalid_base_hire_fee_negative() throws Exception {
      runCommands(CREATE_VENUE, "'Frugal Fiesta Hall'", "FFH", "80", "-150");

      assertContains("Venue not created: hire fee must be a positive number.");
      assertDoesNotContain("Successfully created venue", true);
    }

    @Test
    public void T1_xx_one_venue_saved() throws Exception {
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
    public void T1_xx_first_venue_saved() throws Exception {
      runCommands(
          CREATE_VENUE,
          "'Frugal Fiesta Hall'",
          "FFH",
          "80",
          "150", //
          PRINT_VENUES);

      assertContains("There is one venue in the system:");
      assertContains("Successfully created venue 'Frugal Fiesta Hall' (FFH).");
      assertContains("* Frugal Fiesta Hall (FFH) - 80 people - $150 base hire fee");
    }

    @Test
    public void T1_xx_second_venue_saved() throws Exception {
      runCommands(
          CREATE_VENUE,
          "'Frugal Fiesta Hall'",
          "FFH",
          "80",
          "150", //
          CREATE_VENUE,
          "'Comfy Corner Events Centre'",
          "CCEC",
          "120",
          "250", //
          PRINT_VENUES);

      assertContains("Successfully created venue 'Frugal Fiesta Hall' (FFH).");
      assertContains("Successfully created venue 'Comfy Corner Events Centre' (CCEC).");
      assertContains("There are two venues in the system:");
      assertContains("Frugal Fiesta Hall (FFH) - 80 people - $150 base hire fee");
      assertContains("Comfy Corner Events Centre (CCEC) - 120 people - $250 base hire fee");
    }

    @Test
    public void T1_xx_nine_venues_saved() throws Exception {
      runCommands(unpack(CREATE_NINE_VENUES, PRINT_VENUES));

      assertContains("Successfully created venue 'Frugal Fiesta Hall' (FFH).");
      assertContains("Successfully created venue 'Comfy Corner Events Centre' (CCEC).");
      assertContains("Successfully created venue 'Cozy Comforts Venue' (CCV).");
      assertContains("Successfully created venue 'Charming Charm Hall' (CCH).");
      assertContains("Successfully created venue 'Refined Radiance Venue' (RRV).");
      assertContains("Successfully created venue 'Classy Celebration Venue' (TGB).");
      assertContains("Successfully created venue 'Grand Gala Gardens' (GGG).");
      assertContains("Successfully created venue 'Exclusive Elegance Venue' (EEV).");
      assertContains("Successfully created venue 'Luxurious Legacy Hall' (LLH).");

      assertContains("There are nine venues in the system:");
      assertContains("Frugal Fiesta Hall (FFH) - 80 people - $250 base hire fee");
      assertContains("Comfy Corner Events Centre (CCEC) - 120 people - $500 base hire fee");
      assertContains("Cozy Comforts Venue (CCV) - 200 people - $500 base hire fee");
      assertContains("Charming Charm Hall (CCH) - 220 people - $500 base hire fee");
      assertContains("Refined Radiance Venue (RRV) - 200 people - $500 base hire fee");
      assertContains("Classy Celebration Venue (TGB) - 150 people - $1000 base hire fee");
      assertContains("Grand Gala Gardens (GGG) - 260 people - $1500 base hire fee");
      assertContains("Exclusive Elegance Venue (EEV) - 350 people - $1500 base hire fee");
      assertContains("Luxurious Legacy Hall (LLH) - 800 people - $2500 base hire fee");

      assertDoesNotContain("There is", true);
      assertDoesNotContain("9 venues", true);
    }

    @Test
    public void T1_xx_ten_venues_saved() throws Exception {
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
    public void T1_xx_reject_duplicate_venue() throws Exception {
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

    @Test
    public void T1_xx_reject_duplicate_venue_different_name() throws Exception {
      runCommands(
          CREATE_VENUE,
          "'Frugal Fiesta Hall'",
          "FFH",
          "80",
          "150", //
          CREATE_VENUE,
          "'Finest Fantasy Heights'",
          "FFH",
          "150",
          "2500", //
          PRINT_VENUES);

      assertContains("Successfully created venue 'Frugal Fiesta Hall' (FFH).");
      assertContains("There is one venue in the system:");
      assertContains("* Frugal Fiesta Hall (FFH) - 80 people - $150 base hire fee");

      assertContains("Venue not created: code 'FFH' is already used for 'Frugal Fiesta Hall'.");

      assertDoesNotContain("Successfully created venue 'Finest Fantasy Heights' (FFH).", true);
      assertDoesNotContain(
          "* Finest Fantasy Heights (FFH) - 150 people - $2500 base hire fee", true);
      assertDoesNotContain("two venue", true);
      assertDoesNotContain("2 venue", true);
    }

    @Test
    public void T1_xx_add_second_after_reject_duplicate() throws Exception {
      runCommands(
          CREATE_VENUE,
          "'Frugal Fiesta Hall'",
          "FFH",
          "80",
          "150", //
          CREATE_VENUE,
          "'Finest Fantasy Heights'",
          "FFH",
          "150",
          "2500", //
          CREATE_VENUE,
          "'Comfy Corner Events Centre'",
          "CCEC",
          "120",
          "250", //
          PRINT_VENUES);

      assertContains("Successfully created venue 'Frugal Fiesta Hall' (FFH).");
      assertContains("Successfully created venue 'Comfy Corner Events Centre' (CCEC).");

      assertContains("There are two venues in the system:");
      assertContains("* Frugal Fiesta Hall (FFH) - 80 people - $150 base hire fee");
      assertContains("* Comfy Corner Events Centre (CCEC) - 120 people - $250 base hire fee");

      assertDoesNotContain(
          "* Finest Fantasy Heights (FFH) - 150 people - $2500 base hire fee", true);

      assertDoesNotContain("one venue", true);
      assertDoesNotContain("1 venue", true);
      assertDoesNotContain("three venue", true);
      assertDoesNotContain("3 venue", true);
    }

    public static class YourTests extends CliTest {
      public YourTests() {
        super(Main.class);
      }
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
