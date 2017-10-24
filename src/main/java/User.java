import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hzzone on 2017/7/19.
 */
public class User {
    private String user_id = null;
    private String user_name = null;
    private String password = null;
    private String token = null;

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    private String pic = null;

    /**
     * 获得完整的用户信息
     * @return
     */
    public User getUser(){
        Jdbc jdbc = new Jdbc();
        Functions.log(this.getUser_id());
        String sql = "select * from public.user where user_id='"+this.getUser_id()+"'";
        Functions.log(sql);
        ResultSet rs = jdbc.querydata(sql);
        List<User> users = Functions.getUserList(rs);
        if (users.size()==0)
            return null;
        System.out.println("getUser password: "+users.get(0).getPassword());
        return users.get(0);
    }

    /**
     * 判断用户是否存在
     * @return
     */
    public Boolean isExists(){
        Jdbc jdbc = new Jdbc();
        Functions.log(this.getUser_id());
        String sql = "select * from public.user where user_id='"+this.getUser_id()+"'";
        Functions.log(sql);
        ResultSet rs = jdbc.querydata(sql);
        Boolean value = false;
        List<User> users = new ArrayList<>();
        if (rs!=null) {
            users = Functions.getUserList(rs);
            if (users.size() == 1)
                value = true;
        }
        System.out.println("users size: " + users.size());
        System.out.println("value: " + value);
        return value;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "{" +
                "\"user_id\":\""  + user_id + "\"," +
                "\"user_name\":\"" + user_name + "\"," +
                "\"password\":\"" + password + "\"," +
                "\"token\":\"" + token + "\"}";
    }
}
