package goodsfrom.app.omeryasar.atry;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import goodsfrom.app.omeryasar.atry.R;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import static android.Manifest.permission.CAMERA;

import goodsfrom.app.omeryasar.atry.data.CodeContract;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity  {

    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView scannerView;
    private static int camId = android.hardware.Camera.CameraInfo.CAMERA_FACING_BACK;
    private InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //scannerView = new ZXingScannerView(this);

        //Setting google ads
        MobileAds.initialize(this,"ca-app-pub-3405649653834115~7860269809");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3405649653834115/4084676320");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());


        setContentView(R.layout.activity_main);
        int currentApiVersion = Build.VERSION.SDK_INT;

        if(currentApiVersion >=  Build.VERSION_CODES.M)
        {
            if(checkPermission())
            {
            }
            else
            {
                requestPermission();
            }

            setTitle("GoodsFrom");
        }
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mInterstitialAd.isLoaded()){
                    mInterstitialAd.show();
                }
                else {
                    Log.d("Ad situation","Not Loaded yet");
                }
                scan();

            }
        });

        


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.info_button :
                Intent infoIntent = new Intent(MainActivity.this,InfoActivity.class);
                startActivity(infoIntent);

        }
        return super.onOptionsItemSelected(item);
    }

    public void scan(){
        Intent intent = new Intent(MainActivity.this,ScanActivity.class);
        startActivity(intent);
    }

    private boolean checkPermission()
    {
        return (ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission()
    {
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, REQUEST_CAMERA);
    }





}
