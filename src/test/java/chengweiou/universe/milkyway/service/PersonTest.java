package chengweiou.universe.milkyway.service;


import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import chengweiou.universe.blackhole.exception.FailException;
import chengweiou.universe.blackhole.exception.ProjException;
import chengweiou.universe.blackhole.model.Builder;
import chengweiou.universe.milkyway.data.Data;
import chengweiou.universe.milkyway.manager.andromeda.AccountManager;
import chengweiou.universe.milkyway.manager.carina.CarinaPersonManager;
import chengweiou.universe.milkyway.manager.leob.LeobNotifyManager;
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
	// mock 的话，config配置mock类，这里下面写mock值
	@Autowired
	private AccountManager accountManager;
	@Autowired
	private CarinaPersonManager carinaPersonManager;
	@Autowired
	private LeobNotifyManager leobNotifyManager;
	@Autowired
	private Data data;

	@Autowired
	private PersonDio dio;

	@Test
	public void saveDelete() throws FailException, ProjException {
		Person e = Builder.set("type", PersonType.EMPLOYEE).set("name", "service-test").to(new Person());
		dio.save(e);
		Assertions.assertEquals(true, e.getId()> 0);
		dio.delete(e);
	}

	@Test
	public void saveFailDup() throws FailException {
		Person e1 = Builder.set("type", PersonType.EMPLOYEE).set("name", "service-test").set("phone", data.personList.get(0).getPhone()).to(new Person());
		Assertions.assertThrows(FailException.class, () -> dio.save(e1));
		Person e2 = Builder.set("type", PersonType.EMPLOYEE).set("name", "service-test").set("email", data.personList.get(0).getEmail()).to(new Person());
		Assertions.assertThrows(FailException.class, () -> dio.save(e2));
	}

	@Test
	public void saveDeleteByKey() throws FailException {
		Person e = Builder.set("type", PersonType.EMPLOYEE).set("name", "service-test").to(new Person());
		dio.save(e);
		Assertions.assertEquals(true, e.getId()> 0);
		dio.deleteByKey(Builder.set("name", "service-test").to(new Person()));
		Person indb = dio.findById(e);
		Assertions.assertEquals(null, indb.getId());
	}

	@Test
	public void update() throws FailException {
		Person e = Builder.set("id", data.personList.get(0).getId()).set("name", "service update").to(new Person());
		long count = dio.update(e);
		Assertions.assertEquals(1, count);
		Person indb = dio.findById(e);
		Assertions.assertEquals("service update", indb.getName());

		e = Builder.set("id", data.personList.get(0).getId()).set("phone", data.personList.get(0).getPhone()).to(new Person());
		count = dio.update(e);
		Assertions.assertEquals(1, count);

		dio.update(data.personList.get(0));
	}

	@Test
	public void updateService() throws FailException {
		Person e = Builder.set("id", data.personList.get(0).getId()).set("name", "service update").to(new Person());
		boolean success = service.update(e);
		Assertions.assertEquals(true, success);
		Person indb = dio.findById(e);
		Assertions.assertEquals("service update", indb.getName());

		e = Builder.set("id", data.personList.get(0).getId()).set("phone", data.personList.get(0).getPhone()).to(new Person());
		success = service.update(e);
		Assertions.assertEquals(true, success);

		service.update(data.personList.get(0));
	}

	@Test
	public void updateByKey() throws FailException {
		Person e = Builder.set("imgsrc", "service update by key").set("phone", data.personList.get(0).getPhone()).to(new Person());
		long count = dio.updateByKey(e);
		Assertions.assertEquals(1, count);
		Person indb = dio.findById(data.personList.get(0));
		Assertions.assertEquals("service update by key", indb.getImgsrc());

		dio.update(data.personList.get(0));
	}

	@Test
	public void updateFailDup() throws FailException, ProjException {
		Person e1 = Builder.set("id", data.personList.get(0).getId()).set("phone", data.personList.get(1).getPhone()).to(new Person());
		Assertions.assertThrows(FailException.class, () -> dio.update(e1));
		Person e2 = Builder.set("id", data.personList.get(0).getId()).set("email", data.personList.get(1).getEmail()).to(new Person());
		Assertions.assertThrows(FailException.class, () -> dio.update(e2));
	}

	@Test
	public void countByKey() {
		Person e = Builder.set("name", data.personList.get(0).getName()).to(new Person());
		long count = dio.countByKey(e);
		Assertions.assertEquals(1, count);
	}

	@Test
	public void findByKey() {
		Person e = Builder.set("name", data.personList.get(0).getName()).to(new Person());
		Person indb = dio.findByKey(e);
		Assertions.assertEquals(data.personList.get(0).getId(), indb.getId());
	}

	@Test
	public void count() {
		long count = dio.count(new SearchCondition(), null);
		Assertions.assertEquals(2, count);
	}

	@Test
	public void find() {
		SearchCondition searchCondition = Builder.set("k", "ch").to(new SearchCondition());
		List<Person> list = dio.find(searchCondition, null);
		Assertions.assertEquals(1, list.size());
		Assertions.assertEquals(data.personList.get(1).getId(), list.get(0).getId());
	}

	@Test
	public void register() throws FailException {
		// todo 放到service里面去
		// Person e = Builder.set("type", PersonType.EMPLOYEE).set("name", "service-test").to(new Person());
		// service.register(e);
		// Assertions.assertEquals(true, e.getId()> 0);
		// dio.delete(e);
	}

	@BeforeEach
	public void init() {
		data.init();
	}
	@BeforeEach
	public void mock() throws FailException {
		Mockito.when(accountManager.save(Mockito.any())).thenReturn(1L);
		Mockito.when(accountManager.updateByPerson(Mockito.any())).thenReturn(false);
		Mockito.when(carinaPersonManager.save(Mockito.any())).thenReturn(1L);
		Mockito.when(carinaPersonManager.update(Mockito.any())).thenReturn(true);
		Mockito.when(leobNotifyManager.saveOrUpdate(Mockito.any())).thenReturn(1L);
	}
}
