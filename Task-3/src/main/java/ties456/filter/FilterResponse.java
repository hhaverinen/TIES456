package ties456.filter;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Tuomo Heino
 * @version 10/4/16.
 */
@XmlRootElement
public class FilterResponse {
    private String message;
    
    public FilterResponse(String message) {
        this.message = message;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
}
