package com.feed;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.feed.editor.adapter.FilterItemAdapter;
import com.feed.util.Filters;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

import ai.deepar.ar.DeepAR;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FilterSheetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FilterSheetFragment extends BottomSheetDialogFragment {
    private FilterSheetFragment me;
    private DeepAR deepAR ;

    public FilterSheetFragment(DeepAR pDeepAR) {
        me = this;
        deepAR = pDeepAR;
    }


    public static FilterSheetFragment newInstance(DeepAR pDeepAR) {
        FilterSheetFragment fragment = new FilterSheetFragment(pDeepAR);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_filter_sheet, container, false);
//        ArrayAdapter adapter = new ArrayAdapter<String>(getContext(), R.layout.activity_listview, Filters.getAllFilters());
//        ListView listView = view.findViewById(R.id.filtersList);
//        listView.setAdapter(adapter);
//        listView.setNestedScrollingEnabled(true);
////
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                TextView lbl =  view.findViewById(R.id.label);
//                deepAR.switchEffect("filter", getFilterPath(lbl.getText().toString()));
//                me.dismiss();
//            }
//
//        });

        RecyclerView filterGrid = view.findViewById(R.id.filterGrid);
        FilterItemAdapter adapter = new FilterItemAdapter(getContext(),Filters.getAllFilters());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),3,GridLayoutManager.VERTICAL,false);
        filterGrid.setLayoutManager(gridLayoutManager);
        filterGrid.setAdapter(adapter);
        filterGrid.setNestedScrollingEnabled(true);

        return view;
    }

    private String getFilterPath(String filterName) {
        if (filterName.equals("none")) {
            return null;
        }

        Toast.makeText(getContext(),""+filterName,Toast.LENGTH_SHORT).show();

        return "file:///android_asset/" + filterName;


    }
}