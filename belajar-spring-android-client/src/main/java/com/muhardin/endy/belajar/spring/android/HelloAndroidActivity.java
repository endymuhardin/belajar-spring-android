package com.muhardin.endy.belajar.spring.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class HelloAndroidActivity extends Activity {

    private static String TAG = HelloAndroidActivity.class.getSimpleName();

    /**
     * Called when the activity is first created.
     * @param savedInstanceState If the activity is being re-initialized after 
     * previously being shut down then this Bundle contains the data it most 
     * recently supplied in onSaveInstanceState(Bundle). <b>Note: Otherwise it is null.</b>
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");
        setContentView(R.layout.main);
    }
    
    public void requestDataProduk(View view){
    	Bundle bundle = new Bundle();
		bundle.putString("serverAddress", ((EditText) findViewById(R.id.txtServerAddress)).getText().toString());
		bundle.putString("serverPort", ((EditText) findViewById(R.id.txtServerPort)).getText().toString());
    	Intent requestDaftarProdukIntent = new Intent(this, RequestDaftarProdukActivity.class);
    	requestDaftarProdukIntent.putExtras(bundle);
		startActivity(requestDaftarProdukIntent);
    }
    

}

