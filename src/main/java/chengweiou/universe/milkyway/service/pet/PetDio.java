package chengweiou.universe.milkyway.service.pet;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import chengweiou.universe.blackhole.dao.BaseDio;
import chengweiou.universe.blackhole.dao.BaseSQL;
import chengweiou.universe.blackhole.model.AbstractSearchCondition;
import chengweiou.universe.milkyway.dao.pet.PetDao;
import chengweiou.universe.milkyway.model.SearchCondition;
import chengweiou.universe.milkyway.model.entity.pet.Pet;

@Component
public class PetDio extends BaseDio<Pet, Pet.Dto> {
    @Autowired
    private PetDao dao;
    @Override
    protected PetDao getDao() { return dao; }
    @Override
    protected Class getTClass() { return Pet.class; };
    @Override
    protected String getDefaultSort() { return "updateAt"; };

    @Override
    protected String baseFind(AbstractSearchCondition searchCondition, Pet.Dto sample) {
        return new BaseSQL() {{
            if (searchCondition.getK() != null) WHERE("name LIKE #{searchCondition.full.like.k}");
            if (searchCondition.getIdList() != null) WHERE("id in ${searchCondition.foreachIdList}");
            if (sample != null) {
            }
        }}.toString();
    }

    public Pet findOldest() {
        Pet.Dto result = dao.findOldest();
        if (result == null) return Pet.NULL;
        return result.toBean();
    }

    public List<Pet> findYounger(SearchCondition searchCondition, Pet sample) {
        searchCondition.setDefaultSort("updateAt");
        Pet.Dto dtoSample = sample!=null ? sample.toDto() : Pet.NULL.toDto();
        List<Pet.Dto> dtoList = dao.findYounger(searchCondition, dtoSample);
        List<Pet> result = dtoList.stream().map(e -> e.toBean()).collect(Collectors.toList());
        return result;
    }


}
