function MyNnd_GetChild(parent, tag, index)
{
	var counter = 0;
	if (parent.childNodes)
	{
		for (var i = 0; i < parent.childNodes.length; i++)
		{
			var node = parent.childNodes[i];
			if (node.nodeName == tag.toUpperCase())
			{
				if (counter == index)
				{
					return node;
				}
				else
				{
					counter++;
				}
			}
		}
	}
	return null;
}

function MyNnd_FindByText(rootNode, tag, text)
{
	var nodes = MyNnd_Find_TagList(rootNode, tag);
	if (nodes != null)
	{
		for (var i = 0; i < nodes.length; i++)
		{
			var node = nodes[i];
			if (node.innerHTML.trim() == text)
			{
				return node;
			}
		}
	}
	MyNnd_Log("MyNnd_FindByText not find : " + tag + "," + text, null);
	return null;
}

function MyNnd_Find_Id(rootNode, id)
{
	return rootNode.getElementById(id);
}

function MyNnd_Find_TagList(rootNode, tag)
{
	return rootNode.getElementsByTagName(tag);
}

function MyNnd_Find_TagExt(rootNode, tag, lowLimit, highLimit, getIndex)
{
	var nodes = MyNnd_Find_TagList(rootNode, tag);

	if (nodes == null)
	{
		return null;
	}

	if (lowLimit >= 0 && nodes.length < lowLimit)
	{
		MyNnd_Log("tag数量不正确:" + tag + ",nodes.length is " + nodes.length + ",lowLimit is " + lowLimit + ",highLimit is " + highLimit, null);
		return null;
	}

	if (highLimit >= 0 && nodes.length > highLimit)
	{
		MyNnd_Log("tag数量不正确:" + tag + ",nodes.length is " + nodes.length + ",lowLimit is " + lowLimit + ",highLimit is " + highLimit, null);
		return null;
	}

	return nodes[getIndex];
}

function MyNnd_Find_Tag(rootNode, tag)
{
	return MyNnd_Find_TagExt(rootNode, tag, 1, 1, 0);
}

function MyNnd_Find_ClassList(rootNode, className)
{
	return rootNode.getElementsByClassName(className);
}

function MyNnd_Find_ClassExt(rootNode, className, lowLimit, highLimit, getIndex)
{
	var nodes = MyNnd_Find_ClassList(rootNode, className);

	if (nodes == null)
	{
		return null;
	}

	if (lowLimit >= 0 && nodes.length < lowLimit)
	{
		MyNnd_Log("class数量不正确:" + className + ",nodes.length is " + nodes.length + ",lowLimit is " + lowLimit + ",highLimit is " + highLimit, null);
		return null;
	}

	if (highLimit >= 0 && nodes.length > highLimit)
	{
		MyNnd_Log("class数量不正确:" + className + ",nodes.length is " + nodes.length + ",lowLimit is " + lowLimit + ",highLimit is " + highLimit, null);
		return null;
	}

	return nodes[getIndex];
}

function MyNnd_Find_Class(rootNode, className)
{
	return MyNnd_Find_ClassExt(rootNode, className, 1, 1, 0);
}

function MyNnd_GetStyle(node, name)
{
	var style = node.getAttribute("style");
	if (style != undefined)
	{
		var obj = new Object();

		var attrs = style.split(";");
		for (var i = 0; i < attrs.length; i++)
		{
			var attr = attrs[i].replace(/(^\s*)|(\s*$)/g, "");
			if (attr.length != 0)
			{
				var attrArray = attr.split(":");
				var attrName = attrArray[0].replace(/(^\s*)|(\s*$)/g, "");
				var attrValue = attrArray[1].replace(/(^\s*)|(\s*$)/g, "");
				obj[attrName] = attrValue;
			}
		}

		if (obj[name] != undefined)
			return obj[name];
	}
	return undefined;
}

function MyNnd_Filter_Attr(nodes, attrName, attrValue, callback, required)
{
	var nodeArray = new Array();
	for (var i = 0; i < nodes.length; i++)
	{
		var node = nodes[i];
		if (node.getAttribute(attrName) == attrValue)
		{
			nodeArray[nodeArray.length] = node;
		}
	}
	if (nodeArray.length <= 0)
	{
		if (required)
		{
			MyNnd_Break("过滤失败:" + attrName + " is " + attrValue);
		}
		else
		{
			if (callback != null)
				callback(null);
		}
	}
	else
	{
		if (callback != null)
			callback(nodeArray);
	}
}

function MyNnd_GetNode(nodes, index, callback)
{
	if (index < nodes.length)
	{
		if (callback != null)
			callback(nodes[index], index);
	}
	else
	{
		if (callback != null)
			callback(null, index);
	}
}

function MyNnd_GetHtmlBody()
{
	return document.body.innerHTML;
}

/*
 * DOM json -------------------------------
 */

function MyNnd_GetDOMTree_Iterator_Json(parent)
{
	var root = new Object();

	root.nodeName = parent.nodeName;

	for (var i = 0; i < parent.attributes.length; i++)
	{
		var attr = parent.attributes[i];
		root[attr.name] = attr.value;
	}

	root.childNodes = new Array();

	for (var i = 0; i < parent.childNodes.length; i++)
	{
		var node = parent.childNodes[i];

		root.childNodes[root.childNodes.length++] = MyNnd_GetDOMTree_Iterator(node);
	}

	return root;
}

function MyNnd_GetDOMTree_Json()
{
	var root = MyNnd_GetDOMTree_Iterator_Json(document.body);
	return JSON.stringify(root);
}

/*
 * DOM xml -------------------------------
 */

function MyNnd_GetDOMTree_Iterator_Xml(parent, indent)
{
	var text = "";

	if (parent.nodeName == "#comment")
		return "";

	text += (MyNnd_GetIndentStr(indent) + "<" + parent.nodeName);

	if (parent.attributes)
	{
		for (var i = 0; i < parent.attributes.length; i++)
		{
			var attr = parent.attributes[i];

			if (attr.name == "'")
			{
				/* alert("error attr name : " + attr.name); */
			}
			else
			{
				text += (" " + attr.name + "=" + "\"" + attr.value.replace(/&/g, "&amp;").replace(/'/g, "&qpos;").replace(/"/g, "&quot;") + "\"");
			}
		}
	}
	text += ">\r\n";

	if (parent.nodeName == "IFRAME")
	{
		text += MyNnd_GetDOMTree_Iterator_Xml(parent.contentWindow.document.body, indent + 1);
	}
	else
	{
		if (parent.childNodes)
		{
			for (var i = 0; i < parent.childNodes.length; i++)
			{
				var node = parent.childNodes[i];

				if (node.nodeName == "SCRIPT" || node.nodeName == "STYLE")
					continue;

				if (node.nodeName == "#text")
				{
					text += (MyNnd_GetIndentStr(indent + 1) + node.nodeValue.replace(/&/g, "&amp;") + "\r\n");
				}
				else
				{
					text += MyNnd_GetDOMTree_Iterator_Xml(node, indent + 1);
				}
			}
		}
	}

	text += (MyNnd_GetIndentStr(indent) + "</" + parent.nodeName + ">" + "\r\n");

	return text;
}

function MyNnd_GetDOMTree_Xml()
{
	var text = "";
	try
	{
		text = MyNnd_GetDOMTree_Iterator_Xml(document.body, 0);
	}
	catch (err)
	{
		alert(err.message);
	}
	return text;
}

function MyNnd_DOM()
{
	MyNnd_CallCmd1("dom", MyNnd_GetDOMTree_Xml(), null);
}
