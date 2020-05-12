package com.example.freshprox.vendor;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.freshprox.R;
import com.example.freshprox.SwitchActivity;
import com.example.freshprox.main.MainFragment;
import com.example.freshprox.search.ListOfProducerFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class VendorActivity extends AppCompatActivity {
    private static final String CHANNEL_ID = "mainChannel";
    private static final int NOTIFICATION_ID = 1;
    private ListOfVendor vendors;

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

                //On récupère pour re-push
                SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
                Gson gson = new Gson();
                String json = sharedPreferences.getString("vendors list", null);
                Type type = new TypeToken<ArrayList<Vendor>>() {}.getType();
                ArrayList<Vendor> vendorsList = gson.fromJson(json, type);

                if (vendorsList == null){
                    vendorsList = new ArrayList<>();
                }

                //Création du commerçant
                Vendor newVendor = new Vendor(name, new Address("Dans la rue gros", "06410", "Biot"), prix, Arrays.asList(new Vendor.Product[]{Vendor.Product.fruitLegumes}), R.mipmap.legumes, phone, 0, 0);
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
            }
        });
    }

}
