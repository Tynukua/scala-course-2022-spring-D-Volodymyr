/*

Task:
  • fix the code to make it compilable
  • use at least 2 execution contexts:
    • one for a `for comprehension`
    • at least one for `getComments`, `getLikes`, `getShares`
  • write tests

 */
package karazin.scala.users.group.week4.homework

import java.util.UUID
import scala.concurrent.Future
import scala.util.Success
import scala.util.Failure

import karazin.scala.users.group.week4.homework.model._
import  services._
import scala.concurrent.ExecutionContext
import java.util.concurrent.Executors

object program:
  // Make sure that the result type is exactly `Future[List[PostView]]`
  // not `Future[List[Future[PostView]]]`
  def getPostsViews()(using ctx: ExecutionContext): Future[List[PostView]] =  
    for 
      user <- getUserProfile
      posts <- getPosts(user.userId)
      view  = posts map {post => getPostView(post)}
      views <-  Future.foldLeft(view)(List[PostView]()){(t, h)=>h::t}
    yield
      views
    


  def getPostView(post: Post)(using ctx: ExecutionContext): Future[PostView] = 
    val getCommentsService  = getComments(post.postId)
    val getLikesService     = getLikes(post.postId)
    val getSharesService    = getShares(post.postId)
    for
      comments  ← getCommentsService
      likes     ← getLikesService
      shares    ← getSharesService
    yield 
      PostView(post, comments, likes, shares)
    
    