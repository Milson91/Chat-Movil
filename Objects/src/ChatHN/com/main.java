package ChatHN.com;


import anywheresoftware.b4a.B4AMenuItem;
import android.app.Activity;
import android.os.Bundle;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.B4AActivity;
import anywheresoftware.b4a.ObjectWrapper;
import anywheresoftware.b4a.objects.ActivityWrapper;
import java.lang.reflect.InvocationTargetException;
import anywheresoftware.b4a.B4AUncaughtException;
import anywheresoftware.b4a.debug.*;
import java.lang.ref.WeakReference;

public class main extends Activity implements B4AActivity{
	public static main mostCurrent;
	static boolean afterFirstLayout;
	static boolean isFirst = true;
    private static boolean processGlobalsRun = false;
	BALayout layout;
	public static BA processBA;
	BA activityBA;
    ActivityWrapper _activity;
    java.util.ArrayList<B4AMenuItem> menuItems;
	public static final boolean fullScreen = false;
	public static final boolean includeTitle = false;
    public static WeakReference<Activity> previousOne;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        mostCurrent = this;
		if (processBA == null) {
			processBA = new BA(this.getApplicationContext(), null, null, "ChatHN.com", "ChatHN.com.main");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (main).");
				p.finish();
			}
		}
        processBA.setActivityPaused(true);
        processBA.runHook("oncreate", this, null);
		if (!includeTitle) {
        	this.getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE);
        }
        if (fullScreen) {
        	getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,   
        			android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
		
        processBA.sharedProcessBA.activityBA = null;
		layout = new BALayout(this);
		setContentView(layout);
		afterFirstLayout = false;
        WaitForLayout wl = new WaitForLayout();
        if (anywheresoftware.b4a.objects.ServiceHelper.StarterHelper.startFromActivity(this, processBA, wl, false))
		    BA.handler.postDelayed(wl, 5);

	}
	static class WaitForLayout implements Runnable {
		public void run() {
			if (afterFirstLayout)
				return;
			if (mostCurrent == null)
				return;
            
			if (mostCurrent.layout.getWidth() == 0) {
				BA.handler.postDelayed(this, 5);
				return;
			}
			mostCurrent.layout.getLayoutParams().height = mostCurrent.layout.getHeight();
			mostCurrent.layout.getLayoutParams().width = mostCurrent.layout.getWidth();
			afterFirstLayout = true;
			mostCurrent.afterFirstLayout();
		}
	}
	private void afterFirstLayout() {
        if (this != mostCurrent)
			return;
		activityBA = new BA(this, layout, processBA, "ChatHN.com", "ChatHN.com.main");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "ChatHN.com.main", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (main) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (main) Resume **");
        processBA.raiseEvent(null, "activity_resume");
        if (android.os.Build.VERSION.SDK_INT >= 11) {
			try {
				android.app.Activity.class.getMethod("invalidateOptionsMenu").invoke(this,(Object[]) null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	public void addMenuItem(B4AMenuItem item) {
		if (menuItems == null)
			menuItems = new java.util.ArrayList<B4AMenuItem>();
		menuItems.add(item);
	}
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
        try {
            if (processBA.subExists("activity_actionbarhomeclick")) {
                Class.forName("android.app.ActionBar").getMethod("setHomeButtonEnabled", boolean.class).invoke(
                    getClass().getMethod("getActionBar").invoke(this), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (processBA.runHook("oncreateoptionsmenu", this, new Object[] {menu}))
            return true;
		if (menuItems == null)
			return false;
		for (B4AMenuItem bmi : menuItems) {
			android.view.MenuItem mi = menu.add(bmi.title);
			if (bmi.drawable != null)
				mi.setIcon(bmi.drawable);
            if (android.os.Build.VERSION.SDK_INT >= 11) {
				try {
                    if (bmi.addToBar) {
				        android.view.MenuItem.class.getMethod("setShowAsAction", int.class).invoke(mi, 1);
                    }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			mi.setOnMenuItemClickListener(new B4AMenuItemsClickListener(bmi.eventName.toLowerCase(BA.cul)));
		}
        
		return true;
	}   
 @Override
 public boolean onOptionsItemSelected(android.view.MenuItem item) {
    if (item.getItemId() == 16908332) {
        processBA.raiseEvent(null, "activity_actionbarhomeclick");
        return true;
    }
    else
        return super.onOptionsItemSelected(item); 
}
@Override
 public boolean onPrepareOptionsMenu(android.view.Menu menu) {
    super.onPrepareOptionsMenu(menu);
    processBA.runHook("onprepareoptionsmenu", this, new Object[] {menu});
    return true;
    
 }
 protected void onStart() {
    super.onStart();
    processBA.runHook("onstart", this, null);
}
 protected void onStop() {
    super.onStop();
    processBA.runHook("onstop", this, null);
}
    public void onWindowFocusChanged(boolean hasFocus) {
       super.onWindowFocusChanged(hasFocus);
       if (processBA.subExists("activity_windowfocuschanged"))
           processBA.raiseEvent2(null, true, "activity_windowfocuschanged", false, hasFocus);
    }
	private class B4AMenuItemsClickListener implements android.view.MenuItem.OnMenuItemClickListener {
		private final String eventName;
		public B4AMenuItemsClickListener(String eventName) {
			this.eventName = eventName;
		}
		public boolean onMenuItemClick(android.view.MenuItem item) {
			processBA.raiseEventFromUI(item.getTitle(), eventName + "_click");
			return true;
		}
	}
    public static Class<?> getObject() {
		return main.class;
	}
    private Boolean onKeySubExist = null;
    private Boolean onKeyUpSubExist = null;
	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeydown", this, new Object[] {keyCode, event}))
            return true;
		if (onKeySubExist == null)
			onKeySubExist = processBA.subExists("activity_keypress");
		if (onKeySubExist) {
			if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK &&
					android.os.Build.VERSION.SDK_INT >= 18) {
				HandleKeyDelayed hk = new HandleKeyDelayed();
				hk.kc = keyCode;
				BA.handler.post(hk);
				return true;
			}
			else {
				boolean res = new HandleKeyDelayed().runDirectly(keyCode);
				if (res)
					return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	private class HandleKeyDelayed implements Runnable {
		int kc;
		public void run() {
			runDirectly(kc);
		}
		public boolean runDirectly(int keyCode) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keypress", false, keyCode);
			if (res == null || res == true) {
                return true;
            }
            else if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK) {
				finish();
				return true;
			}
            return false;
		}
		
	}
    @Override
	public boolean onKeyUp(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeyup", this, new Object[] {keyCode, event}))
            return true;
		if (onKeyUpSubExist == null)
			onKeyUpSubExist = processBA.subExists("activity_keyup");
		if (onKeyUpSubExist) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keyup", false, keyCode);
			if (res == null || res == true)
				return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	@Override
	public void onNewIntent(android.content.Intent intent) {
        super.onNewIntent(intent);
		this.setIntent(intent);
        processBA.runHook("onnewintent", this, new Object[] {intent});
	}
    @Override 
	public void onPause() {
		super.onPause();
        if (_activity == null)
            return;
        if (this != mostCurrent)
			return;
		anywheresoftware.b4a.Msgbox.dismiss(true);
        BA.LogInfo("** Activity (main) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        if (mostCurrent != null)
            processBA.raiseEvent2(_activity, true, "activity_pause", false, activityBA.activity.isFinishing());		
        processBA.setActivityPaused(true);
        mostCurrent = null;
        if (!activityBA.activity.isFinishing())
			previousOne = new WeakReference<Activity>(this);
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        processBA.runHook("onpause", this, null);
	}

	@Override
	public void onDestroy() {
        super.onDestroy();
		previousOne = null;
        processBA.runHook("ondestroy", this, null);
	}
    @Override 
	public void onResume() {
		super.onResume();
        mostCurrent = this;
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (activityBA != null) { //will be null during activity create (which waits for AfterLayout).
        	ResumeMessage rm = new ResumeMessage(mostCurrent);
        	BA.handler.post(rm);
        }
        processBA.runHook("onresume", this, null);
	}
    private static class ResumeMessage implements Runnable {
    	private final WeakReference<Activity> activity;
    	public ResumeMessage(Activity activity) {
    		this.activity = new WeakReference<Activity>(activity);
    	}
		public void run() {
            main mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (main) Resume **");
            if (mc != mostCurrent)
                return;
		    processBA.raiseEvent(mc._activity, "activity_resume", (Object[])null);
		}
    }
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	      android.content.Intent data) {
		processBA.onActivityResult(requestCode, resultCode, data);
        processBA.runHook("onactivityresult", this, new Object[] {requestCode, resultCode});
	}
	private static void initializeGlobals() {
		processBA.raiseEvent2(null, true, "globals", false, (Object[])null);
	}
    public void onRequestPermissionsResult(int requestCode,
        String permissions[], int[] grantResults) {
        for (int i = 0;i < permissions.length;i++) {
            Object[] o = new Object[] {permissions[i], grantResults[i] == 0};
            processBA.raiseEventFromDifferentThread(null,null, 0, "activity_permissionresult", true, o);
        }
            
    }

public anywheresoftware.b4a.keywords.Common __c = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtuser = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtpass = null;
public anywheresoftware.b4a.objects.IME _ime = null;
public anywheresoftware.b4a.objects.RuntimePermissions _rp = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chb = null;
public ChatHN.com.generales _generales = null;
public ChatHN.com.chat _chat = null;
public ChatHN.com.starter _starter = null;
public ChatHN.com.menu _menu = null;
public ChatHN.com.registrar _registrar = null;
public ChatHN.com.firebasemessaging _firebasemessaging = null;
public ChatHN.com.httputils2service _httputils2service = null;

public static boolean isAnyActivityVisible() {
    boolean vis = false;
vis = vis | (main.mostCurrent != null);
vis = vis | (chat.mostCurrent != null);
vis = vis | (menu.mostCurrent != null);
vis = vis | (registrar.mostCurrent != null);
return vis;}
public static String  _activity_create(boolean _firsttime) throws Exception{
anywheresoftware.b4a.objects.collections.List _listuser = null;
String _valor = "";
 //BA.debugLineNum = 36;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 38;BA.debugLine="StopService(Starter)";
anywheresoftware.b4a.keywords.Common.StopService(processBA,(Object)(mostCurrent._starter.getObject()));
 //BA.debugLineNum = 39;BA.debugLine="Activity.LoadLayout(\"lytLogin\")";
mostCurrent._activity.LoadLayout("lytLogin",mostCurrent.activityBA);
 //BA.debugLineNum = 40;BA.debugLine="IME.Initialize(\"\")";
mostCurrent._ime.Initialize("");
 //BA.debugLineNum = 41;BA.debugLine="IME.SetLengthFilter(TxtPass, 4)";
mostCurrent._ime.SetLengthFilter((android.widget.EditText)(mostCurrent._txtpass.getObject()),(int) (4));
 //BA.debugLineNum = 42;BA.debugLine="IME.Initialize(\"\")";
mostCurrent._ime.Initialize("");
 //BA.debugLineNum = 43;BA.debugLine="IME.SetLengthFilter(TxtUser, 8)";
mostCurrent._ime.SetLengthFilter((android.widget.EditText)(mostCurrent._txtuser.getObject()),(int) (8));
 //BA.debugLineNum = 45;BA.debugLine="Try";
try { //BA.debugLineNum = 46;BA.debugLine="If File.Exists(File.DirInternal,\"chatuser.txt\")";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"chatuser.txt")) { 
 //BA.debugLineNum = 47;BA.debugLine="Dim listUser As List";
_listuser = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 48;BA.debugLine="Dim VALOR As String";
_valor = "";
 //BA.debugLineNum = 49;BA.debugLine="listUser=File.ReadList(File.DirInternal,\"chatus";
_listuser = anywheresoftware.b4a.keywords.Common.File.ReadList(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"chatuser.txt");
 //BA.debugLineNum = 50;BA.debugLine="VALOR=listUser.Get(0)";
_valor = BA.ObjectToString(_listuser.Get((int) (0)));
 //BA.debugLineNum = 51;BA.debugLine="If(VALOR.Length>1)Then";
if ((_valor.length()>1)) { 
 //BA.debugLineNum = 52;BA.debugLine="Chb.Checked=True";
mostCurrent._chb.setChecked(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 53;BA.debugLine="Generales.Usuario=listUser.Get(0)";
mostCurrent._generales._usuario /*String*/  = BA.ObjectToString(_listuser.Get((int) (0)));
 //BA.debugLineNum = 54;BA.debugLine="StartService(Starter)";
anywheresoftware.b4a.keywords.Common.StartService(processBA,(Object)(mostCurrent._starter.getObject()));
 //BA.debugLineNum = 55;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 56;BA.debugLine="StartActivity(Menu)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._menu.getObject()));
 };
 };
 } 
       catch (Exception e22) {
			processBA.setLastException(e22); //BA.debugLineNum = 61;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("5131097",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 63;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 69;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 71;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 65;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 67;BA.debugLine="End Sub";
return "";
}
public static void  _btnlogin_click() throws Exception{
ResumableSub_BtnLogin_Click rsub = new ResumableSub_BtnLogin_Click(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_BtnLogin_Click extends BA.ResumableSub {
public ResumableSub_BtnLogin_Click(ChatHN.com.main parent) {
this.parent = parent;
}
ChatHN.com.main parent;
String _response = "";
ChatHN.com.httpjob _httplogin = null;
ChatHN.com.httpjob _jobserver = null;
anywheresoftware.b4a.objects.collections.JSONParser _json = null;
anywheresoftware.b4a.objects.collections.Map _m = null;
String _valorcod = "";
anywheresoftware.b4a.objects.collections.List _lis = null;
anywheresoftware.b4a.objects.collections.List _lista = null;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
try {

        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 81;BA.debugLine="Generales.Usuario=TxtUser.Text.Trim";
parent.mostCurrent._generales._usuario /*String*/  = parent.mostCurrent._txtuser.getText().trim();
 //BA.debugLineNum = 82;BA.debugLine="Generales.Pass=TxtPass.Text.Trim";
parent.mostCurrent._generales._pass /*String*/  = parent.mostCurrent._txtpass.getText().trim();
 //BA.debugLineNum = 84;BA.debugLine="If(TxtUser.Text.EqualsIgnoreCase(\"\")) Then";
if (true) break;

case 1:
//if
this.state = 4;
if ((parent.mostCurrent._txtuser.getText().equalsIgnoreCase(""))) { 
this.state = 3;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 85;BA.debugLine="ToastMessageShow(\"Ingrese un usuario\",False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Ingrese un usuario"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 86;BA.debugLine="Return";
if (true) return ;
 if (true) break;
;
 //BA.debugLineNum = 88;BA.debugLine="If(TxtPass.Text.EqualsIgnoreCase(\"\")) Then";

case 4:
//if
this.state = 7;
if ((parent.mostCurrent._txtpass.getText().equalsIgnoreCase(""))) { 
this.state = 6;
}if (true) break;

case 6:
//C
this.state = 7;
 //BA.debugLineNum = 89;BA.debugLine="ToastMessageShow(\"Ingrese una clave\",False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Ingrese una clave"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 90;BA.debugLine="Return";
if (true) return ;
 if (true) break;
;
 //BA.debugLineNum = 93;BA.debugLine="Try";

case 7:
//try
this.state = 40;
this.catchState = 39;
this.state = 9;
if (true) break;

case 9:
//C
this.state = 10;
this.catchState = 39;
 //BA.debugLineNum = 94;BA.debugLine="ProgressDialogShow2(\"Espere por favor...\"& CRLF";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence("Espere por favor..."+anywheresoftware.b4a.keywords.Common.CRLF+"Inciando Registro..."),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 95;BA.debugLine="Dim response As String";
_response = "";
 //BA.debugLineNum = 96;BA.debugLine="Dim HttpLogin As HttpJob";
_httplogin = new ChatHN.com.httpjob();
 //BA.debugLineNum = 97;BA.debugLine="HttpLogin.Initialize(\"HttpLogin\",Me)";
_httplogin._initialize /*String*/ (processBA,"HttpLogin",main.getObject());
 //BA.debugLineNum = 98;BA.debugLine="HttpLogin.PostString(Generales.Url& \"ConsultaMas";
_httplogin._poststring /*String*/ (parent.mostCurrent._generales._url /*String*/ +"ConsultaMaster2","Base=cthTUsuarios&Esquema=INTRANET&ValParametro=SI&Paremetros=[{'Key':'usuario','Valor':'"+parent.mostCurrent._txtuser.getText().trim()+"'},{'Key':'clave','Valor':'"+parent.mostCurrent._txtpass.getText().trim()+"'}]'");
 //BA.debugLineNum = 99;BA.debugLine="HttpLogin.GetRequest.SetContentType(\"application";
_httplogin._getrequest /*anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpRequest*/ ().SetContentType("application/x-www-form-urlencoded");
 //BA.debugLineNum = 100;BA.debugLine="HttpLogin.GetRequest.SetHeader(\"Accept\",\"applica";
_httplogin._getrequest /*anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpRequest*/ ().SetHeader("Accept","application/json");
 //BA.debugLineNum = 101;BA.debugLine="HttpLogin.GetRequest.SetHeader(\"Authorization\",\"";
_httplogin._getrequest /*anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpRequest*/ ().SetHeader("Authorization","AIzaSyDYOkkTJiTPVd2U7EaTOAwhcN9ySH6GoHxOIYOM");
 //BA.debugLineNum = 102;BA.debugLine="HttpLogin.GetRequest.Timeout=20000";
_httplogin._getrequest /*anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpRequest*/ ().setTimeout((int) (20000));
 //BA.debugLineNum = 103;BA.debugLine="Wait For (HttpLogin) JobDone(JobServer As HttpJo";
anywheresoftware.b4a.keywords.Common.WaitFor("jobdone", processBA, this, (Object)(_httplogin));
this.state = 41;
return;
case 41:
//C
this.state = 10;
_jobserver = (ChatHN.com.httpjob) result[0];
;
 //BA.debugLineNum = 104;BA.debugLine="If JobServer.Success = True Then";
if (true) break;

case 10:
//if
this.state = 37;
if (_jobserver._success /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
this.state = 12;
}else {
this.state = 36;
}if (true) break;

case 12:
//C
this.state = 13;
 //BA.debugLineNum = 105;BA.debugLine="response=JobServer.GetString";
_response = _jobserver._getstring /*String*/ ();
 //BA.debugLineNum = 106;BA.debugLine="Dim json As JSONParser";
_json = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 107;BA.debugLine="json.Initialize(response)";
_json.Initialize(_response);
 //BA.debugLineNum = 108;BA.debugLine="Dim m As Map = json.NextObject";
_m = new anywheresoftware.b4a.objects.collections.Map();
_m = _json.NextObject();
 //BA.debugLineNum = 109;BA.debugLine="m= m.Get(\"wsrespuesta\")";
_m.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(_m.Get((Object)("wsrespuesta"))));
 //BA.debugLineNum = 110;BA.debugLine="Dim ValorCod As String= m.Get(\"codError\")";
_valorcod = BA.ObjectToString(_m.Get((Object)("codError")));
 //BA.debugLineNum = 111;BA.debugLine="If(ValorCod.EqualsIgnoreCase(\"0\"))Then";
if (true) break;

case 13:
//if
this.state = 34;
if ((_valorcod.equalsIgnoreCase("0"))) { 
this.state = 15;
}else {
this.state = 33;
}if (true) break;

case 15:
//C
this.state = 16;
 //BA.debugLineNum = 112;BA.debugLine="Dim lis As List=m.Get(\"data\")";
_lis = new anywheresoftware.b4a.objects.collections.List();
_lis.setObject((java.util.List)(_m.Get((Object)("data"))));
 //BA.debugLineNum = 113;BA.debugLine="m=lis.Get(0)";
_m.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(_lis.Get((int) (0))));
 //BA.debugLineNum = 114;BA.debugLine="If(TxtUser.Text.Trim.EqualsIgnoreCase(m.Get(\"p";
if (true) break;

case 16:
//if
this.state = 31;
if ((parent.mostCurrent._txtuser.getText().trim().equalsIgnoreCase(BA.ObjectToString(_m.Get((Object)("pusuario")))))) { 
this.state = 18;
}if (true) break;

case 18:
//C
this.state = 19;
 //BA.debugLineNum = 115;BA.debugLine="ToastMessageShow(\"Bienvenido...\",False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Bienvenido..."),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 116;BA.debugLine="If(Chb.Checked) Then";
if (true) break;

case 19:
//if
this.state = 30;
if ((parent.mostCurrent._chb.getChecked())) { 
this.state = 21;
}else {
this.state = 29;
}if (true) break;

case 21:
//C
this.state = 22;
 //BA.debugLineNum = 117;BA.debugLine="If File.Exists(File.DirInternal,\"chatuser.tx";
if (true) break;

case 22:
//if
this.state = 27;
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"chatuser.txt")) { 
this.state = 24;
}else {
this.state = 26;
}if (true) break;

case 24:
//C
this.state = 27;
 //BA.debugLineNum = 118;BA.debugLine="File.Delete(File.DirInternal,\"chatuser.txt\"";
anywheresoftware.b4a.keywords.Common.File.Delete(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"chatuser.txt");
 //BA.debugLineNum = 119;BA.debugLine="Dim lista As List";
_lista = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 120;BA.debugLine="lista.Initialize";
_lista.Initialize();
 //BA.debugLineNum = 121;BA.debugLine="lista.Add(TxtUser.Text)";
_lista.Add((Object)(parent.mostCurrent._txtuser.getText()));
 //BA.debugLineNum = 122;BA.debugLine="lista.Add(True)";
_lista.Add((Object)(anywheresoftware.b4a.keywords.Common.True));
 //BA.debugLineNum = 123;BA.debugLine="File.WriteList(File.DirInternal,\"chatuser.t";
anywheresoftware.b4a.keywords.Common.File.WriteList(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"chatuser.txt",_lista);
 if (true) break;

case 26:
//C
this.state = 27;
 //BA.debugLineNum = 125;BA.debugLine="Dim lista As List";
_lista = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 126;BA.debugLine="lista.Initialize";
_lista.Initialize();
 //BA.debugLineNum = 127;BA.debugLine="lista.Add(TxtUser.Text)";
_lista.Add((Object)(parent.mostCurrent._txtuser.getText()));
 //BA.debugLineNum = 128;BA.debugLine="lista.Add(True)";
_lista.Add((Object)(anywheresoftware.b4a.keywords.Common.True));
 //BA.debugLineNum = 129;BA.debugLine="File.WriteList(File.DirInternal,\"chatuser.t";
anywheresoftware.b4a.keywords.Common.File.WriteList(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"chatuser.txt",_lista);
 if (true) break;

case 27:
//C
this.state = 30;
;
 if (true) break;

case 29:
//C
this.state = 30;
 //BA.debugLineNum = 132;BA.debugLine="Dim lista As List";
_lista = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 133;BA.debugLine="lista.Initialize";
_lista.Initialize();
 //BA.debugLineNum = 134;BA.debugLine="lista.Add(TxtUser.Text)";
_lista.Add((Object)(parent.mostCurrent._txtuser.getText()));
 //BA.debugLineNum = 135;BA.debugLine="lista.Add(False)";
_lista.Add((Object)(anywheresoftware.b4a.keywords.Common.False));
 //BA.debugLineNum = 136;BA.debugLine="File.WriteList(File.DirInternal,\"chatuser.tx";
anywheresoftware.b4a.keywords.Common.File.WriteList(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"chatuser.txt",_lista);
 if (true) break;

case 30:
//C
this.state = 31;
;
 //BA.debugLineNum = 138;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 139;BA.debugLine="TxtUser.Text=\"\"";
parent.mostCurrent._txtuser.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 140;BA.debugLine="TxtPass.Text=\"\"";
parent.mostCurrent._txtpass.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 141;BA.debugLine="StartService(Starter)";
anywheresoftware.b4a.keywords.Common.StartService(processBA,(Object)(parent.mostCurrent._starter.getObject()));
 //BA.debugLineNum = 142;BA.debugLine="StartActivity(Menu)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(parent.mostCurrent._menu.getObject()));
 if (true) break;

case 31:
//C
this.state = 34;
;
 if (true) break;

case 33:
//C
this.state = 34;
 //BA.debugLineNum = 147;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 148;BA.debugLine="Msgbox(\"Error al iniciar valide sus credencial";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Error al iniciar valide sus credenciales."),BA.ObjectToCharSequence("Notificatión!!!"),mostCurrent.activityBA);
 if (true) break;

case 34:
//C
this.state = 37;
;
 if (true) break;

case 36:
//C
this.state = 37;
 //BA.debugLineNum = 151;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 152;BA.debugLine="Msgbox(\"Error de conexión\"&CRLF&\"Valide sus dat";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Error de conexión"+anywheresoftware.b4a.keywords.Common.CRLF+"Valide sus datos moviles"),BA.ObjectToCharSequence("Notificación!!!"),mostCurrent.activityBA);
 if (true) break;

case 37:
//C
this.state = 40;
;
 if (true) break;

case 39:
//C
this.state = 40;
this.catchState = 0;
 //BA.debugLineNum = 155;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 156;BA.debugLine="Msgbox(\"Error al iniciar valide sus credenciales";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Error al iniciar valide sus credenciales."),BA.ObjectToCharSequence("Notificatión!!!"),mostCurrent.activityBA);
 if (true) break;
if (true) break;

case 40:
//C
this.state = -1;
this.catchState = 0;
;
 //BA.debugLineNum = 159;BA.debugLine="End Sub";
if (true) break;
}} 
       catch (Exception e0) {
			
if (catchState == 0)
    throw e0;
else {
    state = catchState;
processBA.setLastException(e0);}
            }
        }
    }
}
public static void  _jobdone(ChatHN.com.httpjob _jobserver) throws Exception{
}
public static void  _chb_checkedchange(boolean _checked) throws Exception{
ResumableSub_Chb_CheckedChange rsub = new ResumableSub_Chb_CheckedChange(null,_checked);
rsub.resume(processBA, null);
}
public static class ResumableSub_Chb_CheckedChange extends BA.ResumableSub {
public ResumableSub_Chb_CheckedChange(ChatHN.com.main parent,boolean _checked) {
this.parent = parent;
this._checked = _checked;
}
ChatHN.com.main parent;
boolean _checked;
String _permission = "";
boolean _presult = false;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 167;BA.debugLine="rp.CheckAndRequest(rp.PERMISSION_WRITE_EXTERNAL_S";
parent.mostCurrent._rp.CheckAndRequest(processBA,parent.mostCurrent._rp.PERMISSION_WRITE_EXTERNAL_STORAGE);
 //BA.debugLineNum = 168;BA.debugLine="Wait For Activity_PermissionResult (Permission As";
anywheresoftware.b4a.keywords.Common.WaitFor("activity_permissionresult", processBA, this, null);
this.state = 5;
return;
case 5:
//C
this.state = 1;
_permission = (String) result[0];
_presult = (Boolean) result[1];
;
 //BA.debugLineNum = 169;BA.debugLine="If PResult = False Then";
if (true) break;

case 1:
//if
this.state = 4;
if (_presult==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 3;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 170;BA.debugLine="MsgboxAsync(\"No tiene permiso para utilizar la c";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("No tiene permiso para utilizar la camara del dispositivo"),BA.ObjectToCharSequence(""),processBA);
 //BA.debugLineNum = 171;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 4:
//C
this.state = -1;
;
 //BA.debugLineNum = 173;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _activity_permissionresult(String _permission,boolean _presult) throws Exception{
}
public static boolean  _checkforgoogleplayservices() throws Exception{
anywheresoftware.b4j.object.JavaObject _googleapiavailablity = null;
anywheresoftware.b4j.object.JavaObject _context = null;
 //BA.debugLineNum = 175;BA.debugLine="Sub CheckForGooglePlayServices As Boolean";
 //BA.debugLineNum = 176;BA.debugLine="Dim GoogleApiAvailablity As JavaObject";
_googleapiavailablity = new anywheresoftware.b4j.object.JavaObject();
 //BA.debugLineNum = 177;BA.debugLine="GoogleApiAvailablity = GoogleApiAvailablity.Initi";
_googleapiavailablity.setObject((java.lang.Object)(_googleapiavailablity.InitializeStatic("com.google.android.gms.common.GoogleApiAvailability").RunMethod("getInstance",(Object[])(anywheresoftware.b4a.keywords.Common.Null))));
 //BA.debugLineNum = 178;BA.debugLine="Dim context As JavaObject";
_context = new anywheresoftware.b4j.object.JavaObject();
 //BA.debugLineNum = 179;BA.debugLine="context.InitializeContext";
_context.InitializeContext(processBA);
 //BA.debugLineNum = 180;BA.debugLine="If GoogleApiAvailablity.RunMethod(\"isGooglePlaySe";
if ((_googleapiavailablity.RunMethod("isGooglePlayServicesAvailable",new Object[]{(Object)(_context.getObject())})).equals((Object)(0)) == false) { 
 //BA.debugLineNum = 181;BA.debugLine="GoogleApiAvailablity.RunMethod(\"makeGooglePlaySe";
_googleapiavailablity.RunMethod("makeGooglePlayServicesAvailable",new Object[]{(Object)(_context.getObject())});
 //BA.debugLineNum = 182;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 184;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 187;BA.debugLine="End Sub";
return false;
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 25;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 29;BA.debugLine="Private TxtUser As EditText";
mostCurrent._txtuser = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Private TxtPass As EditText";
mostCurrent._txtpass = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Dim IME As IME";
mostCurrent._ime = new anywheresoftware.b4a.objects.IME();
 //BA.debugLineNum = 32;BA.debugLine="Dim rp As RuntimePermissions";
mostCurrent._rp = new anywheresoftware.b4a.objects.RuntimePermissions();
 //BA.debugLineNum = 33;BA.debugLine="Private Chb As CheckBox";
mostCurrent._chb = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 34;BA.debugLine="End Sub";
return "";
}
public static String  _lblregistre_click() throws Exception{
 //BA.debugLineNum = 161;BA.debugLine="Sub LblRegistre_Click";
 //BA.debugLineNum = 162;BA.debugLine="StartActivity(Registrar)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._registrar.getObject()));
 //BA.debugLineNum = 163;BA.debugLine="End Sub";
return "";
}

public static void initializeProcessGlobals() {
    
    if (main.processGlobalsRun == false) {
	    main.processGlobalsRun = true;
		try {
		        main._process_globals();
generales._process_globals();
chat._process_globals();
starter._process_globals();
menu._process_globals();
registrar._process_globals();
firebasemessaging._process_globals();
httputils2service._process_globals();
		
        } catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 19;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 23;BA.debugLine="End Sub";
return "";
}
}
