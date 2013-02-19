package com.muhardin.endy.belajar.spring.android;

import java.util.List;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.muhardin.endy.belajar.spring.android.domain.Produk;
import com.muhardin.endy.belajar.spring.android.service.KoneksiServer;

public class RequestDaftarProdukActivity extends ListActivity {
	private static String TAG = RequestDaftarProdukActivity.class.getSimpleName();
	private ProgressDialog progressDialog;
	private boolean destroyed = false;
	private String alamatServer, portServer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getIntent().getExtras();
        alamatServer = bundle.getString("serverAddress");
        portServer = bundle.getString("serverPort");
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

		new DownloadStatesTask(alamatServer, portServer).execute();
	}
	
	private void refreshStates(List<Produk> daftarProduk) {
		if (daftarProduk == null) {
			return;
		}

		ProdukListAdapter adapter = new ProdukListAdapter(this, daftarProduk);
		setListAdapter(adapter);
	}
	
	private class DownloadStatesTask extends AsyncTask<Void, Void, List<Produk>> {

		private String ipServer;
		private String portServer;
		
		public DownloadStatesTask(String ipServer, String portServer) {
			this.ipServer = ipServer;
			this.portServer = portServer;
		}

		@Override
		protected void onPreExecute() {
			showLoadingProgressDialog();
		}

		@Override
		protected List<Produk> doInBackground(Void... params) {
			try {
				KoneksiServer k = new KoneksiServer(ipServer, portServer);
				return k.ambilDataProduk();
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
