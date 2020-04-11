package com.cliquenextwebsolutions.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    final String SCORE_KEY = "SCORE";
    final String INDEX_KEY = "INDEX";

    private int mUserScore;

    private TextView mTxtQuestion;
    private Button trueBtn;
    private Button falseBtn;

    private int questionIndex;
    private int mQuizQuestion;

    private ProgressBar mProgressBar;
    private TextView mQuizStats;

    private QuizModel[] questionCollection = new QuizModel[] {
            new QuizModel(R.string.q1, true),
            new QuizModel(R.string.q2, false),
            new QuizModel(R.string.q3, true),
            new QuizModel(R.string.q4, false),
            new QuizModel(R.string.q5, true),
            new QuizModel(R.string.q6, false),
            new QuizModel(R.string.q7, true),
            new QuizModel(R.string.q8, false),
            new QuizModel(R.string.q9, true),
            new QuizModel(R.string.q10, false)
    };

    final int USER_PROGRESS = (int) Math.ceil(100.0 / questionCollection.length);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null) {
            mUserScore = savedInstanceState.getInt(SCORE_KEY);
            questionIndex = savedInstanceState.getInt(INDEX_KEY);
        } else {
            mUserScore = 0;
            questionIndex = 0;
        }

        mTxtQuestion = findViewById(R.id.txtQuestion);
        QuizModel q1 = questionCollection[questionIndex];
        mQuizQuestion = q1.getmQuestion();
        mTxtQuestion.setText(mQuizQuestion);
        mProgressBar = findViewById(R.id.quizPB);
        mQuizStats = findViewById(R.id.txtQuizStats);
        mQuizStats.setText("Your Score: " + mUserScore );
        trueBtn = findViewById(R.id.trueBtn);
        falseBtn = findViewById(R.id.falseBtn);

        View.OnClickListener myClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.trueBtn) {
//                    Log.i("btn-clicked", "True Button Tapped!");
                      evaluateAnswer(true);
                } else if (v.getId() == R.id.falseBtn) {
//                    Log.i("btn-clicked", "False Button Tapped!");
                    evaluateAnswer(false);
                }

                changeQuestionOnClick();
            }
        };
        trueBtn.setOnClickListener(myClickListener);
        falseBtn.setOnClickListener(myClickListener);

        QuizModel model = new QuizModel(R.string.q1, true);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // after oncreate method
    }

    @Override
    protected void onResume() {
        super.onResume();

        // application is back in foreground (after onstart method)
    }

    @Override
    protected void onPause() {
        super.onPause();

        // called when application goes into the background after being paused
        // save data here to risk not losing it
    }

    @Override
    protected void onStop() {
        super.onStop();

        // called when application is not visible at all (called after onPause())
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // called when application is completely killed
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(SCORE_KEY, mUserScore);
        outState.putInt(INDEX_KEY, questionIndex);


    }

    private void changeQuestionOnClick() {

        questionIndex = (questionIndex + 1) % 10;
        if(questionIndex == 0) {
            AlertDialog.Builder quizAlert = new AlertDialog.Builder(this);
            quizAlert.setCancelable(false);
            quizAlert.setTitle("Quiz Results");
            quizAlert.setMessage("Your Score: " + mUserScore);
            quizAlert.setPositiveButton("Finish Quiz", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            quizAlert.show();
        }
        mQuizQuestion = questionCollection[questionIndex].getmQuestion();
        mTxtQuestion.setText(mQuizQuestion);
        mProgressBar.incrementProgressBy(USER_PROGRESS);
        mQuizStats.setText("Your Score: " + mUserScore);
    }

    private void evaluateAnswer(boolean userGuess) {
        boolean currentAnswer = questionCollection[questionIndex].ismAnswer();
        if(currentAnswer == userGuess) {
            Toast.makeText(getApplicationContext(), R.string.correct_toast_message, Toast.LENGTH_SHORT).show();
            mUserScore ++;
        } else {
            Toast.makeText(getApplicationContext(), R.string.incorrect_toast_message, Toast.LENGTH_SHORT).show();
        }
    }
}
