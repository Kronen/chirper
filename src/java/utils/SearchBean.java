package utils;

import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLMapping;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpa.controllers.ProfileJpaController;
import jpa.controllers.TagJpaController;
import jpa.controllers.UserJpaController;
import jpa.entities.Profile;
import jpa.entities.Tag;
import jpa.entities.User;

/**
 *
 * @author Kronen
 */
@ManagedBean
@ViewScoped
public class SearchBean {
    transient private EntityManagerFactory emf;
    private String searchText;
    
    public SearchBean() {
    }
    
    @PostConstruct
    public void init() {
        emf = Persistence.createEntityManagerFactory("ChirperDbPU");
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }
    
    /**
     * Generates a filtered list of strings for autocompleting from users and tags in the database.
     *
     *
     * @param input String with the partial text to search
     * @return a List of Strings with the strings found 
     */
    public List<String> complete(String input) {
        List<String> result = new ArrayList<>();
        
        UserJpaController userC = new UserJpaController(emf);
        List<User> users = userC.findUserEntities();
        for(User user : users) {
            if(user.getUserName().toLowerCase().contains(input.toLowerCase())) 
                result.add("@" + user.getUserName());
        }
        
        TagJpaController tagC = new TagJpaController(emf);
        List<Tag> tags = tagC.findTagEntities();
        for(Tag tag : tags) {
            if(tag.getTagName().toLowerCase().contains(input.toLowerCase()))
                result.add(tag.getTagName());
        }
        
        return result;
    }
    
    public String forwardSearch() {
        return "/search/search-view.xhtml?faces-redirect=true&includeViewParams=true";
    }
    
    public List getProfilesFound() {
        List<Profile> result = new ArrayList<>();
        UserJpaController userC = new UserJpaController(emf);
        
        List<User> users = userC.findUserEntities();
        String[] words = searchText.split(" ");
        
        for(String word : words) {
            if(word.length() > 1) {
                if(word.startsWith("@"))
                    word = word.substring(1);
                for(User user : users) {
                    if (user.getUserName().toLowerCase().contains(word.toLowerCase()))
                        result.add(getProfileByUserName(user.getUserName()));
                }
            }
        }
        
        return result;        
    }
    
    public List getTagsFound() {
        List<Tag> result = new ArrayList<>();
        TagJpaController tagC = new TagJpaController(emf);
        List<Tag> tags = tagC.findTagEntities();
        String[] words = searchText.split(" ");
        
        for(String word : words) {
            if(word.length() > 2) {
                for(Tag tag : tags) {
                    if (tag.getTagName().toLowerCase().contains(word.toLowerCase()))
                        result.add(tag);
                }
            }
        }
        
        return result;        
    }
    
    private Profile getProfileByUserName(String username){
        ProfileJpaController profileC = new ProfileJpaController(emf);
        return profileC.findProfileByUserName(username);
    }

}
