package ohm.softa.a06;

import ohm.softa.a06.model.ApiResult;
import ohm.softa.a06.model.Joke;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

public interface ICNDBWrapperApi {
	@GET("/jokes/random")
	Call<ApiResult<Joke>> getRandomJoke();

	@GET("/jokes/random")
	Call<ApiResult<Joke>> getRandomJoke(@Query("firstName") String firstName, @Query("lastName") String lastName);

	@GET("/jokes/random/{count}")
	Call<ApiResult<List<Joke>>> getRandomJokes(@Path("count") int count);

	@GET("/jokes/random/{count}")
	Call<ApiResult<List<Joke>>> getRandomJokes(@Path("count") int count, @Query("firstName") String firstName, @Query("lastName") String lastName);

	@GET("/jokes/random/{count}")
	Call<ApiResult<List<Joke>>> getRandomJokes(@Path("count") int count, @Query("limitTo") String[] categories);

	@GET("/jokes/random/{count}")
	Call<ApiResult<List<Joke>>> getRandomJokes(@Path("count") int count, @Query("limitTo") String[] categories, @Query("firstName") String firstName, @Query("lastName") String lastName);

	@GET("/jokes/{id}")
	Call<ApiResult<Joke>> getJoke(@Path("id") int number);
}
