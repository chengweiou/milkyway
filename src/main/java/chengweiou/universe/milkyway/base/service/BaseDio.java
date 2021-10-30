// package chengweiou.universe.milkyway.base.service;


// import java.lang.reflect.Field;
// import java.lang.reflect.Modifier;
// import java.util.Arrays;
// import java.util.List;
// import java.util.stream.Collectors;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Qualifier;
// import org.springframework.stereotype.Component;

// import chengweiou.universe.blackhole.exception.FailException;
// import chengweiou.universe.milkyway.base.dao.BaseDao;
// import chengweiou.universe.blackhole.model.entity.DtoEntity;
// import chengweiou.universe.blackhole.model.entity.DtoKey;
// import chengweiou.universe.blackhole.model.entity.ServiceEntity;

// @Component
// public abstract class BaseDio<T extends ServiceEntity, Dto extends DtoEntity> {
//     @Autowired
//     @Qualifier("baseDao")
//     private BaseDao dao;

//     public void save(T e) throws FailException {
//         if (hasKey(e)) {
//             long count = dao.countByKey(e.toDto());
//             if (count == 1) throw new FailException("same data exists");
//         }
//         e.fillNotRequire();
//         e.createAt();
//         e.updateAt();
//         DtoEntity dto = e.toDto();
//         long count = dao.save(dto);
//         if (count != 1) throw new FailException();
//         e.setId(dto.getId());
//     }

//     public void delete(T e) throws FailException {
//         long count = dao.delete(e.toDto());
//         if (count != 1) throw new FailException();
//     }

//     public long update(T e) {
//         e.updateAt();
//         return dao.update(e.toDto());
//     }

//     public ServiceEntity findById(T e) {
//         DtoEntity result = dao.findById(e.toDto());
//         if (result == null) return T.NULL;
//         return result.toBean();
//     }

//     public long countByKey(T e) {
//         if (!hasKey(e)) return 0;
//         long count = dao.countByKey(e.toDto());
//         return count;
//     }

//     public ServiceEntity findByKey(T e) {
//         if (!hasKey(e)) return T.NULL;
//         DtoEntity result = dao.findByKey(e.toDto());
//         if (result == null) return T.NULL;
//         return result.toBean();
//     }

//     private boolean hasKey(T e) {
//         DtoEntity dto = e.toDto();
//         List<String> fieldNameList = Arrays.asList(dto.getClass().getDeclaredFields()).stream().filter(field -> !Modifier.isStatic(field.getModifiers()))
//             .filter(field -> field.isAnnotationPresent(DtoKey.class))
//             .map(Field::getName)
//             .collect(Collectors.toList());
//         return !fieldNameList.isEmpty();
//     }
//     // public long count(SearchCondition searchCondition, T sample) {
//     //     return dao.count(searchCondition, sample!=null ? sample.toDto() : null);
//     // }

//     // public List<T> find(SearchCondition searchCondition, T sample) {
//     //     searchCondition.setDefaultSort("updateAt");
//     //     List<Dto> dtoList = dao.find(searchCondition, sample!=null ? sample.toDto() : null);
//     //     List<T> result = dtoList.stream().map(e -> e.toBean()).collect(Collectors.toList());
//     //     return result;
//     // }
// }
