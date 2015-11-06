package com.example.victornovy.goodtravel;

import android.app.ListActivity;
import android.database.Cursor;
import android.view.MenuInflater;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.widget.SimpleAdapter.ViewBinder;
import android.support.v4.content.ContextCompat;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.widget.AdapterView.AdapterContextMenuInfo;

/**
 * Created by Victor Novy on 04/10/2015.
 */
public class SpentListActivity extends ListActivity implements OnItemClickListener
{

    private List<Map<String, Object>> spent;
    private String previousDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        /* Quando quisermos utilizar uma lista fixa de valore e layout padrão utilizamos o comando abaixo
         * O ArrayAdapter não suporta layouts customizados
        setListAdapter(
                new ArrayAdapter<String> (
                        this, android.R.layout.simple_list_item_1, spentList()
                )
        );

        ListView listView = getListView();
        listView.setOnItemClickListener(this);*/

        String[] by = {"date", "description", "value", "category"};
        int[] to = {R.id.lblSpentListDate, R.id.lblSpentListDescription, R.id.lblSpentListValue, R.id.lnlSpentListCategory};

        SimpleAdapter adapter = new SimpleAdapter(this, spentList(), R.layout.spent_list, by, to);

        adapter.setViewBinder(new SpentViewBinder());

        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);

        //registramos o menu de contexto
        registerForContextMenu(getListView());
    }

    /* Quando quisermos utilizar uma lista fixa de valore utilizamos o comando abaixo
    private List<String> spentList()
    {
        return Arrays.asList("Sanduíche R$ 19,90",
            "Táxi Aeroporto - Hotel R$ 34,00",
            "Revista R$ 12,00"
        );
    }*/

    private List<Map<String, Object>> spentList()
    {
        spent = new ArrayList<Map<String, Object>>();

        Map<String, Object> item = new HashMap<String, Object>();
        item.put("date", "01/01/2015");
        item.put("description", "Diária Hotel");
        item.put("value", "R$ 55,00");
        item.put("category", R.color.acommodation_category);
        spent.add(item);

        item = new HashMap<String, Object>();
        item.put("date", "02/01/2015");
        item.put("description", "BK");
        item.put("value", "R$ 30,00");
        item.put("category", R.color.alimentation_category);
        spent.add(item);

        item = new HashMap<String, Object>();
        item.put("date", "03/01/2015");
        item.put("description", "Ônibus");
        item.put("value", "R$ 20,00");
        item.put("category", R.color.transport_category);
        spent.add(item);

        item = new HashMap<String, Object>();
        item.put("date", "03/01/2015");
        item.put("description", "Rádio");
        item.put("value", "R$ 100,00");
        item.put("category", R.color.othes_category);
        spent.add(item);

        item = new HashMap<String, Object>();
        item.put("date", "07/01/2015");
        item.put("description", "Gasolina");
        item.put("value", "R$ 150,00");
        item.put("category", R.color.fuel_category);
        spent.add(item);

        item = new HashMap<String, Object>();
        item.put("date", "03/01/2015");
        item.put("description", "Subway");
        item.put("value", "R$ 43,00");
        item.put("category", R.color.alimentation_category);
        spent.add(item);

        return spent;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
//        TextView textView = (TextView) view; Fixo
//        textView.getText().toString()
        Map<String, Object> map = spent.get(position);
        String description = (String) map.get("description");
        Toast.makeText(getApplicationContext(), "Selected Item: " + description, Toast.LENGTH_LONG).show();
//        startActivity(new Intent(this, SpentListActivity.class));
    }

    private class SpentViewBinder implements ViewBinder
    {
        @Override
        public boolean setViewValue(View view, Object data, String textRepresentation)
        {
            if (view.getId() == R.id.lblSpentListDate) {
                if (!previousDate.equals(data)) {
                    TextView textView = (TextView) view;
                    textView.setText(textRepresentation);
                    previousDate = textRepresentation;
                    view.setVisibility(View.VISIBLE);
                } else
                    view.setVisibility(View.GONE);
                return true;
            }

            if (view.getId() == R.id.lnlSpentListCategory) {
                Integer id = (Integer) data;
                //view.setBackgroundColor(getResources().getColor(id)); getColor vai ser removido
                view.setBackgroundColor(ContextCompat.getColor(view.getContext(), id));
                return true;
            }
            return false;
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo info)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_spent_list, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        if (item.getItemId() == R.id.mnuDeleteSpentList) {
            Toast.makeText(this, "Teste", Toast.LENGTH_SHORT).show();
            AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
            spent.remove(info.position);
            getListView().invalidateViews();
            //TODO remove database
            return true;
        }
        return super.onContextItemSelected(item);
    }
}