package jp.mrtry.repositoryreader.entity

import com.squareup.moshi.Json

data class Permissions(@Json(name = "pull")
                       val pull: Boolean = false,
                       @Json(name = "admin")
                       val admin: Boolean = false,
                       @Json(name = "push")
                       val push: Boolean = false)