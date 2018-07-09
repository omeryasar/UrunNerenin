package goodsfrom.app.omeryasar.atry.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import goodsfrom.app.omeryasar.atry.data.CodeContract.CodeEntry;

public class CodeDbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "codes.db";
    private static final int DB_VERSION = 1;


    public CodeDbHelper(Context context){
        super(context,DB_NAME,null,1);

    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE_STRING = "CREATE TABLE " + CodeEntry.TABLE_NAME + " ("
                + CodeEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CodeEntry.Country_NAME + " TEXT NOT NULL, "
                + CodeEntry.COUNTRY_CODE + " INTEGER NOT NULL, "
                + CodeEntry.COUNTRY_FLAG_CODE + " INTEGER NOT NULL, "
                +CodeEntry.COUNTRY_TRANSLATION + " TEXT NOT NULL);";
        sqLiteDatabase.execSQL(CREATE_TABLE_STRING);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
