package apricot.dom;

import java.util.ArrayList;
import java.util.List;

public class Calculator {

	//---------------------------------------------------------------------

	public static void printTime(List<TimePeriod> tgt_list) {
		printTime(tgt_list,"");
	}

	public static void printTime(List<TimePeriod> tgt_list,String title) {

		int sumTime = 0;

		System.out.println(title);
		for(int i=0;i<tgt_list.size();i++) {
			System.out.println(tgt_list.get(i).getStringTime());
			sumTime += tgt_list.get(i).getIntSub();
		}

		System.out.println("合計: " + Commons.formatTime(sumTime));
	}

	//---------------------------------------------------------------------

	public static void calculate(TimePeriod stamp, WorkShift workShift) {

		// 実就業時間: 出勤～退勤と、始業～終業との重複範囲
		TimePeriod actualWorkTime = workShift.getWork().getDuplication(stamp);

		// 実休憩時間帯: 出勤～退勤と、各休憩時間帯との重複
		List<TimePeriod> actualBreakTime = new ArrayList<TimePeriod>();
		for(int i=0;i<workShift.getBreak().size();i++) {
			TimePeriod temp = workShift.getBreak().get(i).getDuplication(stamp);
			if(temp != null) {actualBreakTime.add(temp);}
		}

		// 実就業時間帯から実休憩時間帯に重複している部分を除外
		List<TimePeriod> actualWorkTimesWithoutBreak = new ArrayList<TimePeriod>();
		{
			List<TimePeriod> temp = null;
			for(int i=0;i<actualBreakTime.size();i++) {
				if(i==0) {
					temp = actualWorkTime.getSubtraction(actualBreakTime.get(i));
					actualWorkTimesWithoutBreak.addAll(temp);
				}else {
					temp = actualWorkTimesWithoutBreak.get(actualWorkTimesWithoutBreak.size() - 1).getSubtraction(actualBreakTime.get(i));
					actualWorkTimesWithoutBreak.remove(actualWorkTimesWithoutBreak.size() - 1);
					actualWorkTimesWithoutBreak.addAll(temp);
				}
			}
		}

		// 出勤～退勤と、各残業時間帯との重複
		List<TimePeriod> actualOverTime = new ArrayList<TimePeriod>();
		for(int i=0;i<workShift.getOver().size();i++) {
			TimePeriod temp = workShift.getOver().get(i).getDuplication(stamp);
			if(temp != null) {actualOverTime.add(temp);}
		}

		// 実残業時間帯から実休憩時間帯に重複している部分を除外
		List<TimePeriod> actualOverTimesWithoutBreak = new ArrayList<TimePeriod>();
		{
			List<TimePeriod> temp = null;
			for(int i=0;i<actualOverTime.size();i++) {
				for(int j=0;j<actualBreakTime.size();j++) {
					if(j==0){
						temp = actualOverTime.get(i).getSubtraction(actualBreakTime.get(j));
						actualOverTimesWithoutBreak.addAll(temp);
					}else{
						temp = actualOverTimesWithoutBreak.get(actualOverTimesWithoutBreak.size() - 1).getSubtraction(actualBreakTime.get(j));
						actualOverTimesWithoutBreak.remove(actualOverTimesWithoutBreak.size() - 1);
						actualOverTimesWithoutBreak.addAll(temp);
					}
				}
			}
		}
		//---------------------------------------------------------------------

		//表示系
		printTime(actualWorkTimesWithoutBreak,"就業時間");
		printTime(actualOverTimesWithoutBreak,"残業時間");
		printTime(actualBreakTime            ,"休憩時間");
	}

}
//時間計算処理系が極めて複雑(クラス・配列・リストが入り乱れている)ので修正が絶対要る
//getDuplicationの返り値型とgetSubtractionの返り値型が揃ってない。
//getSubtractionは「返り値2つ」が有り得るので複数返り値に対応したリストか配列でなければならない（したくない）

//長い、もっと行数圧縮
//変数名を文字列で指定してString[]に格納すれば同じもの書く必要がなかった？

//いい加減for文原理主義を辞めろ

//表示系をコンソールに持ってったほうが良くない？
