package chengweiou.universe.milkyway.service.person;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import chengweiou.universe.blackhole.exception.FailException;
import chengweiou.universe.milkyway.manager.andromeda.AccountManager;
import chengweiou.universe.milkyway.model.SearchCondition;
import chengweiou.universe.milkyway.model.entity.person.Person;

@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    private PersonDio dio;
    @Autowired
    private AccountManager accountManager;

    @Override
    public void save(Person e) throws FailException {
        dio.save(e);
    }

    @Override
    public void delete(Person e) throws FailException {
        dio.delete(e);
    }

    @Override
    public long update(Person e) { return dio.update(e); }

    @Override
    public Person findById(Person e) {
        return dio.findById(e);
    }

    @Override
    public long count(SearchCondition searchCondition, Person sample) {
        return dio.count(searchCondition, sample);
    }
    @Override
    public List<Person> find(SearchCondition searchCondition, Person sample) {
        return dio.find(searchCondition, sample);
    }
}