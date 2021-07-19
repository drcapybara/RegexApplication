package RegexApplication.src;


import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Regex validates user input against the following conditions:
 * A. Social Security Number
 * B. US Phone number
 * C. E-mail address
 * D. Name on a class roster, assuming one or more middle initials - Last name, First name, MI
 * E. Date in MM-DD-YYYY format
 * F. House address - Street number, street name, abbreviation for road, street, boulevard or avenue
 * G. City followed by state followed by zip as it should appear on a letter
 * H. Military time, including seconds
 * I. US Currency down to the penny (ex: $123,456,789.23)
 * J. URL, including http:// (upper and lower case should be accepted)
 * K. A password that contains at least
 *              10 characters
 *              and includes at least:
 *              one upper case character,
 *              lower case character,
 *              digit,
 *              punctuation mark,
 *              and does not have more than 3 consecutive lower case characters
 * L. All words containing an odd number of alphabetic characters, ending in "ion"
 * Q. quit
 * Returns true if input is validated against selected regular expression, and false otherwise.
 *
 * @author Dustin Ray
 * @version Summer 2021
 */
public class RegEx {

    /**List of results of tests on input.  */
    private final ArrayList<Boolean> myResultList;

    /** Constructor for class. */
    public RegEx() {
        Scanner theInput = new Scanner(System.in);
        myResultList = new ArrayList<>();
        System.out.println("Please select an option: ");
        String option = theInput.nextLine();
        while(!option.equals("Q")) {
            System.out.println("in loop, enter text to test;");
            getMethodCall(option, theInput.nextLine());
            option = theInput.nextLine();
            System.out.println("Exiting");
        }

    }


    /**
     * Uses RegEx to validate theInputString as a Social Security Number.
     * https://www.ssa.gov/history/ssn/geocard.html#:~:text=Number%20Has%20Three%20Parts,digits%20is%20the%20Serial%20Number
     * https://www.geeksforgeeks.org/how-to-validate-ssn-social-security-number-using-regular-expression/
     * @param theInputString The string to validate against RegEx defined above.
     */
    private void socialSecurityNumber(final String theInputString) {
        String regex = "^(?!666|000|9\\d{2})\\d{3}[-\s]?(?!00)\\d{2}[-\s]?(?!0{4})\\d{4}$";
        checkPattern(theInputString, regex);
    }



    /**
     * Uses RegEx to validate theInputString as a  10 digit US Phone number.
     * Valid formats:
     *              (234)5555555
     *              (234)555-5555
     *              (234)-555-5555
     *              234-555-5555
     *              2345555555
     * List of valid area codes obtained from:
     * https://textlists.info/geography/list-of-u-s-area-codes-and-states/
     * @param theInputString The string to validate against RegEx defined above.
     */
    private void usPhoneNumber(final String theInputString) {
        String regex = "^[(]?[2-9]\\d{2}[)]?[-\s]?\\d{3}[-\s]?\\d{4}$";
        checkPattern(theInputString, regex);
    }

    /**
     * Uses RegEx to validate theInputString as a valid E-mail address.
     * https://regexlib.com/REDetails.aspx?regexp_id=1855
     * @param theInputString The string to validate against RegEx defined above.
     */
    private void emailAddress(final String theInputString) {
        String regex = "^([a-zA-Z0-9]+(?:[.-]?[a-zA-Z0-9]+)*@[a-zA-Z0-9]+(?:[.-]?[a-zA-Z0-9]+)*\\.[a-zA-Z]{2,7})$";
        checkPattern(theInputString, regex);
    }


    /**
     * Uses RegEx to validate theInputString as a Name on a class roster,
     * assuming one or more middle initials - Last name, First name, MI
     * @param theInputString The string to validate against RegEx defined above.
     */
    private void lastNameFirstNameMiddleInitial(final String theInputString) {
        String regex = "^[a-zA-Z]+(([',.\\-]*[a-zA-Z, ])?[a-zA-Z]*)*$";
        checkPattern(theInputString, regex);
    }


    /**
     * Uses RegEx to validate theInputString as a Date in MM-DD-YYYY format.
     * @param theInputString The string to validate against RegEx defined above.
     */
    private void mmDDYYYY(final String theInputString) {
        String regex = "^((1[0-2]?)-([1-31])-([0-9][0-9][0-9][0-9])|((02)?-(29)-([0-9][0-9][02468][048]|[0-9][0-9][13579]))$)";
        checkPattern(theInputString, regex);
    }


    /**
     * Uses RegEx to validate theInputString as a House address -
     *                                      Street number,
     *                                      street name,
     *                                      abbreviation for road, street, boulevard or avenue
     * @param theInputString The string to validate against RegEx defined above.
     */
    private void houseAddress(final String theInputString) {

        String regex = "^(\\d{3,})\\s?(\\w{0,5})\\s([a-zA-Z]{2,30})\\s([a-zA-Z]{2,15})\\.?\\s?(\\w{0,5})$";
        checkPattern(theInputString, regex);

    }


    /**
     * Uses RegEx to validate theInputString as
     *                                  City followed by
     *                                  state followed by
     *                                  zip as it should appear on a letter
     * @param theInputString The string to validate against RegEx defined above.
     */
    private void cityStateZip(final String theInputString) {

        String regex = "^(([\\w[\\s]?]+,) (A[KLRZ]|C[AOT]|D[CE]|FL|GA|HI|I[ADLN]|K[SY]|LA|M[ADEINOST]|N[CDEHJMVY]|O[HKR]|P[AR]|RI|S[CD]|T[NX]|UT|V[AIT]|W[AIVY]) (\\d{5}(-\\d{4})?))$";
        checkPattern(theInputString, regex);

    }


    /**
     * Uses RegEx to validate theInputString as Military time, including seconds.
     * Accepted as HH:MM:SS, invalid otherwise.
     * @param theInputString The string to validate against RegEx defined above.
     */
    private void militaryTimeWithSeconds(final String theInputString) {

        String regex = "^((([0]?[1-9])(:|\\.)[0-5][0-9]((:|\\.)[0-5][0-9])?)|(([0]?[0-9]|1[0-9]|2[0-3])(:|\\.)[0-5][0-9]((:|\\.)[0-5][0-9])))$";
        checkPattern(theInputString, regex);

    }



    /**
     * Uses RegEx to validate theInputString as US Currency down to the penny (ex: $123,456,789.23).
     * Required to have $ at start of string, pennies can be validated but are optional.
     * $123,456,789 is valid
     * 123,456,789.23 is invalid
     *
     * @param theInputString The string to validate against RegEx defined above.
     */
    private void usCurrencyToPenny(final String theInputString) {

        String regex = "^\\$([0-9]{1,3},([0-9]{3},)*[0-9]{3}|[0-9]+)(.[0-9][0-9])?$";
        checkPattern(theInputString, regex);

    }


    /**
     * Uses RegEx to validate theInputString as URL, including http:// (upper and lower case should be accepted).
     * https://regexlib.com/UserPatterns.aspx?authorId=0efd0ef1-6d4c-4835-89b2-336941ca3c67
     * @param theInputString The string to validate against RegEx defined above.
     */
    private void urlWithHttp(final String theInputString) {

        String regex = "^((((H|h)(T|t)|(F|f))(T|t)(P|p)((S|s)?))\\://)?(www.|[a-zA-Z0-9].)[a-zA-Z0-9\\-\\.]+\\.[a-zA-Z]{2,6}(\\:[0-9]{1,5})*(/($|[a-zA-Z0-9\\.\\,\\;\\?\\'\\+&amp;%\\$#\\=~_\\-]+))*$";
        checkPattern(theInputString, regex);

    }


    /**
     * Uses RegEx to validate theInputString as a password that contains at least
     *                10 characters
     *                and includes at least:
     *                one upper case character,
     *                lower case character,
     *                digit,
     *                punctuation mark,
     *                and does not have more than 3 consecutive lower case characters
     *                todo fails for 3 consecutive characters of any type
     * @param theInputString The string to validate against RegEx defined above.
     */
    private void password(final String theInputString) {

                        //check length      1+ lc       1+uc        1+ sc                  no more than 3 in a row
        String regex = "^(?=.{10,}$)(?=\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^{}&*().,<>;])(?:([\\w\\d*?!:;])\\1?(?!\\1))+$";
        checkPattern(theInputString, regex);

    }


    /**
     * Uses RegEx to validate theInputString as All words
     * containing an odd number of alphabetic characters,
     * ending in "ion".
     * @param theInputString The string to validate against RegEx defined above.
     */
    private void oddCharactersEndingIon(final String theInputString) {
        String regex = "^(..)*(ion)$";
        checkPattern(theInputString, regex);
    }


    private void checkPattern(final String theInputString, final String theRegex){
        boolean result = false;
        Pattern pattern = Pattern.compile(theRegex);
        Matcher matcher = pattern.matcher(theInputString);
        boolean matches = matcher.matches();
        System.out.println("Matches test: " + matches);
        myResultList.add(matches);
    }

    /**
     * Chooses the appropriate method to call based on user input.
     * @param theOption A String representing the method to select.
     * @param theNextLine The String to be used for RegEx validation. 
     */
    private void getMethodCall(final String theOption, final String theNextLine) {
        switch (theOption) {
            case "A" -> socialSecurityNumber(theNextLine);
            case "B" -> usPhoneNumber(theNextLine);
            case "C" -> emailAddress(theNextLine);
            case "D" -> lastNameFirstNameMiddleInitial(theNextLine);
            case "E" -> mmDDYYYY(theNextLine);
            case "F" -> houseAddress(theNextLine);
            case "G" -> cityStateZip(theNextLine);
            case "H" -> militaryTimeWithSeconds(theNextLine);
            case "I" -> usCurrencyToPenny(theNextLine);
            case "J" -> urlWithHttp(theNextLine);
            case "K" -> password(theNextLine);
            case "L" -> oddCharactersEndingIon(theNextLine);

        }
    }

    @Override
    public String toString() {
        return myResultList.toString();
    }

}
