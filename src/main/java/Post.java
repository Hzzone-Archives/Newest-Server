import javax.swing.text.Style;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Created by Hzzone on 2017/7/20.
 */
public class Post {
    private String post_id;
    private String author_name;
    private String author_id;
    private String title;
    private String content;
    private String time;
    private List<Comment> comments = null;
    private String author_pic;
    private String source = null;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    private String category = null;

    public void setLiked(Boolean liked) {
        isLiked = liked;
    }

    private Boolean isLiked = Boolean.FALSE;

    public Boolean isLiked(String user_id){
        Jdbc jdbc = new Jdbc();
        String sql = String.format("SELECT * FROM public.liked WHERE user_id='%s' AND post_id='%s'", user_id, post_id);
        ResultSet rs = jdbc.querydata(sql);
        int i = 0;
        try {
            while (rs.next())
                i++;
        } catch (SQLException e){
            e.printStackTrace();
        }
        if (i==0)
            return Boolean.FALSE;
        return Boolean.TRUE;
    }


    public int getLiked() {
        return liked;
    }

    public void setLiked(int liked) {
        this.liked = liked;
    }

    private int liked;

    public String getAuthor_pic() {
        return author_pic;
    }

    public void setAuthor_pic(String author_pic) {
        this.author_pic = author_pic;
    }


    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }


    public static Post getPost(String post_id, String author_name, String author_id, String title, String content, String time){
        Post post = new Post();
        post.setAuthor_id(author_id);
        post.setAuthor_name(author_name);
        post.setContent(content);
        post.setPost_id(post_id);
        post.setTime(time);
        post.setTitle(title);
        return post;
    }
    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(String author_id) {
        this.author_id = author_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
