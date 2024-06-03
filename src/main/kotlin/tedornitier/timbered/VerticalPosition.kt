package tedornitier.timbered

import net.minecraft.util.StringRepresentable

enum class VerticalPosition : StringRepresentable {
    TOP, BOTTOM, MIDDLE;

    override fun getSerializedName(): String {
        return this.name.lowercase()
    }
}