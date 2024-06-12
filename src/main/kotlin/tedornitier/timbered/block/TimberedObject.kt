package tedornitier.timbered.block

interface TimberedObject {
    abstract val name: String
    abstract val defaultModelName: String
    abstract val hasLeftRight: Boolean
    abstract val hasTopBottom: Boolean
}
