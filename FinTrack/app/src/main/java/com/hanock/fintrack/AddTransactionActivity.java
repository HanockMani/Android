package com.hanock.fintrack;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddTransactionActivity extends AppCompatActivity {

    private EditText etDescription, etAmount;
    private Spinner spinnerType, spinnerCategory;
    private Button btnSaveTransaction;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        // Initialize Views
        etDescription = findViewById(R.id.etDescription);
        etAmount = findViewById(R.id.etAmount);
        spinnerType = findViewById(R.id.spinnerType);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        btnSaveTransaction = findViewById(R.id.btnSaveTransaction);

        dbHelper = new DBHelper(this);

        // Set up Spinners
        setupSpinnerType();
        setupSpinnerCategory();

        // Set OnClick Listener
        btnSaveTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTransaction();
            }
        });
    }

    private void setupSpinnerType() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.transaction_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter);

        // Change category spinner based on type
        spinnerType.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                setupSpinnerCategoryBasedOnType(position);
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    private void setupSpinnerCategory() {
        // Initial setup based on default type selection
        int selectedTypePosition = spinnerType.getSelectedItemPosition();
        setupSpinnerCategoryBasedOnType(selectedTypePosition);
    }

    private void setupSpinnerCategoryBasedOnType(int typePosition) {
        String[] incomeCategories = {"Salary", "Business", "Investment", "Other"};
        String[] expenseCategories = {"Food", "Transport", "Entertainment", "Bills", "Shopping", "Other"};

        ArrayAdapter<String> categoryAdapter;
        if (typePosition == 0) { // Assuming "Income" is first
            categoryAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, incomeCategories);
        } else {
            categoryAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, expenseCategories);
        }
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(categoryAdapter);
    }

    private void saveTransaction() {
        String description = etDescription.getText().toString().trim();
        String amountStr = etAmount.getText().toString().trim();
        String type = spinnerType.getSelectedItem().toString();
        String category = spinnerCategory.getSelectedItem().toString();

        // Input validation
        if (description.isEmpty()) {
            etDescription.setError("Description is required");
            etDescription.requestFocus();
            return;
        }

        if (amountStr.isEmpty()) {
            etAmount.setError("Amount is required");
            etAmount.requestFocus();
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountStr);
        } catch (NumberFormatException e) {
            etAmount.setError("Enter a valid amount");
            etAmount.requestFocus();
            return;
        }

        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        Transaction transaction = new Transaction();
        transaction.setDescription(description);
        transaction.setAmount(amount);
        transaction.setType(type);
        transaction.setCategory(category);
        transaction.setDate(currentDate);

        long id = dbHelper.insertTransaction(transaction);
        if (id != -1) {
            Toast.makeText(this, "Transaction Added!", Toast.LENGTH_SHORT).show();
            finish(); // Close activity
        } else {
            Toast.makeText(this, "Error Adding Transaction", Toast.LENGTH_SHORT).show();
        }
    }
}
