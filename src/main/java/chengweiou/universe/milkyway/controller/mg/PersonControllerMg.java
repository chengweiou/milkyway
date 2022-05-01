package chengweiou.universe.milkyway.controller.mg;


import chengweiou.universe.blackhole.exception.FailException;
import chengweiou.universe.blackhole.exception.ParamException;
import chengweiou.universe.blackhole.exception.ProjException;
import chengweiou.universe.blackhole.model.Builder;
import chengweiou.universe.blackhole.model.Rest;
import chengweiou.universe.blackhole.param.Valid;
import chengweiou.universe.milkyway.base.converter.Account;
import chengweiou.universe.milkyway.manager.andromeda.AccountManager;
import chengweiou.universe.milkyway.manager.carina.CarinaPersonManager;
import chengweiou.universe.milkyway.model.SearchCondition;
import chengweiou.universe.milkyway.model.entity.person.Person;
import chengweiou.universe.milkyway.service.person.PersonDio;
import chengweiou.universe.milkyway.service.person.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("mg")
public class PersonControllerMg {
    @Autowired
    private PersonService service;
    @Autowired
    private PersonDio dio;
    @Autowired
    private AccountManager accountManager;
    @Autowired
    private CarinaPersonManager carinaPersonManager;

    @Transactional(rollbackFor = FailException.class)
    @PostMapping("/person")
    public Rest<Long> save(Person e, Account account) throws ParamException, FailException, ProjException {
        Valid.check("person.type", e.getType()).isNotNull();
        Valid.check("person.name", e.getName()).is().lengthIn(100);
        service.save(e);
        account.setUsername(account.getUsername());
        account.setPassword(account.getPassword());
        account.setPerson(e);
        account.setExtra(e.getType().toString());
        account.setPhone(e.getPhone());
        account.setEmail(e.getEmail());
        account.setActive(true);
        accountManager.save(account);
        carinaPersonManager.save(e);
        return Rest.ok(e.getId());
    }

    @DeleteMapping("/person/{id}")
    public Rest<Boolean> delete(Person e) throws ParamException, FailException {
        Valid.check("person.id", e.getId()).is().positive();
        dio.delete(e);
        return Rest.ok(true);
    }

    @PutMapping("/person/{id}")
    public Rest<Boolean> update(Person e) throws ParamException, FailException, ProjException {
        Valid.check("person.id", e.getId()).is().positive();
        Valid.check("person.type | name | phone", e.getType(), e.getName(), e.getPhone()).are().notAllNull();
        boolean success = service.update(e) == 1;
        if (success) {
            if (e.getName() != null || e.getImgsrc()!=null) carinaPersonManager.update(e);
            if (e.getPhone() != null || e.getEmail() != null) accountManager.updateByPerson(
                Builder.set("person", e).set("phone", e.getPhone()).set("email", e.getEmail()).to(new Account()));
        }
        return Rest.ok(success);
    }

    @GetMapping("/person/{id}")
    public Rest<Person> findById(Person e) throws ParamException {
        Valid.check("person.id", e.getId()).is().positive();
        Person indb = dio.findById(e);
        return Rest.ok(indb);
    }

    @GetMapping("/person/count")
    public Rest<Long> count(SearchCondition searchCondition, Person sample) {
        long count = dio.count(searchCondition, sample);
        return Rest.ok(count);
    }

    @GetMapping("/person")
    public Rest<List<Person>> find(SearchCondition searchCondition, Person sample) {
        List<Person> list = dio.find(searchCondition, sample);
        return Rest.ok(list);
    }
}
