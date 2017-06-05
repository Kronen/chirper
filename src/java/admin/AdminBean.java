package admin;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * AdminBean manages the events fired by the components in the admin view.
 * 
 * @author Alberto G. Lagos
 */
@ManagedBean
@ViewScoped
@URLMapping(id="viewAdmin", pattern="/admin/", viewId="/faces/admin/admin-view.xhtml")
public class AdminBean {
    
    public AdminBean() {
    }
    
    @PostConstruct
    public void init() {

    }

}
