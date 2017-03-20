package com.example.android.a010_quiz_app;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.android.a010_quiz_app.R.drawable.quiz_landscape;
import static com.example.android.a010_quiz_app.R.drawable.quiz_portrait;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //variables
    private Resources mResources;
    private ProgressBar mProgressBar;
    private EditText mEditText;
    private String mPlayerName = "", mAnswerQuestion = "";
    private TextView mQuestion;
    private ImageView mLogoBg;
    private Button mButton;
    private RatingBar mRatingBar;
    private RadioGroup mRadioGroup;
    private RadioButton mRadioButton1, mRadioButton2, mRadioButton3, mRadioButton4;
    private CheckBox mCheckBox1, mCheckBox2, mCheckBox3, mCheckBox4;
    private String[] mQuestionString, mButtonString, mAnswerString, mGradingString;
    private int mCurrentProgress = 0, mAppMainScore = 0;
    private long mClickTime = 0;
    float scalePixToDp = 0;

    //**************
    //METHODS - MAIN
    //**************
    @Override
    protected void onCreate(Bundle mBundle) {
        super.onCreate(mBundle);
        setContentView(R.layout.activity_main);

        //initialize
        mResources = getResources();
        mGradingString = mResources.getStringArray(R.array.grading_array);
        mQuestionString = mResources.getStringArray(R.array.question_array);
        mAnswerString = mResources.getStringArray(R.array.answer_array);
        mButtonString = mResources.getStringArray(R.array.button_array);
        mLogoBg = (ImageView) findViewById(R.id.imageView);
        mProgressBar = (ProgressBar) findViewById(R.id.progressView);
        mRatingBar = (RatingBar) findViewById(R.id.ratingView);
        mQuestion = (TextView) findViewById(R.id.textView);
        mRadioGroup = (RadioGroup) findViewById(R.id.radioGroupView);
        mRadioButton1 = (RadioButton) findViewById(R.id.radioButton1);
        mRadioButton2 = (RadioButton) findViewById(R.id.radioButton2);
        mRadioButton3 = (RadioButton) findViewById(R.id.radioButton3);
        mRadioButton4 = (RadioButton) findViewById(R.id.radioButton4);
        mCheckBox1 = (CheckBox) findViewById(R.id.checkBox1);
        mCheckBox2 = (CheckBox) findViewById(R.id.checkBox2);
        mCheckBox3 = (CheckBox) findViewById(R.id.checkBox3);
        mCheckBox4 = (CheckBox) findViewById(R.id.checkBox4);
        mEditText = (EditText) findViewById(R.id.editView);
        mButton = (Button) findViewById(R.id.buttonView);
        mButton.setOnClickListener(this);
        scalePixToDp = getResources().getDisplayMetrics().density;

        //setting UI
        setScreen("startScreen");
    }

    @Override
    public void onClick(View view) {
        //preventing double click
        preventMultiClick();

        //different click options depending on quiz progress
        switch (mButton.getText().toString()) {
            case "START": {
                mPlayerName = mEditText.getText().toString();
                if (!mPlayerName.isEmpty()) {
                    setScreen("questionScreen");
                    setQuestionAnswer(0);
                    mEditText.setText("");
                    mEditText.setHint(getResources().getString(R.string.click_to_answer));
                } else simpleToast(getResources().getString(R.string.please_insert_name));
                break;
            }
            case "NEXT": {
                switch (mCurrentProgress) {
                    case 0: {
                        //question 1 field
                        if (mRadioButton3.isChecked()) addPoints();
                        ifRadioClickedNextQuestion(1);
                        break;
                    }
                    case 10: {
                        //question 2 field
                        if (mRadioButton3.isChecked()) addPoints();
                        ifRadioClickedNextQuestion(2);
                        break;
                    }
                    case 20: {
                        //question 3 field
                        if (mRadioButton1.isChecked()) addPoints();
                        ifRadioClickedNextQuestion(3);
                        break;
                    }
                    case 30: {
                        //question 4 field
                        if (mRadioButton1.isChecked()) addPoints();
                        ifRadioClickedNextQuestion(4);
                        break;
                    }
                    case 40: {
                        //question 5 field
                        if (mRadioButton3.isChecked()) addPoints();
                        if (mRadioButton1.isChecked() || mRadioButton2.isChecked() || mRadioButton3.isChecked() || mRadioButton4.isChecked()) {
                            mRadioGroup.clearCheck();
                            setProgressBar();
                            setQuestionAnswer(5);
                            mRadioGroup.setVisibility(View.INVISIBLE);
                            mEditText.setVisibility(View.VISIBLE);
                        }
                        break;
                    }
                    case 50: {
                        //question 6 field
                        mAnswerQuestion = mEditText.getText().toString().toLowerCase();
                        if (!mAnswerQuestion.isEmpty()) {
                            if (mAnswerQuestion.equals("java")) {
                                addPoints();
                            }
                            setQuestionAnswer(6);
                            mRadioGroup.setVisibility(View.VISIBLE);
                            mEditText.setVisibility(View.INVISIBLE);
                            setProgressBar();
                        } else simpleToast(getResources().getString(R.string.please_insert_name));
                        break;
                    }
                    case 60: {
                        //question 7 field
                        if (mRadioButton3.isChecked()) addPoints();
                        ifRadioClickedNextQuestion(7);
                        //prepare UI for next question
                        mRadioGroup.setVisibility(View.INVISIBLE);
                        mCheckBox1.setText(mAnswerString[28]);
                        mCheckBox2.setText(mAnswerString[29]);
                        mCheckBox3.setText(mAnswerString[30]);
                        mCheckBox4.setText(mAnswerString[31]);
                        mCheckBox1.setVisibility(View.VISIBLE);
                        mCheckBox2.setVisibility(View.VISIBLE);
                        mCheckBox3.setVisibility(View.VISIBLE);
                        mCheckBox4.setVisibility(View.VISIBLE);
                        break;
                    }
                    case 70: {
                        //question 8 field
                        if (mCheckBox1.isChecked() || mCheckBox3.isChecked() || mCheckBox2.isChecked() || mCheckBox4.isChecked()) {
                            if (mCheckBox1.isChecked() && mCheckBox3.isChecked() && !mCheckBox2.isChecked() && !mCheckBox4.isChecked()) {
                                addPoints();
                            }
                            //prepare UI for next question
                            mCheckBox1.setChecked(false);
                            mCheckBox2.setChecked(false);
                            mCheckBox3.setChecked(false);
                            mCheckBox4.setChecked(false);
                            mCheckBox1.setVisibility(View.INVISIBLE);
                            mCheckBox2.setVisibility(View.INVISIBLE);
                            mCheckBox3.setVisibility(View.INVISIBLE);
                            mCheckBox4.setVisibility(View.INVISIBLE);
                            setQuestionAnswer(8);
                            setProgressBar();
                            mRadioGroup.setVisibility(View.VISIBLE);
                        }
                        break;
                    }
                    case 80: {
                        //question 9 field
                        if (mRadioButton2.isChecked()) addPoints();
                        ifRadioClickedNextQuestion(9);
                        break;
                    }
                    case 90: {
                        //question 10 field, final question
                        setScreen("finishScreen");
                        if (mRadioButton1.isChecked()) addPoints();
                        if (mRadioButton1.isChecked() || mRadioButton2.isChecked() || mRadioButton3.isChecked() || mRadioButton4.isChecked()) {
                            mRadioGroup.clearCheck();
                            setProgressBar();
                            setScreen("resetScreen");
                            grading();
                        }
                        break;
                    }
                }
                break;
            }
            case "RESET": {
                setScreen("startScreen");
                break;
            }
        }
    }

    //*****************
    //METHODS - CUSTOM
    //*****************

    public void preventMultiClick() {
        if (SystemClock.elapsedRealtime() - mClickTime < 1000) {
            return;
        }
        mClickTime = SystemClock.elapsedRealtime();
    }

    public void grading() {
        if (mAppMainScore > 7) {
            mQuestion.setText(mGradingString[0] + " " + mPlayerName + " " + mGradingString[1] + mGradingString[2]);
        } else if (mAppMainScore > 4) {
            mQuestion.setText(mGradingString[0] + " " + mPlayerName + " " + mGradingString[1] + mGradingString[3]);
        } else
            mQuestion.setText(mGradingString[0] + " " + mPlayerName + " " + mGradingString[1] + mGradingString[4]);
        simpleToast(mGradingString[5] + " " + mAppMainScore + mGradingString[6]);
        mRatingBar.setRating((float) mAppMainScore / 2);
    }

    public void addPoints() {
        mAppMainScore++;
    }

    public void setProgressBar() {
        mCurrentProgress += 10;
        mProgressBar.setProgress(mCurrentProgress);
    }

    public void simpleToast(String message) {
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }

    public void ifRadioClickedNextQuestion(int question) {
        if (mRadioButton1.isChecked() || mRadioButton2.isChecked() || mRadioButton3.isChecked() || mRadioButton4.isChecked()) {
            mRadioGroup.clearCheck();
            setProgressBar();
            setQuestionAnswer(question);
        }
    }

    public void setQuestionAnswer(int questionNumber) {
        mQuestion.setText(mQuestionString[questionNumber]);
        mRadioButton1.setText(mAnswerString[questionNumber * 4]);
        mRadioButton2.setText(mAnswerString[questionNumber * 4 + 1]);
        mRadioButton3.setText(mAnswerString[questionNumber * 4 + 2]);
        mRadioButton4.setText(mAnswerString[questionNumber * 4 + 3]);
    }

    public void setScreen(String uiState) {
        switch (uiState) {
            case "startScreen":
                mPlayerName = "";
                mEditText.setText("");
                mEditText.setHint(getResources().getString(R.string.insert_name));
                mCurrentProgress = 0;
                mAppMainScore = 0;
                mRatingBar.setRating(0);
                mCheckBox1.setVisibility(View.INVISIBLE);
                mCheckBox2.setVisibility(View.INVISIBLE);
                mCheckBox3.setVisibility(View.INVISIBLE);
                mCheckBox4.setVisibility(View.INVISIBLE);
                mRatingBar.setVisibility(View.INVISIBLE);
                mRadioGroup.setVisibility(View.INVISIBLE);
                mProgressBar.setProgress(mCurrentProgress);
                mLogoBg.setVisibility(View.VISIBLE);
                mEditText.setVisibility(View.VISIBLE);
                mQuestion.setVisibility(View.INVISIBLE);
                mButton.setText(mButtonString[0]);
                mProgressBar.setVisibility(View.INVISIBLE);
                break;
            case "questionScreen":
                mRadioGroup.setVisibility(View.VISIBLE);
                mLogoBg.setVisibility(View.INVISIBLE);
                mQuestion.setVisibility(View.VISIBLE);
                mButton.setText(mButtonString[1]);
                mProgressBar.setVisibility(View.VISIBLE);
                mEditText.setVisibility(View.INVISIBLE);
                break;
            case "finishScreen":
                mButton.setText(mButtonString[2]);
                break;
            case "resetScreen":
                mRadioGroup.setVisibility(View.INVISIBLE);
                mButton.setText(mButtonString[3]);
                mRatingBar.setVisibility(View.VISIBLE);
                break;
        }
    }

    //*****************************
    //METHODS - ORIENTATION CHANGE
    //*****************************
    public void viewPadding(int xdp, int ydp) {
        xdp = (int) (xdp * scalePixToDp + 0.5f);
        ydp = (int) (ydp * scalePixToDp + 0.5f);
        mCheckBox1.setPadding(xdp, ydp, xdp, ydp);
        mCheckBox2.setPadding(xdp, ydp, xdp, ydp);
        mCheckBox3.setPadding(xdp, ydp, xdp, ydp);
        mCheckBox4.setPadding(xdp, ydp, xdp, ydp);
        mRadioButton1.setPadding(xdp, ydp, xdp, ydp);
        mRadioButton2.setPadding(xdp, ydp, xdp, ydp);
        mRadioButton3.setPadding(xdp, ydp, xdp, ydp);
        mRadioButton4.setPadding(xdp, ydp, xdp, ydp);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        switch (newConfig.orientation) {
            case Configuration.ORIENTATION_LANDSCAPE:
                mLogoBg.setImageResource(quiz_landscape);
                viewPadding(0, 5);
                break;
            case Configuration.ORIENTATION_PORTRAIT:
                mLogoBg.setImageResource(quiz_portrait);
                viewPadding(0, 40);
                break;
        }
    }
}