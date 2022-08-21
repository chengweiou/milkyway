package chengweiou.universe.milkyway.service.person;


import java.lang.reflect.Modifier;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import chengweiou.universe.blackhole.dao.BaseDio;
import chengweiou.universe.blackhole.dao.BaseSQL;
import chengweiou.universe.blackhole.dao.DioDefaultSort;
import chengweiou.universe.blackhole.dao.DioDefaultSortAz;
import chengweiou.universe.blackhole.exception.FailException;
import chengweiou.universe.blackhole.exception.ProjException;
import chengweiou.universe.blackhole.model.AbstractSearchCondition;
import chengweiou.universe.blackhole.model.BasicRestCode;
import chengweiou.universe.blackhole.model.entity.DtoKey;
import chengweiou.universe.milkyway.dao.person.PersonDao;
import chengweiou.universe.milkyway.model.entity.person.Person;


@Component
public class PersonDio extends BaseDio<Person, Person.Dto> {
    @Autowired
    private PersonDao dao;
    @Override
    protected PersonDao getDao() { return dao; }
    @DioDefaultSort("updateAt")
    private String defaultSort;
    @DioDefaultSortAz(true)
    private boolean defaultSortAz;

    @Override
    protected String baseFind(AbstractSearchCondition searchCondition, Person.Dto sample) {
        return new BaseSQL() {{
            if (searchCondition.getK() != null) WHERE("(name LIKE #{searchCondition.like.k} or phone LIKE #{searchCondition.like.k}) or email LIKE #{searchCondition.like.k}");
            if (searchCondition.getMinDate() != null) WHERE("createAt >= #{searchCondition.minDate}::date");
            if (searchCondition.getIdList() != null) WHERE("id in ${searchCondition.foreachIdList}");
            if (sample != null) {
                if (sample.getType() != null) WHERE("type = #{sample.type}");
            }
        }}.toString();
    }

    public long countByPhone(Person e) {
        return dao.countByPhone(e.toDto());
    }
    public Person findByPhone(Person e) {
        Person.Dto result = dao.findByPhone(e.toDto());
        if (result == null) return Person.NULL;
        return result.toBean();
    }
    public long countByEmail(Person e) {
        return dao.countByEmail(e.toDto());
    }
    public Person findByEmail(Person e) {
        Person.Dto result = dao.findByEmail(e.toDto());
        if (result == null) return Person.NULL;
        return result.toBean();
    }
    public long countByPhoneOfOther(Person e) {
        return dao.countByPhoneOfOther(e.toDto());
    }
    public long countByEmailOfOther(Person e) {
        return dao.countByEmailOfOther(e.toDto());
    }
}
