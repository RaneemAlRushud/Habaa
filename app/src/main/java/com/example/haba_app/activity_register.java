package com.example.haba_app;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationRequest;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

public class activity_register extends AppCompatActivity implements LocationListener, AdapterView.OnItemSelectedListener {

    EditText txt_name,ph,email,password,cpassword,txt_address;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 100;
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;
    DatabaseHandler databaseHandler;
    User user;
    String[] user_type = { "Guardian", "Pet Owner"};
    Spinner spin_user_type;
    String coordinates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        databaseHandler=new DatabaseHandler(activity_register.this);
         user = new User();
        txt_name = (EditText) findViewById(R.id.txt_name);
        txt_address=findViewById(R.id.txt_current_address);
        ph = (EditText) findViewById(R.id.txt_mobile);
        spin_user_type = (Spinner) findViewById(R.id.spinner_user_type);
        spin_user_type.setOnItemSelectedListener(this);

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,user_type);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_user_type.setAdapter(aa);



        email = (EditText) findViewById(R.id.txt_email);
        password = (EditText) findViewById(R.id.txt_password);
        cpassword = (EditText) findViewById(R.id.txt_cpassword);
         if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
             checkLocationPermission();
         }
         if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
             locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
             locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
         }
        Button btn_signup=findViewById(R.id.btn_signup);
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(txt_name.getText().toString().equals("")){

                    txt_name.setError("Please enter name");

                }

                else if(email.getText().toString().equals("")){
                    email.setError("Please enter email");
                }
                else if(ph.getText().toString().equals("")){
                    ph.setError("Please enter mobile number");
                }
                else if(password.getText().toString().equals("")){
                    password.setError("Please enter password");
                }
                else if(cpassword.getText().toString().equals("")){
                    cpassword.setError("Please enter confirm password");
                }
                else if(!password.getText().toString().equals(cpassword.getText().toString())){
                Toast.makeText(activity_register.this,"Password and confirm password not matched",Toast.LENGTH_LONG).show();
                }
                else if(txt_address.getText().toString().equals("")){
                    txt_address.setError("Current address field is blank!");
                }
                else{


                    if (!databaseHandler.checkUser(email.getText().toString().trim())) {
                        user.setName(txt_name.getText().toString().trim());
                        user.setEmail(email.getText().toString().trim());
                        user.setMobile(ph.getText().toString().trim());
                        user.setAddress(txt_address.getText().toString().trim());
                        user.setType(spin_user_type.getSelectedItem().toString());
                        user.setCoordinates(coordinates);
                        user.setPassword(password.getText().toString().trim());
                        databaseHandler.addUser(user);

                        Toast.makeText(activity_register.this, "Sign up successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(activity_register.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                    }




                }


            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {
        coordinates=location.getLatitude()+","+location.getLongitude();
         txt_address.setText(getCompleteAddressString(location.getLatitude(),location.getLongitude()));
        locationManager.removeUpdates(this);
       // txtLat.setText("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
    }

    @Override
    public void onProviderDisabled(String provider) {

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                       // locationManager.removeUpdates(this);
                    }
                } else {
                    Toast.makeText(this, "permission denied",
                            Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }


    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Log.w("My Current location ", strReturnedAddress.toString());
            } else {
                Log.w("My Current location ", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("My Current loction ", "Canont get Address!");
        }
        return strAdd;
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
