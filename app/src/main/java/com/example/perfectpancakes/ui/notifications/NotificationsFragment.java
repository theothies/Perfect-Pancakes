package com.example.perfectpancakes.ui.notifications;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.perfectpancakes.PancakeListAdapter;
import com.example.perfectpancakes.PancakeRoomDatabase;
import com.example.perfectpancakes.R;
import com.example.perfectpancakes.dao.PancakeDao;
import com.example.perfectpancakes.models.Pancake;
import com.example.perfectpancakes.ui.dashboard.DashboardFragment;

import java.util.List;

public class NotificationsFragment extends Fragment implements PancakeListAdapter.OnItemListener{
    private View root;
    private PancakeDao dao;
    private RecyclerView recyclerView;
    private PancakeListAdapter adapter;

    private NotificationsViewModel notificationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        root = inflater.inflate(R.layout.fragment_notifications, container, false);

        recyclerView = root.findViewById(R.id.pancake_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new PancakeListAdapter(this);
        recyclerView.setAdapter(adapter);

        dao = PancakeRoomDatabase.getDatabase(getActivity()).pancakeDao();
        return root;
    }

    @Override
    public void onResume(){
        super.onResume();
        new LadePancakesTask().execute();
    }

    @Override
    public void onItemClick(int position) {

        String title = ((TextView) recyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.list_item_title)).getText().toString();
        Pancake pancake = new Pancake(new LadePancakeTask().execute(title));

        /**Fragment fragment = new DashboardFragment();
        Bundle paramPancake = new Bundle();
        paramPancake.putParcelable("pancake", pancake);
        Navigation.findNavController(root).navigate(R.id.navigation_dashboard, paramPancake);
        fragment.setArguments(paramPancake); **/
    }

    class LadePancakesTask extends AsyncTask<Void,Void, List<Pancake>>{

        @Override
        protected List<Pancake> doInBackground(Void... voids){
            return dao.getAll();
        }

        @Override
        protected  void onPostExecute(List<Pancake> pancakes){
            super.onPostExecute(pancakes);
            adapter.setPancakes(pancakes);
        }
    }

    class LadePancakeTask extends AsyncTask<String,String, Pancake>{

        @Override
        protected Pancake doInBackground(String... params) {
            String s = params[0];
            return dao.getPancake(s);

        }

        @Override
        protected void onPostExecute(Pancake pancake){
            super.onPostExecute(pancake);
            Fragment fragment = new DashboardFragment();
            Bundle paramPancake = new Bundle();
            paramPancake.putParcelable("pancake", pancake);
            Navigation.findNavController(root).navigate(R.id.navigation_dashboard, paramPancake);
            fragment.setArguments(paramPancake);
        }
    }
}