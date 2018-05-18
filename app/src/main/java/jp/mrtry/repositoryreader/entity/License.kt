package jp.mrtry.repositoryreader.entity

import com.squareup.moshi.Json

data class License(@Json(name = "html_url")
                   val htmlUrl: String = "",
                   @Json(name = "name")
                   val name: String = "",
                   @Json(name = "spdx_id")
                   val spdxId: String = "",
                   @Json(name = "key")
                   val key: String = "",
                   @Json(name = "url")
                   val url: String = "")