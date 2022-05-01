package chengweiou.universe.milkyway.service.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import chengweiou.universe.blackhole.exception.FailException;
import chengweiou.universe.blackhole.exception.ProjException;
import chengweiou.universe.blackhole.model.BasicRestCode;
import chengweiou.universe.milkyway.manager.andromeda.AccountManager;
import chengweiou.universe.milkyway.model.entity.person.Person;

@Service
public class PersonService {
    @Autowired
    private PersonDio dio;
    @Autowired
    private AccountManager accountManager;

    public void save(Person e) throws FailException, ProjException {
        checkDupKey(e);
        dio.save(e);
    }

    public long update(Person e) throws ProjException {
        checkDupKey(e);
        return dio.update(e);
    }

    public long updateByKey(Person e) throws ProjException {
        checkDupKey(e);
        return dio.updateByKey(e);
    }

    private void checkDupKey(Person e) throws ProjException {
        if (e.getPhone() != null && !e.getPhone().isEmpty()) {
            long count = dio.countByPhoneOfOther(e);
            if (count != 0) throw new ProjException("dup key: " + e.getPhone() + " exists", BasicRestCode.EXISTS);
        }
        if (e.getEmail() != null && !e.getEmail().isEmpty()) {
            long count = dio.countByEmailOfOther(e);
            if (count != 0) throw new ProjException("dup key: " + e.getEmail() + " exists", BasicRestCode.EXISTS);
        }
    }

}
