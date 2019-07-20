<form action="?edit" id="editForm" method="post" style="height: 100%;">
	<textarea id="code" name="code" placeholder="Doccument vide...">${ text }</textarea>
	<input class="hide" type="submit" />
</form>
<c:if test="${ name == 'server.properties' }"><div id="prop"></div></c:if>