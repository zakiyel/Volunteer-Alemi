package kz.zhastar.volunteeralemi;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class SuggestionsListFragment extends Fragment {

    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_suggestions_list, container, false);

        recyclerView = view.findViewById(R.id.suggestion_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

}
