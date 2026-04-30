import com.google.gson.annotations.SerializedName
import com.rikezero.mtgapi_kotlin_sdk.networking.response.card.ForeignNameResponse
import com.rikezero.mtgapi_kotlin_sdk.networking.response.card.RulingResponse
import kotlinx.serialization.Serializable

@Serializable
data class CardResponse(
    @SerializedName("name") val name: String?,
    @SerializedName("manaCost") val manaCost: String?,
    @SerializedName("cmc") val cmc: Double?,
    @SerializedName("colors") val colors: List<String>?,
    @SerializedName("colorIdentity") val colorIdentity: List<String>?,
    @SerializedName("type") val type: String?,
    @SerializedName("types") val types: List<String>?,
    @SerializedName("supertypes") val superTypes: List<String>?,
    @SerializedName("subtypes") val subTypes: List<String>?,
    @SerializedName("rarity") val rarity: String?,
    @SerializedName("set") val set: String?,
    @SerializedName("setName") val setName: String?,
    @SerializedName("text") val text: String?,
    @SerializedName("flavor") val flavor: String?,
    @SerializedName("artist") val artist: String?,
    @SerializedName("number") val number: String?,
    @SerializedName("power") val power: String?,
    @SerializedName("toughness") val toughness: String?,
    @SerializedName("layout") val layout: String?,
    @SerializedName("multiverseid") val multiverseId: Int?,
    @SerializedName("imageUrl") val imageUrl: String?,
    @SerializedName("rulings") val rulings: List<RulingResponse>?,
    @SerializedName("foreignNames") val foreignNames: List<ForeignNameResponse>?,
    @SerializedName("printings") val printings: List<String>?,
    @SerializedName("originalText") val originalText: String?,
    @SerializedName("originalType") val originalType: String?,
    @SerializedName("id") val id: String?
)
