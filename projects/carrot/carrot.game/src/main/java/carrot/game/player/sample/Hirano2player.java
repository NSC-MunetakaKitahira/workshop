package carrot.game.player.sample;

import java.util.Random;

import carrot.game.judge.JankenHand;
import carrot.game.player.JankenPlayer;
import carrot.game.player.SubjectiveMatchStatus;

public class Hirano2player implements JankenPlayer {

	private Random random;

	int value;
	int value2;

	public int gu;
	public int choki;
	public int pa;

	@Override
	public void newGame() {

		gu = choki = pa = 0;
		this.random = new Random();
		this.value = random.nextInt(100);

	}

	@Override
	public JankenHand nextHand(SubjectiveMatchStatus status) {

		this.value2 = random.nextInt(100);

		// 最初のラウンドはグーを出す
		if (status.isFirstRound()) {
			return JankenHand.GU;
		}

		// 相手の得点より50点多いなら以下に関係なくチョキを出す
		if (status.ownScore > status.opponentScore + 50) {
			return JankenHand.CHOKI;

		} else {

			// 20の倍数のラウンドで作戦をリセット
			if (status.round % 20 == 0) {
				this.value = random.nextInt(100);
			}

			// 4割でランダムに手を出し続ける
			if (value <= 40) {
				if (value2 <= 30) {
					return JankenHand.GU;

				} else if (value2 <= 70) {
					return JankenHand.CHOKI;

				} else {
					return JankenHand.PA;
				}

			// 3割で1つ前の相手の手に勝てる手を出し続ける
			} else if (value <= 70) {
				return status.previousOpponentHand.handToWin();

			// 3割でチョキだけ出し続ける
			} else {
				return JankenHand.CHOKI;
			}
		}

	}
}