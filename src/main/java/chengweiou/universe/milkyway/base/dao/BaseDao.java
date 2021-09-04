package chengweiou.universe.milkyway.base.dao;


import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
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

    // @SelectProvider(type = BaseDaoImpl.class, method = "count")
    // long count(SearchCondition searchCondition, T sample);

    // @SelectProvider(type = BaseDaoImpl.class, method = "find")
    // List<T> find(SearchCondition searchCondition, T sample);

    // SQL baseFind(SearchCondition searchCondition, T sample);
}
