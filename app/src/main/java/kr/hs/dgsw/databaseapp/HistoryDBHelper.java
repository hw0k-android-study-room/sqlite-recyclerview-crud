package kr.hs.dgsw.databaseapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class HistoryDBHelper extends SQLiteOpenHelper {
    public HistoryDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void logAll() {
        List<UserBean> users = this.getAll();
        for (UserBean user: users) {
            Log.i("MAIN", "[" + user.getSequenceNumber() + "] " + user.getName());
        }
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String sql = "CREATE TABLE History (sequenceNumber integer primary key autoincrement, name text)";
        database.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        String sql = "DROP TABLE History";
        database.execSQL(sql);
        onCreate(database);
    }

    public long insert(UserBean user) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", user.getName());
        return database.insert("history", null, values);
    }

    public long delete(UserBean user) {
        SQLiteDatabase database = getWritableDatabase();

        return database.delete("history", "sequenceNumber = ?", new String[] { String.valueOf(user.getSequenceNumber()) });
    }

    public long deleteAll() {
        SQLiteDatabase database = getWritableDatabase();

        return database.delete("history", null, null);
    }

    public long update(UserBean user) {
        SQLiteDatabase database = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", user.getName());

        return database.update("history", values,"sequenceNumber = ?", new String[] { String.valueOf(user.getSequenceNumber()) });
    }

    public List<UserBean> getAll() {
        SQLiteDatabase database = getReadableDatabase();

        Cursor cursor = database.query("history", null, null, null, null, null, null);
        List<UserBean> result = new ArrayList<>();

        while (cursor.moveToNext()) {
            UserBean user = new UserBean();
            user.setSequenceNumber(cursor.getInt(cursor.getColumnIndex("sequenceNumber")));
            user.setName(cursor.getString(cursor.getColumnIndex("name")));
            result.add(user);
        }

        return result;
    }

    public UserBean getOne(int id) {
        SQLiteDatabase database = getReadableDatabase();

        Cursor cursor = database.query("history", null, "id = ?", new String[] { String.valueOf(id) }, null, null, null);

        if (cursor.moveToNext()) {
            UserBean user = new UserBean();
            user.setSequenceNumber(cursor.getInt(cursor.getColumnIndex("sequenceNumber")));
            user.setName(cursor.getString(cursor.getColumnIndex("name")));
            return user;
        }

        return null;
    }
}
