package chengweiou.universe.milkyway.service.pet;


import chengweiou.universe.blackhole.exception.FailException;
import chengweiou.universe.milkyway.dao.pet.PetDao;
import chengweiou.universe.milkyway.model.SearchCondition;
import chengweiou.universe.milkyway.model.entity.pet.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PetDio {
    @Autowired
    private PetDao dao;

    public void save(Pet e) throws FailException {
        e.fillNotRequire();
        e.createAt();
        e.updateAt();
        long count = dao.save(e);
        if (count != 1) throw new FailException();
    }

    public void delete(Pet e) throws FailException {
        long count = dao.delete(e);
        if (count != 1) throw new FailException();
    }

    public long update(Pet e) {
        e.updateAt();
        return dao.update(e);
    }

    public Pet findById(Pet e) {
        Pet result = dao.findById(e);
        if (result == null) return Pet.NULL;
        return result;
    }

    public long count(SearchCondition searchCondition, Pet sample) {
        return dao.count(searchCondition, sample);
    }

    public List<Pet> find(SearchCondition searchCondition, Pet sample) {
        searchCondition.setDefaultSort("updateAt");
        List<Pet> result = dao.find(searchCondition, sample);
        return result;
    }
}
