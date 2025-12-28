# EBT 522 – İleri Web Programlama (Yıl İçi Proje) TODO

Bu dosya, verilen ödev metnindeki tüm zorunlu maddeleri **tek tek** karşılayacak şekilde proje yapılacaklar listesidir. Her madde “bitti” kabul edilmeden önce ilgili sayfada **çalışır** ve **hatasız** olduğundan emin olunmalıdır.

## 0) Proje Tanımı ve Kapsam
- [ ] Proje: Üniversite sınıfı için yıllık/mezunlar platformu (sosyal paylaşım web sitesi)
- [ ] Teknoloji: **JSP + Java (Servlet) + PostgreSQL** (destek: JS/HTML/CSS)
- [ ] Yasaklar: Hazır şablon/kopya tasarım yok; ders dışında framework kullanılmaz (Spring vb. yok)
- [ ] Yayın: Free hosting’e yüklenebilir (WAR/Tomcat) olacak şekilde hazırlanır
- [ ] SABİS teslim: Sadece `Ad Soyad` + `Anasayfa linki` içeren `.txt`

## 1) Sayfa Haritası (Zorunlu)
> Anasayfadan (nav menü) tüm sayfalara gidilebilmeli; ölü link/bozuk görsel olmamalı.

### 1.1 Anasayfa (`/`)
- [ ] Site başlığı + kısa açıklama
- [ ] Menü/Linkler:
  - [ ] Kişi Kayıt
  - [ ] Oturum Açma / Çıkış
  - [ ] Forum
  - [ ] Kişiler (Anasayfa)
  - [ ] Fotoğraflar (Anasayfa)
  - [ ] Videolar (Anasayfa)
  - [ ] Kim, Nerede, Ne Yapıyor?
  - [ ] Harita
- [ ] Giriş yaptıysa kullanıcı adı + rol (Normal/Admin) göster

### 1.2 Kişi Kayıt (Üyelik) Sayfası
- [ ] Kayıt formunda **tüm form nesneleri** kullan:
  - [ ] Text: Ad, Soyad, Kullanıcı Adı
  - [ ] Email: E‑posta
  - [ ] Password: Şifre + Şifre tekrar
  - [ ] Radio: Cinsiyet
  - [ ] Checkbox: Hobiler (çoklu)
  - [ ] Combobox/Select: Oturduğu şehir
  - [ ] Date: Doğum tarihi (opsiyonel ama önerilir)
  - [ ] Textarea: Adres / Hakkımda
  - [ ] Tel: Telefon
  - [ ] Url: Web adresi
  - [ ] Text: Facebook id, Twitter vb.
  - [ ] File: Profil fotoğrafı (opsiyonel ama önerilir)
- [ ] Sunucu tarafı validasyon:
  - [ ] Zorunlu alanlar boş geçilemez
  - [ ] Kullanıcı adı benzersiz
  - [ ] E‑posta benzersiz
  - [ ] Şifre politikası (min uzunluk)
- [ ] Veritabanına kayıt işlemi
- [ ] Başarılı kayıt → login’e yönlendir

### 1.3 Oturum Açma (Login)
- [ ] Login formu (kullanıcı adı/e‑posta + şifre)
- [ ] Başarılı login → anasayfa
- [ ] Hatalı giriş mesajı
- [ ] Logout işlemi (session temizleme)
- [ ] Yetki sistemi:
  - [ ] Normal kullanıcı
  - [ ] Admin (yönetici)

### 1.4 Forum
- [ ] Mesaj ekleme formu (login gerekli)
- [ ] Mesajların listesi:
  - [ ] Tarih‑saat formatında gösterim
  - [ ] Mesaj sahibi adı
  - [ ] Mesaj içeriği
- [ ] **Sayfalama** (ekstra puan):
  - [ ] `?page=1` şeklinde QueryString ile sayfa değişimi
  - [ ] Önceki/Sonraki linkleri
- [ ] Admin işlemleri:
  - [ ] Mesaj silme
  - [ ] Mesaj düzenleme

### 1.5 Kişiler
#### 1.5.1 Kişiler Anasayfası
- [ ] Tüm öğrenciler listesi
- [ ] Her öğrenci için:
  - [ ] Thumbnail resim
  - [ ] Ad Soyad
  - [ ] Kişi sayfasına link

#### 1.5.2 Kişi Sayfası
- [ ] Büyük profil resmi
- [ ] Kullanıcı detayları (iletişim/okul/iş vb. uygun alanlar)
- [ ] Yorum ekleme:
  - [ ] Textarea (login gerekli)
  - [ ] Veritabanına kayıt
- [ ] Yorumların listesi tablosu:
  - [ ] Tarih
  - [ ] Saat
  - [ ] Yorumcu ismi
  - [ ] Yorum metni
- [ ] Admin işlemleri:
  - [ ] Yorum silme
  - [ ] Yorum düzenleme

### 1.6 Fotoğraflar
#### 1.6.1 Fotoğraflar Anasayfası
- [ ] Tüm fotoğraflar listesi
- [ ] Thumbnail + büyük foto sayfasına link

#### 1.6.2 Fotoğraf Sayfası
- [ ] Büyük fotoğraf göster
- [ ] Yorum ekleme + DB kayıt (login gerekli)
- [ ] Yorumların tablo halinde listesi (tarih, saat, yorumcu)
- [ ] Ekstra puan (etiketleme – coords/shape):
  - [ ] Foto üzerinde `<map><area>` ile bölgesel link
  - [ ] Etiketli alan → ilgili kişinin sayfasına link
  - [ ] Etiket ekleme arayüzü (admin)

### 1.7 Videolar
#### 1.7.1 Videolar Anasayfası
- [ ] Tüm videolar listesi
- [ ] Thumbnail + video sayfasına link

#### 1.7.2 Video Sayfası
- [ ] Youtube embed (iframe)
- [ ] Otomatik çalıştırma (autoplay parametresi)
- [ ] Yorum ekleme + DB kayıt (login gerekli)
- [ ] Yorumların tablo halinde listesi (tarih, saat, yorumcu)

### 1.8 Kim, Nerede, Ne Yapıyor?
- [ ] Üye bilgilerinin DB’den çekilip tabloda listelenmesi:
  - [ ] Ad, Soyad
  - [ ] Adres
  - [ ] Telefon
  - [ ] Şimdiki okul
  - [ ] İşi
  - [ ] E‑posta
  - [ ] Web adresi
  - [ ] Facebook id
  - [ ] Twitter vb.
- [ ] Admin ek özellikler (ödevde istenen yetkilerle uyumlu):
  - [ ] Kullanıcı silme
  - [ ] Kullanıcıyı admin yapma/geri alma

### 1.9 Harita (20 Puan)
- [ ] Türkiye haritası kullan:
  - [ ] Kaynak: `https://www.kgm.gov.tr/SiteCollectionImages/KGMimages/Haritalar/turistik.jpg`
- [ ] JSP sayfasında **Panning + Zooming**:
  - [ ] Pan: sürükle‑bırak (drag)
  - [ ] Zoom: buton ve/veya mouse wheel
  - [ ] Div + CSS ile haritanın belirli kısmını göster (overflow hidden)
- [ ] Kullanıcı yönergeleri (kısa)

## 2) Tarih‑Saat Düzeltme (Sunucu → İstemci)
- [ ] DB’ye kaydedilen timestamp’ler sunucu saatidir
- [ ] İstemci tarafında yerel saate dönüştür:
  - [ ] Sunucudan UTC/ISO gönder
  - [ ] JS ile `new Date(...)` üzerinden locale format
- [ ] Forum / yorum listelerinde uygulanır

## 3) Veritabanı
- [ ] PostgreSQL şeması
- [ ] Tablo ve ilişkiler:
  - [ ] `users` (profil + rol)
  - [ ] `forum_posts`
  - [ ] `person_comments`
  - [ ] `photos`
  - [ ] `photo_comments`
  - [ ] `photo_tags` (opsiyonel/ekstra)
  - [ ] `videos`
  - [ ] `video_comments`
- [ ] Silme davranışı:
  - [ ] Kullanıcı silinince bağlı kayıtlar (cascade) net tanımlı
- [ ] Seed:
  - [ ] İlk admin kullanıcı (denemek için)
  - [ ] Örnek foto/video kayıtları (thumbnail + sayfa çalışsın)

## 4) Backend (JSP/Servlet)
- [ ] Güvenli şifre saklama (PBKDF2)
- [ ] Session yönetimi
- [ ] Yetkilendirme kontrolü:
  - [ ] Login gerektiren sayfalar/aksiyonlar
  - [ ] Admin gerektiren aksiyonlar
- [ ] CRUD akışları:
  - [ ] Forum post ekle/listele/sil/düzenle
  - [ ] Kişi yorumu ekle/listele/sil/düzenle
  - [ ] Foto yorum ekle/listele/sil/düzenle
  - [ ] Video yorum ekle/listele/sil/düzenle
  - [ ] Admin: kullanıcı sil, rol değiştir
- [ ] Input güvenliği:
  - [ ] SQL Injection’a karşı PreparedStatement
  - [ ] XSS’e karşı çıktı kaçış (en azından `<`, `>` vs.)

## 5) Frontend (Tasarım)
- [ ] Özgün, basit ama tutarlı tasarım
- [ ] Responsive layout (en azından mobilde taşma yok)
- [ ] Ortak navbar + footer
- [ ] Bozuk resim yok (varsayılan placeholder)

## 6) Proje Dosyaları (Teslim Hazırlığı)
- [ ] `README.md`: kurulum + çalıştırma + deploy adımları
- [ ] `db/schema.sql` + `db/seed.sql`
- [ ] `SABIS_TESLIM.txt` (Ad Soyad + anasayfa linki)
- [ ] WAR üretimi (Maven `package`)

## 7) Son Kontrol (Ödev Maddelerine Karşı)
- [ ] Tüm sayfalar nav’dan erişilebilir
- [ ] Login olmadan:
  - [ ] Forum/yorum ekleme engelli (mesaj göster)
  - [ ] Listeleme sayfaları okunabilir
- [ ] Admin olmadan:
  - [ ] Sil/düzenle butonları görünmez ya da 403
- [ ] Tarih‑saatler istemci yerel saatine dönmüş görünüyor
- [ ] Harita pan/zoom çalışıyor
- [ ] Ölü link yok, 404 yok, kırık görsel yok

