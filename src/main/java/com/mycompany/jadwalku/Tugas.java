/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jadwalku;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;


/**
 *
 * @author sekarayumaheswari
 */
public class Tugas extends Kegiatan {
    private String namaTugas;
    private LocalDate tenggat;
    private boolean selesai;

    public Tugas(int id, String mataKuliah, String namaTugas, LocalDate tenggat) {
        super(id, mataKuliah); // memanggil constructor superclass
        this.namaTugas = namaTugas;
        this.tenggat = tenggat;
        this.selesai = false; // tugas baru otomatis belum selesai
    }

    // Getter dan Setter
    public String getNamaTugas() {
        return namaTugas;
    }

    public void setNamaTugas(String namaTugas) {
        this.namaTugas = namaTugas;
    }

    public LocalDate getTenggat() {
        return tenggat;
    }

    public void setTenggat(LocalDate tenggat) {
        this.tenggat = tenggat;
    }

    public boolean isSelesai() {
        return selesai;
    }

    public void setSelesai(boolean selesai) {
        this.selesai = selesai;
    }

    public void tandaiSelesai() {
        this.selesai = true;
    }

    // Menghitung sisa hari dari hari ini sampai tenggat (negatif = sudah lewat)
    public long hitungSisaHari() {
        return ChronoUnit.DAYS.between(LocalDate.now(), tenggat);
    }

    // Penanda tenggat untuk fitur pengingat
    public String getPenanda() {
        long sisa = hitungSisaHari();
        if (selesai) {
            return "SELESAI";
        } else if (sisa < 0) {
            return "TERLEWAT " + Math.abs(sisa) + " hari";
        } else if (sisa == 0) {
            return "HARI INI";
        } else if (sisa == 1) {
            return "BESOK";
        } else {
            return sisa + " hari lagi";
        }
    }

    @Override
    public String tampilkanInfo() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return "[" + getId() + "] " + namaTugas
                + " (" + getMataKuliah() + ")"
                + " | Tenggat: " + tenggat.format(format)
                + " | " + getPenanda();
    }
}
