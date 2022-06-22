package karazin.scala.users.group.week4.homework

import scala.concurrent.Future
import program._
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.ExecutorService
import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContextExecutorService


/*
  Write test for all programs in karazin.scala.users.group.week4.homework.program
  Make sure you control custom execution contexts in tests using `before` and `after` logic

  Review:
    • https://scalameta.org/munit/docs/tests.html
    • https://scalameta.org/munit/docs/assertions.html
    • https://scalameta.org/munit/docs/fixtures.html#ad-hoc-test-local-fixtures
 */

 

class ProgramSuite extends munit.FunSuite:
  val context = new Fixture[ExecutionContext]("context") {
    var ctx: ExecutionContextExecutorService = null
        
    def apply() =  ctx
    override def beforeAll(): Unit = {
      ctx = 
        ExecutionContext.fromExecutorService(Executors.newSingleThreadExecutor)
    }
    override def afterAll(): Unit = {
      ctx.shutdown()
    }
  }
  override def munitFixtures = List(context)
  test("should get ") {
      val views = getPostsViews()(using context())
  }
