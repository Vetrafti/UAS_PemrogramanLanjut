/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aplikasiabsensi;

import java.util.ArrayList;

public class AbsensiManager {
    private ArrayList<Mahasiswa> daftarAbsensi = new ArrayList<>();

    public void tambah(Mahasiswa mhs) {
        daftarAbsensi.add(mhs);
    }

    public void hapus(int index) {
        if (index >= 0 && index < daftarAbsensi.size()) {
            daftarAbsensi.remove(index);
        }
    }

    public void update(int index, Mahasiswa mhsBaru) {
        if (index >= 0 && index < daftarAbsensi.size()) {
            daftarAbsensi.set(index, mhsBaru);
        }
    }

    public ArrayList<Mahasiswa> getSemuaMahasiswa() {
        return daftarAbsensi;
    }
}
