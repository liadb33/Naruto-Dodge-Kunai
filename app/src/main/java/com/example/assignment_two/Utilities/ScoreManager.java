package com.example.assignment_two.Utilities;

import android.util.Log;

import com.example.assignment_two.Models.Score;
import com.example.assignment_two.Models.ScoreList;
import com.google.gson.Gson;

public class ScoreManager {

    private static final int FIRST_TEN = 10;
    private static ScoreManager instance;
    private Gson gson;

    private ScoreManager() {
        gson = new Gson();
    }

    public static ScoreManager getInstance() {
        if (instance == null) {
            instance = new ScoreManager();
        }
        return instance;
    }

    public ScoreList loadScoreList() {
        String scoreListFromJson = SharedPreferencesManagerV3.getInstance().getString("scoreList", "");
        if (scoreListFromJson.isEmpty())
            return new ScoreList();

        return gson.fromJson(scoreListFromJson, ScoreList.class);
    }

    public void saveNewScore(int score, double latitude, double longitude, String address) {
        Score newScore = new Score(score, latitude, longitude, address);
        ScoreList scoreList = loadScoreList();

        if(scoreList.getRecords().size() < FIRST_TEN || newScore.getValue() > scoreList.getRecordScore(FIRST_TEN-1)){
            scoreList.addScore(newScore);
            scoreList.sortRecords();
        }

        String scoreListAsJson = gson.toJson(scoreList);
        Log.d("JSON", scoreListAsJson);
        SharedPreferencesManagerV3.getInstance().putString("scoreList", scoreListAsJson);
    }
}
