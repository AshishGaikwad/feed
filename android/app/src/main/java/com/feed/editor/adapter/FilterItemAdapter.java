//package com.feed.editor.adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.feed.R;
//import com.feed.editor.RecorderActivity;
//import com.feed.entity.FilterEntityParser;
//
//import java.util.List;
//
//import ai.deepar.ar.DeepAR;
//
//public class FilterItemAdapter extends RecyclerView.Adapter<FilterItemAdapter.ViewHolder> {
//
//    FilterEntityParser filterEntityParser;
//    Context context;
//    LayoutInflater inflater ;
//    RecorderActivity recorderActivity;
//
//    public FilterItemAdapter(Context pContext , FilterEntityParser pFilterEntityParser, RecorderActivity pRecorderActivity){
//        this.filterEntityParser = pFilterEntityParser;
//        this.context=pContext;
//        this.inflater=LayoutInflater.from(pContext);
//        this.recorderActivity = pRecorderActivity;
//
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = inflater.inflate(R.layout.filter_item_view,parent,false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//
//        FilterEntityParser.FilterEntity fit = filterEntityParser.getBody().get(position);
//
//        holder.filterName.setText(fit.getFilterName());
//        holder.filterImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                recorderActivity.setFilters(fit);
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return filterEntityParser.getBody().size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder{
//
//        TextView filterName;
//        ImageView filterImage;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            filterName= itemView.findViewById(R.id.filterName);
//            filterImage= itemView.findViewById(R.id.filterImage);
//
//
//
//        }
//    }
//
//    private String getFilters(String filterName) {
//        if (filterName.equals("none")) {
//            return null;
//        }
//        Toast.makeText(context,""+filterName,Toast.LENGTH_SHORT).show();
//
//        return "file:///android_asset/" + filterName;
//    }
//}
