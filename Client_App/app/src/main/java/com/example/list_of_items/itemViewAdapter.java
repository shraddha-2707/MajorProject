package com.example.list_of_items;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class itemViewAdapter extends RecyclerView.Adapter<itemViewAdapter.viewhplderr> {
    private Context context;
    private List<model_list> list;

    public itemViewAdapter(Context context, List<model_list> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public itemViewAdapter.viewhplderr onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_view, parent, false);
        viewhplderr holder = new viewhplderr(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull itemViewAdapter.viewhplderr holder, int position) {
        model_list model_list = list.get(position);
        List<String> vegiee = model_list.getVegiee_name();
        List<String> qtyy = model_list.getQty();

        holder.itemlist.setText("");
        for (int i = 0; i < vegiee.size(); i++) {
            String name = vegiee.get(i) + "\n";
            holder.itemlist.append(name);
        }
        holder.qty.setText("");
        for (int i = 0; i < qtyy.size(); i++) {
            String qt = qtyy.get(i) + "\n";
            holder.qty.append(qt);
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewhplderr extends RecyclerView.ViewHolder {
        TextView itemlist, qty;

        public viewhplderr(@NonNull View itemView) {
            super(itemView);
            itemlist = itemView.findViewById(R.id.itemname);
            qty = itemView.findViewById(R.id.qty);
        }
    }
}
