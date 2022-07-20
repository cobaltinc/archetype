package run.cobalt.archetype.quantity

interface Element {
  val symbol: String
}

typealias E = Element

data class Term<out T : Element>(
  val element: T,
  val power: Int = 1
) {
  override fun toString(): String = "${element.symbol}${if (power != 1) power else ""}"
}

data class Metric(
  val name: String,
  val symbol: String,
  val terms: List<Term<Metric.Element>>
) {
  val dimension: Dimension = Dimension.of(this)

  enum class Element(override val symbol: String, val dimensionElement: Dimension.Element) : E {
    METER("m", Dimension.Element.LENGTH),
    KILOGRAM("kg", Dimension.Element.MASS),
    SECOND("s", Dimension.Element.TIME),
    AMPERE("A", Dimension.Element.ELECTRIC_CURRENT),
    KELVIN("K", Dimension.Element.TEMPERATURE),
    MOL("m", Dimension.Element.AMOUNT_OF_SUBSTANCE),
    CANDELDA("cd", Dimension.Element.LUMINOUS_INTENSITY),
    MILE("mile", Dimension.Element.LENGTH)
  }

  companion object {
    fun of(element: Element): Metric = Metric(
      name = element.name,
      symbol = element.symbol,
      terms = listOf(Term(element))
    )
  }
}


class Dimension private constructor(
  val terms: List<Term<Dimension.Element>>
) {
  companion object {
    fun of(metric: Metric): Dimension = Dimension(
      metric.terms.map { mTerm ->
        Term(mTerm.element.dimensionElement, mTerm.power)
      }.sortedBy { it.element.ordinal }
    )
  }

  override fun equals(other: Any?): Boolean {
    if (other !is Dimension) return false
    return this.terms.size == other.terms.size && this.terms.toSet() == other.terms.toSet()
  }

  override fun hashCode(): Int {
    return this.terms.sumOf { it.hashCode() }
  }

  enum class Element(override val symbol: String) : E {
    LENGTH("L"),
    MASS("M"),
    TIME("T"),
    ELECTRIC_CURRENT("I"),
    TEMPERATURE("Θ"),
    AMOUNT_OF_SUBSTANCE("N"),
    LUMINOUS_INTENSITY("J"),
    DIMENSIONLESS("1"),
  }
}

