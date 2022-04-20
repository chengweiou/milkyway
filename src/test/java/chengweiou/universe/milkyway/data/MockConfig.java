package chengweiou.universe.milkyway.data;

import chengweiou.universe.milkyway.manager.andromeda.AccountManager;
import chengweiou.universe.milkyway.manager.carina.CarinaPersonManager;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class MockConfig {
    @Bean("accountManager")
    public AccountManager accountManager() {
        return Mockito.mock(AccountManager.class);
    }
    @Bean("carinaPersonManager")
    public CarinaPersonManager carinaPersonManager() {
        return Mockito.mock(CarinaPersonManager.class);
    }
}
