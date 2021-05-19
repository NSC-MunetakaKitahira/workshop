# Carrot課題

## ゲームの基本ルール

- 一対一でじゃんけんを300回（ラウンド）やる。
- 結果によってスコア加算
  - グーで勝利：2点
  - チョキで勝利：4点
  - パーで勝利：5点
  - 引き分け：両者に1点
- 300ラウンド終了時にスコアが大きい方が勝者。
- 複数人でやるときは、総当たりとかトーナメントとかで適当に。

## 課題

- 上記のルールを元に戦うアルゴリズムを実装する。
  - JankenPlayerインタフェースを実装したクラスを作る。
- carrot.consoleのJankenConsoleでテスト対戦ができる。

## JankenPlayer実装の注意点

- Exceptionを発生（クラッシュ）させたら即時敗北
  - バグらないように気をつけてね。
  - クラッシュした場合、両者スコア0点扱いで勝敗が決まる。
  - 両者が同じラウンドでクラッシュしたら引き分け。