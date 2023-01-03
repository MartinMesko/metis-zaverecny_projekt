package UI.helpers;

import utils.tangible.OutObject;
import utils.tangible.TryParseHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public final class InputHelper {

    private static String resultDate;

    public static int readInt() {
        String input = new Scanner(System.in).nextLine();
        int value;

        OutObject<Integer> tempOut_value = new OutObject<Integer>();
        while (!TryParseHelper.tryParseInt(input, tempOut_value)) {
            System.out.println("Please enter a valid value.");
            input = new Scanner(System.in).nextLine();
        }
        value = tempOut_value.outArgValue;

        return value;
    }

    public static int readInt(int minValue, int maxValue) {
        var value = readInt();

        while (value < minValue || value > maxValue) {
            OutputHelper.writeLine(String.format("Please enter a number in the range from %1$s to %2$s", minValue, maxValue));
            value = readInt();
        }

        return value;
    }

    public static int readInt(String prompt, int minValue, int maxValue) {
        OutputHelper.writeLine(prompt);

        return readInt(minValue, maxValue);
    }

    public static void readKey(String prompt) {
        OutputHelper.writeLine(prompt);

        try {
            new BufferedReader(new InputStreamReader(System.in)).readLine();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public static String readString() {
        return new Scanner(System.in).nextLine();
    }

    public static String readString(String prompt) {
        System.out.print(prompt);
        return new Scanner(System.in).nextLine();
    }

    public static String readDateTime() {
        String input = new Scanner(System.in).nextLine();

        while (!validateDate(input)) {
            System.out.println("Please enter a valid value.");
            input = new Scanner(System.in).nextLine();
        }
        return resultDate;
    }

    public static String readDateTime(String prompt) {
        OutputHelper.writeLine(prompt);
        return readDateTime();
    }

    private static boolean validateDate(String strDate) {
        if (!strDate.trim().equals("")) {
            SimpleDateFormat sdfrmt = new SimpleDateFormat("dd.MM.yyyy");
            sdfrmt.setLenient(false);

            try {
                Date date = sdfrmt.parse(strDate);
                resultDate = sdfrmt.format(date);
            } catch (ParseException e) {
                return false;
            }
        }
        return true;
    }

}

