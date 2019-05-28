package carrot.player.umemura;

import carrot.game.JankenGameStatus.Subjective;
import carrot.judge.JankenHand;
import carrot.player.JankenPlayer;

public class UmemuraHighPointPlayer implements JankenPlayer{
	
	//引き分け回数をカウント
	//引き分け or 負けでカウントアップ
	private int counter = 0;
	private int max_loose = 3;

	//前回の自分の手
	private JankenHand previousHand;
	private JankenHand baseHand = JankenHand.CHOKI;
	
	@Override
	public void newGame() {
		// 何もしない
	}
	
	public JankenHand nextHand(Subjective currentGameStatus) {
		// 基本はチョキ
		//負け or 一定回数引き分けで前の相手に勝つ手
		//勝敗に関わらずチョキ
		
		if (!currentGameStatus.isFirstRound()) {
			
			if(counter >= max_loose) {
				counter=0;
				//System.out.println("--------------------------------------------------counter:"+counter);
				previousHand = currentGameStatus.previousOpponentHand.handToWin();
				return previousHand;
			}else {
				//前回勝てなかった場合にカウントアップ
				
				if(previousHand.handToLose() == currentGameStatus.previousOpponentHand) {
					counter=0;
				}else {
					counter++;
				}
				//System.out.println("--------------------------------------------------counter:"+counter);
				previousHand = baseHand;
				return previousHand;
			}
		}
		//System.out.println("--------------------------------------------------counter:"+counter);
		previousHand = baseHand;
		return previousHand;
	}
}