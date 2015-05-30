package com.traffic.apptech.guardrailautoquantity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Nathan on 5/24/2015.
 */
public class FragmentA extends Fragment implements OnItemClickListener{
    ListView list;
    Communicator communicator;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_a,container,false);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        communicator = (Communicator) getActivity();
        list = (ListView) getActivity().findViewById(R.id.list);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(),R.array.GuardrailTypes,android.R.layout.simple_list_item_1);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                communicator.respond(position);
            }
        });
        //list.setOnItemClickListener((AdapterView.OnItemClickListener) this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
