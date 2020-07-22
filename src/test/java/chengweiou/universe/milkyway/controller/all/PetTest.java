package chengweiou.universe.milkyway.controller.all;


import chengweiou.universe.blackhole.exception.FailException;
import chengweiou.universe.blackhole.model.BasicRestCode;
import chengweiou.universe.blackhole.model.Builder;
import chengweiou.universe.blackhole.model.Rest;
import chengweiou.universe.milkyway.base.converter.Account;
import chengweiou.universe.milkyway.data.Data;
import chengweiou.universe.milkyway.manager.account.AccountManager;
import chengweiou.universe.milkyway.model.entity.person.Person;
import chengweiou.universe.milkyway.model.entity.pet.Pet;
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

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class PetTest {
	private MockMvc mvc;
	@Autowired
	private WebApplicationContext webApplicationContext;
	// mock 的话，config配置mock类，这里下面写mock值
	@Autowired
	private AccountManager accountManager;
	private Account loginAccount;
	@Autowired
	private Data data;

	@Test
	public void findById() throws Exception {
		String result = mvc.perform(MockMvcRequestBuilders.get("/pet/" + data.petList.get(0).getId())
		).andReturn().getResponse().getContentAsString();
		Rest<Pet> rest = Rest.from(result, Pet.class);
		Assertions.assertEquals(BasicRestCode.OK, rest.getCode());
		Assertions.assertEquals(data.petList.get(0).getId(), rest.getData().getId());
	}

	@Test
	public void count() throws Exception {
		String result = mvc.perform(MockMvcRequestBuilders.get("/pet/count")
				.param("k", "a")
		).andReturn().getResponse().getContentAsString();
		Rest<Long> rest = Rest.from(result, Long.class);
		Assertions.assertEquals(BasicRestCode.OK, rest.getCode());
		Assertions.assertEquals(1, rest.getData());
	}

	@Test
	public void find() throws Exception {
		String result = mvc.perform(MockMvcRequestBuilders.get("/pet")
				.param("k", "a")
		).andReturn().getResponse().getContentAsString();
		Rest<List<Account>> rest = Rest.from(result, List.class);
		Assertions.assertEquals(BasicRestCode.OK, rest.getCode());
		Assertions.assertEquals(1, rest.getData().size());
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
