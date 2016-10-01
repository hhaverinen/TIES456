/**
 * 
 */
package ties456.errorhandling;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Jonna
 * 
 */
@XmlRootElement
public class ErrorMessage {
	private String errorMessage;
	private int errorCode;
	private String documentation;
	public ErrorMessage(){}
	public ErrorMessage(String errorMessage, int errorCode, String documentation) {
		super();
		this.errorMessage = errorMessage;
		this.errorCode = errorCode;
		this.documentation = documentation;
	}
	
    /**
     * Gets the error message
     * @return error message
     */
	public String getErrorMessage() {
		return errorMessage;
	}
	
	/**
     * Sets error message
     * @param error message
     */
	public void setErrorMessage(String errorMessage){
		this.errorMessage=errorMessage;
	}
	
    /**
     * Gets the error code
     * @return error code
     */	
	public int getErrorCode() {
		return errorCode;
	}
	
	/**
     * sets the error code
     * @param error code
     */
	public void setErrorCode (int errorCode) {
		this.errorCode = errorCode;
	}
    /**
     * Gets the documentation
     * @return documentation
     */
	public String getDocumentation() {
		return documentation;
	}
	
	/**
     * sets the documentation
     * @param documentation
     */
	public void setDocumentation(String documentation) {
		this.documentation = documentation;
	}
	
}
