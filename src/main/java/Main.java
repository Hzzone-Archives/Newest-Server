import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {

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
    }

    private static void log(String s){
        System.out.println(s);
    }
}