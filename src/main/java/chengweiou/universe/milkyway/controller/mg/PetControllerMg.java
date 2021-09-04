package chengweiou.universe.milkyway.controller.mg;


import chengweiou.universe.blackhole.exception.FailException;
import chengweiou.universe.blackhole.exception.ParamException;
import chengweiou.universe.blackhole.exception.ProjException;
import chengweiou.universe.blackhole.model.Rest;
import chengweiou.universe.blackhole.param.Valid;
import chengweiou.universe.milkyway.model.entity.pet.Pet;
import chengweiou.universe.milkyway.service.pet.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("mg")
public class PetControllerMg {
    @Autowired
    private PetService service;

    @PostMapping("/pet")
    public Rest<Long> save(Pet e) throws ParamException, FailException, ProjException {
        Valid.check("pet.person", e.getPerson()).isNotNull();
        Valid.check("pet.person.id", e.getPerson().getId()).is().positive();
        Valid.check("pet.name", e.getName()).is().lengthIn(100);
        Valid.check("pet.type", e.getType()).isNotNull();
        service.save(e);
        return Rest.ok(e.getId());
    }

    @DeleteMapping("/pet/{id}")
    public Rest<Boolean> delete(Pet e) throws ParamException, FailException {
        Valid.check("pet.id", e.getId()).is().positive();
        service.delete(e);
        return Rest.ok(true);
    }

    @PutMapping("/pet/{id}")
    public Rest<Boolean> update(Pet e) throws ParamException {
        Valid.check("pet.id", e.getId()).is().positive();
        Valid.check("pet.person.id | pet.type | name | age", e.getPerson(), e.getType(), e.getName(), e.getAge()).are().notAllNull();
        boolean success = service.update(e) == 1;
        return Rest.ok(success);
    }

}
