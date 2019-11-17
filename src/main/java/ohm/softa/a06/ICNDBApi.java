package ohm.softa.a06;

import ohm.softa.a06.model.Joke;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

/**
 * @author Peter Kurfer
 * Created on 11/10/17.
 */

// this is the server and we type here all possible requests
// @Path: parameter is part of the regular URL
// @Query: parameters are added to the url after the ? mark
// Example: http://mydomain.com/tom?id=1    // tom is @Path, while id is @Query
public interface ICNDBApi {

	@GET("/jokes/random")
	Call<Joke> getRandomJoke();

	// methods with parameters
	@GET("/jokes/random")
	Call<Joke> getRandomJoke(@Query("firstName") String firstName, @Query("lastName") String lastName);

	// dynamic path: we type the variable in {}
	@GET("/jokes/random/{count}")
	Call<Joke[]> getRandomJokes(@Path("count") int count);

	@GET("/jokes/random/{count}")
	Call<Joke[]> getRandomJokes(@Path("count") int count, @Query("firstName") String firstName, @Query("lastName") String lastName);

	@GET("/jokes/random/{count}")
	Call<Joke[]> getRandomJokes(@Path("count") int count, @Query("limitTo") String[] categories);

	@GET("/jokes/random/{count}")
	Call<Joke[]> getRandomJokes(@Path("count") int count, @Query("limitTo") String[] categories, @Query("firstName") String firstName, @Query("lastName") String lastName);

	@GET("/jokes/{id}")
	Call<Joke> getJoke(@Path("id") int number);
}
