<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>DocServ V2 Beta</title>
		<link rel="icon" href="/file/favicon/main.ico">
		<link rel="stylesheet" href="/file/css/minecraft.css">
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.9.0/css/all.css">
    </head>
	<body style="display: none;">
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
		<div>
			<%--
			<button class="nav" onclick="window.history.back()"><i class="fas fa-angle-left"></i></button>
			<button class="nav" onclick="window.history.forward()"><i class="fas fa-angle-right"></i></button>
			--%>
			<div>
				<button class="nav" onclick="relodePage()"><i class="fas fa-sync-alt"></i></button>
				<button class="nav" onclick="location.href='?backup';"><i class="fas fa-file-archive"></i></button>
				<button class="nav" onclick="oppenWindows('/explorer/');"><i class="far fa-folder-open"></i></button>
			</div><div>
			<span class="ip">IP : <span class="tooltip" onclick="coppy()" onmouseout="outcoppy()">docjyj.ddns.net<span class="tooltiptext" id="myTooltip">Copy to clipboard</span></span></span>
			</div><div>
				<button class="nav" onclick="window.open('/manager/');"><i class="fas fa-key"></i></button>
				<button class="nav" onclick="location.href='?deconextion';"><i class="fas fa-sign-out-alt"></i></button>
			</div>
		</div>
	</header>
	<section id="servers"></section>
	<script src="https://riversun.github.io/jsframe/jsframe.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.form/4.2.2/jquery.form.js"></script>
	<script src="/file/js/minecraft.js"></script>
	</body>
</html>