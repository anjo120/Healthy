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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SleepFormFragment extends Fragment{
    private DBHelper helper;
    private int ID;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sleep_form, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initSleepFormBackBtn();
        Bundle bundle = getArguments();

        if (bundle != null) {
            Log.d("SLEEPFORM", "bundle is not null");
            EditText _date = (EditText) getView().findViewById(R.id.sleep_form_date);
            EditText _sleepTime = (EditText) getView().findViewById(R.id.sleep_form_time_sleep);
            EditText _wakeupTime = (EditText) getView().findViewById(R.id.sleep_form_time_wakeup);

            initSleepFormUpdateBtn();

            ID = bundle.getInt("id");
            String date = bundle.getString("date");
            String sleepTime = bundle.getString("sleep_time");
            String wakeupTime = bundle.getString("wakeup_time");
            _date.setText(date);
            _sleepTime.setText(sleepTime);
            _wakeupTime.setText(wakeupTime);
        } else {
            initSleepFormSaveBtn();
        }

    }

    void initSleepFormBackBtn(){
        Button _bacSleepBtn = (Button) getView().findViewById(R.id.sleep_form_back_btn);
        _bacSleepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view,new SleepFragment())
                        .addToBackStack(null).commit();
                Log.d("SLEEP FORM","BACK TO SLEEPS");
            }
        });
    }
    void initSleepFormUpdateBtn(){
        Button _saveBtn = (Button) getView().findViewById(R.id.weight_form_save_btn);
        _saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText _date = (EditText) getView().findViewById(R.id.sleep_form_date);
                EditText _sleepTime = (EditText) getView().findViewById(R.id.sleep_form_time_sleep);
                EditText _wakeupTime = (EditText) getView().findViewById(R.id.sleep_form_time_wakeup);
                String _dateStr = _date.getText().toString();
                String _sleepTimeStr = _sleepTime.getText().toString();
                String _wakeupTimeStr = _wakeupTime.getText().toString();

                Sleep sleep = new Sleep();
                sleep.setId(String.valueOf(ID));
                sleep.setDate(_dateStr);
                sleep.setTime_wakeup(_wakeupTimeStr);
                sleep.setTime_sleep(_sleepTimeStr);
                helper.updateSleep(sleep);
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new SleepFragment())
                        .addToBackStack(null).commit();
                Log.d("SLEEP FORM","BACK TO SLEEPS");
            }
        });

    }
    void initSleepFormSaveBtn(){
        Button _saveBtn = (Button) getView().findViewById(R.id.weight_form_save_btn);
        _saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText _date = (EditText) getView().findViewById(R.id.sleep_form_date);
                EditText _sleepTime = (EditText) getView().findViewById(R.id.sleep_form_time_sleep);
                EditText _wakeupTime = (EditText) getView().findViewById(R.id.sleep_form_time_wakeup);
                String _dateStr = _date.getText().toString();
                String _sleepTimeStr = _sleepTime.getText().toString();
                String _wakeupTimeStr = _wakeupTime.getText().toString();

                Sleep sleep = new Sleep();
                sleep.setDate(_dateStr);
                sleep.setTime_wakeup(_wakeupTimeStr);
                sleep.setTime_sleep(_sleepTimeStr);
                helper.addSleep(sleep);
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view , new SleepFragment())
                        .addToBackStack(null).commit();
                Log.d("SLEEP FORM","BACK TO SLEEPS");
            }
        });
    }
}
