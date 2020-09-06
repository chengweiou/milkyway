package chengweiou.universe.milkyway.base.dao;


import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface BaseDao<T> {
    @InsertProvider(type = BaseDaoImpl.class, method = "save")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    long save(T e);

    @DeleteProvider(type = BaseDaoImpl.class, method = "delete")
    long delete(T e);

    @UpdateProvider(type = BaseDaoImpl.class, method = "update")
    long update(T e);

    @SelectProvider(type = BaseDaoImpl.class, method = "findById")
    T findById(T e);

//    @SelectProvider(type = BaseDaoImpl.class, method = "count")
//    long count(@Param("searchCondition") SearchCondition searchCondition, @Param("sample") T sample);
//
//    @SelectProvider(type = BaseDaoImpl.class, method = "find")
//    List<T> find(@Param("searchCondition") SearchCondition searchCondition, @Param("sample") T sample);


}