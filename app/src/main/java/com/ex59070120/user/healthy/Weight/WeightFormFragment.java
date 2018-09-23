package com.ex59070120.user.healthy.Weight;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ex59070120.user.healthy.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class WeightFormFragment extends Fragment{

    FirebaseFirestore _firestore;
    FirebaseAuth _fbauth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weight_form, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        _firestore = FirebaseFirestore.getInstance();
        _fbauth = FirebaseAuth.getInstance();
        initBackBtn();
        initSaveBtn();

    }

    void initBackBtn(){
        Button _backBtn = (Button) getView().findViewById(R.id.weight_form_back_btn);
        _backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view,new WeightFragment())
                        .addToBackStack(null).commit();
                Log.d("WEIGHTFORM","BACK TO WEIGHTS");
            }
        });
    }

    void initSaveBtn(){
        Button _saveBtn = (Button) getView().findViewById(R.id.weight_form_save_btn);
        _saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText _date = (EditText) getView().findViewById(R.id.weight_form_date);
                EditText _weight = (EditText) getView().findViewById(R.id.weight_form_weight);
                String _dateStr = _date.getText().toString();
                String _weightStr = _weight.getText().toString();
                String _uid = _fbauth.getCurrentUser().getUid();

                if (!_dateStr.isEmpty() && !_weightStr.isEmpty()){
                    Weight _data = new Weight(
                            _dateStr, Integer.valueOf(_weightStr), ""
                    );
                    _firestore.collection("myfitness").document(_uid)
                            .collection("weight").document(_dateStr)
                            .set(_data).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            getActivity()
                                    .getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.main_view, new WeightFragment())
                                    .addToBackStack(null).commit();
                            Toast.makeText(
                                    getActivity(),
                                    "บันทึกเสร็จสิ้น",
                                    Toast.LENGTH_SHORT
                            ).show();
                            Log.d("WEIGHT FORM","SAVE NEW WEIGHT");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(
                                    getActivity(),
                                    "ERROR",
                                    Toast.LENGTH_SHORT
                            ).show();
                            Log.d("WEIGHT FORM","ERROR");
                        }
                    });
                }else {
                    Toast.makeText(
                            getActivity(),
                            "กรุณากรอกข้อมูลให้ครบถ้วน",
                            Toast.LENGTH_SHORT
                    ).show();
                    Log.d("WEIGHT FORM","HAVE EMPTY FIELD");
                }



            }
        });
    }
}
