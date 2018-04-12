package com.csl.burakim.smartthermostat;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.csl.burakim.smartthermostat.models.Variable;

import java.util.ArrayList;

/**
 * Created by burak on 4/6/18.
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private ArrayList<Variable> mDataset;
    private Context context;

    public ArrayList<Variable> getmDataset() {
        return mDataset;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView variableName, dateTime, value, variableImage;

        public ViewHolder(View layoutView) {
            super(layoutView);
            variableName = layoutView.findViewById(R.id.variable_name_view);
            dateTime = layoutView.findViewById(R.id.date_time_view);
            value = layoutView.findViewById(R.id.variable_value);
            variableImage = layoutView.findViewById(R.id.variable_picture);

        }
    }

    public DataAdapter(Context context) {
        mDataset = new ArrayList<>();
        this.context = context;
    }


    public void addVariable(Variable variable)
    {
        getmDataset().add(variable);
    }

    public void removeVariable(Variable variable)
    {
        getmDataset().remove(variable);
    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sensor_view_row, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Variable variable = getmDataset().get(position);
        holder.variableName.setText(variable.getName());
        holder.dateTime.setText(variable.getDataTime());
        holder.variableImage.setBackground(variable.getPicture());
        holder.value.setText(Double.toString(variable.getValue()));
    }

    @Override
    public int getItemCount() {
        return getmDataset().size();
    }
}
