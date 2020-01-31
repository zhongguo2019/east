//please beware that function started from _in_header_ must not be obfuscated
//toLowerCase ???


/**
*   @desc: filter grid by mask
*   @type: public
*   @param: column - {number} zero based index of column
*   @param: value - {string} filtering mask
*   @param: preserve - {bool} filter current state or initial one ( false by default )
*   @topic: 0
*/
dhtmlXGridObject.prototype.filterBy=function(column, value, preserve){
		if (this.pagingOn) return this.filterByPaging(column, value, preserve);
		if (this._dload) return this.filterBySRND(column, value, preserve);
		if (this._srowsCol){
			if (!preserve)
				this.rowsCol=dhtmlxArray([].concat(this._srowsCol));
		} else
			this._srowsCol=[].concat(this.rowsCol);	//backup copy
	
		
	if (!this.rowsCol.length) return;
	var d=true;
	if (typeof(value)=="function") d=false;

	
	if (typeof(value)=="object" && value.length)
		for (var j=0; j<value.length; j++)
			this._filterA(column[j],value[j],d);
	else	this._filterA(column,value,d);
	
	//redraw grid
	
	var p=this.obj.parentNode; 
//	p.removeChild(this.obj); //operation outside DOM is much faster
	var pp=this.obj.rows[0].parentNode; //safari way
	for (var i=this.obj.rows.length-1; i>0; i--)
		pp.removeChild(this.obj.rows[i]);
		
	for (var i=0; i<this.rowsCol.length; i++)		
		pp.appendChild(this.rowsCol[i]);
		
	p.appendChild(this.obj); //put back in DOM	
}
dhtmlXGridObject.prototype._filterA=function(column,value,d){
	var e=this.cells3(this.rowsCol[0],column);
	//caching exCell, as result we lost collspans 
	for (var i=this.rowsCol.length-1; i>=0; i--){
		e.cell=this.rowsCol[i].childNodes[column];
		if (d?(e.getValue().indexOf(value)==-1):(!value(e.getValue())))
			this.rowsCol.splice(i,1);//filter row
		
	}
}
dhtmlXGridObject.prototype.filterBySRND=function(column, value, preserve){
		for (var i=0; i<this.rowsCol.length; i++)
    	if ( this.rowsCol[i] && !this.rowsBuffer[1][i]) {
        	this.rowsBuffer[1][i]=this.rowsCol[i];
            this.rowsBuffer[0][i]=this.rowsCol[i].idd;
        }
	this._filterBy_core(column, value, preserve);
	//redraw grid
	for(var i=this.rowsCol.length-1;i>=0;i--)
		if (this.rowsCol[i])
    		this.rowsCol[i].parentNode.removeChild(this.rowsCol[i]);
	this.rowsCol=new dhtmlxArray();
	this.limit=this.rowsBuffer[0].length;
    this._fastAddRowSpacer(0,this.limit*this._srdh);      
    if (this.limit)
    	this._askRealRows(0);
}
dhtmlXGridObject.prototype._filterBy_core=function(column, value, preserve){
	if (this._srowsBuf){
			if (!preserve){
				this.rowsBuffer=[dhtmlxArray([].concat(this._srowsBuf[0])),dhtmlxArray([].concat(this._srowsBuf[1]))];
			}
		} else{
			this._srowsBuf=[[].concat(this.rowsBuffer[0]),[].concat(this.rowsBuffer[1])];
		}
	
	if (!this.rowsBuffer[0].length) return;
	var d=true;
	if (typeof(value)=="function") d=false;
	
		if (typeof(value)=="object" && value.length)
		for (var j=0; j<value.length; j++)
				this._filterB(column[j],value[j],d);
		else	this._filterB(column,value,d);
}
dhtmlXGridObject.prototype._filterB=function(column,value,d){
	var e=this.cells3(this.rowsCol[0],column);
	//caching exCell, as result we lost collspans 
	for (var i=this.rowsBuffer[0].length-1; i>=0; i--){
		if (this.rowsBuffer[1][i].idd){
			e.cell=this.rowsBuffer[1][i].childNodes[column];
			var val=e.getValue();
		} else {
			var val=this.rowsBuffer[1][i].getElementsByTagName("cell")[column];
			if (val && val.firstChild) val=val.firstChild.data;
			else val="";
		}
		if (d?(val.indexOf(value)==-1):(!value(val))){
			this.rowsBuffer[0].splice(i,1);//filter row
			this.rowsBuffer[1].splice(i,1);
		}
		
	}
}
dhtmlXGridObject.prototype.filterByPaging=function(column, value, preserve){
		var a0=[]; var a1=[];
		for (var i=0; i<this.rowsCol.length; i++){
			a0.push(this.rowsCol[i].idd);
			a1.push(this.rowsCol[i]);
		}
    	this.rowsBuffer[1]=dhtmlxArray(a1.concat(this.rowsBuffer[1]));
        this.rowsBuffer[0]=dhtmlxArray(a0.concat(this.rowsBuffer[0]));
        
		this._filterBy_core(column, value, preserve);
		
	//redraw grid

	for(var i=this.rowsCol.length-1;i>=0;i--)
		if (this.rowsCol[i].parentNode)
    		this.rowsCol[i].parentNode.removeChild(this.rowsCol[i]);
	this.rowsCol=new dhtmlxArray();
	this.limit=this.rowsBuffer[0].length;
	this.changePage(1);
}

/**
*   @desc: filter grid by mask
*   @type: public
*   @param: column - {number} zero based index of column
*   @returns: {array} array of all possible data in column
*   @topic: 0
*/
dhtmlXGridObject.prototype.collectValues=function(column){
	var c={}; var f=[];
	if (this.rowsCol.length)
	var e=this.cells3(this.rowsCol[0],column);
	for (var i=0; i<this.rowsCol.length; i++){
		e.cell=this.rowsCol[i].childNodes[column];
		var val=e.getValue();
		if (val) c[val]=true;
	}
	
	for (var i=this.rowsBuffer[0].length-1; i>=0; i--){
		if (!this.rowsBuffer[1][i]) continue;
		if (this.rowsBuffer[1][i].idd){
			e.cell=this.rowsBuffer[1][i].childNodes[column];
			var val=e.getValue();
			if (val) c[val]=true;
		} else {
			var val=this.rowsBuffer[1][i].getElementsByTagName("cell")[column];
			if (val && val.firstChild){
				val=val.firstChild.data;
				if (val) c[val]=true;
			}
		}
	}
	
	for (d in c) 
		if (c[d]===true) f.push(d);
	
	return f.sort();		
	
}

dhtmlXGridObject.prototype.filterByAll=function(){
	var a=[];
	var b=[];
	for (var i=0; i<this.filters.length; i++){
		a.push(this.filters[i][0].value);
		b.push(this.filters[i][1]);
	}
	this.filterBy(b,a);
	this.callEvent("onFilterEnd",[this.filters]);
}

/**
*   @desc: create a filter from any input
*   @type: public
*   @param: id - {string|object} input id or input html object
*   @param: column - {number} index of column
*   @param: preserve - {bool} filter current state or initial one ( false by default )
*   @topic: 0
*/
dhtmlXGridObject.prototype.makeFilter=function(id,column,preserve){
	if (!this.filters) this.filters=[];
	if (typeof(id)!="object")
		id=document.getElementById(id);
	if(!id) return;
	var self=this;
		
	if (id.tagName=='SELECT'){
		this.filters.push([id,column]);
		this._loadSelectOptins(id,column);
		id.onchange=function(){
			self.filterByAll();
		}
	} 
	else if (id.tagName=='INPUT'){
		this.filters.push([id,column]);
		id.value='';
		id.onkeypress=function(){
			if (this._timer) window.clearTimeout(this._timer);
			this._timer=window.setTimeout(function(){
				self.filterByAll();
			},500);
		};
	}
	else if (id.tagName=='DIV' && id.className=="combo"){
		this.filters.push([id,column]);
		if (!window.dhx_globalImgPath) window.dhx_globalImgPath=this.imgURL;
		var z=new dhtmlXCombo(id,"_filter","96%");
		z.enableFilteringMode(true);
		id.combo=z;
		id.value="";
		
		this._loadComboOptins(id,column);
		z.attachEvent("onBlur",function(){
			id.value=z.getSelectedValue();
			self.filterByAll();
		});
	}
}
dhtmlXGridObject.prototype._loadSelectOptins=function(t,c){ 
		var l=this.collectValues(c);
		t.innerHTML="";
		t.options[0]=new Option("","");
		for (var i=0; i<l.length; i++)
			t.options[t.options.length]=new Option(l[i],l[i]);
}
dhtmlXGridObject.prototype._loadComboOptins=function(t,c){ 
	var l=this.collectValues(c);
		t.combo.clearAll();
		t.combo.render(false);
		t.combo.addOption("","");
		for (var i=0; i<l.length; i++)
			t.combo.addOption(l[i],l[i]);
		t.combo.render(true);
}

/**
*   @desc: refresh filters values ( can be used if data in grid changed and filters need to be updated )
*   @type: public
*   @topic: 0
*/
dhtmlXGridObject.prototype.refreshFilters=function(){
	for (var i=0; i<this.filters.length; i++){
		switch(this.filters[i][0].tagName.toLowerCase()){
			case "input":
				break;
			case "select":
				this._loadSelectOptins.apply(this,this.filters[i]);
				break;
			case "div":
				this._loadComboOptins.apply(this,this.filters[i]);
				break;
		}
	}
}

dhtmlXGridObject.prototype._filters_ready=function(fl,code){
	this.attachEvent("onXLE",this.refreshFilters);
	this._filters_ready=function(){};
}

dhtmlXGridObject.prototype._in_header_text_filter=function(t,i){
	t.innerHTML="<input type='text' style='width:98%; font-size:8pt; font-family:Tahoma;'>";
	t.onclick=function(e){ (e||event).cancelBubble=true; return false; }
	this.makeFilter(t.firstChild,i);
	this._filters_ready();
}

dhtmlXGridObject.prototype._in_header_select_filter=function(t,i){
	t.innerHTML="<select style='width:98%; font-size:8pt; font-family:Tahoma;'></select>";
	t.onclick=function(e){ (e||event).cancelBubble=true; return false; }
	this.makeFilter(t.firstChild,i);
	this._filters_ready();
}

dhtmlXGridObject.prototype._in_header_combo_filter=function(t,i){
	t.innerHTML="<div style='width:100%; padding-left:2px; overflow:hidden; font-size:8pt; font-family:Tahoma; -moz-user-select:text;' class='combo'></div>";
	t.onselectstart=function(){ return (event.cancelBubble=true); }
	t.onclick=function(e){ (e||event).cancelBubble=true; return false; }
	this.makeFilter(t.firstChild,i);
	this._filters_ready();
}

