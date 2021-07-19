package org.finos.morphir.ir.sdk

import org.finos.morphir.ir.{ ModuleName, Type }

object IntModule:
  val moduleName: ModuleName = ModuleName.fromString("Int")

  def intType[A](attributes: A): Type[A] = ???
