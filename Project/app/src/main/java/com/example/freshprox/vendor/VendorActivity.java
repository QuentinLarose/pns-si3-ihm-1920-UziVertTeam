package com.example.freshprox.vendor;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.freshprox.R;

import com.example.freshprox.search.ListOfProducerFragment;
import com.example.freshprox.search.MapFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class VendorActivity extends AppCompatActivity {
    private MapFragment mapFragment;
    private static final String CHANNEL_ID = "mainChannel";
    private static final int NOTIFICATION_ID = 1;
    private ListOfVendor vendors;
    private ListOfProducerFragment lOPF;
    private Boolean isLOPF;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vendor_activity);

        //Bouton submit
        final Button submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                boolean passing = true;

                //Nom du producteur
                EditText editName = (EditText)findViewById(R.id.name_input);
                String name = editName.getText().toString();
                if (name.matches("")){
                    editName.setError("Le nom du producteur ne peut pas être vide !");
                    passing = false;
                }

                //Numéro de téléphone
                EditText editPhone = (EditText)findViewById(R.id.phone_number_input);
                String phone = editPhone.getText().toString();

                //Tranche de prix
                RadioGroup priceRange = (RadioGroup)findViewById(R.id.price_range);
                int selectedId = priceRange.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton)findViewById(selectedId);
                CharSequence text = radioButton.getText();
                Vendor.PraticedPrice prix;
                if ("Bas".equals(text)) {
                    prix = Vendor.PraticedPrice.low;
                } else if ("Élevés".equals(text)) {
                    prix = Vendor.PraticedPrice.high;
                } else{
                    prix = Vendor.PraticedPrice.moderate;
                }

                //Trucs en vente
                ArrayList<Vendor.Product> productList = new ArrayList<>();
                CheckBox vegeBox = findViewById(R.id.checkbox_vegetables);
                boolean checked = false;

                if (vegeBox.isChecked()){
                    productList.add(Vendor.Product.fruitLegumes);
                    checked = true;
                }
                CheckBox meatBox = findViewById(R.id.checkbox_meat);
                if (meatBox.isChecked()){
                    productList.add(Vendor.Product.viandes);
                    checked = true;
                }
                CheckBox fishBox = findViewById(R.id.checkbox_vegetables);
                if (fishBox.isChecked()){
                    productList.add(Vendor.Product.poissons);
                    checked = true;
                }
                CheckBox cheeseBox = findViewById(R.id.checkbox_vegetables);
                if (cheeseBox.isChecked()){
                    productList.add(Vendor.Product.fromage);
                    checked = true;
                }

                if (!checked){
                    vegeBox.setError("Veuillez choisir au moins une catégorie !");
                    return;
                }

                //Localisation dans l'espace
                EditText editAddress = (EditText)findViewById(R.id.adress_locator);
                String address = editAddress.getText().toString();
                if (address.matches("")){
                    editName.setError("L'adresse ne peut pas être vide !");
                    passing = false;
                }

                EditText editCode = (EditText)findViewById(R.id.postal_code);
                String code = editCode.getText().toString();

                EditText editCity = (EditText)findViewById(R.id.city);
                String city = editCity.getText().toString();
                if (city.matches("")){
                    editCity.setError("La ville ne peut pas être vide !");
                    passing = false;
                }

                Address adresse = new Address(address, code, city);

                EditText editLat = (EditText)findViewById(R.id.lat);
                EditText editLng = (EditText)findViewById(R.id.lng);
                if (editLat.getText().toString().matches("") || editLng.getText().toString().matches("")){
                    Toast.makeText(getApplicationContext(),"Merci de remplir les champs Latitude et Longitude", Toast.LENGTH_LONG).show();
                    passing = false;
                    return;
                }
                double lat = Double.valueOf(String.valueOf(editLat.getText()));
                double lng = Double.valueOf(String.valueOf(editLng.getText()));
                if (editLat.getText().toString().matches("") || editLng.getText().toString().matches("")){
                    Toast.makeText(getApplicationContext(),"Merci de remplir les champs Latitude et Longitude", Toast.LENGTH_LONG).show();
                    passing = false;
                }


                //On récupère pour re-push
                SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
                Gson gson = new Gson();
                String json = sharedPreferences.getString("vendors list", null);
                Type type = new TypeToken<ArrayList<Vendor>>() {}.getType();
                ArrayList<Vendor> vendorsList = gson.fromJson(json, type);

                if (vendorsList == null){
                    vendorsList = new ArrayList<>();
                }

                if (passing == false) return;
                //Création du commerçant
                Vendor newVendor = new Vendor(name, adresse, prix, productList, R.mipmap.legumes, phone, lat, lng);
                vendorsList.add(newVendor);

                //On ajoute et on re-push
                System.out.println(json);
                String newJson = gson.toJson(vendorsList);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("vendors list", newJson);
                editor.apply();

                //Notification
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    CharSequence notifName = "Notification channel name";
                    int importance = NotificationManager.IMPORTANCE_DEFAULT;
                    NotificationChannel channel = new NotificationChannel(CHANNEL_ID, notifName, importance);
                    channel.setDescription("Notification channel description");
                    // Enregister le canal sur le système : attention de ne plus rien modifier après
                    NotificationManager notificationManager = getSystemService(NotificationManager.class);
                    Objects.requireNonNull(notificationManager).createNotificationChannel(channel);
                }

                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());

                NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle("Nouveau producteur ajouté")
                        .setContentText("Il est maintenant accessible dans la liste des vendeurs")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                // notificationId est un identificateur unique par notification qu'il vous faut définir
                notificationManager.notify(NOTIFICATION_ID, notifBuilder.build());

                finish();
            }
        });
        Button btn = findViewById(R.id.mapButton);
        btn.setOnClickListener(v -> {
            if(mapFragment == null) {
                mapFragment = new MapFragment();
                Bundle args = new Bundle();
                args.putBoolean("AJOUT",true);
                mapFragment.setArguments(args);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main, mapFragment).commit();
            }
        });
    }

    public static JSONObject getJSONObjectFromURL(String urlString) throws IOException, JSONException {
        HttpURLConnection urlConnection = null;
        URL url = new URL(urlString);
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setReadTimeout(10000 /* milliseconds */ );
        urlConnection.setConnectTimeout(15000 /* milliseconds */ );
        urlConnection.setDoOutput(true);
        urlConnection.connect();

        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuilder sb = new StringBuilder();

        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line + "\n");
        }
        br.close();

        String jsonString = sb.toString();
        System.out.println("JSON: " + jsonString);

        return new JSONObject(jsonString);
    }

    public void closeMap(Double [] coord){
        if(mapFragment!=null){
            getSupportFragmentManager().beginTransaction().remove(mapFragment).commit();
        }
    }

}
