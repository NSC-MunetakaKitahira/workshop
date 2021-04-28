package carrot.janken.competition.roundrobin;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import carrot.janken.game.JankenGameResult;

/**
 * 総当たり戦の勝ち点を管理する
 */
public class WinPoints {
	
	private static final int GAIN_WIN = 2;
	private static final int GAIN_DRAW = 1;

	private final Map<Class<?>, Item> map;
	
	public WinPoints(Collection<Class<?>> players) {
		map = players.stream()
				.collect(Collectors.toMap(p -> p, p -> Item.init(p)));
	}
	
	public void process(JankenGameResult result, Class<?> player1, Class<?> player2) {

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
	
	public List<Item> sortedList() {
		return map.entrySet().stream()
				.map(es -> es.getValue())
				.sorted((a, b) -> b.compareTo(a))
				.collect(Collectors.toList());
	}
	
	
	private void win(Class<?> player, int subPoints) {
		addGain(player, GAIN_WIN, subPoints);
	}
	
	private void draw(Class<?> player1, Class<?> player2) {
		addGain(player1, GAIN_DRAW, 0);
		addGain(player2, GAIN_DRAW, 0);
	}
	
	private void addGain(Class<?> player, int gainPoints, int gainSubPoints) {
		map.compute(player, (p, winPoint) -> winPoint.add(gainPoints, gainSubPoints));
	}
	
	public static class Item implements Comparable<Item> {
		public final Class<?> player;
		public final int points;
		public final int subPoints;
		
		private Item(Class<?> player, int points, int subPoints) {
			this.player = player;
			this.points = points;
			this.subPoints = subPoints;
		}
		
		public static  Item init(Class<?> player) {
			return new Item(player, 0, 0);
		}
		
		public Item add(int pointsToAdd, int subPointsToAdd) {
			return new Item(
					player,
					points + pointsToAdd,
					subPoints + subPointsToAdd);
		}

		@Override
		public int compareTo(Item o) {
			if (points == o.points) {
				return subPoints - o.subPoints;
			}
			return points - o.points;
		}
	}
}
