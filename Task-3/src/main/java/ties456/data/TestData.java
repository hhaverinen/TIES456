package ties456.data;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * @author Tuomo Heino
 * @version 28/09/16.
 */
@XmlRootElement
public class TestData {
    private long id;
    private String name;
    private Date updated;
    private String group;
    
    @Override
    public String toString() {
        return "TestData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", updated=" + updated +
                ", group='" + group + '\'' +
                '}';
    }
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Date getUpdated() {
        return updated;
    }
    
    public void setUpdated(Date updated) {
        this.updated = updated;
    }
    
    public String getGroup() {
        return group;
    }
    
    public void setGroup(String group) {
        this.group = group;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        TestData testData = (TestData) o;
    
        return id == testData.id;
    
    }
    
    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
