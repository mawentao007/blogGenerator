import scala.xml._
import scala.collection.mutable.ListBuffer
class ArticleInfo(fileName:String,title:String,paragraph:ListBuffer[String]){
  def myFileName = fileName
  def myTitle = title
  def myParagraph = paragraph


  def toIndexXML =                                                             //提取索引页面内容 
    <div>
      <h2 class = "title">
        <a href = {"./posts/"+ myFileName}>{myTitle}</a>
      </h2>
      <div class = "post-info-top"></div>
      <div class = "entry">
      {
        if(myParagraph.length >= 2)
          <p>{myParagraph(0)}</p>
          <p>{myParagraph(1)}</p>
        else
          <p>{myParagraph(0)}</p>
      }
      </div>
    </div>

  def toSidebarXML =                                                       //侧边栏链接
    <li><a href = {"./posts/" + myFileName}>{myTitle}</a></li>

  def toTitleXML =                                                        //标题链接
    <a href = {myFileName}>{myTitle}</a>

  def toContentXML:ListBuffer[String] = {                                   //博文内容
    val content:ListBuffer[String] = ListBuffer()
    for(p <- myParagraph){
      content += "<p>"
      content += p
      content += "</p>"

    }
    content
  }
}

/*
object test{
  def main(args:Array[String]){
    val lb = new Article
    */
