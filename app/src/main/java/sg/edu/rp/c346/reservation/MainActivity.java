package sg.edu.rp.c346.reservation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.time.Year;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etName;
    EditText etTelephone;
    EditText etSize;
    EditText etTime;
    EditText etDay;
    TextView tvTime;
    TextView tvDay;
    CheckBox checkBox;
    DatePicker datePicker;
    TimePicker timePicker;
    Button btReserve;
    Button btReset;


    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit =prefs.edit();
        edit.putString("name",etName.getText().toString());
        edit.putString("Telephone",etTelephone.getText().toString());
        edit.putString("size",etSize.getText().toString());
        edit.putString("smoking",checkBox.getText().toString());
        edit.putString("day",etDay.getText().toString());
        edit.putString("time",etTime.getText().toString());
        edit.commit();

    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        etName .setText(prefs.getString("name",""));
        etTelephone .setText(prefs.getString("Telephone",""));
        etSize .setText(prefs.getString("size",""));
        checkBox .setText(prefs.getString("smoking",""));
        etDay .setText(prefs.getString("day",""));
        etTime .setText(prefs.getString("time",""));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.editTextName);
        etTelephone = findViewById(R.id.editTextTelephone);
        etSize = findViewById(R.id.editTextSize);
        checkBox = findViewById(R.id.checkBox);
        tvTime=findViewById(R.id.textView2Time);
        tvDay=findViewById(R.id.textViewDay);
        etDay=findViewById(R.id.editTextDay);
        etTime=findViewById(R.id.editTextTime);

        btReserve = findViewById(R.id.buttonReserve);
        btReset = findViewById(R.id.buttonReset);






        etTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog.OnTimeSetListener myTimeListener= new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourofDay, int minute) {

                        etTime.setText( hourofDay + ":" + minute);
                    }


                };
                Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time

                int hour = now.get(Calendar.HOUR);
                int min= now.get(Calendar.MINUTE);
                int mind =now.get(Calendar.HOUR_OF_DAY);



                TimePickerDialog myTimeDialog = new TimePickerDialog(MainActivity.this,myTimeListener,hour,min,true);
                myTimeDialog.show();
            }
        });



        etDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener myDateListener= new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        etDay.setText( dayOfMonth + "/" + (monthOfYear+1)+ "/" +year);
                    }


                };
                Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time

                int Day = now.get(Calendar.DAY_OF_MONTH);
                int year= now.get(Calendar.YEAR);
                int Month= now.get(Calendar.MONTH);

                DatePickerDialog myDateDialog = new DatePickerDialog(MainActivity.this,myDateListener ,year,Month,Day);
                myDateDialog.show();
            }
        });



        btReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String isSmoke = "";
                if (checkBox.isChecked()) {
                    isSmoke = "smoking";
                } else {
                    isSmoke = "non-smoking";
                }
                String mess = "";

                mess = "New Reservation\n";
                mess += "Name:" + etName.getText().toString() + "\n";
                mess += "Smoking :" + isSmoke + "\n";
                mess += "Size:" + etSize.getText().toString() + "\n";
                mess += "Date: " + etDay.getText().toString() + "\n";
                mess += "Time: " + etTime.getText().toString() + "\n";

                AlertDialog.Builder myBuilder = new AlertDialog.Builder(MainActivity.this);
                myBuilder.setTitle("Confirm your order");
                myBuilder.setMessage(mess);
                myBuilder.setCancelable(false);


                myBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "hi", Toast.LENGTH_LONG).show();
                    }
                }).setNeutralButton("Cancel", null);

                AlertDialog myDialog = myBuilder.create();
                myDialog.show();
/*
                Toast.makeText(MainActivity.this,
                        "Hi, " + etName.getText().toString() + ", you have booked a "
                                + etSize.getText().toString() + " person(s) "
                                + isSmoke + " table on "
                                + datePicker.getYear() + "/" + (datePicker.getMonth() + 1) + "/" + datePicker.getDayOfMonth() + " at "
                                + timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute() + ". Your phone number is "
                                + etTelephone.getText().toString() + ".",
                        Toast.LENGTH_LONG).show();*/

            }
        });

        btReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
                String date = now.get(Calendar.DAY_OF_MONTH) + "/" +
                        (now.get(Calendar.MONTH)+1) + "/" +
                        now.get(Calendar.YEAR) ;
                String time = now.get(Calendar.HOUR_OF_DAY) + ":" +
                        now.get(Calendar.MINUTE);


                etName.setText("");
                etTelephone.setText("");
                etSize.setText("");
                checkBox.setChecked(false);
                etDay.setText(date);
                etTime.setText(time);
            }

        });
    }
}