package ties456.data;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Tuomo Heino
 * @version 28/09/16.
 */
@XmlRootElement
public class TestData extends BaseData<TestData> {
    private String name;
    private String group;
    
    @Override
    public void updateData(TestData update) {
        this.name = update.getName();
        this.group = update.getGroup();
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getGroup() {
        return group;
    }
    
    public void setGroup(String group) {
        this.group = group;
    }
}
