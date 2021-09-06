package chengweiou.universe.milkyway.model.entity.person;

import java.io.Serializable;
import java.security.Provider.Service;
import java.time.LocalDateTime;

import org.springframework.beans.BeanUtils;

import chengweiou.universe.blackhole.model.NotNullObj;
import chengweiou.universe.blackhole.model.NullObj;
import chengweiou.universe.milkyway.base.entity.DtoEntity;
import chengweiou.universe.milkyway.base.entity.DtoKey;
import chengweiou.universe.milkyway.base.entity.ServiceEntity;
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

    public void fillNotRequire() {
        type = type!=null ? type : PersonType.GUEST;
        name = name!=null ? name : "";
        phone = phone!=null ? phone : "";
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

        public Person toBean() {
            Person result = new Person();
            BeanUtils.copyProperties(this, result);
            return result;
        }
    }
}
