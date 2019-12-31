package com.cblib.util;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.Toast;

import com.cblib.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public abstract class CBCalendarDialog {
    public static final String START_TIME = "00:00:00";
    public static final String END_TIME = "23:59:59";

    public abstract void onDateSet(Date date, String string, long timeInMillis);

    private DatePickerDialog datePickerDialog;

    public CBCalendarDialog(Context context) {
        final SimpleDateFormat dateFormatter = new SimpleDateFormat(CBDateTime.DATE_FORMATTER_TYPE_1, Locale.US);
        Calendar newCalendar = Calendar.getInstance();
        new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                if (view.isShown()) {
                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, monthOfYear, dayOfMonth);
                    CBCalendarDialog.this.onDateSet(newDate.getTime(), dateFormatter.format(newDate.getTime()), newDate.getTimeInMillis());
                }
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public CBCalendarDialog(final Context context, final Date maxDate, Date minDate) {
        final SimpleDateFormat dateFormatter = new SimpleDateFormat(CBDateTime.DATE_FORMATTER_TYPE_1, Locale.US);
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                if (view.isShown()) {

                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, monthOfYear, dayOfMonth);

                    Calendar newDateMax = Calendar.getInstance();
                    newDateMax.set(year, monthOfYear, dayOfMonth);
                    newDateMax.set(Calendar.DAY_OF_YEAR, newDate.get(Calendar.DAY_OF_YEAR) - 1);

                    /** to handler certain phone */
                    if (view.getMinDate() > newDate.getTimeInMillis() || (maxDate != null) && maxDate.getTime() < newDateMax.getTimeInMillis()) {
                        Toast.makeText(context, context.getString(R.string.cb_dialog_messageInvalidDate), Toast.LENGTH_LONG).show();
                        if (datePickerDialog != null)
                            datePickerDialog.show();
                    } else {
                        CBCalendarDialog.this.onDateSet(newDate.getTime(), dateFormatter.format(newDate.getTime()), newDate.getTimeInMillis());
                    }
                }
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setCalendarViewShown(false);

        if (null != minDate) {
            Calendar calMin = Calendar.getInstance();
            calMin.setTime(minDate);
            calMin.set(Calendar.HOUR_OF_DAY, 0);
            calMin.set(Calendar.MINUTE, 0);
            calMin.set(Calendar.SECOND, 0);
            calMin.set(Calendar.MILLISECOND, 0);
            datePickerDialog.getDatePicker().setMinDate(calMin.getTimeInMillis());
        }
        if (null != maxDate) {
            datePickerDialog.getDatePicker().setMaxDate(maxDate.getTime());
        }

        datePickerDialog.show();
    }

    public static Date addStartTime(Date date) {
        return addTime(date, CBCalendarDialog.START_TIME);
    }

    public static Date addEndTime(Date date) {
        return addTime(date, CBCalendarDialog.END_TIME);
    }


    private static Date addTime(Date date, String time) {
        String dateString = CBDateTime.Formatter.toFormat(date, CBDateTime.DATE_FORMATTER_TYPE_1);
        StringBuilder stringBuilder = new StringBuilder();
        String dateStringNew = stringBuilder.append(dateString).append(" ").append(time).toString();
        return CBDateTime.Formatter.toDate(dateStringNew, CBDateTime.DATE_FORMATTER_TYPE_16);
    }

}
