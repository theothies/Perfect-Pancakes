package com.example.perfectpancakes.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.perfectpancakes.R;
import com.example.perfectpancakes.ui.dashboard.DashboardFragment;

import java.text.DecimalFormat;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    DecimalFormat formatter = new DecimalFormat("#");
    public Button calculate;
    public EditText diameter;
    public EditText thiccness;
    public EditText number;
    public TextView result;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        calculatePancakeRecipe(root);

        return root;
    }

    public void calculatePancakeRecipe (View root){
          calculate = root.findViewById(R.id.calculate);
          diameter = root.findViewById(R.id.diameter);
          thiccness = root.findViewById(R.id.thickness);
          number = root.findViewById(R.id.number);

          calculate.setOnClickListener(new View.OnClickListener() {

              public void onClick(View root){
                  double dia =Double.parseDouble(diameter.getText().toString());
                  double thic = Double.parseDouble(thiccness.getText().toString());
                  int num = Integer.parseInt(number.getText().toString());
                  double res = ((dia * dia) * thic * Math.PI * 1.06 * num)/4;
                  double egg = res * 0.004;
                  double milk = res * 0.412;
                  double butter = res * 0.103;
                  double flour = res * 0.227;
                  double water = res * 0.155;

                  DashboardFragment.setResults(dia, thic, num, res, egg, milk, butter, flour, water);

              }
          });
    }

}