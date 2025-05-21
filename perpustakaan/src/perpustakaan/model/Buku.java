package perpustakaan.model;

public class Buku {
    private int id;
    private String judul, genre, penulis, penerbit, lokasi;
    private int stok;

    public Buku(int id, String judul, String genre, String penulis, String penerbit, String lokasi, int stok) {
        this.id = id;
        this.judul = judul;
        this.genre = genre;
        this.penulis = penulis;
        this.penerbit = penerbit;
        this.lokasi = lokasi;
        this.stok = stok;
    }

    public int getId() { return id; }
    public String getJudul() { return judul; }
    public String getGenre() { return genre; }
    public String getPenulis() { return penulis; }
    public String getPenerbit() { return penerbit; }
    public String getLokasi() { return lokasi; }
    public int getStok() { return stok; }
}

