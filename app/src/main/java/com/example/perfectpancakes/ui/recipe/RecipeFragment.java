package com.example.perfectpancakes.ui.recipe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.perfectpancakes.PancakeRoomDatabase;
import com.example.perfectpancakes.R;
import com.example.perfectpancakes.dao.PancakeDao;
import com.example.perfectpancakes.models.Pancake;

import java.text.DecimalFormat;

public class RecipeFragment extends Fragment {

    private View root;
    private PancakeDao dao;
    private  TextView dia_input, thicc_input, amount_input;
    private  TextView batter_output, egg_output, milk_output, butter_output, flour_output, water_output;
    private DecimalFormat formatter = new DecimalFormat("#");

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_recipe, container, false);
        dao = PancakeRoomDatabase.getDatabase(getActivity()).pancakeDao();
        addTVtoView(root);
        Bundle paramPancake = this.getArguments();

        if (paramPancake != null) {
            Pancake pancake = paramPancake.getParcelable("pancake");
            setResults(pancake);
        }
        return root;
    }

    //the dynamic TextViews are added to view
    public void addTVtoView(View root){

        //the input data of the user
        dia_input = root.findViewById(R.id.text_diameter_input);
        thicc_input = root.findViewById(R.id.text_thicc_input);
        amount_input = root.findViewById(R.id.text_amount_input);

        //the calculated data
        batter_output = root.findViewById(R.id.text_batter_result);
        egg_output = root.findViewById(R.id.text_eggs_result);
        milk_output = root.findViewById(R.id.text_milk_result);
        butter_output = root.findViewById(R.id.text_butter_result);
        flour_output = root.findViewById(R.id.text_flour_result);
        water_output = root.findViewById(R.id.text_water_result);
    }

    public void setResults(Pancake pancake){
        dia_input.setText(""+pancake.getDiameter()+" cm");
        thicc_input.setText(""+pancake.getThickness()+" cm");
        amount_input.setText(""+pancake.getAmount());
        batter_output.setText(""+formatter.format(pancake.getBatter())+" ml");
        egg_output.setText(""+formatter.format(pancake.getEgg()));
        milk_output.setText(""+formatter.format(pancake.getMilk())+" ml");
        butter_output.setText(""+formatter.format(pancake.getButter())+" g");
        flour_output.setText(""+formatter.format(pancake.getFlour())+" g");
        water_output.setText(""+formatter.format(pancake.getWater())+" ml");
    }
}