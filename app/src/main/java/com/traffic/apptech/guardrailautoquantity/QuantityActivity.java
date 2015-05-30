package com.traffic.apptech.guardrailautoquantity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.media.MediaDescriptionCompatApi21;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class QuantityActivity extends AppCompatActivity implements Communicator{
    private int site = 1;
    TextView siteNumberText = null;
    Button guardrailID = null;
    Button installButton = null;
    Button removeButton = null;
    EditText speed = null;
    EditText distToFrontRail = null;
    EditText distToBackHazard = null;
    RadioGroup AADT = null;
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
        guardrailID = (Button) findViewById(R.id.btn_guardrailID);
        //et = (EditText) findViewById(R.id.edit_textbox);
        strARRinstall = new ArrayList<String>();
        strARRremove = new ArrayList<String>();
        guardrailItems = (Spinner) findViewById(R.id.spinner_guardrailItems);

        adapterInstall = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, strARRinstall);
        adapterRemove = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, strARRremove);
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
        String anythingElse = "btn_guardrailID";
        final Context context = getApplicationContext();
        switch (item.getItemId()) {
            case R.id.btn_guardrailID:

                //checks to see if network is availble
                Boolean networkAvailable = false;
                networkAvailable = isNetworkAvailable();
                //if the network is availble then it goes to a website - http://stackoverflow.com/a/4239019
                if(networkAvailable == true) {
                    String url = "http://www.salemsmarthouse.com/WikiVDOT/index.php?title=Guardrail";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
                //if no internet access is availible it will toast, but should show the offline version for the identifier in the furture - http://stackoverflow.com/a/4239019
                else {
                    Toast.makeText(context,"NO Network Connection",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btn_guardrailCondition:
                Resources res = getResources();
                final CharSequence[] items = res.getStringArray(R.array.GuardrailTypes);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Select Guardrail Item?");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        String itemnumber = Integer.toString(item);
                       // Toast.makeText(context,itemnumber,Toast.LENGTH_LONG).show();
                        if (item == 0 ){
                            StrongPostConditionA(item);
                        }
                        if (item == 1 ){
                            cableWeakPostA(item);
                        }
                        else if (item == 2){
                            StrongPostConditionA(item);
                        }
                        else if (item == 3){
                            StrongPostConditionA(item);
                        }
                        else if (item == 4){
                            WeakPostConditionA(item);
                        }
                        else if (item == 5){
                            StrongPostConditionA(item);
                        }
                        else if (item == 6){
                            StrongPostConditionA(item);
                        }
                        else if (item == 7){
                            StrongPostConditionA(item);
                        }
                        else if (item == 8){
                            StrongPostConditionA(item);
                        }
                        else if (item == 9){
                            WeakPostConditionA(item);
                        }
                        else {}

                       respond(item);
                    }
                });
                     AlertDialog alert = builder.create();
                alert.show();


/*                builder.setMessage(
                        "60% of the measured guardrail heights are:\n" +
                                "1. GR-2, GR-6, GR-7, GR-9, GR-10, GRFOA, MB-3: Less than 24 or greater than 33 \n" +
                                "2. GR-8, MB-5: Less than 31 or greater than 36\n" +
                                "3. Cable Post GR-3: Less than 24 or greater than 31\n" +
                                "4. Has steel blockouts present and with washers installed on the rail bolts but no backup plates\n" +
                                "5. Blunt end terminals for W-beam guardrail or median barrier at run on direction\n" +
                                "6. Proprietary High Tension Cable Systems: Does not meet manufacturers requirements\n" +
                                "7. Turned-down terminals at the run-on direction,\n" +
                                "8. Bridge approach guardrail that is not connected to the bridge railing\n" +
                                "9. Has less than one foot of soil backing behind posts.\n");*/

                // add a neutral button to the alert box and assign a click listener
               /* builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    // click listener on the alert box
                    public void onClick(DialogInterface arg0, int arg1) {
                        Toast.makeText(context, "Condition Rating D", Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

                    // click listener on the alert box
                    public void onClick(DialogInterface arg0, int arg1) {
                        Toast.makeText(context, "Condition C or better", Toast.LENGTH_LONG).show();
                    }
                });
                // show it
                builder.show();*/
                break;
            case R.id.btn_LON:
                lonCalculation();
                break;
            default:
                Toast.makeText(context,"You would like this to do something, wouldn't you...",Toast.LENGTH_LONG).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void lonCalculation(){
        LayoutInflater layoutInflater = LayoutInflater.from(QuantityActivity.this);
        View promptView = layoutInflater.inflate(R.layout.la_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(QuantityActivity.this);
        alertDialogBuilder.setView(promptView);
        final EditText speed = (EditText) promptView.findViewById(R.id.edit_speed);
        final EditText distFrontRail = (EditText) promptView.findViewById(R.id.edit_distanceFrontRail);
        final EditText distBackHazard = (EditText) promptView.findViewById(R.id.edit_distanceBackHazard);
        final RadioGroup aadtVAL = (RadioGroup) promptView.findViewById(R.id.aadtRadioGroup);

        alertDialogBuilder.setCancelable(true)
        .setPositiveButton("Calculate", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //radioButton ID help: http://stackoverflow.com/a/6441097
                int radioButtonID = aadtVAL.getCheckedRadioButtonId();
                View radioButton = aadtVAL.findViewById(radioButtonID);
                int idx = aadtVAL.indexOfChild(radioButton);

                //Ensures radio button has something checked.
                if (idx == -1) {
                    Toast.makeText(QuantityActivity.this, "Fill in all values", Toast.LENGTH_LONG).show();
                    return;
                }
                //Ensures all Edittexts have a value.
                String distFrontRailVal = distFrontRail.getText().toString();
                String distBackHazardVal = distBackHazard.getText().toString();
                String speedStringVal = speed.getText().toString();
                if (speedStringVal.length() == 0  || distBackHazardVal.length() == 0  || distFrontRailVal.length() == 0 ) {
                    Toast.makeText(QuantityActivity.this, "Fill in all values", Toast.LENGTH_LONG).show();
                    return;
                }

                //rounds speed to nearest 10
                int speedVal = Integer.parseInt(speed.getText().toString());
                speedVal = (speedVal + 5) / 10 * 10;

                //calculates Lr
                int Lr = DeterminLr(idx, speedVal);

                //calculates top and bottom of LON Formula
                float numResult = (float) Integer.parseInt(distBackHazard.getText().toString()) - Integer.parseInt(distFrontRail.getText().toString());
                float denResult = (float) Integer.parseInt(distBackHazard.getText().toString()) / Lr;  // need to add denomenator here

                //Ensures the denom is not 0
                if (denResult > 0) {
                    float lonResult = numResult / denResult;
                    int lonIntResult = Math.round(lonResult);
                    Toast.makeText(QuantityActivity.this, "Length of Need: " + Integer.toString(lonIntResult) + " feet",Toast.LENGTH_LONG ).show();
                } else if (denResult <= 0) {
                    Toast.makeText(QuantityActivity.this, "Input Error", Toast.LENGTH_LONG).show();
                }
            }
        });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    private int DeterminLr(int idx, int speed) {

        int lr = 0;
        switch (idx){
            case 0:
              if(speed == 30){
                  lr = 70;
              }
                else if (speed == 40){
                  lr = 100;
              }
                else if (speed == 50){
                  lr = 150;
              }
              else if (speed == 60){
                  lr = 200;
              }
              else if (speed == 70){
                  lr = 250;
              }
              else if (speed == 80){
                  lr = 330;
              }
                break;
            case 1:
                if(speed == 30){
                    lr = 80;
                }
                else if (speed == 40){
                    lr = 110;
                }
                else if (speed == 50){
                    lr = 160;
                }
                else if (speed == 60){
                    lr = 210;
                }
                else if (speed == 70){
                    lr = 290;
                }
                else if (speed == 80){
                    lr = 380;
                }
                break;
            case 2:
                if(speed == 30){
                    lr = 90;
                }
                else if (speed == 40){
                    lr = 130;
                }
                else if (speed == 50){
                    lr = 190;
                }
                else if (speed == 60){
                    lr = 250;
                }
                else if (speed == 70){
                    lr = 330;
                }
                else if (speed == 80){
                    lr = 430;
                }
                break;
            case 3:
                if(speed == 30){
                    lr = 110;
                }
                else if (speed == 40){
                    lr = 160;
                }
                else if (speed == 50){
                    lr = 230;
                }
                else if (speed == 60){
                    lr = 300;
                }
                else if (speed == 70){
                    lr = 360;
                }
                else if (speed == 80){
                    lr = 470;
                }
                break;
        }
        return lr;
    }


    private void testtwo() {
        final Context context = getApplicationContext();
        Resources res = getResources();
        final EditText speed = (EditText) findViewById(R.id.edit_speed);
        final EditText distFrontRail = (EditText) findViewById(R.id.edit_distanceFrontRail);
        final EditText distBackHazard = (EditText) findViewById(R.id.edit_distanceBackHazard);
        final CharSequence[] items = res.getStringArray((R.array.GuardrailTypes));

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(getLayoutInflater().inflate(R.layout.la_dialog, null));
        builder.setTitle("Determine Length of Need");
        builder.setPositiveButton("Calculate", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText speed = (EditText) findViewById(R.id.edit_speed);
                String speedVal = speed.getText().toString();
                Toast.makeText(context,speedVal,Toast.LENGTH_LONG).show();
//                String stringSpeed = speed.getText().toString();
//                Toast.makeText(context,stringSpeed,Toast.LENGTH_LONG).show();
//                int speedInt = Integer.parseInt(speed.getText().toString().trim());
//                int distanceFrontRailInt = Integer.parseInt(distFrontRail.getText().toString().trim());
//                int distanceBackHazardInt = Integer.parseInt(distBackHazard.getText().toString().trim());
//                int intLON = speedInt * distanceFrontRailInt * distanceBackHazardInt;
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }


    //Cable Weak Post Condition A
    private void cableWeakPostA(int item) {
        final Context context = getApplicationContext();
        Resources res = getResources();
        final CharSequence[] items = res.getStringArray(R.array.GuardrailDetails);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Does the Guardrail Have All?");
        builder.setMessage("\u202260% of run- 27\"-28\"\n\u2022End terminals meet current NCHRP 350 standards\n\u2022Equipped with wood or composite blockouts with back-up plates\n\u2022Does not have washers at rail bolts");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "Condition Rating A", Toast.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cableWeakPostB();
            }
        });
        //builder.setMessage(items[item]);
        AlertDialog alert = builder.create();
        alert.show();
    }
    private void cableWeakPostB() {
        final Context context = getApplicationContext();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        Resources res = getResources();
        builder.setTitle("Does the Guardrail Have One or more?:");
        builder.setMessage("\u202260% of run- 26\"-27\" or 28\"-29\"\n\u2022Equipped with wood or composite blockouts with back-up plates");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "Condition Rating B", Toast.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cableWeakPostC();

            }
        });
        //builder.setMessage(items[item]);
        AlertDialog alert = builder.create();
        alert.show();
    }
    private void cableWeakPostC() {
        final Context context = getApplicationContext();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        Resources res = getResources();
        builder.setTitle("Does the Guardrail Have One or more?:");
        builder.setMessage("\u202260% of run- 24\"-26\" or 29\"-31\"\n\u2022End terminals do not meet current NCHRP 350 standards\n\u2022The guardrail system has steel blockouts with back-up plates present at nonspice\n" +
                "location\n\u2022The run-on end section of a GR-6 terminal has an exposed end\n\u2022The guardrail system has washers installed at rail bolts\n\u2022The guardrail system has a deficient Length of Need\n\u2022The guardrail system has major rust");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "Condition Rating C", Toast.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            cableWeakPostD
                    ();
            }
        });
        //builder.setMessage(items[item]);
        AlertDialog alert = builder.create();
        alert.show();
    }
    private void cableWeakPostD() {
        final Context context = getApplicationContext();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        Resources res = getResources();
        builder.setTitle("Does the Guardrail Have One or more?:");
        builder.setMessage("\u202260% of run- less than 24\" or greater than 31\"\n\u2022Proprietary High Tension Cable Systems: Does not meet manufacturer\'s requirements\n\u2022Has steel blockouts present and with washers installed on the rail bolts but no backup plates\n\u2022Blunt end terminals for W-beam guardrail or median barrier at run on direction\n\u2022Turned-down terminals at the run-on direction\n\u2022Bridge approach guardrail that is not connected to the bridge railing\n\u2022Has less than one foot of soil backing behind posts");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "Condition Rating D", Toast.LENGTH_LONG).show();
            }
        });
        //builder.setMessage(items[item]);
        AlertDialog alert = builder.create();
        alert.show();
    }
    //Weak Post Condition A
    private void WeakPostConditionA(int item) {
        final Context context = getApplicationContext();
        Resources res = getResources();
        final CharSequence[] items = res.getStringArray(R.array.GuardrailDetails);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Does the Guardrail Have All?");
        builder.setMessage("\u202260% of run- 31\u00BD\"-33\"\n\u2022End terminals meet current NCHRP 350 standards\n\u2022Equipped with wood or composite blockouts with back-up plates\n\u2022Does not have washers at rail bolts");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "Condition Rating A", Toast.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                WeakPostCondtionB();
            }
        });
        //builder.setMessage(items[item]);
        AlertDialog alert = builder.create();
        alert.show();
    }
    private void WeakPostCondtionB() {
        final Context context = getApplicationContext();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        Resources res = getResources();
        builder.setTitle("Does the Guardrail Have One or more?:");
        builder.setMessage("\u202260% of run- 31\"-31\u00BD\" or 33\"-34\"\n\u2022Equipped with wood or composite blockouts with back-up plates");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "Condition Rating B", Toast.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                weakPostCondtionC();
            }
        });
        //builder.setMessage(items[item]);
        AlertDialog alert = builder.create();
        alert.show();
    }
    private void weakPostCondtionC() {
        final Context context = getApplicationContext();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        Resources res = getResources();
        builder.setTitle("Does the Guardrail Have One or more?:");
        builder.setMessage("\u202260% of run- 29\"-31\" or 34\"-36\"\n\u2022End terminals do not meet current NCHRP 350 standards\n\u2022The guardrail system has steel blockouts with back-up plates present at nonspice\n" +
                "location\n\u2022The run-on end section of a GR-6 terminal has an exposed end\n\u2022The guardrail system has washers installed at rail bolts\n\u2022The guardrail system has a deficient Length of Need\n\u2022The guardrail system has major rust");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "Condition Rating C", Toast.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                weakPostCondtionD();
            }
        });
        //builder.setMessage(items[item]);
        AlertDialog alert = builder.create();
        alert.show();
    }
    private void weakPostCondtionD() {
        final Context context = getApplicationContext();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        Resources res = getResources();
        builder.setTitle("Does the Guardrail Have One or more?:");
        builder.setMessage("\u202260% of run- less than 31\" or greater than 36\"\n\u2022Has steel blockouts present and with washers installed on the rail bolts but no backup plates\n\u2022Blunt end terminals for W-beam guardrail or median barrier at run on direction\n\u2022Turned-down terminals at the run-on direction\n\u2022Bridge approach guardrail that is not connected to the bridge railing\n\u2022Has less than one foot of soil backing behind posts");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "Condition Rating D", Toast.LENGTH_LONG).show();
            }
        });
        //builder.setMessage(items[item]);
        AlertDialog alert = builder.create();
        alert.show();
    }
    //Strong Post condition rating sequence starts here
    private void StrongPostConditionA(int item) {
        final Context context = getApplicationContext();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        Resources res = getResources();
        final CharSequence[] items = res.getStringArray(R.array.GuardrailDetails);
        builder.setTitle("Does the Guardrail Have All?");
        builder.setMessage("\u2022 60% of run- 27\u00BE\"-28\u00BE\"\n\u2022End terminals meet current NCHRP 350 standards\n\u2022Equipped with wood or composite blockouts with back-up plates\n\u2022Does not have washers at rail bolts");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "Condition Rating A", Toast.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                strongPostCondtionB();
            }
        });
        //builder.setMessage(items[item]);
        AlertDialog alert = builder.create();
        alert.show();

    }
    private void strongPostCondtionB() {
        final Context context = getApplicationContext();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        Resources res = getResources();
        builder.setTitle("Does the Guardrail Have One or more?:");
        builder.setMessage("\u2022 60% of run- 27\"-27\u00BE\"-28\u00BE\"-30\"\n\u2022Equipped with wood or composite blockouts with back-up plates");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "Condition Rating B", Toast.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                strongPostCondtionC();
            }
        });
        //builder.setMessage(items[item]);
        AlertDialog alert = builder.create();
        alert.show();
    }
    private void strongPostCondtionC() {
        final Context context = getApplicationContext();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        Resources res = getResources();
        builder.setTitle("Does the Guardrail Have One or more?:");
        builder.setMessage("\u2022 60% of run- 24\"-27\" or 30\"-33\"\n\u2022End terminals do not meet current NCHRP 350 standards\n\u2022The guardrail system has steel blockouts with back-up plates present at nonspice\n" +
                "location\n\u2022The run-on end section of a GR-6 terminal has an exposed end\n\u2022The guardrail system has washers installed at rail bolts\n\u2022The guardrail system has a deficient Length of Need\n\u2022The guardrail system has major rust");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "Condition Rating C", Toast.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                strongPostCondtionD();
            }
        });
        //builder.setMessage(items[item]);
        AlertDialog alert = builder.create();
        alert.show();
    }
    private void strongPostCondtionD() {
        final Context context = getApplicationContext();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        Resources res = getResources();
        builder.setTitle("Does the Guardrail Have One or more?:");
        builder.setMessage("\u2022 60% of run- less than 24\" or greater than 33\"\n\u2022Has steel blockouts present and with washers installed on the rail bolts but no backup plates\n\u2022Blunt end terminals for W-beam guardrail or median barrier at run on direction\n\u2022Turned-down terminals at the run-on direction\n\u2022Bridge approach guardrail that is not connected to the bridge railing\n\u2022Has less than one foot of soil backing behind posts");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "Condition Rating D", Toast.LENGTH_LONG).show();
            }
        });
       //builder.setMessage(items[item]);
        AlertDialog alert = builder.create();
        alert.show();
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

public void NextSite(View v){
    site++;
    Context context = getApplicationContext();
    Toast.makeText(context,"Next Site",Toast.LENGTH_LONG).show();
    siteNumberText = (TextView) findViewById(R.id.text_nextsite);
    siteNumberText.setText("Site #" + site);


}
    @Override
    public void respond(int i) {
        FragmentManager manager = getFragmentManager();
        FragmentB f2 = (FragmentB) manager.findFragmentById(R.id.fragment2);
        f2.changeData(i);
    }
}


