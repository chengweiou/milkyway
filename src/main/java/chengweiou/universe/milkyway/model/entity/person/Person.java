package chengweiou.universe.milkyway.model.entity.person;

import chengweiou.universe.blackhole.model.NotNullObj;
import chengweiou.universe.blackhole.model.NullObj;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Person implements NotNullObj, Serializable {
    private Long id;
    private PersonType type;
    private String name;
    private String phone;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    public void fillNotRequire() {
        type = type!=null ? type : PersonType.GUEST;
        name = name!=null ? name : "";
        phone = phone!=null ? phone : "";
    }

    public void createAt() {
        createAt = LocalDateTime.now();
    }
    public void updateAt() {
        updateAt = LocalDateTime.now();
    }

    public static final Person NULL = new Null();
    public static class Null extends Person implements NullObj {
    }

}
