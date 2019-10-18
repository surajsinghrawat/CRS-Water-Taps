package com.example.crswatertaps.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.crswatertaps.Adapter.MoreAdapter;
import com.example.crswatertaps.Model.MoreOption;
import com.example.crswatertaps.R;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class MoreOptionFragment extends Fragment {
    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.more_option_fragment,container,false);
        recyclerView=view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL,false));
        recyclerView.setAdapter(new MoreAdapter(createList(), getActivity()));

        return view;
    }

    private List<MoreOption> createList() {
        List<MoreOption> result = null;
        try {

            String title[] = {
                    "Profile",
                    "Favourites",
                    "About Us",
                    "Contact Us",
                    "App Settings",
                    "FeedBack",
                    "Share The App",
                    "Logout",
            };

            String description[] = {
                    "View Profile",
                    "View Favourites",
                    "About Us",
                    "Contact Us",
                    "App Settings",
                    "Give FeedBack",
                    "Share the app to world",
                    "Logout the application",
            };

            String code[] = {
                    "pro",
                    "fav",
                    "abo",
                    "con",
                    "app",
                    "fee",
                    "sha",
                    "Log",
            };

            int icons[] = {

                R.drawable.ic_dashboard_black_24dp,


            };

            result = new ArrayList<MoreOption>();
            for (int i = 0; i < title.length ; i++) {
                MoreOption moreOption = new MoreOption(title[i],description[i%description.length],icons[i%icons.length]);
                moreOption.setCode(code[i]);
                result.add(moreOption);
            }
        } catch (Exception e) {
            System.out.print(e);
        }
        return result;
    }

}
