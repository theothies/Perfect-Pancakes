package com.example.perfectpancakes.ui.home;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.perfectpancakes.PancakeRoomDatabase;
import com.example.perfectpancakes.R;
import com.example.perfectpancakes.dao.PancakeDao;
import com.example.perfectpancakes.models.Pancake;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HomeFragment extends Fragment {
    View root;
    private PancakeDao dao;

    private HomeViewModel homeViewModel;
    //DecimalFormat formatter = new DecimalFormat("#");
    public Button calculate;
    public EditText title;
    public EditText diameter;
    public EditText thiccness;
    public EditText number;
    //public TextView result;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);

        dao = PancakeRoomDatabase.getDatabase(getActivity()).pancakeDao();

        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });


        getViews(root);

         //calculatePancakeRecipe(root);
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePancakeOnClick();
            }
        });
        return root;
    }

    public void getViews(View root){
        title = root.findViewById(R.id.title);
        calculate = root.findViewById(R.id.calculate);
        diameter = root.findViewById(R.id.diameter);
        thiccness = root.findViewById(R.id.thickness);
        number = root.findViewById(R.id.number);
    }
/*    public void calculatePancakeRecipe (View root){
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
    } */

    private void savePancakeOnClick() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        String strDate = formatter.format(date);

        if(!title.getText().toString().isEmpty() &&
                !diameter.getText().toString().isEmpty() &&
        !thiccness.getText().toString().isEmpty() &&
        !number.getText().toString().isEmpty()){

            double batter = ( ( (Double.parseDouble(diameter.getText().toString())) * (Double.parseDouble(diameter.getText().toString())) )
                    * Double.parseDouble(thiccness.getText().toString()) * Math.PI * 1.06 * Integer.parseInt(number.getText().toString()) ) / 4;
            double egg = batter * 0.004;
            double milk = batter * 0.412;
            double butter = batter * 0.103;
            double flour = batter * 0.227;
            double water = batter * 0.155;

            new SaveTask()
                    .execute(new Pancake(title.getText().toString(),
                            strDate, Double.parseDouble(diameter.getText().toString()),
                            Double.parseDouble(thiccness.getText().toString()), batter,
                            egg, milk, butter, flour, water, Integer.parseInt(number.getText().toString())));
        }else{
            Toast.makeText(getActivity(),"Please fill every parameter", Toast.LENGTH_SHORT).show();
        }
    }

    class SaveTask extends AsyncTask<Pancake,Void,Void> {

        @Override
        protected Void doInBackground(Pancake... pancakes){
            dao.insert(pancakes[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid){
            super.onPostExecute(aVoid);
        }
    }

}