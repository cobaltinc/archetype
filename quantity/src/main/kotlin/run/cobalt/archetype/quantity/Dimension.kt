package run.cobalt.archetype.quantity

open class Dimension private constructor(val unit: Char) {
  companion object {
    val LENGTH = Dimension('L')
    val MASS = Dimension('M')
    val TIME = Dimension('T')
    val ELECTRIC_CURRENT = Dimension('I')
    val TEMPERATURE = Dimension('Θ')
    val AMOUNT_OF_SUBSTANCE = Dimension('N')
    val LUMINOUS_INTENSITY = Dimension('J')
  }
}
