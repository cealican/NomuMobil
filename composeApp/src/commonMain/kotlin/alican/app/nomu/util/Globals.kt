package alican.app.nomu.util

val isMock = true

// TODO: web tarafında loglayarak tüm işlemler yapılacak.

/*
-- 1. Veritabanını oluştur (Eğer yoksa)
CREATE DATABASE IF NOT EXISTS nomu_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE nomu_db;

-- 2. Tarifler ve Aramalar Tablosu
-- Bu tablo hem kullanıcı aramalarını hem de Gemini'den gelen yanıtları saklar.
CREATE TABLE IF NOT EXISTS recipe_cache (
id INT AUTO_INCREMENT PRIMARY KEY,

-- Normalize edilmiş ve İngilizceye çevrilmiş malzeme listesi (Cache Anahtarı)
-- Örn: "eggplant,onion,tomato" (Alfabetik ve virgülle ayrılmış)
ingredients_hash VARCHAR(500) NOT NULL,

-- Lokasyon bilgisi
location VARCHAR(100) DEFAULT 'Global',

-- Gemini'den gelen ham JSON yanıtı
-- (Yemek adı, kalori, protein vb. tüm listeyi içerir)
json_response TEXT NOT NULL,

-- Yanıtın dili (Hangi dilde üretildiği)
response_lang VARCHAR(10) NOT NULL,

-- İstatistik ve temizlik için tarih kayıtları
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

-- Performans için indeksler
-- Aynı malzemeler ve lokasyon ile yapılan aramaları çok hızlı bulmak için
INDEX idx_search (ingredients_hash(255), location, response_lang)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 3. Opsiyonel: Log tablosu (Hataları ve kullanım istatistiklerini izlemek için)
CREATE TABLE IF NOT EXISTS api_logs (
id INT AUTO_INCREMENT PRIMARY KEY,
endpoint VARCHAR(50),
request_data TEXT,
error_message TEXT,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);*/