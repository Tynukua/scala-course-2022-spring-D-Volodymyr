package karazin.scala.users.group.week2.homework

/* 
  Custom implementation of Option (Maybe monad in Haskell)
  Implemented via Scala 3 way for Algebraic Data Types (ADT)
  
  Resources:
  * https://en.wikipedia.org/wiki/Algebraic_data_type
  * https://docs.scala-lang.org/scala3/book/types-adts-gadts.html
*/

object adt:
  enum ErrorOr[+V]:
    
    // Added to make it compilable. Remove it.
    case Value(x: V) extends ErrorOr[V]
    case Error(e: Throwable)extends ErrorOr[V]

    /* 
      Two case must be defined: 
      * a case for a regular value
      * a case for an error (it should contain an actual throwable)
     */
  
    /* 
      The method is used for defining execution pipelines
      Provide a type parameter, an argument and a result type
      
      Make sure that in case of failing the method with exception
      no exception is thrown but the case for an error is returned
    */ 
    def flatMap[Q](f: V ⇒ ErrorOr[Q]): ErrorOr[Q] =
      this match
        case ErrorOr.Error(v) ⇒ ErrorOr.Error(v)
        case ErrorOr.Value(v)  ⇒ try f(v) catch{
          case e: Throwable => ErrorOr.Error(e) 
        }

    /* 
      The method is used for changing the internal object
      Provide a type parameter, an argument and a result type
      
      Make sure that in case of failing the method with exception
      no exception is thrown but the case for an error is returned
     */
    def map[Q](f: V ⇒ Q): ErrorOr[Q] =
      this match
        case ErrorOr.Error(v) ⇒ ErrorOr.Error(v)
        case ErrorOr.Value(v)  ⇒ try ErrorOr.Value(f(v)) catch{
          case ex: Throwable => ErrorOr.Error(ex)
        }
      
    /* 
      The method is used for filtering
      Provide a type parameter, an argument and a result type
      
      Make sure that in case of failing the method with exception
      no exception is thrown but the case for an error is returned
     */
    def withFilter(p: V => Boolean): ErrorOr[V] =
      this match
        case ErrorOr.Error(e)         => ErrorOr.Error(e)
        case ErrorOr.Value(v) if p(v) => ErrorOr.Value(v)
        case _                        => ErrorOr.Error(Exception("Filtered"))
    /* 
      The method is used for getting rid of internal box
      Provide a type parameter, an argument and a result type
    */
    def flatten[U](implicit ev: V <:< ErrorOr[U]): ErrorOr[U] =
      this match
        case ErrorOr.Value(v) => try ev(v) catch{
          case e: Throwable     => ErrorOr.Error(e)
        }
        case ErrorOr.Error(e)    => ErrorOr.Error(e)

    
    /* 
      The method is used for applying side effects without returning any result
      Provide a type parameter, an argument and a result type
    */
    def foreach[U](f: V => U): Unit =
      this match
        case ErrorOr.Error(e) => ()
        case ErrorOr.Value(v) => f(v)

  // Companion object to define constructor
  object ErrorOr:
    /* 
      Provide a type parameter, an argument and a result type
      
      Make sure that in case of failing the method with exception
      no exception is thrown but the case for an error is returned
    */
    def apply[V](v: V): ErrorOr[V] =
      if v == null then ErrorOr.Error(Exception("null is given")) else ErrorOr.Value(v)