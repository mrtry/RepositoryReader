package jp.mrtry.repositoryreader.datastore


/**
 * Created by mrtry on 2018/05/14.
 */
object GitHubRepositoryProvider {
    fun provideGitHubRepository(): GitHubRepository {
        return GitHubRepository(GitHubApiService.create())
    }
}