package carrot.player.hirako;

import carrot.game.JankenGameStatus.Subjective;
import carrot.judge.JankenHand;
import carrot.player.JankenPlayer;


class Test_class {
	
}

public class Hirako_EvilPlayer implements JankenPlayer {

	public void newGame() {
		// 何もしない
	}
	
	@Override
	public JankenHand nextHand(Subjective currentGameStatus) {
		
		
		
		System.out.printf("\n");
		
		StackTraceElement[] ste = new Throwable().getStackTrace();
		for(int i=0;i<ste.length;i++) {
			System.out.printf("%-70s",ste[i]);
			System.out.print(" || ");
			System.out.printf("%13d",ste[i].hashCode());
			System.out.print(" || ");
			System.out.print(ste[i].isNativeMethod());
			System.out.print("\n");
		}
		
		System.out.printf("\n");
		
		return JankenHand.PA;

	}

}
