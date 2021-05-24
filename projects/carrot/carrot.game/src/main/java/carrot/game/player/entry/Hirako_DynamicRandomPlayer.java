package carrot.game.player.entry;

import java.util.Random;

import carrot.game.judge.JankenHand;
import carrot.game.player.JankenPlayer;
import carrot.game.player.SubjectiveMatchStatus;


public class Hirako_DynamicRandomPlayer implements JankenPlayer {

	final boolean DEBUG = false;



	int[] history 				= new int[301];
	int[] opponent_count 		= new int[3];
	double[] opponent_rate 	= new double[3];
	double[] point_gain		= new double[3];
	double[] attack_rate_A		= new double[3];
	double[] attack_rate_B		= new double[3];

	public void newGame() {
		for(int i=0;i<history.length;i++) {
			history[0]=-1;
		}
		opponent_count[0]=0;
		opponent_count[1]=0;
		opponent_count[2]=0;
	}
	
	

	@Override
	public JankenHand nextHand(SubjectiveMatchStatus currentGameStatus) {
		
		
		
		
		if(currentGameStatus.round>=2) {
			if(currentGameStatus.previousOpponentHand==JankenHand.GU) {
				history[currentGameStatus.round - 1] = 0;
				opponent_count[0]++;
			}else if(currentGameStatus.previousOpponentHand==JankenHand.CHOKI) {
				history[currentGameStatus.round - 1] = 1;
				opponent_count[1]++;
			}else if(currentGameStatus.previousOpponentHand==JankenHand.PA) {
				history[currentGameStatus.round - 1] = 2;
				opponent_count[2]++;
			}else {
				System.out.println("###HistoryRegistrationError");
				history[currentGameStatus.round - 1] = -2;
			}
		}
		
		if(currentGameStatus.round>=5) {

			opponent_rate[0]	= (double)opponent_count[0] / ((double)opponent_count[0]+(double)opponent_count[1]+(double)opponent_count[2]);
			opponent_rate[1]	= (double)opponent_count[1] / ((double)opponent_count[0]+(double)opponent_count[1]+(double)opponent_count[2]);
			opponent_rate[2]	= (double)opponent_count[2] / ((double)opponent_count[0]+(double)opponent_count[1]+(double)opponent_count[2]);

			point_gain[0]	=  ( 0 * opponent_rate[0]) + (2 * opponent_rate[1]) - (5 * opponent_rate[2]);
			point_gain[1]	=  (-2 * opponent_rate[0]) + (0 * opponent_rate[1]) + (4 * opponent_rate[2]);
			point_gain[2]	=  ( 5 * opponent_rate[0]) - (4 * opponent_rate[1]) + (0 * opponent_rate[2]);

			attack_rate_A[0] = Math.max(0.0, point_gain[0]);
			attack_rate_A[1] = Math.max(0.0, point_gain[1]);
			attack_rate_A[2] = Math.max(0.0, point_gain[2]);

			attack_rate_B[0] = attack_rate_A[0] / (attack_rate_A[0] + attack_rate_A[1] + attack_rate_A[2]);
			attack_rate_B[1] = attack_rate_A[1] / (attack_rate_A[0] + attack_rate_A[1] + attack_rate_A[2]);
			attack_rate_B[2] = attack_rate_A[2] / (attack_rate_A[0] + attack_rate_A[1] + attack_rate_A[2]);

			Random r = new Random();
			double r_num = r.nextDouble();


			if(DEBUG) {
				System.out.printf("opponent_count[0]=%10d, opponent_count[1]=%10d, opponent_count[2]=%10d\n",opponent_count[0],opponent_count[1],opponent_count[2]);
				System.out.printf("opponent_rate [0]=%10.6f, opponent_rate [1]=%10.6f, opponent_rate [2]=%10.6f\n",opponent_rate[0],opponent_rate[1],opponent_rate[2]);
				System.out.printf("point_gain    [0]=%10.6f, point_gain    [1]=%10.6f, point_gain    [2]=%10.6f\n",point_gain[0],point_gain[1],point_gain[2]);
				System.out.printf("attack_rate_A [0]=%10.6f, attack_rate_A [1]=%10.6f, attack_rate_A [2]=%10.6f\n",attack_rate_A[0],attack_rate_A[1],attack_rate_A[2]);
				System.out.printf("attack_rate_B [0]=%10.6f, attack_rate_B [1]=%10.6f, attack_rate_B [2]=%10.6f\n",attack_rate_B[0],attack_rate_B[1],attack_rate_B[2]);
				System.out.printf("r_num            =%10.6f\n",r_num);
			}

			if(0 <= r_num && r_num < attack_rate_B[0]) {
				return JankenHand.GU;
			}else if(attack_rate_B[0] <= r_num && r_num < attack_rate_B[0] + attack_rate_B[1]) {
				return JankenHand.CHOKI;
			}else if(attack_rate_B[0] + attack_rate_B[1] <= r_num && r_num <= 1) {
				return JankenHand.PA;
			}else {
				System.out.println("###DeterminError");
				return JankenHand.CHOKI;
			}

		}else {
			return JankenHand.CHOKI;
		}

	}

}