package chengweiou.universe.milkyway.dao.person;


import chengweiou.universe.milkyway.model.SearchCondition;
import chengweiou.universe.milkyway.model.entity.person.Person;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface PersonDao {

    @Insert("insert into person(name, type, phone, createAt, updateAt) values" +
            "(#{name}, #{type}, #{phone}, #{createAt}, #{updateAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    long save(Person e);

    @Delete("delete from person where id=#{id}")
    long delete(Person e);

    @UpdateProvider(type = Sql.class, method = "update")
    long update(Person e);

    @Select("select * from person where id=#{id}")
    Person findById(Person e);

    @SelectProvider(type = Sql.class, method = "count")
    long count(@Param("searchCondition") SearchCondition searchCondition, @Param("sample") Person sample);

    @SelectProvider(type = Sql.class, method = "find")
    List<Person> find(@Param("searchCondition") SearchCondition searchCondition, @Param("sample") Person sample);

    class Sql {

        public String update(final Person e) {
            return new SQL() {{
                UPDATE("person");
                if (e.getName() != null) SET("name = #{name}");
                if (e.getType() != null) SET("type = #{type}");
                if (e.getPhone() != null) SET("phone = #{phone}");
                SET("updateAt = #{updateAt}");
                WHERE("id=#{id}");
            }}.toString();
        }
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
