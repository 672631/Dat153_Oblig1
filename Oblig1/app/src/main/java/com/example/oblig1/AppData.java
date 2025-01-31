package com.example.oblig1;

import java.util.ArrayList;
import java.util.List;

public class AppData {
    private static AppData instance;

    private List<PictureTextPair> pictureTextPairs;
    private List<QuizQuestion> quizQuestions;

    private AppData(){
        pictureTextPairs = new ArrayList<>();
        quizQuestions = new ArrayList<>();
    }


     public static synchronized AppData getInstance(){
        if(instance == null){
            instance = new AppData();
        }
        return instance;
    }

    public List<PictureTextPair> getPictureTextPairs(){
        return pictureTextPairs;
    }

    public List<QuizQuestion> getQuizQuestions() {
        return quizQuestions;
    }

}
