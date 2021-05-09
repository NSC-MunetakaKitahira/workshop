package carrot.game.log;

import carrot.game.judge.JankenJudgement;

public class RoundLog {
	
	public final JankenJudgement result;

	public RoundLog(JankenJudgement result) {
		this.result = result;
	}
}
