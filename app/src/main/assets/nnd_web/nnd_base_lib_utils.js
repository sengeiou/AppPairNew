/**
 * open activity -------------------------------
 */

function MyNnd_OpenActivity(callback)
{
	window.open("nnd://com.nukt.codelib/?num=456&info=abc&is=true");
	setTimeout(function()
	{
		if (callback != null)
			callback();
	}, 10);
}

/*
 * click -------------------------------
 */

function MyNnd_MouseDown_Delay_Callback(node, delayTime, callback)
{
	if (node != null)
	{
		var e = document.createEvent("MouseEvents");
		e.initEvent("mousedown", true, true);
		node.dispatchEvent(e);

		setTimeout(function()
		{
			if (callback != null)
				callback();
		}, delayTime);
	}
}

function MyNnd_Click(node)
{
	if (node != null)
	{
		var e = document.createEvent("MouseEvents");
		e.initEvent("click", true, true);
		node.dispatchEvent(e);
	}
}

function MyNnd_Click_Callback(node, callback)
{
	if (node != null)
	{
		var e = document.createEvent("MouseEvents");
		e.initEvent("click", true, true);
		node.dispatchEvent(e);

		setTimeout(function()
		{
			if (callback != null)
				callback();
		}, 10);
	}
}

function MyNnd_Click_Delay_Callback(node, delayTime, callback)
{
	if (node != null)
	{
		var e = document.createEvent("MouseEvents");
		e.initEvent("click", true, true);
		node.dispatchEvent(e);

		setTimeout(function()
		{
			if (callback != null)
				callback();
		}, delayTime);
	}
}

function MyNnd_Delay_Click_Callback(delayTime, node, callback)
{
	MyNnd_Delay(delayTime, function()
	{
		MyNnd_Click_Callback(node, callback);
	});
}

/*
 * delay -------------------------------
 */

function MyNnd_Delay(delayTime, callback)
{
	setTimeout(function()
	{
		if (callback != null)
			callback();
	}, delayTime);
}

function MyNnd_Delay_Param1(delayTime, callback, param1)
{
	setTimeout(function()
	{
		if (callback != null)
			callback(param1);
	}, delayTime);
}

function MyNnd_Delay_Param2(delayTime, callback, param1, param2)
{
	setTimeout(function()
	{
		if (callback != null)
			callback(param1, param2);
	}, delayTime);
}

/*
 * tools -------------------------------
 */

function MyNnd_Str_StartsWith(str, prefix)
{
	if (str.indexOf(prefix) == 0)
	{
		return true;
	}
	else
	{
		return false;
	}
}

function MyNnd_GetIndentStr(indent)
{
	var str = "";
	for (var i = 0; i < indent; i++)
	{
		str += "\t";
	}
	return str;
}

/*
 * break -------------------------------
 */

function MyNnd_Break(info)
{
	MyNnd_Log(info, function(ret)
	{
		MyNnd_Over(null);
	});
}

/*
 * random -------------------------------
 */

/*
 * [from, to)
 */
function MyNnd_RandomImpl(from, to)
{
	return Math.floor(Math.random() * (to - from)) + from;
}

function MyNnd_Random(length)
{
	return MyNnd_RandomImpl(0, length);
}

function MyNnd_Random_Node(nodeArray)
{
	return nodeArray[MyNnd_Random(nodeArray.length)];
}

function MyNnd_Random_Do(probability, callback, required)
{
	if (MyNnd_Random(100) < probability)
	{
		if (callback != null)
			callback(true);
	}
	else
	{
		if (required)
		{
			MyNnd_Break("概率性终止:" + probability);
		}
		else
		{
			if (callback != null)
				callback(false);
		}
	}
}
