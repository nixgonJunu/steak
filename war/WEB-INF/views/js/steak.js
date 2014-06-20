function changeTable( lineNum ) {
	var anch = document.getElementById( 'table' );
	anch.innerHTML = document.getElementById( 'table_' + lineNum ).innerHTML;
}

function popupAlert( cell ) {
	var inner = cell.innerText;
	alert( inner );
}

function showDropdown() {
	alert( 'dropdown!' )
}

function chkToogleAll() {
	var elems = document.getElementsByTagName( 'input' ), i;
	for ( i in elems ) {
		if ( checkClass( elems[ i ].className, 'chkbox' ) ) {
			elems[ i ].checked = document.getElementById( 'chk_all' ).checked;
		}
	}
}

function checkStage( stage ) {
	var elems = document.getElementsByTagName( 'input' ), i;
	for ( i in elems ) {
		if ( checkClass( elems[ i ].className, 'chkbox' ) ) {
			if ( elems[ i ].id.substring( 4, 4 + stage.length ) == stage ) {
				elems[ i ].checked = document.getElementById( 'chk_' + stage ).checked;
			}
		}
	}
}

function foldToggleAll() {
	var elems = document.getElementsByTagName( 'div' ), i;
	var imgs = document.getElementsByTagName( 'img' ), j;
	var arrow = document.getElementById( 'downAll' );

	for ( j in imgs ) {
		if ( checkClass( imgs[ j ].className, 'arrow-down' ) ) {
			if ( checkClass( arrow.className, 'rows-hidden' ) ) {
				imgs[ j ].style.borderLeft = '5px solid transparent';
				imgs[ j ].style.borderRight = '5px solid transparent';
				imgs[ j ].style.borderTop = '5px solid black';
				imgs[ j ].style.borderBottom = '0';
			} else {
				imgs[ j ].style.borderLeft = '5px solid black';
				imgs[ j ].style.borderRight = '0';
				imgs[ j ].style.borderTop = '5px solid transparent';
				imgs[ j ].style.borderBottom = '5px solid transparent';
			}
		}
	}

	for ( i in elems ) {
		if ( checkClass( elems[ i ].className, 'rows' ) ) {
			if ( checkClass( arrow.className, 'rows-hidden' ) ) {
				elems[ i ].style.height = 'auto';
				elems[ i ].style.overflow = 'visible';
				elems[ i ].style.visibility = 'visible';
				arrow.style.borderLeft = '5px solid transparent';
				arrow.style.borderRight = '5px solid transparent';
				arrow.style.borderTop = '5px solid black';
				arrow.style.borderBottom = '0';
			} else {
				elems[ i ].style.height = '0px';
				elems[ i ].style.overflow = 'hidden';
				elems[ i ].style.visibility = 'hidden';
				arrow.style.borderLeft = '5px solid black';
				arrow.style.borderRight = '0';
				arrow.style.borderTop = '5px solid transparent';
				arrow.style.borderBottom = '5px solid transparent';
			}
		}
	}

	if ( checkClass( arrow.className, 'rows-hidden' ) ) {
		arrow.className = arrow.className.replace( ' rows-hidden', '' );
	} else {
		arrow.className += ' rows-hidden';
	}
}

function foldStage( stage ) {
	var elems = document.getElementsByTagName( 'div' ), i;
	var arrow = document.getElementById( 'downStage_' + stage );
	for ( i in elems ) {
		if ( checkClass( elems[ i ].className, 'rows' ) ) {
			if ( checkClass( elems[ i ].className, 'rows_' + stage ) ) {
				if ( checkClass( elems[ i ].className, 'rows_hidden' ) ) {
					elems[ i ].className = elems[ i ].className.replace( ' rows_hidden', '' );
					elems[ i ].style.height = 'auto';
					elems[ i ].style.overflow = 'visible';
					elems[ i ].style.visibility = 'visible';
					arrow.style.borderLeft = '5px solid transparent';
					arrow.style.borderRight = '5px solid transparent';
					arrow.style.borderTop = '5px solid black';
					arrow.style.borderBottom = '0';
				} else {
					elems[ i ].className += ' rows_hidden';
					elems[ i ].style.height = '0px';
					elems[ i ].style.overflow = 'hidden';
					elems[ i ].style.visibility = 'hidden';
					arrow.style.borderLeft = '5px solid black';
					arrow.style.borderRight = '0';
					arrow.style.borderTop = '5px solid transparent';
					arrow.style.borderBottom = '5px solid transparent';
				}
			}
		}
	}
}

function checkClass( src, dst ) {
	if ( ( ' ' + src + ' ' ).indexOf( ' ' + dst + ' ' ) > -1 ) {
		return true;
	} else {
		return false;
	}
}