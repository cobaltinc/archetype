package run.cobalt.archetype.quantity

import io.kotest.core.spec.style.FreeSpec
import org.junit.jupiter.api.assertThrows

class DimensionTest : FreeSpec({
  "Dimension Element" - {

    "Clients can create their own element." - {
      val e1 = Dimension.Element.create(symbol = Symbol('X'), definition = "Custom")

      "If client try creating an element with a symbol which is already defined, throw an AlreadyDefined exception." {
        assertThrows<Dimension.Element.AlreadyDefined> {
          Dimension.Element.create(
            symbol = Symbol('X'),
            definition = "Custom"
          )
        }
      }

      "Clients can get an element with a symbol." {
        val e2 = Dimension.Element.of(Symbol('X'))
        assert(e2.symbol == Symbol('X'))
      }

      "if the symbols of elements are same, this elements must be identical object." {
        val e2 = Dimension.Element.of(symbol = Symbol('X'))
        assert(e1 === e2)
      }
    }

    "A client can access 8 predefined primitive elements, and the primitive elements are singletons." {
      val l = Dimension.Element.of(Symbol('L'))
      assert(l === Dimension.Element.LENGTH)
      val m = Dimension.Element.of(Symbol('M'))
      assert(m === Dimension.Element.MASS)
      val t = Dimension.Element.of(Symbol('T'))
      assert(t === Dimension.Element.TIME)
      val i = Dimension.Element.of(Symbol('I'))
      assert(i === Dimension.Element.ELECTRIC_CURRENT)
      val Θ = Dimension.Element.of(Symbol('Θ'))
      assert(Θ === Dimension.Element.TEMPERATURE)
      val n = Dimension.Element.of(Symbol('N'))
      assert(n === Dimension.Element.AMOUNT_OF_SUBSTANCE)
      val j = Dimension.Element.of(Symbol('J'))
      assert(j === Dimension.Element.LUMINOUS_INTENSITY)
      val z = Dimension.Element.of(Symbol('1'))
      assert(z === Dimension.Element.DIMENSIONLESS)
    }
  }
})
