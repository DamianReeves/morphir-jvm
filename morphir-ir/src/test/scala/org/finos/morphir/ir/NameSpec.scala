package org.finos.morphir.ir
import eu.timepit.refined.auto.*
import zio.test.*
import zio.test.Assertion.equalTo

object NameSpec extends DefaultRunnableSpec:
  def spec = suite("Name Spec")(
    suite("Make a Name from a string and check that:")(
      suite("Name should be creatable from a single word that:")(
        test("Starts with a capital letter") {
          assert(Name.fromString("Marco"))(
            equalTo(Name("marco"))
          )
        },
        test("Is all lowercase") {
          assert(Name.fromString("polo"))(equalTo(Name("polo")))
        }
      ),
      suite("Name should be creatable from compound words that:")(
        test("Are formed from a snake case word") {
          assert(Name.fromString("super_mario_world"))(
            equalTo(Name("super", "mario", "world"))
          )
        },
        test("Contain many kinds of word delimiters") {
          assert(Name.fromString("fooBar_baz 123"))(
            equalTo(Name("foo", "bar", "baz", "123"))
          )
        },
        test("Are formed from a camel-cased string") {
          assert(Name.fromString("valueInUSD"))(
            equalTo(Name("value", "in", "u", "s", "d"))
          )
        },
        test("Are formed from a title-cased string") {

          assert(Name.fromString("ValueInUSD"))(
            equalTo(Name("value", "in", "u", "s", "d"))
          )
        },
        test("Are formed from a title-cased string (2)") {

          assert(Name("ValueInUSD"))(
            equalTo(Name("value", "in", "u", "s", "d"))
          )
        },
        test("Are have a number in the middle") {

          assert(Name.fromString("Nintendo64VideoGameSystem"))(
            equalTo(Name("nintendo", "64", "video", "game", "system"))
          )
        },
        test("Complete and utter nonsense") {
          assert(Name.fromString("_-%"))(equalTo(Name("")))
        }
      )
    )
  )
