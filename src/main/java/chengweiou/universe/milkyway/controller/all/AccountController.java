package chengweiou.universe.milkyway.controller.all;


import chengweiou.universe.blackhole.exception.FailException;
import chengweiou.universe.blackhole.exception.ParamException;
import chengweiou.universe.blackhole.model.Rest;
import chengweiou.universe.blackhole.param.Valid;
import chengweiou.universe.milkyway.base.converter.Account;
import chengweiou.universe.milkyway.manager.andromeda.AccountManager;
import chengweiou.universe.milkyway.model.entity.person.PersonType;
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
    private AccountManager accountManager;

    @Transactional
    @PostMapping("/register")
    public Rest<Long> save(Account account) throws ParamException, FailException {
        Valid.check("account.username", account.getUsername()).is().lengthIn(1, 30);
        Valid.check("account.password", account.getPassword()).is().lengthIn(1, 30);
        Valid.check("account.person", account.getPerson()).isNotNull();
        Valid.check("account.person.name", account.getPerson().getName()).is().lengthIn(1, 30);
        account.getPerson().setType(PersonType.MEMBER);
        account.setExtra(account.getPerson().getType().toString());
        accountManager.save(account);
        service.save(account.getPerson());
        accountManager.updatePerson(account);
//        if (count != 1) throw new FailException();
        return Rest.ok(account.getPerson().getId());
    }
}
