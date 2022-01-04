package AST

import java.lang
import scala.collection.mutable

class Environment {
  private val variables = new mutable.HashMap[String, Type]()

  def valueOf(varName : String) : Type = {
    variables.get(varName) match {
      case Some(ex) => ex
      case None =>  throw new IllegalStateException("Unknown variable name " + varName)
    }
  }

}
