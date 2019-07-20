<c:choose>
    <c:when test="${ openServ == 0 }">
	    <div class="div">
			<h1>Démarer un serveur</h1>
			<div class="formSelect" id="allStart">
				<form id="start">
					<div class="formSelecta" id="selectStart">
						<select required name="name" class="formSelect" onmouseover="document.getElementById('selectStart').classList.add('select');" onmouseout="document.getElementById('selectStart').classList.remove('select');">
				        	<option value="" selected>Serveur</option>
							<c:forEach items="${ server }" var="optgroup">
							    <optgroup label="${ optgroup.key[0] }">
								    <c:forEach items="${ optgroup.value }" var="option" varStatus="status">
									   <option value="${ optgroup.key[1] }/${ option }">${ option }</option>
									</c:forEach>
								</optgroup>
							</c:forEach>
						</select>
					</div><div class="formSelectb">
						<button type="submit" id="subStart" class="formSelect" onmouseover="document.getElementById('allStart').classList.add('select');" onmouseout="document.getElementById('allStart').classList.remove('select');"><i class="fas fa-play"></i></button>
					</div>
				</form>
			</div>
		</div>
	</c:when>
    <c:when test="${ openServ == 1 }">
    	<div class="div">
			<h1>Serveur démarer</h1>
				<span>Nom : ${ serverServ[1] }</span>
			<br>
				<span>Version : ${ serverServ[0] }</span>
		</div>
		<div class="div">
			<h1>Console</h1>
			<div id=console></div>
			<form id="cmd">
				<input class="" type="text" name="cmd" value="${ title }">
				<input class="" type="submit">
			</form>
		</div>
		
	</c:when>
    <c:when test="${ openServ == 2 }">
    	<div class="div">
			<h1>Erreur Interne</h1>
			<div class="formSelect" id="allStart">
				<span>Erreur</span><button class="formSelect2" onclick ="relodServers();"><i class="fas fa-sync-alt"></i></button>
			</div>
		</div>
	</c:when>
</c:choose>