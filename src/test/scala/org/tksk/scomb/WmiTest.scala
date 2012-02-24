package org.tksk.scomb

object WmiTest extends App {
  import org.tksk.scomb.Wmi._
/*
  for{ map <- getProperties("Win32_Service")
    name <- map.get("Name")
    path <- map.get("PathName")
    pid <- map.get("ProcessId")} {

    println("%-20s %6d %s".format(name, pid.toInt, path))
  }*/

  for{ map <- getProperties("Win32_Share")} {

    println("%-20s %s".format(map("Name"), map("UNCName")))
  }
}

/*
")
  For Each objDisk In colDisks
    Debug.Print "Device ID: " & objDisk.DeviceID
    Debug.Print "Name: " & objDisk.Name
    Debug.Print "Free Space: " & objDisk.FreeSpace
    Debug.Print "Size: " & objDisk.Size
    Debug.Print "---"
*/