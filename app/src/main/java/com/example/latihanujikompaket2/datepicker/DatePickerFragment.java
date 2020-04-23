package com.example.latihanujikompaket2.datepicker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    DialogDateListener mListener;

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        mListener.onDialogDateSet(getTag(), i, i1, i2);
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context != null) {
            mListener = (DialogDateListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mListener != null) {
            mListener = null;
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int date = calendar.get(Calendar.DATE);

        return new DatePickerDialog(getActivity(), this, year, month, date);
    }

    public interface DialogDateListener {
        void onDialogDateSet(String tag, int year, int month, int dayOfMonth);
    }
}
