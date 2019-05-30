package apricot.dom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TimePeriod {

	//---------------------------------------------------------------------

	private int start;
	private int end;

	//---------------------------------------------------------------------

	public TimePeriod(int int_start,int int_end){
		if(isIncorrectTime(int_start, int_end)) {throw new IllegalArgumentException();}
		this.start = int_start;
		this.end   = int_end  ;
	}

	public  TimePeriod(String str_start,String str_end){

		int int_start = Commons.parseTimeString(str_start);
		int int_end   = Commons.parseTimeString(str_end);

		if(isIncorrectTime(int_start, int_end)) {throw new IllegalArgumentException();}

		this.start = int_start;
		this.end   = int_end  ;
	}

	public boolean isIncorrectTime(int int_start,int int_end) {
		if(int_start>int_end)				{return true;}
		if(int_start<0 || int_end<0)		{return true;}
		if(int_start>2880 || int_end>2880)	{return true;}
		return false;
	}

	//本当ならDateTimeクラスとか作っておけば「分数が60以上」の不正入力に対応できた。
	//一応parseTimeStringに時間数・分数チェック仕込んでるけど、エラーチェックが散逸している時点で望ましくない

	//---------------------------------------------------------------------

	public String getStringTime()	{return Commons.formatTime(this.start) + "～" + Commons.formatTime(this.end);}
	public String getStringSub()	{return Commons.formatTime(this.end - this.start);}
	public int   getIntStart()		{return start;}
	public int   getIntEnd()		{return end;  }
	public int   getIntSub()		{return end - start;}

	//---------------------------------------------------------------------

	public TimePeriod getDuplication(TimePeriod tgt) {

		if(this.start < tgt.start) {
			if(this.end < tgt.start) {									//this<------->|
				return null;											//tgt          |<------->
			}
			else if(tgt.start <= this.end && this.end <= tgt.end) {	//this<------------>|
				return new TimePeriod(tgt.start,this.end);			//tgt           <---|--->
			}
			else if(tgt.end < this.end) {								//this<------------------>|
				return new TimePeriod(tgt.start,tgt.end);				//tgt           <-------> |
			}
		}else if(tgt.start <= this.start && this.start <= tgt.end) {
			if(this.end <= tgt.end) {									//this            |<->|
				return new TimePeriod(this.start,this.end);			//tgt          |<-|---|->
			}
			else if(tgt.end < this.end) {								//this            |<----->|
				return new TimePeriod(this.start,tgt.end);			//tgt          |<-|-----> |
			}

		}else if(tgt.end < this.start) {								//this                   |<------->
			return null;												//tgt           <------->|
		}
		throw new RuntimeException("たぶん他のケースは無い？");
	}

	public List<TimePeriod> getSubtraction(TimePeriod tgt) {

		// <--->
		//       <--->
		if (this.end < tgt.start) {
			return new ArrayList<TimePeriod>(Arrays.asList(new TimePeriod(this.start,this.end)));
		}

		//       <--->
		// <--->
		if (tgt.end < this.start) {
			return new ArrayList<TimePeriod>(Arrays.asList(new TimePeriod(this.start,this.end)));
		}

		// <--------->
		// <---->
		if (this.start == tgt.start && tgt.end < this.end) {
			return new ArrayList<TimePeriod>(Arrays.asList(new TimePeriod(tgt.end,this.end)));
		}

		// <--------->
		//      <---->
		if (this.start < tgt.start && this.end == tgt.end) {
			return new ArrayList<TimePeriod>(Arrays.asList(new TimePeriod(this.start,tgt.start)));
		}

		// <--------->
		//   <---->
		if (this.start < tgt.start && tgt.end < this.end) {
			return new ArrayList<TimePeriod>(Arrays.asList(new TimePeriod(this.start,tgt.start),new TimePeriod(tgt.end,this.end)));
		}

		//   <---->
		// <--------->
		if (tgt.start <= this.start && this.end <= tgt.end) {
			return new ArrayList<TimePeriod>(Arrays.asList());
		}

		// <------>
		//    <------>
		if (this.start <= tgt.start && this.end <= tgt.end) {
			return new ArrayList<TimePeriod>(Arrays.asList(new TimePeriod(this.start,tgt.start)));
		}

		//    <------>
		// <------>
		if (tgt.start <= this.start && tgt.end <= this.end) {
			return new ArrayList<TimePeriod>(Arrays.asList(new TimePeriod(tgt.end,this.end)));
		}

		throw new RuntimeException("たぶん他のケースは無い？");
	}
}

	//もっと重複・排他処理を簡単に記述できないかと思うが、面倒なのでコピペ＋後で
	//なんかこう、アルゴリズム的な何かを使って

	//数直線の概念を扱うライブラリとか探したが無かった

	//そもそも時間帯をstartとendで表現するべきではなかった、いっそSet使って時間帯をすべて連続数値で埋めるべきだった
	//そうすればSetの集合和・集合積の演算を使えて上記のメソッドを作る必要がなかった。（でもメモリを食うなぁ）

	//使うかわからんけど和集合もあったら便利だった
