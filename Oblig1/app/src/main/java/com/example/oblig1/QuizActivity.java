package com.example.oblig1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {
    private ImageView imageQuiz;
    private TextView  tvQuestion, tvFeedback;
    private Button btnOption1, btnOption2, btnOption3, btnNext;

    private List<QuizQuestion> quizQuestions;
    private int currentQuestionIndex = 0;
    private QuizQuestion quizQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quiz);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;


        });
        imageQuiz = findViewById(R.id.image_quiz);
        tvQuestion = findViewById(R.id.tv_question);
        tvFeedback = findViewById(R.id.tv_feedback);
        btnOption1 = findViewById(R.id.btn_option1);
        btnOption2 = findViewById(R.id.btn_option2);
        btnOption3 = findViewById(R.id.btn_option3);
        btnNext = findViewById(R.id.btn_next);

        quizQuestions = new ArrayList<>( AppData.getInstance().getQuizQuestions());

        if (quizQuestions.isEmpty()) {
            tvQuestion.setText("No quiz questions available!");
            return;
        }

        loadNextQuestion();

        btnOption1.setOnClickListener(view -> checkAnswer(btnOption1));
        btnOption2.setOnClickListener(view -> checkAnswer(btnOption2));
        btnOption3.setOnClickListener(view -> checkAnswer(btnOption3));

        btnNext.setOnClickListener(view -> loadNextQuestion());

    }




    private void loadNextQuestion(){
        if(currentQuestionIndex >= quizQuestions.size()){
            tvQuestion.setText("Quiz finished");
            btnOption1.setVisibility(View.GONE);
            btnOption2.setVisibility(View.GONE);
            btnOption3.setVisibility(View.GONE);
            btnNext.setText("Restart Quiz");
            btnNext.setVisibility(View.VISIBLE);
            btnNext.setOnClickListener(view -> restartQuiz());
            return;
        }
        quizQuestion = quizQuestions.get(currentQuestionIndex);
        currentQuestionIndex++;

        imageQuiz.setImageBitmap(quizQuestion.getQPicture());

        List<String> answers =  new ArrayList<>(quizQuestion.getIncorrectAnswers());
        answers.add(quizQuestion.getCorrectAnswer());
        Collections.shuffle(answers);

        btnOption1.setText(answers.get(0));
        btnOption2.setText(answers.get(1));
        btnOption3.setText(answers.get(2));

        tvFeedback.setVisibility(View.GONE);
        btnNext.setVisibility(View.GONE);

    }

    private void checkAnswer(Button selectedButton){
        String selectedAnswer = selectedButton.getText().toString();
        if(selectedAnswer.equals(quizQuestion.getCorrectAnswer())){
            tvFeedback.setText("Correct!");
            tvFeedback.setTextColor( ContextCompat.getColor(this, android.R.color.holo_green_dark));
        }else{
            tvFeedback.setText("Wrong!");
            tvFeedback.setTextColor(ContextCompat.getColor(this, android.R.color.holo_red_dark));
        }

        tvFeedback.setVisibility(View.VISIBLE);
        btnNext.setVisibility(View.VISIBLE);

    }

    private void restartQuiz() {
        currentQuestionIndex = 0;
        loadNextQuestion();
    }


}