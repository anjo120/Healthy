package com.ex59070120.user.healthy.Weight;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.ex59070120.user.healthy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.*;

import java.util.ArrayList;

public class WeightFragment extends Fragment{

    FirebaseAuth _fbAuth;
    FirebaseFirestore _firestore;
    ArrayList<Weight> weights = new ArrayList<>();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        _firestore = FirebaseFirestore.getInstance();
        _fbAuth = FirebaseAuth.getInstance();

        getWeight();
        initAddWeight();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weight, container, false);
    }

    void initAddWeight(){
        Button _addWeightBtn = (Button) getView().findViewById(R.id.add_weight_btn);
        _addWeightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view,new WeightFormFragment())
                        .addToBackStack(null).commit();
                Log.d("WEIGHT","GO TO WEIGHT FORM");
            }
        });
    }

    private void getWeight(){
        String _uid = _fbAuth.getCurrentUser().getUid();
        _firestore.collection("mufitness").document(_uid).collection("weight")
                .orderBy("date", Query.Direction.ASCENDING).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        weights.clear();
                        if (task.isSuccessful()) {
                            int countLoop = 0;
                            int checkWeight = 0;
                            String status = "";
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String date = document.getData().get("date").toString();
                                int weight = Integer.parseInt(document.getData().get("weight").toString());
                                if (countLoop != 0) {
                                    if (checkWeight > weight) {
                                        status = "DOWN";
                                    } else if (checkWeight < weight) {
                                        status = "UP";
                                    } else if (checkWeight == weight) {
                                        status = "";
                                    }
                                    //String status = document.getData().get("status").toString();
                                }
                                checkWeight = weight;
                                weights.add(new Weight(date, weight, status));
                                ListView _weightList = (ListView) getView().findViewById(R.id.weight_list);
                                WeightItem _weightItem = new WeightItem(getActivity(), R.layout.fragment_weight_item, weights);
                                _weightList.setAdapter((ListAdapter) _weightItem);
                                countLoop += 1;
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
        }
    }
