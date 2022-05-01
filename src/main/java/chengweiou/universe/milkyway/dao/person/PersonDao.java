package chengweiou.universe.milkyway.dao.person;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;

import chengweiou.universe.milkyway.base.dao.BaseDao;
import chengweiou.universe.milkyway.model.entity.person.Person;
import chengweiou.universe.milkyway.model.entity.person.Person.Dto;

@Repository
@Mapper
public interface PersonDao extends BaseDao<Person.Dto> {
    @Select("select count(*) from person where phone=#{phone}")
    long countByPhone(Dto e);
    @Select("select * from person where phone=#{phone}")
    Dto findByPhone(Dto e);

    @Select("select count(*) from person where email=#{email}")
    long countByEmail(Dto e);
    @Select("select * from person where email=#{email}")
    Dto findByEmail(Dto e);
    @SelectProvider(type = Sql.class, method = "countByPhoneOfOther")
    long countByPhoneOfOther(Dto e);
    @SelectProvider(type = Sql.class, method = "countByEmailOfOther")
    long countByEmailOfOther(Dto e);

    class Sql {

        public String countByUsernameOfOther(final Dto e) {
            return otherFind(e).SELECT("count(*)").WHERE("username=#{username}").toString();
        }
        public String countByEmailOfOther(final Dto e) {
            return otherFind(e).SELECT("count(*)").WHERE("email=#{email}").toString();
        }
        public String countByPhoneOfOther(final Dto e) {
            return otherFind(e).SELECT("count(*)").WHERE("phone=#{phone}").toString();
        }
        private SQL otherFind(Dto e) {
            return new SQL() {{
                FROM("person");
                if (e.getId() != null) WHERE("id!=#{id}");
            }};
        }

    }
}
