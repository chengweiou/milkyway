package chengweiou.universe.milkyway.model.entity.person;

import org.springframework.beans.BeanUtils;

import chengweiou.universe.blackhole.model.NullObj;
import chengweiou.universe.blackhole.model.entity.DtoEntity;
import chengweiou.universe.blackhole.model.entity.DtoKey;
import chengweiou.universe.blackhole.model.entity.ServiceEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Person extends ServiceEntity {
    private PersonType type;

    private String name;
    private String phone;
    private String email;

    public void fillNotRequire() {
        type = type!=null ? type : PersonType.GUEST;
        name = name!=null ? name : "";
        phone = phone!=null ? phone : "";
        email = email!=null ? email : "";
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
    @ToString(callSuper = true)
    @EqualsAndHashCode(callSuper = true)
    public static class Dto extends DtoEntity {
        private PersonType type;
        @DtoKey
        private String name;
        private String phone;
        private String email;

        public Person toBean() {
            Person result = new Person();
            BeanUtils.copyProperties(this, result);
            return result;
        }
    }
}
