import java.io._
import scala.xml._
import java.io.{FileNotFoundException,IOException}
import scala.io.Source

object MyRWTool{
  def writeToFile(f:File,content:List[String]){
//    val p = new PrintWriter(new BufferedWriter(new FileWriter(f)))
    val p = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
    new FileOutputStream(f),"UTF-8")))
    val data = content
    try{
      data.foreach(p.println)
    }catch{
      case e:FileNotFoundException => println("Find write file failed!")
    }finally{
      p.close()
    }
  }
    

  def readFromFile(f:File):List[String] = {
    try{
      val  fileContents = Source.fromFile(f).getLines.toList
      fileContents
    }catch{
      case e:FileNotFoundException => println("Find read file failed!")
             List("wrong")
    }

  }
  
  def loadXML(filename:String):Elem = {
    try{
      val xmlFile = XML.loadFile(filename)
      xmlFile
    }catch{
      case e:FileNotFoundException => println("Find xml load file failed!")
      <wrong>wrong</wrong>
    }

  }
}
    
