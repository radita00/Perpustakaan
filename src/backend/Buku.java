/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend;

import java.util.ArrayList;
import java.sql.*;

public class Buku {

    private int idBuku;
    private Kategori kategori = new Kategori();
    private String judul;
    private String penerbit;
    private String penulis;

    public int getIdBuku() {
        return idBuku;
    }

    public void setIdBuku(int idBuku) {
        this.idBuku = idBuku;
    }

    public Kategori getKategori() {
        return kategori;
    }

    public void setKategori(Kategori kategori) {
        this.kategori = kategori;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getPenerbit() {
        return penerbit;
    }

    public void setPenerbit(String penerbit) {
        this.penerbit = penerbit;
    }

    public String getPenulis() {
        return penulis;
    }

    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }

    public Buku() {
    }

    public Buku(Kategori kategori, String judul, String penerbit, String penulis) {
        this.kategori = kategori;
        this.judul = judul;
        this.penerbit = penerbit;
        this.penulis = penulis;
    }

    public Buku getById(int id) {
        Buku buku = new Buku();
        ResultSet rs = DBHelper.selectQuery("SELECT "
                + "     b.idBuku AS idBuku, "
                + "     b.judul AS judul, "
                + "     b.penerbit AS penerbit, "
                + "     b.penulis AS penulis, "
                + "     b.idKategori AS idKategori, "
                + "     k.nama AS nama, "
                + "     k.keterangan AS keterangan "
                + "     FROM buku b "
                + "     LEFT JOIN kategori k ON b.idKategori = k.idKategori "
                + "     WHERE b.idBuku = '" + id + "'");

        try {
            while (rs.next()) {
                buku = new Buku();
                buku.setIdBuku(rs.getInt("idBuku"));
                buku.getKategori().setIdKategori(rs.getInt("idKategori"));
                buku.getKategori().setNama(rs.getString("nama"));
                buku.getKategori().setKeterangan(rs.getString("keterangan"));
                buku.setJudul(rs.getString("judul"));
                buku.setPenerbit(rs.getString("penerbit"));
                buku.setPenulis(rs.getString("penulis"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return buku;
    }

    public ArrayList<Buku> getAll() {
        ArrayList<Buku> listBuku = new ArrayList();

        ResultSet rs = DBHelper.selectQuery("SELECT "
                + "     b.idBuku AS idBuku, "
                + "     b.judul AS judul, "
                + "     b.penerbit AS penerbit, "
                + "     b.penulis AS penulis, "
                + "     b.idKategori AS idKategori, "
                + "     k.nama AS nama, "
                + "     k.keterangan AS keterangan "
                + "     FROM buku b "
                + "     LEFT JOIN kategori k ON b.idKategori = k.idKategori ");

        try {
            while (rs.next()) {
                Buku buku = new Buku();
                buku.setIdBuku(rs.getInt("idBuku"));
                buku.getKategori().setIdKategori(rs.getInt("idKategori"));
                buku.getKategori().setNama(rs.getString("nama"));
                buku.getKategori().setKeterangan(rs.getString("keterangan"));
                buku.setJudul(rs.getString("judul"));
                buku.setPenerbit(rs.getString("penerbit"));
                buku.setPenulis(rs.getString("penulis"));

                listBuku.add(buku);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listBuku;
    }

    public ArrayList<Buku> search(String keyword) {
        ArrayList<Buku> listBuku = new ArrayList();

        ResultSet rs = DBHelper.selectQuery("SELECT "
                + "     b.idBuku AS idBuku, "
                + "     b.judul AS judul, "
                + "     b.penerbit AS penerbit, "
                + "     b.penulis AS penulis, "
                + "     b.idKategori AS idKategori, "
                + "     k.nama AS nama, "
                + "     k.keterangan AS keterangan "
                + "     FROM buku b "
                + "     LEFT JOIN kategori k ON b.idKategori = k.idKategori "
                + "     WHERE b.judul LIKE '%" + keyword + "%' "
                + "     OR b.penerbit LIKE '%" + keyword + "%' "
                + "     OR b.penulis LIKE '%" + keyword + "%' ");

        try {
            while (rs.next()) {
                Buku buku = new Buku();
                buku.setIdBuku(rs.getInt("idBuku"));
                buku.getKategori().setIdKategori(rs.getInt("idKategori"));
                buku.getKategori().setNama(rs.getString("nama"));
                buku.getKategori().setKeterangan(rs.getString("keterangan"));
                buku.setJudul(rs.getString("judul"));
                buku.setPenerbit(rs.getString("penerbit"));
                buku.setPenulis(rs.getString("penulis"));

                listBuku.add(buku);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listBuku;
    }

    public void save() {
        if (getById(idBuku).getIdBuku() == 0) {
            String SQL = "INSERT INTO buku (judul, idKategori, penulis, penerbit) VALUES("
                    + "     '" + this.judul + "', "
                    + "     '" + this.getKategori().getIdKategori() + "', "
                    + "     '" + this.penulis + "', "
                    + "     '" + this.penerbit + "' "
                    + "     )";
            this.idBuku = DBHelper.insertQueryGetId(SQL);
        } else {
            String SQL = "UPDATE buku SET "
                    + "     judul = '" + this.judul + "', "
                    + "     idKategori = '" + this.getKategori().getIdKategori() + "', "
                    + "     penulis = '" + this.penulis + "', "
                    + "     penerbit = '" + this.penerbit + "' "
                    + "     WHERE idBuku = '" + this.idBuku + "'";
            DBHelper.executeQuery(SQL);
        }
    }

    public void delete() {
        String SQL = "DELETE FROM buku WHERE idBuku = '" + this.idBuku + "'";
        DBHelper.executeQuery(SQL);
    }
}
