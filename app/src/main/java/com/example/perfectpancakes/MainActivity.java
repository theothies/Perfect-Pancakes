package com.example.perfectpancakes;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    DecimalFormat formatter = new DecimalFormat("#");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        Button calculate = (Button) findViewById(R.id.calculate);
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                EditText diameter = (EditText) findViewById(R.id.diameter);
                EditText thickness = (EditText) findViewById(R.id.thickness);
                EditText number = (EditText) findViewById(R.id.number);
                TextView result = (TextView) findViewById(R.id.resultPancakes);

                double dia = Double.parseDouble(diameter.getText().toString());
                double thic = Double.parseDouble(thickness.getText().toString());
                int num = Integer.parseInt(number.getText().toString());
                double res = ((dia * dia) * thic * Math.PI * 1.06 * num) / 4;
                double egg = res * 0.004;
                double milk = res * 0.412;
                double butter = res * 0.103;
                double flour = res * 0.227;
                double water = res * 0.155;

                //result.setText("You need to make " + res + "ml of batter");
                result.setText("You need to make\n " + formatter.format(res) + "ml of pancake-batter\n"
                        + "You need the following ingredients:\n"
                        + formatter.format(egg) + " eggs\n"
                        + formatter.format(milk) + " ml of milk\n"
                        + formatter.format(butter) + " g of butter\n"
                        + formatter.format(flour) + " g of plain flour\n"
                        + formatter.format(water) + " ml of water\n"
                        + "And a pinch of salt. Enjoy!");
            }
        });
    }

}
