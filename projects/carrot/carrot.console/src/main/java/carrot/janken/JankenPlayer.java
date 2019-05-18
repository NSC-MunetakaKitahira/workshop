package carrot.janken;

public interface JankenPlayer {

	JankenHand nextHand(JankenGameStatus.Subjective currentGameStatus);
}
