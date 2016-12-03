package com.el_habib.couvoiturage.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.text.format.DateUtils;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;

import com.el_habib.couvoiturage.Couvoiturage;
import com.el_habib.couvoiturage.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class PrincipalActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextInputLayout pointArriveTextInputLayout,pointDepartTextInputLayout;
    Button dateDepartButton;
    GoogleMap map;

    LatLng pointDepart = null,pointArrive = null;
    Date dateDepart = new Date();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        pointArriveTextInputLayout = (TextInputLayout)findViewById(R.id.pointArriveTextInputLayout);
        pointDepartTextInputLayout = (TextInputLayout)findViewById(R.id.pointDepartTextInputLayout);
        dateDepartButton = (Button) findViewById(R.id.dateDepartButton);

        pointDepartTextInputLayout.getEditText().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_ENTER){

                    pointDepart = ((Couvoiturage)getApplication()).getServices().getLocationFromAddress(pointDepartTextInputLayout.getEditText().getText().toString());

                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(pointDepart, 13));

                    map.addMarker(new MarkerOptions()
                            .title("Point de depart")
                            .position(pointDepart));

                    pointDepartTextInputLayout.setVisibility(View.GONE);
                    pointArriveTextInputLayout.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });

        pointArriveTextInputLayout.getEditText().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_ENTER){

                    pointArrive = ((Couvoiturage)getApplication()).getServices().getLocationFromAddress(pointArriveTextInputLayout.getEditText().getText().toString());
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(pointDepart, 13));

                    map.addMarker(new MarkerOptions()
                            .title("Point d'arrivée")
                            .position(pointArrive));

                    pointArriveTextInputLayout.setVisibility(View.GONE);
                    findViewById(R.id.dateLayout).setVisibility(View.VISIBLE);
                }
                return true;
            }
        });

        dateDepartButton.setText(new SimpleDateFormat("dd/MM/yyyy").format(dateDepart));

        dateDepartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap m) {
                map = m;
            }
        });

    }

    public void showDatePicker(){
        Calendar c = Calendar.getInstance();
        c.setTime(dateDepart);
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.YEAR,year);
                c.set(Calendar.MONTH,month);
                c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                dateDepart = c.getTime();
                dateDepartButton.setText(new SimpleDateFormat("dd/MM/yyyy").format(dateDepart));
            }
        };

        new DatePickerDialog(PrincipalActivity.this,dateSetListener,c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH)).show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.settings_item) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
