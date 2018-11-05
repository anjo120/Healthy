package com.ex59070120.user.healthy.Sleep;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.*;
import android.widget.*;
import com.ex59070120.user.healthy.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SleepFormFragment extends Fragment{
    FirebaseFirestore _firestore;
    FirebaseAuth _fbauth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sleep_form, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        _firestore = FirebaseFirestore.getInstance();
        _fbauth = FirebaseAuth.getInstance();
        initSleepFormBackBtn();
        initSleepFormSaveBtn();

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

    void initSleepFormSaveBtn(){
        Button _saveBtn = (Button) getView().findViewById(R.id.weight_form_save_btn);
        _saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText _date_sleep = (EditText) getView().findViewById(R.id.sleep_form_date);
                EditText _time_sleep = (EditText) getView().findViewById(R.id.sleep_form_time_sleep);
                EditText _time_wakeup = (EditText) getView().findViewById(R.id.sleep_form_time_wakeup);
                String _dateStr = _date_sleep.getText().toString();
                String _sleepTimeStr = _time_sleep.getText().toString();
                String _wakeupTimeStr = _time_wakeup.getText().toString();
                String _uid = _fbauth.getCurrentUser().getUid();

                if (!_dateStr.isEmpty() && !_sleepTimeStr.isEmpty() && !_wakeupTimeStr.isEmpty()){
                    Sleep _data = new Sleep(
                            _dateStr,_sleepTimeStr, _wakeupTimeStr, ""
                    );
                    _firestore.collection("myfitness").document(_uid)
                            .collection("sleep").document(_dateStr)
                            .set(_data).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
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
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(
                                    getActivity(),
                                    "ERROR",
                                    Toast.LENGTH_SHORT
                            ).show();
                            Log.d("SLEEP FORM","ERROR");
                        }
                    });
                }else {
                    Toast.makeText(
                            getActivity(),
                            "กรุณากรอกข้อมูลให้ครบถ้วน",
                            Toast.LENGTH_SHORT
                    ).show();
                    Log.d("SLEEP FORM","HAVE EMPTY FIELD");
                }



            }
        });
    }
}
