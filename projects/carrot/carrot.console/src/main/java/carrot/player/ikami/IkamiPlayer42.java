package carrot.player.ikami;

import carrot.game.JankenGameStatus.Subjective;
import carrot.judge.JankenHand;
import carrot.player.JankenPlayer;
//import java.util.Arrays;
//import carrot.player.kitahira.AlwaysPaPlayer;

import carrot.player.ikami.IkamiPlayer42;
import java.util.Random;


 public class IkamiPlayer42 implements JankenPlayer {

@Override
	public void newGame() {
	}
	
	@Override
	public JankenHand nextHand(Subjective currentGameStatus) {
		
  Random random = new Random();
  int randomValue = random.nextInt(3);
   switch(randomValue) {
     case 0:
        return JankenHand.GU;
     case 1 :
        return JankenHand.PA;
     default:
        return JankenHand.CHOKI; 
	}
 }
}
