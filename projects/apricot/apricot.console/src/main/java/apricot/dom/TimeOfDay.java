package apricot.dom;

/**
 * 時刻を表すクラス。
 * 精度は分単位。
 *
 */
public class TimeOfDay {

	/** 0:00からの経過した時間（分単位） */
	private int minutesFromZero;

	/**
	 * コンストラクタ
	 * @param minuites 0:00からの経過した時間（分単位）
	 */
	public TimeOfDay(int minutesFromZero)
	{
		this.minutesFromZero = minutesFromZero;
	}

	/**
	 * コンストラクタ
	 * @param hour 時刻の時間部分（HH:MMのHH）
	 * @param minuites 時刻の分部分（HH:MMのMM）
	 */
	public TimeOfDay(int hourPart, int minutePart)
	{
		// 今のところ、マイナスや分の60以上は考えない
		this(hourPart * 60 + minutePart);
	}

	/**
	 * 時刻を表す値を返す。
	 * @return 0:00から経過した時間（分単位）
	 */
	public int value()
	{
		return this.minutesFromZero;
	}
}
