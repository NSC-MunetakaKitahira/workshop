package apricot.dom;

//時間帯の始点と終点、長さを記録するクラス
public class TimePeriod {

	//時間0:00から何分離れているか
	public final TimeOfDay start;
	public final TimeOfDay end;
	public final TimeOfDay length;
	
	public TimePeriod(TimeOfDay start, TimeOfDay end) {
		if(start.minute > end.minute) {
			throw new IllegalArgumentException("始業時刻は終業時刻より早く設定してください");
		}
		this.start = start;
		this.end = end;
		this.length = new TimeOfDay(end.minute - start.minute);
	}
	
	//重複範囲
	public TimePeriod getDuplication(TimePeriod p) {

		// <--->
		//       <--->
		if (this.end.minute < p.start.minute) {
			return null;
		}
		
		//       <--->
		// <--->
		if (p.end.minute < this.start.minute) {
			return null;
		}
		
		// <--------->
		//   <---->
		if (this.start.minute <= p.start.minute && p.end.minute <= this.end.minute) {
			return new TimePeriod ( p.start, p.end );
		}

		//   <---->
		// <--------->
		if (p.start.minute <= this.start.minute && this.end.minute <= p.end.minute) {
			return new TimePeriod ( this.start, this.end );
		}
		
		// <------>
		//    <------>
		if (this.start.minute <= p.start.minute && this.end.minute <= p.end.minute) {
			return new TimePeriod( p.start, this.end );
		}

		//    <------>
		// <------>
		if (p.start.minute <= this.start.minute && p.end.minute <= this.end.minute) {
			return new TimePeriod ( this.start,  p.end );
		}
		
		throw new RuntimeException("たぶん他のケースは無い？");
	}
	
	//被ってない範囲
	public TimePeriod[] getSubtraction(TimePeriod p) {

		// <--->
		//       <--->
		if (this.end.minute < p.start.minute) {
			return  new TimePeriod[] {new TimePeriod( this.start, this.end )};
		}
		
		//       <--->
		// <--->
		if (p.end.minute < this.start.minute) {
			return new TimePeriod[] {new TimePeriod( this.start, this.end )};
		}
		
		// <--------->
		// <---->
		if (this.start.minute == p.start.minute && p.end.minute < this.end.minute) {
			return new TimePeriod[] {new TimePeriod( p.end, this.end )};
		}

		// <--------->
		//      <---->
		if (this.start.minute < p.start.minute && this.end.minute == p.end.minute) {
			return new TimePeriod[] {new TimePeriod( this.start, p.start )};
		}
		
		// <--------->
		//   <---->
		if (this.start.minute < p.start.minute && p.end.minute < this.end.minute) {
			return new TimePeriod[] {new TimePeriod( this.start, p.start ), new TimePeriod( p.end, this.end )};
		}

		//   <---->
		// <--------->
		if (p.start.minute <= this.start.minute && this.end.minute <= p.end.minute) {
			return new TimePeriod[] {};
		}
		
		// <------>
		//    <------>
		if (this.start.minute <= p.start.minute && this.end.minute <= p.end.minute) {
			return new TimePeriod[] {new TimePeriod( this.start, p.start )};
		}

		//    <------>
		// <------>
		if (p.start.minute <= this.start.minute && p.end.minute <= this.end.minute) {
			return new TimePeriod[] {new TimePeriod( p.end, this.end )};
		}
		
		throw new RuntimeException("たぶん他のケースは無い？");
	}

	@Override
	public String toString() {
		return String.format("%s ~ %s", this.start.formatTime(), this.end.formatTime());
	}
}
