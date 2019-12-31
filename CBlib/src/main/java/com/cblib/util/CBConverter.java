package com.cblib.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public final class CBConverter {

   private CBConverter() {
      // Utility classes should not have a public or default constructor.
      // best practice
   }

   public static String formatAmountToCurrency(String value) {
      if (value == null || value.isEmpty())
         return value;
      return doubleToString(stringToDouble(value));
   }

   public static Double stringToDouble(String value) {
      if (value == null || value.isEmpty())
         return 0d;
      return stringToDouble(Locale.ENGLISH, value);
   }

   public static String doubleToString(double value) {
      return doubleToString(Locale.ENGLISH, value, "#,##0.00");
   }

   public static String doubleToString(double value, String applyPattern) {
      return doubleToString(Locale.ENGLISH, value, applyPattern);
   }

   public static String formatText(String value, String applyPattern) {
      NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.ENGLISH);
      DecimalFormat decimalFormat = (DecimalFormat) numberFormat;
      decimalFormat.applyPattern(applyPattern);
      return numberFormat.format(stringToDouble(value));
   }

   public static String doubleToString(Locale currentLocale, double value, String applyPattern) {
      NumberFormat numberFormat = NumberFormat.getNumberInstance(currentLocale);
      DecimalFormat decimalFormat = (DecimalFormat) numberFormat;
      decimalFormat.applyPattern(applyPattern);
      return numberFormat.format(value);
   }

   private static Double stringToDouble(Locale currentLocale, String value) {
      NumberFormat numberFormat = NumberFormat.getNumberInstance(currentLocale);
      try {
         return numberFormat.parse(value).doubleValue();
      } catch (ParseException e) {
         e.printStackTrace();
      }
      return 0d;
   }
}
