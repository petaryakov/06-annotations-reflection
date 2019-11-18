package ohm.softa.a06.tests;

import ohm.softa.a06.ICNDBWrapperApi;
import ohm.softa.a06.model.ApiResult;
import ohm.softa.a06.model.Joke;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ICNDBWrapperApiTests {
	private static final Logger logger = LogManager.getLogger(ICNDBTests.class);
	private static final int REQUEST_COUNT = 100;

	private ICNDBWrapperApi icndbApi;

	@BeforeEach
	void setup() {

		// warum brauchen wir hier kein GSON Objekt???

		Retrofit retrofit = new Retrofit.Builder()
			.addConverterFactory(GsonConverterFactory.create())
			.baseUrl("http://api.icndb.com")
			.build();

		icndbApi = retrofit.create(ICNDBWrapperApi.class);
	}

	@Test
	void testCollision() throws IOException {
		Set<Integer> jokeNumbers = new HashSet<>();
		int requests = 0;
		boolean collision = false;

		while (requests++ < REQUEST_COUNT) {
			Call<ApiResult<Joke>> jokeCall = icndbApi.getRandomJoke();
			Response<ApiResult<Joke>> jokeResponse = jokeCall.execute();
			if (!jokeResponse.isSuccessful()) continue;
			Joke j = jokeResponse.body().getValue();

			if (jokeNumbers.contains(j.getNumber())) {
				logger.info(String.format("Collision at joke %s", j.getNumber()));
				collision = true;
				break;
			}

			jokeNumbers.add(j.getNumber());
			logger.info(j.toString());
		}

		assertTrue(collision, String.format("Completed %d requests without collision; consider increasing REQUEST_COUNT", requests));
	}

	@Test
	void testGetRandomJokeWithChangedName() throws IOException {
		Joke j = icndbApi.getRandomJoke("Bruce", "Wayne").execute().body().getValue();
		assertNotNull(j);
		assertFalse(j.getContent().contains("Chuck"));
		assertFalse(j.getContent().contains("Norris"));
		logger.info(j.toString());
	}

	@Test
	void testGetMultipleRandomJokes() throws IOException {
		List<Joke> jokes = icndbApi.getRandomJokes(5).execute().body().getValue();
		assertNotNull(jokes);
		assertEquals(5, jokes.size());

		for (Joke j : jokes) {
			logger.info(j.toString());
		}
	}

	@Test
	void testGetMultipleRandomJokesWithChangedName() throws IOException {
		List<Joke> jokes = icndbApi.getRandomJokes(5, "Bruce", "Wayne").execute().body().getValue();
		assertNotNull(jokes);
		assertEquals(5, jokes.size());

		for (Joke j : jokes) {
			assertFalse(j.getContent().contains("Chuck"));
			assertFalse(j.getContent().contains("Norris"));
			logger.info(j.toString());
		}
	}

	@Test
	void testGetMultipleRandomJokesWithCategoriesFilter() throws IOException {
		List<Joke> jokes = icndbApi.getRandomJokes(5, new String[]{"nerdy"}).execute().body().getValue();

		assertNotNull(jokes);
		assertEquals(5, jokes.size());

		jokes.forEach(joke -> {
			assertTrue(joke.getRubrics().stream().anyMatch(r -> r.equals("nerdy")));
			logger.info(joke.toString());
		});
	}

	@Test
	void testGetMultipleRandomJokesWithCategoriesFilterAndChangedName() throws IOException {
		List<Joke> jokes = icndbApi.getRandomJokes(5, new String[]{"nerdy"}, "Bruce", "Wayne").execute().body().getValue();

		assertNotNull(jokes);
		assertEquals(5, jokes.size());

		jokes.forEach(joke -> {
			assertFalse(joke.getContent().contains("Chuck"));
			assertFalse(joke.getContent().contains("Norris"));
			assertTrue(joke.getRubrics().stream().anyMatch(r -> r.equals("nerdy")));
		});
	}

	@Test
	void testGetJokeById() throws IOException {
		List<Integer> randomIds = new ArrayList<>(10);

		for (int i = 0; i < 10; i++) {
			randomIds.add(icndbApi.getRandomJoke().execute().body().getValue().getNumber());
		}

		for (Integer id : randomIds) {
			Joke j = icndbApi.getJoke(id).execute().body().getValue();
			assertNotNull(j);
			assertTrue(randomIds.contains(j.getNumber()));
			logger.info(j.toString());
		}
	}
}
