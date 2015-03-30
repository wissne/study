// Copyright (c) 2012 The Chromium Authors. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

function click(e) {
	chrome.tabs.executeScript(null, {
		code : "document.body.style.backgroundColor='" + e.target.id + "'"
	});
	window.close();
}

function doSearch(e) {
//	alert(($("#keyword").val);
	chrome.tabs.executeScript(null, {
		file: 'jquery-2.1.3.min.js',
		allFrames: false
	});
	var mycode='alert("' + $("#myKeyword").val() + '");';
	chrome.tabs.executeScript(null, {
		file : 'content_script.js',
//		code : 'alert( "' + $("#myKeyword").val()+ '" );',
//		code : mycode,
		allFrames: false
	});
//	window.close();
}

document.addEventListener('DOMContentLoaded', function() {
//	var divs = document.querySelectorAll('div');
//	for (var i = 0; i < divs.length; i++) {
//		divs[i].addEventListener('click', click);
//	}
	// Search
	var button=$("#mySubmit");
	button.bind("click", doSearch);
});

