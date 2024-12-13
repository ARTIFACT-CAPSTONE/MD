package com.example.artifact.data.response

import com.google.gson.annotations.SerializedName

data class UploadResponse(

	@field:SerializedName("photo")
	val photo: Photo? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Photo(

	@field:SerializedName("urlPhoto")
	val urlPhoto: String? = null,

	@field:SerializedName("__v")
	val v: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("imageTitle")
	val imageTitle: String? = null
)
