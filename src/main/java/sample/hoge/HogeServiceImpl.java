package sample.hoge;

public class HogeServiceImpl implements HogeService {
    @Override
    public void exec(String message) {
        System.out.println("HogeService received: " + message);
        // ここでメッセージを処理する簡易ロジックを実装
    }
}