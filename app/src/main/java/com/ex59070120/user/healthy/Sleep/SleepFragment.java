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

}
