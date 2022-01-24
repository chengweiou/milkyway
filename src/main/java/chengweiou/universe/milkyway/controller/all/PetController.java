package chengweiou.universe.milkyway.controller.all;


import chengweiou.universe.blackhole.exception.ParamException;
import chengweiou.universe.blackhole.model.Rest;
import chengweiou.universe.blackhole.param.Valid;
import chengweiou.universe.milkyway.model.SearchCondition;
import chengweiou.universe.milkyway.model.entity.pet.Pet;
import chengweiou.universe.milkyway.service.pet.PetDio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PetController {
    @Autowired
    private PetDio dio;

    @GetMapping("/pet/{id}")
    public Rest<Pet> findById(Pet e) throws ParamException {
        Valid.check("pet.id", e.getId()).is().positive();
        Pet indb = dio.findById(e);
        return Rest.ok(indb);
    }

    @GetMapping("/pet/count")
    public Rest<Long> count(SearchCondition searchCondition, Pet sample) {
        long count = dio.count(searchCondition, sample);
        return Rest.ok(count);
    }

    @GetMapping("/pet")
    public Rest<List<Pet>> find(SearchCondition searchCondition, Pet sample) {
        List<Pet> list = dio.find(searchCondition, sample);
        return Rest.ok(list);
    }
}
