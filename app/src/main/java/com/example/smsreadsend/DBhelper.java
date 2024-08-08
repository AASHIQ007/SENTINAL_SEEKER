package com.example.smsreadsend;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DBhelper extends SQLiteOpenHelper {
    public DBhelper(Context context) {
        super(context, "Register.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase mydb) {
        mydb.execSQL("CREATE TABLE users (Username TEXT, Email TEXT PRIMARY KEY, Password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase mydb, int oldVersion, int newVersion) {
        mydb.execSQL("DROP TABLE IF EXISTS users");
        onCreate(mydb);
    }

    public Boolean insertData(String Username, String Email, String Password) {
        SQLiteDatabase mydb = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Username", Username);
        contentValues.put("Email", Email);
        contentValues.put("Password", hashPassword(Password));
        long result = mydb.insert("users", null, contentValues);
        return result != -1;
    }

    public Boolean checkemail(String Email) {
        SQLiteDatabase mydb = this.getWritableDatabase();
        Cursor cursor = mydb.rawQuery("SELECT * FROM users WHERE Email = ?", new String[]{Email});
        return cursor.getCount() > 0;
    }

    public Boolean checkpassword(String Email, String Password) {
        SQLiteDatabase mydb = this.getWritableDatabase();
        Cursor cursor = mydb.rawQuery("SELECT * FROM users WHERE Email = ? AND Password = ?", new String[]{Email, hashPassword(Password)});
        return cursor.getCount() > 0;
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
