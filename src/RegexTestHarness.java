/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package RegexApplication.src;
import java.io.Console;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.regex.*;

public class RegexTestHarness {

    public static void main(String[] args){
        String testRegex = "^((\\d{2}((0[13578]|1[02])(0[1-9]|[12]\\d|3[01])|(0[13456789]|1[012])(0[1-9]|[12]\\d|30)|02(0[1-9]|1\\d|2[0-8])))|([02468][048]|[13579][26])0229)$";
        /**
         * Uses RegEx to validate theInputString as a password that contains at least
         *  *              10 characters
         *  *              and includes at least:
         *  *              one upper case character,
         *  *              lower case character,
         *  *              digit,
         *  *              punctuation mark,
         *  *              and does not have more than 3 consecutive lower case characters
         * @param theInputString The string to validate against RegEx defined above.
         */



        String regexStage1 = "^(?=.{10,}$)(?=\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^{}&*().,<>;])(?:([\\w\\d*?!:;])\\1?(?!\\1))+$";


        ArrayList<String> testList = new ArrayList<>();

        testList.add("8as4tAa1!");
        testList.add("48as4tAa1!");
        testList.add("48as4tAaa1!");
        testList.add("48as4tAaaa1!");
        testList.add("48as4tA1!!!");
        testList.add("48as4tAa1!48as4tAa1!");
        testList.add("48as4tAaaa1!");
        testList.add("48as4tAa1");
        testList.add("48as4tAa1[");
        testList.add("AAAAA");
        testList.add("AAAAa");
        testList.add("1");
        testList.add("12345");

        for (String s : testList) {
            Pattern pattern =
                    Pattern.compile(regexStage1);
            Matcher matcher =
                    pattern.matcher(s);
            boolean found = false;
            while (matcher.find()) {
                System.out.printf("I found the text" +
                                " << %s >> starting at " +
                                "index %d and ending at index %d.\n",
                        matcher.group(),
                        matcher.start(),
                        matcher.end());
                found = true;
            }
            if (!found) {
                System.out.println("No match found for " + s);
            }
        }
    }
}

