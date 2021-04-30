package carrot.console.file.elimination;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import carrot.game.competition.elimination.SingleElimination;
import carrot.game.player.JankenPlayer;

public class JsonSingleElimination {

	public static void start(int numberOfRounds, List<JankenPlayer> players, Path destination) {
		
		SingleElimination compet = new SingleElimination(numberOfRounds, players);
		SingleEliminationLog log = new SingleEliminationLog(players);
		
		compet.start(
				level -> {
					log.levelStarting(level);
				},
				match -> {
					log.matchStarting(match);
				},
				matchStatus -> {
					log.roundFinished(matchStatus);
				},
				matchResult -> {
					log.matchFinished(matchResult);
				},
				champion -> {
					log.competitionFinished(champion);
				});
		
		ObjectMapper mapper = new ObjectMapper();
		
		String json;
		try {
			json = mapper.writeValueAsString(log);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		
		try {
			Files.deleteIfExists(destination);
			try (FileWriter fw = new FileWriter(destination.toString())) {
				fw.append(json);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
