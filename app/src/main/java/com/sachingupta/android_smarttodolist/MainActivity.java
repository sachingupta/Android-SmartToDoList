package com.sachingupta.android_smarttodolist;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sachingupta.android_smarttodolist.ToDo.ToDo;
import com.sachingupta.android_smarttodolist.googleplaces.PlacesFetcher;
import com.sachingupta.android_smarttodolist.utilities.GeocoderHelper;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String GOOGLE_API_KEY = "AIzaSyDUM3FztSW84l5R7tHlsRVqEp4BVcxc_qg";
    GoogleMap mMap;
    EditText searchQueryET;

    protected TextView mLocationText;
    Context context;
    Toolbar toolbar;

    double latitude;
    double longitude;
    final int PROXIMITY_RADIUS = 5000;
    TextView locationDetailTV;
    GeocoderHelper geocoderHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;
        geocoderHelper = new GeocoderHelper();
        locationDetailTV =(TextView) findViewById(R.id.latlongLocation);
        navigationDrawerSetup();
        floatingActionButtonSetup();
        currentLocationAndMapSetup();
        searchSetup();
    }

    private void navigationDrawerSetup() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void floatingActionButtonSetup() {
        FloatingActionButton addToDo = (FloatingActionButton) findViewById(R.id.addFloatingBtn);
        addToDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddToDoCategorySelectionActivity.class);
                intent.putExtra("toDoObject", new ToDo());
                startActivity(intent);
            }
        });
    }

    private void currentLocationAndMapSetup() {
        latitude = 42.93708;
        longitude = -75.6107;
        // TODO get current location
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if(location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }
        locationDetailTV.setText(geocoderHelper.getCity(context, latitude, longitude));
        mapSetup();
    }

    private void mapSetup(){
        SupportMapFragment supportMapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.googleMap);

        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mMap.setMyLocationEnabled(true);
                // TODO get current location
                LatLng currentLocation = new LatLng(latitude, longitude);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
                mMap.addMarker(new MarkerOptions().position(currentLocation).title("You are Here"));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
            }
        });
    }

    private void searchSetup(){
        searchQueryET = (EditText) findViewById(R.id.placeText);
        Button btnFind = (Button) findViewById(R.id.btnFind);

        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "finding 2", Toast.LENGTH_LONG).show();
                searchGooglePlaces();
            }
        });
    }

    private void searchGooglePlaces(){
        String type = searchQueryET.getText().toString();
        StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        LatLng mapCenterLocation = mMap.getCameraPosition().target;
        locationDetailTV.setText(geocoderHelper.getCity(context, mapCenterLocation.latitude, mapCenterLocation.longitude));
        googlePlacesUrl.append("location=" + mapCenterLocation.latitude + "," + mapCenterLocation.longitude);
        googlePlacesUrl.append("&radius=" + PROXIMITY_RADIUS);
        googlePlacesUrl.append("&type=" + type);
        googlePlacesUrl.append("&sensor=true");
        googlePlacesUrl.append("&key=" + GOOGLE_API_KEY);

        PlacesFetcher placesRFetcher = new PlacesFetcher();
        Object[] toPass = new Object[2];
        toPass[0] = mMap;
        toPass[1] = googlePlacesUrl.toString();
        placesRFetcher.execute(toPass);
    }

    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, this, 0).show();
            return false;
        }
    }

    private void updateLocationOnMap(LatLng location, String marker){
        mMap.addMarker(new MarkerOptions().position(location).title(marker));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        mLocationText.setText(" Current Location: " + marker);
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
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_my_list) {
            Intent intent = new Intent(context, MyListActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_add_to_do) {
            Intent intent = new Intent(context, AddToDoCategorySelectionActivity.class);
            intent.putExtra("toDoObject", new ToDo());
            startActivity(intent);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
