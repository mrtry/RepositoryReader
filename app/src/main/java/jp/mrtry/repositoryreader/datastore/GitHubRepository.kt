package jp.mrtry.repositoryreader.datastore

import io.reactivex.Observable
import jp.mrtry.repositoryreader.entity.Repository


/**
 * Created by mrtry on 2018/05/14.
 */
class GitHubRepository(private val apiService: GitHubApiService) {

    fun getRepositories(page: Int = 1, perPage: Int = 20): Observable<List<Repository>> {
        return apiService.getRepositories(page, perPage)
    }
}