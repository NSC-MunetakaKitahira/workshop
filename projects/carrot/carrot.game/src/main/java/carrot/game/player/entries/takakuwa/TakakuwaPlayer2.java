package carrot.game.player.entries.takakuwa;

import carrot.game.judge.JankenHand;
import carrot.game.player.JankenPlayer;
import carrot.game.player.SubjectiveGameStatus;

public class TakakuwaPlayer2 implements JankenPlayer {
	
	//変数定義
	private int gu = 0;
	private int choki = 0;
	private int pa = 0;

	@Override
	public void newGame() {
		
		//初期化
		gu = 0;
		choki = 0;
		pa = 0;
	}
	
	@Override
	public JankenHand nextHand(SubjectiveGameStatus currentGameStatus) {
		
		
		//相手の出した手をカウント
		if (!(currentGameStatus.isFirstRound())) {
			switch(currentGameStatus.previousOpponentHand.toString()) {
			case "GU":
				gu ++;
				break;
			case "CHOKI":
				choki ++;
				break;
			default:
				pa ++;
			}
		}
		
		//負けにくい手を選ぶ
		if(gu >= choki && gu > pa) {
			if(choki > pa) return JankenHand.GU;
			else return JankenHand.PA;
		}
		else if(choki > gu && choki >= pa) {
			if(gu > pa) return JankenHand.GU;
			else return JankenHand.CHOKI;
		}
		else if(pa >= gu && pa > choki) {
			if(gu > choki) return JankenHand.PA;
			else return JankenHand.CHOKI;
		}
		else return JankenHand.CHOKI;
		
	}
}
