package chengweiou.universe.milkyway.controller.mg;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import chengweiou.universe.blackhole.exception.FailException;
import chengweiou.universe.blackhole.exception.ParamException;
import chengweiou.universe.blackhole.exception.ProjException;
import chengweiou.universe.blackhole.model.Rest;
import chengweiou.universe.blackhole.param.Valid;
import chengweiou.universe.milkyway.model.entity.pet.Pet;
import chengweiou.universe.milkyway.service.pet.PetDio;

@RestController
@RequestMapping("mg")
public class PetControllerMg {
    @Autowired
    private PetDio dio;

    @PostMapping("/pet")
    public Rest<Long> save(Pet e) throws ParamException, FailException, ProjException {
        Valid.check("pet.person", e.getPerson()).isNotNull();
        Valid.check("pet.person.id", e.getPerson().getId()).is().positive();
        Valid.check("pet.name", e.getName()).is().lengthIn(100);
        Valid.check("pet.type", e.getType()).isNotNull();
        dio.save(e);
        return Rest.ok(e.getId());
    }

    @DeleteMapping("/pet/{id}")
    public Rest<Boolean> delete(Pet e) throws ParamException, FailException {
        Valid.check("pet.id", e.getId()).is().positive();
        dio.delete(e);
        return Rest.ok(true);
    }

    @PutMapping("/pet/{id}")
    public Rest<Boolean> update(Pet e) throws ParamException, FailException {
        Valid.check("pet.id", e.getId()).is().positive();
        Valid.check("pet.person.id | pet.type | name | age", e.getPerson(), e.getType(), e.getName(), e.getAge()).are().notAllNull();
        boolean success = dio.update(e) == 1;
        return Rest.ok(success);
    }

}
