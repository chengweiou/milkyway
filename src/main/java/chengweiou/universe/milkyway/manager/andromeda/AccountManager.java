package chengweiou.universe.milkyway.manager.andromeda;

import chengweiou.universe.blackhole.exception.FailException;
import chengweiou.universe.blackhole.model.BasicRestCode;
import chengweiou.universe.blackhole.model.Rest;
import chengweiou.universe.milkyway.base.converter.Account;
import chengweiou.universe.milkyway.sdk.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("!test")
@Service
public class AccountManager {
    @Autowired
    private AccountService accountService;
    public Long save(Account e) throws FailException {
        Rest<Long> rest = accountService.save(e);
        if (rest.getCode() != BasicRestCode.OK) throw new FailException("account service return code of: " + rest.getCode() + ". and message:" + rest.getMessage());
        e.setId(rest.getData());
        return rest.getData();
    }
    public Boolean updatePerson(Account e) throws FailException {
        Rest<Boolean> rest = accountService.updatePerson(e);
        if (rest.getCode() != BasicRestCode.OK) throw new FailException("account service return code of: " + rest.getCode() + ". and message:" + rest.getMessage());
        return rest.getData();
    }
}
