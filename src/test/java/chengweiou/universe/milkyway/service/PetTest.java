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
import chengweiou.universe.milkyway.model.entity.pet.Pet;
import chengweiou.universe.milkyway.service.pet.PetDio;
import chengweiou.universe.milkyway.service.pet.PetService;

@SpringBootTest
@ActiveProfiles("test")
public class PetTest {
	@Autowired
	private PetService service;
	@Autowired
	private Data data;

	@Autowired
	private PetDio dio;

	@Test
	public void saveDelete() throws FailException {
		Pet e = Builder.set("person", data.personList.get(0)).set("name", "service-test").set("age", 30).to(new Pet());
		service.save(e);
		Assertions.assertEquals(true, e.getId()> 0);
		service.delete(e);
	}

	@Test
	public void update() {
		Pet e = Builder.set("id", data.petList.get(0).getId()).set("name", "service update").to(new Pet());
		long count = service.update(e);
		Assertions.assertEquals(1, count);
		Pet indb = service.findById(e);
		Assertions.assertEquals("service update", indb.getName());

		dio.update(data.petList.get(0));
	}

	@Test
	public void count() {
		long count = service.count(new SearchCondition(), null);
		Assertions.assertEquals(2, count);
	}

	@Test
	public void find() {
		SearchCondition searchCondition = Builder.set("k", "b").to(new SearchCondition());
		List<Pet> list = service.find(searchCondition, null);
		Assertions.assertEquals(1, list.size());
		Assertions.assertEquals(data.petList.get(1).getId(), list.get(0).getId());
	}

	@BeforeEach
	public void init() {
		data.init();
	}
}
