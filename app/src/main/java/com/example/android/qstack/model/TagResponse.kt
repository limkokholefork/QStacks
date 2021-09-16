package com.example.android.qstack.model

import com.squareup.moshi.Json

data class TagResponse(

	@Json(name="quota_max")
	val quotaMax: Int? = null,

	@Json(name="quota_remaining")
	val quotaRemaining: Int? = null,

	@Json(name="has_more")
	val hasMore: Boolean? = null,

	@Json(name="items")
	val items: List<TagItem>? = null
)

data class CollectivesItem(

	@Json(name="external_links")
	val externalLinks: List<ExternalLinksItem?>? = null,

	@Json(name="link")
	val link: String? = null,

	@Json(name="name")
	val name: String? = null,

	@Json(name="description")
	val description: String? = null,

	@Json(name="slug")
	val slug: String? = null,

	@Json(name="tags")
	val tags: List<String?>? = null
)

data class ExternalLinksItem(

	@Json(name="link")
	val link: String? = null,

	@Json(name="type")
	val type: String? = null
)

data class TagItem(

	@Json(name="is_required")
	val isRequired: Boolean? = null,

	@Json(name="count")
	val count: Int? = null,

	@Json(name="name")
	val name: String = "",

	@Json(name="has_synonyms")
	val hasSynonyms: Boolean? = null,

	@Json(name="is_moderator_only")
	val isModeratorOnly: Boolean? = null,

	@Json(name="collectives")
	val collectives: List<CollectivesItem?>? = null
)
