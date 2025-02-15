package com.example.perfectpancakes.ui.pancake;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.perfectpancakes.PancakeRoomDatabase;
import com.example.perfectpancakes.R;
import com.example.perfectpancakes.dao.PancakeDao;
import com.example.perfectpancakes.models.Pancake;
import com.example.perfectpancakes.ui.recipe.RecipeFragment;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PancakeFragment extends Fragment {

    private View root;
    private PancakeDao dao;
    private Button calculate;
    private EditText title;
    private EditText diameter;
    private EditText thiccness;
    private EditText number;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_pancake, container, false);

        dao = PancakeRoomDatabase.getDatabase(getActivity()).pancakeDao();

        getViews(root);

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

    private void savePancakeOnClick() {

        //saves date as String
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy, HH:mm");
        String strDate = formatter.format(date);

        //creates pancake object and saves into DB
        if(!title.getText().toString().isEmpty() &&
                !diameter.getText().toString().isEmpty() &&
        !thiccness.getText().toString().isEmpty() &&
        !number.getText().toString().isEmpty()){

            // if (title not taken yet) {

                double batter = (((Double.parseDouble(diameter.getText().toString())) * (Double.parseDouble(diameter.getText().toString())))
                        * Double.parseDouble(thiccness.getText().toString()) * Math.PI * 1.06 * Integer.parseInt(number.getText().toString())) / 4;
                double egg = batter * 0.004;
                double milk = batter * 0.412;
                double butter = batter * 0.103;
                double flour = batter * 0.227;
                double water = batter * 0.155;

                Pancake pancake = new Pancake(title.getText().toString(),
                        strDate, Double.parseDouble(diameter.getText().toString()),
                        Double.parseDouble(thiccness.getText().toString()), batter,
                        egg, milk, butter, flour, water, Integer.parseInt(number.getText().toString()));

                new SaveTask().execute(pancake);

                Fragment fragment = new RecipeFragment();
                Bundle paramPancake = new Bundle();
                paramPancake.putParcelable("pancake", pancake);
                Navigation.findNavController(root).navigate(R.id.navigation_recipe, paramPancake);
                fragment.setArguments(paramPancake);

            //} else {
            //  Toast.makeText(getActivity(),"Recipe Name already taken. Please use another recipe name", Toast.LENGTH_SHORT).show();
            //}  <--- soll vermeiden, dass ein Titel mehrfach als Titel benutzt werden kann

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