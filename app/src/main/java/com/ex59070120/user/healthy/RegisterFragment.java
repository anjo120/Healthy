package com.ex59070120.user.healthy;

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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterFragment extends Fragment{

    private FirebaseAuth fbAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button _registerBtn = (Button) getView().findViewById(R.id.register_btn);
        _registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

        fbAuth = FirebaseAuth.getInstance();

        EditText _register_user_id = (EditText) getActivity().findViewById(R.id.register_userid);
        EditText _register_password = (EditText) getActivity().findViewById(R.id.register_password);
        EditText _register_repassword = (EditText) getActivity().findViewById(R.id.register_repassword);

        String _regis_userIdStr = _register_user_id.getText().toString();
        String _regis_passwordStr = _register_password.getText().toString();
        String _regis_repasswordStr = _register_repassword.getText().toString();

        if (    _regis_userIdStr.isEmpty() ||
                _regis_passwordStr.isEmpty() ||
                _regis_repasswordStr.isEmpty()){
            Toast.makeText(
                    getActivity(),
                    "กรุณากรอกข้อมูลให้ครบถ้วน",
                    Toast.LENGTH_SHORT
            ).show();
            Log.d("REGISTER","HAVE FIELD IS EMPTY");
        }else if (_regis_passwordStr.length() < 6){
            Toast.makeText(
                    getActivity(),
                    "รหัสผ่านต้องมีความยาวตั้งแต่ 6 ตัวอักษรขึ้นไป",
                    Toast.LENGTH_SHORT
            ).show();
            Log.d("REGISTER","PASSWORD IS TOO SHORT");
        }else if (!_regis_repasswordStr.equals(_regis_passwordStr)){
            Toast.makeText(
                    getActivity(),
                    "รหัสผ่านไม่ตรงกัน",
                    Toast.LENGTH_SHORT
            ).show();
            Log.d("REGISTER","PASSWORD AND RE-PASSWORD ARE NOT MATCH");
        }else {
            fbAuth.createUserWithEmailAndPassword(_regis_userIdStr, _regis_passwordStr)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            sendVerifiedEmail(authResult.getUser());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(
                                    getActivity(),
                                    "อีเมลไม่ถูกต้อง",
                                    Toast.LENGTH_SHORT
                            ).show();
                            Log.d("REGISTER","WRONG EMAIL");
                        }
                    });

        }
    }
});


    }

    private void sendVerifiedEmail(FirebaseUser _user){
        _user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("REGISTER","GO TO LOGIN");
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view,new LoginFragment())
                        .addToBackStack(null)
                        .commit();
                Toast.makeText(
                        getActivity(),
                        "การสมัครเสร็จสิ้น",
                        Toast.LENGTH_SHORT
                ).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(
                        getActivity(),
                        "อีเมลไม่ถูกต้อง",
                        Toast.LENGTH_SHORT
                ).show();
                Log.d("REGISTER","WRONG EMAIL");
            }
        });
    }


}
