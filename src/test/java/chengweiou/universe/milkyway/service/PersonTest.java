package chengweiou.universe.milkyway.service;


import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import chengweiou.universe.blackhole.exception.FailException;
import chengweiou.universe.blackhole.model.Builder;
import chengweiou.universe.milkyway.data.Data;
import chengweiou.universe.milkyway.model.SearchCondition;
import chengweiou.universe.milkyway.model.entity.person.Person;
import chengweiou.universe.milkyway.model.entity.person.PersonType;
import chengweiou.universe.milkyway.service.person.PersonDio;
import chengweiou.universe.milkyway.service.person.PersonService;

@SpringBootTest
@ActiveProfiles("test")
public class PersonTest {
	@Autowired
	private PersonService service;
	@Autowired
	private Data data;

	@Autowired
	private PersonDio dio;

	@Test
	public void saveDelete() throws FailException {
		Person e = Builder.set("type", PersonType.EMPLOYEE).set("name", "service-test").to(new Person());
		service.save(e);
		Assertions.assertEquals(true, e.getId()> 0);
		service.delete(e);
	}

	@Test
	public void update() {
		Person e = Builder.set("id", data.personList.get(0).getId()).set("name", "service update").to(new Person());
		long count = service.update(e);
		Assertions.assertEquals(1, count);
		Person indb = service.findById(e);
		Assertions.assertEquals("service update", indb.getName());

		dio.update(data.personList.get(0));
	}

	@Test
	public void countByKey() {
		Person e = Builder.set("id", data.personList.get(0).getId()).to(new Person());
		long count = service.countByKey(e);
		Assertions.assertEquals(0, count);
	}

	@Test
	public void findByKey() {
		Person e = Builder.set("name", data.personList.get(0).getName()).to(new Person());
		Person indb = service.findByKey(e);
		Assertions.assertEquals(data.personList.get(0).getId(), indb.getId());
	}

	@Test
	public void count() {
		long count = service.count(new SearchCondition(), null);
		Assertions.assertEquals(2, count);
	}

	@Test
	public void find() {
		SearchCondition searchCondition = Builder.set("k", "ch").to(new SearchCondition());
		List<Person> list = service.find(searchCondition, null);
		Assertions.assertEquals(1, list.size());
		Assertions.assertEquals(data.personList.get(1).getId(), list.get(0).getId());
	}

	@BeforeEach
	public void init() {
		data.init();
	}
}
