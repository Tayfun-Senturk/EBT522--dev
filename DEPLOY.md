# Deploy (En Kolay Yöntem – Docker + Postgres)

Bu proje **JSP/Servlet** olduğu için statik hostinge yüklenerek çalışmaz. En kolay yöntem:
- Uygulamayı **Docker (Tomcat 9 + WAR)** olarak çalıştırmak
- Veritabanını ücretsiz bir **Managed PostgreSQL** (Neon/Supabase vb.) ile sağlamak

Repo içinde hazır: `Dockerfile` (multi‑stage build) ve `DB_URL/DB_USER/DB_PASSWORD` env ayarları.

## 1) Ücretsiz PostgreSQL (Neon veya Supabase)
1. Yeni bir Postgres veritabanı oluşturun.
2. Bağlantı bilgilerini alın:
   - Host, Port (genelde 5432), Database, User, Password
3. Şemayı yükleyin:
   - `db/schema.sql` içeriğini SQL editor’da çalıştırın
   - `db/seed.sql` içeriğini çalıştırın (admin + demo + örnek foto/video ekler)

Not: Harita görseli projeye statik eklidir (`src/main/webapp/assets/img/turistik.jpg`) ve hotlink problemi yaşamazsınız.

## 2) Uygulama Deploy (Docker destekleyen bir platform)
Docker deploy destekleyen bir platform seçin (Render / Fly.io / Koyeb vb.).

Not: Docker deploy’da uygulama `ROOT.war` olarak çalıştırılır; yani URL’ler genelde `https://DOMAIN/home` şeklinde olur.

### Ortak ayarlar
Platform panelinde aşağıdaki env değişkenlerini tanımlayın:
- `DB_URL` = `jdbc:postgresql://<HOST>:5432/<DB>?sslmode=require`
- `DB_USER` = `<USER>`
- `DB_PASSWORD` = `<PASSWORD>`

Notlar:
- Bazı sağlayıcılar `DATABASE_URL` verir. Bu proje onu da destekler (bkz. `src/main/java/tr/edu/ebt522/social/db/Db.java`).
- Docker içinde Tomcat portu varsayılan `8080`’dir. Platform `PORT` veriyorsa, `docker/entrypoint.sh` bunu otomatik uygular.

### Render (örnek akış)
1. Projeyi GitHub’a push edin.
2. Render → New → Web Service → repo seçin.
3. Runtime: **Docker**
4. Port: `8080` (veya Render’ın yönlendirdiği port)
5. Env vars’ları ekleyin (`DB_URL`, `DB_USER`, `DB_PASSWORD`)
6. Deploy edin ve çıkan URL’yi `SABIS_TESLIM.txt` içine yazın.

## 3) Son Kontrol (Hocaya Link Vermeden Önce)
- `/home`, `/register`, `/login` açılıyor
- Admin ile giriş: `admin` / `Admin123!`
- `/forum` mesaj ekleme + listeleme çalışıyor
- `/people` listeleniyor, kişi sayfasında yorum ekleniyor
- `/photos` ve `/videos` listeleniyor, yorum ekleniyor
- `/activity` tablo listesi geliyor (admin işlemleri görünüyor)
- `/map` pan/zoom çalışıyor
