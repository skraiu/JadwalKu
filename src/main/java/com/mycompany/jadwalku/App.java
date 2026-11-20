package com.mycompany.jadwalku;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * 
 * @author sekarayumaheswari
 * 
 */
public class App extends Application {

    private static final DateTimeFormatter FORMAT_TANGGAL =
            DateTimeFormatter.ofPattern("dd-MM-uuuu").withResolverStyle(ResolverStyle.STRICT);

    // Object dari class pondasi program terminal
    private final PengelolaJadwal pengelolaJadwal = new PengelolaJadwal();
    private final PengelolaTugas pengelolaTugas = new PengelolaTugas();

    // Komponen yang diperbarui setelah data berubah
    private final ListView<Tugas> listTugas = new ListView<>();
    private final ListView<JadwalKuliah> listJadwal = new ListView<>();
    private final Label labelPengingat = new Label();

    @Override
    public void start(Stage stage) {

        TabPane tabPane = new TabPane(buatTabTugas(), buatTabJadwal());

        Scene scene = new Scene(tabPane, 820, 640);
        stage.setTitle("JadwalKu - Jadwal Kuliah dan Pengingat Tugas");
        stage.setScene(scene);
        stage.show();
    }

    // ===== TAHAP 1: Desain antarmuka tab Tugas =====
    private Tab buatTabTugas() {
        Label judul = new Label("Kelola Tugas");
        judul.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        labelPengingat.setStyle("-fx-text-fill: #b00020; -fx-font-weight: bold;");

        TextField tfMataKuliah = new TextField();
        tfMataKuliah.setPromptText("contoh: Basis Data");
        TextField tfNamaTugas = new TextField();
        tfNamaTugas.setPromptText("contoh: Laporan Proyek Akhir");
        TextField tfTenggat = new TextField();
        tfTenggat.setPromptText("dd-MM-yyyy, contoh: 20-11-2026");

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(8);
        form.add(new Label("Mata kuliah"), 0, 0);
        form.add(tfMataKuliah, 1, 0);
        form.add(new Label("Nama tugas"), 0, 1);
        form.add(tfNamaTugas, 1, 1);
        form.add(new Label("Tenggat"), 0, 2);
        form.add(tfTenggat, 1, 2);
        tfMataKuliah.setPrefWidth(320);
        tfNamaTugas.setPrefWidth(320);
        tfTenggat.setPrefWidth(320);

        Button btnTambah = new Button("Tambah Tugas");
        Button btnSelesai = new Button("Tandai Selesai");
        Button btnHapus = new Button("Hapus Tugas");
        HBox tombol = new HBox(10, btnTambah, btnSelesai, btnHapus);

        // ===== TAHAP 2 dan 3: event handling + validasi =====
        btnTambah.setOnAction(e -> {
            String mataKuliah = tfMataKuliah.getText().trim();
            String namaTugas = tfNamaTugas.getText().trim();
            String teksTenggat = tfTenggat.getText().trim();

            // Validasi: kolom tidak boleh kosong
            if (mataKuliah.isEmpty() || namaTugas.isEmpty() || teksTenggat.isEmpty()) {
                tampilkanAlert(Alert.AlertType.WARNING, "Data belum lengkap",
                        "Mata kuliah, nama tugas, dan tenggat wajib diisi.");
                return;
            }

            // Validasi: format tanggal dengan try-catch
            try {
                LocalDate tenggat = LocalDate.parse(teksTenggat, FORMAT_TANGGAL);
                pengelolaTugas.tambahTugas(mataKuliah, namaTugas, tenggat);
                tfMataKuliah.clear();
                tfNamaTugas.clear();
                tfTenggat.clear();
                refreshTugas();
                tampilkanAlert(Alert.AlertType.INFORMATION, "Berhasil", "Tugas berhasil ditambahkan.");
            } catch (DateTimeParseException ex) {
                tampilkanAlert(Alert.AlertType.ERROR, "Format tanggal salah",
                        "Gunakan format dd-MM-yyyy, contoh: 20-11-2026.");
            }
        });

        btnSelesai.setOnAction(e -> {
            Tugas dipilih = listTugas.getSelectionModel().getSelectedItem();
            if (dipilih == null) {
                tampilkanAlert(Alert.AlertType.WARNING, "Belum ada yang dipilih",
                        "Klik salah satu tugas di daftar terlebih dahulu.");
                return;
            }
            pengelolaTugas.tandaiSelesai(dipilih.getId());
            refreshTugas();
        });

        btnHapus.setOnAction(e -> {
            Tugas dipilih = listTugas.getSelectionModel().getSelectedItem();
            if (dipilih == null) {
                tampilkanAlert(Alert.AlertType.WARNING, "Belum ada yang dipilih",
                        "Klik salah satu tugas di daftar terlebih dahulu.");
                return;
            }
            pengelolaTugas.hapusTugas(dipilih.getId());
            refreshTugas();
        });

        VBox isi = new VBox(12, judul, labelPengingat, form, tombol,
                new Label("Daftar tugas (tenggat terdekat di atas)"), listTugas);
        isi.setPadding(new Insets(15));
        VBox.setVgrow(listTugas, Priority.ALWAYS);

        refreshTugas();

        Tab tab = new Tab("Tugas", isi);
        tab.setClosable(false);
        return tab;
    }

    // ===== TAHAP 1: Desain antarmuka tab Jadwal Kuliah =====
    private Tab buatTabJadwal() {
        Label judul = new Label("Kelola Jadwal Kuliah");
        judul.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TextField tfMataKuliah = new TextField();
        tfMataKuliah.setPromptText("contoh: Pemrograman Berorientasi Objek");
        ComboBox<String> cbHari = new ComboBox<>();
        cbHari.getItems().addAll("Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu", "Minggu");
        cbHari.setValue("Senin");
        TextField tfJamMulai = new TextField();
        tfJamMulai.setPromptText("contoh: 08:00");
        TextField tfJamSelesai = new TextField();
        tfJamSelesai.setPromptText("contoh: 10:30");
        TextField tfRuangan = new TextField();
        tfRuangan.setPromptText("contoh: Lab 1");
        TextField tfDosen = new TextField();
        tfDosen.setPromptText("nama dosen");

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(8);
        form.add(new Label("Mata kuliah"), 0, 0);
        form.add(tfMataKuliah, 1, 0);
        form.add(new Label("Hari"), 0, 1);
        form.add(cbHari, 1, 1);
        form.add(new Label("Jam mulai"), 0, 2);
        form.add(tfJamMulai, 1, 2);
        form.add(new Label("Jam selesai"), 0, 3);
        form.add(tfJamSelesai, 1, 3);
        form.add(new Label("Ruangan"), 0, 4);
        form.add(tfRuangan, 1, 4);
        form.add(new Label("Dosen"), 0, 5);
        form.add(tfDosen, 1, 5);
        tfMataKuliah.setPrefWidth(320);
        tfJamMulai.setPrefWidth(320);
        tfJamSelesai.setPrefWidth(320);
        tfRuangan.setPrefWidth(320);
        tfDosen.setPrefWidth(320);

        Button btnTambah = new Button("Tambah Jadwal");
        Button btnHapus = new Button("Hapus Jadwal");
        HBox tombol = new HBox(10, btnTambah, btnHapus);

        btnTambah.setOnAction(e -> {
            String mataKuliah = tfMataKuliah.getText().trim();
            String hari = cbHari.getValue();
            String jamMulai = tfJamMulai.getText().trim();
            String jamSelesai = tfJamSelesai.getText().trim();
            String ruangan = tfRuangan.getText().trim();
            String dosen = tfDosen.getText().trim();

            // Validasi: kolom tidak boleh kosong
            if (mataKuliah.isEmpty() || jamMulai.isEmpty() || jamSelesai.isEmpty()
                    || ruangan.isEmpty() || dosen.isEmpty()) {
                tampilkanAlert(Alert.AlertType.WARNING, "Data belum lengkap",
                        "Semua kolom jadwal wajib diisi.");
                return;
            }

            pengelolaJadwal.tambahJadwal(mataKuliah, hari, jamMulai, jamSelesai, ruangan, dosen);
            tfMataKuliah.clear();
            tfJamMulai.clear();
            tfJamSelesai.clear();
            tfRuangan.clear();
            tfDosen.clear();
            refreshJadwal();
            tampilkanAlert(Alert.AlertType.INFORMATION, "Berhasil", "Jadwal berhasil ditambahkan.");
        });

        btnHapus.setOnAction(e -> {
            JadwalKuliah dipilih = listJadwal.getSelectionModel().getSelectedItem();
            if (dipilih == null) {
                tampilkanAlert(Alert.AlertType.WARNING, "Belum ada yang dipilih",
                        "Klik salah satu jadwal di daftar terlebih dahulu.");
                return;
            }
            pengelolaJadwal.hapusJadwal(dipilih.getId());
            refreshJadwal();
        });

        VBox isi = new VBox(12, judul, form, tombol, new Label("Daftar jadwal kuliah"), listJadwal);
        isi.setPadding(new Insets(15));
        VBox.setVgrow(listJadwal, Priority.ALWAYS);

        refreshJadwal();

        Tab tab = new Tab("Jadwal Kuliah", isi);
        tab.setClosable(false);
        return tab;
    }

    // ===== Method bantu =====
    private void refreshTugas() {
        listTugas.getItems().setAll(pengelolaTugas.getTugasUrutTenggat());

        // Fitur unggulan: pengingat tenggat
        int mendesak = 0;
        for (Tugas t : pengelolaTugas.getDaftarTugas()) {
            if (!t.isSelesai() && t.hitungSisaHari() <= 1) {
                mendesak++;
            }
        }
        if (mendesak > 0) {
            labelPengingat.setText("Pengingat: " + mendesak + " tugas terlewat, hari ini, atau besok!");
        } else {
            labelPengingat.setText("Pengingat: tidak ada tugas yang mendesak.");
        }
    }

    private void refreshJadwal() {
        listJadwal.getItems().setAll(pengelolaJadwal.getDaftarJadwal());
    }

    private void tampilkanAlert(Alert.AlertType jenis, String judul, String pesan) {
        Alert alert = new Alert(jenis);
        alert.setTitle(judul);
        alert.setHeaderText(judul);
        alert.setContentText(pesan);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch();
    }
}
