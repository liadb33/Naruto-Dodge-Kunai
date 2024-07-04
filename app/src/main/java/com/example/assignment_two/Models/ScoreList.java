package com.example.assignment_two.Models;

import java.util.ArrayList;
import java.util.Comparator;

public class ScoreList {

    private String name = "";

    private ArrayList<Score> records = new ArrayList<>();

    public  ScoreList(){

    }

    public String getName() {
        return name;
    }

    public ScoreList setName(String name) {
        this.name = name;
        return this;
    }

    public ArrayList<? extends Object> getRecords() {
        return records;
    }

    public ScoreList setRecords(ArrayList<Score> records) {
        this.records = records;
        return this;
    }

    public ScoreList addScore(Score score){
        this.records.add(score);
        return this;
    }

    public int getRecordScore(int index){
        return records.get(index).getValue();
    }
    public void sortRecords(){
        records.sort(Comparator.comparing(Score::getValue).reversed());
    }

    @Override
    public String toString() {
        return "ScoreList{" +
                "name='" + name + '\'' +
                ", records=" + records +
                '}';
    }
}
