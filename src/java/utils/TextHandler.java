package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Alberto G. Lagos
 */
public class TextHandler {
    
    public static List extractTags(String text) {
        Matcher m = Pattern.compile("\\B#[a-zA-Z0-9_-]{4,81}").matcher(text);
        
        List<String> allTags = new ArrayList<>();        
        while(m.find())
            allTags.add(m.group());
        return allTags;
    }
    
    public static List extractMentions(String text) {
        /* min/max length is {4, 21} because username  */
        Matcher m = Pattern.compile("\\B@[a-zA-Z0-9_-]{4,21}").matcher(text); 
        
        List<String> allMentions = new ArrayList<>();        
        while(m.find())
            allMentions.add(m.group());        
        return allMentions;
    }
}
