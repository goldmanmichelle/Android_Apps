//Michelle Goldman
//APD2 1602
//February 27, 2016

package com.michellegoldman.truthortale;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private static final int PREFERENCE_MODE_PRIVATE = 0;

    //Used for saving current game state
    private static final String KEY_INDEX = "index";
    private static final String IS_NEW_GAME = "newGame";



    private ImageButton mTruthButton, mTaleButton;
    private TextView mQuestionTextView, mAnswerTextView, mTimerTextView, mLevelTextView;
    public TextView mCategoryName, mCorrectAnswers;
    private CountDownTimer mCountdownTimer;
    private final long startTime = 20 * 1000;

   // private final long level1StartTime = 20 * 1000;
   // private final long level2StartTime = 15 * 1000;
   // private final long level3StartTime = 10 * 1000;
    private final long countDownInterval = 1 * 1000;
    private boolean timerStarted = true;
    private boolean isPaused = false;
    private boolean isCancelled = false;
    private int mCurrentLevel;
    private int mCurrentIndex = 0;
    public int numCorrectAnswers = 0;
    private int categoryId;
    private boolean geographyBank;
    private boolean artBank = true;
    private boolean musicBank;
    private boolean scienceBank;
    private boolean historyBank;
    private boolean peopleBank;
    private boolean sportsBank;
    private boolean foodBank;
    private SharedPreferences preferenceLevel;
    private SharedPreferences.Editor preferenceEditor;
    private boolean mNewGame = true;
    private boolean level1;
    private boolean level2;
    private boolean level3;
    private boolean levelComplete;
    private boolean endOfQuestions;
    int gameLevel = 1;

    public boolean playingGame = true;


    //Geography Level 1 Question Bank
    private final Question[] mGeographyQuestionBankL1 = new Question[] {
            new Question(R.string.question_oceans, true, 1),
            new Question(R.string.question_mideast, false, 1),
            new Question(R.string.question_africa, false, 1),
            new Question(R.string.question_americas, true, 1),
            new Question(R.string.question_asia, true, 1),
            new Question(R.string.question_honolulu, false, 1),
            new Question(R.string.question_united_states, true, 1),
            new Question(R.string.question_greece, true, 1),
            new Question(R.string.question_croatia, false, 1),
            new Question(R.string.question_san_juan, true, 1),

    };

    //Geography Level 2 Question Bank
    private final Question[] mGeographyQuestionBankL2 = new Question[] {
            new Question(R.string.question_phillipines, false, 2),
            new Question(R.string.question_drumlins, false, 2),
            new Question(R.string.question_date_line, false, 2),
            new Question(R.string.question_iraq, true, 2),
            new Question(R.string.question_lochness, true, 2),
            new Question(R.string.question_appalachian, true, 2),
            new Question(R.string.question_amur, false, 2),
            new Question(R.string.question_haiti, true, 2),
            new Question(R.string.question_iberian, false, 2),
            new Question(R.string.question_luxembourg, false, 2),

    };

    //Art Level 1 Question Bank
    private final Question[] mArtQuestionBankL1 = new Question[] {
            new Question(R.string.question_macbeth, false, 1),
            new Question(R.string.question_sherlock, true, 1),
            new Question(R.string.question_botticelli, true, 1),
            new Question(R.string.question_potter, false, 1),
            new Question(R.string.question_rand, false, 1),
            new Question(R.string.question_expialidocious, true, 1),
            new Question(R.string.question_dickens, true, 1),
            new Question(R.string.question_palette, false, 1),
            new Question(R.string.question_enders_game, true, 1),
            new Question(R.string.question_nobel_prize, true, 1),

    };

    //Art Level 2 Question Bank
    private final Question[] mArtQuestionBankL2 = new Question[] {
            new Question(R.string.question_jane_austen, true, 2),
            new Question(R.string.question_van_gogh, false, 2),
            new Question(R.string.question_peter_pan, true, 2),
            new Question(R.string.question_shakespeare, false, 2),
            new Question(R.string.question_monet, true, 2),
            new Question(R.string.question_beatrix_potter, true, 2),
            new Question(R.string.question_homer, false, 2),
            new Question(R.string.question_jaws, false, 2),
            new Question(R.string.question_barrie, false, 2),
            new Question(R.string.question_genre, true, 2),

    };

    //Music Level 1 Question Bank
    private final Question[] mMusicQuestionBankL1 = new Question[] {
            new Question(R.string.question_beatles, true, 1),
            new Question(R.string.question_horn, false, 1),
            new Question(R.string.question_mp3, false, 1),
            new Question(R.string.question_benatar, false, 1),
            new Question(R.string.question_tlc, true, 1),
            new Question(R.string.question_50cent, true, 1),
            new Question(R.string.question_violin, false, 1),
            new Question(R.string.question_bagpipe, true, 1),
            new Question(R.string.question_pretenders, false, 1),
            new Question(R.string.question_ozzy, true, 1),

    };

    //Music Level 2 Question Bank
    private final Question[] mMusicQuestionBankL2 = new Question[] {
            new Question(R.string.question_charles, true, 2),
            new Question(R.string.question_didgeridoo, true, 2),
            new Question(R.string.question_allegro, false, 2),
            new Question(R.string.question_jackson, true, 2),
            new Question(R.string.question_mccartney, true, 2),
            new Question(R.string.question_britney, false, 2),
            new Question(R.string.question_scale, true, 2),
            new Question(R.string.question_musical, false, 2),
            new Question(R.string.question_clarinet, true, 2),
            new Question(R.string.question_eminem, true, 2),

    };

    //Science Level 1 Question Bank
    private final Question[] mScienceQuestionBankL1 = new Question[] {
            new Question(R.string.question_absolute_zero, true, 1),
            new Question(R.string.question_tintin, true, 1),
            new Question(R.string.question_pitcher, false, 1),
            new Question(R.string.question_pouch, false, 1),
            new Question(R.string.question_uterus, true, 1),
            new Question(R.string.question_eeg, false, 1),
            new Question(R.string.question_housecat, true, 1),
            new Question(R.string.question_pewter, false, 1),
            new Question(R.string.question_squareroot, true, 1),
            new Question(R.string.question_shrimp, true, 1),

    };

    //Science Level 2 Question Bank
    private final Question[] mScienceQuestionBankL2 = new Question[] {
            new Question(R.string.question_snake, false, 2),
            new Question(R.string.question_dragonflies, false, 2),
            new Question(R.string.question_hepatitis, true, 2),
            new Question(R.string.question_flourine, false, 2),
            new Question(R.string.question_venus, true, 2),
            new Question(R.string.question_aspirin, true, 2),
            new Question(R.string.question_sirocco, true, 2),
            new Question(R.string.question_kangaroos, false, 2),
            new Question(R.string.question_seal, false, 2),
            new Question(R.string.question_speed_of_light, true, 2),
    };

    //History Level 1 Question Bank
    private final Question[] mHistoryQuestionBankL1 = new Question[] {
            new Question(R.string.question_roman, true, 1),
            new Question(R.string.question_rosetta, false, 1),
            new Question(R.string.question_harvard, false, 1),
            new Question(R.string.question_frosty, false, 1),
            new Question(R.string.question_cleopatra, true, 1),
            new Question(R.string.question_castro, true, 1),
            new Question(R.string.question_smallpox, false, 1),
            new Question(R.string.question_caesar, true, 1),
            new Question(R.string.question_button, true, 1),
            new Question(R.string.question_playboy, false, 1),

    };

    //History Level 2 Question Bank
    private final Question[] mHistoryQuestionBankL2 = new Question[] {
            new Question(R.string.question_christmas_island, true, 2),
            new Question(R.string.question_smallpox2, true, 2),
            new Question(R.string.question_revolution, false, 2),
            new Question(R.string.question_mlk, false, 2),
            new Question(R.string.question_woodstock, true, 2),
            new Question(R.string.question_jesus, false, 2),
            new Question(R.string.question_anthony, true, 2),
            new Question(R.string.question_lennon, true, 2),
            new Question(R.string.question_lily, false, 2),
            new Question(R.string.question_boleyn, true, 2),

    };

    //People Level 1 Question Bank
    private final Question[] mPeopleQuestionBankL1 = new Question[] {
            new Question(R.string.question_billy, true, 1),
            new Question(R.string.question_emu, true, 1),
            new Question(R.string.question_pop, false, 1),
            new Question(R.string.question_eiffel, false, 1),
            new Question(R.string.question_lindenbergh, true, 1),
            new Question(R.string.question_divorce, true, 1),
            new Question(R.string.question_moon, false, 1),
            new Question(R.string.question_connery, true, 1),
            new Question(R.string.question_epsom, false, 1),
            new Question(R.string.question_bugs, true, 1),

    };

    //People Level 2 Question Bank
    private final Question[] mPeopleQuestionBankL2 = new Question[] {
            new Question(R.string.question_bassinger, true, 2),
            new Question(R.string.question_fort_knox, false, 2),
            new Question(R.string.question_princess_diana, true, 2),
            new Question(R.string.question_friendship_bridge, false, 2),
            new Question(R.string.question_james_dean, false, 2),
            new Question(R.string.question_picasso, true, 2),
            new Question(R.string.question_manchester, true, 2),
            new Question(R.string.question_crystal_palace, true, 2),
            new Question(R.string.question_easter_island, false, 2),
            new Question(R.string.question_mike_tyson, true, 2),

    };

    //Sports Level 1 Question Bank
    private final Question[] mSportsQuestionBankL1 = new Question[] {
            new Question(R.string.question_heisman, true, 1),
            new Question(R.string.question_billiard, false, 1),
            new Question(R.string.question_beer, false, 1),
            new Question(R.string.question_chang, true, 1),
            new Question(R.string.question_biathlon, true, 1),
            new Question(R.string.question_swimming, false, 1),
            new Question(R.string.question_bowling, false, 1),
            new Question(R.string.question_salopettes, true, 1),
            new Question(R.string.question_paramount, true, 1),
            new Question(R.string.question_heavyweight, false, 1),

    };

    //Sports Level 2 Question Bank
    private final Question[] mSportsQuestionBankL2 = new Question[] {
            new Question(R.string.question_world_cup, false, 2),
            new Question(R.string.question_olympic, true, 2),
            new Question(R.string.question_ederle, true, 2),
            new Question(R.string.question_queens, true, 2),
            new Question(R.string.question_golf, false, 2),
            new Question(R.string.question_era, true, 2),
            new Question(R.string.question_canasta, false, 2),
            new Question(R.string.question_stroke, true, 2),
            new Question(R.string.question_nightmare, false, 2),
            new Question(R.string.question_hats, true, 2),

    };

    //Food Level 1 Question Bank
    private final Question[] mFoodQuestionBankL1 = new Question[] {
            new Question(R.string.question_blini, true, 1),
            new Question(R.string.question_carrot, false, 1),
            new Question(R.string.question_stinger, true, 1),
            new Question(R.string.question_sandwich, true, 1),
            new Question(R.string.question_epicure, true, 1),
            new Question(R.string.question_lepidotery, false, 1),
            new Question(R.string.question_pesce_martello, false, 1),
            new Question(R.string.question_forks, true, 1),
            new Question(R.string.question_chop_suey, true, 1),
            new Question(R.string.question_pentagon, true, 1),

    };

    //Food Level 2 Question Bank
    private final Question[] mFoodQuestionBankL2 = new Question[] {
            new Question(R.string.question_chablis, true, 2),
            new Question(R.string.question_eggplant, true, 2),
            new Question(R.string.question_couscous, false, 2),
            new Question(R.string.question_cow, false, 2),
            new Question(R.string.question_sangria, false, 2),
            new Question(R.string.question_rosti, true, 2),
            new Question(R.string.question_gin, false, 2),
            new Question(R.string.question_tayberry, false, 2),
            new Question(R.string.question_artichoke, true, 2),
            new Question(R.string.question_ghee, true, 2),

    };

    public class GameTimer extends CountDownTimer {

        public GameTimer(long startTime, long countDownInterval) {
            super(startTime, countDownInterval);

        }

        @Override
        public void onTick(long startTime) {
            Log.d(TAG, "onTick() called");

            if(isCancelled  && levelComplete == true) {
                Log.d(TAG, "Cancelling timer");
                mTimerTextView.setText("Level Complete!");

                cancel();
                //levelCompleteAlert();
                Log.d(TAG, "advanceLevel() called from Timer onTick()");

                advanceLevel();


            } else if(isPaused  && levelComplete == false) {
                Log.d(TAG, "Cancelling timer");
                mTimerTextView.setText("Try Again!");

                cancel();
                tryAgainAlert();



           // } else if(level1 ==  true && gameLevel == 1) {
            } else if(mCurrentLevel == 1) {

                Log.d(TAG, "Level 1 timer");

                mTimerTextView.setText("" + startTime/1000);

            //} else if (level2 == true && gameLevel == 2) {
            } else if(mCurrentLevel == 2) {

                Log.d(TAG, "Level 2 timer");

                startTime = startTime - 5000;
                mTimerTextView.setText("" + startTime/1000);

           // } else if (level3 == true && gameLevel == 3) {
            } else if(mCurrentLevel == 3) {

                Log.d(TAG, "Level 3 timer");

                startTime = startTime - 10000;
                mTimerTextView.setText("" + startTime/1000);
            }
        }

        @Override
        public void onFinish() {
            Log.d(TAG, "onFinish() called");
/*
            if(endOfQuestions == true && levelComplete == true) {
                cancel();
                advanceLevel();

            } else */if(endOfQuestions == true && levelComplete == false) {
                cancel();
                Log.d(TAG, "Try Again called from Timer onFinish()");

                mTimerTextView.setText("Try Again!");

            } else {
                mTimerTextView.setText("Time's Up");

                //Load Game Over Activity
                Intent intent = new Intent(getApplicationContext(), GameOverActivity.class);
                startActivity(intent);
            }


        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");

        setContentView(R.layout.activity_quiz);

        //Instantiate &  Set Toolbar to House "Quit Game" Functionality
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Returning to Category Screen...");
                quitGameAlert();
            }
        });
        setSupportActionBar(toolbar);


        //Get Intent Data from Category Activity
        Bundle bundle = getIntent().getExtras();
        String data = bundle.get("categoryName").toString();

        //Set Category TextView Contents (based off above Intent)
        mCategoryName = (TextView)findViewById(R.id.category_textView);
        mCategoryName.setText(data);

        //Instantiate Question & Level TextViews
        mQuestionTextView = (TextView)findViewById(R.id.question_textView);
        mLevelTextView = (TextView) findViewById(R.id.level_textView);

        //Instantiate Timer TextView & CountDownTimer
        mTimerTextView = (TextView)findViewById(R.id.timer_textView);
        mCountdownTimer = new GameTimer(startTime, countDownInterval);


        //Instantiate # of Correct Answers TextView (updated as user answers questions correctly)
        mCorrectAnswers = (TextView) findViewById(R.id.correct_answers_textView);

        //Instantiate Correct/Incorrect Answer TextView to Hidden until Question is Answered
        mAnswerTextView = (TextView)findViewById(R.id.answer_textView);
        mAnswerTextView.setVisibility(View.INVISIBLE);

        //Instantiate True Button. Set OnClickListener.
        mTruthButton = (ImageButton) findViewById(R.id.truthButton);
        mTruthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Go to next question
                Log.d(TAG, "Loading next question...");

                mCurrentLevel = preferenceLevel.getInt("level", mCurrentLevel);

                checkL1Answers(true);
                //Go to next question
                Log.d(TAG, "Loading next question...");

               // if(level1 == true) {
               if(mCurrentLevel == 1) {

                   advanceL1Questions();
                   Log.d(TAG, "advanceL1Questions() called from Truth button.");

                   updateL1Questions();
                   Log.d(TAG, "updateL1Questions() called from Truth button.");

                   //mCountdownTimer = new GameTimer(level1StartTime, countDownInterval);

                   // } else if (level2 == true) {
               } else if(mCurrentLevel == 2) {

                   advanceL2Questions();
                   updateL2Questions();
                    // mCountdownTimer = new GameTimer(level2StartTime, countDownInterval);

                //} else if (level3 == true) {
               } else if(mCurrentLevel == 3) {

                   //add logic for Level 3 questions
                }

            }
        });

        //Instantiate Tale Button. Set OnClickListener.
        mTaleButton = (ImageButton) findViewById(R.id.taleButton);
        mTaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Go to next question
                Log.d(TAG, "Loading next question...");
                checkL1Answers(false);
                //Go to next question
                Log.d(TAG, "Loading next question...");
                // if(level1 == true) {
                if(mCurrentLevel == 1) {

                    advanceL1Questions();
                    Log.d(TAG, "advanceL1Questions() called from Truth button.");

                    updateL1Questions();
                    Log.d(TAG, "updateL1Questions() called from Truth button.");

                    //mCountdownTimer = new GameTimer(level1StartTime, countDownInterval);

                    // } else if (level2 == true) {
                } else if(mCurrentLevel == 2) {

                    advanceL2Questions();
                    updateL2Questions();
                    // mCountdownTimer = new GameTimer(level2StartTime, countDownInterval);

                    //} else if (level3 == true) {
                } else if(mCurrentLevel == 3) {

                    //add logic for Level 3 questions
                }
            }
        });

        if(savedInstanceState != null) {
            mCurrentLevel = savedInstanceState.getInt(KEY_INDEX, 0);
            mNewGame = savedInstanceState.getBoolean(IS_NEW_GAME);
        }

        //Check For New Game or Previous Saved Level
        preferenceLevel = getPreferences(PREFERENCE_MODE_PRIVATE);
        int levelInt = preferenceLevel.getInt("level", mCurrentLevel);

        if (mNewGame = true && levelInt == 1) {
            Log.d(TAG, "You're on Level 1");
            //level1 = true;
            mCurrentLevel = 1;
            mLevelTextView.setText("Level 1");
            updateL1Questions();
            selectL1QuestionBank();

        } else if (mNewGame == false && levelInt == 2) {
            Log.d(TAG, "You're on Level 2");
            //level2 = true;
            mCurrentLevel = 2;
            mLevelTextView.setText("Level 2");
            updateL2Questions();
            selectL2QuestionBank();

        } else if (mNewGame == false && levelInt == 3) {
            Log.d(TAG, "You're on Level 3");
            //level3 = true;
            mCurrentLevel = 3;
            mLevelTextView.setText("Level 3");

        }

        //Load Initial Question & Category Name
        updateL1Questions();
        selectL1QuestionBank();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");
        outState.putInt(KEY_INDEX, mCurrentIndex);
        outState.putBoolean(IS_NEW_GAME, mNewGame);
    }




    //Advance Level 1 Question Banks (based on selected Category)
    public void advanceL1Questions() {
        Log.d(TAG, "advanceL1Questions() called");

        mCurrentLevel = 1;

            //if (artBank == true && level1 == true ) {
        if (artBank == true && mCurrentLevel == 1 ) {

            Log.d(TAG, " Advancing Level 1 artBank questions");

                if(mCurrentIndex < 9) {
                    mCurrentIndex = (mCurrentIndex + 1) % mArtQuestionBankL1.length;
                    Log.d(TAG, "Current question is " + mCurrentIndex);
                } else {
                    //User completed level 1 successfully and will advance to level 2
                    Log.d(TAG, " Level 1 Art category completed.");
                    endOfQuestions = true;
                    if(numCorrectAnswers >= 7) {
                        Log.d(TAG, " Loading Level 2 Art questions...");
                        isCancelled = true;
                        levelComplete = true;
                        mCurrentLevel = 2;
                        saveLevel();
                        advanceLevel();
                        //gameLevel = 2;
                        //level2 = true;

                    } else {
                        //User did not complete level 1 successfully and will be given the option to try again or choose a new category
                        isPaused = true;
                        levelComplete = false;
                        //level1 = true;
                        mCurrentLevel = 1;
                    }

                }

           // } else if (musicBank == true && level1 == true) {
            } else if (musicBank == true && mCurrentLevel == 1) {

            Log.d(TAG, " Advancing Level 1 musicBank questions");

                if(mCurrentIndex < 9) {
                    mCurrentIndex = (mCurrentIndex + 1) % mMusicQuestionBankL1.length;
                    Log.d(TAG, "Current question is " + mCurrentIndex);
                } else {
                    //User completed level 1 successfully and will advance to level 2
                    Log.d(TAG, " Level 1 Music category completed.");
                    endOfQuestions = true;
                    if(numCorrectAnswers >= 7) {
                        Log.d(TAG, " Loading Level 2 Music questions...");
                        isCancelled = true;
                        levelComplete = true;
                        mCurrentLevel = 2;
                        saveLevel();
                        //gameLevel = 2;
                        //level2 = true;

                    } else {
                        //User did not complete level 1 successfully and will be given the option to try again or choose a new category
                        isPaused = true;
                        levelComplete = false;
                        //level1 = true;
                        mCurrentLevel = 1;
                    }

                }

            //} else if (scienceBank == true && level1 == true) {
            } else if (scienceBank == true && mCurrentLevel == 1) {

            Log.d(TAG, " Advancing Level 1 scienceBank questions");

                if(mCurrentIndex < 9) {
                    mCurrentIndex = (mCurrentIndex + 1) % mScienceQuestionBankL1.length;
                    Log.d(TAG, "Current question is " + mCurrentIndex);
                } else {
                    //User completed level 1 successfully and will advance to level 2
                    Log.d(TAG, " Level 1 Science category completed.");
                    endOfQuestions = true;
                    if(numCorrectAnswers >= 7) {
                        Log.d(TAG, " Loading Level 2 Science questions...");
                        isCancelled = true;
                        levelComplete = true;
                        mCurrentLevel = 2;
                        saveLevel();
                        //gameLevel = 2;
                        //level2 = true;

                    } else {
                        //User did not complete level 1 successfully and will be given the option to try again or choose a new category
                        isPaused = true;
                        levelComplete = false;
                        //level1 = true;
                        mCurrentLevel = 1;
                    }

                }

            //} else if (geographyBank == true && level1 == true) {
            } else if (geographyBank == true && mCurrentLevel == 1) {

            Log.d(TAG, " Advancing Level 1 geographyBank questions");

                if(mCurrentIndex < 9) {
                    mCurrentIndex = (mCurrentIndex + 1) % mGeographyQuestionBankL1.length;
                    Log.d(TAG, "Current question is " + mCurrentIndex);
                } else {
                    //User completed level 1 successfully and will advance to level 2
                    Log.d(TAG, " Level 1 Geography category completed.");
                    endOfQuestions = true;
                    if(numCorrectAnswers >= 7) {
                        Log.d(TAG, " Loading Level 2 Geography questions...");
                        isCancelled = true;
                        levelComplete = true;
                        mCurrentLevel = 2;
                        saveLevel();
                        //gameLevel = 2;
                        //level2 = true;

                    } else {
                        //User did not complete level 1 successfully and will be given the option to try again or choose a new category
                        isPaused = true;
                        levelComplete = false;
                        //level1 = true;
                        mCurrentLevel = 1;
                    }

                }

           // } else if (historyBank == true && level1 == true) {
            } else if (historyBank == true && mCurrentLevel == 1) {

            Log.d(TAG, " Advancing Level 1 historyBank questions");

                if(mCurrentIndex < 9) {
                    mCurrentIndex = (mCurrentIndex + 1) % mHistoryQuestionBankL1.length;
                    Log.d(TAG, "Current question is " + mCurrentIndex);
                } else {
                    //User completed level 1 successfully and will advance to level 2
                    Log.d(TAG, " Level 1 History category completed.");
                    endOfQuestions = true;
                    if(numCorrectAnswers >= 7) {
                        Log.d(TAG, " Loading Level 2 History questions...");
                        isCancelled = true;
                        levelComplete = true;
                        mCurrentLevel = 2;
                        saveLevel();
                        //gameLevel = 2;
                        //level2 = true;

                    } else {
                        //User did not complete level 1 successfully and will be given the option to try again or choose a new category
                        isPaused = true;
                        levelComplete = false;
                        //level1 = true;
                        mCurrentLevel = 1;
                    }

                }

            //} else if (peopleBank == true && level1 == true) {
            } else if (peopleBank == true && mCurrentLevel == 1) {

            Log.d(TAG, " Advancing Level 1 peopleBank questions");

                if(mCurrentIndex < 9) {
                    mCurrentIndex = (mCurrentIndex + 1) % mPeopleQuestionBankL1.length;
                    Log.d(TAG, "Current question is " + mCurrentIndex);
                } else {
                    //User completed level 1 successfully and will advance to level 2
                    Log.d(TAG, " Level 1 People category completed.");
                    endOfQuestions = true;
                    if(numCorrectAnswers >= 7) {
                        Log.d(TAG, " Loading Level 2 People questions...");
                        isCancelled = true;
                        levelComplete = true;
                        mCurrentLevel = 2;
                        saveLevel();
                        //gameLevel = 2;
                        //level2 = true;

                    } else {
                        //User did not complete level 1 successfully and will be given the option to try again or choose a new category
                        isPaused = true;
                        levelComplete = false;
                        //level1 = true;
                        mCurrentLevel = 1;
                    }

                }

            //} else if (sportsBank == true && level1 == true) {
            } else if (sportsBank == true && mCurrentLevel == 1) {

            Log.d(TAG, " Advancing Level 1 sportsBank questions");

                if(mCurrentIndex < 9) {
                    mCurrentIndex = (mCurrentIndex + 1) % mSportsQuestionBankL1.length;
                    Log.d(TAG, "Current question is " + mCurrentIndex);
                } else {
                    //User completed level 1 successfully and will advance to level 2
                    Log.d(TAG, " Level 1 Sports category completed.");
                    endOfQuestions = true;
                    if(numCorrectAnswers >= 7) {
                        Log.d(TAG, " Loading Level 2 Sports questions...");
                        isCancelled = true;
                        levelComplete = true;
                        mCurrentLevel = 2;
                        saveLevel();
                        //gameLevel = 2;
                        //level2 = true;

                    } else {
                        //User did not complete level 1 successfully and will be given the option to try again or choose a new category
                        isPaused = true;
                        levelComplete = false;
                        //level1 = true;
                        mCurrentLevel = 1;
                    }

                }

            //} else if (foodBank == true && level1 == true) {
            } else if (foodBank == true && mCurrentLevel == 1) {

            Log.d(TAG, " Advancing Level 1 foodBank questions");

                if(mCurrentIndex < 9) {
                    mCurrentIndex = (mCurrentIndex + 1) % mFoodQuestionBankL1.length;
                    Log.d(TAG, "Current question is " + mCurrentIndex);
                } else {
                    //User completed level 1 successfully and will advance to level 2
                    Log.d(TAG, " Level 1 Food category completed.");
                    endOfQuestions = true;
                    if(numCorrectAnswers >= 7) {
                        Log.d(TAG, " Loading Level 2 Food questions...");
                        isCancelled = true;
                        levelComplete = true;
                        mCurrentLevel = 2;
                        saveLevel();
                        //gameLevel = 2;
                        //level2 = true;

                    } else {
                        //User did not complete level 1 successfully and will be given the option to try again or choose a new category
                        isPaused = true;
                        levelComplete = false;
                        //level1 = true;
                        mCurrentLevel = 1;
                    }

                }

            }

    }

    //Advance Level 2 Question Banks (based on selected Category)
    public void advanceL2Questions() {
        Log.d(TAG, "advanceL2Questions() called");

        mCurrentLevel = 2;
        //if (artBank == true && level2 == true ) {
        if (artBank == true && mCurrentLevel == 2 ) {

            Log.d(TAG, " Advancing Level 2 artBank questions");
            artBank = true;

            if(mCurrentIndex < 9) {
                mCurrentIndex = (mCurrentIndex + 1) % mArtQuestionBankL2.length;
                Log.d(TAG, "Current question is " + mCurrentIndex);
            } else {
                //User completed level 2 successfully and will advance to level 3
                Log.d(TAG, " Level 2 Art category completed.");
                endOfQuestions = true;
                if(numCorrectAnswers >= 7) {
                    Log.d(TAG, " Loading Level 3 Art questions...");
                    isCancelled = true;
                    levelComplete = true;
                    mCurrentLevel = 3;
                    saveLevel();
                    //gameLevel = 3;
                    //level2 = true;

                } else {
                    //User did not complete level 2 successfully and will be given the option to try again or choose a new category
                    isPaused = true;
                    levelComplete = false;
                    mCurrentLevel = 2;
                    //level2 = true;
                }


            }

       // } else if (musicBank == true && level2 == true) {
        } else if (musicBank == true && mCurrentLevel == 2) {

            Log.d(TAG, " Advancing Level 2 musicBank questions");
            musicBank = true;

            if(mCurrentIndex < 9) {
                mCurrentIndex = (mCurrentIndex + 1) % mMusicQuestionBankL2.length;
                Log.d(TAG, "Current question is " + mCurrentIndex);
            } else {
                //User completed level 2 successfully and will advance to level 3
                Log.d(TAG, " Level 2 music category completed.");
                endOfQuestions = true;
                if(numCorrectAnswers >= 7) {
                    Log.d(TAG, " Loading Level 3 Music questions...");
                    isCancelled = true;
                    levelComplete = true;
                    mCurrentLevel = 3;
                    saveLevel();
                    //gameLevel = 3;
                    //level2 = true;

                } else {
                    //User did not complete level 2 successfully and will be given the option to try again or choose a new category
                    isPaused = true;
                    levelComplete = false;
                    mCurrentLevel = 2;
                    //level2 = true;
                }


            }

        //} else if (scienceBank == true && level2 == true) {
        } else if (scienceBank == true && mCurrentLevel == 2) {

            Log.d(TAG, " Advancing Level 2 scienceBank questions");
            scienceBank = true;

            if(mCurrentIndex < 9) {
                mCurrentIndex = (mCurrentIndex + 1) % mScienceQuestionBankL2.length;
                Log.d(TAG, "Current question is " + mCurrentIndex);
            } else {
                //User completed level 2 successfully and will advance to level 3
                Log.d(TAG, " Level 2 science category completed.");
                endOfQuestions = true;
                if(numCorrectAnswers >= 7) {
                    Log.d(TAG, " Loading Level 3 Science questions...");
                    isCancelled = true;
                    levelComplete = true;
                    mCurrentLevel = 3;
                    saveLevel();
                    //gameLevel = 3;
                    //level2 = true;

                } else {
                    //User did not complete level 2 successfully and will be given the option to try again or choose a new category
                    isPaused = true;
                    levelComplete = false;
                    mCurrentLevel = 2;
                    //level2 = true;
                }


            }

       // } else if (geographyBank == true && level2 == true) {
        } else if (geographyBank == true && mCurrentLevel == 2) {

            Log.d(TAG, " Advancing Level 2 geographyBank questions");
            geographyBank = true;

            if(mCurrentIndex < 9) {
                mCurrentIndex = (mCurrentIndex + 1) % mGeographyQuestionBankL2.length;
                Log.d(TAG, "Current question is " + mCurrentIndex);
            } else {
                //User completed level 2 successfully and will advance to level 3
                Log.d(TAG, " Level 2 geography category completed.");
                endOfQuestions = true;
                if(numCorrectAnswers >= 7) {
                    Log.d(TAG, " Loading Level 3 Geography questions...");
                    isCancelled = true;
                    levelComplete = true;
                    mCurrentLevel = 3;
                    saveLevel();
                    //gameLevel = 3;
                    //level2 = true;

                } else {
                    //User did not complete level 2 successfully and will be given the option to try again or choose a new category
                    isPaused = true;
                    levelComplete = false;
                    mCurrentLevel = 2;
                    //level2 = true;
                }


            }

       // } else if (historyBank == true && level2 == true) {
        } else if (historyBank == true && mCurrentLevel == 2) {

            Log.d(TAG, " Advancing Level 2 historyBank questions");
            historyBank = true;

            if(mCurrentIndex < 9) {
                mCurrentIndex = (mCurrentIndex + 1) % mHistoryQuestionBankL2.length;
                Log.d(TAG, "Current question is " + mCurrentIndex);
            } else {
                //User completed level 2 successfully and will advance to level 3
                Log.d(TAG, " Level 2 history category completed.");
                endOfQuestions = true;
                if(numCorrectAnswers >= 7) {
                    Log.d(TAG, " Loading Level 3 History questions...");
                    isCancelled = true;
                    levelComplete = true;
                    mCurrentLevel = 3;
                    saveLevel();
                    //gameLevel = 3;
                    //level2 = true;

                } else {
                    //User did not complete level 2 successfully and will be given the option to try again or choose a new category
                    isPaused = true;
                    levelComplete = false;
                    mCurrentLevel = 2;
                    //level2 = true;
                }


            }

       // } else if (peopleBank == true && level2 == true) {
        } else if (peopleBank == true && mCurrentLevel == 2) {

            Log.d(TAG, " Advancing Level 2 peopleBank questions");
            peopleBank = true;

            if(mCurrentIndex < 9) {
                mCurrentIndex = (mCurrentIndex + 1) % mPeopleQuestionBankL2.length;
                Log.d(TAG, "Current question is " + mCurrentIndex);
            } else {
                //User completed level 2 successfully and will advance to level 3
                Log.d(TAG, " Level 2 people category completed.");
                endOfQuestions = true;
                if(numCorrectAnswers >= 7) {
                    Log.d(TAG, " Loading Level 3 People questions...");
                    isCancelled = true;
                    levelComplete = true;
                    mCurrentLevel = 3;
                    saveLevel();
                    //gameLevel = 3;
                    //level2 = true;

                } else {
                    //User did not complete level 2 successfully and will be given the option to try again or choose a new category
                    isPaused = true;
                    levelComplete = false;
                    mCurrentLevel = 2;
                    //level2 = true;
                }


            }

        //} else if (sportsBank == true && level2 == true) {
        } else if (sportsBank == true && mCurrentLevel == 2) {

            Log.d(TAG, " Advancing Level 2 sportsBank questions");
            sportsBank = true;

            if(mCurrentIndex < 9) {
                mCurrentIndex = (mCurrentIndex + 1) % mSportsQuestionBankL2.length;
                Log.d(TAG, "Current question is " + mCurrentIndex);
            } else {
                //User completed level 2 successfully and will advance to level 3
                Log.d(TAG, " Level 2 sports category completed.");
                endOfQuestions = true;
                if(numCorrectAnswers >= 7) {
                    Log.d(TAG, " Loading Level 3 Sports questions...");
                    isCancelled = true;
                    levelComplete = true;
                    mCurrentLevel = 3;
                    saveLevel();
                    //gameLevel = 3;
                    //level2 = true;

                } else {
                    //User did not complete level 2 successfully and will be given the option to try again or choose a new category
                    isPaused = true;
                    levelComplete = false;
                    mCurrentLevel = 2;
                    //level2 = true;
                }


            }

        //} else if (foodBank == true && level2 == true) {
        } else if (foodBank == true && mCurrentLevel == 2) {

            Log.d(TAG, " Advancing Level 2 foodBank questions");
            foodBank = true;

            if(mCurrentIndex < 9) {
                mCurrentIndex = (mCurrentIndex + 1) % mFoodQuestionBankL2.length;
                Log.d(TAG, "Current question is " + mCurrentIndex);
            } else {
                //User completed level 2 successfully and will advance to level 3
                Log.d(TAG, " Level 2 food category completed.");
                endOfQuestions = true;
                if(numCorrectAnswers >= 7) {
                    Log.d(TAG, " Loading Level 3 Food questions...");
                    isCancelled = true;
                    levelComplete = true;
                    mCurrentLevel = 3;
                    //gameLevel = 3;
                    //level2 = true;

                } else {
                    //User did not complete level 2 successfully and will be given the option to try again or choose a new category
                    isPaused = true;
                    levelComplete = false;
                    mCurrentLevel = 2;
                    //level2 = true;
                }


            }

        }
    }

    //Update Level 1 Question Bank (based on Category name)
    public void selectL1QuestionBank() {
        Log.v(TAG, " selectL1QuestionBank() called.");

        if (mCategoryName.getText().equals("Art & Literature")) {
            Log.d(TAG, "artBank loaded");

            artBank = true;
            //level1 = true;
            //mCurrentLevel = 1;
            mCurrentLevel = mArtQuestionBankL1[mCurrentIndex].getLevel();
            Log.d(TAG, "Current level is " + mCurrentLevel);

            categoryId = 0;
            int question = mArtQuestionBankL1[mCurrentIndex].getTextResId();
            Log.d(TAG, "Question ID is " + question);

            mQuestionTextView.setText(question);


        } else if (mCategoryName.getText().equals("Music")) {

            Log.d(TAG, "musicBank loaded");

            musicBank = true;
            //level1 = true;
            //mCurrentLevel = 1;
            mCurrentLevel = mMusicQuestionBankL1[mCurrentIndex].getLevel();
            Log.d(TAG, "Current level is " + mCurrentLevel);

            categoryId = 1;
            int question = mMusicQuestionBankL1[mCurrentIndex].getTextResId();
            mQuestionTextView.setText(question);

        } else if (mCategoryName.getText().equals("Science & Nature")) {
            Log.d(TAG, "scienceBank loaded");

            scienceBank = true;
            //level1 = true;
            //mCurrentLevel = 1;
            mCurrentLevel = mScienceQuestionBankL1[mCurrentIndex].getLevel();
            Log.d(TAG, "Current level is " + mCurrentLevel);

            categoryId = 2;
            int question = mScienceQuestionBankL1[mCurrentIndex].getTextResId();
            mQuestionTextView.setText(question);

        }  else if (mCategoryName.getText().equals("Geography")) {
            Log.d(TAG, "geographyBank loaded");

            geographyBank = true;
            //level1 = true;
            //mCurrentLevel = 1;
            mCurrentLevel = mGeographyQuestionBankL1[mCurrentIndex].getLevel();
            Log.d(TAG, "Current level is " + mCurrentLevel);

            categoryId = 3;
            int question = mGeographyQuestionBankL1[mCurrentIndex].getTextResId();
            mQuestionTextView.setText(question);
            mLevelTextView.setText("Level 2");

        } else if (mCategoryName.getText().equals("History & Holidays")) {
            Log.d(TAG, "historyBank loaded");

            historyBank = true;
            //level1 = true;
            //mCurrentLevel = 1;
            mCurrentLevel = mHistoryQuestionBankL1[mCurrentIndex].getLevel();
            Log.d(TAG, "Current level is " + mCurrentLevel);

            categoryId = 4;
            int question = mHistoryQuestionBankL1[mCurrentIndex].getTextResId();
            mQuestionTextView.setText(question);

        } else if (mCategoryName.getText().equals("People & Places")) {
            Log.d(TAG, "peopleBank loaded");

            peopleBank = true;
            //level1 = true;
            //mCurrentLevel = 1;
            mCurrentLevel = mPeopleQuestionBankL1[mCurrentIndex].getLevel();
            Log.d(TAG, "Current level is " + mCurrentLevel);

            categoryId = 5;
            int question = mPeopleQuestionBankL1[mCurrentIndex].getTextResId();
            mQuestionTextView.setText(question);

        } else if (mCategoryName.getText().equals("Sports & Leisure")) {
            Log.d(TAG, "sportsBank loaded");

            sportsBank = true;
            //level1 = true;
            //mCurrentLevel = 1;
            mCurrentLevel = mSportsQuestionBankL1[mCurrentIndex].getLevel();
            Log.d(TAG, "Current level is " + mCurrentLevel);

            categoryId = 6;
            int question = mSportsQuestionBankL1[mCurrentIndex].getTextResId();
            mQuestionTextView.setText(question);

        } else if (mCategoryName.getText().equals("Food & Drink")) {
            Log.d(TAG, "foodBank loaded");

            foodBank = true;
            //level1 = true;
            //mCurrentLevel = 1;
            mCurrentLevel = mFoodQuestionBankL1[mCurrentIndex].getLevel();
            Log.d(TAG, "Current level is " + mCurrentLevel);

            categoryId = 7;
            int question = mFoodQuestionBankL1[mCurrentIndex].getTextResId();
            mQuestionTextView.setText(question);
        }

    }


    //Update Level 2 Question Bank (based on Category name)
    public void selectL2QuestionBank() {
        Log.v(TAG, " selectL2QuestionBank() called.");

        mCurrentLevel = 2;

        if (mCategoryName.getText().equals("Art & Literature")) {
            Log.d(TAG, "artBank loaded");

            artBank = true;
            //level2 = true;
            //mCurrentLevel = 2;
            mCurrentLevel = mArtQuestionBankL2[mCurrentIndex].getLevel();
            Log.d(TAG, "Current level is " + mCurrentLevel);

            categoryId = 0;
            int question = mArtQuestionBankL2[mCurrentIndex].getTextResId();
            mQuestionTextView.setText(question);

        } else if (mCategoryName.getText().equals("Music")) {

            Log.d(TAG, "musicBank loaded");

            musicBank = true;
            //level2 = true;
            //mCurrentLevel = 2;
            mCurrentLevel = mMusicQuestionBankL2[mCurrentIndex].getLevel();
            Log.d(TAG, "Current level is " + mCurrentLevel);

            categoryId = 1;
            int question = mMusicQuestionBankL2[mCurrentIndex].getTextResId();
            mQuestionTextView.setText(question);

        } else if (mCategoryName.getText().equals("Science & Nature")) {
            Log.d(TAG, "scienceBank loaded");

            scienceBank = true;
            //level2 = true;
            //mCurrentLevel = 2;
            mCurrentLevel = mScienceQuestionBankL2[mCurrentIndex].getLevel();
            Log.d(TAG, "Current level is " + mCurrentLevel);

            categoryId = 2;
            int question = mScienceQuestionBankL2[mCurrentIndex].getTextResId();
            mQuestionTextView.setText(question);

        } else if (mCategoryName.getText().equals("Geography")) {
            Log.d(TAG, "geographyBank loaded");

            geographyBank = true;
            //level2 = true;
            //mCurrentLevel = 2;
            mCurrentLevel = mGeographyQuestionBankL2[mCurrentIndex].getLevel();
            Log.d(TAG, "Current level is " + mCurrentLevel);

            categoryId = 3;
            int question = mGeographyQuestionBankL2[mCurrentIndex].getTextResId();
            mQuestionTextView.setText(question);
            mLevelTextView.setText("Level 2");

        } else if (mCategoryName.getText().equals("History & Holidays")) {
            Log.d(TAG, "historyBank loaded");

            historyBank = true;
            //level2 = true;
            //mCurrentLevel = 2;
            mCurrentLevel = mHistoryQuestionBankL2[mCurrentIndex].getLevel();
            Log.d(TAG, "Current level is " + mCurrentLevel);

            categoryId = 4;
            int question = mHistoryQuestionBankL2[mCurrentIndex].getTextResId();
            mQuestionTextView.setText(question);

        } else if (mCategoryName.getText().equals("People & Places")) {
            Log.d(TAG, "peopleBank loaded");

            peopleBank = true;
            //level2 = true;
            //mCurrentLevel = 2;
            mCurrentLevel = mPeopleQuestionBankL2[mCurrentIndex].getLevel();
            Log.d(TAG, "Current level is " + mCurrentLevel);

            categoryId = 5;
            int question = mPeopleQuestionBankL2[mCurrentIndex].getTextResId();
            mQuestionTextView.setText(question);

        } else if (mCategoryName.getText().equals("Sports & Leisure")) {
            Log.d(TAG, "sportsBank loaded");

            sportsBank = true;
            //level2 = true;
            //mCurrentLevel = 2;
            mCurrentLevel = mSportsQuestionBankL2[mCurrentIndex].getLevel();
            Log.d(TAG, "Current level is " + mCurrentLevel);

            categoryId = 6;
            int question = mSportsQuestionBankL2[mCurrentIndex].getTextResId();
            mQuestionTextView.setText(question);

        } else if (mCategoryName.getText().equals("Food & Drink")) {
            Log.d(TAG, "foodBank loaded");

            foodBank = true;
            //level2 = true;
            //mCurrentLevel = 2;
            mCurrentLevel = mFoodQuestionBankL2[mCurrentIndex].getLevel();
            Log.d(TAG, "Current level is " + mCurrentLevel);

            categoryId = 7;
            int question = mFoodQuestionBankL2[mCurrentIndex].getTextResId();
            mQuestionTextView.setText(question);
        }

    }

    //Load Initial Level 1 Questions or Advance 1 Level 1 Question with Next Button
    private void updateL1Questions() {
        Log.d(TAG, "updateL1Questions() called");

        switch (categoryId) {

            case 0:
                //level1 = true;
                //gameLevel = 1;
                //mCurrentLevel = 1;
                mCurrentLevel = mArtQuestionBankL1[mCurrentIndex].getLevel();
                Log.d(TAG, "Current level is " + mCurrentLevel);

                int artQuestion = mArtQuestionBankL1[mCurrentIndex].getTextResId();
                mQuestionTextView.setText(artQuestion);
                break;
            case 1:
                //level1 = true;
                //gameLevel = 1;
                //mCurrentLevel = 1;
                mCurrentLevel = mMusicQuestionBankL1[mCurrentIndex].getLevel();
                Log.d(TAG, "Current level is " + mCurrentLevel);

                int musicQuestion = mMusicQuestionBankL1[mCurrentIndex].getTextResId();
                mQuestionTextView.setText(musicQuestion);
                break;
            case 2:
                //level1 = true;
                //gameLevel = 1;
                //mCurrentLevel = 1;
                mCurrentLevel = mScienceQuestionBankL1[mCurrentIndex].getLevel();
                Log.d(TAG, "Current level is " + mCurrentLevel);

                int scienceQuestion = mScienceQuestionBankL1[mCurrentIndex].getTextResId();
                mQuestionTextView.setText(scienceQuestion);
                break;
            case 3:
                //level1 = true;
                //gameLevel = 1;
                //mCurrentLevel = 1;
                mCurrentLevel = mGeographyQuestionBankL1[mCurrentIndex].getLevel();
                Log.d(TAG, "Current level is " + mCurrentLevel);

                int geographyQuestion = mGeographyQuestionBankL1[mCurrentIndex].getTextResId();
                mQuestionTextView.setText(geographyQuestion);
                break;
            case 4:
                //level1 = true;
                //gameLevel = 1;
                //mCurrentLevel = 1;
                mCurrentLevel = mHistoryQuestionBankL1[mCurrentIndex].getLevel();
                Log.d(TAG, "Current level is " + mCurrentLevel);

                int historyQuestion = mHistoryQuestionBankL1[mCurrentIndex].getTextResId();
                mQuestionTextView.setText(historyQuestion);
                break;
            case 5:
                //level1 = true;
                //gameLevel = 1;
                //mCurrentLevel = 1;
                mCurrentLevel = mPeopleQuestionBankL1[mCurrentIndex].getLevel();
                Log.d(TAG, "Current level is " + mCurrentLevel);

                int peopleQuestion = mPeopleQuestionBankL1[mCurrentIndex].getTextResId();
                mQuestionTextView.setText(peopleQuestion);
                break;
            case 6:
                //level1 = true;
                //gameLevel = 1;
                //mCurrentLevel = 1;
                mCurrentLevel = mSportsQuestionBankL1[mCurrentIndex].getLevel();
                Log.d(TAG, "Current level is " + mCurrentLevel);

                int sportsQuestion = mSportsQuestionBankL1[mCurrentIndex].getTextResId();
                mQuestionTextView.setText(sportsQuestion);
                break;
            case 7:
                //level1 = true;
                //gameLevel = 1;
                //mCurrentLevel = 1;
                mCurrentLevel = mFoodQuestionBankL1[mCurrentIndex].getLevel();
                Log.d(TAG, "Current level is " + mCurrentLevel);

                int foodQuestion = mFoodQuestionBankL1[mCurrentIndex].getTextResId();
                mQuestionTextView.setText(foodQuestion);
                break;
            default:
                //mCurrentLevel = 1;
                mCurrentLevel = mArtQuestionBankL1[mCurrentIndex].getLevel();
                Log.d(TAG, "Current level is " + mCurrentLevel);

                int defaultQuestion = mArtQuestionBankL1[mCurrentIndex].getTextResId();
                mQuestionTextView.setText(defaultQuestion);
        }

        //Start Game Timer
        Log.d(TAG, "Timer started from updateL1Question");
        mCountdownTimer.start();


    }


    //Load Initial Level 2 Questions or Advance 1 Level 2 Question with Next Button
    private void updateL2Questions() {
        Log.d(TAG, "updateL2Questions() called");

        mCurrentLevel = 2;

        switch (categoryId) {

            case 0:
                //level2 = true;
                //gameLevel = 2;
                mCurrentLevel = 2;
                mCurrentLevel = mArtQuestionBankL2[mCurrentIndex].getLevel();
                Log.d(TAG, "Current level is " + mCurrentLevel);

                int artQuestion = mArtQuestionBankL2[mCurrentIndex].getTextResId();
                mQuestionTextView.setText(artQuestion);
                break;
            case 1:
                //level2 = true;
                //gameLevel = 2;
                mCurrentLevel = 2;
                mCurrentLevel = mMusicQuestionBankL2[mCurrentIndex].getLevel();
                Log.d(TAG, "Current level is " + mCurrentLevel);

                int musicQuestion = mMusicQuestionBankL2[mCurrentIndex].getTextResId();
                mQuestionTextView.setText(musicQuestion);
                break;
            case 2:
                //level2 = true;
                //gameLevel = 2;
                mCurrentLevel = 2;
                mCurrentLevel = mScienceQuestionBankL2[mCurrentIndex].getLevel();
                Log.d(TAG, "Current level is " + mCurrentLevel);

                int scienceQuestion = mScienceQuestionBankL2[mCurrentIndex].getTextResId();
                mQuestionTextView.setText(scienceQuestion);
                break;
            case 3:
                //level2 = true;
                //gameLevel = 2;
                mCurrentLevel = 2;
                mCurrentLevel = mGeographyQuestionBankL2[mCurrentIndex].getLevel();
                Log.d(TAG, "Current level is " + mCurrentLevel);

                int geographyQuestion = mGeographyQuestionBankL2[mCurrentIndex].getTextResId();
                mQuestionTextView.setText(geographyQuestion);
                break;
            case 4:
                //level2 = true;
                //gameLevel = 2;
                mCurrentLevel = 2;
                mCurrentLevel = mHistoryQuestionBankL2[mCurrentIndex].getLevel();
                Log.d(TAG, "Current level is " + mCurrentLevel);

                int historyQuestion = mHistoryQuestionBankL2[mCurrentIndex].getTextResId();
                mQuestionTextView.setText(historyQuestion);
                break;
            case 5:
                //level2 = true;
                //gameLevel = 2;
                mCurrentLevel = 2;
                mCurrentLevel = mPeopleQuestionBankL2[mCurrentIndex].getLevel();
                Log.d(TAG, "Current level is " + mCurrentLevel);

                int peopleQuestion = mPeopleQuestionBankL2[mCurrentIndex].getTextResId();
                mQuestionTextView.setText(peopleQuestion);
                break;
            case 6:
                //level2 = true;
                //gameLevel = 2;
                mCurrentLevel = 2;
                mCurrentLevel = mSportsQuestionBankL2[mCurrentIndex].getLevel();
                Log.d(TAG, "Current level is " + mCurrentLevel);

                int sportsQuestion = mSportsQuestionBankL2[mCurrentIndex].getTextResId();
                mQuestionTextView.setText(sportsQuestion);
                break;
            case 7:
                //level2 = true;
                //gameLevel = 2;
                mCurrentLevel = 2;
                mCurrentLevel = mFoodQuestionBankL2[mCurrentIndex].getLevel();
                Log.d(TAG, "Current level is " + mCurrentLevel);

                int foodQuestion = mFoodQuestionBankL2[mCurrentIndex].getTextResId();
                mQuestionTextView.setText(foodQuestion);
                break;
            default:
                mCurrentLevel = 2;
                mCurrentLevel = mArtQuestionBankL2[mCurrentIndex].getLevel();
                Log.d(TAG, "Current level is " + mCurrentLevel);

                int defaultQuestion = mArtQuestionBankL2[mCurrentIndex].getTextResId();
                mQuestionTextView.setText(defaultQuestion);
        }

        //Start Game Timer
        Log.d(TAG, "Timer started from updateL2Question");
        mCountdownTimer.start();


    }

    //Increment Correct Answer
    private String tallyAnswers() {
        //Add to Number of Correctly Answered Questions
        numCorrectAnswers++;
        String newNumber = Integer.toString(numCorrectAnswers);
        mCorrectAnswers.setText(newNumber);

        Log.d(TAG, "You have answered " + newNumber + " questions correctly so far.");

        return newNumber;

    }

    //Advance to Next Level
    private void advanceLevel() {
        Log.d(TAG, " You're going to the next level");

        //if(artBank == true && level2 == true && endOfQuestions == true) {
        if(artBank == true && mCurrentLevel == 2 && endOfQuestions == true) {

            Log.d(TAG, " That's all of Level 1 Art questions...");
            //level2 = true;
            mCurrentLevel = 2;
            mCurrentLevel = preferenceLevel.getInt("level", 2);
            Log.d(TAG, " Retrieving level from preferences..." + mCurrentLevel);

            isCancelled = false;
            isPaused = false;
            mCurrentIndex = 0;
            mLevelTextView.setText("Level 2");
            numCorrectAnswers = 0;
            mCorrectAnswers.setText("");

            updateL2Questions();
            selectL2QuestionBank();

            mCountdownTimer.start();


        } else {
            //Present user with option to retry category level or choose new level
            Log.d(TAG, "Want to try again?");

        }


    }


    //Check Level 1 Answers. Display "Correct" or "Incorrect"
    private void checkL1Answers (boolean userPressedTruth) {

        if (categoryId == 0 ) {
            boolean answerIsTrue = mArtQuestionBankL1[mCurrentIndex].isAnswerTrue();
            if(userPressedTruth == answerIsTrue) {
                Log.d(TAG, "Truth Pressed");
                mCountdownTimer.cancel();
                Log.d(TAG, "Timer cancelled with checkAnswer()");

                //Display Answer
                mAnswerTextView.setText(R.string.correct_answer);

                //Add to Number of Correctly Answered Questions
                tallyAnswers();

                //Hide Answer
                mAnswerTextView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAnswerTextView.setVisibility(View.INVISIBLE);
                    }
                }, 500);


            } else {
                Log.d(TAG, "Tale Pressed");
                mCountdownTimer.cancel();
                Log.d(TAG, "Timer cancelled with checkAnswer()");

                //Display Answer
                mAnswerTextView.setText(R.string.incorrect_answer);
                mAnswerTextView.setVisibility(View.VISIBLE);

                //Hide Answer
                mAnswerTextView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAnswerTextView.setVisibility(View.INVISIBLE);
                    }
                }, 500);
            }

        } else if (categoryId == 1 ) {
            boolean answerIsTrue = mMusicQuestionBankL1[mCurrentIndex].isAnswerTrue();
            if(userPressedTruth == answerIsTrue) {
                Log.d(TAG, "Truth Pressed");
                mCountdownTimer.cancel();

                //Display Answer
                mAnswerTextView.setText(R.string.correct_answer);

                //Add to Number of Correctly Answered Questions
                tallyAnswers();

                //Hide Answer
                mAnswerTextView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAnswerTextView.setVisibility(View.INVISIBLE);
                    }
                }, 1000);


            } else {
                Log.d(TAG, "Tale Pressed");
                mCountdownTimer.cancel();

                //Display Answer
                mAnswerTextView.setText(R.string.incorrect_answer);
                mAnswerTextView.setVisibility(View.VISIBLE);

                //Hide Answer
                mAnswerTextView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAnswerTextView.setVisibility(View.INVISIBLE);
                    }
                }, 1000);
            }

        } else if (categoryId == 2 ) {
            boolean answerIsTrue = mScienceQuestionBankL1[mCurrentIndex].isAnswerTrue();
            if(userPressedTruth == answerIsTrue) {
                Log.d(TAG, "Truth Pressed");
                mCountdownTimer.cancel();

                //Display Answer
                mAnswerTextView.setText(R.string.correct_answer);

                //Add to Number of Correctly Answered Questions
                tallyAnswers();

                //Hide Answer
                mAnswerTextView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAnswerTextView.setVisibility(View.INVISIBLE);
                    }
                }, 1000);


            } else {
                Log.d(TAG, "Tale Pressed");
                mCountdownTimer.cancel();

                //Display Answer
                mAnswerTextView.setText(R.string.incorrect_answer);
                mAnswerTextView.setVisibility(View.VISIBLE);

                //Hide Answer
                mAnswerTextView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAnswerTextView.setVisibility(View.INVISIBLE);
                    }
                }, 1000);
            }

        } else if (categoryId == 3 ) {
            boolean answerIsTrue = mGeographyQuestionBankL1[mCurrentIndex].isAnswerTrue();
            if(userPressedTruth == answerIsTrue) {
                Log.d(TAG, "Truth Pressed");
                mCountdownTimer.cancel();

                //Display Answer
                mAnswerTextView.setText(R.string.correct_answer);

                //Add to Number of Correctly Answered Questions
                tallyAnswers();

                //Hide Answer
                mAnswerTextView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAnswerTextView.setVisibility(View.INVISIBLE);
                    }
                }, 1000);


            } else {
                Log.d(TAG, "Tale Pressed");
                mCountdownTimer.cancel();

                //Display Answer
                mAnswerTextView.setText(R.string.incorrect_answer);
                mAnswerTextView.setVisibility(View.VISIBLE);

                //Hide Answer
                mAnswerTextView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAnswerTextView.setVisibility(View.INVISIBLE);
                    }
                }, 1000);
            }

        } else if (categoryId == 4 ) {
            boolean answerIsTrue = mHistoryQuestionBankL1[mCurrentIndex].isAnswerTrue();
            if(userPressedTruth == answerIsTrue) {
                Log.d(TAG, "Truth Pressed");
                mCountdownTimer.cancel();

                //Display Answer
                mAnswerTextView.setText(R.string.correct_answer);

                //Add to Number of Correctly Answered Questions
                tallyAnswers();

                //Hide Answer
                mAnswerTextView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAnswerTextView.setVisibility(View.INVISIBLE);
                    }
                }, 1000);


            } else {
                Log.d(TAG, "Tale Pressed");
                mCountdownTimer.cancel();

                //Display Answer
                mAnswerTextView.setText(R.string.incorrect_answer);
                mAnswerTextView.setVisibility(View.VISIBLE);

                //Hide Answer
                mAnswerTextView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAnswerTextView.setVisibility(View.INVISIBLE);
                    }
                }, 1000);
            }

        } else if (categoryId == 5 ) {
            boolean answerIsTrue = mPeopleQuestionBankL1[mCurrentIndex].isAnswerTrue();

            if(userPressedTruth == answerIsTrue) {
                Log.d(TAG, "Truth Pressed");
                mCountdownTimer.cancel();

                //Display Answer
                mAnswerTextView.setText(R.string.correct_answer);

                //Add to Number of Correctly Answered Questions
                tallyAnswers();

                //Hide Answer
                mAnswerTextView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAnswerTextView.setVisibility(View.INVISIBLE);
                    }
                }, 1000);


            } else {
                Log.d(TAG, "Tale Pressed");
                mCountdownTimer.cancel();

                //Display Answer
                mAnswerTextView.setText(R.string.incorrect_answer);
                mAnswerTextView.setVisibility(View.VISIBLE);

                //Hide Answer
                mAnswerTextView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAnswerTextView.setVisibility(View.INVISIBLE);
                    }
                }, 1000);
            }
        } else if (categoryId == 6 ) {
            boolean answerIsTrue = mSportsQuestionBankL1[mCurrentIndex].isAnswerTrue();
            if(userPressedTruth == answerIsTrue) {
                Log.d(TAG, "Truth Pressed");
                mCountdownTimer.cancel();

                //Display Answer
                mAnswerTextView.setText(R.string.correct_answer);

                //Add to Number of Correctly Answered Questions
                tallyAnswers();

                //Hide Answer
                mAnswerTextView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAnswerTextView.setVisibility(View.INVISIBLE);
                    }
                }, 1000);


            } else {
                Log.d(TAG, "Tale Pressed");
                mCountdownTimer.cancel();

                //Display Answer
                mAnswerTextView.setText(R.string.incorrect_answer);
                mAnswerTextView.setVisibility(View.VISIBLE);

                //Hide Answer
                mAnswerTextView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAnswerTextView.setVisibility(View.INVISIBLE);
                    }
                }, 1000);
            }

        } else if (categoryId == 7 ) {
            boolean answerIsTrue = mFoodQuestionBankL1[mCurrentIndex].isAnswerTrue();
            if(userPressedTruth == answerIsTrue) {
                Log.d(TAG, "Truth Pressed");
                mCountdownTimer.cancel();

                //Display Answer
                mAnswerTextView.setText(R.string.correct_answer);

                //Add to Number of Correctly Answered Questions
                tallyAnswers();

                //Hide Answer
                mAnswerTextView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAnswerTextView.setVisibility(View.INVISIBLE);
                    }
                }, 1000);


            } else {
                Log.d(TAG, "Tale Pressed");
                mCountdownTimer.cancel();

                //Display Answer
                mAnswerTextView.setText(R.string.incorrect_answer);
                mAnswerTextView.setVisibility(View.VISIBLE);

                //Hide Answer
                mAnswerTextView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAnswerTextView.setVisibility(View.INVISIBLE);
                    }
                }, 1000);
            }


        }
    }

    //Check Level 2 Answers. Display "Correct" or "Incorrect"
    private void checkL2Answers (boolean userPressedTruth) {

        if (categoryId == 0 ) {
            boolean answerIsTrue = mArtQuestionBankL2[mCurrentIndex].isAnswerTrue();
            if(userPressedTruth == answerIsTrue) {
                Log.d(TAG, "Truth Pressed");
                mCountdownTimer.cancel();
                Log.d(TAG, "Timer cancelled with checkAnswer()");

                //Display Answer
                mAnswerTextView.setText(R.string.correct_answer);

                //Add to Number of Correctly Answered Questions
                tallyAnswers();

                //Hide Answer
                mAnswerTextView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAnswerTextView.setVisibility(View.INVISIBLE);
                    }
                }, 500);


            } else {
                Log.d(TAG, "Tale Pressed");
                mCountdownTimer.cancel();
                Log.d(TAG, "Timer cancelled with checkAnswer()");

                //Display Answer
                mAnswerTextView.setText(R.string.incorrect_answer);
                mAnswerTextView.setVisibility(View.VISIBLE);

                //Hide Answer
                mAnswerTextView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAnswerTextView.setVisibility(View.INVISIBLE);
                    }
                }, 500);
            }

        } else if (categoryId == 1 ) {
            boolean answerIsTrue = mMusicQuestionBankL2[mCurrentIndex].isAnswerTrue();
            if(userPressedTruth == answerIsTrue) {
                Log.d(TAG, "Truth Pressed");
                mCountdownTimer.cancel();

                //Display Answer
                mAnswerTextView.setText(R.string.correct_answer);

                //Add to Number of Correctly Answered Questions
                tallyAnswers();

                //Hide Answer
                mAnswerTextView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAnswerTextView.setVisibility(View.INVISIBLE);
                    }
                }, 1000);


            } else {
                Log.d(TAG, "Tale Pressed");
                mCountdownTimer.cancel();

                //Display Answer
                mAnswerTextView.setText(R.string.incorrect_answer);
                mAnswerTextView.setVisibility(View.VISIBLE);

                //Hide Answer
                mAnswerTextView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAnswerTextView.setVisibility(View.INVISIBLE);
                    }
                }, 1000);
            }

        } else if (categoryId == 2 ) {
            boolean answerIsTrue = mScienceQuestionBankL2[mCurrentIndex].isAnswerTrue();
            if(userPressedTruth == answerIsTrue) {
                Log.d(TAG, "Truth Pressed");
                mCountdownTimer.cancel();

                //Display Answer
                mAnswerTextView.setText(R.string.correct_answer);

                //Add to Number of Correctly Answered Questions
                tallyAnswers();

                //Hide Answer
                mAnswerTextView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAnswerTextView.setVisibility(View.INVISIBLE);
                    }
                }, 1000);


            } else {
                Log.d(TAG, "Tale Pressed");
                mCountdownTimer.cancel();

                //Display Answer
                mAnswerTextView.setText(R.string.incorrect_answer);
                mAnswerTextView.setVisibility(View.VISIBLE);

                //Hide Answer
                mAnswerTextView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAnswerTextView.setVisibility(View.INVISIBLE);
                    }
                }, 1000);
            }

        } else if (categoryId == 3 ) {
            boolean answerIsTrue = mGeographyQuestionBankL2[mCurrentIndex].isAnswerTrue();
            if(userPressedTruth == answerIsTrue) {
                Log.d(TAG, "Truth Pressed");
                mCountdownTimer.cancel();

                //Display Answer
                mAnswerTextView.setText(R.string.correct_answer);

                //Add to Number of Correctly Answered Questions
                tallyAnswers();

                //Hide Answer
                mAnswerTextView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAnswerTextView.setVisibility(View.INVISIBLE);
                    }
                }, 1000);


            } else {
                Log.d(TAG, "Tale Pressed");
                mCountdownTimer.cancel();

                //Display Answer
                mAnswerTextView.setText(R.string.incorrect_answer);
                mAnswerTextView.setVisibility(View.VISIBLE);

                //Hide Answer
                mAnswerTextView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAnswerTextView.setVisibility(View.INVISIBLE);
                    }
                }, 1000);
            }

        } else if (categoryId == 4 ) {
            boolean answerIsTrue = mHistoryQuestionBankL2[mCurrentIndex].isAnswerTrue();
            if(userPressedTruth == answerIsTrue) {
                Log.d(TAG, "Truth Pressed");
                mCountdownTimer.cancel();

                //Display Answer
                mAnswerTextView.setText(R.string.correct_answer);

                //Add to Number of Correctly Answered Questions
                tallyAnswers();

                //Hide Answer
                mAnswerTextView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAnswerTextView.setVisibility(View.INVISIBLE);
                    }
                }, 1000);


            } else {
                Log.d(TAG, "Tale Pressed");
                mCountdownTimer.cancel();

                //Display Answer
                mAnswerTextView.setText(R.string.incorrect_answer);
                mAnswerTextView.setVisibility(View.VISIBLE);

                //Hide Answer
                mAnswerTextView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAnswerTextView.setVisibility(View.INVISIBLE);
                    }
                }, 1000);
            }

        } else if (categoryId == 5 ) {
            boolean answerIsTrue = mPeopleQuestionBankL2[mCurrentIndex].isAnswerTrue();

            if(userPressedTruth == answerIsTrue) {
                Log.d(TAG, "Truth Pressed");
                mCountdownTimer.cancel();

                //Display Answer
                mAnswerTextView.setText(R.string.correct_answer);

                //Add to Number of Correctly Answered Questions
                tallyAnswers();

                //Hide Answer
                mAnswerTextView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAnswerTextView.setVisibility(View.INVISIBLE);
                    }
                }, 1000);


            } else {
                Log.d(TAG, "Tale Pressed");
                mCountdownTimer.cancel();

                //Display Answer
                mAnswerTextView.setText(R.string.incorrect_answer);
                mAnswerTextView.setVisibility(View.VISIBLE);

                //Hide Answer
                mAnswerTextView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAnswerTextView.setVisibility(View.INVISIBLE);
                    }
                }, 1000);
            }
        } else if (categoryId == 6 ) {
            boolean answerIsTrue = mSportsQuestionBankL2[mCurrentIndex].isAnswerTrue();
            if(userPressedTruth == answerIsTrue) {
                Log.d(TAG, "Truth Pressed");
                mCountdownTimer.cancel();

                //Display Answer
                mAnswerTextView.setText(R.string.correct_answer);

                //Add to Number of Correctly Answered Questions
                tallyAnswers();

                //Hide Answer
                mAnswerTextView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAnswerTextView.setVisibility(View.INVISIBLE);
                    }
                }, 1000);


            } else {
                Log.d(TAG, "Tale Pressed");
                mCountdownTimer.cancel();

                //Display Answer
                mAnswerTextView.setText(R.string.incorrect_answer);
                mAnswerTextView.setVisibility(View.VISIBLE);

                //Hide Answer
                mAnswerTextView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAnswerTextView.setVisibility(View.INVISIBLE);
                    }
                }, 1000);
            }

        } else if (categoryId == 7 ) {
            boolean answerIsTrue = mFoodQuestionBankL2[mCurrentIndex].isAnswerTrue();
            if(userPressedTruth == answerIsTrue) {
                Log.d(TAG, "Truth Pressed");
                mCountdownTimer.cancel();

                //Display Answer
                mAnswerTextView.setText(R.string.correct_answer);

                //Add to Number of Correctly Answered Questions
                tallyAnswers();

                //Hide Answer
                mAnswerTextView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAnswerTextView.setVisibility(View.INVISIBLE);
                    }
                }, 1000);


            } else {
                Log.d(TAG, "Tale Pressed");
                mCountdownTimer.cancel();

                //Display Answer
                mAnswerTextView.setText(R.string.incorrect_answer);
                mAnswerTextView.setVisibility(View.VISIBLE);

                //Hide Answer
                mAnswerTextView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAnswerTextView.setVisibility(View.INVISIBLE);
                    }
                }, 1000);
            }


        }
    }


    //Try Again Alert Displayed When User Unsuccessfully Completes a Level
    private void levelCompleteAlert() {
        //Create Alert
        AlertDialog.Builder levelDialog = new AlertDialog.Builder(QuizActivity.this);
        //Set Dialog Message
        levelDialog.setMessage("Way to go Smarty Pants. Try your luck at another category.");
        //Set Alert Title
        levelDialog.setTitle("LEVEL" + mCurrentLevel + " COMPLETE");
        //Set Alert Message Icon
        levelDialog.setIcon(R.drawable.caution);
        //Set OnClick & Dismiss Buttons
        levelDialog.setPositiveButton("New Category", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Return to Category Screen
                finish();
                saveLevel();
            }
        });

        levelDialog.setNegativeButton("Quit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d(TAG, "Returning to Home Screen...");

                //Load Home Screen Activity
                Intent homeIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(homeIntent);
            }
        });

        levelDialog.show();
    }

    //Try Again Alert Displayed When User Unsuccessfully Completes a Level
    private void tryAgainAlert() {
        //Create Alert
        AlertDialog.Builder tryDialog = new AlertDialog.Builder(QuizActivity.this);
        //Set Dialog Message
        tryDialog.setMessage("You must get at least 7 correct answers to complete the level.");
        //Set Alert Title
        tryDialog.setTitle("TRY LEVEL AGAIN");
        //Set Alert Message Icon
        tryDialog.setIcon(R.drawable.caution);
        //Set OnClick & Dismiss Buttons
        tryDialog.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Return to Category Screen
                finish();
                saveLevel();
            }
        });

        tryDialog.setNegativeButton("Quit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d(TAG, "Returning to Home Screen...");

                //Load Home Screen Activity
                Intent homeIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(homeIntent);
            }
        });

        tryDialog.show();
    }

    //Quit Game Alert Displayed When User Presses "Quit Game" in Toolbar
    private void quitGameAlert() {
        //Create Alert
        AlertDialog.Builder quitDialog = new AlertDialog.Builder(QuizActivity.this);
        //Set Dialog Message
        quitDialog.setMessage("ALL current level progress will be lost if you continue.");
        //Set Alert Title
        quitDialog.setTitle("QUIT CURRENT GAME");
        //Set Alert Message Icon
        quitDialog.setIcon(R.drawable.caution);
        //Set OnClick & Dismiss Buttons
        quitDialog.setPositiveButton("Quit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Return to Category Screen
                finish();
                saveLevel();
            }
        });

        quitDialog.setNegativeButton("Resume", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d(TAG, "Returning to Game Quiz Screen...");

            }
        });

        quitDialog.show();
    }


    //Save Current Game Level
    private void saveLevel() {
        //Save level info if level was completed
        if(levelComplete == true) {
            preferenceLevel = getPreferences(PREFERENCE_MODE_PRIVATE);
            preferenceEditor = preferenceLevel.edit();
           // preferenceEditor.putInt("level", gameLevel);
            preferenceEditor.putInt("level", mCurrentLevel);

        } else {
            //Alert user level won't be saved since level wasn't completed
            //Create Alert
            AlertDialog.Builder saveDialog = new AlertDialog.Builder(QuizActivity.this);
            //Set Dialog Message
            saveDialog.setMessage("Your current level can't be saved until it has been completed.");
            //Set Alert Title
            saveDialog.setTitle("CURRENT LEVEL NOT SAVED");
            //Set Alert Message Icon
            saveDialog.setIcon(R.drawable.caution);
            //Set OnClick & Dismiss Buttons
            saveDialog.setPositiveButton("Quit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //Return to Category Screen
                    finish();
                }
            });

            saveDialog.setNegativeButton("Resume", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Log.d(TAG, "Returning to Game Quiz Screen...");

                }
            });

            saveDialog.show();
        }

    }

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        Log.d(TAG, "onBackPressed() called");
        quitGameAlert();
    }

    //HANDLE LIFE CYCLE METHODS
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");

        //Save current level progress

    }


}
