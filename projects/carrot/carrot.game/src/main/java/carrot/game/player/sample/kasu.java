//勝ってるときチョキ

package carrot.game.player.sample;

import carrot.game.judge.JankenHand;
import carrot.game.player.JankenPlayer;
import carrot.game.player.SubjectiveMatchStatus;

public class kasu implements JankenPlayer{

	@Override
	public void newGame() {

	}

	@Override
	public JankenHand nextHand(SubjectiveMatchStatus currentMatchStatus) {

		if (currentMatchStatus.ownScore<currentMatchStatus.opponentScore) {
			return JankenHand.PA;
		}

		if (currentMatchStatus.ownScore>currentMatchStatus.opponentScore) {
			return JankenHand.CHOKI;
		}

		else {
			return JankenHand.GU;
		}
	}
}
