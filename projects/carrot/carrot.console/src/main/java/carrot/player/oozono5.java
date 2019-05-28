package carrot.player;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import carrot.game.JankenGame;
import carrot.game.JankenGameStatus.Subjective;
import carrot.judge.JankenHand;

//相手の手を格納しておく
public class oozono5 implements JankenPlayer {

	@Override
	public void newGame() {
		// 何もしない
	}

	// じゃんけんの手
	private int gu = 0;
	private int choki = 0;
	private int pa = 0;

	@Override
	public JankenHand nextHand(Subjective currentGameStatus) {

		// 相手が出した当てを格納する
		List<JankenGame> games = new ArrayList<>(Arrays.asList(gu, choki, pa));

		System.out.println(list.size());
		return list.size();
	}

	private JankenHand list.size() {
			
		}
}
