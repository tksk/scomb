package org.tksk.scomb

object WmiTest extends App {
  import org.tksk.scomb.Wmi._

  for{ map <- getProperties("Win32_Service")
    name <- map.get("Name")
    path <- map.get("PathName")
    pid <- map.get("ProcessId")} {

    println("%-20s %6d %s".format(name.toString, pid.toInt, path.toString))
  }
}