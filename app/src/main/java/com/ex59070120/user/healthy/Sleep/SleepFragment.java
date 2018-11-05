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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class SleepFragment extends Fragment {

    //FirebaseAuth _fbAuth;
    //FirebaseFirestore _firestore;
    List<Sleep> sleeps = new ArrayList<>();
    DBHelper helper;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initAddSleep();
        helper = new DBHelper(getContext());
        //_firestore = FirebaseFirestore.getInstance();
        //_fbAuth = FirebaseAuth.getInstance();
        //sleeps.add(new Sleep("20 Sep 2018","23:00","7:00","9:00"));
        //getSleep();
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
                    bundle.putString("time_sleep", sleeps.get(position).getTime_sleep());
                    bundle.putString("time_wakeup", sleeps.get(position).getTime_wakeup());
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
        Button _addSleepBtn = (Button) getView().findViewById(R.id.add_sleep_btn);
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
/*
    private void getSleep(){
        String _uid = _fbAuth.getCurrentUser().getUid();
        _firestore.collection("myfitness")
                .document(_uid).collection("sleep")
                .orderBy("date", Query.Direction.ASCENDING).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        sleeps.clear();
                        if (task.isSuccessful()) {
                            int countLoop = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String date = document.getData().get("date").toString();
                                String time_sleep = document.getData().get("time_sleep").toString();
                                String time_wakeup = document.getData().get("time_wakeup").toString();
                                String time_dream = document.getData().get("time_dream").toString();
                                SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                                try {
                                    Date time_sleepDate = format.parse(time_sleep);
                                    Date time_wakeupDate = format.parse(time_wakeup);
                                    long diff = 0;
                                    if (time_sleepDate.getTime() > time_wakeupDate.getTime()) {
                                        diff = (86400000 - time_sleepDate.getTime())+time_wakeupDate.getTime();
                                    }else {
                                        diff = time_wakeupDate.getTime() - time_sleepDate.getTime();
                                    }
                                    long difHour = diff/(60*60*1000)%24;
                                    long difMin = diff/(60*1000)%60;
                                    Date time_dreamDate = format.parse(String.valueOf(difHour)+":"+String.valueOf(difMin));
                                    time_dream = format.format(time_dreamDate);

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                sleeps.add(new Sleep(date, time_sleep, time_wakeup, time_dream));
                                ListView _sleepList = (ListView) getView().findViewById(R.id.sleep_list);
                                SleepItem _sleepItem = new SleepItem(getActivity(), R.layout.fragment_sleep_item, sleeps);
                                _sleepList.setAdapter((ListAdapter) _sleepItem);
                                countLoop += 1;
                            }

                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
*/




}
