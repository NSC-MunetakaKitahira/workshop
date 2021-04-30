package carrot.game.competition.elimination;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import carrot.game.match.JankenMatch;
import carrot.game.match.JankenMatchResult;
import carrot.game.match.JankenMatchStatus;
import carrot.game.player.JankenPlayer;

/**
 * シングルエリミネーション（勝ち抜き戦）のコントロール
 * マッチングはコンストラクタに与えられたリストの端から順番に。
 * プレイヤーが奇数の場合は先頭のプレイヤーが一回戦をスキップする。
 */
public class SingleElimination {

	private final int numberOfRounds;
	private final List<JankenPlayer> players;
	
	public SingleElimination(int numberOfRounds, List<JankenPlayer> players) {
		this.numberOfRounds = numberOfRounds;
		this.players = players;
	}
	
	public void start(
			Consumer<Integer> notifyLevel,
			Consumer<JankenMatch> notifyMatchStarting,
			Consumer<JankenMatchStatus> notifyRoundResult,
			Consumer<JankenMatchResult> notifyMatchResult,
			Consumer<JankenPlayer> notifyChampion
			) {
		
		List<JankenPlayer> currentLevelPlayers = new ArrayList<>();
		List<JankenPlayer> nextLevelPlayers = new ArrayList<>(players);
		
		for (int level = 1; ; level++) {
			
			notifyLevel.accept(level);
			
			currentLevelPlayers.clear();
			currentLevelPlayers.addAll(nextLevelPlayers);
			nextLevelPlayers.clear();
			
			// 人数が奇数なら先頭プレイヤーが次の回戦へ
			if (currentLevelPlayers.size() % 2 == 1) {
				nextLevelPlayers.add(currentLevelPlayers.get(0));
				currentLevelPlayers = currentLevelPlayers.subList(1, currentLevelPlayers.size());
			}
			
			for (int i = 0; i < currentLevelPlayers.size(); i += 2) {
	
				JankenPlayer player1 = currentLevelPlayers.get(i);
				JankenPlayer player2 = currentLevelPlayers.get(i + 1);
				
				operateMatch(notifyMatchStarting, notifyRoundResult, notifyMatchResult, nextLevelPlayers, player1, player2);
			}
			
			if (nextLevelPlayers.size() <= 1) {
				break;
			}
		}
		
		notifyChampion.accept(nextLevelPlayers.get(0));
	}

	private void operateMatch(
			Consumer<JankenMatch> notifyMatchStarting,
			Consumer<JankenMatchStatus> notifyRoundResult,
			Consumer<JankenMatchResult> notifyMatchResult,
			List<JankenPlayer> nextLayerPlayers,
			JankenPlayer player1,
			JankenPlayer player2) {
		
		JankenMatch match = new JankenMatch(numberOfRounds, player1, player2);
		notifyMatchStarting.accept(match);
		
		JankenMatchResult matchResult = match.start(matchStatus -> {
			notifyRoundResult.accept(matchStatus);
		});
		notifyMatchResult.accept(matchResult);
		
		switch (matchResult.resultClass()) {
		case PLAYER1_WIN:
			nextLayerPlayers.add(player1);
			break;
		case PLAYER2_WIN:
			nextLayerPlayers.add(player2);
			break;
		default:
			// 引き分けたら決着がつくまで再試合
			operateMatch(notifyMatchStarting, notifyRoundResult, notifyMatchResult, nextLayerPlayers, player1, player2);
			break;
		}
	}
	
	
}
