package chengweiou.universe.milkyway.model.entity.pet;

import java.io.Serializable;
import java.time.LocalDateTime;

import chengweiou.universe.blackhole.model.NotNullObj;
import chengweiou.universe.blackhole.model.NullObj;
import lombok.Data;

@Data
public class Pet implements NotNullObj, Serializable {
    private Long id;
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

}
