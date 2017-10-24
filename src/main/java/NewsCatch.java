import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import javafx.scene.input.DataFormat;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

import com.gargoylesoftware.htmlunit.*;

import javax.xml.crypto.Data;

public class NewsCatch {

    public static void main(String[] args){
        String test = "<div class=\"topic\">\n" +
                " <a href=\"http://www.cnbeta.com/topics/422.htm\" target=\"_blank\"><img title=\"the United States 美国\" src=\"http://static.cnbetacdn.com/topics/0f975693732be0a.png\"></a>\n" +
                "</div> \n" +
                "<p><strong>美国总统川普和俄罗斯总统普京曾在之前的会晤时共商成立美俄网络安全联盟的事宜，但是美国国家安全局执行官迈克·罗杰斯对此举表示反对。</strong>考虑到美俄之间微妙的外交关系、俄国操控美国大选的传言以及川普对自己和俄罗斯关系的不断否认，迈克对于联盟的反对有些出人意料。他表示现在并不是成立该联盟的最佳时机。</p><p>在上周出席的一场安全论坛上，他对于美俄两国网络安全联盟的问题回应道，尽管我不负责决策，但是我认为现在并不是成立联盟的最好时机。</p>\n" +
                "<p><img src=\"https://upload.wikimedia.org/wikipedia/commons/3/39/Flag-map_of_Russia-USA.svg\"></p>\n" +
                "<p>不过考虑到美俄两国之间的利益，是否有合作的时机还尚不明朗，我们也无法确认两国元首对于此项提议讨论的严肃和细节程度。甚至连川普本人都表示我和普京讨论过这个联盟并不意味着它一定会成立。</p>";
        Document doc = Jsoup.parse(test);
        Elements elements = doc.select("div.topic");
        String pic = null;
        Elements elements1;
        for (Element element:elements) {
            elements1 = element.getElementsByTag("img");
            for (Element element1 : elements1)
                pic = element1.attr("src");
        }
        doc.select("div.topic").remove();

    }

    private String title = null;

    private String date = null;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    private String source = "Newest Tech";

    private String contents = null;

    private String id = null;

    private String catagory = null;

    private String url_address = null;

    public Boolean isExists(){
        Jdbc jdbc  = new Jdbc();
        String sql = String.format("SELECT * FROM public.post WHERE post_id='%s'", this.getUrl_address()).toString();
        ResultSet rs = jdbc.querydata(sql);
        Boolean value = false;
        int i = 0;
        try {
            while (rs.next())
                i++;
            if (i>0)
                value = true;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return value;
    }

    public String getUrl_address() {
        return url_address;
    }

    public void setUrl_address(String url_address) {
        this.url_address = url_address;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCatagory() {
        return catagory;
    }

    public void setCatagory(String catagory) {
        this.catagory = catagory;
    }

    private static String website = "http://www.cnbeta.com/";

    public static List<NewsCatch> NewsCatching() throws  IOException{

        List<NewsCatch> newsCatchList = new ArrayList<>();
        final WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setCssEnabled(false);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        HtmlPage htmlPage = null;

        try {
            htmlPage = webClient.getPage(website);
            Thread.sleep(1000);
        } catch (IOException e){
            e.printStackTrace();
        }catch (InterruptedException e){
            e.printStackTrace();
        }



        String pageXml = htmlPage.asXml();



        try {
            Document document = Jsoup.parse(pageXml);

            Elements main = document.getElementsByClass("items-area");

            Elements url = main.first().getElementsByClass("item");


            for (Element news : url) {
                if (news.attr("class").equals("item cooperation"))
                    continue;

                //每条新闻的链接
                String link = news.select("a").first().attr("abs:href");
                Document document1 = Jsoup.connect(link)
                        .userAgent("Mozilla/5.0 (Windows; U; Windows NT 5.2) AppleWebKit/525.13 (KHTML, like Gecko) Chrome/0.2.149.27 Safari/525.13 ")
                        .get();

                Elements xw = document1.getElementsByClass("cnbeta-article");

                NewsCatch newsCatch = new NewsCatch();


                //爬取新闻url
                newsCatch.setUrl_address(link);

                //爬取新闻id
                String pattern = "(\\d+)";
                Pattern r = Pattern.compile(pattern);
                Matcher m = r.matcher(link);
                if(m.find())
                    newsCatch.setId(m.group(0));



                //爬取新闻类别
                String[] parts = link.split("/");
                newsCatch.setCatagory(parts[4]);


                //爬取新闻标题
                newsCatch.setTitle(xw.first()
                        .select("header")
                        .select("h1").html());


                //爬取新闻时间
                String str = xw.first()
                        .select("header")
                        .select("div")
                        .select("span:nth-child(1)").html();
                String s1 = str.split(" ")[0];
                String s2 = str.split(" ")[1];
                s2 = s2.replaceAll("\r|\n", " ");
                String[] s = s2.split("  ");
                if (s.length==2)
                    newsCatch.setSource(s[1]);

                newsCatch.setDate(s1);


                //爬取新闻正文
                String topic = xw.first().select("div.article-summary").html();
                String content = xw.first().select("div.article-content").html();
                newsCatch.setContents(topic+content);



                //将newsCatch加入到List
                newsCatchList.add(newsCatch);
            }
        }catch (NullPointerException e){
            e.getStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return newsCatchList;
    }
}
