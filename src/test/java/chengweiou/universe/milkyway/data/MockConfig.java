package chengweiou.universe.milkyway.data;

import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import chengweiou.universe.milkyway.manager.andromeda.AccountManager;
import chengweiou.universe.milkyway.manager.carina.CarinaPersonManager;
import chengweiou.universe.milkyway.manager.leob.LeobNotifyManager;

@Profile("test")
@Configuration
public class MockConfig {
    @ConditionalOnProperty("onMock.andromeda")
    @Bean
    @Primary
    public AccountManager mockAccountManager() {
        return Mockito.mock(AccountManager.class);
    }
    @ConditionalOnProperty("onMock.carina")
    @Bean
    @Primary
    public CarinaPersonManager mockCarinaPersonManager() {
        return Mockito.mock(CarinaPersonManager.class);
    }
    @ConditionalOnProperty("onMock.leob")
    @Bean
    @Primary
    public LeobNotifyManager mockLeobNotifyManager() {
        return Mockito.mock(LeobNotifyManager.class);
    }
}
