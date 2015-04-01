chrome.omnibox.setDefaultSuggestion({description: "Only Search in current page"});
 
chrome.omnibox.onInputChanged.addListener(
  function(text, suggest) {
    suggest([
      {content: "TR="+text, description: "Search with tr " + text}, 
      {content: "DIV="+text, description: "Search with div " + text},
      {content: "BaiDu="+text, description: "Search in Baidu " + text},
      {content: "Google="+text, description: "Search in Google " + text}
    ]);
  });
 
chrome.omnibox.onInputEntered.addListener(function(text) {
  var url;
  var type="tr";
  if(text.indexOf("tr=") == 0){
    url = text.substring(3);
	type = "tr";
  }else if(text.indexOf("div=") == 0){
    url = text.substring(4);
	type = "div";
  }else if(text.indexOf("BaiDu=") == 0){
    url = "http://www.baidu.com/s?wd=" + text.substring(6);
	type = null;
  }else if(text.indexOf("Google=") == 0){
    url = "http://www.google.com/search?q=" + text.substring(7);
	type = null;		
  }else {
	url = text;
  }
  
  // 找到当前 Tab, 并在当前 Tab 中打开相应的页面 
  chrome.tabs.getSelected(null, function(tab){
    // chrome.tabs.update(tab.id, {url: url});
//	chrome.tabs.executeScript(null, {
//		file: 'jquery-2.1.3.min.js'
//	}, function(){
		if (type) {
			chrome.tabs.executeScript(null, {
				allFrames: true,
				code: 'var allObjects=document.getElementsByTagName("' +type+'");for(var i=0;i<allObjects.length;i++){allObjects[i].hidden=true;if (allObjects[i].innerHTML.indexOf("' +url+ '")>0)allObjects[i].hidden=false;}'
			});
		} else {
			chrome.tabs.update(tab.id, {url: url});
		}
//	});

  });
 
});