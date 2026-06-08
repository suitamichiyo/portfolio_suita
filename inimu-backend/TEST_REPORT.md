# テスト報告書

## プロジェクト情報
| 項目 | 内容 |
|---|---|
| プロジェクト名 | inimu バックエンドシステム |
| テスト実施日 | 2026年6月6日 |
| テスト環境 | ローカル（H2）/ 本番（Render + PostgreSQL） |
| テスト実施者 | suitamichiyo |

## テスト結果サマリー
| カテゴリ | テスト数 | 合格 | 不合格 |
|---|---|---|---|
| API（予約） | 3 | 3 | 0 |
| API（問い合わせ） | 2 | 2 | 0 |
| 管理画面 | 4 | 4 | 0 |
| 本番デプロイ | 3 | 3 | 0 |
| **合計** | **12** | **12** | **0** |

## 詳細テスト結果

### 1. 予約API

| # | テスト内容 | 期待結果 | 実際の結果 | 合否 |
|---|---|---|---|---|
| 1 | GET /api/v1/reservations/slots?from=2026-06-01&to=2026-06-30 | 12スロット返却 | 12スロット返却・日付/時刻正常 | ✅ |
| 2 | POST /api/v1/reservations（正常データ） | 予約完了・reservationId返却 | {"message":"予約が完了しました","reservationId":1} | ✅ |
| 3 | POST /api/v1/reservations（メール送信） | devModeログ出力 | お客様・管理者宛メール内容がログに出力 | ✅ |

### 2. 問い合わせAPI

| # | テスト内容 | 期待結果 | 実際の結果 | 合否 |
|---|---|---|---|---|
| 4 | POST /api/v1/contacts（正常データ） | 受付完了メッセージ返却 | {"message":"お問い合わせを受け付けました"} | ✅ |
| 5 | POST /api/v1/contacts（自動返信メール） | devModeログ出力 | お客様宛自動返信メール内容がログに出力 | ✅ |

### 3. 管理画面

| # | テスト内容 | 期待結果 | 実際の結果 | 合否 |
|---|---|---|---|---|
| 6 | GET /admin/login | ログイン画面表示 | ログイン画面正常表示 | ✅ |
| 7 | POST /admin/login（正常認証） | ダッシュボードへリダイレクト | /admin/dashboard へ遷移 | ✅ |
| 8 | POST /admin/login（認証失敗） | エラーメッセージ表示 | 「IDまたはパスワードが正しくありません」表示 | ✅ |
| 9 | GET /admin/dashboard | 予約・問い合わせ一覧表示 | 予約一覧・お問い合わせ一覧テーブル正常表示 | ✅ |

### 4. 本番デプロイ（Render）

| # | テスト内容 | 期待結果 | 実際の結果 | 合否 |
|---|---|---|---|---|
| 10 | Dockerビルド | ビルド成功 | eclipse-temurin:21でビルド成功 | ✅ |
| 11 | PostgreSQL接続 | DB接続成功・テーブル作成 | HikariPool接続成功・schema.sql実行確認 | ✅ |
| 12 | 本番管理画面アクセス | ログイン・ダッシュボード表示 | https://inimu-backend.onrender.com/admin/login 正常動作 | ✅ |

## 確認済みエンドポイント一覧
| エンドポイント | メソッド | 認証 | 状態 |
|---|---|---|---|
| /api/v1/reservations/slots | GET | 不要 | ✅ |
| /api/v1/reservations | POST | 不要 | ✅ |
| /api/v1/contacts | POST | 不要 | ✅ |
| /admin/login | GET/POST | 不要 | ✅ |
| /admin/dashboard | GET | 必要 | ✅ |
| /admin/reservations/{id}/status | POST | 必要 | ✅ |
| /admin/contacts/{id}/status | POST | 必要 | ✅ |

## 本番環境情報
| 項目 | 内容 |
|---|---|
| バックエンドURL | https://inimu-backend.onrender.com |
| DB | PostgreSQL（Render Free） |
| DB有効期限 | 2026年7月6日 |
| 管理画面URL | https://inimu-backend.onrender.com/admin/login |

## 備考
- Free プランのため、非アクティブ時にスピンダウンが発生し初回アクセスに50秒程度かかる場合あり
- MAIL_DEV_MODE=true のため本番メール送信は未実施（ログ出力で動作確認済み）
- SQL_INIT_MODE=always のため再デプロイ時にスロットデータが再投入される
