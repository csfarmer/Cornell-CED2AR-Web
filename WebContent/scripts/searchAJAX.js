var xmlhttp;
function search()
{

  xmlhttp=GetXmlHttpObject();

  if (xmlhttp==null)
  {
   alert ("Your browser does not support Ajax HTTP");
   return;
  }

    var query = document.getElementsByName("query")[0].value;
    url="SearchServlet?query="+query;
    xmlhttp.onreadystatechange=getOutput;
    xmlhttp.open("GET",url,true);
    xmlhttp.send(null);
    
    var simpleSearchHeader = '<p>You are searching for "' + document.getElementsByName("query")[0].value + '"</p> \
    <hr /> \
    <table class="codebookTable"><tr><td class=\"tdLeft\">Variable</td><td class=\"tdRight\">Label</td></tr></table> \
    <hr />';
    $("#simpleSearchHeader").html(simpleSearchHeader);
    
    document.getElementById("results").innerHTML="Loading...";
}

function getOutput()
{
  if (xmlhttp.readyState==4)
  {
	  document.getElementById("results").innerHTML=xmlhttp.responseText;
  }
}

function GetXmlHttpObject()
{
    if (window.XMLHttpRequest)
    {
       return new XMLHttpRequest();
    }
    if (window.ActiveXObject)
    {
      return new ActiveXObject("Microsoft.XMLHTTP");
    }
 return null;
}