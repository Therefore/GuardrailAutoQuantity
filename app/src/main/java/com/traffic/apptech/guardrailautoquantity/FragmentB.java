package com.traffic.apptech.guardrailautoquantity;

import android.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Nathan on 5/24/2015.
 */
public class FragmentB extends Fragment{
    TextView text;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_b,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        text = (TextView) getActivity().findViewById(R.id.textView);
     }

    public void changeData(int i){
        Resources res = getResources();
        String[] conditionDetails = res.getStringArray(R.array.GuardrailDetails);
        text.setText(conditionDetails[i]);
    }
}
