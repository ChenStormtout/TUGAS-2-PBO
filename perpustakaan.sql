-- Buat database
CREATE DATABASE IF NOT EXISTS perpustakaan;
USE perpustakaan;

-- Buat tabel buku
CREATE TABLE IF NOT EXISTS buku (
    id_buku INT AUTO_INCREMENT PRIMARY KEY,
    judul VARCHAR(100) NOT NULL,
    genre VARCHAR(50),
    penulis VARCHAR(100),
    penerbit VARCHAR(100),
    lokasi VARCHAR(50),
    stok INT
);

-- Tambahkan data awal
INSERT INTO buku (judul, genre, penulis, penerbit, lokasi, stok) VALUES
('Laskar Pelangi', 'Novel', 'Andrea Hirata', 'Bentang Pustaka', 'Rak A1', 10),
('Negeri 5 Menara', 'Novel', 'Ahmad Fuadi', 'Gramedia', 'Rak A2', 7),
('Atomic Habits', 'Self Development', 'James Clear', 'Penguin Random House', 'Rak B1', 5),
('Belajar Java', 'Teknologi', 'Eka Setiawan', 'Informatika Bandung', 'Rak C1', 4),
('Sapiens', 'Sejarah', 'Yuval Noah Harari', 'Harper', 'Rak B3', 6);
