package com.hanock.fintrack;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TransactionHistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerViewTransactions;
    private TransactionAdapter adapter;
    private DBHelper dbHelper;
    private List<Transaction> transactionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);

        recyclerViewTransactions = findViewById(R.id.recyclerViewTransactions);
        recyclerViewTransactions.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new DBHelper(this);
        transactionList = dbHelper.getAllTransactions();

        adapter = new TransactionAdapter(transactionList, dbHelper);
        recyclerViewTransactions.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh data in case of new transactions
        transactionList.clear();
        transactionList.addAll(dbHelper.getAllTransactions());
        adapter.notifyDataSetChanged();
    }
}