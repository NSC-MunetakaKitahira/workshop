package carrot.game.player.entry;

import java.util.Random;

import carrot.game.judge.JankenHand;
import carrot.game.player.JankenPlayer;
import carrot.game.player.SubjectiveMatchStatus;

public class HiranoMemoryD implements JankenPlayer {

	// 相手が勝った次の手のカウント 変数・・・左からグー、チョキ、パー 数字・・・0グー、1チョキ、2パー
	private int wg[] = new int[3], wc[] = new int[3], wp[] = new int[3];

	// 相手があいこ…
	private int ag[] = new int[3], ac[] = new int[3], ap[] = new int[3];

	// 相手が負け…
	private int lg[] = new int[3], lc[] = new int[3], lp[] = new int[3];

	// ランダム用
	private Random random;
	int value;

	// 相手のスコア(更新1個前)
	private int scoreA;
	// 相手のスコア(更新2個前)
	private int scoreB;

	// 相手の2つ前に出した手 (1つ前はpreviousOpponentHand)
	private int gu, choki, pa = 0;

	@Override
	public void newGame() {
		// カウントを初期化
		for (int i = 0; i < 3; i++) {
			wg[i] = wc[i] = wp[i] = ag[i] = ac[i] = ap[i] = lg[i] = lc[i] = lp[i] = 0;
		}

		this.random = new Random();
		scoreA = scoreB = 0;
		gu = choki = pa = 0;

	}

	@Override
	public JankenHand nextHand(SubjectiveMatchStatus status) {

		this.value = random.nextInt(3);

		// 100の倍数でカウントリセット
		if (status.round % 100 == 0) {
			for (int i = 0; i < 3; i++) {
				wg[i] = wc[i] = wp[i] = ag[i] = ac[i] = ap[i] = lg[i] = lc[i] = lp[i] = 0;
			}
		}

		// 3ラウンド目までグーだけ
		if (status.round <= 3) {

			return JankenHand.GU;
		}

		// 50ラウンド目までランダム
		else if (status.round <= 50) {

			extractedHandCount(status);
			extracted(status);

			return random();
		}

		else {
			extractedHandCount(status);
			extracted(status);
			// 有利な手を返す
			return hand(status);
		}

	}

	private JankenHand random() {
		if (value == 0) {
			return JankenHand.GU;

		} else if (value == 1) {
			return JankenHand.CHOKI;

		} else {
			return JankenHand.PA;
		}
	}

	private JankenHand hand(SubjectiveMatchStatus status) {
		switch (status.previousOpponentHand) {

		case GU:// 1つ前グー(相手)
			if (status.opponentScore - scoreA == 2) {
				if (wg[0] > wg[1] && wg[0] > wg[2]) {
					
					return JankenHand.PA;
				} else if (wg[1] > wg[0] && wg[1] > wg[2]) {
				
					return JankenHand.GU;
				} else if (wg[2] > wg[0] && wg[2] > wg[1]) {
			
					return JankenHand.CHOKI;
				} else {
					return random();
				}
			} // あいこ
			else if (status.opponentScore - scoreA == 1) {
				if (ag[0] > ag[1] && ag[0] > ag[2]) {
				
					return JankenHand.PA;
				} else if (ag[1] > ag[0] && ag[1] > ag[2]) {
			
					return JankenHand.GU;
				} else if (ag[2] > ag[0] && ag[2] > ag[1]) {
		
					return JankenHand.CHOKI;
				} else {
					return random();
				}
			} // 負け
			else {
				if (lg[0] > lg[1] && lg[0] > lg[2]) {
					
					return JankenHand.PA;
				} else if (lg[1] > lg[0] && lg[1] > lg[2]) {
				
					return JankenHand.GU;
				} else if (lg[2] > lg[0] && lg[2] > lg[1]) {
				
					return JankenHand.CHOKI;
				} else {
					return random();
				}
			}

			// 1つ前チョキ(相手)
		case CHOKI:
			if (status.opponentScore - scoreA == 4) {
				if (wc[0] > wc[1] && wc[0] > wc[2]) {
			
					return JankenHand.PA;
				} else if (wc[1] > wc[0] && wc[1] > wc[2]) {
				
					return JankenHand.GU;
				} else if (wc[2] > wc[0] && wc[2] > wc[1]) {
		
					return JankenHand.CHOKI;
				} else {
					return random();
				}
			} // あいこ
			else if (status.opponentScore - scoreA == 1) {
				if (ac[0] > ac[1] && ac[0] > ac[2]) {
	
					return JankenHand.PA;
				} else if (ac[1] > ac[0] && ac[1] > ac[2]) {
			
					return JankenHand.GU;
				} else if (ac[2] > ac[0] && ac[2] > ac[1]) {
			
					return JankenHand.CHOKI;
				} else {
					return random();
				}
			} // 負け
			else {
				if (lc[0] > lc[1] && lc[0] > lc[2]) {
				
					return JankenHand.PA;
				} else if (lc[1] > lc[0] && lc[1] > lc[2]) {
			
					return JankenHand.GU;
				} else if (lc[2] > lc[0] && lc[2] > lc[1]) {
		
					return JankenHand.CHOKI;
				} else {
					return random();
				}
			}
			// 1つ前パー(相手)
		case PA:
			if (status.opponentScore - scoreA == 5) {
				if (wp[0] > wp[1] && wp[0] > wp[2]) {
	
					return JankenHand.PA;
				} else if (wp[1] > wp[0] && wp[1] > wp[2]) {
	
					return JankenHand.GU;
				} else if (wp[2] > wp[0] && wp[2] > wp[1]) {
			
					return JankenHand.CHOKI;
				} else {
					return random();
				}
			} // あいこ
			else if (status.opponentScore - scoreA == 1) {
				if (ap[0] > ap[1] && ap[0] > ap[2]) {

					return JankenHand.PA;
				} else if (ap[1] > ap[0] && ap[1] > ap[2]) {

					return JankenHand.GU;
				} else if (ap[2] > ap[0] && ap[2] > ap[1]) {

					return JankenHand.CHOKI;
				} else {
					return random();
				}
			} // 負け
			else {
				if (lp[0] > lp[1] && lp[0] > lp[2]) {

					return JankenHand.PA;
				} else if (lp[1] > lp[0] && lp[1] > lp[2]) {

					return JankenHand.GU;
				} else if (lp[2] > lp[0] && lp[2] > lp[1]) {

					return JankenHand.CHOKI;
				} else {
					return random();
				}
			}
		default:
			throw new RuntimeException("エラーです。");
		}
	}

	private void extractedHandCount(SubjectiveMatchStatus status) {

		switch (scoreA - scoreB) {
		// 2つ前グーで勝ち(相手)
		case (2):
			switch (status.previousOpponentHand) {
			// 1つ前グー(相手)
			case GU:
				wg[0]++;
				break;
			// 1つ前チョキ(相手)
			case CHOKI:
				wg[1]++;
				break;
			// 1つ前パー(相手)
			case PA:
				wg[2]++;
				break;
			}
			break;
		// 2つ前チョキで勝ち(相手)
		case (4):
			switch (status.previousOpponentHand) {
			// 1つ前グー(相手)
			case GU:
				wc[0]++;
				break;
			// 1つ前チョキ(相手)
			case CHOKI:
				wc[1]++;
				break;
			// 1つ前パー(相手)
			case PA:
				wc[2]++;
				break;
			}
			break;
		// 2つ前パーで勝ち(相手)
		case (5):
			switch (status.previousOpponentHand) {
			// 1つ前グー(相手)
			case GU:
				wp[0]++;
				break;
			// 1つ前チョキ(相手)
			case CHOKI:
				wp[1]++;
				break;
			// 1つ前パー(相手)
			case PA:
				wp[2]++;
				break;
			}
			break;
		// 2つ前あいこ
		case (1):
			// グーで
			if (gu == 1) {

				switch (status.previousOpponentHand) {
				// 1つ前グー(相手)
				case GU:
					ag[0]++;
					break;
				// 1つ前チョキ(相手)
				case CHOKI:
					ag[1]++;
					break;
				// 1つ前パー(相手)
				case PA:
					ag[2]++;
					break;
				}
			}
			// チョキで
			if (choki == 1) {

				switch (status.previousOpponentHand) {
				// 1つ前グー(相手)
				case GU:
					ac[0]++;
					break;
				// 1つ前チョキ(相手)
				case CHOKI:
					ac[1]++;
					break;
				// 1つ前パー(相手)
				case PA:
					ac[2]++;
					break;
				}
			}
			// パーで
			if (pa == 1) {

				switch (status.previousOpponentHand) {
				// 1つ前グー(相手)
				case GU:
					ap[0]++;
					break;
				// 1つ前チョキ(相手)
				case CHOKI:
					ap[1]++;
					break;
				// 1つ前パー(相手)
				case PA:
					ap[2]++;
					break;
				}
			}
			break;
		// 2つ前負け(相手)
		case (0):

			// グーで
			if (gu == 1) {

				switch (status.previousOpponentHand) {
				// 1つ前グー(相手)
				case GU:
					lg[0]++;
					break;
				// 1つ前チョキ(相手)
				case CHOKI:
					lg[1]++;
					break;
				// 1つ前パー(相手)
				case PA:
					lg[2]++;
					break;
				}
			}

			// チョキで
			if (choki == 1) {

				switch (status.previousOpponentHand) {
				// 1つ前グー(相手)
				case GU:
					lc[0]++;
					break;
				// 1つ前チョキ(相手)
				case CHOKI:
					lc[1]++;
					break;
				// 1つ前パー(相手)
				case PA:
					lc[2]++;
					break;
				}
			}

			// パーで
			if (pa == 1) {

				switch (status.previousOpponentHand) {
				// 1つ前グー(相手)
				case GU:
					lp[0]++;
					break;
				// 1つ前チョキ(相手)
				case CHOKI:
					lp[1]++;
					break;
				// 1つ前パー(相手)
				case PA:
					lp[2]++;
					break;
				}
			}

		}
	}

	private void extracted(SubjectiveMatchStatus status) {

		// 相手のスコア
		scoreB = scoreA;
		scoreA = status.opponentScore;

		// 相手の手をリセット(2つ前)
		gu = choki = pa = 0;

		switch (status.previousOpponentHand) {
		case GU:
			gu++;
			break;
		case CHOKI:
			choki++;
			break;
		default:
			pa++;
			break;
		}
	}

}