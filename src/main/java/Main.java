import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {

        get("/hello", (req, res) -> "Hello World");

        get("/login", (req, res) -> {
            return "login page";
        });

        post("/login", ((req, res) -> {
            return "hello world";
        }));
    }

    private void log(String s){
        System.out.println(s);
    }
}