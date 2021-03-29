package utils;

import api.dto.RegisterUserDto;
import lombok.NoArgsConstructor;

import java.util.concurrent.ThreadLocalRandom;

@NoArgsConstructor
public class TestDataHelper {
    public static RegisterUserDto getTestData() {
        int rndValue = ThreadLocalRandom.current().nextInt(0, 10000);

        String username = "TestUser-" + rndValue;
        String password = "password" + rndValue;
        String email = username + "@api.com";

        return new RegisterUserDto(username, password, email);
    }
}
