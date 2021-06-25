package carrot.game.player.entry;

import java.util.Random;

import carrot.game.judge.JankenHand;
import carrot.game.player.JankenPlayer;
import carrot.game.player.SubjectiveMatchStatus;

public class KUNII implements JankenPlayer{

	private Random random;
	private int myscore;

	@Override
	public void newGame() {
		this.random = new Random();
	}

	@Override
	public JankenHand nextHand(SubjectiveMatchStatus currentMatchStatus) {
		//最初は固定でチョキ
		if (currentMatchStatus.isFirstRound()) {
			return JankenHand.CHOKI;
		}
		//前ラウンドであいこならその手に負ける手を出す
		//勝ちならその手に勝つ手を出す
		//負けたらランダムに出す
		if (Score(currentMatchStatus)==1) {
			myscore = currentMatchStatus.ownScore;
			return currentMatchStatus.previousOpponentHand.handToLose();
		}else if (Score(currentMatchStatus)>=2) {
			myscore = currentMatchStatus.ownScore;
			return currentMatchStatus.previousOpponentHand.handToWin();
		}else {
			myscore = currentMatchStatus.ownScore;
			return Random();
		}
	}

	//前回の勝敗を見るメソッド
	private int Score(SubjectiveMatchStatus currentMatchStatus) {
		return currentMatchStatus.ownScore-myscore;
	}

	//ランダムに手を出すメソッド
	private JankenHand Random() {
		int value = random.nextInt(21);

		if (value>=0 && value<4) {
			return JankenHand.CHOKI;
		}

		else if (value>4 && value<=14) {
			return JankenHand.GU;
		}

		else {
			return JankenHand.PA;
		}
	}

}
