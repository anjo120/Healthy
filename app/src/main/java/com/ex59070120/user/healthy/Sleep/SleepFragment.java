package com.ex59070120.user.healthy.Sleep;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.*;
import android.widget.*;

import com.ex59070120.user.healthy.DBHelper;
import com.ex59070120.user.healthy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SleepFragment extends Fragment {

    DBHelper helper;
    ArrayList<Sleep> sleeps = new ArrayList<>();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        super.onActivityCreated(savedInstanceState);
        initAddSleep();

        //Database
        helper = new DBHelper(getContext());
        sleeps = helper.getSleepList();

        if (!sleeps.isEmpty()) {
            SleepItem _sleepAdapter = new SleepItem(getActivity(), R.layout.fragment_sleep_item, sleeps);
            ListView _sleepList = (ListView) getView().findViewById(R.id.sleep_list);
            _sleepList.setAdapter(_sleepAdapter);
            _sleepList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int sleepSize = sleeps.size();
                    Bundle bundle = new Bundle();
                    int columnId = position*(-1)+sleepSize;
                    bundle.putInt("id", columnId);
                    bundle.putString("date", sleeps.get(position).getDate());
                    bundle.putString("sleep_time", sleeps.get(position).getTime_sleep());
                    bundle.putString("wakeup_time", sleeps.get(position).getTime_wakeup());
                    SleepFormFragment sleepForm = new SleepFormFragment();
                    sleepForm.setArguments(bundle);
                    getActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_view, sleepForm)
                            .addToBackStack(null)
                            .commit();
                }
            });
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sleep, container, false);
    }

    void initAddSleep(){
        Button _addSleepBtn = (Button) getView().findViewById(R.id.sleep_add_btn);
        _addSleepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view,new SleepFormFragment())
                        .addToBackStack(null).commit();
                Log.d("SLEEP","GO TO SLEEP FORM");
            }
        });
    }
}
