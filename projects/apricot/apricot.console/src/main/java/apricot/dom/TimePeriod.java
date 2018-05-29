package apricot.dom;

/**
 * @author yuta_sano
 *
 */
public class TimePeriod{
	private Time startTime;
	private Time endTime;


	/**
	 * @param startTime Timeクラスの開始時刻
	 * @param endTime Timeクラスの修了時刻
	 */
	public TimePeriod(Time startTime, Time endTime) {
		this(startTime.toInt(),endTime.toInt());
	}
	/**
	 * @param startTimeOfInt 整数形式の開始時刻（e.g. 830）
	 * @param endTimeOfInt 整数形式の修了時刻（e.g. 1730）
	 */
	public TimePeriod(int startTimeOfInt, int endTimeOfInt) {
		this.startTime = new Time(startTimeOfInt);
		this.endTime = new Time(endTimeOfInt);
	}

	/**
	 * @param startTimeOfInt 文字列形式の開始時刻（e.g. "8:30"）
	 * @param endTimeOfInt 文字列形式の修了時刻（e.g. "17:30"）
	 */
	public TimePeriod(String startTimeOfString, String endTimeOfString) {
		this.startTime = new Time(startTimeOfString);
		this.endTime = new Time(endTimeOfString);
	}
	
	public Time start() {
		return this.startTime;
	}
	public Time end() {
		return this.endTime;		
	}

	@Override
	public String toString() {
		return startTime.toString() + "～" + endTime.toString();
	}
	
	public Time temporalLength() {
		return this.end().sub(this.start());
	}
	
	/**
	 * targetに指定された期間との重複している期間を取得する.
	 * @param target
	 * @return duplicationTimePeriod 重複している期間
	 */
	public TimePeriod getDuplicationWith(TimePeriod target) {
		Time thisStartTime = this.startTime;
		Time thisEndTime = this.endTime;
		Time targetStartTime = target.start();
		Time targetEndTime = target.end();

		java.util.EnumMap<DuplicationType, TimePeriod> duplicationMap = new java.util.EnumMap<DuplicationType, TimePeriod>(DuplicationType.class){
			private static final long serialVersionUID = 1L;
			{
				this.put(DuplicationType.noDuplication, new TimePeriod(0, 0));
				this.put(DuplicationType.cover, new TimePeriod(targetStartTime, targetEndTime));
				this.put(DuplicationType.beCovered, new TimePeriod(thisStartTime, thisEndTime));
				this.put(DuplicationType.overtake, new TimePeriod( thisStartTime,  targetEndTime ));
				this.put(DuplicationType.beOvertaken, new TimePeriod( targetStartTime, thisEndTime ));
				this.put(DuplicationType.startSame, this.get(DuplicationType.cover));
				this.put(DuplicationType.endSame, this.get(DuplicationType.cover));
			}
		};
		
		return duplicationMap.get(this.compareWith(target));		
	}
	/**
	 * targetに指定された期間との重複している期間を取得する.
	 * @param target
	 * @return duplicationTimePeriod 分割された期間のリスト
	 */
	public TimePeriod[] getSubtractionWith(TimePeriod target) {
		Time thisStartTime = this.startTime;
		Time thisEndTime = this.endTime;
		Time targetStartTime = target.start();
		Time targetEndTime = target.end();

		java.util.EnumMap<DuplicationType, TimePeriod[]> duplicationMap = new java.util.EnumMap<DuplicationType, TimePeriod[]>(DuplicationType.class){
			private static final long serialVersionUID = 1L;
			{
				this.put(DuplicationType.noDuplication, new TimePeriod[] { new TimePeriod(thisStartTime, thisEndTime) });
				this.put(DuplicationType.cover, new TimePeriod[] { new TimePeriod(thisStartTime, targetStartTime), new TimePeriod(targetEndTime, thisEndTime) } );
				this.put(DuplicationType.beCovered, new TimePeriod[] {  } );
				this.put(DuplicationType.overtake, new TimePeriod[] { new TimePeriod( targetEndTime, thisEndTime ) } );
				this.put(DuplicationType.beOvertaken, new TimePeriod[] { new TimePeriod(thisStartTime, targetStartTime ) });
				this.put(DuplicationType.startSame, new TimePeriod[] { new TimePeriod(targetEndTime, thisEndTime) } );
				this.put(DuplicationType.endSame, new TimePeriod[] { new TimePeriod(thisStartTime, targetStartTime) } );
			}
		};
		
		return duplicationMap.get(this.compareWith(target));		
	}
	public DuplicationType compareWith(TimePeriod target) {
		Time thisStartTime = this.startTime;
		Time thisEndTime = this.endTime;
		Time targetStartTime = target.start();
		Time targetEndTime = target.end();
		
		// <--->
		//       <--->
		if (thisEndTime.le(targetStartTime)) {
			return DuplicationType.noDuplication;
		}
		
		//       <--->
		// <--->
		if (thisStartTime.ge(targetEndTime)) {
			return DuplicationType.noDuplication;
		}
		
		// <--------->
		// <---->
		if (thisStartTime.eq(targetStartTime) && thisEndTime.gt(targetEndTime)) {
			return DuplicationType.startSame;
		}

		// <--------->
		//      <---->
		if (thisStartTime.lt(targetStartTime) && thisEndTime.eq(targetEndTime)) {
			return DuplicationType.endSame;
		}

		// <--------->
		//   <---->
		if (thisStartTime.le(targetStartTime) && thisEndTime.ge(targetEndTime)) {
			return DuplicationType.cover;
		}

		//   <---->
		// <--------->
		if (thisStartTime.ge(targetStartTime) && thisEndTime.le(targetEndTime)) {
			return DuplicationType.beCovered;
		}
		
		// <------>
		//    <------>
		if (thisStartTime.le(targetStartTime) && thisEndTime.le(targetEndTime)) {
			return DuplicationType.beOvertaken;
		}

		//    <------>
		// <------>
		if (thisStartTime.ge(targetStartTime) && thisEndTime.ge(targetEndTime)) {
			return DuplicationType.overtake;
		}
		
		throw new RuntimeException("たぶん他のケースは無い？");
	}
}