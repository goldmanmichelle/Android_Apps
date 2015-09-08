//Michelle Goldman
//Java 2 Week 4 1504
//April 28, 2015

package com.michellegoldman.multiapp.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.michellegoldman.multiapp.R;

import java.util.ArrayList;

/**
 * Created by mgoldman on 4/29/15.
 */
public class SurveyAdapter extends BaseAdapter {

    public static final long CONSTANT_ID = 0x010101010L;
    private Context mContext;
    private ArrayList<Survey> mSurveyList;

    public SurveyAdapter(Context _context, ArrayList<Survey> _surveyArray) {
        mContext = _context;
        mSurveyList = _surveyArray;
    }

    @Override
    public int getCount() {
        return mSurveyList.size();
    }

    @Override
    public Object getItem(int position) {
        return mSurveyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return CONSTANT_ID + position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.survey_list_item, parent, false);
        }
        Survey survey = (Survey) getItem(position);
        TextView firstName = (TextView) convertView.findViewById(R.id.firstNameTV);
        TextView lastName = (TextView) convertView.findViewById(R.id.lastNameTV);
        TextView city = (TextView) convertView.findViewById(R.id.cityTV);
        TextView state = (TextView) convertView.findViewById(R.id.stateTV);
        firstName.setText(survey.getfName());
        lastName.setText(survey.getlName());
        city.setText(survey.getCity());
        state.setText(survey.getState());

        return convertView;
    }
}
