package carrot.game.player;

import static java.util.stream.Collectors.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.reflections.Reflections;

/**
 * carrotパッケージ内にあるJankenPlayerを全て保持する
 */
public class Players {

	private static final List<JankenPlayer> ALL = new Reflections("carrot")
			.getSubTypesOf(JankenPlayer.class)
			.stream()
			.sorted(Comparator.comparing(c -> c.getName()))
			.map(c -> create(c))
			.collect(toList());
	
	private static JankenPlayer create(Class<?> playerClass) {
		try {
			return (JankenPlayer) playerClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static List<JankenPlayer> findAll() {
		return new ArrayList<>(ALL);
	}
	
	public static Optional<JankenPlayer> findByName(String playerName) {
		return ALL.stream()
				.filter(p -> p.getClass().getSimpleName().equals(playerName))
				.findFirst();
	}
	
}
