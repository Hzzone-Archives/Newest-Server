import java.util.Date;

/**
 * Created by Hzzone on 2017/7/21.
 */
public class Comment {
    private String post_id;
    private String from_id;
    private String to_id;
    private String content;
    private Date date;
    private String from_name;
    private String from_pic;
    private String to_name;
    private String to_pic;

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getFrom_id() {
        return from_id;
    }

    public void setFrom_id(String from_id) {
        this.from_id = from_id;
    }

    public String getTo_id() {
        return to_id;
    }

    public void setTo_id(String to_id) {
        this.to_id = to_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
