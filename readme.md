## 参加手順とか

1. 自分のGitHubアカウントを作る。
1. 北平に参加したい旨とアカウントを伝える。
	- 北平がこのリポジトリへの参加登録手続きをしておきます。
1. SourceTreeをインストールする。
	- 他のGitクライアントでも良いですが、使い方は自分で調べてください。
	1. [Atlassian公式サイト](https://ja.atlassian.com/software/sourcetree)からダウンロードしてインストール開始。
	1. 恐らくAtlassianアカウントが必要になるので、登録する（無料です）。
	1. GitHub（とか他のサービス）との紐付けをするか訊かれるが、ここでやっても後でやっても良い。
	1. SourceTreeが利用するGit本体をどうするか訊かれるが、おすすめは「内臓Git」。SourceTreeがインストールしてくれるので、手軽。
1. SourceTreeでこのリポジトリをクローンする。
	- クローン手順はバージョンによって異なる。下記手順は2.1.11.0のもの。
	1. メニュー「ファイル」→「新規 / クローンを作成する」
	1. 「元のパス/URL」の欄に「https://github.com/NSC-MunetakaKitahira/workshop.git 」と入力。
	1. 「保存先のパス」はお好きなように。
	1. 「名前」もお好きなように。
	1. 「クローン」ボタンをクリックしたらソースがダウンロードされて完了。
1. Eclipseをインストールする。
	- 他のIDEでも良いですが、使い方は自分で調べてください。
	1. [Eclipse公式サイト](https://www.eclipse.org/)からダウンロードしてインストール開始。
	1. 特に設定とか気にしなくて良いと思います。恐らく。
1. Eclipseにソースを読み込む。
	- 以下の手順はOxygenバージョンのものです。
	1. Eclipseを起動する。
	1. メニュー「File」→「Import」
	1. Importダイアログで「Gradle」フォルダを開き、「Existing Gradle Project」を選択、「Next」ボタンをクリック。
	1. なんか長い文章が出るが気にせず「Next」
	1. 「Project root directory」に先ほどクローンしたソースコードのフォルダの中の、「projects/apricot」フォルダを指定。
		- 「build.gradle」というファイルがあるフォルダです。
	1. 正しいフォルダを指定すれば「Finish」ボタンが押せるようになるので、クリック。
1. EclipseでjavaファイルをUTF-8として読み込めるようにする
	- SourceTreeでの文字化けを防ぐため、文字コードはUTF-8を使います。
	- [こちらの記事](http://proengineer.internous.co.jp/content/columnfeature/9147#12)に従って、Eclipseのデフォルトの文字コードを変更してください。
1. コンソールアプリの実行方法
	- 再生ボタンっぽいアイコンを押せば実行できるはずです。
	- 虫っぽいアイコンがデバッグ実行です。

以上で参加できるようになりました。

## 現在のお題：apricot

汚いソースコードをみんなでリファクタリングしましょう。
参加する場合は、[こちら](https://github.com/NSC-MunetakaKitahira/workshop/issues/3)を見てください。