package com.example.perfectpancakes.ui.notifications;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.perfectpancakes.PancakeListAdapter;
import com.example.perfectpancakes.PancakeRoomDatabase;
import com.example.perfectpancakes.R;
import com.example.perfectpancakes.models.Pancake;
import com.example.perfectpancakes.ui.dashboard.DashboardFragment;
import java.util.List;

public class NotificationsFragment extends Fragment implements PancakeListAdapter.OnItemListener {
    private View root;
    private RecyclerView recyclerView;
    private PancakeListAdapter adapter;
    private PancakeRoomDatabase mDB;
    private Pancake mPancake;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_notifications, container, false);

        mDB = PancakeRoomDatabase.getDatabase(getActivity());

        recyclerView = root.findViewById(R.id.pancake_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new PancakeListAdapter(this);

        mPancake = new Pancake();

        new ItemTouchHelper(itemTouchHelper).attachToRecyclerView(recyclerView);

        recyclerView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        new LadePancakesTask().execute();
    }

    @Override
    public void onItemClick(int position) {

        String title = ((TextView) recyclerView.findViewHolderForAdapterPosition(position)
                .itemView.findViewById(R.id.list_item_title)).getText().toString();
        new ShowPancakeTask().execute(title);
    }

    private ItemTouchHelper.SimpleCallback itemTouchHelper = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            String title = ((TextView) recyclerView.findViewHolderForAdapterPosition(viewHolder.getAdapterPosition())
                    .itemView.findViewById(R.id.list_item_title)).getText().toString();

            new DeletePancakeTask().execute(title);

            new LadePancakesTask().execute();

        }
    };

    class LadePancakesTask extends AsyncTask<Void, Void, List<Pancake>> {

        @Override
        protected List<Pancake> doInBackground(Void... voids) {
            return mDB.pancakeDao().getAll();
        }

        @Override
        protected void onPostExecute(List<Pancake> pancakes) {
            super.onPostExecute(pancakes);
            adapter.setPancakes(pancakes);
        }
    }

    class ShowPancakeTask extends AsyncTask<String, String, Pancake> {

        @Override
        protected Pancake doInBackground(String... params) {
            return mDB.pancakeDao().getPancake(params[0]);

        }

        @Override
        protected void onPostExecute(Pancake pancake) {
            super.onPostExecute(pancake);
            Fragment fragment = new DashboardFragment();
            Bundle paramPancake = new Bundle();
            paramPancake.putParcelable("pancake", pancake);
            Navigation.findNavController(root).navigate(R.id.navigation_dashboard, paramPancake);
            fragment.setArguments(paramPancake);
        }
    }

    class LadePancakeTask extends AsyncTask<String, String, Pancake> {

        @Override
        protected Pancake doInBackground(String... params) {
            return mDB.pancakeDao().getPancake(params[0]);
        }

        @Override
        protected void onPostExecute(Pancake pancake){
            super.onPostExecute(pancake);
            mPancake.setId(pancake.getId());
        }
    }

    class DeletePancakeTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... title) {
            mDB.pancakeDao().deleteByTitle(title[0]);
            return null;
        }
    }
}