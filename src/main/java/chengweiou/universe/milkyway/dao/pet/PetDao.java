package chengweiou.universe.milkyway.dao.pet;


import chengweiou.universe.milkyway.base.dao.BaseDao;
import chengweiou.universe.milkyway.model.SearchCondition;
import chengweiou.universe.milkyway.model.entity.pet.Pet;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface PetDao extends BaseDao<Pet.Dto> {

    @SelectProvider(type = Sql.class, method = "count")
    long count(@Param("searchCondition") SearchCondition searchCondition, @Param("sample") Pet.Dto sample);
    @SelectProvider(type = Sql.class, method = "find")
    List<Pet.Dto> find(@Param("searchCondition") SearchCondition searchCondition, @Param("sample") Pet.Dto sample);

    default SQL baseFind(SearchCondition searchCondition, Pet.Dto sample) {
        return new SQL() {{
            FROM("pet");
            if (searchCondition.getK() != null) WHERE("name LIKE #{searchCondition.full.like.k}");
            if (searchCondition.getIdList() != null) WHERE("id in ${searchCondition.foreachIdList}");
            if (sample != null) {
            }
        }};
    }
    class Sql {

        public String count(@Param("searchCondition") SearchCondition searchCondition, @Param("sample") Pet.Dto sample) {
            return baseFind(searchCondition, sample).SELECT("count(*)").toString();
        }
        public String find(@Param("searchCondition") SearchCondition searchCondition, @Param("sample") Pet.Dto sample) {
            return baseFind(searchCondition, sample).SELECT("*").toString().concat(searchCondition.getOrderBy()).concat(searchCondition.getSqlLimit());
        }
        private SQL baseFind(SearchCondition searchCondition, Pet.Dto sample) {
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
