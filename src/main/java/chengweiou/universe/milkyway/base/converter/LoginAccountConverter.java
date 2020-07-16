package chengweiou.universe.milkyway.base.converter;


import com.google.gson.Gson;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LoginAccountConverter implements Converter<String, chengweiou.universe.milkyway.base.converter.Account> {
    @Override
    public chengweiou.universe.milkyway.base.converter.Account convert(String source) {
        return new Gson().fromJson(source, chengweiou.universe.milkyway.base.converter.Account.class);
    }
}
