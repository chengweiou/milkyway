package chengweiou.universe.milkyway.service.pet;


import chengweiou.universe.blackhole.dao.BaseSQL;
import chengweiou.universe.blackhole.exception.FailException;
import chengweiou.universe.milkyway.dao.pet.PetDao;
import chengweiou.universe.milkyway.model.SearchCondition;
import chengweiou.universe.milkyway.model.entity.pet.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PetDio {
    @Autowired
    private PetDao dao;

    public void save(Pet e) throws FailException {
        e.fillNotRequire();
        e.createAt();
        e.updateAt();
        Pet.Dto dto = e.toDto();
        long count = dao.save(dto);
        if (count != 1) throw new FailException();
        e.setId(dto.getId());
    }

    public void delete(Pet e) throws FailException {
        long count = dao.delete(e.toDto());
        if (count != 1) throw new FailException();
    }

    public long update(Pet e) {
        e.updateAt();
        return dao.update(e.toDto());
    }

    public Pet findById(Pet e) {
        Pet.Dto result = dao.findById(e.toDto());
        if (result == null) return Pet.NULL;
        return result.toBean();
    }

    public long count(SearchCondition searchCondition, Pet sample) {
        Pet.Dto dtoSample = sample!=null ? sample.toDto() : Pet.NULL.toDto();
        String where = baseFind(searchCondition, dtoSample);
        return dao.count(searchCondition, dtoSample, where);
    }

    public List<Pet> find(SearchCondition searchCondition, Pet sample) {
        searchCondition.setDefaultSort("updateAt");
        Pet.Dto dtoSample = sample!=null ? sample.toDto() : Pet.NULL.toDto();
        String where = baseFind(searchCondition, dtoSample);
        List<Pet.Dto> dtoList = dao.find(searchCondition, dtoSample, where);
        List<Pet> result = dtoList.stream().map(e -> e.toBean()).collect(Collectors.toList());
        return result;
    }

    private String baseFind(SearchCondition searchCondition, Pet.Dto sample) {
        return new BaseSQL() {{
            if (searchCondition.getK() != null) WHERE("name LIKE #{searchCondition.full.like.k}");
            if (searchCondition.getIdList() != null) WHERE("id in ${searchCondition.foreachIdList}");
            if (sample != null) {
            }
        }}.toString();
    }
}
