package chengweiou.universe.milkyway.service.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import chengweiou.universe.blackhole.exception.FailException;
import chengweiou.universe.blackhole.model.Builder;
import chengweiou.universe.milkyway.base.converter.Account;
import chengweiou.universe.milkyway.manager.andromeda.AccountManager;
import chengweiou.universe.milkyway.manager.carina.CarinaPersonManager;
import chengweiou.universe.milkyway.manager.leob.LeobNotifyManager;
import chengweiou.universe.milkyway.model.entity.person.Person;
import chengweiou.universe.milkyway.sdk.push.Notify;

@Service
public class PersonService {
    @Autowired
    private PersonDio dio;
    @Autowired
    private AccountManager accountManager;
    @Autowired
    private CarinaPersonManager carinaPersonManager;
    @Autowired
    private LeobNotifyManager leobNotifyManager;

    public long save(Person e, Account account) throws FailException {
        dio.save(e);
        Builder.set("person", e).set("extra", e.getType().name()).set("phone", e.getPhone()).set("email", e.getEmail()).set("active", true).to(account);
        accountManager.save(account);
        carinaPersonManager.save(e);
        Notify notify = Builder.set("person", e).set("email", e.getEmail()).set("sms", e.getPhone()).to(new Notify());
        leobNotifyManager.saveOrUpdate(notify);
        return e.getId();
    }

    public boolean update(Person e) throws FailException {
        boolean success = dio.update(e) == 1;
        if (success) {
            if (e.getName() != null || e.getImgsrc()!=null) carinaPersonManager.update(e);
            if (e.getPhone() != null || e.getEmail() != null) {
                accountManager.updateByPerson(Builder.set("person", e).set("phone", e.getPhone()).set("email", e.getEmail()).to(new Account()));
                leobNotifyManager.saveOrUpdate(Builder.set("person", e).set("sms", e.getPhone()).set("email", e.getEmail()).to(new Notify()));
            }
        }
        return success;
    }
}
