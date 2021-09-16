package com.example.android.qstack.model

import com.squareup.moshi.Json

data class UserResponse(

	@Json(name="quota_max")
	val quotaMax: Int? = null,

	@Json(name="quota_remaining")
	val quotaRemaining: Int? = null,

	@Json(name="has_more")
	val hasMore: Boolean? = null,

	@Json(name="items")
	val items: List<UserItem> = emptyList()
)

data class BadgeCounts(

	@Json(name="gold")
	val gold: Int? = null,

	@Json(name="silver")
	val silver: Int? = null,

	@Json(name="bronze")
	val bronze: Int? = null
)

data class UserItem(

	@Json(name="link")
	val link: String? = null,

	@Json(name="last_modified_date")
	val lastModifiedDate: Int? = null,

	@Json(name="last_access_date")
	val lastAccessDate: Int? = null,

	@Json(name="reputation")
	val reputation: Int? = null,

	@Json(name="badge_counts")
	val badgeCounts: BadgeCounts? = null,

	@Json(name="creation_date")
	val creationDate: Int? = null,

	@Json(name="answer_count")
	val answerCount: Int? = null,

	@Json(name="display_name")
	val displayName: String? = null,

	@Json(name="accept_rate")
	val acceptRate: Int? = null,

	@Json(name="about_me")
	val aboutMe: String? = null,

	@Json(name="question_count")
	val questionCount: Int? = null,

	@Json(name="is_employee")
	val isEmployee: Boolean? = null,

	@Json(name="profile_image")
	val profileImage: String? = null,

	@Json(name="account_id")
	val accountId: Int? = null,

	@Json(name="user_type")
	val userType: String? = null,

	@Json(name="website_url")
	val websiteUrl: String? = null,

	@Json(name="user_id")
	val userId: Int? = null,

	@Json(name="location")
	val location: String? = null,

	@Json(name="view_count")
	val viewCount: Int? = null
)
