package com.example.assignment_two.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.assignment_two.Adapters.RecordAdapter;
import com.example.assignment_two.Interfaces.MapZoomCallback;
import com.example.assignment_two.R;
import com.example.assignment_two.Models.Score;
import com.example.assignment_two.Models.ScoreList;
import com.example.assignment_two.Utilities.SharedPreferencesManagerV3;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ListFragment extends Fragment {
    private ScoreList scoreList;
    private RecyclerView fragment_LST_records;
    private MapZoomCallback mapZoomCallback;
    private Gson gson;

    public ListFragment(MapZoomCallback mapZoomCallback) {
        this.mapZoomCallback = mapZoomCallback;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_list, container, false);
        gson = new Gson();
        findViews(v);
        initViews();
        return v;
    }

    private void initViews() {
        String scoreListFromJson = SharedPreferencesManagerV3.getInstance().getString("scoreList","");
        if(scoreListFromJson.isEmpty())
            scoreList = new ScoreList();
        else
            scoreList = gson.fromJson(scoreListFromJson,ScoreList.class);

        RecordAdapter recordAdapter = new RecordAdapter((ArrayList<Score>) scoreList.getRecords(), mapZoomCallback);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        fragment_LST_records.setLayoutManager(linearLayoutManager);
        fragment_LST_records.setAdapter(recordAdapter);
    }

    private void findViews(View v) {
        fragment_LST_records = v.findViewById(R.id.fragment_LST_records);
    }

}