package nz.ac.auckland.se281;

import java.util.Random;

/** You cannot modify this class! */
public class BookingReferenceGenerator {
  private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
  private static final int CODE_LENGTH = 8;
  private static final long SEED = 23;

  private static Random random = new Random(SEED);

  public static String generateBookingReference() {
    StringBuilder bookingReference = new StringBuilder();

    for (int i = 0; i < CODE_LENGTH; i++) {
      int randomIndex = random.nextInt(CHARACTERS.length());
      char randomChar = CHARACTERS.charAt(randomIndex);
      bookingReference.append(randomChar);
    }

    return bookingReference.toString();
  }

  public static void reset() {
    random = new Random(SEED);
  }

  public static void main(String[] args) {
    // Example usage
    for (int i = 0; i < 20; i++) {
      System.out.println(generateBookingReference());
    }
  }
}
