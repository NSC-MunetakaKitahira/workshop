package banana.sample;

public class SampleReadResult {

	private final String name;
	private final int value;
	private final String errorMessage;
	
	private SampleReadResult(String name, int value, String errorMessage) {
		this.name = name;
		this.value = value;
		this.errorMessage = errorMessage;
	}
	
	public static SampleReadResult found(String name, int value) {
		return new SampleReadResult(name, value, null);
	}
	
	public static SampleReadResult notFound() {
		return new SampleReadResult(null, 0, "データが見つかりません");
	}

	public String getName() {
		return name;
	}

	public int getValue() {
		return value;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
	
}
