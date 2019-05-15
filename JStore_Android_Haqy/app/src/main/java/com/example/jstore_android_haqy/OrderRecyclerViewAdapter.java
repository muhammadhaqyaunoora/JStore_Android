package com.example.jstore_android_haqy;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class OrderRecyclerViewAdapter extends RecyclerView.Adapter<OrderRecyclerViewAdapter.OrderViewHolder> {
    private ArrayList<Invoice> invoices;
    private Context context;

    public OrderRecyclerViewAdapter(ArrayList<Invoice> invoices, Context context) {
        this.invoices = invoices;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_order_listitem, viewGroup, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder orderViewHolder, int i) {
        final Invoice invoice = invoices.get(i);
        StringBuilder text = new StringBuilder();
        text = text.append(invoices.get(i).getItem().get(0));
        if (invoice.getItem().size() >= 1) {
            for (int x = 1; x < invoice.getItem().size(); x++) {
                text.append(", ").append(invoice.getItem().get(x));
            }
        }
        orderViewHolder.tvItem.setText(text.toString());
        orderViewHolder.tvCategory.setText(invoice.getInvoiceStatus());
        Log.d("totalPrice", String.valueOf(invoice.getTotalPrice()));
        orderViewHolder.tvPrice.setText("Rp. " + (invoice.getTotalPrice()));

        orderViewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SelesaiPesananActivity.class);
                intent.putExtra("id", invoice.getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return invoices.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView tvItem, tvCategory, tvPrice;
        ConstraintLayout layout;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(R.id.tvItem);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvCategory = itemView.findViewById(R.id.tvPayment);
            layout = itemView.findViewById(R.id.layout);

        }
    }
}
