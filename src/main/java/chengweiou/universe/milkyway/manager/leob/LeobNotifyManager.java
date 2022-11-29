package chengweiou.universe.milkyway.manager.leob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import chengweiou.universe.blackhole.exception.FailException;
import chengweiou.universe.blackhole.model.BasicRestCode;
import chengweiou.universe.blackhole.model.Rest;
import chengweiou.universe.milkyway.sdk.NotifyService;
import chengweiou.universe.milkyway.sdk.push.Notify;

@Service
public class LeobNotifyManager {
    @Autowired
    private NotifyService nofityService;

    public Long saveOrUpdate(Notify e) throws FailException {
        Rest<Long> rest = nofityService.save(e);
        if (rest.getCode() != BasicRestCode.OK) throw new FailException("nofity service return code of: " + rest.getCode() + ". and message:" + rest.getMessage());
        e.setId(rest.getData());
        return rest.getData();
    }
}
