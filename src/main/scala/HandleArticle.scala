import scala.xml._
import scala.collection.mutable.ListBuffer
import MyRWTool._
import java.io._
object HandleArticle{

    def loadIndexContent(env:MyEnv):ListBuffer[ArticleInfo] = {                  //载入索引文件内容
      val postList:ListBuffer[ArticleInfo] = new ListBuffer()
      val xmlNameLoader = loadXML("./config/postname.xml")
      for(a <- xmlNameLoader\\"name"){                          //依次载入blog内容
        var newLoader = loadXML(env.sourcePath + a.text)
        var i = 0
        val plist:ListBuffer[String] = new  ListBuffer()
        var title = newLoader\\"title"
        for{
          a <- newLoader\\"p" 
          if(i < 2)
        }{
          i=i+1
          plist += a.child.mkString
 
        }
        var newPost = new ArticleInfo(a.text,title.text,plist)
        postList += newPost
      }
      postList
    }
    
    def loadArticleContent(env:MyEnv):ListBuffer[ArticleInfo] = {                  //载入要生成新博客文件内容
      val postContent:ListBuffer[ArticleInfo] = new ListBuffer()
      val xmlNameLoader = XML.loadFile("./config/postname.xml")
      for(a <- xmlNameLoader\\"new-post" if(a.text != "")){                          //依次载入blog内容
        var newLoader = XML.loadFile(env.sourcePath + a.text)
        val plist:ListBuffer[String] = new  ListBuffer()
        var title = newLoader\\"title"
        for{
          a <- newLoader\\"p" 
        }{
          plist += a.child.mkString
        }
        var newPost = new ArticleInfo(a.text,title.text,plist)
        postContent += newPost
      }
      postContent
    }



   def loadIndexTemplete(templeteName:String,env:MyEnv):ListBuffer[String]={
    val templeteContent = readFromFile(new File(env.templetePath + templeteName))  //读取index 索引文件模板
    var wContent:ListBuffer[String] = new ListBuffer()
    for(line <- templeteContent){
        wContent += line
    }
    wContent
  }

  
        
  def genPagination(num:Int,all:Int):String = {                                   //生成下角导航
    var t = <p></p>
    if(all == 1)
      t = <span></span>
    else if(num == 1)
      t = <span><a href = {"index-"+num+".html"}>Next page</a></span>
    

    else if(num == 2 && num != all)
      t = <span>
          <a href = {"index.html"}>Previous page</a>
          <a href = {"index-"+num+".html"}>Next page</a>
          </span>
    else if(num == 2 && num == all)
      t = <span>
          <a href = {"index.html"}>Previous page</a>
          </span>
    else if(num < all)
      t = <span>
          <a href = {"index-"+(num-2)+".html"}>Previous page</a>
          <a href = {"index-"+num+".html"}>Next page</a>
          </span>
    
    else if(num == all)
       t = <span>
          <a href = {"index-"+(num-2)+".html"}>Previous page</a>
          </span>

    t.toString
  }
        
  def combineSidebarPart(postList:ListBuffer[ArticleInfo]):ListBuffer[Elem] = {      //合并侧边栏
      val xmlSidebarList:ListBuffer[Elem] = new ListBuffer()
      for(c <- postList)
        xmlSidebarList += c.toSidebarXML
      xmlSidebarList
    }


    def combinePostPart(postList:ListBuffer[ArticleInfo]):ListBuffer[ListBuffer[Elem]] = {             //合并索引博文部分，将6篇为一组放到同一个页面上
      val xmlPostList:ListBuffer[ListBuffer[Elem]] = new ListBuffer()
      var partPostList:ListBuffer[Elem] = new ListBuffer()
      var i = 0
      for(c <- postList){
        partPostList += c.toIndexXML
        i = i+1
        if(i == 6){
          xmlPostList += partPostList
          partPostList = new ListBuffer()
          i = 0
        }
      }
      if(partPostList.length != 0)
        xmlPostList += partPostList
      xmlPostList

    }
}

