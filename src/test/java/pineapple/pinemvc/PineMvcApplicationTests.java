package pineapple.pinemvc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;

import java.net.MalformedURLException;
import java.net.URL;

@SpringBootTest
class PineMvcApplicationTests {

	@Value("${pineapple.auth-server-url}")
	private String authServer;

	@Test
	void contextLoads() {
	}

	@Test
	void getPorperties() throws MalformedURLException {
		StringBuilder sb = new StringBuilder();
//		URL mergedURL = new URL(new URL(authServer), "/test/url");
		sb.append(authServer).append("/test/url");
		System.out.println(sb);
//		System.out.println(mergedURL);
//		System.out.println(mergedURL.toString());
	}

}
