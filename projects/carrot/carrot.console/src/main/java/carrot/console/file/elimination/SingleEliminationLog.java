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
	
	/** 回戦ごとのマッチ結果 */
	public final List<List<Match>> matchesByLevel = new ArrayList<>();
	
	public SingleEliminationLog(List<JankenPlayer> players) {
		this.players = players.stream()
				.map(p -> p.getClass().getSimpleName())
				.collect(toList());
	}

	public void levelStarting(int level) {
		matchesByLevel.add(new ArrayList<>());
	}
	
	public void matchStarting(JankenMatch match) {
		getCurrentLevelMatches().add(new Match(match.player1, match.player2));
	}

	private List<Match> getCurrentLevelMatches() {
		return matchesByLevel.get(matchesByLevel.size() - 1);
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
		List<Match> matches = getCurrentLevelMatches();
		return matches.get(matches.size() - 1);
	}
	
	public void competitionFinished(JankenPlayer champion) {
		this.champion = champion.getClass().getSimpleName();
	}
	
	public static class Match {
		
		public final String player1;
		public final String player2;
		public JankenMatchResult result = null;
		
		private final List<Round> rounds = new ArrayList<>();

		public Match(JankenPlayer player1, JankenPlayer player2) {
			super();
			this.player1 = player1.getClass().getSimpleName();
			this.player2 = player2.getClass().getSimpleName();
		}
		
		public void roundFinished(JankenJudgement result) {
			this.rounds.add(new Round(result));
		}
		
		public void matchFinished(JankenMatchResult result) {
			this.result = result;
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
