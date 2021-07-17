package org.finos.morphir.ir.internal

extension (self: String | Null)
  def toEmptyIfNull: String =
    if self == null then "" else self

  def transform(ifNull: => String, ifNotNull: String => String): String =
    if self == null then ifNull
    else ifNotNull(self)

  def coalesce(whenNull: => String): String =
    if self == null then whenNull else self
