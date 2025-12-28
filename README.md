# EBT 522 – Sosyal Paylaşım (JSP + Java + PostgreSQL)

Bu proje; “yıllık/mezunlar platformu” ödevi için **JSP + Servlet + PostgreSQL** kullanılarak hazırlanmıştır.

## Zorunlu Sayfalar
- Anasayfa: `/home`
- Kişi Kayıt: `/register`
- Oturum Açma / Çıkış: `/login`, `/logout`
- Forum: `/forum` (sayfalama: `?page=1`)
- Kişiler: `/people` → Kişi sayfası: `/person?id=...`
- Fotoğraflar: `/photos` → Fotoğraf sayfası: `/photo?id=...`
- Videolar: `/videos` → Video sayfası: `/video?id=...`
- Kim, Nerede, Ne Yapıyor?: `/activity`
- Harita (Pan + Zoom): `/map`

## Admin Yetkileri
- Forum mesajlarını düzenleme/silme
- Kişi/Fotograf/Video yorumlarını düzenleme/silme
- Kullanıcı silme ve admin atama: `/activity` (sadece admin görür)

## Tarih‑Saat Düzeltme (Sunucu → İstemci)
- Sunucu tarafında kaydedilen zamanlar, sayfalarda `data-epoch-ms` ile gönderilir.
- İstemci tarafında `src/main/webapp/assets/js/app.js` yerel saate çevirip ekrana basar.

## Veritabanı Kurulumu (PostgreSQL)
1) Veritabanı oluşturun:
- `CREATE DATABASE ebt522_social;`

2) Şemayı çalıştırın:
- `psql -d ebt522_social -f db/schema.sql`

3) Örnek veriyi (admin + demo + foto/video) çalıştırın:
- `psql -d ebt522_social -f db/seed.sql`

### Varsayılan Admin Hesabı (Seed ile gelir)
- Kullanıcı adı: `admin`
- Şifre: `Admin123!`

### Demo Kullanıcı
- Kullanıcı adı: `demo`
- Şifre: `Demo123!`

## DB Bağlantı Ayarları
Uygulama `Db.java` üzerinden şu değişkenleri okur:
- `DB_URL` (örn: `jdbc:postgresql://localhost:5432/ebt522_social`)
- `DB_USER`
- `DB_PASSWORD`

Alternatif olarak JVM parametreleri:
- `-Ddb.url=... -Ddb.user=... -Ddb.password=...`

## Build / Deploy
Bu repo Maven `war` projesidir (`pom.xml`).

- WAR üretimi:
  - Windows: `.\mvnw.cmd -DskipTests package`
  - macOS/Linux: `./mvnw -DskipTests package`
- Üretilen dosya: `target/ebt522-sosyal-paylasim.war`
- Tomcat `webapps/` altına kopyalayarak yayınlayabilirsiniz.

## Docker ile Lokal Çalıştırma (İsteğe Bağlı)
Docker kuruluysa en hızlı test:
- `docker compose up --build`
- Aç: `http://localhost:8080/home`

## Harita Notu
Harita görseli ödevde verilen kaynaktır ve projeye statik olarak eklendi:
- Kaynak: `https://www.kgm.gov.tr/SiteCollectionImages/KGMimages/Haritalar/turistik.jpg`
- Lokal dosya: `src/main/webapp/assets/img/turistik.jpg`

`/map` sayfası önce lokal dosyayı gösterir; yüklenemezse otomatik olarak kaynak URL’ye düşer (bkz. `src/main/webapp/WEB-INF/views/map/index.jsp:1`).
