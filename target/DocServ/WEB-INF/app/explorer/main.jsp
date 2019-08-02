<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Explorateur de fichier</title>
	<link rel="icon" href="/file/favicon/explorer.ico">
	<link rel="stylesheet" href="/file/css/explorer.css">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.9.0/css/all.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.48.0/codemirror.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.48.0/theme/twilight.css">
</head>
<body style="display: none;">
	<form action="?upload" class="hide" id="fileForm" method="post" enctype="multipart/form-data">
		<input type="file" name="file" id="fileInput" multiple/>
		<input type="submit" />
	</form>

	<div class="loading" style="display: none;">
		 <div></div>
		 <div></div>
		 <div></div>
		 <div></div>
		 <div></div>
		 <div></div>
		 <div></div>
		 <div></div>
	</div>
	<header>
		<button class="nav" onclick="window.history.back()">
			<i class="fas fa-angle-left"></i>
		</button><button class="nav" onclick="window.history.forward()">
			<i class="fas fa-angle-right"></i>
		</button><button class="nav" onclick="relodePage()">
			<i class="fas fa-sync-alt"></i>
		</button><button id="noBut" class="nav">
			<i class="fas"></i>
		</button><button id="plusfile" class="nav" onclick="shownewfile()" style="display: none;">
			<i class="fas fa-plus"></i>
		</button><button id="saveedit" class="nav" onclick="savefile()" style="display: none;">
			<i class="fas fa-save"></i>
		</button><form id="nav">
			<input class="nav" type="text" id="navigation" value="${ title }">
			<input class="hide" type="submit">
		</form>
	</header>
	<section id="dropstyle" style="width: calc(100% - 6px);height: calc(100% - 57px);"></section>
	<section id="dropfile"></section>
	<section id="addNew" style="display: none;">
		<div class="popup">
			<div style="text-align: right; height: 0;"><button class="closePop" onclick="closepopup()"><i class="fas fa-times"></i></button></div>
			<h1>Importer des fichiers depuis le pc</h1>
			<button class="creeItem" onclick="openfilechose()"><span>Importer</span></button>
			<h1>Créer un fichier vierge ou un dossier</h1>
			<form class="creeItem" action="?newfile" id="newFileForm" method="post">
			<input class="" type="text" id="navigation" name="title" placeholder="Nom du fichier/docier"><label class="container">Fichier<input type="radio" checked="checked" name="radio" value="Fichier"><span class="checkmark"></span></label><label class="container">Dossier<input type="radio" name="radio" value="Dossier"><span class="checkmark"></span></label><button class="" type="submit" onclick=""><span>Créer</span></button>
			</form>
		</div>
	</section>
	<section id="confirm" class="confirm"  style="display: none;">
		<div class="popup">
		<div style="text-align: right; height: 0;"><button class="closePop" onclick="closepopup()"><i class="fas fa-times"></i></button></div>
		<p id="name"></p>
		<button class="confirm" onclick="filesubmit()"><span>Importer</span></button>
		<button class="confirm" onclick="closepopup()"><span>Anuler</span></button>
		</div>
	</section>
	<section id="confirmDel" class="confirm" style="display: none;">
		<div class="popup">
		<div style="text-align: right; height: 0;"><button class="closePop" onclick="closepopup()"><i class="fas fa-times"></i></button></div>
		<h1>Suprimer</h1>
		<p id="nameDel"></p>
		<button class="confirm" onclick="delFileConfirm()"><span>Suprimer</span></button>
		<button class="confirm" onclick="closepopup()"><span>Anuler</span></button>
		</div>
	</section>

	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.form/4.2.2/jquery.form.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.48.0/codemirror.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.48.0/mode/javascript/javascript.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.48.0/mode/xml/xml.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.48.0/mode/properties/properties.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.48.0/addon/selection/active-line.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.48.0/addon/fold/foldcode.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.48.0/addon/fold/comment-fold.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.48.0/addon/lint/lint.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.48.0/addon/lint/json-lint.js"></script>

	<script src="/file/js/explorer.js"></script>
</body>
</html>