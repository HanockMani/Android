package com.hanock.fintrack;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    private List<Transaction> transactionList;
    private DBHelper dbHelper;

    public TransactionAdapter(List<Transaction> transactionList, DBHelper dbHelper) {
        this.transactionList = transactionList;
        this.dbHelper = dbHelper;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_item, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        Transaction transaction = transactionList.get(position);
        holder.tvDescription.setText(transaction.getDescription());
        holder.tvCategory.setText(transaction.getCategory());
        holder.tvAmount.setText(String.format("$%.2f", transaction.getAmount()));
        holder.tvDate.setText(transaction.getDate());

        // Optionally, change text color based on type
        if (transaction.getType().equalsIgnoreCase("Income")) {
            holder.tvAmount.setTextColor(holder.itemView.getContext().getResources().getColor(android.R.color.holo_green_dark));
        } else {
            holder.tvAmount.setTextColor(holder.itemView.getContext().getResources().getColor(android.R.color.holo_red_dark));
        }

        holder.buttonDelete.setOnClickListener(v -> {
            deleteTransaction(position);
        });
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    public static class TransactionViewHolder extends RecyclerView.ViewHolder {

        public View buttonDelete;

        TextView tvDescription, tvCategory, tvAmount, tvDate;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            tvDate = itemView.findViewById(R.id.tvDate);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }

    public void deleteTransaction(int position) {
        Transaction transaction = transactionList.get(position);
        dbHelper.deleteTransaction(transaction.getId()); // Make sure your Transaction class has a getId() method
        transactionList.remove(position);
        notifyItemRemoved(position);
    }
}
