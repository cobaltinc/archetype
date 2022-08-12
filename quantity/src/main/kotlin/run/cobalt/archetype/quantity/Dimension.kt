package run.cobalt.archetype.quantity

@JvmInline
value class Symbol(private val symbol: Char)

class Dimension {
  class Element private constructor(val symbol: Symbol, val definition: String? = null) {

    class AlreadyDefined : IllegalArgumentException()
    class Undefined : IllegalArgumentException()

    companion object {
      private val lock: Any = Any()
      private val pool: MutableMap<Symbol, Element> = mutableMapOf()

      val LENGTH: Element = create(symbol = Symbol('L'), definition = "length")
      val MASS = create(symbol = Symbol('M'), definition = "mass")
      val TIME = create(symbol = Symbol('T'), definition = "time")
      val ELECTRIC_CURRENT = create(symbol = Symbol('I'), definition = "electric current")
      val TEMPERATURE = create(symbol = Symbol('Θ'), definition = "thermodynamic temperature")
      val AMOUNT_OF_SUBSTANCE = create(symbol = Symbol('N'), definition = "amount of substance")
      val LUMINOUS_INTENSITY = create(symbol = Symbol('J'), definition = "luminous intensity")
      val DIMENSIONLESS = create(symbol = Symbol('1'), definition = "dimensionless")

      fun create(symbol: Symbol, definition: String? = null): Element {
        synchronized(lock) {
          if (pool.containsKey(symbol)) throw AlreadyDefined()
          val element = Element(symbol, definition)
          pool[symbol] = element
          return element
        }
      }

      fun of(symbol: Symbol): Element {
        return pool[symbol] ?: throw Undefined()
      }
    }
  }
}