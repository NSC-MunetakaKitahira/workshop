package apricot.dom;

/**
 * 時刻（任意の一日内における0時からの経過時間）を表すクラス。
 * 精度は、現在の仕様上、時と分まで。
 */
public class TimeOfDay {
	
	/** 0時からの経過時間(分) */
	private final int minutesFromZero;
	
	/**
	 * コンストラクタ
	 * @param minutesFromZero 0時からの経過時間(分)
	 */
	public TimeOfDay(int minutesFromZero) {
		this.minutesFromZero = minutesFromZero;
	}
	
	/**
	 * コンストラクタ
	 * @param hourPart 時刻の「時」部分の値
	 * @param minutePart 時刻の「分」部分の値
	 */
	public TimeOfDay(int hourPart, int minutePart) {
		this(hourPart * 60 + minutePart);
	}
	
	/**
	 * 内部値を返す
	 * @return 内部値 0時からの経過時間(分)
	 */
	public int value() {
		return this.minutesFromZero;
	}
}
