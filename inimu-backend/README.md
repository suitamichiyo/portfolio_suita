# inimu バックエンド

## 概要
inimu（浅草・香り体験店）のワークショップ予約・問い合わせ管理システム。

## 技術スタック
| 項目 | 内容 |
|---|---|
| 言語 | Java 21 |
| フレームワーク | Spring Boot 3.5.x |
| ビルドツール | Maven |
| DB（開発） | H2（インメモリ） |
| DB（本番） | PostgreSQL（Render） |
| ORM | MyBatis |
| テンプレートエンジン | Thymeleaf |
| 認証 | Spring Security |
| デプロイ | Render（Docker） |

## 機能一覧
| 機能 | エンドポイント |
|---|---|
| 空き枠一覧取得 | GET /api/v1/reservations/slots |
| 予約登録 | POST /api/v1/reservations |
| 問い合わせ送信 | POST /api/v1/contacts |
| 管理画面ログイン | GET/POST /admin/login |
| 管理ダッシュボード | GET /admin/dashboard |
| 予約ステータス更新 | POST /admin/reservations/{id}/status |
| 問い合わせステータス更新 | POST /admin/contacts/{id}/status |

## ディレクトリ構成
```
inimu-backend/
├── src/main/java/com/karainnovate/inimu/
│   ├── InimuApplication.java
│   ├── config/
│   │   ├── CorsConfig.java
│   │   └── SecurityConfig.java
│   ├── controller/
│   │   ├── AdminController.java
│   │   ├── ContactController.java
│   │   └── ReservationController.java
│   ├── mapper/
│   │   ├── AdminMapper.java
│   │   ├── ContactMapper.java
│   │   ├── ReservationMapper.java
│   │   └── WorkshopSlotMapper.java
│   ├── model/
│   │   ├── Contact.java
│   │   ├── Reservation.java
│   │   └── WorkshopSlot.java
│   └── service/
│       └── MailService.java
├── src/main/resources/
│   ├── application.properties
│   ├── schema.sql
│   ├── data.sql
│   └── templates/admin/
│       ├── login.html
│       └── dashboard.html
├── Dockerfile
└── pom.xml
```

## ローカル起動手順
```bash
# リポジトリのクローン
git clone https://github.com/suitamichiyo/portfolio_suita.git
cd portfolio_suita/inimu-backend

# 起動（H2インメモリDBで動作）
./mvnw spring-boot:run

# アクセス
# API: http://localhost:8080/api/v1/
# 管理画面: http://localhost:8080/admin/login
# H2コンソール: http://localhost:8080/h2-console
```

## 本番環境（Render）
URL: https://inimu-backend.onrender.com

### 必要な環境変数
| 変数名 | 説明 |
|---|---|
| DATABASE_URL | PostgreSQL接続URL（JDBC形式） |
| DB_DRIVER | org.postgresql.Driver |
| SQL_INIT_MODE | always（初回）/ never（以降） |
| H2_CONSOLE | false |
| ADMIN_USERNAME | 管理者ID |
| ADMIN_PASSWORD | 管理者パスワード |
| MAIL_DEV_MODE | false（本番メール送信時） |
| MAIL_USERNAME | 送信元メールアドレス |
| MAIL_PASSWORD | メールアプリパスワード |

## DB設計
### workshop_slots（WSスロット）
| カラム | 型 | 説明 |
|---|---|---|
| id | BIGSERIAL | PK |
| slot_date | DATE | 開催日 |
| start_time | TIME | 開始時刻 |
| end_time | TIME | 終了時刻 |
| capacity | INT | 定員（デフォルト10） |
| reserved_count | INT | 予約済み人数 |
| is_active | BOOLEAN | 公開フラグ |

### reservations（予約）
| カラム | 型 | 説明 |
|---|---|---|
| id | BIGSERIAL | PK |
| slot_id | BIGINT | FK→workshop_slots |
| name | VARCHAR(100) | 氏名 |
| name_kana | VARCHAR(100) | ふりがな |
| email | VARCHAR(255) | メールアドレス |
| phone | VARCHAR(20) | 電話番号 |
| num_people | INT | 人数 |
| allergy_note | VARCHAR(500) | アレルギー・備考 |
| status | VARCHAR(20) | CONFIRMED/CANCELLED |

### contacts（問い合わせ）
| カラム | 型 | 説明 |
|---|---|---|
| id | BIGSERIAL | PK |
| category | VARCHAR(50) | カテゴリ |
| name | VARCHAR(100) | 氏名 |
| company | VARCHAR(100) | 会社名 |
| email | VARCHAR(255) | メールアドレス |
| message | TEXT | 問い合わせ内容 |
| status | VARCHAR(20) | UNREAD/READ |

## 開発者
制作：訓練校ポートフォリオ課題
クライアント：inimu / 株式会社 KARA INNOVATE
