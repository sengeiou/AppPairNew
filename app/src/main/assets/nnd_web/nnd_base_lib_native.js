function MyNnd_CanJs()
{
	try
	{
		var str = nnd_native.getNative();
		if (str == "nnd_native")
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	catch (err)
	{
		return false;
	}
}

function MyNnd_EncodeStr(str)
{
	return MyNnd_Base64.encode(str).replace(/=/g, "#").replace(/\+/g, "!");
}

function MyNnd_DecodeStr(str)
{
	return MyNnd_Base64.decode(str.replace(/#/g, "=").replace(/!/g, "+"));
}

function MyNnd_MsgQueue_Class()
{
	this.queue = new Array();
	this.isRunning = false;
	this.currObj = null;

	this.unshift = function(obj)
	{
		this.queue.unshift(obj);
	};

	this.pop = function()
	{
		return this.queue.pop();
	};

	this.isEmpty = function()
	{
		return this.queue.length == 0;
	};

	this.run = function()
	{
		if (this.isRunning)
			return;

		this.isRunning = true;

		this.sendAMsg();
	};

	this.sendAMsg = function()
	{
		if (!this.isEmpty())
		{
			this.currObj = this.pop();

			if (this.currObj.func == "log")
			{
				window.open("msg://com.nukt.codelib/?func=log&info=" + MyNnd_EncodeStr(this.currObj.info));
			}
			else if (this.currObj.func == "toast")
			{
				window.open("msg://com.nukt.codelib/?func=toast&info=" + MyNnd_EncodeStr(this.currObj.info));
			}
			else if (this.currObj.func == "init")
			{
				window.open("msg://com.nukt.codelib/?func=init");
			}
			else if (this.currObj.func == "over")
			{
				window.open("msg://com.nukt.codelib/?func=over");
			}
			else if (this.currObj.func == "call")
			{
				window.open("msg://com.nukt.codelib/?func=call");
			}
			else if (this.currObj.func == "callStr")
			{
				window.open("msg://com.nukt.codelib/?func=callStr&str=" + MyNnd_EncodeStr(this.currObj.str));
			}
			else if (this.currObj.func == "callCmd")
			{
				window.open("msg://com.nukt.codelib/?func=callCmd&cmd=" + MyNnd_EncodeStr(this.currObj.cmd));
			}
			else if (this.currObj.func == "callCmd1")
			{
				window.open("msg://com.nukt.codelib/?func=callCmd1&cmd=" + MyNnd_EncodeStr(this.currObj.cmd) + "&p1=" + MyNnd_EncodeStr(this.currObj.p1));
			}
			else if (this.currObj.func == "callCmd2")
			{
				window.open("msg://com.nukt.codelib/?func=callCmd2&cmd=" + MyNnd_EncodeStr(this.currObj.cmd) + "&p1=" + MyNnd_EncodeStr(this.currObj.p1) + "&p2=" + MyNnd_EncodeStr(this.currObj.p2));
			}
			else if (this.currObj.func == "callCmd3")
			{
				window.open("msg://com.nukt.codelib/?func=callCmd3&cmd=" + MyNnd_EncodeStr(this.currObj.cmd) + "&p1=" + MyNnd_EncodeStr(this.currObj.p1) + "&p2=" + MyNnd_EncodeStr(this.currObj.p2) + "&p3=" + MyNnd_EncodeStr(this.currObj.p3));
			}
			else if (this.currObj.func == "callCmd4")
			{
				window.open("msg://com.nukt.codelib/?func=callCmd4&cmd=" + MyNnd_EncodeStr(this.currObj.cmd) + "&p1=" + MyNnd_EncodeStr(this.currObj.p1) + "&p2=" + MyNnd_EncodeStr(this.currObj.p2) + "&p3=" + MyNnd_EncodeStr(this.currObj.p3) + "&p4=" + MyNnd_EncodeStr(this.currObj.p4));
			}
			else if (this.currObj.func == "callCmd5")
			{
				window.open("msg://com.nukt.codelib/?func=callCmd5&cmd=" + MyNnd_EncodeStr(this.currObj.cmd) + "&p1=" + MyNnd_EncodeStr(this.currObj.p1) + "&p2=" + MyNnd_EncodeStr(this.currObj.p2) + "&p3=" + MyNnd_EncodeStr(this.currObj.p3) + "&p4=" + MyNnd_EncodeStr(this.currObj.p4) + "&p5=" + MyNnd_EncodeStr(this.currObj.p5));
			}
			else if (this.currObj.func == "callCmd6")
			{
				window.open("msg://com.nukt.codelib/?func=callCmd6&cmd=" + MyNnd_EncodeStr(this.currObj.cmd) + "&p1=" + MyNnd_EncodeStr(this.currObj.p1) + "&p2=" + MyNnd_EncodeStr(this.currObj.p2) + "&p3=" + MyNnd_EncodeStr(this.currObj.p3) + "&p4=" + MyNnd_EncodeStr(this.currObj.p4) + "&p5=" + MyNnd_EncodeStr(this.currObj.p5) + "&p6=" + MyNnd_EncodeStr(this.currObj.p6));
			}
			else if (this.currObj.func == "callCmd7")
			{
				window.open("msg://com.nukt.codelib/?func=callCmd7&cmd=" + MyNnd_EncodeStr(this.currObj.cmd) + "&p1=" + MyNnd_EncodeStr(this.currObj.p1) + "&p2=" + MyNnd_EncodeStr(this.currObj.p2) + "&p3=" + MyNnd_EncodeStr(this.currObj.p3) + "&p4=" + MyNnd_EncodeStr(this.currObj.p4) + "&p5=" + MyNnd_EncodeStr(this.currObj.p5) + "&p6=" + MyNnd_EncodeStr(this.currObj.p6) + "&p7=" + MyNnd_EncodeStr(this.currObj.p7));
			}
			else if (this.currObj.func == "callCmd8")
			{
				window.open("msg://com.nukt.codelib/?func=callCmd8&cmd=" + MyNnd_EncodeStr(this.currObj.cmd) + "&p1=" + MyNnd_EncodeStr(this.currObj.p1) + "&p2=" + MyNnd_EncodeStr(this.currObj.p2) + "&p3=" + MyNnd_EncodeStr(this.currObj.p3) + "&p4=" + MyNnd_EncodeStr(this.currObj.p4) + "&p5=" + MyNnd_EncodeStr(this.currObj.p5) + "&p6=" + MyNnd_EncodeStr(this.currObj.p6) + "&p7=" + MyNnd_EncodeStr(this.currObj.p7) + "&p8=" + MyNnd_EncodeStr(this.currObj.p8));
			}
			else if (this.currObj.func == "callCmd9")
			{
				window.open("msg://com.nukt.codelib/?func=callCmd9&cmd=" + MyNnd_EncodeStr(this.currObj.cmd) + "&p1=" + MyNnd_EncodeStr(this.currObj.p1) + "&p2=" + MyNnd_EncodeStr(this.currObj.p2) + "&p3=" + MyNnd_EncodeStr(this.currObj.p3) + "&p4=" + MyNnd_EncodeStr(this.currObj.p4) + "&p5=" + MyNnd_EncodeStr(this.currObj.p5) + "&p6=" + MyNnd_EncodeStr(this.currObj.p6) + "&p7=" + MyNnd_EncodeStr(this.currObj.p7) + "&p8=" + MyNnd_EncodeStr(this.currObj.p8) + "&p9="
						+ MyNnd_EncodeStr(this.currObj.p9));
			}
		}
		else
		{
			this.isRunning = false;
			this.currObj = null;
		}
	};

	this.feedback = function(ret)
	{
		if (this.currObj.callback != null)
			this.currObj.callback(ret);
		this.sendAMsg();
	};
}

var MyNnd_MsgQueue = new MyNnd_MsgQueue_Class();

function MyNnd_Log(info, callback)
{
	if (MyNnd_CanJs())
	{
		var str = nnd_native.log(info);
		setTimeout(function()
		{
			if (callback != null)
				callback(str);
		}, 1);
	}
	else
	{
		var obj = new Object();
		obj.func = "log";
		obj.info = info;
		obj.callback = callback;
		MyNnd_MsgQueue.unshift(obj);

		MyNnd_MsgQueue.run();
	}
}

function MyNnd_Toast(info, callback)
{
	if (MyNnd_CanJs())
	{
		var str = nnd_native.toast(info);
		setTimeout(function()
		{
			if (callback != null)
				callback(str);
		}, 1);
	}
	else
	{
		var obj = new Object();
		obj.func = "toast";
		obj.info = info;
		obj.callback = callback;
		MyNnd_MsgQueue.unshift(obj);

		MyNnd_MsgQueue.run();
	}
}

function MyNnd_Init(callback)
{
	if (MyNnd_CanJs())
	{
		var str = nnd_native.init();
		setTimeout(function()
		{
			if (callback != null)
				callback(str);
		}, 1);
	}
	else
	{
		var obj = new Object();
		obj.func = "init";
		obj.callback = callback;
		MyNnd_MsgQueue.unshift(obj);

		MyNnd_MsgQueue.run();
	}
}

function MyNnd_Over(callback)
{
	if (MyNnd_CanJs())
	{
		var str = nnd_native.over();
		setTimeout(function()
		{
			if (callback != null)
				callback(str);
		}, 1);
	}
	else
	{
		var obj = new Object();
		obj.func = "over";
		obj.callback = callback;
		MyNnd_MsgQueue.unshift(obj);

		MyNnd_MsgQueue.run();
	}
}

function MyNnd_Call(callback)
{
	if (MyNnd_CanJs())
	{
		var str = nnd_native.call();
		setTimeout(function()
		{
			if (callback != null)
				callback(str);
		}, 1);
	}
	else
	{
		var obj = new Object();
		obj.func = "call";
		obj.callback = callback;
		MyNnd_MsgQueue.unshift(obj);

		MyNnd_MsgQueue.run();
	}
}

function MyNnd_CallStr(str, callback)
{
	if (MyNnd_CanJs())
	{
		var str = nnd_native.callStr(str);
		setTimeout(function()
		{
			if (callback != null)
				callback(str);
		}, 1);
	}
	else
	{
		var obj = new Object();
		obj.func = "callStr";
		obj.str = str;
		obj.callback = callback;
		MyNnd_MsgQueue.unshift(obj);

		MyNnd_MsgQueue.run();
	}
}

function MyNnd_CallCmd(cmd, callback)
{
	if (MyNnd_CanJs())
	{
		var str = nnd_native.callCmd(cmd);
		setTimeout(function()
		{
			if (callback != null)
				callback(str);
		}, 1);
	}
	else
	{
		var obj = new Object();
		obj.func = "callCmd";
		obj.cmd = cmd;
		obj.callback = callback;
		MyNnd_MsgQueue.unshift(obj);

		MyNnd_MsgQueue.run();
	}
}

function MyNnd_CallCmd1(cmd, p1, callback)
{
	if (MyNnd_CanJs())
	{
		var str = nnd_native.callCmd1(cmd, p1);
		setTimeout(function()
		{
			if (callback != null)
				callback(str);
		}, 1);
	}
	else
	{
		var obj = new Object();
		obj.func = "callCmd1";
		obj.cmd = cmd;
		obj.p1 = p1;
		obj.callback = callback;
		MyNnd_MsgQueue.unshift(obj);

		MyNnd_MsgQueue.run();
	}
}

function MyNnd_CallCmd2(cmd, p1, p2, callback)
{
	if (MyNnd_CanJs())
	{
		var str = nnd_native.callCmd2(cmd, p1, p2);
		setTimeout(function()
		{
			if (callback != null)
				callback(str);
		}, 1);
	}
	else
	{
		var obj = new Object();
		obj.func = "callCmd2";
		obj.cmd = cmd;
		obj.p1 = p1;
		obj.p2 = p2;
		obj.callback = callback;
		MyNnd_MsgQueue.unshift(obj);

		MyNnd_MsgQueue.run();
	}
}

function MyNnd_CallCmd3(cmd, p1, p2, p3, callback)
{
	if (MyNnd_CanJs())
	{
		var str = nnd_native.callCmd3(cmd, p1, p2, p3);
		setTimeout(function()
		{
			if (callback != null)
				callback(str);
		}, 1);
	}
	else
	{
		var obj = new Object();
		obj.func = "callCmd3";
		obj.cmd = cmd;
		obj.p1 = p1;
		obj.p2 = p2;
		obj.p3 = p3;
		obj.callback = callback;
		MyNnd_MsgQueue.unshift(obj);

		MyNnd_MsgQueue.run();
	}
}

function MyNnd_CallCmd4(cmd, p1, p2, p3, p4, callback)
{
	if (MyNnd_CanJs())
	{
		var str = nnd_native.callCmd4(cmd, p1, p2, p3, p4);
		setTimeout(function()
		{
			if (callback != null)
				callback(str);
		}, 1);
	}
	else
	{
		var obj = new Object();
		obj.func = "callCmd4";
		obj.cmd = cmd;
		obj.p1 = p1;
		obj.p2 = p2;
		obj.p3 = p3;
		obj.p4 = p4;
		obj.callback = callback;
		MyNnd_MsgQueue.unshift(obj);

		MyNnd_MsgQueue.run();
	}
}

function MyNnd_CallCmd5(cmd, p1, p2, p3, p4, p5, callback)
{
	if (MyNnd_CanJs())
	{
		var str = nnd_native.callCmd5(cmd, p1, p2, p3, p4, p5);
		setTimeout(function()
		{
			if (callback != null)
				callback(str);
		}, 1);
	}
	else
	{
		var obj = new Object();
		obj.func = "callCmd5";
		obj.cmd = cmd;
		obj.p1 = p1;
		obj.p2 = p2;
		obj.p3 = p3;
		obj.p4 = p4;
		obj.p5 = p5;
		obj.callback = callback;
		MyNnd_MsgQueue.unshift(obj);

		MyNnd_MsgQueue.run();
	}
}

function MyNnd_CallCmd6(cmd, p1, p2, p3, p4, p5, p6, callback)
{
	if (MyNnd_CanJs())
	{
		var str = nnd_native.callCmd6(cmd, p1, p2, p3, p4, p5, p6);
		setTimeout(function()
		{
			if (callback != null)
				callback(str);
		}, 1);
	}
	else
	{
		var obj = new Object();
		obj.func = "callCmd6";
		obj.cmd = cmd;
		obj.p1 = p1;
		obj.p2 = p2;
		obj.p3 = p3;
		obj.p4 = p4;
		obj.p5 = p5;
		obj.p6 = p6;
		obj.callback = callback;
		MyNnd_MsgQueue.unshift(obj);

		MyNnd_MsgQueue.run();
	}
}

function MyNnd_CallCmd7(cmd, p1, p2, p3, p4, p5, p6, p7, callback)
{
	if (MyNnd_CanJs())
	{
		var str = nnd_native.callCmd7(cmd, p1, p2, p3, p4, p5, p6, p7);
		setTimeout(function()
		{
			if (callback != null)
				callback(str);
		}, 1);
	}
	else
	{
		var obj = new Object();
		obj.func = "callCmd7";
		obj.cmd = cmd;
		obj.p1 = p1;
		obj.p2 = p2;
		obj.p3 = p3;
		obj.p4 = p4;
		obj.p5 = p5;
		obj.p6 = p6;
		obj.p7 = p7;
		obj.callback = callback;
		MyNnd_MsgQueue.unshift(obj);

		MyNnd_MsgQueue.run();
	}
}

function MyNnd_CallCmd8(cmd, p1, p2, p3, p4, p5, p6, p7, p8, callback)
{
	if (MyNnd_CanJs())
	{
		var str = nnd_native.callCmd8(cmd, p1, p2, p3, p4, p5, p6, p7, p8);
		setTimeout(function()
		{
			if (callback != null)
				callback(str);
		}, 1);
	}
	else
	{
		var obj = new Object();
		obj.func = "callCmd8";
		obj.cmd = cmd;
		obj.p1 = p1;
		obj.p2 = p2;
		obj.p3 = p3;
		obj.p4 = p4;
		obj.p5 = p5;
		obj.p6 = p6;
		obj.p7 = p7;
		obj.p8 = p8;
		obj.callback = callback;
		MyNnd_MsgQueue.unshift(obj);

		MyNnd_MsgQueue.run();
	}
}

function MyNnd_CallCmd9(cmd, p1, p2, p3, p4, p5, p6, p7, p8, p9, callback)
{
	if (MyNnd_CanJs())
	{
		var str = nnd_native.callCmd9(cmd, p1, p2, p3, p4, p5, p6, p7, p8, p9);
		setTimeout(function()
		{
			if (callback != null)
				callback(str);
		}, 1);
	}
	else
	{
		var obj = new Object();
		obj.func = "callCmd9";
		obj.cmd = cmd;
		obj.p1 = p1;
		obj.p2 = p2;
		obj.p3 = p3;
		obj.p4 = p4;
		obj.p5 = p5;
		obj.p6 = p6;
		obj.p7 = p7;
		obj.p8 = p8;
		obj.p9 = p9;
		obj.callback = callback;
		MyNnd_MsgQueue.unshift(obj);

		MyNnd_MsgQueue.run();
	}
}
