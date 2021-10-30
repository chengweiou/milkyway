package chengweiou.universe.milkyway.model.entity.pet;

import org.springframework.beans.BeanUtils;

import chengweiou.universe.blackhole.model.Builder;
import chengweiou.universe.blackhole.model.NullObj;
import chengweiou.universe.blackhole.model.entity.DtoEntity;
import chengweiou.universe.blackhole.model.entity.ServiceEntity;
import chengweiou.universe.milkyway.model.entity.person.Person;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Pet extends ServiceEntity {
    private Person person;
    private String name;
    private PetType type;
    private Integer age;

    public void fillNotRequire() {
        type = type!=null ? type : PetType.DOG;
        name = name!=null ? name : "";
        age = age!=null ? age : 0;
    }

    public static final Pet NULL = new Null();
    public static class Null extends Pet implements NullObj {
    }
    public Dto toDto() {
        Dto result = new Dto();
        BeanUtils.copyProperties(this, result);
        if (person != null) result.setPersonId(person.getId());
        return result;
    }
    @Data
    @ToString(callSuper = true)
    @EqualsAndHashCode(callSuper = true)
    public static class Dto extends DtoEntity {
        private Long personId;
        private String name;
        private PetType type;
        private Integer age;

        public Pet toBean() {
            Pet result = new Pet();
            BeanUtils.copyProperties(this, result);
            result.setPerson(Builder.set("id", personId).to(new Person()));
            return result;
        }
    }
}
