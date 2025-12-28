-- Örnek veri (Admin ve demo içerik).
-- Not: Admin şifresi PBKDF2 hash ile kaydedilmiştir.
-- Admin giriş:
--   kullanıcı adı: admin
--   şifre: Admin123!

INSERT INTO users (
  username, email,
  password_hash, password_salt, password_iterations,
  first_name, last_name,
  gender, hobbies, city, birth_date,
  phone, address, about,
  current_school, current_job, web_url, facebook_id, twitter_handle,
  avatar_path, role
) VALUES (
  'admin', 'admin@example.com',
  'PBKDF2WithHmacSHA256:310000:mENQnklv8K9wNYkLxrmpdQ==:DMN+8mxFp8bu8mIqhilgtDCP5hJjav+MIN62JounzB0=',
  'mENQnklv8K9wNYkLxrmpdQ==',
  310000,
  'Admin', 'Kullanıcı',
  'OTHER', ARRAY['yönetim','moderasyon'], 'Ankara', '2000-01-01',
  '0000 000 00 00', 'Örnek adres', 'Sistemi yönetir.',
  'EBT 522', 'Yönetici', 'https://example.com', 'admin', 'admin',
  NULL, 'ADMIN'
) ON CONFLICT (username) DO NOTHING;

INSERT INTO users (
  username, email,
  password_hash, password_salt, password_iterations,
  first_name, last_name,
  gender, hobbies, city,
  phone, address,
  current_school, current_job,
  avatar_path, role
) VALUES (
  'demo', 'demo@example.com',
  'PBKDF2WithHmacSHA256:310000:Q0ej3BFAo5C/WsT5RQqeUA==:Cp29EywNSlrMdnifdj2KZZXyVwDYi259LzpvZSn796Q=',
  'Q0ej3BFAo5C/WsT5RQqeUA==',
  310000,
  'Demo', 'Öğrenci',
  'F', ARRAY['sinema','müzik'], 'İstanbul',
  '0555 555 55 55', 'İstanbul / Türkiye',
  'Üniversite', 'Öğrenci',
  NULL, 'USER'
) ON CONFLICT (username) DO NOTHING;

-- Örnek foto/video kayıtları (statik dosyalar ve YouTube ID)
INSERT INTO photos (title, image_path, thumb_path, uploaded_by)
VALUES
  ('Örnek Fotoğraf 1', 'assets/img/photos/photo-1.png', 'assets/img/photos/photo-1-thumb.png', (SELECT id FROM users WHERE username='admin')),
  ('Örnek Fotoğraf 2', 'assets/img/photos/photo-2.png', 'assets/img/photos/photo-2-thumb.png', (SELECT id FROM users WHERE username='admin'))
ON CONFLICT DO NOTHING;

INSERT INTO videos (title, youtube_id, thumb_path, created_by)
VALUES
  ('Örnek Video', 'dQw4w9WgXcQ', 'https://img.youtube.com/vi/dQw4w9WgXcQ/hqdefault.jpg', (SELECT id FROM users WHERE username='admin'))
ON CONFLICT DO NOTHING;
