package com.example.vazquez.proyectouaq;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class QuizTwoActivity extends AppCompatActivity {

    private int ids_answers[] = {
            R.id.answer1, R.id.answer2, R.id.answer3, R.id.answer4
    };
    private int correct_answer;
    private int current_question;
    private String[] all_questions_two;
    private boolean[] answer_is_correct;
    private int ans1, ans2, ans3, ans4;
    private int[] answer;
    private TextView text_question;
    private RadioGroup group;
    private Button btn_next, btn_prev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_two);
        text_question = (TextView) findViewById(R.id.text_question);
        group = (RadioGroup) findViewById(R.id.answer_group);
        btn_next = (Button) findViewById(R.id.btn_check);
        btn_prev = (Button) findViewById(R.id.btn_prev);
        all_questions_two = getResources().getStringArray(R.array.all_questions_two);
        answer_is_correct = new boolean[all_questions_two.length];
        answer = new int[all_questions_two.length];
        for (int i = 0; i < answer.length; i++) {
            answer[i] = -1;
        }
        current_question = 0;
        showQuestion();
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
                if (current_question < all_questions_two.length-1) {
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
                    Toast.makeText(QuizTwoActivity.this, resultado, Toast.LENGTH_LONG).show();
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
        String q = all_questions_two[current_question];
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
        if (current_question == all_questions_two.length-1) {
            btn_next.setText(R.string.finish);
            startActivity(new Intent(this, ResultActivity.class));
        } else {
            btn_next.setText(R.string.next);
        }
    }
}