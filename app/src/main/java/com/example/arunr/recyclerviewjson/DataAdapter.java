package com.example.arunr.recyclerviewjson;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by arun.r on 12-02-2018.
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    // We define a list from the DataList java class
    private List<DataList> dataLists;
    private Context context;

    public DataAdapter(List<DataList> dataLists, Context context) {

        // Generate constructors to initialize the list and Context objects
        this.dataLists = dataLists;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // define the view objects
        public TextView name;
        public TextView genre;
        public ImageView images;
        public LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            // initialise the view objects
            name = itemView.findViewById(R.id.name);
            genre = itemView.findViewById(R.id.genre);
            images = itemView.findViewById(R.id.images);
            linearLayout = itemView.findViewById(R.id.linearLayout);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // this method will be called whenever our ViewHolder is created
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.data_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // this method will bind the data to the viewHolder from where it'll be shown to other views
        final DataList dataList = dataLists.get(position);
        holder.name.setText(dataList.getName());
        holder.genre.setText(dataList.getGenre());
        Picasso.with(context)
                .load(getImageUrl(dataList.getImageList().get(dataList.getId())))
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.images);
    }

    @Override
    public int getItemCount() {
        return dataLists.size();
    }

    public String getImageUrl(JSONArray array) {
        String url = "";
        if (array != null) {
            for (int i = 0; i < array.length(); i++) {
                try {
                    JSONObject jsonObject = array.getJSONObject(i);
                    url = jsonObject.getString("image");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            }
        }

        return url;
    }
}
