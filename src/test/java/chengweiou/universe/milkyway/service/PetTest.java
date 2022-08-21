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

@SpringBootTest
@ActiveProfiles("test")
public class PetTest {
	@Autowired
	private PetDio dio;
	@Autowired
	private Data data;

	@Test
	public void saveDelete() throws FailException {
		Pet e = Builder.set("person", data.personList.get(0)).set("name", "dio-test").set("age", 30).to(new Pet());
		dio.save(e);
		Assertions.assertEquals(true, e.getId()> 0);
		Pet indb = dio.findById(e);
		Assertions.assertEquals(e.getId(), indb.getId());
		dio.delete(e);
		indb = dio.findById(e);
		Assertions.assertEquals(null, indb.getId());
	}

	@Test
	public void saveDeleteBySample() throws FailException {
		Pet e = Builder.set("person", data.personList.get(0)).set("name", "dio-test").set("age", 30).to(new Pet());
		dio.save(e);
		Assertions.assertEquals(true, e.getId()> 0);
		dio.deleteBySample(e, e);
		Pet indb = dio.findById(e);
		Assertions.assertEquals(null, indb.getId());
	}
	@Test
	public void saveDeleteByIdList() throws FailException {
		Pet e = Builder.set("person", data.personList.get(0)).set("name", "dio-test").set("age", 30).to(new Pet());
		dio.save(e);
		Assertions.assertEquals(true, e.getId()> 0);
		dio.deleteByIdList(List.of(e.getId().toString()));
		Pet indb = dio.findById(e);
		Assertions.assertEquals(null, indb.getId());
	}

	@Test
	public void update() throws FailException {
		Pet e = Builder.set("id", data.petList.get(0).getId()).set("name", "dio update").to(new Pet());
		long count = dio.update(e);
		Assertions.assertEquals(1, count);
		Pet indb = dio.findById(e);
		Assertions.assertEquals("dio update", indb.getName());

		dio.update(data.petList.get(0));
	}

	@Test
	public void updateBySample() throws FailException {
		Pet e = Builder.set("name", "dio update").to(new Pet());
		Pet sample = Builder.set("name", data.petList.get(0).getName()).to(new Pet());
		long count = dio.updateBySample(e, sample);
		Assertions.assertEquals(1, count);
		Pet indb = dio.findById(data.petList.get(0));
		Assertions.assertEquals("dio update", indb.getName());

		dio.update(data.petList.get(0));
	}

	@Test
	public void updateByIdList() throws FailException {
		Pet e = Builder.set("name", "dio update").to(new Pet());
		List<String> idList = List.of(data.petList.get(0).getId().toString());
		long count = dio.updateByIdList(e, idList);
		Assertions.assertEquals(1, count);
		Pet indb = dio.findById(data.petList.get(0));
		Assertions.assertEquals("dio update", indb.getName());

		dio.update(data.petList.get(0));
	}

	@Test
	public void count() {
		long count = dio.count(new SearchCondition(), null);
		Assertions.assertEquals(2, count);
	}

	@Test
	public void find() {
		SearchCondition searchCondition = Builder.set("k", "b").to(new SearchCondition());
		List<Pet> list = dio.find(searchCondition, null);
		Assertions.assertEquals(1, list.size());
		Assertions.assertEquals(data.petList.get(1).getId(), list.get(0).getId());
	}

	@Test
	public void findYoung() {
		SearchCondition searchCondition = Builder.set("k", "b").to(new SearchCondition());
		Pet sample = Builder.set("age", "1").to(new Pet());
		List<Pet> list = dio.findYounger(searchCondition, sample);
		Assertions.assertEquals(0, list.size());
	}

	@Test
	public void findOld() {
		Pet pet = dio.findOldest();
		Assertions.assertEquals(data.petList.get(1).getId(), pet.getId());
	}

	@BeforeEach
	public void init() {
		data.init();
	}
}
