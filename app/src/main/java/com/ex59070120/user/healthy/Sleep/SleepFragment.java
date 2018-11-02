package com.ex59070120.user.healthy.Sleep;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.*;
import android.widget.*;

import com.ex59070120.user.healthy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SleepFragment extends Fragment {

    FirebaseAuth _fbAuth;
    FirebaseFirestore _firestore;
    ArrayList<Sleep> sleeps = new ArrayList<>();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        _firestore = FirebaseFirestore.getInstance();
        _fbAuth = FirebaseAuth.getInstance();

        sleeps.add(new Sleep("20 Sep 2018","23.00","7.00"));

        getSleep();
        initAddSleep();

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

    private void getSleep(){
        String _uid = _fbAuth.getCurrentUser().getUid();
        _firestore.collection("myfitness")
                .document(_uid).collection("sleep")
                .orderBy("date", Query.Direction.ASCENDING).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    }
                });
    }
}
