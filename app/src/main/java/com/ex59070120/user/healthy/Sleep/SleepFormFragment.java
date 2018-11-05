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

public class SleepFormFragment extends Fragment{
    //FirebaseFirestore _firestore;
    //FirebaseAuth _fbauth;
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
        //_firestore = FirebaseFirestore.getInstance();
        //_fbauth = FirebaseAuth.getInstance();
        helper = new DBHelper(getContext());

        initSleepFormBackBtn();

        Bundle bundle = getArguments();
        if (bundle != null) {
            Log.d("SLEEPFORM", "bundle is not null");
            EditText _date_sleep = (EditText) getView().findViewById(R.id.sleep_form_date);
            EditText _time_sleep = (EditText) getView().findViewById(R.id.sleep_form_time_sleep);
            EditText _time_wakeup = (EditText) getView().findViewById(R.id.sleep_form_time_wakeup);
            initUpdateBtn();
            ID = bundle.getInt("id");
            String date = bundle.getString("date");
            String sleepTime = bundle.getString("time_sleep");
            String wakeupTime = bundle.getString("time_wakeup");
            _date_sleep.setText(date);
            _time_sleep.setText(sleepTime);
            _time_wakeup.setText(wakeupTime);
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

    void initSleepFormSaveBtn() {
        Button _saveBtn = (Button) getView().findViewById(R.id.sleep_form_save_btn);
        _saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText _date_sleep = (EditText) getView().findViewById(R.id.sleep_form_date);
                EditText _time_sleep = (EditText) getView().findViewById(R.id.sleep_form_time_sleep);
                EditText _time_wakeup = (EditText) getView().findViewById(R.id.sleep_form_time_wakeup);
                String _dateStr = _date_sleep.getText().toString();
                String _sleepTimeStr = _time_sleep.getText().toString();
                String _wakeupTimeStr = _time_wakeup.getText().toString();

                Sleep sleep = new Sleep();
                sleep.setDate(_dateStr);
                sleep.setTime_sleep(_sleepTimeStr);
                sleep.setTime_wakeup(_wakeupTimeStr);
                helper.addSleep(sleep);
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new SleepFragment())
                        .addToBackStack(null).commit();
                Toast.makeText(
                        getActivity(),
                        "กรุณากรอกข้อมูลให้ครบถ้วน",
                        Toast.LENGTH_SHORT
                ).show();
                Log.d("SLEEP FORM", "HAVE EMPTY FIELD");
            }
        });
    }
        void initUpdateBtn() {
            Button _saveBtn_complete = (Button) getView().findViewById(R.id.sleep_form_save_btn);
            _saveBtn_complete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText _date_sleep = (EditText) getView().findViewById(R.id.sleep_form_date);
                    EditText _time_sleep = (EditText) getView().findViewById(R.id.sleep_form_time_sleep);
                    EditText _time_wakeup = (EditText) getView().findViewById(R.id.sleep_form_time_wakeup);
                    String _dateStr = _date_sleep.getText().toString();
                    String _sleepTimeStr = _time_sleep.getText().toString();
                    String _wakeupTimeStr = _time_wakeup.getText().toString();

                    Sleep sleep = new Sleep();
                    sleep.setID(String.valueOf(ID));
                    sleep.setDate(_dateStr);
                    sleep.setTime_wakeup(_wakeupTimeStr);
                    sleep.setTime_sleep(_sleepTimeStr);
                    helper.updateSleep(sleep);
                    getActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_view, new SleepFragment())
                            .addToBackStack(null).commit();
                    Toast.makeText(
                            getActivity(),
                            "บันทึกเสร็จสิ้น",
                            Toast.LENGTH_SHORT
                    ).show();
                    Log.d("SLEEP FORM","SAVE NEW SLEEP");
                }
            });

    }
}
