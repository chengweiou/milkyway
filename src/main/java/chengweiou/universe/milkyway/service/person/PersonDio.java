package chengweiou.universe.milkyway.service.person;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import chengweiou.universe.blackhole.dao.BaseDio;
import chengweiou.universe.blackhole.dao.BaseSQL;
import chengweiou.universe.blackhole.model.AbstractSearchCondition;
import chengweiou.universe.milkyway.dao.person.PersonDao;
import chengweiou.universe.milkyway.model.entity.person.Person;


@Component
public class PersonDio extends BaseDio<Person, Person.Dto> {
    @Autowired
    private PersonDao dao;
    @Override
    protected PersonDao getDao() { return dao; }
    @Override
    protected Class getTClass() { return Person.class; };
    @Override
    protected String getDefaultSort() { return "updateAt"; };

    @Override
    protected String baseFind(AbstractSearchCondition searchCondition, Person.Dto sample) {
        return new BaseSQL() {{
            if (searchCondition.getK() != null) WHERE("(name LIKE #{searchCondition.like.k} or phone LIKE #{searchCondition.like.k})");
            if (searchCondition.getMinDate() != null) WHERE("createAt >= #{searchCondition.minDate}::date");
            if (searchCondition.getIdList() != null) WHERE("id in ${searchCondition.foreachIdList}");
            if (sample != null) {
                if (sample.getType() != null) WHERE("type = #{sample.type}");
            }
        }}.toString();
    }

}
