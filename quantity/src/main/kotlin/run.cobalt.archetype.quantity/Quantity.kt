package run.cobalt.archetype.quantity

import java.math.BigDecimal

data class Quantity(
  val amount: BigDecimal,
  val metric: Metric
)