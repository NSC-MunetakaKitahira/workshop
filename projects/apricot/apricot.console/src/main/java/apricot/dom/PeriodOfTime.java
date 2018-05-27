package apricot.dom;

/**
 * 時間帯クラス
 * 時刻の開始と終了の組み合わせを表す
 *
 */
public class PeriodOfTime {

	/** 開始時刻 */
	private TimeOfDay startTime;

	/** 終了時刻 */
	private TimeOfDay endTime;

	/**
	 * 開始時刻を取得
	 * @return 開始時刻
	 */
	public TimeOfDay getStartTime()
	{
		return this.startTime;
	}

	/**
	 * 終了時刻を取得
	 * @return 終了時刻
	 */
	public TimeOfDay getEndTime()
	{
		return this.endTime;
	}

	/**
	 * コンストラクタ
	 * @param startTime 開始時刻
	 * @param endTime 終了時刻
	 */
	public PeriodOfTime(TimeOfDay startTime, TimeOfDay endTime)
	{
		this.startTime = startTime;
		this.endTime = endTime;
	}
}
