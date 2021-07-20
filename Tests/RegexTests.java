package RegexApplication.Tests;

import RegexApplication.src.Testable;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for regex methods. Extends helpers from Testable.
 * @author Dustin Ray
 * @version Summer 2021
 */
public class RegexTests extends Testable {

    /** Contains list of strings to test. */
    private final ArrayList<String> myTestList;

    /** regex to match strings against */
    private String myRegEx;

    /** Constructor, initialize test list. */
    public RegexTests () {
        myTestList = new ArrayList<>();
    }

    /**
     * I am validator. Feed me input.
     * @param theInString string to match against myRegex
     * @return true if match found, false otherwise.
     */
    private boolean validator(String theInString) {

        Pattern pattern =
                Pattern.compile(myRegEx);
        Matcher matcher =
                pattern.matcher(theInString);

        boolean found = false;
        while (matcher.find()) {
            found = true;
        }
        return found;
    }

    /** Clear test assets for reuse. */
    @Before
    public void init() {
        myRegEx = "";
    }

    /**
     * Uses RegEx to validate theInputString as a Social Security Number.
     * valid if format is: ###-##-#### or #########, invalid otherwise.
     * https://www.ssa.gov/history/ssn/geocard.html#:~:text=Number%20Has%20Three%20Parts,digits%20is%20the%20Serial%20Number
     * https://www.geeksforgeeks.org/how-to-validate-ssn-social-security-number-using-regular-expression/
     */
    @Test
    public void testSocialSecurityNumber() {
        myRegEx = "^(?!666|000|9\\d{2})\\d{3}[-\s]?(?!00)\\d{2}[-\s]?(?!0{4})\\d{4}$";

        myTestList.add("555-55-5555");
        myTestList.add("555555555");
        for (String s : myTestList) {assertTrue(validator(s));}

        myTestList.clear();

        myTestList.add("5555555555");
        myTestList.add("555555555A");
        myTestList.add("55555555");
        myTestList.add("555-55-555");
        myTestList.add("555-55-55555");
        myTestList.add("5555-5-5555");
        myTestList.add("");
        myTestList.add(" ");
        for (String s : myTestList) {assertFalse(validator(s));}

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
     */
    @Test
    public void testUsPhoneNumber() throws FileNotFoundException {
        myRegEx = "^[(]?[2-9]\\d{2}[)]?[-\s]?\\d{3}[-\s]?\\d{4}$";

        myTestList.add("(234)5555555");
        myTestList.add("(234)555-5555");
        myTestList.add("(234)-555-5555");
        myTestList.add("234-555-5555");
        myTestList.add("2345555555");

        for (String s : myTestList) {assertTrue(validator(s) && checkValidAreaCode(s));}

        myTestList.clear();

        myTestList.add("(234)55555555");
        myTestList.add("(199)5555555");
        myTestList.add("(234)555555");
        myTestList.add("(234)555A5555");
        myTestList.add("(199)555A5555");
        myTestList.add("(199)555A5555");
        myTestList.add("(234)555 555");
        myTestList.add("");
        myTestList.add(" ");
        for (String s : myTestList) {assertFalse(validator(s) && checkValidAreaCode(s));}

    }

    /**
     * Uses RegEx to validate theInputString as a valid E-mail address.
     * https://regexlib.com/REDetails.aspx?regexp_id=1855
     */
    @Test
    public void testEmailAddress() {
        myRegEx = "^([a-zA-Z0-9]+(?:[.-]?[a-zA-Z0-9]+)*@[a-zA-Z0-9]+(?:[.-]?[a-zA-Z0-9]+)*\\.[a-zA-Z]{2,7})$";

        myTestList.add("a@a.com");
        myTestList.add("a@a.co");
        myTestList.add("a@a.gov");

        for (String s : myTestList) {assertTrue(validator(s));}

        myTestList.clear();

        myTestList.add("a@a.3");
        myTestList.add("3");
        myTestList.add("@");
        myTestList.add("3@3.3");
        myTestList.add("");
        myTestList.add(" ");
        for (String s : myTestList) {assertFalse(validator(s));}

    }

    /**
     * Uses RegEx to validate theInputString as a Name on a class roster,
     * assuming one or more middle initials - Last name, First name, MI
     * valid: Last, First R M S
     *        Last, First R
     *        Last, First R.
     *        Last, First R. M. S.
     * Accepts hyphenated last names.
     * invalid: Last, First RMS
     *          and otherwise
     * support for international characters is included.
     */
    @Test
    public void testValidLastNameFirstNameMiddleInitial() {

        myRegEx = "^([a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð,'-]*,) ([a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð']?)+((([\\s][A-Za-zàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð])[.])*|([\\s][A-Za-zàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð])*)$";

        myTestList.add("Smith, John");
        myTestList.add("o'malley-Smith, Jones");
        myTestList.add("Smith, Jones J.");
        myTestList.add("Smith, Jones J. R.");
        myTestList.add("Smith, Jones J");
        myTestList.add("Smith, Jones J R");

        for (String s : myTestList) {assertTrue(validator(s));}
        myTestList.add("");
        myTestList.clear();
        myTestList.add("o'malley, Smith-Jones");
        myTestList.add("o'malley, Smith-Jones Johns");
        myTestList.add("Smith, Jones J. R");
        myTestList.add("o'malley, John Smith");
        myTestList.add("o'malley, John Smith-Jones");
        myTestList.add("Smith, John Jones");
        myTestList.add("Smith. Jones J.");
        myTestList.add("Smith");
        myTestList.add("3");
        myTestList.add("Smith Smith Smith");
        myTestList.add(" ");

        for (String s : myTestList) {assertFalse(validator(s));}

    }

    /**
     * Uses RegEx to validate theInputString as a Date in MM-DD-YYYY format.
     */
    @Test
    public void testMMDDYYYY() {
        myRegEx = "(0\\d{1}|1[0-2])-([0-2]\\d{1}|3[0-1])-([0-9]){2}\\d{2}";

        myTestList.add("09-22-1992");
        myTestList.add("09-22-0000");
        myTestList.add("09-22-9999");
        myTestList.add("02-29-2016");
        for (String s : myTestList) {assertTrue(validator(s));}
        myTestList.clear();

        myTestList.add("09-22-1992");
        myTestList.add("09-22-0000");
        myTestList.add("09-22-9999");
        myTestList.add("02-29-2016");
        for (String s : myTestList) {assertTrue(validator(s) && checkValidDate(s));}

        myTestList.clear();
        myTestList.add("");
        myTestList.add(" ");
        myTestList.add("09-31-2016");
        myTestList.add("00-29-2016");
        myTestList.add("02-29-2017");
        myTestList.add("01-00-2016");
        myTestList.add("00-00-0000");
        for (String s : myTestList) {assertFalse(validator(s) && checkValidDate(s));}

    }

    /**
     * Uses RegEx to validate theInputString as a House address -
     *      Street number,
     *      street name,
     *      abbreviation for road, street, boulevard or avenue
     */
    @Test
    public void testHouseAddress() {
        myRegEx = "^(\\d{3,})\\s?(\\w{0,5})\\s([a-zA-Z]{2,30})\\s([a-zA-Z]{2,15})\\.?\\s?(\\w{0,5})$";

        myTestList.add("123 StreetName blvd");
        myTestList.add("123 StreetName st");
        myTestList.add("123 StreetName ave");
        myTestList.add("123 StreetName avenue");
        myTestList.add("123 StreetName boulevard");
        myTestList.add("123 StreetName street");

        for (String s : myTestList) {assertTrue(validator(s));}

        myTestList.clear();
        myTestList.add("123 StreetName street, lakewood WA 98498");
        myTestList.add("streetName street");
        myTestList.add("");
        myTestList.add(" ");
        for (String s : myTestList) {assertFalse(validator(s));}

    }

    /**
     * Uses RegEx to validate theInputString as
     *                                  City followed by
     *                                  state followed by
     *                                  zip as it should appear on a letter
     * Valid: (city), (state code, i.e. WA) (zip code)
     * Invalid: (city), (state name, i.e. Washington) (zip code)
     *          and otherwise
     */
    @Test
    public void testCityStateZip() {
        myRegEx = "^(([\\w[\\s]?]+,) (A[KLRZ]|C[AOT]|D[CE]|FL|GA|HI|I[ADLN]|K[SY]|LA|M[ADEINOST]|N[CDEHJMVY]|O[HKR]|P[AR]|RI|S[CD]|T[NX]|UT|V[AIT]|W[AIVY]) (\\d{5}(-\\d{4})?))$";

        myTestList.add("Lakewood, WA 98498");
        for (String s : myTestList) {assertTrue(validator(s));}
        myTestList.clear();
        myTestList.add("Lakewood, ZZ 98498");
        myTestList.add("Lakewood, WA");
        myTestList.add("Lakewood, WA, 98498");
        myTestList.add("Lakewood, Washington 98498");
        myTestList.add("");
        myTestList.add(" ");
        for (String s : myTestList) {assertFalse(validator(s));}

    }

    /**
     * Uses RegEx to validate theInputString as Military time, including seconds.
     * Accepted as HH:MM:SS, invalid otherwise.
     */
    @Test
    public void testMilitaryTimeWithSeconds() {
        myRegEx = "^((([0]?[1-9])(:|\\.)[0-5][0-9]((:|\\.)[0-5][0-9])?)|(([0]?[0-9]|1[0-9]|2[0-3])(:|\\.)[0-5][0-9]((:|\\.)[0-5][0-9])))$";

        myTestList.add("23:23:23");
        myTestList.add("00:00:00");
        myTestList.add("01:01:01");
        for (String s : myTestList) {assertTrue(validator(s));}
        myTestList.clear();

        myTestList.add("1:1:1");
        myTestList.add("24:00:00");
        myTestList.add("00:00");
        myTestList.add("");
        myTestList.add(" ");
        for (String s : myTestList) {assertFalse(validator(s));}

    }

    /**
     * Uses RegEx to validate theInputString as US Currency down to the penny (ex: $123,456,789.23).
     * Required to have $ at start of string, pennies can be validated but are optional.
     * $123,456,789 is valid
     * 123,456,789.23 is invalid
     */
    @Test
    public void testUsCurrencyToPenny() {
        myRegEx = "^\\$([0-9]{1,3},([0-9]{3},)*[0-9]{3}|[0-9]+)(.[0-9][0-9])?$";

        myTestList.add("$123,456,789.00");
        myTestList.add("$123,456,789");
        myTestList.add("$0.00");

        for (String s : myTestList) {assertTrue(validator(s)); }
        myTestList.clear();

        myTestList.add("-$123,456,789.00");
        myTestList.add("$-123,456,789");
        myTestList.add("$-0.00");
        myTestList.add("123,456,789.00");
        myTestList.add("123,456,789");
        myTestList.add("0");
        myTestList.add("$");
        myTestList.add("");
        myTestList.add(" ");

        for (String s : myTestList) {assertFalse(validator(s));}

    }


    /**
     * Uses RegEx to validate theInputString as URL, including http:// (upper and lower case should be accepted).
     * https://regexlib.com/UserPatterns.aspx?authorId=0efd0ef1-6d4c-4835-89b2-336941ca3c67
     */
    @Test
    public void testUrlWithHttp() {
        myRegEx = "^((((H|h)(T|t)|(F|f))(T|t)(P|p)((S|s)?))\\://)?(www.|[a-zA-Z0-9].)[a-zA-Z0-9\\-\\.]+\\.[a-zA-Z]{2,6}(\\:[0-9]{1,5})*(/($|[a-zA-Z0-9\\.\\,\\;\\?\\'\\+&amp;%\\$#\\=~_\\-]+))*$";

        myTestList.add("https://www.a.com");
        myTestList.add("HTTPS://www.a.com");
        myTestList.add("HTTPS://www.a.co");
        myTestList.add("HTTPS://www.0.com");
        myTestList.add("HTTPS://www.a.co.za");
        myTestList.add("https://www.a.co");
        myTestList.add("https://www.0.com");
        myTestList.add("https://www.a.co.za");
        myTestList.add("http://www.a.com");
        myTestList.add("HTTP://www.a.com");
        myTestList.add("HTTP://www.a.co");
        myTestList.add("HTTP://www.0.com");
        myTestList.add("HTTP://www.a.co.za");
        myTestList.add("http://www.a.co");
        myTestList.add("http://www.0.com");
        myTestList.add("http://www.a.co.za");
        myTestList.add("HTTPS://wwwa.co");


        for (String s : myTestList) {assertTrue(validator(s));}

        myTestList.clear();

        myTestList.add("https://www.a.");
        myTestList.add("HTTPS:/www.a.com");
        myTestList.add("HTTPS//www.0.com");
        myTestList.add("HTTPS:www.a.co.");
        myTestList.add("https://www.a.");
        myTestList.add("https:www.0.com");
        myTestList.add("https/www.a.co.za");
        myTestList.add("");
        myTestList.add(" ");

        for (String s : myTestList) {assertFalse(validator(s));}

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
     */
    @Test
    public void testPassword() {

        myRegEx = "^(?=.{10,}$)(?=\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(?!.*([a-z])\\1{2}).*$";

        myTestList.add("48as4tAaa1!");
        myTestList.add("48as4tA1!!!");
        myTestList.add("48as4tAa1!48as4tAa1!");
        myTestList.add("48as4tAa1[");
        myTestList.add("48as4tAa1!");

        for (String s : myTestList) {assertTrue(validator(s));}

        myTestList.clear();
        myTestList.add("8as4tAa1!");
        myTestList.add("48as4tAaaa1!");
        myTestList.add("48as4tAaaa1!");
        myTestList.add("48as4tAa1");
        myTestList.add("AAAAA");
        myTestList.add("AAAAa");
        myTestList.add("1");
        myTestList.add("12345");

        for (String s : myTestList) {assertFalse(validator(s));}

    }


    /**
     * Uses RegEx to validate theInputString as All words
     * containing an odd number of alphabetic characters,
     * ending in "ion".
     */
    @Test
    public void testOddCharactersEndingIon() {
        myRegEx = "^(..)*(ion)$";

        myTestList.add("ion");
        myTestList.add("llion");
        myTestList.add("imagination");

        for (String s : myTestList) {assertTrue(validator(s));}

        myTestList.clear();

        myTestList.add("ionn");
        myTestList.add("lion");
        myTestList.add("imagiation");
        myTestList.add("on");
        myTestList.add("lilon");
        myTestList.add("ionion");
        myTestList.add("");
        myTestList.add(" ");
        for (String s : myTestList) {assertFalse(validator(s));}
    }




}
