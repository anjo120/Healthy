package com.ex59070120.user.healthy;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.ex59070120.user.healthy.Sleep.SleepFragment;
import com.ex59070120.user.healthy.Weight.WeightFragment;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MenuFragment extends Fragment {

    private FirebaseAuth fbAuth;
    ArrayList<String> menu_list = new ArrayList<>();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        fbAuth = FirebaseAuth.getInstance();
        menu_list.clear();
        menu_list.add("BMI");
        menu_list.add("Weight");
        menu_list.add("Sleep");
        menu_list.add("Sign out");

        ListView _menu = (ListView) getView().findViewById(R.id.menu_list);
        final ArrayAdapter<String> menuAdapter;
        menuAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1, menu_list);
        _menu.setAdapter((ListAdapter) menuAdapter);
        _menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (menu_list.get(position).equals("BMI")){
                    getActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_view, new BMIFragment())
                            .addToBackStack(null).commit();
                    Log.d("MENU","GO TO BMI");
                }else if (menu_list.get(position).equals("Weight")) {
                    getActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_view, new WeightFragment())
                            .addToBackStack(null).commit();
                    Log.d("MENU", "GO TO WEIGHT DAILY");
                }else if (menu_list.get(position).equals("Sign out")) {
                    fbAuth.signOut();
                    getActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_view, new LoginFragment())
                            .addToBackStack(null).commit();
                    Log.d("MENU", "GO OUT SYSTEM");
                }else if (menu_list.get(position).equals("Sleep")) {
                    getActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_view,new SleepFragment())
                            .addToBackStack(null).commit();
                    Log.d("MENU","GO TO SLEEP DAILY");
                }
            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }
}
