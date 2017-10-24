import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

/**
 *
 * Jdbc: 将sql查删改增操作打包成一个接口，只要new一个对象，然后选择对应的方法传入sql语句就完成了
 *
 * 用的是postgresql, 不是SQL Server, 需要安装postgresql和相应的jdbc驱动
 */
public class Jdbc {

    private Statement stmt=null;

    public static void main(String args[]){
        Date date = new Date();
//        String s = String.format("%tc", date);
        System.out.println(date.toString());
    }

    public Jdbc(){
        init();
    }

    public void init(){
        //1.加载驱动包,代表这是postgresql的jdbc驱动包
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        //获取到数据库连接字符串

        Connection conn=null;
        try {
            conn = getConnection();
            System.out.println("Connection Success!");
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        try {
            stmt =(Statement)conn.createStatement();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws Exception {
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost:5432/postgres";
        return DriverManager.getConnection(url, "postgres", "");
    }



    /**
     * 添加一条记录到数据库
     */
    public void save(String sql){

        //4.执行操作数据库语句

//        String sql="insert into user(username,password) values('张明','hzh19870110')";
        int row=0;
        try {
            row=stmt.executeUpdate(sql);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if(row>0){
            System.out.println("数据库添加记录成功!");
        }

    }


    /**
     * 修改一条记录到数据库
     */
    public void edit(String sql){

        //4.执行操作数据库语句
//        String sql="update user set username='林佳',password='123456' where id=7";
        int row=0;
        try {
            //executeUpdate:执行增删改数据库语句时，要操作的方法
            row=stmt.executeUpdate(sql);
        } catch (SQLException e) {

            e.printStackTrace();
        }
        if(row>0){
            System.out.println("数据库修改记录成功!");
        }
    }

    /**
     * 查询记录集合
     * 返回的是一个ResultSet, 包含查询到的行
     */
    public ResultSet querydata(String sql){
        //4.执行操作数据库语句
//        String sql="select * from user";
        ResultSet rs = null;
        try {
            // executeQUery:执行查询数据库语句时，要操作的方法
            /**
             * ResultSet:查询结果集
             * 是一个集合，那样就会遍历结果集
             * rs.next():如果读取到结果集
             */
            rs=stmt.executeQuery(sql);
            System.out.println("rs: "+rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return rs;
        }

    }

    /**
     * 删除操作
     *
     *
     */
    public void delete(String sql){
        int row = 0;
        try {
            // executeQUery:执行查询数据库语句时，要操作的方法
            row = stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(row>0){
            System.out.println("数据库删除记录成功");
        }
    }






}
