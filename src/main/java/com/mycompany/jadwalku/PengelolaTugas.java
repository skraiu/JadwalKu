/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jadwalku;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

/**
 *
 * @author sekarayumaheswari
 */
public class PengelolaTugas {
    private ArrayList<Tugas> daftarTugas = new ArrayList<>();
    private int idBerikutnya = 1;

    public void tambahTugas(String mataKuliah, String namaTugas, LocalDate tenggat) {
        Tugas tugas = new Tugas(idBerikutnya, mataKuliah, namaTugas, tenggat);
        daftarTugas.add(tugas);
        idBerikutnya++;
    }

    public void tampilkanSemua() {
        if (daftarTugas.isEmpty()) {
            System.out.println("Belum ada tugas.");
            return;
        }
        for (Tugas t : daftarTugas) {
            System.out.println(t.tampilkanInfo());
        }
    }

    public boolean tandaiSelesai(int id) {
        for (Tugas t : daftarTugas) {
            if (t.getId() == id) {
                t.tandaiSelesai();
                return true;
            }
        }
        return false;
    }

    public boolean hapusTugas(int id) {
        for (int i = 0; i < daftarTugas.size(); i++) {
            if (daftarTugas.get(i).getId() == id) {
                daftarTugas.remove(i);
                return true;
            }
        }
        return false;
    }

    // Fitur unggulan: pengingat tenggat
    // Menampilkan tugas yang belum selesai, diurutkan dari tenggat terdekat.
    public void tampilkanPengingat() {
        ArrayList<Tugas> belumSelesai = new ArrayList<>();
        for (Tugas t : daftarTugas) {
            if (!t.isSelesai()) {
                belumSelesai.add(t);
            }
        }

        if (belumSelesai.isEmpty()) {
            System.out.println("Tidak ada tugas yang perlu dikerjakan. Mantap!");
            return;
        }

        belumSelesai.sort(Comparator.comparing(Tugas::getTenggat));
        for (Tugas t : belumSelesai) {
            System.out.println(t.tampilkanInfo());
        }
    }
}
