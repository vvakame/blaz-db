# Blaz DB

## プロジェクト概要

GAEのDatastoreクローン(Slim3 APIレベルで一致)を作成する。
最終的な目標としては、MavenCentralが利用できればいつでもどこでもSlim3と同等の楽さのDBを手に入れる事である。目指す動作環境としては、GAE/J, Android, 通常のJVM上とする。

共通のAPIを用意するだけであり、裏側の実装は個別に差し替え可能なように配慮する。
互換性や制限について、あまり厳密にDatastoreと同じに統一する努力は行わない。例として、DatastoreではTxで5EGまでの制限があるが、SQLite版では特に制限を行わない。

## プロジェクトの構成の解説

### blazdb-parent

blazdb全体のmvn共通設定

### blazdb-core

blazdbのコアAPI 裏側が何であれ、実装時に利用するAPIはこのプロジェクトに含まれているもの。

### blazdb-apt

Slim3相当のMetaクラスを生成するプロジェクト。別途、Runtimeとなるプロジェクトが必要？

### blazdb-generated

もしかしたらblazdb-apt用ランタイムかもしれない。今のところ何もなし。

### blazdb-sqlite

実装にAndroid用SQLiteを利用したバージョン。

SQLite上にフレキシブルなテーブルを用意し、そこにデータを貯めこむことによりKVS likeな構造を実現する。

設計概要は以下のとおり。Key用のテーブルとValue用のテーブルの2つを用意。

Key用のテーブルの構成は以下の通り。

`CREATE TABLE KEY_TABLE (ID INTEGER, NAME TEXT, KIND TEXT,KEY_STR TEXT)`

通常のGAEのKeyとほとんど変わらず。ParentKeyのサポートがない問題がある。

Value用のテーブルの構成は以下のとおり。1レコードが1つのEntityの1つのPropertyを表現する。

`CREATE TABLE VALUE_TABLE (KEY_STR TEXT, KIND TEXT, NAME TEXT, TYPE TEXT, SEQ INTEGER, VAL_STR TEXT, VAL_INT INTEGER, VAL_REAL REAL, VAL_BYTES BLOB)`

注目するべきカラムについて解説を加える。

* KEY_STR
    * Keyの文字列表現を持つ。
* TYPE
    * Valueがどういう値なのかを表す。ここの値によって、どうやってValueを取り出すべきかが決定される。
    * KvsOpenHelper の T_ で始まる定数を参照の事。
    * 各値はListになりうるので、その場合 T_L_ から始まる定数を参照。
* SEQ
    * Listの順番を指定するのに使われているはず。
    * 今のところ有効ではないようだ。

### blazdb-android-sqlite

blazdb-sqlite のAndroid用。JDBCからAndroidのAPIに置き換えたもの。

### tryout

Slim3相当のMetaクラスを手書きで試作する、だった気がする。

### blazdb-compat-test

BlazDBはBareDatastoreを実装すればどんなDBが裏側でも利用できる。その互換性を検査するためのテスト。
net.vvakame.blaz.compat.RawDatastoreTestBase と net.vvakame.blaz.compat.Benchmark を継承したTestCaseを作る。
