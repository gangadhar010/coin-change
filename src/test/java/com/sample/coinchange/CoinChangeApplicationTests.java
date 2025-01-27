package com.sample.coinchange;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CoinChangeApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void testForNonAcceptableBill() {
		ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + port + "/api/change/6", String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_ACCEPTABLE);
		assertThat(response.getBody()).isEqualTo("Non acceptable bill. Unable to process request. \n" +
				"Acceptable bills are:[1, 2, 5, 10, 20, 50, 100]");
	}

	@Test
	void testForNoSufficientCoins() {
		ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + port + "/api/change/100", String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_ACCEPTABLE);
		assertThat(response.getBody()).isEqualTo("No sufficient coins available in bank.Unable to provide exact change!");
	}

	@Test
	void testForHappyPath() {
		ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + port + "/api/change/20", String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isEqualTo("{\"0.25\":80}");
	}

}
