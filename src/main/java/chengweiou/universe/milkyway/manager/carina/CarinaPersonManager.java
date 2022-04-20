package chengweiou.universe.milkyway.manager.carina;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import chengweiou.universe.blackhole.exception.FailException;
import chengweiou.universe.blackhole.model.BasicRestCode;
import chengweiou.universe.blackhole.model.Rest;
import chengweiou.universe.milkyway.model.entity.person.Person;
import chengweiou.universe.milkyway.sdk.CarinaPersonService;

@Profile("!test")
@Service
public class CarinaPersonManager {
    @Autowired
    private CarinaPersonService service;
    public Long save(Person e) throws FailException {
        Rest<Long> rest = service.save(e);
        if (rest.getCode() != BasicRestCode.OK) throw new FailException("carina person service return code of: " + rest.getCode() + ". and message:" + rest.getMessage());
        e.setId(rest.getData());
        return rest.getData();
    }
    public Boolean update(Person e) throws FailException {
        Rest<Boolean> rest = service.update(e);
        if (rest.getCode() != BasicRestCode.OK) throw new FailException("carina person service return code of: " + rest.getCode() + ". and message:" + rest.getMessage());
        return rest.getData();
    }
}
