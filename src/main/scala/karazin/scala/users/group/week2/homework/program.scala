package karazin.scala.users.group.week2.homework

// Do not forget to import custom implementation
import adt._
import model._
import services._

object program:
  
  /*
   Print all view for all user's posts if they exists
  */
  def printPostsViews(): ErrorOr[List[PostView]] = ???

  /*
   Getting view for all user's posts if they exists
  */
  def getPostsViews(): ErrorOr[List[ErrorOr[PostView]]]= 
    for
      profile   <- getUserProfile()
      posts     <- getPosts(profile.userId)
      postsView <- ErrorOr(posts map { post => getPostView(post) })
    yield postsView
  

  /* 
    Getting view for a particular user's post
    Provide an argument and a result type
  */
  def getPostView(post: Post): ErrorOr[PostView] = 
    for
      comments  <- getComments(post.postId)
      likes     <- getLikes(post.postId)
      shares    <- getShares(post.postId)
    yield  PostView(post, comments, likes, shares)

  /*
   Provide desugared version of the previous two methods
  */
  def getPostsViewDesugared() = ???


  