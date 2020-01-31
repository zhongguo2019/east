ActiveXTree = function ()
{
};

ActiveXTree.showEl = function(el, width, height)
{
	el.style.width = width;
	el.style.height = height;
};

ActiveXTree.hideEl = function(el)
{
	ActiveXTree.showEl(el, "1px", "1px");	
}; 

ActiveXTree.getAbsolutePos = function(el) 
{
	var SL = 0, ST = 0;
	var is_div = /^div$/i.test(el.tagName);
	if (is_div && el.scrollLeft)
	{
		SL = el.scrollLeft;
	}
	if (is_div && el.scrollTop)
	{
		ST = el.scrollTop;
	}
	var r = { x: el.offsetLeft - SL, y: el.offsetTop - ST };
	if (el.offsetParent) 
	{
		var tmp = this.getAbsolutePos(el.offsetParent);
		r.x += tmp.x;
		r.y += tmp.y;
	}
	return r;
};

ActiveXTree.getTreeValue = function(obj, valueInput, textInput)
{
	if(obj.checkStyle == "0")
	{
		valueInput.value = obj.getCurrentItem();
		textInput.value = obj.getCurrentItemName();
	}
	else
	{
		if(obj.selectChild == "1" || obj.checkStyle == "2")
		{
			valueInput.value = obj.getSelectedEndItems();
			textInput.value = obj.getSelectedEndItemNames();
		}
		else
		{
			valueInput.value = obj.getSelectedItems();
			textInput.value = obj.getSelectedItemNames();
		}
	}	
}

ActiveXTree.initTree = function(obj, xmlPath, backColor, checkStyle, selectChild, columnTitle, columnWidth, fillLayer, rootId, treeValue, isBuildTree)
{
	if(isBuildTree)
	{
		obj.setXMLPath(xmlPath);
		obj.backColor = backColor;
		obj.checkStyle = checkStyle;
		obj.selectChild = selectChild;
		obj.columnTitle = columnTitle;
		obj.columnWidth = columnWidth;
		obj.fillLayer = fillLayer;
		obj.rootId = rootId;	
		
		obj.setActive();
		obj.show();
		
		if(treeValue)
		{
			if(checkStyle == "0")
			{
				obj.setCurrentItem(treeValue);
			}
			else if(checkStyle == "3")
			{
				obj.selectItems(treeValue);
				obj.expand(treeValue);
				obj.setCurrentItem(treeValue);
			}
			else
			{
				obj.selectItems(treeValue);
			}
		}
		else
		{
			//obj.selectItems("");
		}
	}
};

ActiveXTree.loadTree = function(div, obj, xmlPath, backColor, checkStyle, selectChild, columnTitle, columnWidth, fillLayer, rootId, treeValue, isBuildTree)
{	
	if(!isBuildTree)
	{
		//div.style.display = "none";
		//obj.style.display = "none";
		ActiveXTree.hideEl(div);
		ActiveXTree.hideEl(obj);
	}
	ActiveXTree.initTree(obj, xmlPath, backColor, checkStyle, selectChild, columnTitle, columnWidth, fillLayer, rootId, treeValue, isBuildTree);
	//div.style.display = "none";
	//obj.style.display = "none";
	ActiveXTree.hideEl(div);
	ActiveXTree.hideEl(obj);
};

ActiveXTree.showTree = function (div, obj, width, height, xmlPath, backColor, checkStyle, selectChild, columnTitle, columnWidth, fillLayer, rootId, treeValue, el) 
{
	//div.style.display = "block";
	//obj.style.display = "block";
	if(div.isInitialized)
	{
		obj.setActive();
		if(!div.isHidden)
		{
			if(el)
			{
				var p = ActiveXTree.getAbsolutePos(el);
				div.style.left = p.x + "px";
				div.style.top = p.y + "px";
			}			
			//div.style.width = width;
			//div.style.height = height;
			//obj.style.width = "100%";	
			//obj.style.height = "100%";
			ActiveXTree.showEl(div, width, height);	
			ActiveXTree.showEl(obj, width, height);
		}
	}
	else
	{
		div.isInitialized = true;
		div.style.position = "absolute";
	
		if(div.isHidden)
		{
			//div.style.width = "0";
			//div.style.height = "0";
			//obj.style.width = "0";	
			//obj.style.height = "0";
			ActiveXTree.hideEl(div);
			ActiveXTree.hideEl(obj);
			div.isHidden = false;
		}
		else
		{
			if(el)
			{
				var p = ActiveXTree.getAbsolutePos(el);
				div.style.left = p.x + "px";
				div.style.top = p.y + "px";
			}
			//div.style.width = width;
			//div.style.height = height;
			//obj.style.width = "100%";	
			//obj.style.height = "100%";
			ActiveXTree.showEl(div, width, height);	
			ActiveXTree.showEl(obj, width, height);		
		}		
		ActiveXTree.initTree(obj, xmlPath, backColor, checkStyle, selectChild, columnTitle, columnWidth, fillLayer, rootId, treeValue, true);
	}
};

ActiveXTree.onLoad = function(div, obj, xmlPath, backColor, checkStyle, selectChild, columnTitle, columnWidth, fillLayer, rootId, valueInput, textInput, textShow)
{
	var isBuildTree = (valueInput.value != "" && textInput.value == "");
	ActiveXTree.loadTree(div, obj, xmlPath, backColor, checkStyle, selectChild, columnTitle, columnWidth, fillLayer, rootId, valueInput.value, isBuildTree);
	if(isBuildTree)
	{
		ActiveXTree.getTreeValue(obj, valueInput, textInput);
	}
	textShow.value = textInput.value;	
};

ActiveXTree.onHide = function(div, obj, valueInput, textInput, onHide)
{
	/*
	if(div.style.display != "none")
	{
		div.style.display = "none";
		obj.style.display = "none";
		ActiveXTree.getTreeValue(obj, valueInput, textInput);
		if(div.isInitialized && onHide)
		{
			onHide();
		}
	}
	*/
	if(div.style.width != "1px" && div.style.height != "1px")
	{
		ActiveXTree.hideEl(div);
		ActiveXTree.hideEl(obj);	
		ActiveXTree.getTreeValue(obj, valueInput, textInput);
		if(div.isInitialized && onHide)
		{
			onHide();
		}
	}
};

ActiveXTree.setTreeValue = function(divObj, activeXObj, btnObj, textObj, valueInput, textInput)
{
	if(!divObj.isInitialized)
	{		
		divObj.isHidden = true;
		btnObj.onclick();
		divObj.onmouseout();
	}
	if(valueInput.value != "" && valueInput.value != null)
	{
		activeXObj.selectItems(valueInput.value);
		ActiveXTree.getTreeValue(activeXObj, valueInput, textInput);
		textObj.value = textInput.value;
	}
	else
	{
		textInput.value = "";
		textObj.value = "";
	}
};

ActiveXTree.createControl = function (divId, objId, codebase)
{
  document.getElementById(divId).innerHTML = '<object id="' + objId + '" classid="CLSID:B6DE0B41-91FE-41BC-9F4B-DC9428EA2B4B" codebase="' + codebase + '"style="display:block; width:1px; height:1px;"></object>';
};