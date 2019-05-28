package carrot.player;

import java.util.Random;

import carrot.game.JankenGameStatus.Subjective;
import carrot.judge.JankenHand;
import carrot.player.JankenPlayer;

public class oozono1 implements JankenPlayer{
	
	@Override
	public void newGame() {
		// 何もしない
	}

	
	
	public JankenHand nextHand(Subjective currentGameStatus) {
		
	
	//乱数でじゃんけんの手を返す
    Random	rand = new Random();
    int r = rand.nextInt(3);
    	
    switch(r) {
    case 0:
    	
    	return JankenHand.GU;
    	
    	
    case 1:
    	
    	return JankenHand.CHOKI;
    	
    case 2:
    	
    	return JankenHand.PA;
    	
    }
	//ｒが0.1.2以外の時パーだす	
    return JankenHand.PA;
    }
	}
    
    
    



   