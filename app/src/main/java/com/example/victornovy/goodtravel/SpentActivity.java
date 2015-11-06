package com.example.victornovy.goodtravel;

import android.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.view.View;
import android.app.Dialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.content.Intent;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Victor Novy on 28/09/2015.
 */
public class SpentActivity extends AppCompatActivity {

    private int day, year, month;
    private Button dateSpent;


    /*Para setar valor no spinner via array usamos entries e via codigo usar o prompt */
    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spent);

        Calendar calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        dateSpent = (Button) findViewById(R.id.btnSelectDateSpent);
        dateSpent.setText(day + "/" + (month+1) + "/" + year);

        /*ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.spending_category, android.R.layout.simple_spinner_item
        );
        Spinner category = (Spinner) findViewById(R.id.spnCategory);
        category.setAdapter(adapter);*/
    }

    public void selectDate(View view)
    {
        /* Metodo utilizado pela apostila, mas como este metodo esta para ser depreciado foi feito
         * de uma outra maneira
         * showDialog(view.getId());
         */

        if (R.id.btnSelectDateSpent == view.getId())
            showDatePicker(dateSpent);
    }

    public void showDatePicker(final Button date)
    {
        DialogFragment df = new DialogFragment() {

            @Override
            public Dialog onCreateDialog (Bundle savedInstanceState)
            {
                SpentActivity content = (SpentActivity) getActivity();
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

   /*
    * Metodo utilizado pela apostila, mas como este metodo esta para ser depreciado foi utilizada
    * de uma outra maneira
    @Override
    protected Dialog onCreateDialog(int id)
    {
        if (R.id.btnSelectDateSpent == id) {
            return new DatePickerDialog(this, listener, year, month, day);
        }
        return null;
    }

    private OnDateSetListener listener = new OnDateSetListener() {

        @Override
        public void onDateSet (DatePicker view, int yearSelect, int monthOfYear, int dayOfMonth)
        {
            day = dayOfMonth;
            month = monthOfYear;
            year = yearSelect;
            dateSpent.setText(day + "/" + (month+1) + "/" + year);
        }
    };*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_spent, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.mnuNewSpent:
                startActivity(new Intent(this, SpentActivity.class));
                return true;
            case R.id.mnuDeleteSpent:
                //TODO apaga a viagem do banco
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}