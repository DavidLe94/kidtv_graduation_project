package hl.kidtvchannel.haule.kidtvchannel.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Hai Son on 1/22/2018.
 * Modify by Hau Le on 1/23/2018.
 */

public class Sqlite extends SQLiteOpenHelper {

    public Sqlite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void createTable(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public Cursor GetData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }

    public void insertMovies(String playlistName, String playlistId,
                             String image, String createDate, String category, String derectors, String description){
        SQLiteDatabase database = getWritableDatabase();
        String insertSQL = "INSERT INTO Movies VALUES (null,'"+playlistName+"','"+playlistId+"'," +
                "'"+image+"', '"+createDate+"', '"+category+"', '"+derectors+"', '"+description+"')";
        database.execSQL(insertSQL);
    }
    public void  deleteMovies(String playlistName){
        SQLiteDatabase database = getWritableDatabase();
        String deleteSQL = "DELETE FROM Movies WHERE playlistName = '"+ playlistName +"'";
        database.execSQL(deleteSQL);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
