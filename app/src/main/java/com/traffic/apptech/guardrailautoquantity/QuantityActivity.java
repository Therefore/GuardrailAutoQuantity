package com.traffic.apptech.guardrailautoquantity;

import android.app.ListActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class QuantityActivity extends ActionBarActivity{

    Button installButton = null;
    Button removeButton = null;
    private ListView listInstall;
    private ListView listRemove;
    private ArrayList<String> strARRinstall;
    private ArrayList<String> strARRremove;
    private ArrayAdapter<String> adapterInstall;
    private ArrayAdapter<String> adapterRemove;
    private EditText et;
    private Spinner guardrailItems;
    private int check = 0;
    private boolean bool_install = false;
    private  boolean bool_remove = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quantity);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        installButton = (Button) findViewById(R.id.btn_install);
        removeButton = (Button) findViewById(R.id.btn_remove);
        listInstall = (ListView) findViewById(R.id.list_install);
        listRemove = (ListView) findViewById(R.id.list_remove);
        //et = (EditText) findViewById(R.id.edit_textbox);
        strARRinstall = new ArrayList<String>();
        strARRremove = new ArrayList<String>();
        guardrailItems = (Spinner) findViewById(R.id.spinner_guardrailItems);

        adapterInstall = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,strARRinstall);
        adapterRemove = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,strARRremove);
        listInstall.setAdapter(adapterInstall);
        listRemove.setAdapter(adapterRemove);



        //install button
        installButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardrailItems.setVisibility(View.VISIBLE);
                bool_install = true;
                guardrailItems.performClick();
                //strARRinstall.add(guardrailItems.getSelectedItem().toString());
                adapterInstall.notifyDataSetChanged();
            }
        });

        //remove button
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                guardrailItems.setVisibility(View.VISIBLE);
                bool_remove = true;
                guardrailItems.performClick();
                //strARRremove.add(guardrailItems.getSelectedItem().toString());
                adapterRemove.notifyDataSetChanged();
            }
        });

        //spinner control
        guardrailItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                check = check + 1;
                if (check > 1) {

                    if (bool_install == true) {
                        strARRinstall.add(guardrailItems.getSelectedItem().toString());
                        adapterInstall.notifyDataSetChanged();
                        //Toast.makeText(getApplicationContext(),""+strARRinstall.size(),Toast.LENGTH_LONG).show();
                        //Toast.makeText(getApplicationContext(),"This is Toast",Toast.LENGTH_LONG);
                        bool_install = false;
                        guardrailItems.setVisibility(View.GONE);

                    }
                    if (bool_remove == true) {
                        strARRremove.add(guardrailItems.getSelectedItem().toString());
                        adapterRemove.notifyDataSetChanged();
                        bool_remove = false;
                        guardrailItems.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                bool_install = false;
                bool_remove = false;
                guardrailItems.setVisibility(View.GONE);
            }
        });

        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quantity, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
