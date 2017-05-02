/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;


/**
 *
 * @author Alberto G. Lagos
 */
public class MessageHandler {
    
    public static void addErrorMessage(String msg, String clientId) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", msg);
        facesContext.addMessage(clientId, fm);
    }
    
    public static void addInfoMessage(String msg, String clientId) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", msg);
        facesContext.addMessage(clientId, fm);
    }
}
