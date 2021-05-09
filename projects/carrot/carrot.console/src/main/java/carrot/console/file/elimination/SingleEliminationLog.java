package carrot.console.file.elimination;

import static java.util.stream.Collectors.*;

import java.util.ArrayList;
import java.util.List;

import carrot.game.log.LevelLog;
import carrot.game.match.JankenMatch;
import carrot.game.match.JankenMatchResult;
import carrot.game.match.JankenMatchStatus;
import carrot.game.player.JankenPlayer;

public class SingleEliminationLog {
	
	public final List<String> players;
	public String champion = null;
	public final List<LevelLog> levels = new ArrayList<>();
	
	public SingleEliminationLog(List<JankenPlayer> players) {
		this.players = players.stream()
				.map(p -> p.getClass().getSimpleName())
				.collect(toList());
	}

	public void levelStarting(int level) {
		levels.add(new LevelLog(level));
	}
	
	public void matchStarting(int count, JankenMatch match) {
		getCurrentLevel().matchStarting(count, match);
	}

	private LevelLog getCurrentLevel() {
		return levels.get(levels.size() - 1);
	}
	
	public void roundFinished(JankenMatchStatus matchStatus) {
		getCurrentLevel().roundFinished(matchStatus);
	}
	
	public void matchFinished(JankenMatchResult result) {
		getCurrentLevel().matchFinished(result);
	}
	
	public void competitionFinished(JankenPlayer champion) {
		this.champion = champion.getClass().getSimpleName();
	}
	
}
