package karazin.scala.users.group.week4.homework

import scala.concurrent.Future
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.ExecutorService
import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContextExecutorService
import services._

/*
  Write test for all service in karazin.scala.users.group.week4.homework.services
  Make sure you control custom execution contexts in tests using `before` and `after` logic

  Review:
    • https://scalameta.org/munit/docs/tests.html
    • https://scalameta.org/munit/docs/assertions.html
    • https://scalameta.org/munit/docs/fixtures.html#ad-hoc-test-local-fixtures
 */
class ServicesSuite extends munit.FunSuite:
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
  test("async test example") {
      getUserProfile(using context()).map(print(_))(using executor = context())
  }

  