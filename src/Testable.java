package RegexApplication.src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Testable {

    /** Default Constructor */
    public Testable() {}

    /**
     * Helper to handle date validation. Checks for leap years and
     * months with only 30 or 31 days.
     * @param theInString verified regex to test.
     * @return true if verified as valid day depending on month and year.
     */
    public boolean checkValidDate(final String theInString) {

        boolean result = true;

        ArrayList<String> monthsWithThirtyDays = new ArrayList<>();
        monthsWithThirtyDays.add("09");
        monthsWithThirtyDays.add("04");
        monthsWithThirtyDays.add("06");
        monthsWithThirtyDays.add("11");

        String[] resultStr = theInString.split("-");

        if (resultStr[0].equals("00")) {result = false;}
        else if (resultStr[1].equals("00")) {result = false;}
        else if(resultStr[0].equals("02")
                && resultStr[1].equals("29")
                && Integer.parseInt(resultStr[2]) %4 != 0) {result = false;}

        else if (monthsWithThirtyDays.contains((String)resultStr[0])
                && resultStr[1].equals("31")) {result = false;}

        return result;

    }

    /**
     * Checks to see if validated phone number contains a valid area code.
     * List of valid area codes obtained from:
     * https://textlists.info/geography/list-of-u-s-area-codes-and-states/
     */
    public boolean checkValidAreaCode(final String theInString) throws FileNotFoundException {

        Scanner fileScanner = new Scanner( new File("RegexApplication/res/areaCodes.txt"));
        ArrayList<String> areaCodesList = new ArrayList<>();
        while(fileScanner.hasNextLine()) {areaCodesList.add(fileScanner.nextLine());}
        String extractedAreaCode = "";
        if (theInString.charAt(0) == '(') {extractedAreaCode = theInString.substring(1, 4);}
        else {extractedAreaCode = theInString.substring(0, 3); }
        return areaCodesList.contains(extractedAreaCode);

    }


}
