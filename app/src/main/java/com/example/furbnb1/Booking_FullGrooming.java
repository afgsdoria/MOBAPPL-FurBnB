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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Booking_FullGrooming extends ComponentActivity {
    private int dogNum = 1;
    private final int min_dogs = 1;
    private final int max_dogs = 2;

    private Calendar calendar;
    private int selectedYear, selectedMonth, selectedDay, selectedHour, selectedMinute;
    private Button btnSelectDate, btnSelectTime, btnConfirm;
    private TextView errorMessage, dogNumText;
    private ImageView addButton, grayLessButton;
    private EditText dogName, dogBreed;
    private RadioGroup dogSize1, dogSize2;
    private TextView dogNameLabel, dogBreedLabel, dogSizeLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.booking_full_grooming);

        //return button na ayaw magpakita rn
        ImageView returnButton = findViewById(R.id.return_bttn);
        returnButton.setOnClickListener(v -> {
            Intent intent = new Intent(Booking_FullGrooming.this, Booking.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_right, R.anim.slide_right);
        });

        //getting the UI
        addButton = findViewById(R.id.addButton);
        grayLessButton = findViewById(R.id.grayLessButton);
        dogNumText = findViewById(R.id.dogNum1);
        btnSelectDate = findViewById(R.id.selectDate);
        btnSelectTime = findViewById(R.id.selectTime);
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
        btnSelectTime.setOnClickListener(v -> showTimePicker());

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

    //calculates the total price
    private int calculatePrice() {
        //declaration of prices
        int priceXS = 400;
        int priceS = 500;
        int priceM = 700;
        int priceL = 900;
        int totalPrice = 0; // initally set to 0

        //gets selected size
        String size1 = getSelectedDogSize(dogSize1);
        String size2 = dogNum == 2 ? getSelectedDogSize(dogSize2) : "";

        Log.d("Booking_FullGrooming", "Selected Size 1: " + size1);
        Log.d("Booking_FullGrooming", "Selected Size 2: " + size2);

        //kung ano yung pinili, maa-add sa toatl price
        if (size1.equals("XS")) {
            totalPrice += priceXS;
        } else if (size1.equals("S")) {
            totalPrice += priceS;
        } else if (size1.equals("M")) {
            totalPrice += priceM;
        } else if (size1.equals("L")) {
            totalPrice += priceL;
        }

        //same sa taas except maco-compute lang if 2 dogs pinli
        if (!size2.isEmpty() && !size2.equals("N/A")) {
            if (size2.equals("XS")) {
                totalPrice += priceXS;
            } else if (size2.equals("S")) {
                totalPrice += priceS;
            } else if (size2.equals("M")) {
                totalPrice += priceM;
            } else if (size2.equals("L")) {
                totalPrice += priceL;
            }
        }

        //log and return price
        Log.d("Booking_FullGrooming", "Calculated Total Price: " + totalPrice);
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

    //time picker stuff
    //time picker stuff
    private void showTimePicker() {
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
                    selectedHour = hourOfDay;
                    selectedMinute = minutePicked;
                    calendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                    calendar.set(Calendar.MINUTE, selectedMinute);

                    // format time so it is not in 24 hour format
                    SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault()); // hh is for 12-hour format and aa is for the AM/PM
                    String formattedTime = timeFormat.format(calendar.getTime());
                    btnSelectTime.setText(formattedTime);
                }, hour, minute, false);

        timePickerDialog.show();
    }

    //kukunin lahat ng pwedeng makuha from this page and ipapakita sa summary page

    private void openConfirmAppointmentActivity() {
        int totalPrice = calculatePrice();

        //for the price
        Log.d("Booking_FullGrooming", "Sending Total Price: " + totalPrice);

        Intent intent = new Intent(Booking_FullGrooming.this, Booking_FullGrooming_Summary.class);
        intent.putExtra("dogNum", dogNum);
        intent.putExtra("dogName", dogName.getText().toString());
        intent.putExtra("dogBreed", dogBreed.getText().toString());
        intent.putExtra("dogSize1", getSelectedDogSize(dogSize1));
        intent.putExtra("dogSize2", dogNum == 2 ? getSelectedDogSize(dogSize2) : "N/A");
        intent.putExtra("date", btnSelectDate.getText().toString());
        intent.putExtra("time", btnSelectTime.getText().toString());
        intent.putExtra("totalPrice", totalPrice);

        startActivity(intent);
    }
}
