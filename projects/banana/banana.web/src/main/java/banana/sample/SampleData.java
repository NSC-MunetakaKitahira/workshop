package banana.sample;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "SAMPLE_DATA")
@Entity
public class SampleData {

	@Id
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "VALUE")
	private int value;

	public SampleData() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public SampleData(String name, int value) {
		this.name = name;
		this.value = value;
	}
}
