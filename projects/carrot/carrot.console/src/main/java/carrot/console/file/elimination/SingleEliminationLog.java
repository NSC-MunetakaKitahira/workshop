package carrot.console.file.elimination;

import static java.util.stream.Collectors.*;

import java.util.ArrayList;
import java.util.List;

import carrot.game.judge.JankenJudgement;
import carrot.game.match.JankenMatch;
import carrot.game.match.JankenMatchResult;
import carrot.game.match.JankenMatchStatus;
import carrot.game.player.JankenPlayer;

public class SingleEliminationLog {
	
	public final List<String> players;
	public String champion = null;
	public final List<Level> levels = new ArrayList<>();
	
	public SingleEliminationLog(List<JankenPlayer> players) {
		this.players = players.stream()
				.map(p -> p.getClass().getSimpleName())
				.collect(toList());
	}

	public void levelStarting(int level) {
		levels.add(new Level(level));
	}
	
	public void matchStarting(int count, JankenMatch match) {
		getCurrentLevel().matchStarting(count, match);
	}

	private Level getCurrentLevel() {
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
	
	public static class Level {
		
		public final int levelCount;
		public final List<Match> matches = new ArrayList<>();
		
		public Level(int count) {
			this.levelCount = count;
		}
		
		public void matchStarting(int count, JankenMatch match) {
			matches.add(new Match(count, match.player1, match.player2));
		}
		
		public void roundFinished(JankenMatchStatus matchStatus) {
			Match match = getCurrentMatch();
			match.roundFinished(matchStatus.previousJudgement());
		}
		
		public void matchFinished(JankenMatchResult result) {
			Match match = getCurrentMatch();
			match.matchFinished(result);
		}
		
		private Match getCurrentMatch() {
			return matches.get(matches.size() - 1);
		}
	}
	
	public static class Match {
		
		public final int matchCount;
		public final String player1;
		public final String player2;
		public JankenMatchResult result = null;
		public String winner = null;
		
		private final List<Round> rounds = new ArrayList<>();

		public Match(int count, JankenPlayer player1, JankenPlayer player2) {
			this.matchCount = count;
			this.player1 = player1.getClass().getSimpleName();
			this.player2 = player2.getClass().getSimpleName();
		}
		
		public void roundFinished(JankenJudgement result) {
			this.rounds.add(new Round(result));
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
	
	public static class Round {
		
		public final JankenJudgement result;

		public Round(JankenJudgement result) {
			super();
			this.result = result;
		}
	}
	
}
