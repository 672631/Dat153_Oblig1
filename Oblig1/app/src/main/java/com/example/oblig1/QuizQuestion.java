package com.example.oblig1;

import android.graphics.Bitmap;

import java.util.List;

public class QuizQuestion {
   private PictureTextPair correctAnswer;
   private List<String> incorrectAnswers;

   public QuizQuestion(PictureTextPair correctAnswer, List<String> incorrectAnswers){
      this.correctAnswer = correctAnswer;
      this.incorrectAnswers = incorrectAnswers;
   }

   public String getCorrectAnswer(){
      return correctAnswer.getText();
   }

   public Bitmap getQPicture(){
      return correctAnswer.getPicture();
   }

   public List<String> getIncorrectAnswers(){
      return incorrectAnswers;
   }
}
