package chengweiou.universe.milkyway.dao.person;


import chengweiou.universe.milkyway.base.dao.BaseDao;
import chengweiou.universe.milkyway.model.SearchCondition;
import chengweiou.universe.milkyway.model.entity.person.Person;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface PersonDao extends BaseDao<Person> {

    @SelectProvider(type = Sql.class, method = "count")
    long count(@Param("searchCondition") SearchCondition searchCondition, @Param("sample") Person sample);

    @SelectProvider(type = Sql.class, method = "find")
    List<Person> find(@Param("searchCondition") SearchCondition searchCondition, @Param("sample") Person sample);

    class Sql {

        public String count(@Param("searchCondition") SearchCondition searchCondition, @Param("sample") Person sample) {
            return baseFind(searchCondition, sample).SELECT("count(*)").toString();
        }
        public String find(@Param("searchCondition") SearchCondition searchCondition, @Param("sample") Person sample) {
            return baseFind(searchCondition, sample).SELECT("*").toString().concat(searchCondition.getOrderBy()).concat(searchCondition.getSqlLimit());
        }
        private SQL baseFind(SearchCondition searchCondition, Person sample) {
            return new SQL() {{
                FROM("person");
                if (searchCondition.getK() != null) WHERE("(name LIKE #{searchCondition.like.k} or phone LIKE #{searchCondition.like.k})");
                if (searchCondition.getMinDate() != null) WHERE("createAt >= #{searchCondition.minDate}::date");
                if (sample != null) {
                    if (sample.getType() != null) WHERE("type = #{sample.type}");
                }
            }};
        }
    }
}
