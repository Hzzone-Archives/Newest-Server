import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

import com.gargoylesoftware.htmlunit.*;

public class NewsCatch {

    public static void main(String[] args) throws IOException{
        List<NewsCatch> list = NewsCatch.NewsCatching();
        System.out.println(list.size());
        System.out.println(list.get(0).getTitle());

    }



    private String title = null;

    private String date = null;

    private String contents = null;

    private String id = null;

    private String catagory = null;

    private String url_address = null;

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

            //这段测试用，一会儿把删掉
            FileWriter fw = new FileWriter("myfile.txt");


        List<NewsCatch> newsCatchList = new ArrayList<>();
        final WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setCssEnabled(false);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        HtmlPage htmlPage = null;

        try {
            htmlPage = webClient.getPage(website);
            Thread.sleep(3000);
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

                fw.write(link);

                fw.write("\n\n新闻id****************************************************************\n\n");


                //爬取新闻id
                String pattern = "(\\d+)";
                Pattern r = Pattern.compile(pattern);
                Matcher m = r.matcher(link);
                if(m.find()){
                    newsCatch.setId(m.group(0));
                }

                fw.write(m.group(0));


                fw.write("\n\n新闻类别****************************************************************\n\n");

                //爬取新闻类别
                String [] parts = link.split("/");
                newsCatch.setCatagory(parts[2]);

                fw.write(parts[2]);

                fw.write("\n\n新闻标题****************************************************************\n\n");


                //爬取新闻标题
                newsCatch.setTitle(xw.first()
                        .select("header")
                        .select("h1").html());

                fw.write(xw.first()
                        .select("header")
                        .select("h1").html());

                fw.write("\n\n新闻时间****************************************************************\n\n");


                //爬取新闻时间
                newsCatch.setDate(xw.first()
                        .select("header")
                        .select("div")
                        .select("span:nth-child(1)").html());

                fw.write(xw.first()
                        .select("header")
                        .select("div")
                        .select("span:nth-child(1)").html());

                fw.write("\n\n新闻正文****************************************************************\n\n");


                //爬取新闻正文
                newsCatch.setContents(xw.first()
                        .select("div.cnbeta-article-body").html());

                fw.write(xw.first()
                        .select("div.cnbeta-article-body").html());

                fw.write("****************************************************************\n\n\n\n\n\n");



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
