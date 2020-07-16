package chengweiou.universe.milkyway.base.dao;


import chengweiou.universe.blackhole.exception.FailException;
import chengweiou.universe.milkyway.model.SearchCondition;

import java.util.List;

public interface BaseDao<T> {
    void save(T e) throws FailException;
    long delete(T e);
    T findById(T e);
    long count(SearchCondition searchCondition);
    List<T> find(SearchCondition searchCondition);
}
