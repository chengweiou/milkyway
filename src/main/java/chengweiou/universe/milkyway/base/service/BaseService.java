// package chengweiou.universe.milkyway.base.service;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import chengweiou.universe.blackhole.exception.FailException;
// import chengweiou.universe.milkyway.base.entity.ServiceEntity;

// @Service
// public abstract class BaseService<T extends ServiceEntity> {
//     @Autowired
//     private BaseDio dio;

//     public void save(T e) throws FailException {
//         dio.save(e);
//     }

//     public void delete(T e) throws FailException {
//         dio.delete(e);
//     }

//     public long update(T e) { return dio.update(e); }

//     public ServiceEntity findById(T e) {
//         return dio.findById(e);
//     }

// }
