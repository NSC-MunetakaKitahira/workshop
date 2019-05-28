package carrot.player;

import carrot.game.JankenGameStatus.Subjective;
import carrot.judge.JankenHand;
import carrot.player.JankenPlayer;

public class oozono3 implements JankenPlayer {

	@Override
	public void newGame() {
		// 何もしない
	}

	// じゃんけんの手
	private int gu = 0;
	private int choki = 0;
	private int pa = 0;

	// 相手が出した手を格納しておく
	int[] history = new int[300];

	@Override
	public JankenHand nextHand(Subjective currentGameStatus) {

		history[currentGameStatus.round - 1] = 0;

		// if文たすこと
		// 相手が出している手をカウントする
		// 相手が出した手がグーの時グーを足していく
		if (currentGameStatus.previousOpponentHand == JankenHand.GU) {
			gu ++;
			// 相手が出した手がチョキの時チョキ足していく
		} else if (currentGameStatus.previousOpponentHand == JankenHand.PA) {
			pa ++;
			// 相手がグーとチョキ以外の時パー足していく
		} else {
			choki ++;
		}
		// 一番多かった手を返す
		return mostFrequentlyHand();
	}

	// 相手が多く出している手に勝てる手をだす
	// グーが一番多いときパーだす
	private JankenHand mostFrequentlyHand() {
		if (gu > choki && gu > pa) {
			return JankenHand.PA;
			// パーが多いときチョキ出す
		} else if (pa > gu && pa > choki) {
			return JankenHand.CHOKI;
			// それ以外の時はチョキが出る時が多いからグーだす
		} else {
			return JankenHand.GU;
		}
	}
}