package goodsfrom.app.omeryasar.atry.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import goodsfrom.app.omeryasar.atry.MyDataBase;

public class CodeProvider extends ContentProvider {

    //Helps database process
    //private CodeDbHelper codeDbHelper;
    private MyDataBase myDataBase;

    //Unique codes for different query cases
    private static final int _ID_CODE = 100;
    private static final int CODES = 101;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);


    //add uri types to uriMatcher
    static {
        sUriMatcher.addURI(CodeContract.CONTENT_AUTHORITY,CodeContract.PATH,CODES);
        sUriMatcher.addURI(CodeContract.CONTENT_AUTHORITY,CodeContract.PATH+"/#",_ID_CODE);
    }
    @Override
    public boolean onCreate() {
        //codeDbHelper = new CodeDbHelper(getContext());
        myDataBase = new MyDataBase(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        SQLiteDatabase db = myDataBase.getReadableDatabase();
        Cursor cursor;
        int match = sUriMatcher.match(uri);
        switch (match) {
            case CODES :
                cursor = db.query(CodeContract.CodeEntry.TABLE_NAME,strings,s,strings1,null,null,null);
                break;

            case _ID_CODE :
                s = CodeContract.CodeEntry._ID + "=?";
                strings1 = new String[] {String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(CodeContract.CodeEntry.TABLE_NAME,strings,s,strings1,null,null,null);
                break;

            default:
                throw new IllegalArgumentException("Can't resolve uri "+uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case CODES:
                return CodeContract.CodeEntry.CONTENT_LIST_TYPE;
            case _ID_CODE:
                return CodeContract.CodeEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match){
            case CODES:
                return insertCode(uri,contentValues);
            default:
                throw new IllegalArgumentException("Addition not supported to "+uri);

        }
    }

    private Uri insertCode(Uri uri, ContentValues contentValues){
        SQLiteDatabase database = myDataBase.getWritableDatabase();
        String cName = contentValues.getAsString(CodeContract.CodeEntry.Country_NAME);
        Integer cNo = contentValues.getAsInteger(CodeContract.CodeEntry.COUNTRY_CODE);

        if(cName == null){
            throw new IllegalArgumentException("Country name can't be null");
        }
        if(cNo<0 || cNo == null){
            throw new IllegalArgumentException("Country code can't be null");
        }
        long id = database.insert(CodeContract.CodeEntry.TABLE_NAME,null,contentValues);

        if(id == -1){
            Log.e(CodeProvider.class.getSimpleName(),"Failed to insert pet for"+uri);
            return null;

        }
        return ContentUris.withAppendedId(uri,id);


    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
