package chengweiou.universe.milkyway.dao.pet;


import chengweiou.universe.milkyway.model.SearchCondition;
import chengweiou.universe.milkyway.model.entity.pet.Pet;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface PetDao {

    @Insert("insert into pet(type, name, age, createAt, updateAt) values" +
            "(#{type}, #{name}, #{age}, #{createAt}, #{updateAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    long save(Pet e);

    @Delete("delete from pet where id=#{id}")
    long delete(Pet e);

    @UpdateProvider(type = PetDao.Sql.class, method = "update")
    long update(Pet e);

    @Select("select * from pet where id=#{id}")
    Pet findById(Pet e);

    @SelectProvider(type = Sql.class, method = "count")
    long count(@Param("searchCondition") SearchCondition searchCondition, @Param("sample") Pet sample);
    @SelectProvider(type = Sql.class, method = "find")
    List<Pet> find(@Param("searchCondition") SearchCondition searchCondition, @Param("sample") Pet sample);

    class Sql {

        public String update(final Pet e) {
            return new SQL() {{
                UPDATE("pet");
                if (e.getType() != null) SET("type = #{type}");
                if (e.getName() != null) SET("name = #{name}");
                if (e.getAge() != null) SET("age = #{age}");
                SET("updateAt = #{updateAt}");
                WHERE("id=#{id}");
            }}.toString();
        }

        public String count(@Param("searchCondition") SearchCondition searchCondition, @Param("sample") Pet sample) {
            return baseFind(searchCondition, sample).SELECT("count(*)").toString();
        }
        public String find(@Param("searchCondition") SearchCondition searchCondition, @Param("sample") Pet sample) {
            return baseFind(searchCondition, sample).SELECT("*").toString().concat(searchCondition.getOrderBy()).concat(searchCondition.getSqlLimit());
        }
        private SQL baseFind(SearchCondition searchCondition, Pet sample) {
            return new SQL() {{
                FROM("pet");
                if (searchCondition.getK() != null) WHERE("name LIKE #{searchCondition.full.like.k}");
                if (searchCondition.getIdList() != null) WHERE("id in ${searchCondition.foreachIdList}");
                if (sample != null) {
                }
            }};
        }
    }
}
