package chengweiou.universe.milkyway.sdk;


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import chengweiou.universe.blackhole.model.BasicRestCode;
import chengweiou.universe.blackhole.model.Rest;
import chengweiou.universe.blackhole.util.LogUtil;
import chengweiou.universe.milkyway.sdk.push.Notify;

@Component
public class NotifyService {
    @Autowired
    private SiteConfig siteConfig;
    public Rest<Long> save(Notify e) {
        List<String> paramList = new ArrayList<>();
        paramList.add("person.id=" + e.getPerson().getId());
        paramList.add("email=" + e.getEmail());
        paramList.add("sms=" + e.getSms());
        String param = paramList.stream().collect(Collectors.joining("&"));

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(siteConfig.getLeob() + "/mg/notify")).timeout(Duration.ofMinutes(2))
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

}
