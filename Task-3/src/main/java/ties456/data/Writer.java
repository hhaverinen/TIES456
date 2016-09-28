package ties456.data;

/**
 * @author Tuomo Heino
 * @version 28/09/16.
 */
public class Writer extends BaseData<Writer> {
    private String name;
    
    @Override
    public void updateData(Writer update) {
        this.name = update.name;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
}
