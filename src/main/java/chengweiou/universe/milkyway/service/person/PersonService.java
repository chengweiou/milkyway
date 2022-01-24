package chengweiou.universe.milkyway.service.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import chengweiou.universe.milkyway.manager.andromeda.AccountManager;

@Service
public class PersonService {
    @Autowired
    private PersonDio dio;
    @Autowired
    private AccountManager accountManager;

}
