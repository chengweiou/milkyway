package chengweiou.universe.milkyway.model.entity.pet;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.beans.BeanUtils;

import chengweiou.universe.blackhole.model.Builder;
import chengweiou.universe.blackhole.model.NotNullObj;
import chengweiou.universe.blackhole.model.NullObj;
import chengweiou.universe.milkyway.model.entity.person.Person;
import lombok.Data;

@Data
public class Pet implements NotNullObj, Serializable {
    private Long id;
    private Person person;
    private String name;
    private PetType type;
    private Integer age;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    public void fillNotRequire() {
        type = type!=null ? type : PetType.DOG;
        name = name!=null ? name : "";
        age = age!=null ? age : 0;
    }

    public void createAt() {
        createAt = LocalDateTime.now();
    }
    public void updateAt() {
        updateAt = LocalDateTime.now();
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
    public static class Dto {
        private Long id;
        private Long personId;
        private String name;
        private PetType type;
        private Integer age;
        private LocalDateTime createAt;
        private LocalDateTime updateAt;

        public Pet toBean() {
            Pet result = new Pet();
            BeanUtils.copyProperties(this, result);
            result.setPerson(Builder.set("id", personId).to(new Person()));
            return result;
        }
    }
}
