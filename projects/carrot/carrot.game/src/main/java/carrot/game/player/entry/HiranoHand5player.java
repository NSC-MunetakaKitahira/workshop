package carrot.game.player.entry;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import carrot.game.judge.JankenHand;
import carrot.game.player.JankenPlayer;
import carrot.game.player.SubjectiveMatchStatus;

public class HiranoHand5player implements JankenPlayer {

	// 5手の手札を保管するリストを作成(最初だけ6手)
	List<Integer> list = new ArrayList<Integer>();

	// ランダム
	private Random random;
	int value;

	@Override
	public void newGame() {

		this.random = new Random();
		// グ2チ2パ2を初期で持っている
		list.add(1);
		list.add(1);
		list.add(2);
		list.add(2);
		list.add(3);
		list.add(3);

	}

	@Override
	public JankenHand nextHand(SubjectiveMatchStatus status) {

		this.value = random.nextInt(5);

		// 1ラウンド目以外
		if (status.round != 1) {
			extracted(status);
		}

		switch (list.get(value)) {
		case (1):
			list.remove(value);
			return JankenHand.GU;

		case (2):
			list.remove(value);
			return JankenHand.CHOKI;

		case (3):
			list.remove(value);
			return JankenHand.PA;

		default:
		throw new RuntimeException("エラーです。");

		}


	}

	private void extracted(SubjectiveMatchStatus status) {
		switch (status.previousOpponentHand) {

		// 相手の出した手に勝てる手札を追加
		case GU:
			list.add(3);
			break;
		case CHOKI:
			list.add(1);
			break;
		case PA:
			list.add(2);
			break;
		}
	}

}
