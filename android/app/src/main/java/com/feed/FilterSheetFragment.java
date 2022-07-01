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

import com.feed.editor.RecorderActivity;
import com.feed.editor.adapter.FilterItemAdapter;
import com.feed.entity.FilterEntityParser;
import com.feed.services.ApiCall;
import com.feed.util.ApiClient;
import com.feed.util.Filters;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

import ai.deepar.ar.DeepAR;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FilterSheetFragment extends BottomSheetDialogFragment {
    private FilterSheetFragment me;
    private RecorderActivity recorderActivity;

    public FilterSheetFragment(RecorderActivity pRecorderActivity) {
        this.me = this;

        this.recorderActivity = pRecorderActivity;
    }


//    public static FilterSheetFragment newInstance(DeepAR pDeepAR) {
//        FilterSheetFragment fragment = new FilterSheetFragment(pDeepAR);
//
//        return fragment;
//    }

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

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),3,GridLayoutManager.VERTICAL,false);
        filterGrid.setLayoutManager(gridLayoutManager);

        filterGrid.setNestedScrollingEnabled(true);

        ApiCall lApiCall = ApiClient.getClient().create(ApiCall.class);
        Call<FilterEntityParser> call = lApiCall.getFilters();
        call.enqueue(new Callback<FilterEntityParser>() {
            @Override
            public void onResponse(Call<FilterEntityParser> call, Response<FilterEntityParser> response) {
                FilterEntityParser.FilterEntity pFilter = new FilterEntityParser.FilterEntity();
                pFilter.setFilterName("none");
                response.body().getBody().add(0,pFilter);
                FilterItemAdapter adapter = new FilterItemAdapter(getContext(),response.body(),recorderActivity);
                filterGrid.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<FilterEntityParser> call, Throwable t) {
                t.printStackTrace();
            }
        });


        return view;
    }


}