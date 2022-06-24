package com.feed.editor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.feed.R;

import java.util.List;

public class FilterItemAdapter extends RecyclerView.Adapter<FilterItemAdapter.ViewHolder> {

    List<String> filterNames;
    Context context;
    LayoutInflater inflater ;

    public FilterItemAdapter(Context pContext , List<String> pFilterName){
        this.filterNames = pFilterName;
        this.context=pContext;
        this.inflater=LayoutInflater.from(pContext);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.filter_item_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.filterName.setText(filterNames.get(position));
    }

    @Override
    public int getItemCount() {
        return filterNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView filterName;
        ImageView filterImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            filterName= itemView.findViewById(R.id.filterName);
            filterImage= itemView.findViewById(R.id.filterImage);


        }
    }
}
