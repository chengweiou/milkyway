package chengweiou.universe.milkyway.controller.all;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import chengweiou.universe.blackhole.exception.FailException;
import chengweiou.universe.blackhole.exception.ParamException;
import chengweiou.universe.blackhole.exception.ProjException;
import chengweiou.universe.blackhole.model.Rest;
import chengweiou.universe.blackhole.param.Valid;
import chengweiou.universe.milkyway.base.converter.Account;
import chengweiou.universe.milkyway.model.entity.person.PersonType;
import chengweiou.universe.milkyway.service.person.PersonDio;
import chengweiou.universe.milkyway.service.person.PersonService;

@RestController
public class AccountController {
    @Autowired
    private PersonService service;
    @Autowired
    private PersonDio dio;

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
        account.getPerson().setType(PersonType.MEMBER);
        service.save(account.getPerson(), account);
        return Rest.ok(account.getPerson().getId());
    }
}
