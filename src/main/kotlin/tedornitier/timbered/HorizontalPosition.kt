package tedornitier.timbered

import net.minecraft.util.StringRepresentable

enum class HorizontalPosition : StringRepresentable {
    LEFT, RIGHT, MIDDLE;

    override fun getSerializedName(): String {
        return this.name.lowercase()
    }
}