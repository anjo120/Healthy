package com.ex59070120.user.healthy.feature;

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
import android.widget.TextView;
import android.widget.Toast;

public class BMIFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bmi,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        calculateBtn();

    }

    double initCalculate(Double height,Double weight){

        Double heightInM = height/100;
        Double BMI = weight/height*height;
        return BMI;
    }

    void calculateBtn(){
        Button _calculate = (Button) getView().findViewById(R.id.calculate_btn);
        _calculate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                EditText _height = (EditText) getView().findViewById(R.id.bmi_height);
                EditText _weight = (EditText) getView().findViewById(R.id.bmi_weight);

                String _heightStr = _height.getText().toString();
                String _weightStr = _weight.getText().toString();



                if (_heightStr.isEmpty() || _weightStr.isEmpty()){
                    Toast.makeText(
                            getActivity(),
                            "กรุณาระบุข้อมูลให้ครบถ้วน",
                            Toast.LENGTH_SHORT
                    ).show();
                    Log.d("BMI", "FIELD IS EMPTY");
                }else {
                    Double _heightDouble = Double.parseDouble(_heightStr);
                    Double _weightDouble = Double.parseDouble(_weightStr);
                    Double BMI = initCalculate(_heightDouble, _weightDouble);
                    BMI = Math.round(BMI * 100d) / 100d;
                    TextView bmiText = (TextView) getView().findViewById(R.id.rs_bmi);
                    bmiText.setText(String.valueOf(BMI));
                    Log.d("BMI", "BMI VALUE");
                }
            }
        });
    }
}
