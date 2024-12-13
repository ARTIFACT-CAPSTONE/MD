package com.example.artifact.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class GetAllDataResponse(

	@field:SerializedName("GetAllDataResponse")
	val getAllDataResponse: List<GetAllDataResponseItem> = emptyList()
)

@Parcelize
data class GetAllDataResponseItem(

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("urlPhoto")
	val urlPhoto: String? = null,

	@field:SerializedName("__v")
	val v: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("imageTitle")
	val imageTitle: String? = null
) : Parcelable
