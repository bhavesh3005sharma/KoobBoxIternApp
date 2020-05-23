package com.example.koobboxrecyclerview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Adapter_ extends RecyclerView.Adapter<Adapter_.viewHolder> implements Filterable {
    ArrayList<ModelElement> list;
    ArrayList<ModelElement> filteredList;
    Context context;

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_file, parent, false);
        return new viewHolder(view);
    }

    public Adapter_(ArrayList<ModelElement> list, Context context) {
        this.filteredList=list;
        this.list = list;
        this.context = context;
    }

    void sort(int a) {
        if(a==0) {
            Collections.sort(filteredList, new java.util.Comparator<ModelElement>() {
                @Override
                public int compare(ModelElement o1, ModelElement o2) {
                    return (o1.getPriority()) > (o2.priority) ? -1 : (o1.getPriority()) > (o2.priority) ? 1 : 0;
                }
            });
        }
        else if (a==1){
            Collections.sort(filteredList, new Comparator<ModelElement>() {
                @Override
                public int compare(ModelElement o1, ModelElement o2) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date date1 = null,date2 = null;
                    try {
                         date1= sdf.parse(o1.getDate());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    try {
                        date2 = sdf.parse(o2.getDate());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return  (date1).compareTo(date2);
                }
            });
                Collections.reverse(filteredList);
        }
        else if (a==2){
            Collections.sort(filteredList, new Comparator<ModelElement>() {
                @Override
                public int compare(ModelElement o1, ModelElement o2) {
                    return (o1.getTitle()).compareTo(o2.getTitle());
                }
            });
        }
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        ModelElement model = filteredList.get(position);
        holder.title.setText(model.getTitle());
        holder.subTitle.setText(model.getSubTitle());
        holder.date.setText(model.getDate());
        holder.type.setText(model.getType());
        holder.checkboxSelection.setSelected(model.isSelected);

        if(model.getPriority()==0)
            holder.priority.setText("Low");
        else if(model.getPriority()==1)
            holder.priority.setText("Medium");
        else if(model.getPriority()==2)
            holder.priority.setText("High");
    }

    @Override
    public int getItemCount() {return (filteredList!= null? filteredList.size() : 0); }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    filteredList = list;
                } else if(charString.equals("Animal") || charString.equals("Bird")){
                    ArrayList<ModelElement> newList = new ArrayList<>();
                    for (ModelElement row : list) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getType().toLowerCase().contains(charString.toLowerCase())) {
                            newList.add(row);
                        }
                    }

                    filteredList = newList;
                }else{
                    String Dates[] = charString.split("#DATE_CHECK#");

                    ArrayList<ModelElement> newList = new ArrayList<>();
                    for (ModelElement row : list) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date date1 = null,date2 = null,date=null;
                        try {
                            date1= sdf.parse(Dates[0]);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        try {
                            date2 = sdf.parse(Dates[1]);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        try {
                            date = sdf.parse(row.getDate());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        int x= date.compareTo(date1);
                        int y = date.compareTo(date2);
                        Log.d("Comparision",date1+"pp"+date2+"pp"+x+" * "+y);
                        if (x!=(-1) && y!=(1)) {
                            newList.add(row);
                        }
                    }

                    filteredList = newList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredList = (ArrayList<ModelElement>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.title) TextView title;
        @BindView(R.id.subTitle) TextView subTitle;
        @BindView(R.id.date) TextView date;
        @BindView(R.id.priority) TextView priority;
        @BindView(R.id.type) TextView type;
        @BindView(R.id.checkboxSelection) TextView checkboxSelection;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
