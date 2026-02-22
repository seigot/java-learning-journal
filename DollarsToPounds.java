import javafx.application.Application;              // JavaFXアプリの基底クラス
import javafx.geometry.Pos;                         // レイアウトの配置指定用
import javafx.scene.Scene;                          // 画面全体（Scene）
import javafx.scene.control.Alert;                  // エラーダイアログ用
import javafx.scene.control.Button;                 // ボタン
import javafx.scene.control.TextField;              // 入力欄
import javafx.scene.layout.HBox;                    // 横並びレイアウト
import javafx.scene.layout.VBox;                    // 縦並びレイアウト
import javafx.scene.text.Text;
import javafx.scene.control.Label;                  // テキスト表示用
import javafx.stage.Stage;                          // ウィンドウ本体

/**
 * ドル金額を入力し、ポンドに換算して表示する JavaFX アプリ
 */
public class DollarsToPounds extends Application {

    // ドル → ポンドの為替レート（固定値）
    final static double EXCHANGE_RATE = 0.81;

    /**
     * JavaFXアプリのエントリポイント
     * ウィンドウが起動すると自動的に呼ばれる
     */
    @Override
    public void start(Stage stage) {

        // 「Input value: $」と表示するラベル
        Label valueLbl = new Label("Input value: $");

        // 換算結果（ポンド）を表示するラベル
        Label poundsLbl = new Label();

        // ドル金額を入力するテキストフィールド
        TextField dollarsTxt = new TextField();

        // 「Exchange」ボタンを作成
        Button exchangeBtn = new Button();
        exchangeBtn.setText("Exchange");

        /**
         * Exchangeボタンがクリックされたときの処理
         */
        exchangeBtn.setOnAction(event -> {

            // 入力された文字列を取得
            String dollarsStr = dollarsTxt.getCharacters().toString();

            try {
                // 入力文字列を double 型に変換
                double dollars = Double.parseDouble(dollarsStr);

                // 為替レートを使ってポンドに換算
                double pounds = exchange(dollars);

                // 小数点以下2桁で結果を表示
                poundsLbl.setText(String.format("%.2f", pounds));

            } catch (NumberFormatException e) {
                // 数値に変換できなかった場合（文字入力など）

                // エラー用のダイアログを作成
                Alert a = new Alert(Alert.AlertType.ERROR);

                // ダイアログのタイトル
                a.setTitle("Error");

                // エラーの見出し
                a.setHeaderText("Invalid Dollar Amount");

                // エラーの詳細メッセージ
                a.setContentText("Please only use digits.");

                // ダイアログを表示してユーザの操作を待つ
                a.showAndWait();
            }
        });

        // 入力ラベルと入力欄を横並びに配置
        HBox input = new HBox();
        input.setAlignment(Pos.CENTER);
        input.getChildren().addAll(valueLbl, dollarsTxt);

        // 画面全体を縦並びで構成
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(8);

        // 上から「入力欄」「ボタン」「結果表示」の順に配置
        root.getChildren().addAll(
            input,
            exchangeBtn,
            poundsLbl
        );

        // シーン（画面）を作成（400×400）
        Scene scene = new Scene(root, 400, 400);

        // ウィンドウタイトルを設定
        stage.setTitle("Dollars to Pounds");

        // シーンをウィンドウに設定
        stage.setScene(scene);

        // ウィンドウを表示
        stage.show();
    }

    /**
     * ドル金額をポンドに換算するメソッド
     */
    private double exchange(double dollars) {
        return dollars * EXCHANGE_RATE;
    }
}