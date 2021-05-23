package carrot.game.player.sample;

import carrot.game.judge.JankenHand;
import carrot.game.player.JankenPlayer;
import carrot.game.player.SubjectiveMatchStatus;

public class Tajimileaning implements JankenPlayer {

	private String g;
	private String c;
	private String p;
	private String nextg;
	private String nextp;
	private String nextc;
	private String memory1;
	private String memory2;
	private String memory3;
	private String oneTimeMemory;
	private String referenceData;
	private int countggg;
	private int countggc;
	private int countggp;
	private int countgcg;
	private int countgcc;
	private int countgcp;
	private int countgpg;
	private int countgpc;
	private int countgpp;
	private int countcgg;
	private int countcgc;
	private int countcgp;
	private int countccg;
	private int countccc;
	private int countccp;
	private int countcpg;
	private int countcpc;
	private int countcpp;
	private int countpgg;
	private int countpgc;
	private int countpgp;
	private int countpcg;
	private int countpcc;
	private int countpcp;
	private int countppg;
	private int countppc;
	private int countppp;
	private int countGu;

	@Override
	public void newGame() {
		// TODO 自動生成されたメソッド・スタブ
		memory1 = memory2 = memory3 = oneTimeMemory = referenceData = "";
		countggg = countggc = countggp = countgcg = countgcc = countgcp = countgpg = countgpc = countgpp = 0;
		countcgg = countcgc = countcgp = countccg = countccc = countccp = countcpg = countcpc = countcpp = 0;
		countpgg = countpgc = countpgp = countpcg = countpcc = countpcp = countppg = countppc = countppp = 0;
		countGu = 0;
	}

	@Override
	public JankenHand nextHand(SubjectiveMatchStatus currentMatchStatus) {
		// TODO 自動生成されたメソッド・スタブ

		if (currentMatchStatus.isFirstRound()) {

			return JankenHand.CHOKI;
		}

		else if (currentMatchStatus.round <= 4) {
			oneTime(currentMatchStatus);
			// 手の記憶

			referenceData = untilThree(currentMatchStatus);
			// 3手目まで

			reference();
			// データ参照・カウント

			return JankenHand.CHOKI;
		}

		else {

			oneTime(currentMatchStatus);
			// 手の記憶

			referenceData = toDropFour();
			// 4手目以降

			reference();
			// データ参照・カウント

			String next = mostHand();
			// 相手が次に出す手の予想

			if (countGu <= 150) {
				switch (next) {
				// 予想に対して勝利するように手を出す。

				case "nextg":
					return JankenHand.PA;

				case "nextc":
					countGu++;
					return JankenHand.GU;

				default:
					return JankenHand.CHOKI;
				}
			} else {
				switch (next) {
				// 予想に対して勝利するように手を出す。

				case "nextg":
					return JankenHand.PA;

				case "nextc":
					return JankenHand.CHOKI;

				default:
					return JankenHand.CHOKI;
				}
			}

		}
	}

	// 今相手が出した手を記憶し、変数に入れるメソッド
	public void oneTime(SubjectiveMatchStatus currentMatchStatus) {
		switch (currentMatchStatus.previousOpponentHand) {
		case GU:
			oneTimeMemory = "g";
			break;

		case CHOKI:
			oneTimeMemory = "c";
			break;

		default:
			oneTimeMemory = "p";
			break;
		}
	}

	// 3手目までの特殊な処理
	public String untilThree(SubjectiveMatchStatus currentMatchStatus) {
		switch (currentMatchStatus.round) {
		case 2:
			memory1 = oneTimeMemory;
			oneTimeMemory = "";
			break;

		case 3:
			memory2 = oneTimeMemory;
			oneTimeMemory = "";
			break;
		default:
			memory3 = oneTimeMemory;
			oneTimeMemory = "";
			break;
		}
		return memory1 + memory2 + memory3;
	}

	// 4手目以降
	public String toDropFour() {
		memory1 = memory2;
		memory2 = memory3;
		memory3 = oneTimeMemory;
		oneTimeMemory = "";
		return memory1 + memory2 + memory3;
	}

	// データ参照・カウント
	public void reference() {
		switch (referenceData) {
		case "ggg":
			countggg++;
			break;

		case "ggc":
			countggc++;
			break;

		case "ggp":
			countggp++;
			break;

		case "gcg":
			countgcg++;
			break;

		case "gcc":
			countgcc++;
			break;

		case "gcp":
			countgcp++;
			break;

		case "gpg":
			countgpg++;
			break;

		case "gpc":
			countgpc++;
			break;

		case "gpp":
			countgpp++;
			break;

		case "cgg":
			countcgg++;
			break;

		case "cgc":
			countcgc++;
			break;

		case "cgp":
			countcgp++;
			break;

		case "ccg":
			countccg++;
			break;

		case "ccc":
			countccc++;
			break;

		case "ccp":
			countccp++;
			break;

		case "cpg":
			countcpg++;
			break;

		case "cpc":
			countcpc++;
			break;

		case "cpp":
			countcpp++;
			break;

		case "pgg":
			countpgg++;
			break;

		case "pgc":
			countpgc++;
			break;

		case "pgp":
			countpgp++;
			break;

		case "pcg":
			countpcg++;
			break;

		case "pcc":
			countpcc++;
			break;

		case "pcp":
			countpcp++;
			break;

		case "ppg":
			countppg++;
			break;

		case "ppc":
			countppc++;
			break;

		case "ppp":
			countppp++;
			break;
		}
	}

	// 直近2手を収集、referenceと参照,3択の内、一番多い手を選出
	public String mostHand() {
		String twoHand = memory2 + memory3;
		switch (twoHand) {
		case "gg":
			if (countggg == 0 && countggc == 0 && countggp == 0) {
				return "nextp";
			} else if (countggg > countggc && countggg > countggp) {
				return "nextg";
			} else if (countggc > countggg && countggc > countggp) {
				return "nextc";
			} else {
				return "nextp";
			}
		case "gc":
			if (countgcg == 0 && countgcc == 0 && countgcp == 0) {
				return "nextp";
			} else if (countgcg > countgcc && countgcg > countgcp) {
				return "nextg";
			} else if (countgcc > countgcg && countgcc > countgcp) {
				return "nextc";
			} else {
				return "nextp";
			}
		case "gp":
			if (countgpg == 0 && countgpc == 0 && countgpp == 0) {
				return "nextp";
			} else if (countgpg > countgpc && countgpg > countgpp) {
				return "nextg";
			} else if (countgpc > countgpg && countgpc > countgpp) {
				return "nextc";
			} else {
				return "nextp";
			}
		case "cg":
			if (countcgg == 0 && countcgc == 0 && countcgp == 0) {
				return "nextp";
			} else if (countcgg > countcgc && countcgg > countcgp) {
				return "nextg";
			} else if (countcgc > countcgg && countcgc > countcgp) {
				return "nextc";
			} else {
				return "nextp";
			}
		case "cc":
			if (countccg == 0 && countccc == 0 && countccp == 0) {
				return "nextp";
			} else if (countccg > countccc && countccg > countccp) {
				return "nextg";
			} else if (countccc > countccg && countccc > countccp) {
				return "nextc";
			} else {
				return "nextp";
			}
		case "cp":
			if (countcpg == 0 && countcpc == 0 && countcpp == 0) {
				return "nextp";
			} else if (countcpg > countcpc && countcpg > countcpp) {
				return "nextg";
			} else if (countcpc > countcpg && countcpc > countcpp) {
				return "nextc";
			} else {
				return "nextp";
			}
		case "pg":
			if (countpgg == 0 && countpgc == 0 && countpgp == 0) {
				return "nextp";
			} else if (countpgg > countpgc && countpgg > countpgp) {
				return "nextg";
			} else if (countpgc > countpgg && countpgc > countpgp) {
				return "nextc";
			} else {
				return "nextp";
			}
		case "pc":
			if (countpcg == 0 && countpcc == 0 && countpcp == 0) {
				return "nextp";
			} else if (countpcg > countpcc && countpcg > countpcp) {
				return "nextg";
			} else if (countpcc > countpcg && countpcc > countpcp) {
				return "nextc";
			} else {
				return "nextp";
			}
		default:
			if (countppg == 0 && countppc == 0 && countppp == 0) {
				return "nextp";
			} else if (countppg > countppc && countppg > countppp) {
				return "nextg";
			} else if (countppc > countppg && countppc > countppp) {
				return "nextc";
			} else {
				return "nextp";
			}
		}

	}
}