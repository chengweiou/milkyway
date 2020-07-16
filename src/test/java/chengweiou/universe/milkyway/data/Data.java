package chengweiou.universe.milkyway.data;

import chengweiou.universe.milkyway.model.SearchCondition;
import chengweiou.universe.milkyway.model.entity.person.Person;
import chengweiou.universe.milkyway.model.entity.pet.Pet;
import chengweiou.universe.milkyway.service.person.PersonDio;
import chengweiou.universe.milkyway.service.pet.PetDio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Data {
    @Autowired
    private PersonDio personDio;
    public List<Person> personList;

    @Autowired
    private PetDio petDio;
    public List<Pet> petList;

    public void init() {
        personList = personDio.find(new SearchCondition(), null).stream().sorted(Comparator.comparingLong(Person::getId)).collect(Collectors.toList());
        petList = petDio.find(new SearchCondition(), null).stream().sorted(Comparator.comparingLong(Pet::getId)).collect(Collectors.toList());
    }
}
