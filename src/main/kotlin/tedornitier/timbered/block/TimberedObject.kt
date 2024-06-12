package tedornitier.timbered.block

interface TimberedObject {
    val name: String
    val defaultModelName: String
    val hasLeftRight: Boolean
    val hasTopBottom: Boolean
    val recipePattern: String

    fun getLocalizedName(locale: String): String
}
