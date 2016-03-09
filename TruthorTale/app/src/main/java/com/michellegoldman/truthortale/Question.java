//Michelle Goldman
//APD2 1602
//February 27, 2016

package com.michellegoldman.truthortale;


import java.io.Serializable;

public class Question implements Serializable {

    private int mTextResId;
    private int mLevel;
    private boolean mAnswerTrue;
    private boolean mNewGame;

    public Question(int textResId, boolean answerTrue, int level) {
        mTextResId = textResId;
        mLevel = level;
        mAnswerTrue = answerTrue;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean mAnswerTrue) {
        this.mAnswerTrue = mAnswerTrue;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public int getLevel() {
        return mLevel;
    }

    public void setLevel(int mLevel) {
        this.mLevel = mLevel;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isNewGame() {
        return mNewGame;
    }

    public void setNewGame(boolean newGame) {
        this.mNewGame = newGame;
    }
}
