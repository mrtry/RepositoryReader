package jp.mrtry.repositoryreader.datastore

import com.squareup.moshi.Moshi
import io.reactivex.Observable
import jp.mrtry.repositoryreader.entity.Repository
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor


/**
 * Created by mrtry on 2018/05/14.
 */
interface GitHubApiService {
    @GET("/users/pepabo/repos")
    fun getRepositories(@Query("page") page: Int = 1, @Query("per_page") perPage: Int = 20): Observable<List<Repository>>

    companion object Factory {
        fun create(): GitHubApiService {
            val moshi = Moshi.Builder().build()

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

            return Retrofit.Builder()
                    .baseUrl("https://api.github.com")
                    .client(client)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .build()
                    .create(GitHubApiService::class.java)
        }
    }
}