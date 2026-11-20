/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jadwalku;

/**
 *
 * @author sekarayumaheswari
 */
public class Kegiatan {
    private int id;
    private String mataKuliah;

    public Kegiatan(int id, String mataKuliah) {
        this.id = id;
        this.mataKuliah = mataKuliah;
    }

    // Getter dan Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMataKuliah() {
        return mataKuliah;
    }

    public void setMataKuliah(String mataKuliah) {
        this.mataKuliah = mataKuliah;
    }

    // Akan di-override oleh subclass
    public String tampilkanInfo() {
        return "[" + id + "] " + mataKuliah;
    }

    // Dipakai ListView JavaFX untuk menampilkan teks objek
    @Override
    public String toString() {
        return tampilkanInfo();
    }
}
