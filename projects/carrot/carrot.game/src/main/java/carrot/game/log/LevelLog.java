package carrot.game.log;

import java.util.ArrayList;
import java.util.List;

import carrot.game.match.JankenMatch;
import carrot.game.match.JankenMatchResult;
import carrot.game.match.JankenMatchStatus;

public class LevelLog {

	public final int levelCount;
	public final List<MatchLog> matches = new ArrayList<>();
	
	public LevelLog(int count) {
		this.levelCount = count;
	}
	
	public void matchStarting(int count, JankenMatch match) {
		matches.add(new MatchLog(count, match.player1, match.player2));
	}
	
	public void roundFinished(JankenMatchStatus matchStatus) {
		MatchLog match = getCurrentMatch();
		match.roundFinished(matchStatus.previousJudgement());
	}
	
	public void matchFinished(JankenMatchResult result) {
		MatchLog match = getCurrentMatch();
		match.matchFinished(result);
	}
	
	private MatchLog getCurrentMatch() {
		return matches.get(matches.size() - 1);
	}
}
