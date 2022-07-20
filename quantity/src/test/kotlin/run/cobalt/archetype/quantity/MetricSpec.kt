package run.cobalt.archetype.quantity

import io.kotest.core.spec.style.StringSpec
import mu.KotlinLogging

//private val logger = KotlinLogging.logger {}

class MetricSpec : StringSpec({
  "원하는 디멘젼을 가진다" {
    val meter: Metric = Metric.of(Metric.Element.METER)
    val newton: Metric = Metric(
      name = "newton",
      symbol = "N",
      terms = listOf(
        Term(Metric.Element.KILOGRAM),
        Term(Metric.Element.METER),
        Term(Metric.Element.SECOND, -2)
      )
    )

    println(meter)
    println(newton)
  }
})
