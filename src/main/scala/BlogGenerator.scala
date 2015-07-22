import java.io._
import java.io.{FileNotFoundException,IOException}
import scala.collection.mutable.ListBuffer
import scala.xml._
import scala.io.Source
import scala.util.matching.Regex

import HandleArticle._
import MyRWTool._
import VersionControl._
object BlogGenerator {
  
  def generateIndex(templeteName:String,productName:String,env:MyEnv){
    val templeteContent = loadIndexTemplete(templeteName,env)           //内容作为字符串list被载入
    val indexPostContent = loadIndexContent(env)                        //索引内容被按照6篇文章一组分组，作为一个列表
    val indexPostContentGroup =combinePostPart(indexPostContent)        //分组后的post内容，list[list[elem]]        
    val xmlSidebarContent =combineSidebarPart(indexPostContent)         //sidebar
//    val xmlIndexContent = combinePostPart(indexPostContent)

    val postPattern = "pagination|article-content|title-list".r
    var i = 1
    for(page <- indexPostContentGroup){
      var wContent:ListBuffer[String] = templeteContent.filter(e=>true)                  //存储最终结果
      for(line <- templeteContent){
        var it = postPattern findFirstIn line 
        for( mt <- it){
          mt match{
            case "article-content" => wContent.insert(wContent.indexOf(line)+1,page.mkString)
//            println(wContent.indexOf(line)+1)
                    
            case "title-list" => wContent.insert(wContent.indexOf(line)+1,xmlSidebarContent.mkString)
            case "pagination" => wContent.insert(wContent.indexOf(line)+1,genPagination(i,indexPostContentGroup.length))
            case _ => 
          }
        }
      }
//    writeToFile(new File(env.productPath + productName),wContent.toList)
      if(i == 1)
        writeToFile(new File("index.html"),wContent.toList)
      else
        writeToFile(new File("index"+"-"+(i-1)+".html"),wContent.toList)
      i=i+1
    }
  }

  
  

  def generateBlog(templeteName:String,env:MyEnv){

    val templeteFileContent = readFromFile(new File(env.templetePath + templeteName))                     //从模板文件读取内容
    val postPattern = "post-title|article-content".r                                                      //要匹配的模式
    val postContent = loadArticleContent(env)                   //从博客文件中读取每篇文章内容     
    for(p <- postContent){                                       //依次处理每篇文章
      var wContent:ListBuffer[String] = new ListBuffer()
      for(line <- templeteFileContent){
        wContent += line
        var it = postPattern findFirstIn line 
        for( mt <- it){
          mt match{
            case "post-title" => wContent += p.toTitleXML.toString
            case "article-content" => wContent += p.toContentXML.mkString
            case _ => 
          }
        }
      }
      writeToFile(new File(env.productPath + {p.myFileName}),wContent.toList)
    }
  }
  



  def main(args:Array[String]){
    
    def initEnv():MyEnv = {
      val elem = XML.loadFile("./config/pathfile.xml")
      val env = new MyEnv(elem)
      env
    }
    
    val env = initEnv()
    generatePostnameFile(args(0))
    generateIndex("index.templete","./index.html",env)
    generateBlog("post.templete",env)

  


  }
}





