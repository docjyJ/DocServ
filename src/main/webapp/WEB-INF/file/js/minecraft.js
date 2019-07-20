//main
function oppenWindows(url) {
	new JSFrame().create({
		title: 'Window',
		left: 20,
		top: 20,
		width: 680,
		height: 450,
		minWidth: 680,//The minimum width of the window
		minHeight: 450,//The minimum height of the window
		appearanceName: 'material',
		appearanceParam: {
			border: {
				color: '#404040',
				width: 1,
				radius: 0
			},
			titleBar: {
				color: 'white',
				background: '#404040',
				leftMargin: 40,
				height: 30,
				fontSize: 14,
				buttonWidth: 36,
				buttonHeight: 16,
				buttonColor: 'white',
				buttons: [ // buttons on the right
					{
						//Set font-awesome fonts(https://fontawesome.com/icons?d=gallery&m=free)
						fa: 'fas fa-times-circle',//code of font-awesome
						name: 'closeButton',
						visible: true // visibility when window is created.
					},
					{
						fa: 'fas fa-minus-circle',
						name: 'maximizeButton',
						visible: false
					},
					{
						fa: 'fas fa-stop-circle',
						name: 'minimizedButton',
						visible: false
					}
				]
			}
		},
		movable: true,
		resizable: true,
		url: url
	}).show();
}
function coppy() {
	navigator.clipboard.writeText("docjyj.ddns.net");
	document.getElementById("myTooltip").innerHTML = "Copié !";
}
function outcoppy() {
	document.getElementById("myTooltip").innerHTML = "Click pour copier";
}
// Change page
window.onload =	function() {
	$('body').show();
	relodePage();
}
window.onpopstate = function() {
	relodePage();
}
function relodePage(){
	$('.loading').show();
	window.document.title = "Explorateur de fichier : Chargement...";
	$.ajax({
        url: "/minecraft/?serveurs",
        type: "GET",
        data: null,
        success: function (data) {
        	document.querySelector("section#servers").innerHTML = data;
        	var path = window.location.pathname.substring(9);
        	window.document.title = "Explorateur de fichier : ";
        	if ($("form#start").length){
	        	subStart();
        	}
        	if ($("form#cmd").length){
        		subCMD();
        	}
        	$('.loading').hide();
        }
    });
}
function loadPage(url){
	//history.pushState({}, "", url);
	relodePage();
}
//form
function subStart() {
	$("form#start").submit(function(e) {
	    e.preventDefault();
	    var formdata = $('form#start').serialize();
		document.getElementById('allStart').classList.remove('select');
		document.getElementById("allStart").innerHTML = "<load></load>";
		$.ajax({
		    url: "?start",
		    type: "POST",
		    data: formdata,
		    success: function (data) {
	    		document.getElementById("allStart").innerHTML = "<span>Démaré</span><button class=\"formSelect2\" onclick =\"relodePage();\">🗘</button>";
	        }
	    });
	});
}
function subCMD() {
	$("form#cmd").submit(function(e) {
	    e.preventDefault();
	    var formdata = $('form#cmd').serialize();
		$.ajax({
		    url: "?command",
		    type: "POST",
		    data: formdata,
		    success: function (data) {
	    		document.getElementById("console").innerHTML += "<br>" +data;
	        }
	    });
	});
}
