package utils.date_utils;

import core.entities.RentalEntry;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateUtils {

    public static final String DATE_FORMAT_DD_MM_YYYY = "dd.MM.yyyy";
    public static final int MAX_ALLOWED_LENGTH_OF_INPUT_DATE = 10;

    public static final String nowDate = LocalDate.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT_DD_MM_YYYY));

    private static final SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_DD_MM_YYYY);

    public static Date parseStringDateIntoDate(String strDate) {
        try {
            return sdf.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String addDaysToRentedDay(RentalEntry entry, int addDays) {
        Date rentedDate = DateUtils.parseStringDateIntoDate(entry.getRentedDate());
        assert rentedDate != null;
        long addDaysToRentedDate = rentedDate.getTime() + TimeUnit.DAYS.toMillis(addDays);

        Date rentedDateWithAddedDays = new Date(addDaysToRentedDate);
        return sdf.format(rentedDateWithAddedDays);
    }

}
