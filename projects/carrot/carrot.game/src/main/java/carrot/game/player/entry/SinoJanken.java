package carrot.game.player.entry;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import carrot.game.judge.JankenHand;
import carrot.game.player.JankenPlayer;
import carrot.game.player.SubjectiveMatchStatus;

public class SinoJanken implements JankenPlayer {
//相手の出した手の履歴
	List<Integer> list = new ArrayList<Integer>();
	List<Integer> score = new ArrayList<Integer>();
	int hand = 6;
	private Random random;
	
	@Override
	public void newGame() {
		this.random = new Random();
	}

	@Override
	public JankenHand nextHand(SubjectiveMatchStatus currentMatchStatus) {
		score.add(currentMatchStatus.ownScore);
//履歴にGUなら1，CHOKIなら2,PAなら3を追加
		if (currentMatchStatus.previousOpponentHand == JankenHand.GU) {

			list.add(1);
		} else if (currentMatchStatus.previousOpponentHand == JankenHand.CHOKI) {

			list.add(2);
		} else {

			list.add(3);
		}
//
		
			if (currentMatchStatus.ownScore < currentMatchStatus.opponentScore&&currentMatchStatus.round>201) {
				hand+=judge(currentMatchStatus);
				return prediction(currentMatchStatus);
				}
		
//参照データが貯まったround以降はpredictionを実行
		else if (currentMatchStatus.round > hand) {
			return prediction(currentMatchStatus);
		}
//データが貯まるまではGU
		else {
			return JankenHand.GU;
		}
	}

	private JankenHand prediction(SubjectiveMatchStatus currentMatchStatus) {
//3つ分の手を複合したデータ(最後に出した手によって格納するMapを変更）
		Map<Integer, Integer> gu = new HashMap<Integer, Integer>();
		Map<Integer, Integer> choki = new HashMap<Integer, Integer>();
		Map<Integer, Integer> pa = new HashMap<Integer, Integer>();

//Mapにデータを追加
		addcombination(gu, choki, pa, currentMatchStatus);

//直近3回の相手の手の組み合わせ

		int recent = 0;
		for (int i = 0; i < hand; i++) {
			recent += list.get(list.size() - (hand - i)) * (int) Math.pow(10, hand - (i + 1));
		}
//recentCobinationの組み合わせと一致するキーが各Mapに存在するかの確認
//複数のMapにある場合は値を比較し最も多いMapの手に勝てる手を返す

		int g = 0;
		int c = 0;
		int p = 0;

		if (gu.get(recent) != null && choki.get(recent) != null && pa.get(recent) != null) {
			g += gu.get(recent);
			c += choki.get(recent);
			p += pa.get(recent);

		} else if (choki.get(recent) != null && gu.get(recent) != null) {
			c += choki.get(recent);
			g += gu.get(recent);
		} else if (choki.get(recent) != null && pa.get(recent) != null) {
			p += pa.get(recent);
			c += choki.get(recent);
		} else if (pa.get(recent) != null && gu.get(recent) != null) {
			p += pa.get(recent);
			g += gu.get(recent);
		} else if (pa.get(recent) != null) {
			p += pa.get(recent);
		} else if (gu.get(recent) != null) {
			g += gu.get(recent);
		} else if (choki.get(recent) != null) {
			c += choki.get(recent);
		}

//どのMapにも該当するキーが存在しない場合、ｎ-1回の相手の手を参照する
		if (g == c && c == p && g == 0) {
			return spare(gu, choki, pa);
		} else if (p >= g && p >= c) {
			return JankenHand.CHOKI;
		} else if (c >= g) {
			return JankenHand.GU;
		} else {
			return JankenHand.PA;
		}

	}

//		相手が出したnつの手をキーに追加、その手の組み合わせが出た回数をカウント
//		キーの重複を許可しない
	private void addcombination(Map<Integer, Integer> gu, Map<Integer, Integer> choki, Map<Integer, Integer> pa,
			SubjectiveMatchStatus currentMatchStatus) {
		for (int i = 0; i < (list.size()) - hand; i++) {
			int combination = 0;
			for (int m = 0; m < hand; m++) {
				combination += list.get(i + m) * (int) Math.pow(10, hand - (m + 1));
			}
			int after = list.get(i + hand);
			if (gu.containsKey(combination) == false && after == 1) {
				gu.put(combination, 1);
			} else if (gu.containsKey(combination) && after == 1) {
				gu.replace(combination, gu.get(combination) + hensa(currentMatchStatus, i));
			} else if (choki.containsKey(combination) == false && after == 2) {
				choki.put(combination, 1);
			} else if (choki.containsKey(combination) && after == 2) {
				choki.replace(combination, choki.get(combination) + hensa(currentMatchStatus, i));
			} else if (pa.containsKey(combination) == false && after == 3) {
				pa.put(combination, 1);
			} else {
				pa.replace(combination, pa.get(combination) + hensa(currentMatchStatus, i));
			}
		}
	}

	private JankenHand spare(Map<Integer, Integer> gu, Map<Integer, Integer> choki, Map<Integer, Integer> pa) {
//直近hand-1回の相手の手の組み合わせ
		hand--;
		int n = 0;
		for (int i = 0; i < hand; i++) {
			n += list.get(list.size() - (hand - i)) * (int) Math.pow(10, hand - (i + 1));
		}
		// nの組み合わせと一致するキーが各Mapに存在するかの確認
		int g = 0;
		int c = 0;
		int p = 0;

		for (int l = 1; l <= 3; l += 1) {
			n += l * (int) Math.pow(10, hand);
			if (gu.get(n) != null && choki.get(n) != null && pa.get(n) != null) {
				g += gu.get(n);
				c += choki.get(n);
				p += pa.get(n);

			} else if (choki.get(n) != null && gu.get(n) != null) {
				c += choki.get(n);
				g += gu.get(n);
			} else if (choki.get(n) != null && pa.get(n) != null) {
				p += pa.get(n);
				c += choki.get(n);
			} else if (pa.get(n) != null && gu.get(n) != null) {
				p += pa.get(n);
				g += gu.get(n);
			} else if (pa.get(n) != null) {
				p += pa.get(n);
			} else if (gu.get(n) != null) {
				g += gu.get(n);
			} else if (choki.get(n) != null) {
				c += choki.get(n);
			}

		}
		if (g > c && g > p) {
			hand++;
			return JankenHand.PA;
		} else if (c > p) {
			hand++;
			return JankenHand.GU;
		} else {
			hand++;
			return JankenHand.CHOKI;
		}

	}

	private int hensa(SubjectiveMatchStatus currentMatchStatus, int i) {
		if (i > currentMatchStatus.round * 0.9) {
			return 3;
		} else if (i > currentMatchStatus.round * 0.6) {
			return 2;
		} else {
			return 1;
		}
	}

	private int judge(SubjectiveMatchStatus status) {
		if(score.get(status.round-1)-score.get(status.round-101)<score.get(status.round-101)-score.get(status.round-201)) {
						int value = random.nextInt(2); 
			
			switch(value) {
			case 1:
				return 1;
			default:
				return -1;
			}
		}else {
			return 0;
		}
	}
}
