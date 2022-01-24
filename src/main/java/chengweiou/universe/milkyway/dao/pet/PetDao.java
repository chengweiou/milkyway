package chengweiou.universe.milkyway.dao.pet;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;

import chengweiou.universe.milkyway.base.dao.BaseDao;
import chengweiou.universe.milkyway.model.SearchCondition;
import chengweiou.universe.milkyway.model.entity.pet.Pet;
import chengweiou.universe.milkyway.model.entity.pet.Pet.Dto;

@Repository
@Mapper
public interface PetDao extends BaseDao<Pet.Dto> {

    @Select("select * from pet order by age desc limit 1")
    Dto findOldest();

    @SelectProvider(type = Sql.class, method = "findYounger")
    List<Dto> findYounger(@Param("searchCondition") SearchCondition searchCondition, @Param("sample") Dto sample);

    class Sql {
        public String findYounger(@Param("searchCondition") SearchCondition searchCondition, @Param("sample") Dto sample) {
            return youngerFind(searchCondition, sample).toString().concat(searchCondition.getOrderBy()).concat(searchCondition.getSqlLimit());
        }
        private SQL youngerFind(SearchCondition searchCondition, Dto sample) {
            return new SQL() {{
                SELECT("*"); FROM("pet");
                WHERE("age <= #{sample.age}");
            }};
        }
    }
}
