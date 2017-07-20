import jdk.nashorn.internal.scripts.JD;
import spark.Filter;
import spark.Request;
import spark.Response;

import java.sql.ResultSet;
import java.util.List;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        enableCORS("*", "*", "*");

        get("/hello", (request, response) -> "Hello World");

        get("/login", (request, response) -> {
            return "login page";
        });

        post("/login", ((request, response) -> {
            String user_id = request.queryParams("user_id");
            String password = request.queryParams("password");
            log(user_id + " " + password);
            if(password == null || user_id == null)
                return "empty value";
            User user = new User(user_id, password);
            if(user.isExists()) {
                user = user.getUser();
                if (password.equals(user.getPassword()))
                    return "{\"isOk\":true, \"msg\":\"登录成功\",\"user\":"+user.toString()+"}";
                else return "{\"isOk\":false, \"msg\":\"密码错误\"}";
            }
            else
                return "{\"isOk\":false, \"msg\":\"用户不存在\"}";
        }));

        /**
         * 注册1：发送验证码
         */
        post("/register-1", (((request, response) -> {
            String user_id = request.queryParams("user_id");
            String password = request.queryParams("password");
            String user_name = request.queryParams("user_name");
            Functions.log(user_id+ "\t"+user_name + "\t"+password);
            if (user_id==null||user_name==null||password==null)
                return "empty value";
            User user = new User(user_id, password);
            user.setUser_name(user_name);
            String code = null;
            if (user.isExists()){
                user = user.getUser();
                return "{\"isOk\":false, \"msg\":\"用户已存在\""+"}";
            }
            else {
                user.setToken(Functions.getRandomString(30));
                code = Functions.getRandomString(6);
                Functions.sendEmail("验证码", code, user_id);
            }
            return "{\"isOk\":true, \"msg\":\""+code+"\"}";
        })));

        /**
         * 登录2：插入数据库
         */
        post("/register-2", (((request, response) -> {
            String user_id = request.queryParams("user_id");
            String password = request.queryParams("password");
            String user_name = request.queryParams("user_name");
            Functions.log(user_id+ "\t"+user_name + "\t"+password);
            if (user_id==null||user_name==null||password==null)
                return "empty value";
            User user = new User(user_id, password);
            user.setUser_name(user_name);
            Jdbc jdbc = new Jdbc();
            while (true) {
                String token = Functions.getRandomString(30);
                String sql = "select * from public.user where token='" + token + "'";
                Functions.log(sql);
                ResultSet rs = jdbc.querydata(sql);
                List<User> users = Functions.getUserList(rs);
                if (users.size() == 0) {
                    user.setToken(token);
                    break;
                }
            }
            String sql = String.format("insert into public.user(user_id, user_name, password, token) values('%s', '%s', '%s', '%s')"
                    , user.getUser_id(), user.getUser_name(), user.getPassword(), user.getToken());
            Functions.log(sql);
            jdbc.save(sql);
            return String.format("{\"isOk\":true, \"msg\":\"%s\",\"user\":%s}", "注册成功", user.toString());
        })));
    }

    private static void log(String s){
        System.out.println(s);
    }

    private static void enableCORS(final String origin, final String methods, final String headers) {
        before(new Filter() {
            @Override
            public void handle(Request request, Response response) {
                response.header("Access-Control-Allow-Origin", origin);
                response.header("Access-Control-Request-Method", methods);
                response.header("Access-Control-Allow-Headers", headers);
            }
        });
    }

}