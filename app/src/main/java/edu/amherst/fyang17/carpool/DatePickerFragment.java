package edu.amherst.fyang17.carpool;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by Caller on 4/12/2015.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    public static String date;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
    // Use the current date as the default date in the picker
    final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
        }

public void onDateSet(DatePicker view, int year, int month, int day) {
    StringBuilder sb = new StringBuilder();
    sb.append(month+1);
    sb.append('/');
    sb.append(day);
    sb.append('/');
    sb.append(year);
    date = sb.toString();
        }
}