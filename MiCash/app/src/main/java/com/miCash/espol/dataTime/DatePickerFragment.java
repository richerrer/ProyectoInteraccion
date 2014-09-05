package com.miCash.espol.dataTime;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.Toast;

import com.miCash.espol.activity.MenuPrincipal;

import java.util.Calendar;

/**
 * Created by usuario on 03/09/2014.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private Context context;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month,day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        showSetDate(year,month,day);
    }

    public void showSetDate(int year,int month,int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month,day);
        ((MenuPrincipal)context).searchTransactions(calendar);
    }
}
