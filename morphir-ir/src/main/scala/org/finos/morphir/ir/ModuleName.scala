package org.finos.morphir.ir

final case class ModuleName(path: List[Name]) {
  def toDottedName: String = ???
}
object ModuleName:
  def fromString(input: String): ModuleName                                                  = ???
  def toString(moduleName: ModuleName, separator: String)(nameToStr: Name => String): String = ???
  def fromList(names: List[Name]): ModuleName                                                = ???
