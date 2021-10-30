package chengweiou.universe.milkyway.dao.pet;


import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import chengweiou.universe.milkyway.base.dao.BaseDao;
import chengweiou.universe.milkyway.model.entity.pet.Pet;

@Repository
@Mapper
public interface PetDao extends BaseDao<Pet.Dto> {
}
