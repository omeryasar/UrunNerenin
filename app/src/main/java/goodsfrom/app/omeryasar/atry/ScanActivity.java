package goodsfrom.app.omeryasar.atry;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import goodsfrom.app.omeryasar.atry.R;

import goodsfrom.app.omeryasar.atry.data.CodeContract;
import goodsfrom.app.omeryasar.atry.data.CodeDbHelper;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    ZXingScannerView scannerView;
    private static final int REQUEST_CAMERA = 1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView = new ZXingScannerView(this);
        scan1();
    }


    public void scan1(){
        setContentView(scannerView);
        scannerView.startCamera();
        scannerView.resumeCameraPreview(ScanActivity.this);
    }

    public void onDestroy() {
        super.onDestroy();
        scannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        //CodeDbHelper codeDbHelper = new CodeDbHelper(this);
        FlagCodes flagCodes = new FlagCodes(); //have each flag image resource id
        //SQLiteDatabase db = codeDbHelper.getReadableDatabase();
        String countryName;
        int flagID = R.drawable.unknown_flag;
        final String myResultLength = result.getText();

        final String seekingValue2 = myResultLength.substring(0,2); //Getting country codes
        final String seekingValue3 = myResultLength.substring(0,3);
        String[] projection = {  //Get columns from db
                CodeContract.CodeEntry._ID,
                CodeContract.CodeEntry.Country_NAME,
                CodeContract.CodeEntry.COUNTRY_CODE,
                CodeContract.CodeEntry.COUNTRY_TRANSLATION,
                CodeContract.CodeEntry.COUNTRY_FLAG_CODE};
        String selection = CodeContract.CodeEntry.COUNTRY_CODE+"=?"; //Select according to country code
        String[] selectionArgs = new String[]{String.valueOf(seekingValue3)};
        Log.d("Country Code: "  ,String.valueOf(seekingValue3));
        Uri uri = CodeContract.CodeEntry.CONTENT_URI;
        Cursor cursor = getContentResolver().query(uri,projection,selection,selectionArgs,null);//Looking first three digit
        if(cursor.getCount() == 0){//Looking first two digit
            selectionArgs = new String[] {String.valueOf(seekingValue2)};
            cursor = getContentResolver().query(uri,projection,selection,selectionArgs,null);

        }

        if (cursor.getCount() == 0){

            countryName =  getString(R.string.unknown);
        }
        else{
            if(flagCodes.flagDict.get(new Integer(Integer.valueOf(seekingValue2)))!=null)
                flagID = flagCodes.flagDict.get(Integer.valueOf(seekingValue2));
            else
                flagID = flagCodes.flagDict.get(Integer.valueOf(seekingValue3));
            int cColumn = cursor.getColumnIndex(CodeContract.CodeEntry.Country_NAME);
            int cColumnTr = cursor.getColumnIndex(CodeContract.CodeEntry.COUNTRY_TRANSLATION);
            cursor.moveToNext();
            if(getString(R.string.unknown).equals("Unknown"))
                countryName = cursor.getString(cColumn);
            else
                countryName = cursor.getString(cColumnTr);

        }

        cursor.close(); // close the cursor
        ImageView imageView = new ImageView(this);
        if(cursor.getCount() != 0) {
            int flagColumn = cursor.getColumnIndex(CodeContract.CodeEntry.COUNTRY_FLAG_CODE);
            //flagID = cursor.getInt(flagColumn);
            imageView.setImageResource(flagID);

        }
        else {
            imageView.setImageResource(R.drawable.unknown_flag);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.scan_result));
        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                scannerView.resumeCameraPreview(ScanActivity.this);
                scannerView.stopCamera();
                //setContentView(R.layout.activity_main);
                finish();
            }
        });
        builder.setNeutralButton(R.string.continuee, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                scannerView.resumeCameraPreview(ScanActivity.this);
            }
        })
                .setView(imageView);
        builder.setMessage(getString(R.string.barcode_numer)+result.getText()+"\n\n"+getString(R.string.product_country)+countryName+"\n");
        AlertDialog alert1 = builder.create();
        alert1.show();

    }
}
