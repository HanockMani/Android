package com.hanock.fintrack;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "FinTrack.db";
    private static final int DATABASE_VERSION = 1;

    // Transactions table
    private static final String TABLE_TRANSACTIONS = "transactions";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_AMOUNT = "amount";
    private static final String COLUMN_TYPE = "type";
    private static final String COLUMN_CATEGORY = "category";
    private static final String COLUMN_DATE = "date";

    // Budgets table
    private static final String TABLE_BUDGETS = "budgets";
    private static final String COLUMN_BUDGET_ID = "id";
    private static final String COLUMN_BUDGET_CATEGORY = "category";
    private static final String COLUMN_BUDGET_AMOUNT = "amount";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TRANSACTIONS_TABLE = "CREATE TABLE " + TABLE_TRANSACTIONS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_DESCRIPTION + " TEXT,"
                + COLUMN_AMOUNT + " REAL,"
                + COLUMN_TYPE + " TEXT,"
                + COLUMN_CATEGORY + " TEXT,"
                + COLUMN_DATE + " TEXT"
                + ")";
        db.execSQL(CREATE_TRANSACTIONS_TABLE);

        String CREATE_BUDGETS_TABLE = "CREATE TABLE " + TABLE_BUDGETS + "("
                + COLUMN_BUDGET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_BUDGET_CATEGORY + " TEXT UNIQUE,"
                + COLUMN_BUDGET_AMOUNT + " REAL"
                + ")";
        db.execSQL(CREATE_BUDGETS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BUDGETS);
        onCreate(db);
    }

    // CRUD operations for Transactions

    public long insertTransaction(Transaction transaction) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DESCRIPTION, transaction.getDescription());
        values.put(COLUMN_AMOUNT, transaction.getAmount());
        values.put(COLUMN_TYPE, transaction.getType());
        values.put(COLUMN_CATEGORY, transaction.getCategory());
        values.put(COLUMN_DATE, transaction.getDate());

        long id = db.insert(TABLE_TRANSACTIONS, null, values);
        db.close();
        return id;
    }

    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_TRANSACTIONS + " ORDER BY " + COLUMN_DATE + " DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Transaction transaction = new Transaction();
                transaction.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                transaction.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)));
                transaction.setAmount(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_AMOUNT)));
                transaction.setType(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE)));
                transaction.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY)));
                transaction.setDate(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE)));
                transactions.add(transaction);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return transactions;
    }

    // CRUD operations for Budgets

    public long insertOrUpdateBudget(Budget budget) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_BUDGET_CATEGORY, budget.getCategory());
        values.put(COLUMN_BUDGET_AMOUNT, budget.getAmount());

        // Insert or replace to handle updates
        long id = db.insertWithOnConflict(TABLE_BUDGETS, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
        return id;
    }

    public Budget getBudgetByCategory(String category) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_BUDGETS + " WHERE " + COLUMN_BUDGET_CATEGORY + "=?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{category});

        if (cursor != null && cursor.moveToFirst()) {
            Budget budget = new Budget();
            budget.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BUDGET_ID)));
            budget.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BUDGET_CATEGORY)));
            budget.setAmount(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_BUDGET_AMOUNT)));
            cursor.close();
            db.close();
            return budget;
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return null;
    }

    public int deleteTransaction(long transactionId) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Delete the transaction where the ID matches
        int rowsDeleted = db.delete(TABLE_TRANSACTIONS, COLUMN_ID + " = ?", new String[]{String.valueOf(transactionId)});

        db.close();
        return rowsDeleted;  // Returns the number of rows deleted
    }

}
