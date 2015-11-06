package com.example.victornovy.goodtravel;

import android.app.Dialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.content.Intent;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Victor Novy on 28/09/2015.
 */
public class NewtravelActivity extends AppCompatActivity {

    private int day, month, year;
    private Button dateGoOut, dateGoBack;
    private DatabaseHelper helper;
    private EditText destiny, manyPeople, budget;
    private RadioGroup travelType;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newtravel);

        Calendar calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        dateGoOut = (Button) findViewById(R.id.btnSelectDateOut);
        dateGoOut.setText(day + "/" + (month+1) + "/" + year);
        dateGoBack = (Button) findViewById(R.id.btnSelectDateBack);
        dateGoBack.setText(day + "/" + (month+1) + "/" + year);

        destiny = (EditText) findViewById(R.id.txtDestiny);
        budget = (EditText) findViewById(R.id.txtBudget);
        manyPeople = (EditText) findViewById(R.id.txtHowManyPeople);

        travelType = (RadioGroup) findViewById(R.id.rdgTravelType);

        helper = new DatabaseHelper(this);
    }

    @Override
    public void onDestroy()
    {
        helper.close();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_travel, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.mnuNewTravel:
                startActivity(new Intent(this, NewtravelActivity.class));
                return true;
            case R.id.mnuDeleteTravel:
                //TODO do the event to remove the travel
                Toast.makeText(this, "Travel removed", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void selectDate(View view)
    {
        switch (view.getId()) {
            case R.id.btnSelectDateOut:
                Toast.makeText(this, "a",Toast.LENGTH_SHORT).show();
                showDatePicker(dateGoOut);
                break;
            case R.id.btnSelectDateBack:
                Toast.makeText(this, "b",Toast.LENGTH_SHORT).show();
                showDatePicker(dateGoBack);
                break;
        }
    }

    /* TODO 04/11/2015 refazer este metodo para que varias classes o usem sem precisarem instanciar o objeto
     * TODO 04/11/2015 verificar o porque do final
     */
    public void showDatePicker(final Button date)
    {
        DialogFragment df = new DialogFragment() {

            @Override
            public Dialog onCreateDialog (Bundle savedInstanceState)
            {
                NewtravelActivity content = (NewtravelActivity) getActivity();
                DatePickerDialog datePicker = new DatePickerDialog(content, listener, year, month, day);
                // TODO: 02/10/2015 Change the title and create the layout.
                datePicker.setTitle("Date");
                // datePicker.setView(R.layout.layoutDatePickerDialog);
                return datePicker;
            }

            private OnDateSetListener listener = new OnDateSetListener()
            {
                @Override
                public void onDateSet (DatePicker veiw, int yearSelect, int monthOfYear, int dayOfMonth)
                {
                    day = dayOfMonth;
                    month = monthOfYear;
                    year = yearSelect;
                    date.setText(day + "/" + (month+1) + "/" + year);
                }
            };
        };
        df.show(getFragmentManager(), "datePicker");
    }

    public void saveTravel(View view)
    {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("DESTINY", destiny.getText().toString());
        //TODO date no banco de dados salvar como long, então procurar algum mode de converter isso. page 135
        values.put("DTGOOUT", dateGoOut.getText().toString());
        values.put("DTGOBACK", dateGoBack.getText().toString());
        values.put("BUDGET", budget.getText().toString());
        values.put("MANYPEOPLE", manyPeople.getText().toString());

        int type = travelType.getCheckedRadioButtonId();

        if (type == R.id.rdbRelax)
            values.put("IDTRAVELTYPE", Constants.RELEX_TRAVEL);
        else
            values.put("IDTRAVELTYPE", Constants.WORK_TRAVEL);

        long result = db.insert("TRAVEL", null, values);

        if (result != -1)
            Toast.makeText(this, getString(R.string.saved_data), Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, getString(R.string.error_saving), Toast.LENGTH_LONG).show();

        /*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());*/
    }

    /*public static String formatDateTime(Context context, String timeToFormat) {

        String finalDateTime = "";

        SimpleDateFormat iso8601Format = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");

        Date date = null;
        if (timeToFormat != null) {
            try {
                date = iso8601Format.parse(timeToFormat);
            } catch (ParseException e) {
                date = null;
            }

            if (date != null) {
                long when = date.getTime();
                int flags = 0;
                flags |= android.text.format.DateUtils.FORMAT_SHOW_TIME;
                flags |= android.text.format.DateUtils.FORMAT_SHOW_DATE;
                flags |= android.text.format.DateUtils.FORMAT_ABBREV_MONTH;
                flags |= android.text.format.DateUtils.FORMAT_SHOW_YEAR;

                finalDateTime = android.text.format.DateUtils.formatDateTime(context,
                        when + TimeZone.getDefault().getOffset(when), flags);
            }
        }
        return finalDateTime;
    }*/
}
