package com.example.victornovy.goodtravel;

import android.app.ListActivity;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.TextView;
import android.widget.ImageView;
import android.content.Intent;

import java.util.List;
import java.util.Arrays;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AdapterView.AdapterContextMenuInfo;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.app.AlertDialog;

import android.widget.SimpleAdapter.ViewBinder;

/**
 * Created by Victor Novy on 04/10/2015.
 */
public class TravelListActivity extends ListActivity implements OnItemClickListener, OnClickListener {

    private List<Map<String, Object>> travels;
    private AlertDialog alertDialog;
    private int travelSeleted;
    private AlertDialog dialogConfirmation;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        /* Quando quisermos utilizar uma lista fixa de valore e layout padrão utilizamos o comando abaixo
         * O ArrayAdapter não suporta layouts customizados
        setListAdapter (
            new ArrayAdapter<String> (
                   this, android.R.layout.simple_list_item_1, travelList()
            )
        );

        ListView listView = getListView();
        listView.setOnItemClickListener(this);*/

        String[] by = {"imgType", "destiny", "date", "value", "progress"};
        int[] to = {R.id.imgTravelType, R.id.lblTravelListDestiny, R.id.lblTravelListDate, R.id.lblTravelListValue, R.id.pgbBudget};

        SimpleAdapter adapter = new SimpleAdapter(this, travelList(), R.layout.travel_list, by, to);
        adapter.setViewBinder(setViewBinder());
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);

        //registramos o menu de contexto
        registerForContextMenu(getListView());

        this.alertDialog = createAlertDialog();
        this.dialogConfirmation = createDialogConfirmation();
    }

    /* Quando quisermos utilizar uma lista fixa de valore utilizamos o comando abaixo
    private List<String> travelList()
    {
        return Arrays.asList("São Paulo", "Belo Horizonte", "Londres");
    }*/

    private List<Map<String, Object>> travelList()
    {
        travels = new ArrayList<Map<String, Object>>();

        Map<String, Object> item = new HashMap<String, Object>();
        item.put("imgType", R.drawable.negocios);
        item.put("destiny", "São Paulo");
        item.put("date", "01/01/2015 à 31/01/2015");
        item.put("value", "Total Gasto R$25000");
        item.put("progress", new Double[]{500.0,450.0,329.0});
        travels.add(item);

        item = new HashMap<String, Object>();
        item.put("imgType", R.drawable.lazer);
        item.put("destiny", "Belo Horizonte");
        item.put("date", "01/04/2015 à 15/04/2015");
        item.put("value", "Total Gasto R$4000");
        item.put("progress", new Double[]{632.0,270.0,196.0});
        travels.add(item);

        return travels;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
//        TextView textView = (TextView) view; Fixo
//        textView.getText().toString()
        Map<String, Object> map = travels.get(position);
        String destiny = (String) map.get("destiny");
        Toast.makeText(getApplicationContext(), "Selected trip: " + destiny, Toast.LENGTH_LONG).show();

        this.travelSeleted = position;
        alertDialog.show();

        /*startActivity(new Intent(this, SpentListActivity.class));*/
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo menuInfo)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_travel_list, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        if (item.getItemId() == R.id.mnuDeleteTravelList) {
            Toast.makeText(this, "Deleted Test", Toast.LENGTH_SHORT).show();
            /*AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
            travels.remove(info.position);
            getListView().invalidateViews();*/
            //TODO remove database
            return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onClick(DialogInterface dialog, int item)
    {
        switch (item) {
            case 0:
                startActivity(new Intent(this, NewtravelActivity.class));
                break;
            case 1:
                startActivity(new Intent(this, SpentActivity.class));
                break;
            case 2:
                startActivity(new Intent(this, SpentListActivity.class));
                break;
            case 3:
                dialogConfirmation.show();
                break;
            case AlertDialog.BUTTON_POSITIVE:
                travels.remove(this.travelSeleted);
                getListView().invalidateViews();
                break;
            case AlertDialog.BUTTON_NEGATIVE:
                dialogConfirmation.dismiss();
                break;
        }
    }

    private AlertDialog createAlertDialog()
    {
        final CharSequence[] items = {
                getString(R.string.edit),
                getString(R.string.new_spent),
                getString(R.string.my_spents),
                getString(R.string.delete),
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.options));
        builder.setItems(items, this);

        return builder.create();
    }

    private AlertDialog createDialogConfirmation()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delele_confirmation);
        builder.setPositiveButton(getString(R.string.yes), this);
        builder.setNegativeButton(getString(R.string.no), this);

        return builder.create();
    }

    private ViewBinder setViewBinder()
    {
        return new ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data, String textRepresentation)
            {
                if (view.getId() == R.id.pgbBudget) {
                    Double values[] = (Double[]) data;
                    ProgressBar progressBar = (ProgressBar) view;
                    progressBar.setMax(values[0].intValue());
                    progressBar.setSecondaryProgress(values[1].intValue());
                    progressBar.setProgress(values[2].intValue());
                    return true;
                }
                return false;
            }
        };
    }
}
