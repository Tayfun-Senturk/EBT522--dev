-- EBT 522 - Sosyal Paylaşım Projesi (PostgreSQL)
-- Şema: kullanıcılar, forum, kişi/foto/video yorumları, foto etiketleri

CREATE TABLE IF NOT EXISTS users (
  id BIGSERIAL PRIMARY KEY,
  username VARCHAR(50) NOT NULL UNIQUE,
  email VARCHAR(120) NOT NULL UNIQUE,

  password_hash VARCHAR(256) NOT NULL,
  password_salt VARCHAR(256) NOT NULL,
  password_iterations INT NOT NULL,

  first_name VARCHAR(60) NOT NULL,
  last_name  VARCHAR(60) NOT NULL,

  gender VARCHAR(16),
  hobbies TEXT[],
  city VARCHAR(60),
  birth_date DATE,

  phone VARCHAR(40),
  address TEXT,
  about TEXT,

  current_school VARCHAR(120),
  current_job VARCHAR(120),
  web_url VARCHAR(200),
  facebook_id VARCHAR(120),
  twitter_handle VARCHAR(120),

  avatar_path VARCHAR(300),

  role VARCHAR(16) NOT NULL DEFAULT 'USER',

  created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_users_role ON users(role);

CREATE TABLE IF NOT EXISTS forum_posts (
  id BIGSERIAL PRIMARY KEY,
  user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
  content TEXT NOT NULL,
  created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_forum_posts_created_at ON forum_posts(created_at DESC);

CREATE TABLE IF NOT EXISTS person_comments (
  id BIGSERIAL PRIMARY KEY,
  person_user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
  author_user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
  content TEXT NOT NULL,
  created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_person_comments_person ON person_comments(person_user_id, created_at DESC);

CREATE TABLE IF NOT EXISTS photos (
  id BIGSERIAL PRIMARY KEY,
  title VARCHAR(120),
  image_path VARCHAR(300) NOT NULL,
  thumb_path VARCHAR(300) NOT NULL,
  uploaded_by BIGINT REFERENCES users(id) ON DELETE SET NULL,
  created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_photos_created_at ON photos(created_at DESC);

CREATE TABLE IF NOT EXISTS photo_comments (
  id BIGSERIAL PRIMARY KEY,
  photo_id BIGINT NOT NULL REFERENCES photos(id) ON DELETE CASCADE,
  author_user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
  content TEXT NOT NULL,
  created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_photo_comments_photo ON photo_comments(photo_id, created_at DESC);

-- Ekstra puan: foto etiketleri (coords/shape)
CREATE TABLE IF NOT EXISTS photo_tags (
  id BIGSERIAL PRIMARY KEY,
  photo_id BIGINT NOT NULL REFERENCES photos(id) ON DELETE CASCADE,
  tagged_user_id BIGINT REFERENCES users(id) ON DELETE SET NULL,
  shape VARCHAR(10) NOT NULL,
  coords VARCHAR(200) NOT NULL,
  label VARCHAR(120),
  created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_photo_tags_photo ON photo_tags(photo_id);

CREATE TABLE IF NOT EXISTS videos (
  id BIGSERIAL PRIMARY KEY,
  title VARCHAR(120),
  youtube_id VARCHAR(20) NOT NULL,
  thumb_path VARCHAR(300),
  created_by BIGINT REFERENCES users(id) ON DELETE SET NULL,
  created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_videos_created_at ON videos(created_at DESC);

CREATE TABLE IF NOT EXISTS video_comments (
  id BIGSERIAL PRIMARY KEY,
  video_id BIGINT NOT NULL REFERENCES videos(id) ON DELETE CASCADE,
  author_user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
  content TEXT NOT NULL,
  created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_video_comments_video ON video_comments(video_id, created_at DESC);
