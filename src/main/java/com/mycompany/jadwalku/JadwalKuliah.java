/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jadwalku;

/**
 *
 * @author sekarayumaheswari
 */
public class JadwalKuliah extends Kegiatan {
    private String hari;
    private String jamMulai;
    private String jamSelesai;
    private String ruangan;
    private String dosen;

    public JadwalKuliah(int id, String mataKuliah, String hari, String jamMulai,
                        String jamSelesai, String ruangan, String dosen) {
        super(id, mataKuliah); 
        this.hari = hari;
        this.jamMulai = jamMulai;
        this.jamSelesai = jamSelesai;
        this.ruangan = ruangan;
        this.dosen = dosen;
    }

    // Getter dan Setter
    public String getHari() {
        return hari;
    }

    public void setHari(String hari) {
        this.hari = hari;
    }

    public String getJamMulai() {
        return jamMulai;
    }

    public void setJamMulai(String jamMulai) {
        this.jamMulai = jamMulai;
    }

    public String getJamSelesai() {
        return jamSelesai;
    }

    public void setJamSelesai(String jamSelesai) {
        this.jamSelesai = jamSelesai;
    }

    public String getRuangan() {
        return ruangan;
    }

    public void setRuangan(String ruangan) {
        this.ruangan = ruangan;
    }

    public String getDosen() {
        return dosen;
    }

    public void setDosen(String dosen) {
        this.dosen = dosen;
    }

    @Override
    public String tampilkanInfo() {
        return "[" + getId() + "] " + getMataKuliah()
                + " | " + hari + ", " + jamMulai + "-" + jamSelesai
                + " | Ruang " + ruangan
                + " | " + dosen;
    }
}
