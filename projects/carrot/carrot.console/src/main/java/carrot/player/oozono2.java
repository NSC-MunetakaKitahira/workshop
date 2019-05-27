package carrot.player;

import carrot.game.JankenGameStatus.Subjective;
import carrot.judge.JankenHand;
import carrot.player.JankenPlayer;

public class oozono2 implements JankenPlayer {

	@Override
	public void newGame() {
		// 何もしない

		gu = pa = choki = 0;

	}

	// じゃんけんの手
	private int gu = 0;
	private int choki = 0;
	private int pa = 0;

	@Override
	public JankenHand nextHand(Subjective currentGameStatus) {

		// 相手が出した手がグーの時グーを足していく
		if (currentGameStatus.previousOpponentHand == JankenHand.GU) {
			gu += 1;
			// 相手が出した手がチョキの時チョキ足していく
		} else if (currentGameStatus.previousOpponentHand == JankenHand.PA) {
			pa += 1;
			// 相手がグーとチョキ以外の時パー足していく
		} else {
			choki += 1;
		}
		// 一番多かった手を返す
		return mostFrequentlyHand();
	}

	private JankenHand mostFrequentlyHand() {
		// TODO Auto-generated method stub
		// パー５０回以上でないかつチョキが５０回以上でない時パーだす
		if (!(pa >= 50) && !(choki >= 50)) {
			return JankenHand.PA;
			// パーが１００回以上でないかつチョキが１００回以上でないときパーだす
		} else if (!(pa >= 100) && !(choki >= 100)) {
			return JankenHand.PA;
			// それ以外の場合はチョキ出す
		} else {
			return JankenHand.CHOKI;
		}
	}

}
