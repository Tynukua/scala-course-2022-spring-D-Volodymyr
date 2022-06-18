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
  val contex = new Fixture[ExecutionContext]("context") {
    var ctx: ExecutionContextExecutorService = 
        ExecutionContext.fromExecutorService(Executors.newSingleThreadExecutor)
    def apply() =  ctx
    override def beforeEach(context: BeforeEach): Unit = {
      ctx = 
        ExecutionContext.fromExecutorService(Executors.newSingleThreadExecutor)
    }
    override def afterEach(context: AfterEach): Unit = {
      ctx.shutdown()
    }
  }
  test("successful async test example") {
      val views = getPostsViews()(using contex())
      views.map(v => print(v))(using executor = contex())
  }
