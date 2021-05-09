package carrot.game.log;

import java.util.ArrayList;
import java.util.List;

import carrot.game.judge.JankenJudgement;
import carrot.game.match.JankenMatch;
import carrot.game.match.JankenMatchResult;
import carrot.game.player.JankenPlayer;

public class MatchLog {

	public final int matchCount;
	public final String player1;
	public final String player2;
	public JankenMatchResult result = null;
	public String winner = null;
	public final List<RoundLog> rounds = new ArrayList<>();

	public MatchLog(int count, JankenPlayer player1, JankenPlayer player2) {
		this.matchCount = count;
		this.player1 = player1.getClass().getSimpleName();
		this.player2 = player2.getClass().getSimpleName();
	}
	
	public static MatchLog log(JankenMatch match) {
		
		MatchLog matchLog = new MatchLog(match.numberOfRounds, match.player1, match.player2);
		
		match.start(matchStatus -> {
			matchLog.roundFinished(matchStatus.previousJudgement());
		});
		
		return matchLog;
	}
	
	public void roundFinished(JankenJudgement result) {
		this.rounds.add(new RoundLog(result));
	}
	
	public void matchFinished(JankenMatchResult result) {
		this.result = result;
		
		switch (result.resultClass()) {
		case PLAYER1_WIN:
			this.winner = this.player1;
			break;
		case PLAYER2_WIN:
			this.winner = this.player2;
			break;
		default:
			this.winner = null;
			break;
		}
	}
}
