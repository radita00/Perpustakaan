/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend;
import java.util.ArrayList;
import java.sql.*;
/**
 *
 * @author radit
 */
public class Peminjaman {
    private int idPeminjaman;
    private Anggota anggota = new Anggota();
    private Buku buku = new Buku();
    private String tanggalPinjam;
    private String tanggalKembali;

    public int getIdPeminjaman() {
        return idPeminjaman;
    }

    public void setIdPeminjaman(int idPeminjaman) {
        this.idPeminjaman = idPeminjaman;
    }

    public Anggota getAnggota() {
        return anggota;
    }

    public void setAnggota(Anggota anggota) {
        this.anggota = anggota;
    }

    public Buku getBuku() {
        return buku;
    }

    public void setBuku(Buku buku) {
        this.buku = buku;
    }

    public String getTanggalPinjam() {
        return tanggalPinjam;
    }

    public void setTanggalPinjam(String tanggalPinjam) {
        this.tanggalPinjam = tanggalPinjam;
    }

    public String getTanggalKembali() {
        return tanggalKembali;
    }

    public void setTanggalKembali(String tanggalKembali) {
        this.tanggalKembali = tanggalKembali;
    }

    public Peminjaman() {
    }

    public Peminjaman(Anggota anggota , Buku buku, String tanggalPinjam, String tanggalKembali) {
        this.anggota = anggota;
        this.buku = buku;
        this.tanggalPinjam = tanggalPinjam;
        this.tanggalKembali = tanggalKembali;
    }
    public Peminjaman getById(int id) {
        Peminjaman peminjaman = new Peminjaman();
        ResultSet rs = DBHelper.selectQuery(
            "SELECT * FROM peminjaman WHERE idPeminjaman = " + id
        );

        try {
            while (rs.next()) {
                peminjaman = new Peminjaman();
                peminjaman.setIdPeminjaman(rs.getInt("idPeminjaman"));
                peminjaman.setAnggota(new Anggota().getById(rs.getInt("idAnggota")));
                peminjaman.setBuku(new Buku().getById(rs.getInt("idBuku")));
                peminjaman.setTanggalPinjam(rs.getString("tanggalPinjam"));
                peminjaman.setTanggalKembali(rs.getString("tanggalKembali"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return peminjaman;
    }
    public ArrayList<Peminjaman> getAll() {
        ArrayList<Peminjaman> list = new ArrayList<>();

        ResultSet rs = DBHelper.selectQuery("SELECT * FROM peminjaman");

        try {
            while (rs.next()) {
                Peminjaman p = new Peminjaman();
                p.setIdPeminjaman(rs.getInt("idPeminjaman"));
                p.setAnggota(new Anggota().getById(rs.getInt("idAnggota")));
                p.setBuku(new Buku().getById(rs.getInt("idBuku")));
                p.setTanggalPinjam(rs.getString("tanggalPinjam"));
                p.setTanggalKembali(rs.getString("tanggalKembali"));

                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    public ArrayList<Peminjaman> searchAnggota(String keyword) {
        ArrayList<Peminjaman> list = new ArrayList<>();

        String sql = 
            "SELECT * FROM peminjaman p " +
            "INNER JOIN anggota a ON p.idAnggota = a.idAnggota " +
            "WHERE a.nama LIKE '%" + keyword + "%'";

        ResultSet rs = DBHelper.selectQuery(sql);

        try {
            while (rs.next()) {
                Peminjaman p = new Peminjaman();
                p.setIdPeminjaman(rs.getInt("idPeminjaman"));
                p.setAnggota(new Anggota().getById(rs.getInt("idAnggota")));
                p.setBuku(new Buku().getById(rs.getInt("idBuku")));
                p.setTanggalPinjam(rs.getString("tanggalPinjam"));
                p.setTanggalKembali(rs.getString("tanggalKembali"));

                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    
    public ArrayList<Peminjaman> searchBuku(String keyword) {
        ArrayList<Peminjaman> list = new ArrayList<>();

        String sql = 
            "SELECT * FROM peminjaman p " +
            "INNER JOIN buku b ON p.idBuku = b.idBuku " +
            "WHERE b.judul LIKE '%" + keyword + "%'";

        ResultSet rs = DBHelper.selectQuery(sql);

        try {
            while (rs.next()) {
                Peminjaman p = new Peminjaman();
                p.setIdPeminjaman(rs.getInt("idPeminjaman"));
                p.setAnggota(new Anggota().getById(rs.getInt("idAnggota")));
                p.setBuku(new Buku().getById(rs.getInt("idBuku")));
                p.setTanggalPinjam(rs.getString("tanggalPinjam"));
                p.setTanggalKembali(rs.getString("tanggalKembali"));

                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    
    public void save() {
        if (getById(idPeminjaman).getIdPeminjaman() == 0) {
            String SQL = "INSERT INTO peminjaman (idAnggota, idBuku, tanggalPinjam, tanggalKembali) VALUES("
                    + "'" + this.getAnggota().getIdAnggota() + "', "
                    + "'" + this.getBuku().getIdBuku() + "', "
                    + "'" + this.tanggalPinjam + "', "
                    + "'" + this.tanggalKembali + "' "
                    + ")";

            this.idPeminjaman = DBHelper.insertQueryGetId(SQL);
        } else {
            String SQL = "UPDATE peminjaman SET "
                    + "idAnggota = '" + this.getAnggota().getIdAnggota() + "', "
                    + "idBuku = '" + this.getBuku().getIdBuku() + "', "
                    + "tanggalPinjam = '" + this.tanggalPinjam + "', "
                    + "tanggalKembali = '" + this.tanggalKembali + "' "
                    + "WHERE idPeminjaman = '" + this.idPeminjaman + "'";

            DBHelper.executeQuery(SQL);
        }
    }

    public void delete() {
        String SQL = "DELETE FROM peminjaman WHERE idPeminjaman = " + this.idPeminjaman;
        DBHelper.executeQuery(SQL);
    }
}

