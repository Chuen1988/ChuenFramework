package com.cblib.util;

import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;


public class CBFormatter {
    /**
     * Do not initialze instance for static class only
     */
    private CBFormatter() {
    }

    /**
     * Regular expression used to check the current amount
     */
    public static final String REGEX_AMOUNT = "(\\d{1,3}(\\,\\d{3})*|(\\d+))(\\.\\d{2})";

    public static class Amount {
        private static final String DEFAULT_1_DECIMAL_FORMAT = "###,###,###,###,##0.0";
        private static final String DEFAULT_DECIMAL_FORMAT = "###,###,###,###,##0.00";
        private static final String DEFAULT_4_DECIMAL_FORMAT = "###,###,###,###,##0.0000";
        private static final String DEFAULT_0_DECIMAL_FORMAT = "###,###,###,###,##0";
        private static Locale locale = new Locale("en");
        private static DecimalFormatSymbols symbols = new DecimalFormatSymbols(locale);

        /**
         * Format currency to ###,###,###,###,##0.00
         * if amount is 0, it will default to 0.00
         *
         * @param amount non-formatted amount
         * @return formatted amount
         */
        public static String format(String amount) {
            return format(amount, false);
        }

        public static String formatSpecial(Double amount) {
            String formattedAmount = format(amount, false);

            if ((null != formattedAmount && formattedAmount.equalsIgnoreCase("0")) ||
                (null != formattedAmount && formattedAmount.equalsIgnoreCase("0.00"))) {
                return "-";
            }

            return formattedAmount;
        }

        /**
         * Format currency to ###,###,###,###,##0
         * if amount is 0, it will default to 0
         *
         * @param amount non-formatted amount
         * @return formatted amount
         */
        public static String format0Decimal(String amount) {
            if (TextUtils.isEmpty(amount)) {
                return amount;
            }

            /**
             * Important NOTICE
             */
            /** Use Big decimal instead because double amount will round up if digit more than 15 digits*/
            BigDecimal bigDecimalAmount;
            try {
                bigDecimalAmount = new BigDecimal(amount.replaceAll(",", ""));
            } catch (Exception e) {
                e.printStackTrace();
                return amount;
            }

            DecimalFormat formatter = new DecimalFormat(DEFAULT_0_DECIMAL_FORMAT, symbols);
            return formatter.format(bigDecimalAmount);
        }

        /**
         * Format currency to ###,###,###,###,##0.00
         * if amount is 0, it will default to 0.00
         *
         * @param amount non-formatted amount
         * @return formatted amount
         */
        public static String format(String amount, boolean is4Decimal) {
            if (TextUtils.isEmpty(amount)) {
                return amount;
            }

            /**
             * Important NOTICE
             */
            /** Use Big decimal instead because double amount will round up if digit more than 15 digits*/
            BigDecimal bigDecimalAmount;
            try {
                bigDecimalAmount = new BigDecimal(amount.replaceAll(",", ""));
            } catch (Exception e) {
                e.printStackTrace();
                return amount;
            }

            DecimalFormat formatter = new DecimalFormat(is4Decimal ? DEFAULT_4_DECIMAL_FORMAT : DEFAULT_DECIMAL_FORMAT, symbols);
            return formatter.format(bigDecimalAmount);
        }

        /**
         * Format currency to ###,###,###,###,##0.00
         * if amount is 0, it will default to 0.00
         *
         * @param amount non-formatted amount
         * @return formatted amount
         */
        public static String format1Decimal(String amount) {
            if (TextUtils.isEmpty(amount)) {
                return amount;
            }

            /**
             * Important NOTICE
             */
            /** Use Big decimal instead because double amount will round up if digit more than 15 digits*/
            BigDecimal bigDecimalAmount;
            try {
                bigDecimalAmount = new BigDecimal(amount.replaceAll(",", ""));
            } catch (Exception e) {
                e.printStackTrace();
                return amount;
            }

            DecimalFormat formatter = new DecimalFormat(DEFAULT_1_DECIMAL_FORMAT, symbols);
            return formatter.format(bigDecimalAmount);
        }

        /**
         * Format double amount to Amount Currency type
         *
         * @param amount double amount
         * @return formatted amount
         */
        public static String format(Double amount) {
            return format(amount, false);
        }

        /**
         * Format double amount to Amount Currency type
         *
         * @param amount     double amount
         * @param is4Decimal if is 4 decimal
         * @return formatted amount
         */
        public static String format(Double amount, boolean is4Decimal) {
            DecimalFormat formatter = new DecimalFormat(is4Decimal ? DEFAULT_4_DECIMAL_FORMAT : DEFAULT_DECIMAL_FORMAT, symbols);
            return formatter.format(amount);
        }


        /**
         * Format double amount to Amount Currency type
         *
         * @param amount     String amount
         * @return formatted amount
         */
        public static String formatWithoutRoundUp(String amount, boolean is4Decimal) {
            if (TextUtils.isEmpty(amount)) {
                return amount;
            }

            Double f1 = Double.parseDouble(amount);
            DecimalFormat formatter = new DecimalFormat(is4Decimal ? DEFAULT_4_DECIMAL_FORMAT : DEFAULT_DECIMAL_FORMAT, symbols);
            formatter.setRoundingMode(RoundingMode.DOWN); // Note this extra step
            return formatter.format(f1);
        }


        public static String formatRemoveComma(String amount) {
            return amount.replaceAll(",", "");
        }

        public static String formatIfNegativeShowCR(String amount) {
            /** Only for Credit Card amount display for Level 2 & 3**/
            if (TextUtils.isEmpty(amount)) {
                return amount;
            }

            /** Check if negative value**/
            if ("-".equals(amount.substring(0, 1))) {
                return "CR " + amount;
            } else {
                return amount;
            }
        }

        public static String formatInterestRate(String interestAmount) {
            String formattedInterestAmount = interestAmount;
            if (TextUtils.isEmpty(formattedInterestAmount)) {
                return formattedInterestAmount;
            }

            /** format to 2 decimal places **/
            formattedInterestAmount = format(formattedInterestAmount);
            /** replace dot with comma **/
            formattedInterestAmount = formattedInterestAmount.replace(".", ",");
            /** Append Percentage at the back**/
            formattedInterestAmount += "%";
            return formattedInterestAmount;
        }
    }

    @SuppressWarnings("deprecation")
    public static class HtmlToTextView {
        public static Spanned getHtmlText(String htmlString) {
            if (TextUtils.isEmpty(htmlString)) {
                return null;
            }

            htmlString = htmlString
                    .replaceAll("</br>", "<br>")
                    .replaceAll("\n", "<br>")
                    .replaceAll("<br/>", "<br>");

            if (Build.VERSION.SDK_INT >= 24) {
                return Html.fromHtml(htmlString, 1);
            } else {
                return Html.fromHtml(htmlString);
            }
        }


        public static String textSetColor(String text, String hexColorCode) {
            return "<font color='" + hexColorCode + "'>" + text + "</font>";
        }

        public static String textSetColor(String text, int hexColorCode) {
            return textSetColor(text, hexColorCode + "");
        }

        public static String textHtmlToNewLine(String text) {
            if (TextUtils.isEmpty(text)) {
                return text;
            }
            return text
                    .replaceAll("</br>", "\n")
                    .replaceAll("<br>", "\n")
                    .replaceAll("<br/>", "\n");
        }
    }

    public static String convertTransactionHistoryAmount(String sign, String amount) {
        if (TextUtils.isEmpty(sign)) {
            if (!TextUtils.isEmpty(amount)) {
                return amount;
            }
            return null;
        }

        String toReturn;
        if (sign.equalsIgnoreCase("D")) {
            toReturn = "- " + amount;

        } else if (sign.equalsIgnoreCase("C")) {
            toReturn = "+ " + amount;
        } else {
            toReturn = amount;
        }
        return toReturn;
    }

    public static class Mask {
        private static String KEY_ASK = "*";

        /*0 is first char */
        public static String phoneNumber(String phone, int from, int to) {
            return phoneNumber(phone, from, to, KEY_ASK);
        }

        public static String phoneNumber(String phone, int from, int to, String maskChar) {
            if (TextUtils.isEmpty(phone)) {
                return "";
            }
            int maskLength = to - from;
            if ((from + to + 1) > phone.length()) {
                return phone;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(phone.substring(0, from));
            for (int i = 0; i < maskLength; i++) {
                stringBuilder.append(maskChar);
            }
            stringBuilder.append(phone.substring(to));

            return stringBuilder.toString();
        }

        public static String maskName(String ktpLangkapName) {
            String starName = "*";
            StringBuilder maskName = new StringBuilder();
            if (TextUtils.isEmpty(ktpLangkapName)) {
                return "";
            }

            String[] listName = ktpLangkapName.split(" ");
            for (int i = 0; i < listName.length; i++) {
                listName[i] = listName[i];
                char singleName = listName[i].charAt(0);
                StringBuilder startString = new StringBuilder();
                for (int i1 = 1; i1 < listName[i].length(); i1++) {
                    startString.append(starName);
                }
                listName[i] = singleName + startString.toString();
            }

            for (String s : listName) {
                maskName.append(" ").append(s);
            }

            return maskName.toString();
        }
    }

    public static String removeNonNumberFromString(String string){
        return string.replaceAll("[^\\d+]","");
    }

    public static String NPWPFormat(String input){
        if(input.length() <= 0){
            return "";
        }
        input = removeNonNumberFromString(input);
        String Regex_NPWP = "##.###.###.#-###.###";
        int j = 0;
        StringBuilder result = new StringBuilder();
        char[] formatArray = Regex_NPWP.toCharArray();
        char[] inputArray = input.toCharArray();

        for (char c : formatArray) {
            if (!(c + "").equals("#")) {
                result.append(c);
            } else {
                result.append(inputArray[j]);
                j++;
                if (j == inputArray.length) {
                    break;
                }
            }
        }
        return result.toString();
    }

    //Account Formatter
//    public static class Account {
//        public static final String SEPARATOR = "-";
//
//        public static String formatRemoveSeparator(String _accountNumber) {
//            return _accountNumber.replaceAll(SEPARATOR, "");
//        }
//
//        public static String format(String acc, String type) {
//
//            //Commented by Chuen 3/10/2019 JTRAC VELO1SIT-308
////            if (TextUtils.isEmpty(type))
////                return acc;
//
//            return format(acc, SAccountList.AccountType.fromString(type));
//        }
//
//        /**
//         * This will format account number into XXX-XXX-XXXXX-X
//         * Account Type for:-
//         * S, D, FD, SP, M
//         *
//         * @param acc account number to be formatted
//         * @return formatted account
//         */
//        public static String format(String acc, SAccountList.AccountType type) {
//            if (TextUtils.isEmpty(acc) || null == type) {
//                return acc;
//            }
//
//            switch (type) {
//
//                case SAVING_ACCOUNT:
//                case MULTI_CURRENCY_ACCOUNT:
//                case TIME_DEPOSIT:
//                case LOAN:
//                case CURRENT_ACCOUNT:
//                case REWARD_POINT:
//                    if (acc.length() != 12) {
//                        return acc;
//                    }
//                    else {
//                        /** ddd-ddd-ddddd-d **/
//                        return acc.substring(0, 3) + SEPARATOR
//                                + acc.substring(3, 6) + SEPARATOR
//                                + acc.substring(6, 11) + SEPARATOR
//                                + acc.substring(11, 12);
//                    }
//                case CREDIT_CARD:
//                    if (acc.length() != 16) {
//                        return acc;
//                    }
//                    else {
//                        /** dddd-dddd-dddd-dddd **/
//                        return acc.substring(0, 4) + SEPARATOR
//                                + acc.substring(4, 8) + SEPARATOR
//                                + acc.substring(8, 12) + SEPARATOR
//                                + acc.substring(12, 16);
//                    }
//                case UNIT_TRUST:
//                case INSURANCE:
//                    /** Format not specify **/
//                    /** Return not formatted **/
//                    return acc;
//
//                //No Account type given use default formatting
//                default:
//                    if (acc.length() != 12) {
//                        return acc;
//                    }
//                    else {
//                        /** ddd-ddd-ddddd-d **/
//                        return acc.substring(0, 3) + SEPARATOR
//                                + acc.substring(3, 6) + SEPARATOR
//                                + acc.substring(6, 11) + SEPARATOR
//                                + acc.substring(11, 12);
//                    }
//
//            }
//        }
//    }
}
