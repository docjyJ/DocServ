// Change page
function startLoad(){
	$('.loading').show();
	closepopup();
	window.document.title = "Explorateur de fichier : Chargement...";
}
function endLoad(){
	var path = window.location.pathname.substring(9)
	window.document.title = "Explorateur de fichier : " + path;
	$('#navigation')[0].value = path;
	$('#plusfile').hide();
	$('#saveedit').hide();
	$('#noBut').hide();
	if($("#explorer").length){
		$('#plusfile').show();
	}
	else if($("textarea#code").length){
        var text
	    if($("#prop").length) {
            text = {
                mode:"properties",
                theme: "twilight",
                lineNumbers: true,
                lineWrapping: true,
                lint: true,
                styleActiveLine: true,
                foldGutter: true,
                gutters: ["CodeMirror-linenumbers", "CodeMirror-foldgutter","CodeMirror-lint-markers", "breakpoints"]
                //extraKeys: {"Ctrl-Q": function(cm){ cm.foldCode(cm.getCursor()); }}
            }
        }
	    else{
            text = {
                theme: "twilight",
                    lineNumbers: true,
                lineWrapping: true,
                lint: true,
                styleActiveLine: true,
                foldGutter: true,
                gutters: ["CodeMirror-linenumbers", "CodeMirror-foldgutter","CodeMirror-lint-markers", "breakpoints"]
                //extraKeys: {"Ctrl-Q": function(cm){ cm.foldCode(cm.getCursor()); }}
            }
        }
	     editor = CodeMirror.fromTextArea(document.getElementById("code"), text);
		editor.on("gutterClick", function(cm, n) {
    	  var info = cm.lineInfo(n);
    	  cm.setGutterMarker(n, "breakpoints", info.gutterMarkers ? null : function() {
    		  var marker = document.createElement("div");
    		  marker.style.color = "#822";
    		  marker.innerHTML = "●";
    		  return marker;
    		});
    	});
		$('#editForm').ajaxForm(function() {
			relodePage();
		});
		$('#saveedit').show();
	}
	else {
		$('#noBut').show();
	}
	$('.loading').hide();
}
window.onload =	function() { 
	$('body').show();
	relodePage();
}
window.onpopstate = function() {
	relodePage();
}
$("form#nav").submit(function(e) {
    e.preventDefault();
    loadPage("/explorer/" + $("#navigation")[0].value);
});
function manOpen(location){
	loadPage(window.location.pathname + location)
}
function relodePage(){
	startLoad();
	var url = window.location.pathname + "?files";
	$.ajax({
        url: url,
        type: "GET",
        data: null,
        success: function (data) {
        	document.querySelector("section#dropfile").innerHTML = data;
        	endLoad();
        }
    });
}
function loadPage(url){
	history.pushState({}, "", url);
	relodePage();
}
var editor;
//Form
$('#fileForm').ajaxForm(function() {
	relodePage();
});
$('#newFileForm').ajaxForm(function() {
	relodePage();
});
function delFileConfirm(){
	var url = $("p#nameDel")[0].innerHTML +"?delete";
	$.ajax({
        url: url,
		type: "POST",
		data: null,
		success: function (data) {
        	relodePage();
        }
    });
}
function savefile(){
	editor.save();
	$('#editForm').submit();
}
function downFile(name){
	window.location.href = window.location.pathname + name + "?download";
}
function extractFile(name){
	var url = window.location.pathname + name + "?extract";
	$.ajax({
		url: url,
		type: "POST",
		data: null,
		success: function (data) {
			relodePage();
		}
	});
}


//popup
function closepopup(){
	$('section#confirm').hide();
	$('section#addNew').hide();
	$('section#confirmDel').hide();
}
function delFile(path){
	$("p#nameDel")[0].innerHTML = window.location.pathname + path;
	closepopup();
	$('section#confirmDel').show();
}
function shownewfile(){
	if($("section#addNew").is(":visible")) {
		closepopup();
	}
	else {
		closepopup();
		$('section#addNew').show();
	}
}
function openfilechose(){
	$('#fileInput').click();
}
function showDialog(){
	var text="<h1>Fichiers</h1><ul>";
	for(var i=0; i<$("#fileInput")[0].files.length; i++){
		text += "<li>" + $('#fileInput')[0].files[i].name + "</li>";
	}
	$("p#name")[0].innerHTML = text + "</ul>";
	closepopup();
	$('section#confirm').show();
}
function filesubmit(){
	$('.loading').show();
	window.document.title = "Explorateur de fichier : Chargement...";
	$('#fileForm').submit();
}
// Gestion drag drop
$('#dropfile').on('dragover', function(e){
    e.preventDefault();
    e.stopPropagation();
    if($("#explorer").length){
	    $("#dropstyle").css('border', '3px dashed #BBBBBB');
    }
});
$('#dropfile').on('dragleave', function(e) {
    e.preventDefault();
    e.stopPropagation();
    if($("#explorer").length){
        $("#dropstyle").css('border', '0');
    }
});
$('#dropfile').on('drop', function (e) {
    e.preventDefault();
    e.stopPropagation();
    if($("#explorer").length){
        $("#dropstyle").css('border', '0');
        $("#fileInput")[0].files = e.originalEvent.dataTransfer.files;
        showDialog();
    }
});

$('#fileInput').on('change', function(){
	showDialog();
});