package utils;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author Alberto G. Lagos
 */
public class ContextHandler {
    
    public static ExternalContext externalContext() {
        return facesContext().getExternalContext();
    }

    public static FacesContext facesContext() {
        return FacesContext.getCurrentInstance();
    }
}
