chrome.omnibox.setDefaultSuggestion({description: "Search articles in current page"});
 
chrome.omnibox.onInputChanged.addListener(
  function(text, suggest) {
    suggest([
      {content: "tr="+text, description: "Search with tr " + text}, 
      {content: "div="+text, description: "Search with div " + text}
    ]);
  });
 
chrome.omnibox.onInputEntered.addListener(function(text) {
 
  var url;
  var type;
  if(text.indexOf("tr=") == 0){
    url = text.substring(3);
	type = "tr";
  }else if(text.indexOf("div=") == 0){
    url = text.substring(4);
	type = "div";
  }else{
    url = "https://www.baidu.com/search?kw="+encodeURIComponent(text)
  }
  
  // 找到当前 Tab, 并在当前 Tab 中打开相应的页面 
  chrome.tabs.getSelected(null, function(tab){
    // chrome.tabs.update(tab.id, {url: url});
	chrome.tabs.executeScript(null, {
		file: 'jquery-2.1.3.min.js'
	}, function(){
		if (type) {
			chrome.tabs.executeScript(null, {
				allFrames: true,
				code: 'var allObjects=document.getElementsByTagName("' +type+'");for(var i=0;i<allObjects.length;i++){allObjects[i].hidden=true;if (allObjects[i].innerHTML.indexOf("' +url+ '")>0)allObjects[i].hidden=false;}'
			});
		} else {
			chrome.tabs.update(tab.id, {url: url});
		}
	});

  });
 
});