package chengweiou.universe.milkyway.controller.me;


import chengweiou.universe.blackhole.exception.FailException;
import chengweiou.universe.blackhole.model.BasicRestCode;
import chengweiou.universe.blackhole.model.Builder;
import chengweiou.universe.blackhole.model.Rest;
import chengweiou.universe.milkyway.base.converter.Account;
import chengweiou.universe.milkyway.data.Data;
import chengweiou.universe.milkyway.manager.account.AccountManager;
import chengweiou.universe.milkyway.model.entity.person.Person;
import chengweiou.universe.milkyway.service.person.PersonDio;
import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@ActiveProfiles("test")
public class PersonTest {
	private MockMvc mvc;
	@Autowired
	private WebApplicationContext webApplicationContext;
	// mock 的话，config配置mock类，这里下面写mock值
	@Autowired
	private AccountManager accountManager;
	private Account loginAccount;
	@Autowired
	private Data data;
	@Autowired
	private PersonDio dio;

	@Test
	public void update() throws Exception {
		String old = data.personList.get(0).getName();

		String result = mvc.perform(MockMvcRequestBuilders.put("/me")
				.header("loginAccount", new Gson().toJson(loginAccount))
				.param("name", "controller-test")
		).andReturn().getResponse().getContentAsString();
		Rest<Boolean> rest = Rest.from(result, Boolean.class);
		Assertions.assertEquals(BasicRestCode.OK, rest.getCode());
		Assertions.assertEquals(true, rest.getData());

		dio.update(Builder.set("id", data.personList.get(0).getId()).set("name", old).to(new Person()));
	}

	@Test
	public void updateFail() throws Exception {
		String result = mvc.perform(MockMvcRequestBuilders.put("/me")
				.header("loginAccount", new Gson().toJson(loginAccount))
		).andReturn().getResponse().getContentAsString();
		Rest<Boolean> rest = Rest.from(result);
		Assertions.assertEquals(BasicRestCode.PARAM, rest.getCode());
	}

	@Test
	public void findMe() throws Exception {
		String result = mvc.perform(MockMvcRequestBuilders.get("/me")
				.header("loginAccount", new Gson().toJson(loginAccount))
		).andReturn().getResponse().getContentAsString();
		Rest<Person> rest = Rest.from(result, Person.class);
		Assertions.assertEquals(BasicRestCode.OK, rest.getCode());
		Assertions.assertEquals("ou", rest.getData().getName());
	}

	@BeforeEach
	public void before() {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		loginAccount = Builder.set("person", Builder.set("id", 1L).to(new Person()))
				.set("extra", "SUPER")
				.to(new Account());
	}
	@BeforeEach
	public void init() {
		data.init();
	}
	@BeforeEach
	public void mock() throws FailException {
		Mockito.when(accountManager.save(Mockito.any())).thenReturn(1L);
	}
}
