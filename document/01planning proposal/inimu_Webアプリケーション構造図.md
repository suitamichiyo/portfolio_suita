# inimu Webアプリケーション構造図
**サービス名：** inimu集客増を目指した来店理由形成型ホームページ再編
**運営：** 株式会社 KARA INNOVATE
**技術スタック：** Java / Spring Boot / Thymeleaf / MyBatis / MySQL / Spring Security
**デプロイ：** Render
**最終更新：** 2025年5月

---

## アプリケーション全体構造

```
inimu.jp（Spring Boot アプリケーション）
│
├── [公開サイト] inimu.jp/
│     フロントエンド（HTML / CSS / JavaScript）
│     Spring Boot REST API と連携
│
└── [管理システム] inimu.jp/admin/
      Spring Boot + Thymeleaf
      Spring Security（認証・権限管理）
```

---

## 公開サイト ページ構造（フロントエンド）

```
inimu.jp/
│
├── /                                    TOP
├── /workshop                            WORKSHOP
│   └── /workshop/reserve                RESERVE（F2）
│         └── /workshop/reserve/complete  予約完了サンクス
├── /products                            PRODUCTS
│   ├── /products/hatenko                破天荒 シリーズトップ
│   ├── /products/perfumers              PERFUMERS シリーズトップ
│   └── /products/wanowa                 WANOWA シリーズトップ
│         ├── /products/wanowa/kokuzo-yuzu       国造ゆず
│         ├── /products/wanowa/kakihara-hinoki   加子母ひのき
│         ├── /products/wanowa/wazuka-cha        和束茶
│         ├── /products/wanowa/tosa-buntan       土佐文旦
│         ├── /products/wanowa/nakagawa-hakka    中川町和薄荷
│         └── /products/wanowa/yamato-tachibana  大和橘
├── /story                               STORY
├── /visit                               VISIT
├── /online                              ONLINE
├── /contact                             CONTACT（F2）
│   └── /contact/complete                送信完了
└── /error                               エラーページ（404 / 500）
```

---

## 管理システム 画面構造（Spring Boot + Thymeleaf）

```
/admin/
│
├── /admin/login                         ログイン画面
│     メールアドレス＋パスワード認証
│     未認証アクセスはここへリダイレクト（Spring Security）
│
├── /admin/dashboard                     ダッシュボード（ホーム）
│     本日の予約件数 / 今月売上速報
│     未対応問い合わせ件数 / 直近7日カレンダープレビュー
│
├── /admin/reservations/                 予約管理
│   ├── /admin/reservations              予約一覧・検索
│   │     日付 / ステータス / 氏名 で絞り込み・CSV出力
│   ├── /admin/reservations/new          予約新規登録（当日飛び込み対応）
│   └── /admin/reservations/{id}         予約詳細・編集
│         ステータス変更（確定 / キャンセル / 入場済）
│         スタッフメモ入力
│
├── /admin/customers/                    顧客管理
│   ├── /admin/customers                 顧客一覧・検索
│   │     氏名 / メール / 電話 / LINE登録 / リピーター フィルター・CSV出力
│   ├── /admin/customers/new             顧客新規登録
│   └── /admin/customers/{id}            顧客詳細
│         基本情報 / 予約履歴 / LINE登録ステータス / スタッフメモ
│
├── /admin/contacts/                     問い合わせ管理
│   ├── /admin/contacts                  問い合わせ一覧
│   │     カテゴリ（一般 / WS / ギフト・法人 / OEM開発）
│   │     ステータス（未対応 / 対応中 / 完了）で絞り込み
│   └── /admin/contacts/{id}             問い合わせ詳細
│         対応メモ入力 / ステータス変更
│
├── /admin/products/                     商品管理（Should）
│   ├── /admin/products                  商品一覧
│   │     カテゴリ別（WANOWA / 破天荒 / PERFUMERS等）
│   │     表示 / 非表示 / 看板商品フラグ 切り替え
│   └── /admin/products/{id}             商品情報編集
│         商品名 / 説明文 / 価格 / 素材情報 / 産地 / 画像
│
├── /admin/reports/                      売上・レポート（管理者のみ・Should）
│   └── /admin/reports                   売上レポート
│         期間指定（月次 / 年次 / カスタム）
│         WS実績サマリー（予約件数 / キャンセル率 / じゃらん比率等）
│         CSV・PDF出力
│
└── /admin/settings/                     設定（管理者のみ）
    ├── /admin/settings/calendar         予約カレンダー設定
    │     営業日（土日祝のみ）/ 時間帯 / 定員数 / 臨時休業日
    ├── /admin/settings/plans            WSプラン設定
    │     プラン名 / 料金（現在¥5,500/名）/ 所要時間 / 定員 / 公開切り替え
    ├── /admin/settings/users            ユーザー管理
    │     スタッフアカウント 新規作成・編集・削除
    │     権限設定（管理者 / スタッフ）
    └── /admin/settings/emails           メールテンプレート設定（Should）
          予約確認メール / 問い合わせ自動返信メール テンプレート編集
```

---

## REST API 構造（フロントエンド連携用）

```
/api/v1/
│
├── /api/v1/reservations/slots           GET  空き枠一覧取得（日付指定）
│     RESERVEページのカレンダー表示に使用
│
├── /api/v1/reservations                 POST 予約新規登録
│     RESERVEページのフォーム送信
│
├── /api/v1/reservations/{id}            GET  予約詳細取得
│
└── /api/v1/contacts                     POST 問い合わせ送信
      CONTACTページのフォーム送信
```

---

## Spring Security アクセス制御

```
公開URL（認証不要）
  inimu.jp/ 以下 全ページ
  /api/v1/ 以下 全エンドポイント
  /admin/login

管理者のみアクセス可
  /admin/reports/**
  /admin/settings/**

スタッフ以上アクセス可（管理者・スタッフ共通）
  /admin/dashboard
  /admin/reservations/**
  /admin/customers/**
  /admin/contacts/**
  /admin/products/**

未認証アクセス
  → /admin/login へリダイレクト
```

---

## データベース 主要テーブル構成

```
DB: inimu（MySQL本番 / H2開発・テスト）
│
├── users                  管理ユーザー（管理者 / スタッフ）
├── reservations           予約情報
├── customers              顧客情報
├── contacts               問い合わせ情報
├── products               商品情報
├── workshop_slots         WSスロット（日付 / 時間帯 / 定員 / 残枠）
└── email_templates        メールテンプレート
```

---

## 権限別アクセス可能機能

| 機能 | 管理者 | スタッフ |
|---|---|---|
| ダッシュボード | ✅ | ✅ |
| 予約管理（一覧・詳細・編集） | ✅ | ✅ |
| 顧客管理 | ✅ | ✅ |
| 問い合わせ管理 | ✅ | ✅ |
| 商品管理 | ✅ | ✅ |
| 売上レポート | ✅ | ❌ |
| 予約カレンダー設定 | ✅ | ❌ |
| WSプラン設定 | ✅ | ❌ |
| ユーザー管理 | ✅ | ❌ |
| メールテンプレート設定 | ✅ | ❌ |

---

*inimu Webアプリケーション構造図 — 株式会社 KARA INNOVATE*
