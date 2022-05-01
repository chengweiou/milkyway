package chengweiou.universe.milkyway.controller.all;


import chengweiou.universe.blackhole.exception.FailException;
import chengweiou.universe.blackhole.exception.ParamException;
import chengweiou.universe.blackhole.exception.ProjException;
import chengweiou.universe.blackhole.model.Rest;
import chengweiou.universe.blackhole.param.Valid;
import chengweiou.universe.milkyway.base.converter.Account;
import chengweiou.universe.milkyway.manager.andromeda.AccountManager;
import chengweiou.universe.milkyway.manager.carina.CarinaPersonManager;
import chengweiou.universe.milkyway.model.entity.person.PersonType;
import chengweiou.universe.milkyway.service.person.PersonDio;
import chengweiou.universe.milkyway.service.person.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {
    @Autowired
    private PersonService service;
    @Autowired
    private PersonDio dio;
    @Autowired
    private AccountManager accountManager;
    @Autowired
    private CarinaPersonManager carinaPersonManager;

    @Transactional(rollbackFor = FailException.class)
    @PostMapping("/register")
    public Rest<Long> save(Account account) throws ParamException, FailException, ProjException {
        Valid.check("account.username", account.getUsername()).is().lengthIn(1, 30);
        Valid.check("account.password", account.getPassword()).is().lengthIn(1, 30);
        Valid.check("account.person", account.getPerson()).isNotNull();
        Valid.check("account.person.name", account.getPerson().getName()).is().lengthIn(1, 30);
        Valid.check("account.phone", account.getPhone()).is().lengthIn(100);
        Valid.check("account.email", account.getEmail()).is().lengthIn(100);
        account.getPerson().setPhone(account.getPhone());
        account.getPerson().setEmail(account.getEmail());
        service.save(account.getPerson());
        account.setUsername(account.getUsername());
        account.setPassword(account.getPassword());
        account.setPerson(account.getPerson());
        account.setExtra(PersonType.MEMBER.name());
        account.setPhone(account.getPhone());
        account.setEmail(account.getEmail());
        account.setActive(true);
        accountManager.save(account);
        carinaPersonManager.save(account.getPerson());
//        if (count != 1) throw new FailException();
        return Rest.ok(account.getPerson().getId());
    }
}
