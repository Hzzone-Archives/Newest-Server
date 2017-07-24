import com.google.gson.Gson;
import jdk.nashorn.internal.scripts.JD;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hzzone on 2017/7/22.
 */
public class Test {
    public static void main(String[] args){
        String post_id = "11";
        String sql = String.format("SELECT * FROM public.comment AS c WHERE c.post_id='%s'", post_id).toString();
        Jdbc jdbc = new Jdbc();
        ResultSet rs = jdbc.querydata(sql);
        List<Comment> comments = new ArrayList<>();
        Gson gson = new Gson();
        try {
            while (rs.next()) {
                Comment comment = new Comment();
                comment.setPost_id(post_id);
                comment.setContent(rs.getString("content"));
                comment.setDate(rs.getString("time"));
                String from_id = rs.getString("from_id");
                comment.setFrom_id(from_id);
                String to_id = rs.getString("to_id");
                if (to_id != null) {
                    comment.setTo_id(to_id);
                    System.out.println(sql);
                    ResultSet rs1 = jdbc.querydata(sql);
                    while (rs1.next()) {
                        comment.setTo_name(rs1.getString("to_name"));
                        comment.setTo_pic(rs1.getString("to_pic"));
                    }
                }
                sql = String.format("select * from public.user where user_id='%s'", from_id);
                Jdbc jdbc1 = new Jdbc();
                ResultSet rs2 = jdbc1.querydata(sql);
                while (rs2.next()) {
                    comment.setFrom_name(rs2.getString("user_name"));
                    comment.setFrom_pic(rs2.getString("pic"));
                }
                comments.add(comment);
                System.out.println(gson.toJson(comment));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
