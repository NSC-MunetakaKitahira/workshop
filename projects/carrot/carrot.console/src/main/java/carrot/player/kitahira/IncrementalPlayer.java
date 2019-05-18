package carrot.player.kitahira;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import carrot.game.JankenGameStatus.Subjective;
import carrot.judge.JankenHand;
import carrot.player.JankenPlayer;

public class IncrementalPlayer implements JankenPlayer {

	private final List<JankenHand> opponentHands = new ArrayList<>();
	
	@Override
	public JankenHand nextHand(Subjective currentGameStatus) {
		
		if (currentGameStatus.previousOpponentHand == null) {
			return JankenHand.PA;
		}
		
		opponentHands.add(currentGameStatus.previousOpponentHand);
		
		List<Map.Entry<JankenHand, Long>> map = opponentHands.stream()
				.collect(Collectors.groupingBy(x -> x, Collectors.counting()))
				.entrySet().stream()
				.sorted((a, b) -> b.getValue().compareTo(a.getValue()))
				.collect(Collectors.toList());
		
		return map.stream().findFirst()
				.map(x -> x.getKey())
				.get()
				.handToWin();
	}

}
