package chengweiou.universe.milkyway.base.converter;


import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import chengweiou.universe.blackhole.model.NotNullObj;
import chengweiou.universe.blackhole.model.NullObj;
import chengweiou.universe.milkyway.model.entity.person.Person;
import lombok.Data;

@Data
public class Account implements NotNullObj, Serializable {
    private Long id;
    private Person person;
    private String username;
    @JsonIgnore
    private String password;
    private String extra;
    private String phone;
    private String email;
    public static final Account NULL = new Account.Null();
    public static class Null extends Account implements NullObj {
        @Override
        public Person getPerson() { return Person.NULL; }
    }

}
