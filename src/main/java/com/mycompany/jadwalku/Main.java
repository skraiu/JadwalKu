/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.jadwalku;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Scanner;

/**
 *
 * @author sekarayumaheswari
 */
public class Main {

    private static final DateTimeFormatter FORMAT_TANGGAL = DateTimeFormatter.ofPattern("dd-MM-uuuu").withResolverStyle(ResolverStyle.STRICT);
    private static final String[] HARI_VALID = {"Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu", "Minggu"};

    public static void main(String[] args) {
        // Input dari user menggunakan Scanner
        Scanner input = new Scanner(System.in);

        // Instansiasi object
        PengelolaJadwal pengelolaJadwal = new PengelolaJadwal();
        PengelolaTugas pengelolaTugas = new PengelolaTugas();

        muatDataContoh(pengelolaJadwal, pengelolaTugas);

        System.out.println("=====================================");
        System.out.println("   JadwalKu - Jadwal & Pengingat Tugas");
        System.out.println("=====================================");

        int pilihan;
        do {
            System.out.println("\n===== MENU UTAMA =====");
            System.out.println("1. Lihat Jadwal Hari Ini");
            System.out.println("2. Kelola Jadwal Kuliah");
            System.out.println("3. Kelola Tugas");
            System.out.println("4. Pengingat Tugas");
            System.out.println("5. Keluar");
            pilihan = bacaAngka(input, "Pilih menu: ");

            switch (pilihan) {
                case 1:
                    System.out.println("\n--- Jadwal Hari Ini (" + hariIni() + ") ---");
                    pengelolaJadwal.tampilkanHariIni(hariIni());
                    break;
                case 2:
                    menuJadwal(input, pengelolaJadwal);
                    break;
                case 3:
                    menuTugas(input, pengelolaTugas);
                    break;
                case 4:
                    System.out.println("\n--- Pengingat Tugas ---");
                    pengelolaTugas.tampilkanPengingat();
                    break;
                case 5:
                    System.out.println("Terima kasih sudah memakai JadwalKu. Sampai jumpa!");
                    break;
                default:
                    System.out.println("Pilihan tidak valid, coba lagi.");
            }
        } while (pilihan != 5);

        input.close();
    }

    // ===== Submenu Jadwal Kuliah =====
    private static void menuJadwal(Scanner input, PengelolaJadwal pengelolaJadwal) {
        int pilihan;
        do {
            System.out.println("\n--- Kelola Jadwal Kuliah ---");
            System.out.println("1. Tambah jadwal");
            System.out.println("2. Lihat semua jadwal");
            System.out.println("3. Cari jadwal");
            System.out.println("4. Hapus jadwal");
            System.out.println("0. Kembali");
            pilihan = bacaAngka(input, "Pilih: ");

            switch (pilihan) {
                case 1:
                    String mataKuliah = bacaTeks(input, "Mata kuliah   : ");
                    String hari = bacaHari(input);
                    String jamMulai = bacaTeks(input, "Jam mulai     : ");
                    String jamSelesai = bacaTeks(input, "Jam selesai   : ");
                    String ruangan = bacaTeks(input, "Ruangan       : ");
                    String dosen = bacaTeks(input, "Dosen         : ");
                    pengelolaJadwal.tambahJadwal(mataKuliah, hari, jamMulai, jamSelesai, ruangan, dosen);
                    System.out.println("Jadwal berhasil ditambahkan.");
                    break;
                case 2:
                    pengelolaJadwal.tampilkanSemua();
                    break;
                case 3:
                    String kataKunci = bacaTeks(input, "Kata kunci mata kuliah: ");
                    pengelolaJadwal.cariJadwal(kataKunci);
                    break;
                case 4:
                    int idHapus = bacaAngka(input, "ID jadwal yang dihapus: ");
                    if (pengelolaJadwal.hapusJadwal(idHapus)) {
                        System.out.println("Jadwal berhasil dihapus.");
                    } else {
                        System.out.println("ID jadwal tidak ditemukan.");
                    }
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Pilihan tidak valid, coba lagi.");
            }
        } while (pilihan != 0);
    }

    // ===== Submenu Tugas =====
    private static void menuTugas(Scanner input, PengelolaTugas pengelolaTugas) {
        int pilihan;
        do {
            System.out.println("\n--- Kelola Tugas ---");
            System.out.println("1. Tambah tugas");
            System.out.println("2. Lihat semua tugas");
            System.out.println("3. Tandai tugas selesai");
            System.out.println("4. Hapus tugas");
            System.out.println("0. Kembali");
            pilihan = bacaAngka(input, "Pilih: ");

            switch (pilihan) {
                case 1:
                    String mataKuliah = bacaTeks(input, "Mata kuliah : ");
                    String namaTugas = bacaTeks(input, "Nama tugas   : ");
                    LocalDate tenggat = bacaTanggal(input, "Tenggat (dd-MM-yyyy): ");
                    pengelolaTugas.tambahTugas(mataKuliah, namaTugas, tenggat);
                    System.out.println("Tugas berhasil ditambahkan.");
                    break;
                case 2:
                    pengelolaTugas.tampilkanSemua();
                    break;
                case 3:
                    int idSelesai = bacaAngka(input, "ID tugas yang selesai: ");
                    if (pengelolaTugas.tandaiSelesai(idSelesai)) {
                        System.out.println("Tugas ditandai selesai.");
                    } else {
                        System.out.println("ID tugas tidak ditemukan.");
                    }
                    break;
                case 4:
                    int idHapus = bacaAngka(input, "ID tugas yang dihapus: ");
                    if (pengelolaTugas.hapusTugas(idHapus)) {
                        System.out.println("Tugas berhasil dihapus.");
                    } else {
                        System.out.println("ID tugas tidak ditemukan.");
                    }
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Pilihan tidak valid, coba lagi.");
            }
        } while (pilihan != 0);
    }

    // ===== Method bantu untuk input =====
    private static String bacaTeks(Scanner input, String pesan) {
        String teks;
        do {
            System.out.print(pesan);
            teks = input.nextLine().trim();
            if (teks.isEmpty()) {
                System.out.println("Input tidak boleh kosong.");
            }
        } while (teks.isEmpty());
        return teks;
    }

    private static int bacaAngka(Scanner input, String pesan) {
        while (true) {
            System.out.print(pesan);
            try {
                return Integer.parseInt(input.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Masukkan angka yang valid.");
            }
        }
    }

    private static String bacaHari(Scanner input) {
        while (true) {
            String hari = bacaTeks(input, "Hari (Senin-Minggu): ");
            for (String valid : HARI_VALID) {
                if (valid.equalsIgnoreCase(hari)) {
                    return valid;
                }
            }
            System.out.println("Nama hari tidak valid.");
        }
    }

    private static LocalDate bacaTanggal(Scanner input, String pesan) {
        while (true) {
            String teks = bacaTeks(input, pesan);
            try {
                return LocalDate.parse(teks, FORMAT_TANGGAL);
            } catch (DateTimeParseException e) {
                System.out.println("Format tanggal salah. Contoh: 15-11-2026");
            }
        }
    }

    private static String hariIni() {
        DayOfWeek hari = LocalDate.now().getDayOfWeek();
        switch (hari) {
            case MONDAY:
                return "Senin";
            case TUESDAY:
                return "Selasa";
            case WEDNESDAY:
                return "Rabu";
            case THURSDAY:
                return "Kamis";
            case FRIDAY:
                return "Jumat";
            case SATURDAY:
                return "Sabtu";
            default:
                return "Minggu";
        }
    }

    // Data contoh agar program langsung menampilkan sesuatu saat dijalankan
    private static void muatDataContoh(PengelolaJadwal pengelolaJadwal, PengelolaTugas pengelolaTugas) {
        String hari = hariIni();
        pengelolaJadwal.tambahJadwal("Pemrograman Berorientasi Objek", hari, "08:00", "10:30", "Lab R2", "Asisten Dosen PBO");
        pengelolaJadwal.tambahJadwal("Basis Data", "Selasa", "13:00", "15:30", "B2", "Dosen Basis Data");

        pengelolaTugas.tambahTugas("Pemrograman Berorientasi Objek", "Tugas 4 Program Awal", LocalDate.now().plusDays(1));
        pengelolaTugas.tambahTugas("Basis Data", "Latihan CRUD PHP", LocalDate.now().plusDays(4));
        pengelolaTugas.tambahTugas("Jaringan Komputer", "Laporan Router", LocalDate.now().minusDays(1));
    }
}




    

