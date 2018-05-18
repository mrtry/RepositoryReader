package jp.mrtry.repositoryreader.viewModel

import android.arch.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import jp.mrtry.repositoryreader.R
import jp.mrtry.repositoryreader.datastore.GitHubRepositoryProvider
import jp.mrtry.repositoryreader.entity.Repository
import jp.mrtry.repositoryreader.util.MutableListLiveData
import jp.mrtry.repositoryreader.util.SnackbarMessage


/**
 * Created by mrtry on 2018/05/11.
 */
class RepositoryViewModel: ViewModel() {
    private var disposable: Disposable? = null

    val snackbarMessage = SnackbarMessage()
    val repositories = MutableListLiveData<Repository>()

    var currentPage = 1
    var isLoading = false
    var isLoadFinished = false

    fun getRepositories() {
        if (!isLoadFinished) {
            isLoading = true

            disposable = GitHubRepositoryProvider
                    .provideGitHubRepository()
                    .getRepositories(currentPage)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                            {
                                repositories.addAll(it)
                                currentPage++
                                isLoading = false

                                if (it.isEmpty()) isLoadFinished = true
                            },
                            {
                                snackbarMessage.value = R.string.err_failed_to_get_repositories
                            }
                    )
        }
    }

    override fun onCleared() {
        disposable?.dispose()
        super.onCleared()
    }
}