package chengweiou.universe.milkyway.controller.mg;


import chengweiou.universe.blackhole.exception.FailException;
import chengweiou.universe.blackhole.model.BasicRestCode;
import chengweiou.universe.blackhole.model.Builder;
import chengweiou.universe.blackhole.model.Rest;
import chengweiou.universe.blackhole.util.GsonUtil;
import chengweiou.universe.milkyway.base.converter.Account;
import chengweiou.universe.milkyway.data.Data;
import chengweiou.universe.milkyway.manager.andromeda.AccountManager;
import chengweiou.universe.milkyway.model.entity.person.Person;
import chengweiou.universe.milkyway.service.pet.PetDio;
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

	@Autowired
	private PetDio dio;

	@Test
	public void saveDelete() throws Exception {
		String result = mvc.perform(MockMvcRequestBuilders.post("/mg/pet")
				.header("loginAccount", GsonUtil.create().toJson(loginAccount))
				.param("type", "DOG")
				.param("name", "controller save")
			).andReturn().getResponse().getContentAsString();
		Rest<Long> saveRest = Rest.from(result, Long.class);
		Assertions.assertEquals(BasicRestCode.OK, saveRest.getCode());

		result = mvc.perform(MockMvcRequestBuilders.delete("/mg/pet/" + saveRest.getData())
				.header("loginAccount", GsonUtil.create().toJson(loginAccount))
		).andReturn().getResponse().getContentAsString();
		Rest<Boolean> delRest = Rest.from(result);
		Assertions.assertEquals(BasicRestCode.OK, delRest.getCode());
	}

	@Test
	public void saveDeleteFail() throws Exception {
		String result = mvc.perform(MockMvcRequestBuilders.post("/mg/pet")
				.header("loginAccount", GsonUtil.create().toJson(loginAccount))
				.param("name", "controller save fail")
		).andReturn().getResponse().getContentAsString();
		Rest<Long> saveRest = Rest.from(result);
		Assertions.assertEquals(BasicRestCode.PARAM, saveRest.getCode());
	}

	@Test
	public void update() throws Exception {
		String result = mvc.perform(MockMvcRequestBuilders.put("/mg/pet/" + data.petList.get(0).getId())
				.header("loginAccount", GsonUtil.create().toJson(loginAccount))
				.param("name", "controller update")
		).andReturn().getResponse().getContentAsString();
		Rest<Boolean> rest = Rest.from(result);
		Assertions.assertEquals(BasicRestCode.OK, rest.getCode());
		Assertions.assertEquals(true, rest.getData());

		dio.update(data.petList.get(0));
	}

	@Test
	public void updateFail() throws Exception {
		String result = mvc.perform(MockMvcRequestBuilders.put("/mg/pet/" + data.petList.get(0).getId())
				.header("loginAccount", GsonUtil.create().toJson(loginAccount))
		).andReturn().getResponse().getContentAsString();
		Rest<Boolean> rest = Rest.from(result);
		Assertions.assertEquals(BasicRestCode.PARAM, rest.getCode());
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
