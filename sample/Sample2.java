import java.util.ArrayList;
import java.util.List;

public class Sample2 {
    private List<Sample1> entities;

    public Sample2() {
        entities = new ArrayList<>();
    }

    public void addEntity(Sample1 entity) {
        entities.add(entity);
    }

    public Sample1 findEntityById(int id) {
        for (Sample1 entity : entities) {
            if (entity.getId() == id) {
                return entity;
            }
        }
        return null;
    }

    public int getEntityCount() {
        return entities.size();
    }

    public List<Sample1> getAllEntities() {
        return new ArrayList<>(entities);
    }
}
