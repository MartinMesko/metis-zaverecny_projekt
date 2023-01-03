package UI.helpers;

import utils.tangible.OutObject;
import utils.tangible.TryParseHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.time.*;

public final class InputHelper {

    //C# TO JAVA CONVERTER WARNING: Nullable reference types have no equivalent in Java:
    //ORIGINAL LINE: string? input = Console.ReadLine();
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

    public static String ReadString() {
        return new Scanner(System.in).nextLine();
    }

    public static String ReadString(String prompt) {
        System.out.print(prompt);
        return new Scanner(System.in).nextLine();
    }

    public static LocalDateTime ReadDateTime() {
        String input = new Scanner(System.in).nextLine();
        LocalDateTime value = LocalDateTime.MIN;

//        OutObject<LocalDateTime> tempOut_value = new OutObject<LocalDateTime>();
//        while (!LocalDateTime.TryParse(input, tempOut_value)) {
//            value = tempOut_value.outArgValue;
//            System.out.println("Please enter a valid value.");
//            input = new Scanner(System.in).nextLine();
//        }
//        value = tempOut_value.outArgValue;

        return value;
    }

    public static LocalDateTime ReadDateTime(String prompt) {
        OutputHelper.writeLine(prompt);
        return ReadDateTime();
    }

}

