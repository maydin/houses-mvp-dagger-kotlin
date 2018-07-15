package murat.com.houses.data.model

import com.squareup.moshi.Json

data class House(
        @Json(name = "url") val url: String,
        @Json(name = "name") val name: String,
        @Json(name = "region") val region: String,
        @Json(name = "coatOfArms") val coatOfArms: String,
        @Json(name = "words") val words: String,
        @Json(name = "titles") val titles: List<String>?,
        @Json(name = "seats") val seats: List<String>?,
        @Json(name = "currentLord") val currentLord: String?,
        @Json(name = "heir") val heir: String?,
        @Json(name = "overlord") val overlord: String,
        @Json(name = "founded") val founded: String?,
        @Json(name = "founder") val founder: String?,
        @Json(name = "diedOut") val diedOut: String?,
        @Json(name = "ancestralWeapons") val ancestralWeapons: List<String>?,
        @Json(name = "cadetBranches") val cadetBranches: List<String>?,
        @Json(name = "swornMembers") val swornMembers: List<String>?
)