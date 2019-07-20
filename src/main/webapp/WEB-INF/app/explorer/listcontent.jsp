<div id="explorer">
	<c:forEach items="${ files }" var="file">
			<c:choose>
				<c:when test="${ file[1] == 0 }">
			    <button class="item" ondblclick="manOpen('${ file[0] }/')">
					<i class="fas fa-folder"></i>
		    		<span>${ file[0] }</span>
		    		<div class="editbox">
						<i class="fas fa-file-download" onclick="downFile('${ file[0] }/')"></i>
		    			<!-- <span class="oi" data-glyph="pencil" onclick=""></span> -->
						<i class="fas fa-trash-alt" onclick="delFile('${ file[0] }/')"></i>
		    		</div>
				</button>
				</c:when>
				<c:when test="${ file[1] == 1 }">
					<button class="item" ondblclick="manOpen('${ file[0] }')">
						<i class="fas fa-file"></i>
						<span>${ file[0] }</span>
						<div class="editbox">
							<i class="fas fa-file-download" onclick="downFile('${ file[0] }')"></i>
							<!-- <span class="oi" data-glyph="pencil" onclick=""></span> -->
							<i class="fas fa-trash-alt" onclick="delFile('${ file[0] }')"></i>
						</div>
					</button>
				</c:when>
				<c:when test="${ file[1] == 2 }">
					<button class="item" ondblclick="manOpen('${ file[0] }')">
						<i class="fas fa-file-code"></i>
						<span>${ file[0] }</span>
						<div class="editbox">
							<i class="fas fa-file-download" onclick="downFile('${ file[0] }')"></i>
							<!-- <span class="oi" data-glyph="pencil" onclick=""></span> -->
							<i class="fas fa-trash-alt" onclick="delFile('${ file[0] }')"></i>
						</div>
					</button>
				</c:when>
				<c:when test="${ file[1] == 3 }">
					<button class="item" ondblclick="manOpen('${ file[0] }')">
						<i class="fas fa-file-archive"></i>
						<span>${ file[0] }</span>
						<div class="editbox">
							<i class="fas fa-external-link-square-alt" onclick="extractFile('${ file[0] }')"></i>
							<i class="fas fa-file-download" onclick="downFile('${ file[0] }')"></i>
							<!-- <span class="oi" data-glyph="pencil" onclick=""></span> -->
							<i class="fas fa-trash-alt" onclick="delFile('${ file[0] }')"></i>
						</div>
					</button>
				</c:when>
				<c:when test="${ file[1] == 4 }">
					<button class="item" ondblclick="manOpen('${ file[0] }')">
						<i class="fas fa-file-image"></i>
						<span>${ file[0] }</span>
						<div class="editbox">
							<i class="fas fa-file-download" onclick="downFile('${ file[0] }')"></i>
							<!-- <span class="oi" data-glyph="pencil" onclick=""></span> -->
							<i class="fas fa-trash-alt" onclick="delFile('${ file[0] }')"></i>
						</div>
					</button>
				</c:when>
			</c:choose>
	</c:forEach>
</div>