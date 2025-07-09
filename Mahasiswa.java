/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aplikasiabsensi;

public class Mahasiswa {
    private String nim;
    private String nama;
    private String keterangan;
    private String tanggal;
    private String status;

    public Mahasiswa(String nim, String nama, String keterangan, String tanggal, String status) {
        this.nim = nim;
        this.nama = nama;
        this.keterangan = keterangan;
        this.tanggal = tanggal;
        this.status = status;
    }

    public String getNim() { return nim; }
    public void setNim(String nim) { this.nim = nim; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getKeterangan() { return keterangan; }
    public void setKeterangan(String keterangan) { this.keterangan = keterangan; }

    public String getTanggal() { return tanggal; }
    public void setTanggal(String tanggal) { this.tanggal = tanggal; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
