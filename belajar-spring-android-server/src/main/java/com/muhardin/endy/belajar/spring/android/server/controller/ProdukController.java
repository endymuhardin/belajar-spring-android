/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.muhardin.endy.belajar.spring.android.server.controller;

import com.muhardin.endy.belajar.spring.android.server.domain.Produk;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author endy
 */
@Controller
public class ProdukController {

    private List<Produk> daftarProduk = new ArrayList<Produk>();
    
    public ProdukController() {
        
        for(int i=1; i<10; i++){
            Produk p = new Produk();
            p.setId(String.valueOf(i));
            p.setKode("P-00"+i);
            p.setNama("Produk 00"+i);
            daftarProduk.add(p);
        }
    }
    
    @RequestMapping(value="/master/produk", method=RequestMethod.GET)
    @ResponseBody
    public List<Produk> semuaProduk(){
        return daftarProduk;
    }
}
