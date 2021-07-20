package RegexApplication.src;

import java.io.FileNotFoundException;
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
public class RegEx extends Testable {

    /** Constructor for class. */
    public RegEx() throws FileNotFoundException {
        Scanner theInput = new Scanner(System.in);
        System.out.println("Please select an option: ");
        String option = theInput.nextLine();
        while(!option.equals("Q")) {
            System.out.println("in loop, enter text to test;");
            getMethodCall(option, theInput.nextLine());
            option = theInput.nextLine();

        }System.out.println("Exiting");
    }

    /**
     * Uses RegEx to validate theInputString as a Social Security Number.
     * https://www.ssa.gov/history/ssn/geocard.html#:~:text=Number%20Has%20Three%20Parts,digits%20is%20the%20Serial%20Number
     * https://www.geeksforgeeks.org/how-to-validate-ssn-social-security-number-using-regular-expression/
     * @param theInputString The string to validate against RegEx defined above.
     * @return result of regex pattern match
     */
    private boolean socialSecurityNumber(final String theInputString) {
        String regex = "^(?!666|000|9\\d{2})\\d{3}[-\s]?(?!00)\\d{2}[-\s]?(?!0{4})\\d{4}$";
        return checkPattern(theInputString, regex);
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
     * @return result of regex pattern match
     */
    private boolean usPhoneNumber(final String theInputString) throws FileNotFoundException {
        String regex = "^[(]?[2-9]\\d{2}[)]?[-\s]?\\d{3}[-\s]?\\d{4}$";
        return checkPattern(theInputString, regex) && checkValidAreaCode(theInputString);
    }

    /**
     * Uses RegEx to validate theInputString as a valid E-mail address.
     * https://regexlib.com/REDetails.aspx?regexp_id=1855
     * @param theInputString The string to validate against RegEx defined above.
     * @return result of regex pattern match
     */
    private boolean emailAddress(final String theInputString) {
        String regex = "^([a-zA-Z0-9]+(?:[.-]?[a-zA-Z0-9]+)*@[a-zA-Z0-9]+(?:[.-]?[a-zA-Z0-9]+)*\\.[a-zA-Z]{2,7})$";
        return checkPattern(theInputString, regex);
    }


    /**
     * Uses RegEx to validate theInputString as a Name on a class roster,
     * assuming one or more middle initials - Last name, First name, MI
     * valid: Last, First R M S
     *        Last, First R
     *        Last, First R.
     *        Last, First R. M. S.
     * Accepts hyphenated last names.
     * invalid: Last, First RMS and otherwise
     * support for international characters is included.
     *
     * @param theInputString The string to validate against RegEx defined above.
     * @return result of regex pattern match
     */
    private boolean lastNameFirstNameMiddleInitial(final String theInputString) {
        String regex = "^([a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð,'-]*,) ([a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð']?)+((([\\s][A-Za-zàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð])[.])*|([\\s][A-Za-zàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð])*)$";

        return checkPattern(theInputString, regex);
    }


    /**
     * Uses RegEx to validate theInputString as a Date in MM-DD-YYYY format.
     * Valid : MM-DD-YYYY
     * Invalid: MMDDYYYY and otherwise. Uses helper to check for leap year
     * and valid days in months.
     *
     * @param theInputString The string to validate against RegEx defined above.
     * @return result of regex pattern match
     */
    private boolean mmDDYYYY(final String theInputString) {
        String regex = "(0\\d{1}|1[0-2])-([0-2]\\d{1}|3[0-1])-([0-9]){2}\\d{2}";
        return (checkPattern(theInputString, regex) && checkValidDate(theInputString));
    }


    /**
     * Uses RegEx to validate theInputString as a House address -
     *                                      Street number,
     *                                      street name,
     *                                      abbreviation for road, street, boulevard or avenue
     * I wanted to make this one more precise but I ran out of time.
     * @param theInputString The string to validate against RegEx defined above.
     * @return result of regex pattern match
     */
    private boolean houseAddress(final String theInputString) {
        String regex = "^(\\d{3,})\\s?(\\w{0,5})\\s([a-zA-Z]{2,30})\\s([a-zA-Z]{2,15})\\.?\\s?(\\w{0,5})$";
        return checkPattern(theInputString, regex);
    }


    /**
     * Uses RegEx to validate theInputString as
     *                                  City followed by
     *                                  state followed by
     *                                  zip as it should appear on a letter
     * @param theInputString The string to validate against RegEx defined above.
     * @return result of regex pattern match
     */
    private boolean cityStateZip(final String theInputString) {
        String regex = "^(([\\w[\\s]?]+,) (A[KLRZ]|C[AOT]|D[CE]|FL|GA|HI|I[ADLN]|K[SY]|LA|M[ADEINOST]|N[CDEHJMVY]|O[HKR]|P[AR]|RI|S[CD]|T[NX]|UT|V[AIT]|W[AIVY]) (\\d{5}(-\\d{4})?))$";
        return checkPattern(theInputString, regex);
    }


    /**
     * Uses RegEx to validate theInputString as Military time, including seconds.
     * Accepted as HH:MM:SS, invalid otherwise.
     * @param theInputString The string to validate against RegEx defined above.
     * @return result of regex pattern match
     */
    private boolean militaryTimeWithSeconds(final String theInputString) {
        String regex = "^((([0]?[1-9])(:|\\.)[0-5][0-9]((:|\\.)[0-5][0-9])?)|(([0]?[0-9]|1[0-9]|2[0-3])(:|\\.)[0-5][0-9]((:|\\.)[0-5][0-9])))$";
        return checkPattern(theInputString, regex);
    }

    /**
     * Uses RegEx to validate theInputString as US Currency down to the penny (ex: $123,456,789.23).
     * Required to have $ at start of string, pennies can be validated but are optional.
     * $123,456,789 is valid
     * 123,456,789.23 is invalid
     *
     * @param theInputString The string to validate against RegEx defined above.
     * @return result of regex pattern match
     */
    private boolean usCurrencyToPenny(final String theInputString) {
        String regex = "^\\$([0-9]{1,3},([0-9]{3},)*[0-9]{3}|[0-9]+)(.[0-9][0-9])?$";
        return checkPattern(theInputString, regex);
    }

    /**
     * Uses RegEx to validate theInputString as URL, including http:// (upper and lower case should be accepted).
     * https://regexlib.com/UserPatterns.aspx?authorId=0efd0ef1-6d4c-4835-89b2-336941ca3c67
     * @param theInputString The string to validate against RegEx defined above.
     * @return result of regex pattern match
     */
    private boolean urlWithHttp(final String theInputString) {
        String regex = "^((((H|h)(T|t)|(F|f))(T|t)(P|p)((S|s)?))\\://)?(www.|[a-zA-Z0-9].)[a-zA-Z0-9\\-\\.]+\\.[a-zA-Z]{2,6}(\\:[0-9]{1,5})*(/($|[a-zA-Z0-9\\.\\,\\;\\?\\'\\+&amp;%\\$#\\=~_\\-]+))*$";
        return checkPattern(theInputString, regex);
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
     *
     * @param theInputString The string to validate against RegEx defined above.
     * @return result of regex pattern match
     */
    private boolean password(final String theInputString) {
        String regex = "^(?=.{10,}$)(?=\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(?!.*([a-z])\\1{2}).*$";
        return checkPattern(theInputString, regex);
    }


    /**
     * Uses RegEx to validate theInputString as All words
     * containing an odd number of alphabetic characters,
     * ending in "ion".
     * @param theInputString The string to validate against RegEx defined above.
     * @return result of regex pattern match
     */
    private boolean oddCharactersEndingIon(final String theInputString) {
        String regex = "^(..)*(ion)$";
        return checkPattern(theInputString, regex);
    }


    /**
     * RegEx pattern checker.
     * @param theInputString is the string to match.
     * @param theRegex is the regex to match against.
     */
    private boolean checkPattern(final String theInputString, final String theRegex){
        Pattern pattern = Pattern.compile(theRegex);
        Matcher matcher = pattern.matcher(theInputString);
        boolean matches = matcher.matches();
        System.out.println("Matches RegEx test: " + matches);

        return matches;
    }

    /**
     * Chooses the appropriate method to call based on user input.
     * @param theOption A String representing the method to select.
     * @param theNextLine The String to be used for RegEx validation.
     */
    private void getMethodCall(final String theOption, final String theNextLine) throws FileNotFoundException {
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
}
