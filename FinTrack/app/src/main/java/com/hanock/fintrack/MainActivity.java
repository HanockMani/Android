package com.hanock.fintrack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView tvBalance, tvTotalIncome, tvTotalExpenses;
    private Button btnAddTransaction, btnViewTransactions, btnBudgetSettings;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Views
        tvBalance = findViewById(R.id.tvBalance);
        tvTotalIncome = findViewById(R.id.tvTotalIncome);
        tvTotalExpenses = findViewById(R.id.tvTotalExpenses);
        btnAddTransaction = findViewById(R.id.btnAddTransaction);
        btnViewTransactions = findViewById(R.id.btnViewTransactions);
        btnBudgetSettings = findViewById(R.id.btnBudgetSettings);

        dbHelper = new DBHelper(this);

        // Set OnClick Listeners
        btnAddTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddTransactionActivity.class));
            }
        });

        btnViewTransactions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TransactionHistoryActivity.class));
            }
        });

        btnBudgetSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BudgetSettingsActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateDashboard();
    }

    private void updateDashboard() {
        List<Transaction> transactions = dbHelper.getAllTransactions();
        double totalIncome = 0.0;
        double totalExpenses = 0.0;

        for (Transaction t : transactions) {
            if (t.getType().equalsIgnoreCase("Income")) {
                totalIncome += t.getAmount();
            } else {
                totalExpenses += t.getAmount();
            }
        }

        double balance = totalIncome - totalExpenses;

        tvTotalIncome.setText(String.format("Income: $%.2f", totalIncome));
        tvTotalExpenses.setText(String.format("Expenses: $%.2f", totalExpenses));
        tvBalance.setText(String.format("Balance: $%.2f", balance));

        // Optionally, check budgets
        checkBudgets(transactions);
    }

    private void checkBudgets(List<Transaction> transactions) {
        // Retrieve all budgets
        String[] categories = {"Food", "Transport", "Entertainment", "Bills", "Shopping"};
        for (String category : categories) {
            double totalSpent = 0.0;
            double budgetAmount = 0.0;

            for (Transaction t : transactions) {
                if (t.getCategory().equalsIgnoreCase(category) && t.getType().equalsIgnoreCase("Expense")) {
                    totalSpent += t.getAmount();
                }
            }

            Budget budget = dbHelper.getBudgetByCategory(category);
            if (budget != null) {
                budgetAmount = budget.getAmount();
                if (totalSpent >= budgetAmount) {
                    Toast.makeText(this, "You've exceeded your " + category + " budget!", Toast.LENGTH_LONG).show();
                } else if (totalSpent >= 0.8 * budgetAmount) {
                    Toast.makeText(this, "You're close to exceeding your " + category + " budget!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}
