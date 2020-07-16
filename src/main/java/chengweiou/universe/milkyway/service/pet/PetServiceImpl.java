package chengweiou.universe.milkyway.service.pet;


import chengweiou.universe.blackhole.exception.FailException;
import chengweiou.universe.milkyway.model.SearchCondition;
import chengweiou.universe.milkyway.model.entity.pet.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetServiceImpl implements PetService {
    @Autowired
    private PetDio dio;

    @Override
    public void save(Pet e) throws FailException {
        dio.save(e);
    }

    @Override
    public void delete(Pet e) throws FailException {
        dio.delete(e);
    }

    @Override
    public long update(Pet e) {
        long result = dio.update(e);
        return result;
    }

    @Override
    public Pet findById(Pet e) {
        Pet result = dio.findById(e);
        return result;
    }

    @Override
    public long count(SearchCondition searchCondition, Pet sample) {
        return dio.count(searchCondition, sample);
    }
    @Override
    public List<Pet> find(SearchCondition searchCondition, Pet sample) {
        return dio.find(searchCondition, sample);
    }

}
