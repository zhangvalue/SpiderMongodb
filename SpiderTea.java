/**
 * @ Author zhangsf
 * @CreateTime 2019/5/10 - 2:19 PM
 */
package com.zsf.spider;

import com.zsf.spider.model.Commontea;
import com.zsf.spider.model.Commonwine;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.zsf.spider.util.DateUtil.now;
import static com.zsf.spider.util.HttpRequest.httpRequest;

/**
 * 抓取治病常用茶
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpiderApplication.class)
public class SpiderTea {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void start() {
        String url = "http://www.pharmnet.com.cn/tcm/ycdq/zhiliao/";
        Commontea commontea = null;
        try {
            //获取到当前首页中的所有的信息
            String html = httpRequest(url, "GET", null, "gb2312");
            //第一步，将字符内容解析成一个Document类
            Document doc = Jsoup.parse(html);
            //第二步，根据我们需要得到的标签，选择提取相应标签的内容
            Elements elements = doc
                    .select("table[bgcolor=FFF3B8]")
                    .select("td[class=yellow]")
                    .select("a");
            System.out.println(elements.size());
            int i = 1;
            String department_name = null;
            //药酒名称
            String tea_name = null;
            //配方
            String formula = null;
            //功效
            String effect = null;
            String department_url = null;
            String disease_url=null;
            String use_url = null;
            String disease_name = null;
            int page;
            for (Element element : elements) {
                department_url = "http://www.pharmnet.com.cn" + element.select("a").attr("href");
                department_name = element.text();
                System.out.println("第" + i + "----科室名称：" + department_name + "-URL：" + department_url);

                String context = httpRequest(department_url, "GET", null, "gb2312");
                Document context_doc = Jsoup.parse(context);
                //进入科室URL查看疾病的名称和url
                Elements elements0 = context_doc
                        .select("table[bgcolor=FFF3B8]")
                        .select("td[width=94%]")
                        .select("a");
                System.out.println(elements0.size());
                for (int j = 0; j < elements0.size(); j++) {
                     disease_url = "http://www.pharmnet.com.cn" + elements0.get(j).select("a").attr("href");
                    disease_name = elements0.get(j).text();
                    System.out.println("科室名称：" + department_name + "疾病名：" + disease_name + "疾病url:" + disease_url);
                    String context1 = httpRequest(disease_url, "GET", null, "gb2312");
                    Document context_doc1 = Jsoup.parse(context1);
                    //进入了页面之后才会知道一共有多少条记录
                    Elements elements_pages = context_doc1.select("span[class=black]");
                    String pages = elements_pages.text();
                    /* 截取 pages数*/
                    String result = pages.substring(pages.indexOf("有") + 1, pages.lastIndexOf("条记录"));
                    if (Integer.parseInt(result.trim()) > 15) {
                        //每一页是15条数据,向上进一位
                        page = Integer.parseInt(result.trim()) / 15 + 1;
                    } else {
                        page = 1;
                    }
                    System.out.println("一共的数量:" + result + "页数为" + page);
                        //出去后面的内科方和无茶
                    for (int count = 1; i<=5&&count <= page; count++) {
                        System.out.println("当前page为" + count);
                        if (count == 1) {
                            use_url = disease_url.trim();
                        } else {
                            use_url = disease_url.trim() + "/index-" + count + ".html";
                        }

                        String context2 = httpRequest(use_url, "GET", null, "gb2312");
                        Document context_doc2 = Jsoup.parse(context2);
                    Elements elements1 = context_doc2
                            .select("table[bgcolor=FFFfe0]");
                    for (int h = 0; h <elements1.size() ; h++) {
                        Elements elements2 = elements1.get(h).select("td[class=yellow]");
                        tea_name=elements2.text();
                       // System.out.println("药酒名称："+tea_name);
                        Elements elements3 = elements1.get(h).select("td[valign=top]");
                        formula=elements3.get(0).text().split("〕")[1];
                        if (elements3.get(1).text().contains("〕")){
                            effect=elements3.get(1).text().split("〕")[1];
                        }
                        commontea=new  Commontea ( department_name,  disease_name,  tea_name,  formula,  effect,  use_url,  now()) ;
                        System.out.println(commontea.toString());
                        //插入到MongoDB中
                        mongoTemplate.insert(commontea, "spider_tea");
                    }
                    }
                }

                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
