package com.tuvistavie.scalog.models


class Atom(val predicate: Predicate, val arguments: List[Symbol]) {
  def canEqual(other: Any): Boolean = other.isInstanceOf[Atom]

  val name: String = predicate.name

  override def equals(other: Any): Boolean = other match {
    case that: Atom =>
      (that canEqual this) &&
        predicate == that.predicate &&
        arguments == that.arguments
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(predicate, arguments)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }

  def a: Atom = this

  def toFact: Fact = Fact(this)

  def apply(symbols: String*) = Atom(predicate, symbols map { Symbol(_) } toList)

  def :-(formula: Formula) = Clause(this, formula)


  override def toString: String = arguments match {
    case Nil => predicate.toString
    case _   => predicate + arguments.mkString("(", ", ", ")")
  }
}

object Atom {
  def apply(predicate: Predicate): Atom = new Atom(predicate, List.empty)
  def apply(predicate: Predicate, args: List[Symbol]): Atom = new Atom(predicate, args)
  def apply(predicateName: String): Atom = Atom(Predicate(predicateName))
}
