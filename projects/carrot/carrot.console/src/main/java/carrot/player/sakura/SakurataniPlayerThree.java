package carrot.player.sakura;

import java.util.Random;
import carrot.game.JankenGameStatus.Subjective;
import carrot.judge.JankenHand;
import carrot.player.JankenPlayer;

public class SakurataniPlayerThree implements JankenPlayer {

	/* 自分のポイントが相手以上になると次の手をランダムに決め、そうでなくなると相手の最も出す手に勝とうとするプレイヤー */

	private int gu;
	private int choki;
	private int pa;

	@Override
	public void newGame() {
		gu = choki = pa = 0;
	}

	@Override
	public JankenHand nextHand(Subjective currentGameStatus) {

		Random rnd;

		//最初のラウンドは必ずこちら
		if (currentGameStatus.ownScore >= currentGameStatus.opponentScore) {
			rnd = new Random();
			int random = rnd.nextInt(3);
			switch (random) {
			case 0:
				return JankenHand.GU;
			case 1:
				return JankenHand.PA;
			case 2:
				return JankenHand.CHOKI;
			default:
				throw new RuntimeException("error");
			}
		} else  {
			if (currentGameStatus.previousOpponentHand == JankenHand.CHOKI) {
				choki++;
			} else if (currentGameStatus.previousOpponentHand == JankenHand.PA) {
				pa++;
			} else {
				gu++;
			}
			return ph();
		}
	}

	private JankenHand ph() {
		if (choki > pa && choki > gu) {
			return JankenHand.GU;
		}
		if (gu > pa) {
			return JankenHand.PA;
		}
		return JankenHand.CHOKI;
	}
}
