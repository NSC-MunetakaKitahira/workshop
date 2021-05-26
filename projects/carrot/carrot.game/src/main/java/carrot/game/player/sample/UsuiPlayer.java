package carrot.game.player.sample;

import java.util.Random;

import carrot.game.judge.JankenHand;
import carrot.game.player.JankenPlayer;
import carrot.game.player.SubjectiveMatchStatus;

public class UsuiPlayer implements JankenPlayer {

	private boolean maybeRandom;
	private Random random;

	@Override
	public void newGame() {

		maybeRandom = false;
		this.random = new Random();
	}

	@Override
	public JankenHand nextHand(SubjectiveMatchStatus currentMatchStatus) {

		if (this.maybeRandom) {
			return JankenHand.CHOKI;
		}

		// 最初チョキ30回
		if (currentMatchStatus.round <= 30) {
			// 30回時にランダムの可能性ならチョキ
			int myScore = currentMatchStatus.ownScore;
			if (currentMatchStatus.round == 30 && myScore <= 60 && myScore >= 40) {
				this.maybeRandom = true;
			}
			return JankenHand.CHOKI;
		}
		// 残りラウンド*2-(自分の得点-相手の得点) >=1ならチョキ
		int restRound = currentMatchStatus.maxRound - currentMatchStatus.round;
		int winOwnScore = currentMatchStatus.ownScore - currentMatchStatus.opponentScore;
		if (winOwnScore - restRound * 2 >= 1) {

			return JankenHand.CHOKI;
		}
		// 残りラウンド数
		// ランダムじゃないならランダムになる
		if (currentMatchStatus.round >= 30) {
			int value = random.nextInt(99);
			if (value < 50) {
				return JankenHand.CHOKI;
			}
			if (value >= 50 && value < 80) {
				return JankenHand.PA;
			}
			if (value >= 80) {
				return JankenHand.GU;
			}
		}
		return JankenHand.CHOKI;

	}
}
