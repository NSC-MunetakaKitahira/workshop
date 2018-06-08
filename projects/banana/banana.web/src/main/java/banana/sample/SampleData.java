package banana.sample;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//@Table(name = "SAMPLE_DATA") // テーブル名を指定
@Table(name = "WORKSHIFT") // テーブル名を指定
//@Table(name = "SAMPLE_DATA") // テーブル名を指定
@Entity
public class SampleData {
	
	@Id // 主キーであることの表明
	@Column(name = "masterCode") // 列名を指定
	private int masterCode;
	
	@Column(name = "worktimeStart")
	private int worktimeStart;
	
	@Column(name = "worktimeEnd")
	private int worktimeEnd;

	@Column(name = "overtime1Start")
	private int overtime1Start;
	
	@Column(name = "overtime1End")
	private int overtime1End;
	
	@Column(name = "overtime2Start")
	private int overtime2Start;
	
	@Column(name = "overtime2End")
	private int overtime2End;
	
	@Column(name = "resttime1Start")
	private int resttime1Start;
	
	@Column(name = "resttime1End")
	private int resttime1End;
	
	@Column(name = "resttime2Start")
	private int resttime2Start;
	
	@Column(name = "resttime2End")
	private int resttime2End;

	
	public SampleData() {
	}
	
	public List<Integer> getTime() {
		
		List<Integer> list = new ArrayList<>();
		
		list.add(masterCode);
		list.add(worktimeStart);
		list.add(worktimeEnd);
		list.add(overtime1Start);
		list.add(overtime1End);
		list.add(overtime2Start);
		list.add(overtime2End);
		list.add(resttime1Start);
		list.add(resttime1End);
		list.add(resttime2Start);
		list.add(resttime2End);
		
		return list;
	}


	public SampleData(int masterCode) {
		this.masterCode = masterCode;
	}
}
