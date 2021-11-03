package com.khystudent.restaurantemployees;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomListAdapter extends RecyclerView.Adapter<CustomListAdapter.ViewHolder> {

    private ArrayList<String> listData;
    private final AppCompatActivity context;

    public CustomListAdapter(ArrayList<String> listData,AppCompatActivity context) {
        this.listData = listData;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.textView.setText(listData.get(position));

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.empData = (listData.get(holder.getAdapterPosition()));
                Log.d(TAG, "onClick: "+ MainActivity.empData);

                String empData = listData.get(holder.getAdapterPosition());

                ReaderWriter.deleteEmp(ReaderWriter.getFolder(context), ReaderWriter.nameSubstringMaker(empData));
                ReaderWriter.reduceEmployeeStaticData(ReaderWriter.salarySubstringMaker(empData));
                listData.remove(holder.getAdapterPosition());
                ReaderWriter.saveToShared(sharedPreferences);
            }
        });

    }


    @Override
    public int getItemCount() {
        return listData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout linearLayout;
        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.textView = (TextView) itemView.findViewById(R.id.text_view_item);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linear_layout);

        }
    }
}
