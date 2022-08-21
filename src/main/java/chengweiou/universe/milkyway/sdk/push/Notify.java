package chengweiou.universe.milkyway.sdk.push;

import org.springframework.beans.BeanUtils;

import chengweiou.universe.blackhole.model.Builder;
import chengweiou.universe.blackhole.model.NullObj;
import chengweiou.universe.blackhole.model.entity.DtoEntity;
import chengweiou.universe.blackhole.model.entity.DtoKey;
import chengweiou.universe.blackhole.model.entity.ServiceEntity;
import chengweiou.universe.milkyway.model.entity.person.Person;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Notify extends ServiceEntity {
    private Person person;
    private String email;
    private String sms;
    private String phoneToken;
    private String padToken;
    private Boolean activeEmail;
    private Boolean activeSms;
    private Boolean activePush;
    public void fillNotRequire() {
        email = email != null ? email : "";
        sms = sms != null ? sms : "";
        phoneToken = phoneToken != null ? phoneToken : "";
        padToken = padToken != null ? padToken : "";
        activeEmail = true;
        activeSms = true;
        activePush = true;
    }
    public static final Notify NULL = new Null();
    public static class Null extends Notify implements NullObj {
        @Override
        public Person getPerson() { return Person.NULL; }
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
        @DtoKey
        private Long personId;
        private String email;
        private String sms;
        private String phoneToken;
        private String padToken;
        private Boolean activeEmail;
        private Boolean activeSms;
        private Boolean activePush;

        public Notify toBean() {
            Notify result = new Notify();
            BeanUtils.copyProperties(this, result);
            result.setPerson(Builder.set("id", personId).to(new Person()));
            return result;
        }
    }
}
