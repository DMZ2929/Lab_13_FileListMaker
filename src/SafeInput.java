import java.util.Scanner;

public class SafeInput {
    /**
     * Gets a non-zero length string from the user.
     *
     * @param pipe  a Scanner opened to read from System.in
     * @param prompt prompt for the user
     * @return a String response that is not zero length
     */
    public static String getNonZeroLenString(Scanner pipe, String prompt) {
        String retString = "";
        do {
            System.out.print("\n" + prompt + ": ");
            retString = pipe.nextLine();
        } while (retString.length() == 0);
        return retString;
    }

    /**
     * Gets an integer from the user.
     *
     * @param pipe  a Scanner opened to read from System.in
     * @param prompt prompt for the user
     * @return a valid integer
     */
    public static int getInt(Scanner pipe, String prompt) {
        int retInt = 0;
        boolean isValid = false;
        do {
            System.out.print("\n" + prompt + ": ");
            if (pipe.hasNextInt()) {
                retInt = pipe.nextInt();
                isValid = true;
            } else {
                System.out.println("Invalid input. Please enter an integer.");
                pipe.next(); // Clear invalid input
            }
        } while (!isValid);
        pipe.nextLine(); // Clear the newline from the buffer
        return retInt;
    }

    /**
     * Gets a double from the user.
     *
     * @param pipe  a Scanner opened to read from System.in
     * @param prompt prompt for the user
     * @return a valid double
     */
    public static double getDouble(Scanner pipe, String prompt) {
        double retDouble = 0;
        boolean isValid = false;
        do {
            System.out.print("\n" + prompt + ": ");
            if (pipe.hasNextDouble()) {
                retDouble = pipe.nextDouble();
                isValid = true;
            } else {
                System.out.println("Invalid input. Please enter a double.");
                pipe.next(); // Clear invalid input
            }
        } while (!isValid);
        pipe.nextLine(); // Clear the newline from the buffer
        return retDouble;
    }

    /**
     * Gets an integer within a specified range from the user.
     *
     * @param pipe  a Scanner opened to read from System.in
     * @param prompt prompt for the user
     * @param low   the low value for the input range
     * @param high  the high value for the input range
     * @return a valid integer within the specified range
     */
    public static int getRangedInt(Scanner pipe, String prompt, int low, int high) {
        int retInt = 0;
        boolean isValid = false;
        do {
            System.out.print("\n" + prompt + " [" + low + " - " + high + "]: ");
            if (pipe.hasNextInt()) {
                retInt = pipe.nextInt();
                if (retInt >= low && retInt <= high) {
                    isValid = true;
                } else {
                    System.out.println("Input out of range. Please enter a number between " + low + " and " + high + ".");
                }
            } else {
                System.out.println("Invalid input. Please enter an integer.");
                pipe.next(); // Clear invalid input
            }
        } while (!isValid);
        pipe.nextLine(); // Clear the newline from the buffer
        return retInt;
    }

    /**
     * Gets a double within a specified range from the user.
     *
     * @param pipe  a Scanner opened to read from System.in
     * @param prompt prompt for the user
     * @param low   the low value for the input range
     * @param high  the high value for the input range
     * @return a valid double within the specified range
     */
    public static double getRangedDouble(Scanner pipe, String prompt, double low, double high) {
        double retDouble = 0;
        boolean isValid = false;
        do {
            System.out.print("\n" + prompt + " [" + low + " - " + high + "]: ");
            if (pipe.hasNextDouble()) {
                retDouble = pipe.nextDouble();
                if (retDouble >= low && retDouble <= high) {
                    isValid = true;
                } else {
                    System.out.println("Input out of range. Please enter a number between " + low + " and " + high + ".");
                }
            } else {
                System.out.println("Invalid input. Please enter a double.");
                pipe.next(); // Clear invalid input
            }
        } while (!isValid);
        pipe.nextLine(); // Clear the newline from the buffer
        return retDouble;
    }

    /**
     * Gets a Yes or No confirmation from the user.
     *
     * @param pipe  a Scanner opened to read from System.in
     * @param prompt prompt for the user
     * @return true for Yes, false for No
     */
    public static boolean getYNConfirm(Scanner pipe, String prompt) {
        String input;
        boolean isValid = false;
        do {
            System.out.print("\n" + prompt + " [Y/N]: ");
            input = pipe.nextLine().toUpperCase();
            if (input.equals("Y") || input.equals("N")) {
                isValid = true;
            } else {
                System.out.println("Invalid input. Please enter Y or N.");
            }
        } while (!isValid);
        return input.equals("Y");
    }

    /**
     * Gets a string that matches a RegEx pattern from the user.
     *
     * @param pipe  a Scanner opened to read from System.in
     * @param prompt prompt for the user
     * @param regEx the RegEx pattern to match
     * @return a string that matches the RegEx pattern
     */
    public static String getRegExString(Scanner pipe, String prompt, String regEx) {
        String input;
        boolean isValid = false;
        do {
            System.out.print("\n" + prompt + ": ");
            input = pipe.nextLine();
            if (input.matches(regEx)) {
                isValid = true;
            } else {
                System.out.println("Invalid input. Please enter a string that matches the pattern: " + regEx);
            }
        } while (!isValid);
        return input;
    }

    /**
     * Displays a pretty header with a centered message.
     *
     * @param msg the message to center in the header
     */
    public static void prettyHeader(String msg) {
        int totalWidth = 60;
        int msgLength = msg.length();
        int starsEachSide = (totalWidth - msgLength - 6) / 2; // 6 accounts for the 3 stars on each side

        // Print top row of stars
        for (int i = 0; i < totalWidth; i++) {
            System.out.print("*");
        }
        System.out.println();

        // Print middle row with centered message
        System.out.print("***");
        for (int i = 0; i < starsEachSide; i++) {
            System.out.print(" ");
        }
        System.out.print(msg);
        for (int i = 0; i < starsEachSide; i++) {
            System.out.print(" ");
        }
        // Adjust for odd lengths
        if ((totalWidth - msgLength - 6) % 2 != 0) {
            System.out.print(" ");
        }
        System.out.println("***");

        // Print bottom row of stars
        for (int i = 0; i < totalWidth; i++) {
            System.out.print("*");
        }
        System.out.println();
    }
}