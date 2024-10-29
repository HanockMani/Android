package com.hanock.fintrack;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BudgetSettingsActivity extends AppCompatActivity {

    private EditText etFoodBudget, etTransportBudget, etEntertainmentBudget, etBillsBudget, etShoppingBudget;
    private Button btnSaveBudgets;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_settings);

        // Initialize Views
        etFoodBudget = findViewById(R.id.etFoodBudget);
        etTransportBudget = findViewById(R.id.etTransportBudget);
        etEntertainmentBudget = findViewById(R.id.etEntertainmentBudget);
        etBillsBudget = findViewById(R.id.etBillsBudget);
        etShoppingBudget = findViewById(R.id.etShoppingBudget);
        btnSaveBudgets = findViewById(R.id.btnSaveBudgets);

        dbHelper = new DBHelper(this);

        // Load existing budgets if any
        loadExistingBudgets();

        // Set OnClick Listener
        btnSaveBudgets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveBudgets();
            }
        });
    }

    private void loadExistingBudgets() {
        String[] categories = {"Food", "Transport", "Entertainment", "Bills", "Shopping"};
        EditText[] budgetEdits = {etFoodBudget, etTransportBudget, etEntertainmentBudget, etBillsBudget, etShoppingBudget};

        for (int i = 0; i < categories.length; i++) {
            Budget budget = dbHelper.getBudgetByCategory(categories[i]);
            if (budget != null) {
                budgetEdits[i].setText(String.valueOf(budget.getAmount()));
            }
        }
    }

    private void saveBudgets() {
        String[] categories = {"Food", "Transport", "Entertainment", "Bills", "Shopping"};
        EditText[] budgetEdits = {etFoodBudget, etTransportBudget, etEntertainmentBudget, etBillsBudget, etShoppingBudget};

        for (int i = 0; i < categories.length; i++) {
            String amountStr = budgetEdits[i].getText().toString().trim();
            if (!amountStr.isEmpty()) {
                double amount;
                try {
                    amount = Double.parseDouble(amountStr);
                } catch (NumberFormatException e) {
                    budgetEdits[i].setError("Invalid amount");
                    budgetEdits[i].requestFocus();
                    return;
                }

                Budget budget = new Budget();
                budget.setCategory(categories[i]);
                budget.setAmount(amount);
                dbHelper.insertOrUpdateBudget(budget);
            }
        }

        Toast.makeText(this, "Budgets Saved!", Toast.LENGTH_SHORT).show();
        finish(); // Close activity
    }
}
