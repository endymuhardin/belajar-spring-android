package com.muhardin.endy.belajar.spring.android;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public class RequestDaftarProdukActivity extends ListActivity {
	private static String TAG = RequestDaftarProdukActivity.class.getSimpleName();
	private ProgressDialog progressDialog;
	private boolean destroyed = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
	protected void onDestroy() {
		super.onDestroy();
		this.destroyed = true;
	}
    
    public void showLoadingProgressDialog() {
		this.showProgressDialog("Loading. Please wait...");
	}

	public void showProgressDialog(CharSequence message) {
		if (this.progressDialog == null) {
			this.progressDialog = new ProgressDialog(this);
			this.progressDialog.setIndeterminate(true);
		}

		this.progressDialog.setMessage(message);
		this.progressDialog.show();
	}

	public void dismissProgressDialog() {
		if (this.progressDialog != null && !this.destroyed) {
			this.progressDialog.dismiss();
		}
	}
	
	@Override
	public void onStart() {
		super.onStart();

		new DownloadStatesTask().execute();
	}
	
	private void refreshStates(List<Produk> daftarProduk) {
		if (daftarProduk == null) {
			return;
		}

		ProdukListAdapter adapter = new ProdukListAdapter(this, daftarProduk);
		setListAdapter(adapter);
	}
	
	private class DownloadStatesTask extends AsyncTask<Void, Void, List<Produk>> {

		@Override
		protected void onPreExecute() {
			showLoadingProgressDialog();
		}

		@Override
		protected List<Produk> doInBackground(Void... params) {
			try {
				// The URL for making the GET request
				final String url = "http://localhost:10000/belajar-spring-android-server/master/produk";

				// Set the Accept header for "application/json"
				HttpHeaders requestHeaders = new HttpHeaders();
				List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
				acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
				requestHeaders.setAccept(acceptableMediaTypes);

				// Populate the headers in an HttpEntity object to use for the request
				HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

				// Create a new RestTemplate instance
				RestTemplate restTemplate = new RestTemplate();
				restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());

				// Perform the HTTP GET request
				ResponseEntity<Produk[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
						Produk[].class);

				// convert the array to a list and return it
				return Arrays.asList(responseEntity.getBody());
			} catch (Exception e) {
				Log.e(TAG, e.getMessage(), e);
			}

			return null;
		}

		@Override
		protected void onPostExecute(List<Produk> result) {
			dismissProgressDialog();
			refreshStates(result);
		}

	}

}
