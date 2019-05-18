package carrot.game;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import carrot.janken.JankenGame;

public class WinPoints<P> {
	
	private static final int GAIN_WIN = 2;
	private static final int GAIN_DRAW = 1;

	private final Map<P, Item<P>> map;
	
	public WinPoints(Collection<P> players) {
		map = players.stream()
				.collect(Collectors.toMap(p -> p, p -> Item.init(p)));
	}
	
	public void process(JankenGame.Result result, P player1, P player2) {

		switch (result.resultClass()) {
		case PLAYER1_WIN:
			win(player1, result.subPoints());
			break;
		case PLAYER2_WIN:
			win(player2, result.subPoints());
			break;
		default:
			draw(player1, player2);
			break;
		}
	}
	
	public List<Item<P>> sortedList() {
		return map.entrySet().stream()
				.map(es -> es.getValue())
				.sorted((a, b) -> b.compareTo(a))
				.collect(Collectors.toList());
	}
	
	
	private void win(P player, int subPoints) {
		addGain(player, GAIN_WIN, subPoints);
	}
	
	private void draw(P player1, P player2) {
		addGain(player1, GAIN_DRAW, 0);
		addGain(player2, GAIN_DRAW, 0);
	}
	
	private void addGain(P player, int gainPoints, int gainSubPoints) {
		map.compute(player, (p, winPoint) -> winPoint.add(gainPoints, gainSubPoints));
	}
	
	public static class Item<P> implements Comparable<Item<P>> {
		public final P player;
		public final int points;
		public final int subPoints;
		
		private Item(P player, int points, int subPoints) {
			this.player = player;
			this.points = points;
			this.subPoints = subPoints;
		}
		
		public static <P> Item<P> init(P player) {
			return new Item<>(player, 0, 0);
		}
		
		public Item<P> add(int pointsToAdd, int subPointsToAdd) {
			return new Item<>(
					player,
					points + pointsToAdd,
					subPoints + subPointsToAdd);
		}

		@Override
		public int compareTo(Item<P> o) {
			if (points == o.points) {
				return subPoints - o.subPoints;
			}
			return points - o.points;
		}
	}
}
