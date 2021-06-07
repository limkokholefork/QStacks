package com.example.android.qstack.model

import com.squareup.moshi.Json

data class QuestionResponse(

	@Json(name="quota_max")
	val quotaMax: Int? = null,

	@Json(name="quota_remaining")
	val quotaRemaining: Int? = null,

	@Json(name="has_more")
	val hasMore: Boolean? = null,

	@Json(name="items")
	val items: List<ItemsItem>? = null
)

data class ItemsItem(

	@Json(name="owner")
	val owner: Owner,

	@Json(name="closed_date")
	val closedDate: Int? = null,

	@Json(name="link")
	val link: String,

	@Json(name="last_activity_date")
	val lastActivityDate: Long? = null,

	@Json(name="creation_date")
	val creationDate: Long,

	@Json(name="answer_count")
	val answerCount: Int? = null,

	@Json(name="title")
	val title: String,

	@Json(name="question_id")
	val questionId: Int,

	@Json(name="tags")
	val tags: List<String>? = null,

	@Json(name="score")
	val score: Int? = null,

	@Json(name="closed_reason")
	val closedReason: String? = null,

	@Json(name="is_answered")
	val isAnswered: Boolean? = null,

	@Json(name="view_count")
	val viewCount: Int? = null,

	@Json(name="last_edit_date")
	val lastEditDate: Int? = null,

	@Json(name="content_license")
	val contentLicense: String? = null,

	@Json(name="accepted_answer_id")
	val acceptedAnswerId: Int? = null
)

data class Owner(

	@Json(name="profile_image")
	val profileImage: String? = null,

	@Json(name="user_type")
	val userType: String? = null,

	@Json(name="user_id")
	val userId: Int? = null,

	@Json(name="link")
	val link: String? = null,

	@Json(name="reputation")
	val reputation: Int? = null,

	@Json(name="display_name")
	val displayName: String,

	@Json(name="accept_rate")
	val acceptRate: Int? = null
)
