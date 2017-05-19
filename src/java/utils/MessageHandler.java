package utils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;


/**
 *
 * @author Alberto G. Lagos
 */

/**
 * This class manages the messages we send to the view.
 * 
 * It allows errors and info messages to show in the view through the message/messages tags, 
 * like form's validation messages when an exception has happened
 */
public class MessageHandler {
    
    /**
     * Append an error FacesMessage to the set of messages associated with the specified client identifier.
     * 
     * If clientId is null, this FacesMessage is assumed to not be associated with any specific component 
     * instance and it will show in the view formessages tags with the attribute globalOnly set to true.
     * 
     * @param msg content of the message to append.
     * @param clientId id of the component the message will be associated to.
     */
    public static void addErrorMessage(String msg, String clientId) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", msg);
        facesContext.addMessage(clientId, fm);
    }
    
    /**
     * Append an info FacesMessage to the set of messages associated with the specified client identifier.
     * 
     * If clientId is null, this FacesMessage is assumed to not be associated with any specific component 
     * instance and it will show in the view formessages tags with the attribute globalOnly set to true.
     * 
     * @param msg content of the message to append.
     * @param clientId id of the component the message will be associated to.
     */
    public static void addInfoMessage(String msg, String clientId) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", msg);
        facesContext.addMessage(clientId, fm);
    }
}
