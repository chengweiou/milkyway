package chengweiou.universe.milkyway.dao.person;


import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import chengweiou.universe.milkyway.base.dao.BaseDao;
import chengweiou.universe.milkyway.model.entity.person.Person;

@Repository
@Mapper
public interface PersonDao extends BaseDao<Person.Dto> {

}
