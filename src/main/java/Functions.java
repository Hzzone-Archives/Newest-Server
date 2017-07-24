import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.util.MailSSLSocketFactory;

/**
 * Created by Hzzone on 2017/7/20.
 */
public class Functions {
    public static void log(String s){
        System.out.println(s);
    }

    public static List<User> getUserList(ResultSet rs){
        List<User> users = new ArrayList<User>();
        try {
            while (rs.next()){
                User user = new User();
                user.setUser_id(rs.getString("user_id"));
                user.setUser_name(rs.getString("user_name"));
                user.setPassword(rs.getString("password"));
                user.setToken(rs.getString("token"));
                user.setPic(rs.getString("pic"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return users;
        }
    }


    public static void sendEmail(String title, String context, String _to){
        Properties prop = new Properties();
        prop.setProperty("mail.debug", "true");
        prop.setProperty("mail.host", "smtp.qq.com");
        prop.setProperty("mail.smtp.auth", "true");
        prop.setProperty("mail.transport.protocol", "smtp");
        try {
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            prop.put("mail.smtp.ssl.enable", "true");
            prop.put("mail.smtp.ssl.socketFactory", sf);
            Session session = Session.getInstance(prop);
            Transport ts = session.getTransport();
            ts.connect("smtp.qq.com", "1141408077", "emxrefjwdjprigcg");
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("1141408077@qq.com"));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(_to));
            message.setSubject(title);
            message.setContent(context, "text/html;charset=UTF-8");
            ts.sendMessage(message, message.getAllRecipients());
            ts.close();
        } catch (java.security.GeneralSecurityException e){
            e.printStackTrace();
        } catch (NoSuchProviderException e){
            e.printStackTrace();
        } catch (MessagingException e){
            e.printStackTrace();
        }

    }



    public static void main(String[] args){
//        sendEmail("Test", "hhhhhh", "zhizhonghwang@gmail.com");
        getRandomString(6);
        getRandomString(30);
    }


    private static String str1 = "abcdefghijklmnopqrstuvwxyzhjgdisjwlsldyquwaajzueya";
    private static String str2 = "124153647586908967341729304";

    public static String getRandomString(int length){
        StringBuffer sb = new StringBuffer();
        Random r = new Random();
        if (length==6)
            for (int i=0; i<length; i++)
                sb.append(str2.charAt(Math.abs(r.nextInt()%str2.length())));
        else
            for (int i = 0; i < length; i++)
                sb.append(str1.charAt(Math.abs(r.nextInt()%str1.length())));
        log(sb.toString());
        return sb.toString();
    }

//    public static void upload()
}
