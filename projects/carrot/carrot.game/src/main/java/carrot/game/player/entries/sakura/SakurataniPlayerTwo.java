package carrot.game.player.entries.sakura;

import java.util.Random;

import carrot.game.judge.JankenHand;
import carrot.game.player.JankenPlayer;
import carrot.game.player.SubjectiveGameStatus;

public class SakurataniPlayerTwo implements JankenPlayer {

	/* 初手をランダムに出す。ポイントが相手より低い場合、直前の相手の手に勝てる手を、高い場合、直前の相手の手に負ける手を出すプレイヤー */

	@Override
	public void newGame() {
	}

	@Override
	public JankenHand nextHand(SubjectiveGameStatus currentGameStatus) {

		Random rnd;

		if (currentGameStatus.isFirstRound()) {
			rnd = new Random();
			int random = rnd.nextInt(3);
			switch (random) {
			case 0:
				return JankenHand.GU;
			case 1:
				return JankenHand.PA;
			case 2:
				return JankenHand.CHOKI;
			}
		} else if (currentGameStatus.ownScore >= currentGameStatus.opponentScore) {
			return currentGameStatus.previousOpponentHand.handToLose();
		} else {
			return currentGameStatus.previousOpponentHand.handToWin();
		}
		return null;
	}
}
