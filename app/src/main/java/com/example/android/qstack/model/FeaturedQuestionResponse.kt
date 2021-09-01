package com.example.android.qstack.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

data class FeaturedQuestionResponse(

	@Json(name="quota_max")
	val quotaMax: Int? = null,

	@Json(name="quota_remaining")
	val quotaRemaining: Int? = null,

	@Json(name="has_more")
	val hasMore: Boolean? = null,

	@Json(name="items")
	val items: List<FeaturedQuestion> = emptyList()
)

@Entity
data class FeaturedQuestion(

	@Embedded
	@Json(name="owner")
	val owner: FeaturedQuestionOwner? = null,

	@Json(name="content_license")
	val contentLicense: String? = null,

	@Json(name="link")
	val link: String? = null,

	@Json(name="last_activity_date")
	val lastActivityDate: Int? = null,

	@Json(name="creation_date")
	val creationDate: Long? = null,

	@Json(name="answer_count")
	val answerCount: Int? = null,

	@Json(name="title")
	val title: String? = null,

	@PrimaryKey
	@Json(name="question_id")
	val questionId: Int,

	@Json(name="tags")
	val tags: List<String?>? = null,

	@Json(name="score")
	val score: Int? = null,

	@Json(name="bounty_amount")
	val bountyAmount: Int? = null,

	@Json(name="bounty_closes_date")
	val bountyClosesDate: Int? = null,

	@Json(name="is_answered")
	val isAnswered: Boolean? = null,

	@Json(name="view_count")
	val viewCount: Int? = null,

	@Json(name="last_edit_date")
	val lastEditDate: Int? = null,

	@Json(name="accepted_answer_id")
	val acceptedAnswerId: Int? = null
)

data class FeaturedQuestionOwner(

	@Json(name="profile_image")
	val profileImage: String? = null,

	@Json(name="account_id")
	val accountId: Int? = null,

	@Json(name="user_type")
	val userType: String? = null,

	@Json(name="user_id")
	val userId: Int? = null,

	@Json(name="link")
	val OwnerLink: String? = null,

	@Json(name="reputation")
	val reputation: Int? = null,

	@Json(name="display_name")
	val displayName: String? = null,

	@Json(name="accept_rate")
	val acceptRate: Int? = null
)
