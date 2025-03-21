package com.example.furbnb1;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.ComponentActivity;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Booking_DayBoarding extends ComponentActivity {
    private int dogNum = 1;
    private final int min_dogs = 1;
    private final int max_dogs = 2;
    private int selectedCheckInHour = -1;
    private int selectedCheckOutHour = -1;

    Calendar calendar;
    private int selectedYear, selectedMonth, selectedDay, selectedCheckInMinute, selectedCheckOutMinute;
    private Button btnSelectDate, btnSelectCheckInTime, btnSelectCheckOutTime, btnConfirm;
    private TextView errorMessage, dogNumText;
    private ImageView return_bttn, addButton, grayLessButton;
    private EditText dogName, dogBreed;
    private RadioGroup dogSize1, dogSize2;
    private TextView dogNameLabel, dogBreedLabel, dogSizeLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.booking_day_boarding);

        //return button
        ImageView returnButton = findViewById(R.id.return_bttn);
        returnButton.setOnClickListener(v -> {
            Intent intent = new Intent(Booking_DayBoarding.this, Booking.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_right, R.anim.slide_right);
        });

        //getting the UI
        addButton = findViewById(R.id.addButton);
        grayLessButton = findViewById(R.id.grayLessButton);
        dogNumText = findViewById(R.id.dogNum1);
        btnSelectDate = findViewById(R.id.selectDate);
        btnSelectCheckInTime = findViewById(R.id.selectCheckInTime);
        btnSelectCheckOutTime = findViewById(R.id.selectCheckoutTime);
        btnConfirm = findViewById(R.id.send_btn);
        errorMessage = findViewById(R.id.errorMessage);
        dogName = findViewById(R.id.dogName);
        dogBreed = findViewById(R.id.breed);
        dogSize1 = findViewById(R.id.dogSize);
        dogSize2 = findViewById(R.id.dogSize2);
        dogNameLabel = findViewById(R.id.dogNameText);
        dogBreedLabel = findViewById(R.id.breedText);
        dogSizeLabel = findViewById(R.id.sizeText);

        //hiding the second size option initially
        dogSize2.setVisibility(View.GONE);
        dogNumText.setText(String.valueOf(dogNum));
        grayLessButton.setImageResource(R.drawable.icon_less_gray);
        updateLabels();

        //add button on number of dogs
        addButton.setOnClickListener(v -> {
            if (dogNum < max_dogs) {
                dogNum++;
                dogNumText.setText(String.valueOf(dogNum));
                updateLabels();
                if (dogNum == 2) dogSize2.setVisibility(View.VISIBLE); // makes number 2 visible instead of 1
                if (dogNum == max_dogs) addButton.setImageResource(R.drawable.icon_add_gray); // if max num (2) is reached, add becomes gray
                grayLessButton.setImageResource(R.drawable.icon_less);
            }
        });

        //lessen button
        grayLessButton.setOnClickListener(v -> {
            if (dogNum > min_dogs) {
                dogNum--;
                dogNumText.setText(String.valueOf(dogNum));
                updateLabels();
                if (dogNum == 1) dogSize2.setVisibility(View.GONE); // if minus button is clicked, it hides the number 2 and shows initial number 1
                if (dogNum == min_dogs) grayLessButton.setImageResource(R.drawable.icon_less_gray); // 1 dog makes the less button gray to indicate min number of dogs
                addButton.setImageResource(R.drawable.icon_add);
            }
        });

        //button instances
        calendar = Calendar.getInstance();
        btnSelectDate.setOnClickListener(v -> showDatePicker());
        btnSelectCheckInTime.setOnClickListener(v -> showCheckInTimePicker());
        btnSelectCheckOutTime.setOnClickListener(v -> showCheckOutTimePicker());

        btnConfirm.setOnClickListener(v -> openConfirmAppointmentActivity());
    }

    //gets the chosen size of dog from the radio grp
    private String getSelectedDogSize(RadioGroup radioGroup) {
        int selectedSizeId = radioGroup.getCheckedRadioButtonId();
        if (selectedSizeId != -1) { //check if a radio btn is selected
            RadioButton selectedButton = findViewById(selectedSizeId);
            return selectedButton.getText().toString(); //gets the chosen size
        }
        return "Not selected";
    }

    //calculates the total price based on time
    private int calculatePrice() {
        if (selectedYear == 0 || selectedCheckInHour == -1 || selectedCheckOutHour == -1) {
            return 0;
        }

        Calendar checkInCalendar = Calendar.getInstance();
        checkInCalendar.set(selectedYear, selectedMonth, selectedDay, selectedCheckInHour, selectedCheckInMinute);

        Calendar checkOutCalendar = Calendar.getInstance();
        checkOutCalendar.set(selectedYear, selectedMonth, selectedDay, selectedCheckOutHour, selectedCheckOutMinute);

        long diff = checkOutCalendar.getTimeInMillis() - checkInCalendar.getTimeInMillis();
        long hours = diff / (60 * 60 * 1000);

        int totalPrice = 0;
        if (hours > 0) {
            totalPrice = 100;
            if (hours > 1) {
                totalPrice += (hours - 1) * 50;
            }
        }
        totalPrice *= dogNum;
        return totalPrice;
    }

    //changes from singular to plural if 2 dogs is chosen
    private void updateLabels() {
        dogNameLabel.setText(dogNum == 2 ? "Dog Names" : "Dog Name");
        dogBreedLabel.setText(dogNum == 2 ? "Dog Breeds" : "Dog Breed");
        dogSizeLabel.setText(dogNum == 2 ? "Dog Sizes" : "Dog Size");
    }

    //date picker stuff
    private void showDatePicker() {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, yearPicked, monthOfYear, dayOfMonth) -> {
                    Calendar tempCalendar = Calendar.getInstance();
                    tempCalendar.set(Calendar.YEAR, yearPicked);
                    tempCalendar.set(Calendar.MONTH, monthOfYear);
                    tempCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    Calendar today = Calendar.getInstance(); // kinukuha present date
                    if (tempCalendar.before(today) || tempCalendar.equals(today)) { // bawal present date
                        new AlertDialog.Builder(this)
                                .setMessage("We don't cater to same-day booking. Please choose a different date.")
                                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                                .setCancelable(false)
                                .show();
                        return;
                    }

                    selectedYear = yearPicked;
                    selectedMonth = monthOfYear;
                    selectedDay = dayOfMonth;
                    calendar.set(Calendar.YEAR, selectedYear);
                    calendar.set(Calendar.MONTH, selectedMonth);
                    calendar.set(Calendar.DAY_OF_MONTH, selectedDay);

                    SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault());
                    String formattedDate = dateFormat.format(calendar.getTime());
                    btnSelectDate.setText(formattedDate);
                }, year, month, day);

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    //check in time picker
    private void showCheckInTimePicker() {
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (view, hourOfDay, minutePicked) -> {
                    Calendar tempCalendar = Calendar.getInstance();
                    tempCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    tempCalendar.set(Calendar.MINUTE, minutePicked);

                    if (hourOfDay < 9 || hourOfDay >= 17) {
                        new AlertDialog.Builder(this)
                                .setMessage("The time you chose is beyond our operating hours. Please choose from 9 AM to 5 PM only.")
                                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                                .setCancelable(false)
                                .show();
                        return;
                    }
                    selectedCheckInHour = hourOfDay;
                    selectedCheckInMinute = minutePicked;

                    // format time so it is not in 24 hour format
                    SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault()); // hh is for 12-hour format and aa is for the AM/PM
                    String formattedTime = timeFormat.format(tempCalendar.getTime());
                    btnSelectCheckInTime.setText(formattedTime);
                }, hour, minute, false);

        timePickerDialog.show();
    }

    //check out time picker
    private void showCheckOutTimePicker() {
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (view, hourOfDay, minutePicked) -> {
                    Calendar tempCalendar = Calendar.getInstance();
                    tempCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    tempCalendar.set(Calendar.MINUTE, minutePicked);

                    if (hourOfDay < 9 || hourOfDay >= 17) {
                        // ... (operating hours error)
                        return;
                    }

                    if (selectedCheckInHour != -1) {
                        if (hourOfDay < selectedCheckInHour + 1) {
                            new AlertDialog.Builder(this)
                                    .setMessage("Check-out time must be at least one hour after check-in time.")
                                    .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                                    .setCancelable(false)
                                    .show();
                            return;
                        } else if (hourOfDay == selectedCheckInHour + 1 && minutePicked < selectedCheckInMinute){
                            new AlertDialog.Builder(this)
                                    .setMessage("Check-out time must be at least one hour after check-in time.")
                                    .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                                    .setCancelable(false)
                                    .show();
                            return;
                        }
                    }

                    selectedCheckOutHour = hourOfDay;
                    selectedCheckOutMinute = minutePicked;

                    // format time so it is not in 24 hour format
                    SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
                    String formattedTime = timeFormat.format(tempCalendar.getTime());
                    btnSelectCheckOutTime.setText(formattedTime);
                }, hour, minute, false);

        timePickerDialog.show();
    }

    //kukunin lahat ng pwedeng makuha from this page and ipapakita sa summary page
    private void openConfirmAppointmentActivity() {
        if (selectedYear == 0) {
            new AlertDialog.Builder(this)
                    .setMessage("Please select a date.")
                    .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                    .setCancelable(false)
                    .show();
            return;
        }

        if (selectedCheckInHour == -1) {
            new AlertDialog.Builder(this)
                    .setMessage("Please select a check-in time.")
                    .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                    .setCancelable(false)
                    .show();
            return;
        }

        if (selectedCheckOutHour == -1) {
            new AlertDialog.Builder(this)
                    .setMessage("Please select a check-out time.")
                    .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                    .setCancelable(false)
                    .show();
            return;
        }

        if (selectedCheckInHour != -1 && selectedCheckOutHour < selectedCheckInHour + 1) {
            if (selectedCheckOutHour == selectedCheckInHour + 1 && selectedCheckOutMinute < selectedCheckInMinute){
                new AlertDialog.Builder(this)
                        .setMessage("Check-out time must be at least one hour after check-in time.")
                        .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                        .setCancelable(false)
                        .show();
                return;
            } else if (selectedCheckOutHour < selectedCheckInHour + 1){
                new AlertDialog.Builder(this)
                        .setMessage("Check-out time must be at least one hour after check-in time.")
                        .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                        .setCancelable(false)
                        .show();
                return;
            }
        }

        if (selectedCheckInHour == selectedCheckOutHour && selectedCheckInMinute == selectedCheckOutMinute){
            new AlertDialog.Builder(this)
                    .setMessage("Check-in and Check-out time cannot be the same.")
                    .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                    .setCancelable(false)
                    .show();
            return;
        }

        // All fields are filled, proceed with calculation and intent
        int totalPrice = calculatePrice();

        Log.d("DayBoarding", "Sending Total Price: " + totalPrice);

        Intent intent = new Intent(Booking_DayBoarding.this, Booking_DayBoarding_Summary.class);
        intent.putExtra("dogNum", dogNum);
        intent.putExtra("dogName", dogName.getText().toString());
        intent.putExtra("dogBreed", dogBreed.getText().toString());
        intent.putExtra("dogSize1", getSelectedDogSize(dogSize1));
        intent.putExtra("dogSize2", dogNum == 2 ? getSelectedDogSize(dogSize2) : "N/A");
        intent.putExtra("date", btnSelectDate.getText().toString());
        intent.putExtra("checkInTime", btnSelectCheckInTime.getText().toString());
        intent.putExtra("checkOutTime", btnSelectCheckOutTime.getText().toString());
        intent.putExtra("totalPrice", totalPrice);

        startActivity(intent);
    }
}
