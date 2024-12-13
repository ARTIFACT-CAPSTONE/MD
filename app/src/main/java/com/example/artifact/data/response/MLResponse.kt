package com.example.artifact.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MLResponse(

	@field:SerializedName("analysisResults")
	val analysisResults: AnalysisResults? = null,

	@field:SerializedName("imageUrl")
	val imageUrl: String? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable

@Parcelize
data class LabelsItem(

	@field:SerializedName("score")
	val score: Double? = null,

	@field:SerializedName("confidence")
	val confidence: Int? = null,

	@field:SerializedName("mid")
	val mid: String? = null,

	@field:SerializedName("description")
	val description: String? = null
) : Parcelable

@Parcelize
data class AnalysisResults(

	@field:SerializedName("imageUrl")
	val imageUrl: String? = null,

	@field:SerializedName("category")
	val category: String? = null,

	@field:SerializedName("labels")
	val labels: List<LabelsItem?>? = null
) : Parcelable
