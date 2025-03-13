package com.example.furbnb1;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class dateTime extends AppCompatActivity {

    Button pickDateButton;
    Button pickTimeButton;
    Calendar calendar;
    int selectedYear, selectedMonth, selectedDay;
    int selectedHour, selectedMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datatime);

        pickDateButton = findViewById(R.id.pickDateButton);
        pickTimeButton = findViewById(R.id.pickTimeButton);

        calendar = Calendar.getInstance();

        pickDateButton.setOnClickListener(v -> showDatePicker());
        pickTimeButton.setOnClickListener(v -> showTimePicker());
    }

    private void showDatePicker() {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, yearPicked, monthOfYear, dayOfMonth) -> {
                    Calendar tempCalendar = Calendar.getInstance(); // Temporary calendar
                    tempCalendar.set(Calendar.YEAR, yearPicked);
                    tempCalendar.set(Calendar.MONTH, monthOfYear);
                    tempCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    Calendar today = Calendar.getInstance(); // Get today's date
                    if (tempCalendar.before(today) || tempCalendar.equals(today)) { //Disallow current date
                        new AlertDialog.Builder(this)
                                .setTitle("Invalid Date")
                                .setMessage("Cannot select today or past dates.")
                                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                                .setCancelable(false)
                                .show();
                        return; // Don't update if date is invalid
                    }

                    selectedYear = yearPicked;
                    selectedMonth = monthOfYear;
                    selectedDay = dayOfMonth;
                    calendar.set(Calendar.YEAR, selectedYear);
                    calendar.set(Calendar.MONTH, selectedMonth);
                    calendar.set(Calendar.DAY_OF_MONTH, selectedDay);

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    String formattedDate = dateFormat.format(calendar.getTime());
                    pickDateButton.setText(formattedDate);
                }, year, month, day);

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000); //Keeps the past dates unclickable
        datePickerDialog.show();
    }

    private void showTimePicker() {
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (view, hourOfDay, minutePicked) -> {
                    Calendar tempCalendar = Calendar.getInstance(); // Temporary calendar
                    tempCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    tempCalendar.set(Calendar.MINUTE, minutePicked);

                    if (hourOfDay < 9 || hourOfDay >= 17) {
                        new AlertDialog.Builder(this)
                                .setTitle("Invalid Time")
                                .setMessage("The time you chose is beyond our operating hours. Please choose from 9 AM to 5 PM only.")
                                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                                .setCancelable(false)
                                .show();
                        return; // Don't update if time is invalid
                    }
                    selectedHour = hourOfDay;
                    selectedMinute = minutePicked;
                    calendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                    calendar.set(Calendar.MINUTE, selectedMinute);
                    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
                    String formattedTime = timeFormat.format(calendar.getTime());
                    pickTimeButton.setText(formattedTime);
                }, hour, minute, false);

        timePickerDialog.show();
    }
}