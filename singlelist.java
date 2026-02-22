/**
 * ジェネリックな単方向リンクリスト
 * 各ノードのデータに "pls" を付けて出力する kindLatin メソッドを持つ
 */
public class GenericLinkedList<T> {

    /**
     * リンクリストのノードを表す内部クラス
     * 外部から直接触らせないため private にしている
     */
    private class Node<E> {
        E data;          // ノードが保持するデータ
        Node<E> next;    // 次のノードへの参照

        /**
         * ノードのコンストラクタ
         * @param data ノードに格納するデータ
         * @param next 次のノードへの参照
         */
        Node(E data, Node<E> next) {
            this.data = data;
            this.next = next;
        }
    }

    // リンクリストの先頭（head）ノード
    private Node<T> head;

    /**
     * コンストラクタ
     * 空のリストを作成するため、head は null
     */
    public GenericLinkedList() {
        head = null;
    }

    /**
     * リストを先頭から順に走査し、
     * 各ノードの data.toString() に "pls" を連結して出力する
     */
    public void kindLatin() {

        // 走査用のポインタ（現在のノード）
        Node<T> current = head;

        // リストの末尾（null）に到達するまで繰り返す
        while (current != null) {

            // データを文字列に変換し "pls" を連結して出力
            System.out.println(current.data.toString() + "pls");

            // 次のノードへ進む
            current = current.next;
        }
    }
}
