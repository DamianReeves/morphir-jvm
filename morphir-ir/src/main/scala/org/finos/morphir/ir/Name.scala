package org.finos.morphir.ir

import org.finos.morphir.ir.internal.*
import scala.annotation.tailrec
final case class Name private (val parts: List[String]):
  def +(other: Name): Name = Name(parts ++ other.parts)

  //def /(other: Name): Path = Path(this, other)

  def humanize: List[String] =
    val words                        = parts
    val join: List[String] => String = abbrev => abbrev.map(_.toUpperCase()).mkString("")

    @tailrec
    def process(
        prefix: List[String],
        abbrev: List[String],
        suffix: List[String]
      ): List[String] =
      suffix match
        case Nil =>
          abbrev match
            case Nil => prefix
            case _   => prefix ++ List(join(abbrev))

        case first :: rest =>
          if first.length() == 1 then process(prefix, abbrev ++ List(first), rest)
          else
            abbrev match
              case Nil => process(prefix ++ List(first), List.empty, rest)
              case _ =>
                process(prefix ++ List(join(abbrev), first), List.empty, rest)

    process(List.empty, List.empty, words)

  def mkString(f: String => String)(sep: String): String =
    parts.map(f).mkString(sep)

  def toLowerCase: String =
    mkString(part => part.toLowerCase.toEmptyIfNull)("")

  def toCamelCase: String =
    parts match {
      case Nil => ""
      case head :: tail =>
        (head :: tail.map(_.capitalize)).mkString("")
    }

  def toKebabCase: String =
    humanize.mkString("-")

  def toSnakeCase: String =
    humanize.mkString("_")

  def toTitleCase: String =
    parts
      .map(_.capitalize)
      .mkString("")

object Name:
  import eu.timepit.refined.api.Refined
  import eu.timepit.refined.types.string.NonEmptyString
  type Part = NonEmptyString

  def apply(firstWord: String, otherWords: String*): Name =
    (firstWord :: otherWords.toList).map(fromString).reduce(_ + _)

  def fromString(str: String): Name =
    val pattern = """[a-zA-Z][a-z]*|[0-9]+""".r
    Name(pattern.findAllIn(str).toList.map(_.toLowerCase.toEmptyIfNull))

  def name(head: Part, rest: Part*): Name =
    val parts: List[String] = head.toString :: rest.map(_.toString).toList
    Name(parts)
