/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jadwalku;

import java.util.ArrayList;

/**
 *
 * @author sekarayumaheswari
 */
public class PengelolaJadwal {
    private ArrayList<JadwalKuliah> daftarJadwal = new ArrayList<>();
    private int idBerikutnya = 1;

    public void tambahJadwal(String mataKuliah, String hari, String jamMulai,
                             String jamSelesai, String ruangan, String dosen) {
        JadwalKuliah jadwal = new JadwalKuliah(idBerikutnya, mataKuliah, hari,
                jamMulai, jamSelesai, ruangan, dosen);
        daftarJadwal.add(jadwal);
        idBerikutnya++;
    }

    public void tampilkanSemua() {
        if (daftarJadwal.isEmpty()) {
            System.out.println("Belum ada jadwal kuliah.");
            return;
        }
        for (JadwalKuliah j : daftarJadwal) {
            System.out.println(j.tampilkanInfo());
        }
    }

    public void tampilkanHariIni(String hari) {
        boolean ada = false;
        for (JadwalKuliah j : daftarJadwal) {
            if (j.getHari().equalsIgnoreCase(hari)) {
                System.out.println(j.tampilkanInfo());
                ada = true;
            }
        }
        if (!ada) {
            System.out.println("Tidak ada kuliah hari ini (" + hari + ").");
        }
    }

    public void cariJadwal(String kataKunci) {
        boolean ketemu = false;
        for (JadwalKuliah j : daftarJadwal) {
            if (j.getMataKuliah().toLowerCase().contains(kataKunci.toLowerCase())) {
                System.out.println(j.tampilkanInfo());
                ketemu = true;
            }
        }
        if (!ketemu) {
            System.out.println("Jadwal dengan kata kunci \"" + kataKunci + "\" tidak ditemukan.");
        }
    }

    public boolean hapusJadwal(int id) {
        for (int i = 0; i < daftarJadwal.size(); i++) {
            if (daftarJadwal.get(i).getId() == id) {
                daftarJadwal.remove(i);
                return true;
            }
        }
        return false;
    } 
}
