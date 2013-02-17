package com.muhardin.endy.belajar.spring.android;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ProdukListAdapter extends BaseAdapter {

	private List<Produk> daftarProduk;
	private final LayoutInflater layoutInflater;
	
	public ProdukListAdapter(Context context, List<Produk> daftarProduk) {
		this.daftarProduk = daftarProduk;
		this.layoutInflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		return this.daftarProduk != null ? daftarProduk.size() : 0;
	}

	@Override
	public Produk getItem(int position) {
		return this.daftarProduk.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = this.layoutInflater.inflate(R.layout.daftar_produk, parent, false);
		}

		Produk produk = getItem(position);
		if (produk != null) {
			TextView t = (TextView) convertView.findViewById(R.id.nama_produk);
			t.setText(produk.getNama());
		}

		return convertView;
	}

}
