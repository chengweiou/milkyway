package chengweiou.universe.milkyway.sdk;


import chengweiou.universe.blackhole.model.BasicRestCode;
import chengweiou.universe.blackhole.model.Rest;
import chengweiou.universe.blackhole.util.LogUtil;
import chengweiou.universe.milkyway.model.entity.person.Person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Component
public class CarinaPersonService {
    @Autowired
    private SiteConfig siteConfig;
    public Rest<Long> save(Person e) {
        List<String> paramList = new ArrayList<>();
        paramList.add("id=" + e.getId());
        paramList.add("name=" + e.getName());
        // paramList.add("imgsrc=" + e.getImgsrc());
        String param = paramList.stream().collect(Collectors.joining("&"));

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(siteConfig.getCarina() + "/mg/person")).timeout(Duration.ofMinutes(2))
                .method("POST", HttpRequest.BodyPublishers.ofString(param))
                .header("content-type", "application/x-www-form-urlencoded")
                .header("inServer", "true")
                .build();
        HttpClient client = HttpClient.newHttpClient();
        try {
            String response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body).get();
            return Rest.from(response, Long.class);
        } catch (InterruptedException | ExecutionException ex) {
            LogUtil.e("person save fail", ex);
            return Rest.fail(BasicRestCode.FAIL);
        }
    }

    public Rest<Boolean> update(Person e) {
        List<String> paramList = new ArrayList<>();
        if (e.getName()!=null) paramList.add("name=" + e.getName());
        // if (e.getImgsrc()!=null) paramList.add("imgsrc=" + e.getImgsrc());
        String param = paramList.stream().collect(Collectors.joining("&"));

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(siteConfig.getCarina() + "/mg/person/" + e.getId())).timeout(Duration.ofMinutes(2))
                .method("PUT", HttpRequest.BodyPublishers.ofString(param))
                .header("content-type", "application/x-www-form-urlencoded")
                .header("inServer", "true")
                .build();
        HttpClient client = HttpClient.newHttpClient();
        try {
            String response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body).get();
            return Rest.from(response, Boolean.class);
        } catch (InterruptedException | ExecutionException ex) {
            LogUtil.e("person update fail: personId: " + e.getId(), ex);
            return Rest.fail(BasicRestCode.FAIL);
        }
    }
}
