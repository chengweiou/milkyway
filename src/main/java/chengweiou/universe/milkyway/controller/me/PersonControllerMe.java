package chengweiou.universe.milkyway.controller.me;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import chengweiou.universe.blackhole.exception.FailException;
import chengweiou.universe.blackhole.exception.ParamException;
import chengweiou.universe.blackhole.exception.ProjException;
import chengweiou.universe.blackhole.model.Rest;
import chengweiou.universe.blackhole.param.Valid;
import chengweiou.universe.milkyway.base.converter.Account;
import chengweiou.universe.milkyway.model.entity.person.Person;
import chengweiou.universe.milkyway.service.person.PersonDio;
import chengweiou.universe.milkyway.service.person.PersonService;

@RestController
@RequestMapping("me")
public class PersonControllerMe {
    @Autowired
    private PersonDio dio;
    @Autowired
    private PersonService service;

    @PutMapping("")
    public Rest<Boolean> update(Person e, @RequestHeader("loginAccount") Account loginAccount) throws ParamException, ProjException, FailException {
        Valid.check("loginAccount.person", loginAccount.getPerson()).isNotNull();
        Valid.check("loginAccount.person.id", loginAccount.getPerson().getId()).is().positive();
        Valid.check("person.name", e.getName()).is().lengthIn(1, 30);
        e.setId(loginAccount.getPerson().getId());
        return Rest.ok(service.update(e));
    }

    @GetMapping("")
    public Rest<Person> findById(@RequestHeader("loginAccount") Account loginAccount) throws ParamException {
        Valid.check("loginAccount.person", loginAccount.getPerson()).isNotNull();
        Valid.check("loginAccount.person.id", loginAccount.getPerson().getId()).is().positive();
        Person indb = dio.findById(loginAccount.getPerson());
        return Rest.ok(indb);
    }
}
