package banana.sample;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * SAMPLE_DATAへの読み書き
 */
@Stateless // おまじない
public class SampleTransaction {
	
	// おまじない
    @PersistenceContext(unitName = "BANANA")
    private EntityManager entityManager;

    /**
     * 書き込み
     * @param param
     */
//	public void write(SampleWriteParameter param) {
//		
//		SampleData data = this.entityManager.find(SampleData.class, param.getCode());
//		if (data != null) {
//			// update
//			data.setStartTime(param.getStartTime());
//			data.setEndTime(param.getEnd());
//		} else {
//			// insert
//			data = new SampleData(param.getCode(), param.getStartTime(),param.getEnd());
//			this.entityManager.persist(data);
//		}
//		
//	}
//	
	/**
	 * 読み込み
	 * @param name
	 * @return
	 */
	public SampleReadResult read(int masterCode) { //String name
		SampleData data = this.entityManager.find(
				SampleData.class, // エンティティのクラス
				//name // 主キー
				masterCode // 主キー
				);
		
		if (data == null) {
			return SampleReadResult.notFound();
		}
		
//		return SampleReadResult.found(data.getName(), data.getValue());
		return SampleReadResult.found(data.getTime());
	}
}
