package chengweiou.universe.milkyway.controller.me;


import chengweiou.universe.blackhole.exception.ParamException;
import chengweiou.universe.blackhole.model.Rest;
import chengweiou.universe.blackhole.param.Valid;
import chengweiou.universe.milkyway.base.converter.Account;
import chengweiou.universe.milkyway.model.entity.person.Person;
import chengweiou.universe.milkyway.service.person.PersonDio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("me")
public class PersonControllerMe {
    @Autowired
    private PersonDio dio;
    @PutMapping("")
    public Rest<Boolean> update(Person e, @RequestHeader("loginAccount") Account loginAccount) throws ParamException {
        Valid.check("loginAccount.person", loginAccount.getPerson()).isNotNull();
        Valid.check("loginAccount.person.id", loginAccount.getPerson().getId()).is().positive();
        Valid.check("person.name", e.getName()).is().lengthIn(1, 30);
        e.setId(loginAccount.getPerson().getId());
        long count = dio.update(e);
        return Rest.ok(count == 1);
    }

    @GetMapping("")
    public Rest<Person> findById(@RequestHeader("loginAccount") Account loginAccount) throws ParamException {
        Valid.check("loginAccount.person", loginAccount.getPerson()).isNotNull();
        Valid.check("loginAccount.person.id", loginAccount.getPerson().getId()).is().positive();
        Person indb = dio.findById(loginAccount.getPerson());
        return Rest.ok(indb);
    }
}
