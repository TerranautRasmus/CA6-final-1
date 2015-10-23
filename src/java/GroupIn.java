
import java.util.concurrent.Callable;
import entity.Group;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GroupIn implements Callable<Group> {

    List<String> urls = new ArrayList<String>() {
        {
            //Class A
            add("http://cphbusinessjb.cloudapp.net/CA2/");
            add("http://ca2-ebski.rhcloud.com/CA2New/");
            add("http://ca2-chrislind.rhcloud.com/CA2Final/");
            add("http://ca2-pernille.rhcloud.com/NYCA2/");
            add("https://ca2-jonasrafn.rhcloud.com:8443/company.jsp");
            add("http://ca2javathehutt-smcphbusiness.rhcloud.com/ca2/index.jsp");

            //Class B
            add("https://ca2-ssteinaa.rhcloud.com/CA2/");
            add("http://tomcat-nharbo.rhcloud.com/CA2/");
            add("https://ca2-cphol24.rhcloud.com/3.semCa.2/");
            add("https://ca2-ksw.rhcloud.com/DeGuleSider/");
            add("http://ca2-ab207.rhcloud.com/CA2/index.html");
            add("http://ca2-sindt.rhcloud.com/CA2/index.jsp");
            add("http://ca2gruppe8-tocvfan.rhcloud.com/");
            add("https://ca-ichti.rhcloud.com/CA2/");

            //Class COS
            add("https://ca2-9fitteen.rhcloud.com:8443/CA2/");
            add("https://cagroup04-coolnerds.rhcloud.com/CA_v1/index.html");
            add("http://catwo-2ndsemester.rhcloud.com/CA2/");

        }
    };

    public String getUrl() {
        String url = urls.get(0);
        urls.remove(0);
        return url;
    }

    @Override
    public Group call() throws Exception {

        String urls = getUrl();
        Group group = new Group();
        Elements ele;

        Document doc = Jsoup.connect(urls).get();

        ele = doc.select("#authors");
        group.setGroupAuthors(ele.text());
        ele = doc.select("#class");
        group.setGroupClass(ele.text());
        ele = doc.select("#group");
        group.setGroupNumber(ele.text());

        return group;
    }
    
    public void getAllGroups() {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        List<Future<Group>> list = new ArrayList<>();

        Callable<Group> callable = new GroupIn();

        for (int i = 0; i < urls.size(); i++) {
            //submit Callable tasks to be executed by thread pool
            Future<Group> future = executor.submit(callable);
            //add Future to the list, we can get return value using Future
            list.add(future);
        }
        for (Future<Group> fut : list) {
            try {
                //print the return value of Future, notice the output delay in console
                // because Future.get() waits for task to get completed
                System.out.println("Hep: " + fut.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        //shut down the executor service now
        executor.shutdown();
    }

}
