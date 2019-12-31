package com.cblib.util;

import android.annotation.SuppressLint;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@SuppressLint("SimpleDateFormat")
public class CBDateTime {
    /**
     * Do not initialze instance for static class only
     */
    private CBDateTime() {
    }

    public static final String DATE_FORMATTER_TYPE_1 = "dd MMM yyyy";
    public static final String DATE_FORMATTER_TYPE_2 = "MMM dd, yyyy hh:mm:ss a";
    public static final String DATE_FORMATTER_TYPE_3 = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMATTER_TYPE_4 = "dd MMMM yyyy";
    public static final String DATE_FORMATTER_TYPE_5 = "dd MMMM yyyy HH:mm";
    public static final String DATE_FORMATTER_TYPE_6 = "dd-MM-yyyy HH:mm";
    public static final String DATE_FORMATTER_TYPE_7 = "dd-MM-yyyy";
    public static final String DATE_FORMATTER_TYPE_8 = "yyyy-MM-dd";
    public static final String DATE_FORMATTER_TYPE_9 = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    public static final String DATE_FORMATTER_TYPE_10 = "dd/MM/yyyy";
    public static final String DATE_FORMATTER_TYPE_11 = "EEEE, dd MMM yyyy HH:mm:ss";
    public static final String DATE_FORMATTER_TYPE_12 = "EEEE, dd MMM yyyy";
    public static final String DATE_FORMATTER_TYPE_13 = "dd/MM/yyyy hh:mm a";
    public static final String DATE_FORMATTER_TYPE_14 = "dd/MM/yyyy, hh:mma";
    public static final String DATE_FORMATTER_TYPE_15 = "dd MMM, yyyy hh:mm:ss a";
    public static final String DATE_FORMATTER_TYPE_16 = "dd MMM yyyy HH:mm:ss";

    public static class Formatter {

        /**
         * @param date             new Date()
         * @param dateResultFormat example : yyyy-MM-dd'T'HH:mm:ss'Z'
         * @return 2017-10-27T19:01:22Z
         */
        public static String toFormat(Date date, String dateResultFormat) {
            return new SimpleDateFormat(dateResultFormat, Locale.US).format(date);
        }

        /**
         * @param dateString "2010-10-15T09:27:37Z"
         * @param format     yyyy-MM-dd'T'HH:mm:ss'Z'
         * @return new Date()
         */
        public static Date toDate(String dateString, String format) {
            return toDate(dateString, format, false);
        }

        /**
         * @param dateString "2010-10-15T09:27:37Z"
         * @param format     yyyy-MM-dd'T'HH:mm:ss'Z'
         * @return new Date()
         */
        @SuppressWarnings("TryWithIdenticalCatches")
        public static Date toDate(String dateString, String format, boolean isReturnNull) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.US);
            try {
                return simpleDateFormat.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return isReturnNull ? null : new Date();
        }


        /**
         * @param dateString
         * @param format
         * @param locale
         * @return
         */
        @SuppressWarnings("TryWithIdenticalCatches")
        public static String toDateWithLocale(String dateString, String format, Locale locale) {
            long milliSeconds = toDate(dateString, DATE_FORMATTER_TYPE_2).getTime();
            Date date = new Date(milliSeconds);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, locale);
            try {
                return simpleDateFormat.format(date);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return new Date().toString();
        }

        public static String toDateWithLocale(String dateString, String formatTo, String formatFrom, String localeString) {
            Locale locale = new Locale(localeString);
            String date = fromValueToValue(dateString,formatTo,formatFrom);
            return toDateWithLocale(date,formatFrom, locale);
        }


        /**
         * @param value      2010-10-15T09:27:37Z
         * @param formatFrom yyyy-MM-dd'T'HH:mm:ss'Z'
         * @param formatTo   dd MMM yyyy
         * @return 15 Oct 2010
         */
        public static String fromValueToValue(String value, String formatFrom, String formatTo) {

            if (value == null) {
                return null;
            }

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatFrom, Locale.US);
            try {
                return toFormat(simpleDateFormat.parse(value), formatTo);
            } catch (ParseException ignored) {
            } catch (Exception e) {
                e.printStackTrace();
            }
            return value;
        }

        public static String fromMilliSecondToValue(String milli, String formatTo) {
            if (milli == null) {
                return null;
            }
            long milliSeconds = Long.parseLong(milli);
            Date date = new Date(milliSeconds);
            return toFormat(date, formatTo);
        }

        public static boolean isToday(Date date1, Date date2) {
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(date1);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(date2);

            int year1 = calendar1.get(Calendar.YEAR);
            int dayOfYear1 = calendar1.get(Calendar.DAY_OF_YEAR);

            int year2 = calendar2.get(Calendar.YEAR);
            int dayOfYear2 = calendar2.get(Calendar.DAY_OF_YEAR);

            return year1 == year2 && dayOfYear1 == dayOfYear2;
        }

        /**
         * @param _date
         * @return integer month
         */
        public static int getDateMonth(Date _date) {
            if (_date != null) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(_date);
                return cal.get(Calendar.MONTH) + 1;
            } else
                return 0;
        }

        /**
         * @param _date
         * @return integer year
         */
        public static int getDateYear(Date _date) {
            if (_date != null) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(_date);
                return cal.get(Calendar.YEAR);
            } else
                return 0;
        }

        /**
         * @param month
         * @return month full name
         */
        public static String getMonthFullName(int month) {
            return new DateFormatSymbols(Locale.US).getMonths()[month - 1];
        }
    }

    public static class DateCalculation {

        /**
         * Get date with plus or minus of days
         * <p>
         * To get date 30 days after starting date
         * e.g. getDateFromStartDate(startingDate, 30)
         * <p>
         * To get date 30 days before starting date
         * e.g. getDateFromStartDate(startingDate, -30)
         *
         * @param startingDate current date
         * @param dayDifferent plus days
         * @return
         */
        public static Date getDatePlusDay(Date startingDate, int dayDifferent) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startingDate);
            calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + dayDifferent);

            return calendar.getTime();
        }

        public static String getTodayDate(String format) {
            SimpleDateFormat dateFormatter = new SimpleDateFormat(format, Locale.US);
            return dateFormatter.format(new Date());
        }


        public static Date getDatePlusMonths(Date startingDate, int monthDifferent) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startingDate);
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + monthDifferent);
            return calendar.getTime();
        }

        public static Date getDateCountDate(Date fromDate, Date todayDate, int monthDifferent) {

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fromDate);
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + monthDifferent);

            if (calendar.getTime().getTime() < todayDate.getTime()) {
                return calendar.getTime();
            } else {
                return todayDate;
            }
        }

        public static String getDayOfMonthSuffix(int n) {
            switch(n){
                case 1:
                case 21:
                case 31:
                    return "st";
                case 2:
                case 22:
                    return "nd";
                case 3:
                case 13:
                    return "rd";
                default:
                    return "th";
            }
        }

        public static int daysDifference(Date startDate, Date endDate) {
            long different = endDate.getTime() - startDate.getTime();
            long daysInMilli = 1000 * 60 * 60 * 24;
            long elapsedDays = (different / daysInMilli);

            if (elapsedDays > Integer.MAX_VALUE) {
                return Integer.MAX_VALUE;
            } else {
                return (int) elapsedDays;
            }
        }

        public static Date getZeroTime(Date date) {
            Calendar calDateTime = Calendar.getInstance();
            calDateTime.setTime(date);

            calDateTime.setTime(date);
            calDateTime.set(Calendar.HOUR_OF_DAY, 0);
            calDateTime.set(Calendar.MINUTE, 0);
            calDateTime.set(Calendar.SECOND, 0);
            calDateTime.set(Calendar.MILLISECOND, 0);
            return calDateTime.getTime();
        }
    }

}
