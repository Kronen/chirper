package tags;

/**
 *
 * @author Alberto G. Lagos
 */
public class TrendingTopic {
    String tagName;
    Long count;

    public TrendingTopic(String tag, Long postCount) {
        this.tagName = tag;
        this.count = postCount;
    }
    
    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
    
    
}
