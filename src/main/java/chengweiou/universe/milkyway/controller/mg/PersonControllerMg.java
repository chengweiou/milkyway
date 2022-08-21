package chengweiou.universe.milkyway.controller.mg;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import chengweiou.universe.blackhole.exception.FailException;
import chengweiou.universe.blackhole.exception.ParamException;
import chengweiou.universe.blackhole.exception.ProjException;
import chengweiou.universe.blackhole.model.Rest;
import chengweiou.universe.blackhole.param.Valid;
import chengweiou.universe.milkyway.base.converter.Account;
import chengweiou.universe.milkyway.model.SearchCondition;
import chengweiou.universe.milkyway.model.entity.person.Person;
import chengweiou.universe.milkyway.service.person.PersonDio;
import chengweiou.universe.milkyway.service.person.PersonService;

@RestController
@RequestMapping("mg")
public class PersonControllerMg {
    @Autowired
    private PersonService service;
    @Autowired
    private PersonDio dio;

    @Transactional(rollbackFor = FailException.class)
    @PostMapping("/person")
    public Rest<Long> save(Person e, Account account) throws ParamException, FailException, ProjException {
        Valid.check("person.type", e.getType()).isNotNull();
        Valid.check("person.name", e.getName()).is().lengthIn(100);
        service.save(e, account);
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
        return Rest.ok(service.update(e));
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
