//package com.example.artifact.data.data
//
//import android.util.Log
//import androidx.paging.PagingSource
//import androidx.paging.PagingState
//import com.dicoding.picodiploma.loginwithanimation.data.remote.retrofit.ApiService
//import com.example.artifact.data.response.GetAllDataResponseItem
//
//class HistoryPagingSource(private val apiService: ApiService) : PagingSource<Int, GetAllDataResponseItem>() {
//    private companion object {
//        const val INITIAL_PAGE_INDEX = 1
//    }
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GetAllDataResponseItem> {
//        return try {
//            val page = params.key ?: 1
//            val response = apiService.getStories(page = page, size = params.loadSize)
//
//            val sortedResponse = response.sortedByDescending { it.id }
//
//            LoadResult.Page(
//                data = sortedResponse,
//                prevKey = if (page == 1) null else page - 1,
//                nextKey = if (response.isEmpty()) null else page + 1
//            )
//        } catch (e: Exception) {
//            LoadResult.Error(e)
//        }
//    }
//
//    override fun getRefreshKey(state: PagingState<Int, GetAllDataResponseItem>): Int? {
//        return state.anchorPosition?.let { anchorPosition ->
//            val anchorPage = state.closestPageToPosition(anchorPosition)
//            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
//        }
//    }
//
//}