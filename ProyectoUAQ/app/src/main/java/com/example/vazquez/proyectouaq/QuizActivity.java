package com.example.vazquez.proyectouaq;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {

    private int ids_answers[] = {
            com.example.vazquez.proyectouaq.R.id.answer1, com.example.vazquez.proyectouaq.R.id.answer2, com.example.vazquez.proyectouaq.R.id.answer3, com.example.vazquez.proyectouaq.R.id.answer4
    };
    private int correct_answer;
    private int current_question;
    private String[] all_questions;
    private boolean[] answer_is_correct;
    private int ans1, ans2, ans3, ans4;
    private int[] answer;
    private TextView text_question;
    private RadioGroup group;
    private Button btn_next, btn_prev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.vazquez.proyectouaq.R.layout.activity_quiz);
        text_question = (TextView) findViewById(com.example.vazquez.proyectouaq.R.id.text_question);
        group = (RadioGroup) findViewById(com.example.vazquez.proyectouaq.R.id.answer_group);
        btn_next = (Button) findViewById(com.example.vazquez.proyectouaq.R.id.btn_check);
        btn_prev = (Button) findViewById(com.example.vazquez.proyectouaq.R.id.btn_prev);
        all_questions = getResources().getStringArray(com.example.vazquez.proyectouaq.R.array.all_questions);
        answer_is_correct = new boolean[all_questions.length];
        answer = new int[all_questions.length];
        for (int i = 0; i < answer.length; i++) {
            answer[i] = -1;
        }
        current_question = 0;
        showQuestion();
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
                if (current_question < all_questions.length-1) {
                    current_question++;
                    showQuestion();
                }
                else {
                    int answer1 = 4, answer2 = 3, answer3 = 2, answer4 = 1, suma;
                    suma = answer1 +answer2 + answer3 + answer4;

                    int correctas = 0, incorrectas = 0;
                    for (boolean b : answer_is_correct) {
                        if (b) suma++;
                        else incorrectas++;
                    }
                    String resultado =
                            String.format(Locale.getDefault(), "Correctas: %d -- Incorrectas: %d",suma, correctas, incorrectas);
                    Toast.makeText(QuizActivity.this, resultado, Toast.LENGTH_LONG).show();
                }
            }
        });

        btn_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
                if (current_question > 0) {
                    current_question--;
                    showQuestion();
                }
            }
        });
    }
  private void checkAnswer() {
        int id = group.getCheckedRadioButtonId();
        int ans = -1;
        for (int i = 0; i < ids_answers.length; i++) {
            if (ids_answers[i] == id) {
                ans = i;
            }
        }
        answer_is_correct[current_question] = (ans == correct_answer);
        answer[current_question] = ans;
    }

    private void showQuestion() {
        String q = all_questions[current_question];
        String[] parts = q.split(";");

        group.clearCheck();

        text_question.setText(parts[0]);
        for (int i = 0; i < ids_answers.length; i++) {
            RadioButton rb = (RadioButton) findViewById(ids_answers[i]);
            String ans = parts[i+1];
            if (ans.charAt(0) == 4) {
                correct_answer = i;

                ans = ans.substring(1);
            }
            rb.setText(ans);
            if (answer[current_question] == i) {
                rb.setChecked(true);
            }
        }
        if (current_question == 0) {
            btn_prev.setVisibility(View.GONE);
        } else {
            btn_prev.setVisibility(View.VISIBLE);
        }
        if (current_question == all_questions.length-1) {
            btn_next.setText(com.example.vazquez.proyectouaq.R.string.finish);
            startActivity(new Intent(this, ResultActivity.class));
        } else {
            btn_next.setText(com.example.vazquez.proyectouaq.R.string.next);
        }
    }
}