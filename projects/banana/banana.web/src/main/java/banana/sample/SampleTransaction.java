package banana.sample;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class SampleTransaction {
	
    @PersistenceContext(unitName = "BANANA")
    private EntityManager entityManager;

	public void write(SampleWriteParameter param) {
		
		SampleData data = this.entityManager.find(SampleData.class, param.getName());
		if (data != null) {
			data.setValue(param.getValue());
		} else {
			data = new SampleData(param.getName(), param.getValue());
			this.entityManager.persist(data);
		}
		
	}
	
	public SampleReadResult read(String name) {
		SampleData data = this.entityManager.find(SampleData.class, name);
		
		if (data == null) {
			return SampleReadResult.notFound();
		}
		
		return SampleReadResult.found(data.getName(), data.getValue());
	}
}
