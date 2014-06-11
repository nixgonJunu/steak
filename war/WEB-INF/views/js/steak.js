window.onload = function() {
	var navbarWidth = document.getElementById( 'nav-container' ).offsetWidth;
	var navItemWidth = document.getElementById( 'pipeline-container' ).offsetWidth;

	boxTable.style.maxWidth = document.getElementById( 'stageContainer' ).offsetWidth + 'px';
	boxTable.style.minWidth = 16 * document.getElementById( 'cols' ).offsetWidth + 'px';

	mainContainer.style.height = ( document.getElementById( 'mainContainer' ).offsetHeight - 60 ) + 'px';
	boxContainer.style.height = ( document.getElementById( 'boxContainer' ).offsetHeight - document
			.getElementById( 'stageContainer' ).offsetHeight )
			+ 'px';
}

function changePipeline( lineNum ) {
	var anch = document.getElementById( 'pipeline' );
	anch.innerHTML = document.getElementById( 'pipeline_' + lineNum ).innerHTML;
}

function popupAlert( cell ) {
	var inner = cell.innerText;
	alert( inner );
}

function showDropdown() {
	alert( 'dropdown!' )
}