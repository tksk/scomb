package org.tksk.scomb

import com.jacob.activeX.ActiveXComponent
import com.jacob.com.Dispatch
import com.jacob.com.EnumVariant
import com.jacob.com.Variant

object Wmi {

  case class Hostname(body: String)
  implicit def toHostname(body: String) = Hostname(body)
  implicit def toVariant(a: Any) = new Variant(a)

  def execQuery(query: String)(implicit hostname: Hostname = "localhost") = {
    val connectStr = """winmgmts:\\%s\root\CIMV2""".format(hostname.body)
    val axWMI = new ActiveXComponent(connectStr)
    val vCollection = axWMI.invoke("ExecQuery", query)
    val enumVariant = new EnumVariant(vCollection.toDispatch)
    import collection.JavaConverters._
    enumVariant.asScala map { _.toDispatch }
  }

  def getProperties(target: String, keys: List[String]) = {
    val query = "SELECT %s FROM %s".format(keys.mkString(","), target)
    for(row <- execQuery(query)) yield
      keys.map { key => (key, Dispatch.call(row, key)) } toMap
  }

  private def tryOption[A](f: => A) = try { Option(f) } catch { case e => None }

  def getProperties(target: String) = {
    val query = "SELECT * FROM %s".format(target)
    for(row <- execQuery(query)) yield
      new collection.immutable.DefaultMap[String, Variant] {
        def get(key: String) = tryOption(Dispatch.call(row, key))
        def iterator = Nil.iterator
      }
  }

}
