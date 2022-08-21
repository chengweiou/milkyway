package chengweiou.universe.milkyway.manager.leob;

import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import chengweiou.universe.blackhole.exception.FailException;
import chengweiou.universe.blackhole.model.BasicRestCode;
import chengweiou.universe.blackhole.model.Rest;
import chengweiou.universe.milkyway.sdk.NotifyService;
import chengweiou.universe.milkyway.sdk.push.Notify;
import chengweiou.universe.milkyway.sdk.push.Push;
import chengweiou.universe.milkyway.sdk.push.PushService;

@Service
public class PushManager {
    @Autowired
    private PushService pushService;

    @Async
    public Future<Long> pushAsync(Push e) throws FailException {
        Rest<Long> rest = pushService.push(e);
        if (rest.getCode() != BasicRestCode.OK) new AsyncResult<>(0);
        return new AsyncResult<>(rest.getData());
    }

}
