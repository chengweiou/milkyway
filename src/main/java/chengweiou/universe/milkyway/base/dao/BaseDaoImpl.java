package chengweiou.universe.milkyway.base.dao;


import chengweiou.universe.blackhole.util.LogUtil;
import org.apache.ibatis.jdbc.SQL;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BaseDaoImpl<T> {
    private static String table;
    public String save(T e) {
        List<Field> fieldList = Arrays.asList(e.getClass().getDeclaredFields()).stream().filter(field -> !Modifier.isStatic(field.getModifiers()))
                .collect(Collectors.toList());
        return new SQL() {{
            INSERT_INTO(getTable(e));
            VALUES(
                    fieldList.stream().map(Field::getName).collect(Collectors.joining(",")),
                    fieldList.stream().map(f -> "#{"+f.getName()+"}").collect(Collectors.joining(","))
            );
        }}.toString();
    }

    public String delete(T e) {
        return new SQL() {{
            DELETE_FROM(getTable(e)); WHERE("id=#{id}");
        }}.toString();
    }

    public String update(T e) {
        List<Field> fieldList = Arrays.asList(e.getClass().getDeclaredFields()).stream().filter(field -> !Modifier.isStatic(field.getModifiers()))
                .filter(f -> !f.getName().equals("id") && !f.getName().equals("createAt"))
                .filter(f -> {
                    try {
                        f.setAccessible(true);
                        return f.get(e) != null;
                    } catch (IllegalAccessException ex) {
                        LogUtil.e("访问" + e.getClass().getSimpleName() + "中属性："+f.getName(), ex);
                        return false;
                    }
                })
                .collect(Collectors.toList());

        return new SQL() {{
            UPDATE(getTable(e));
            for (Field f : fieldList) {
                SET(f.getName() + "=#{"+f.getName()+"}");
            }
            WHERE("id=#{id}");
        }}.toString();
    }

    public String findById(T e) {
        return new SQL() {{
            SELECT("*"); FROM(getTable(e));
            WHERE("id=#{id}");
        }}.toString();
    }
// todo null 获取不到类型
//    public String count(@Param("searchCondition") SearchCondition searchCondition, @Param("sample") T sample) {
//        return baseFind(searchCondition, sample).SELECT("count(*)").toString();
//    }
//    public String find(@Param("searchCondition") SearchCondition searchCondition, @Param("sample") T sample) {
//        return baseFind(searchCondition, sample).SELECT("*").toString().concat(searchCondition.getOrderBy()).concat(searchCondition.getSqlLimit());
//    }
//    public SQL baseFind(SearchCondition searchCondition, T sample) {
//        return new SQL() {{
//            FROM(getTable(sample));
//        }};
//    }

    private String getTable(T e) {
        if (table != null) return table;
        table = e.getClass().getSimpleName().substring(0, 1).toLowerCase() + e.getClass().getSimpleName().substring(1);
        return table;
    }
}
