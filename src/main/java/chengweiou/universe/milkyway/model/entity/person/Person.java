package chengweiou.universe.milkyway.model.entity.person;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.beans.BeanUtils;

import chengweiou.universe.blackhole.model.NotNullObj;
import chengweiou.universe.blackhole.model.NullObj;
import lombok.Data;

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

    public Dto toDto() {
        Dto result = new Dto();
        BeanUtils.copyProperties(this, result);
        return result;
    }
    @Data
    public static class Dto {
        private Long id;
        private PersonType type;
        private String name;
        private String phone;
        private LocalDateTime createAt;
        private LocalDateTime updateAt;

        public Person toBean() {
            Person result = new Person();
            BeanUtils.copyProperties(this, result);
            return result;
        }
    }
}
