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

public class menu extends Activity implements B4AActivity{
	public static menu mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "ChatHN.com", "ChatHN.com.menu");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (menu).");
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
		activityBA = new BA(this, layout, processBA, "ChatHN.com", "ChatHN.com.menu");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "ChatHN.com.menu", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (menu) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (menu) Resume **");
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
		return menu.class;
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
        BA.LogInfo("** Activity (menu) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            menu mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (menu) Resume **");
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
public anywheresoftware.b4a.objects.ListViewWrapper _listview1 = null;
public ChatHN.com.main _main = null;
public ChatHN.com.generales _generales = null;
public ChatHN.com.chat _chat = null;
public ChatHN.com.starter _starter = null;
public ChatHN.com.registrar _registrar = null;
public ChatHN.com.firebasemessaging _firebasemessaging = null;
public ChatHN.com.httputils2service _httputils2service = null;

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 19;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 21;BA.debugLine="Activity.LoadLayout(\"lytMenu\")";
mostCurrent._activity.LoadLayout("lytMenu",mostCurrent.activityBA);
 //BA.debugLineNum = 25;BA.debugLine="cargar";
_cargar();
 //BA.debugLineNum = 26;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 32;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 34;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 28;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 30;BA.debugLine="End Sub";
return "";
}
public static void  _button1_click() throws Exception{
ResumableSub_Button1_Click rsub = new ResumableSub_Button1_Click(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_Button1_Click extends BA.ResumableSub {
public ResumableSub_Button1_Click(ChatHN.com.menu parent) {
this.parent = parent;
}
ChatHN.com.menu parent;
String _topic = "";
anywheresoftware.b4a.objects.collections.Map _m = null;
anywheresoftware.b4a.objects.collections.Map _data = null;
anywheresoftware.b4a.objects.collections.JSONParser.JSONGenerator _json = null;
String _response = "";
ChatHN.com.httpjob _httpmenu = null;
ChatHN.com.httpjob _jobserver = null;

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
 //BA.debugLineNum = 89;BA.debugLine="Try";
if (true) break;

case 1:
//try
this.state = 10;
this.catchState = 9;
this.state = 3;
if (true) break;

case 3:
//C
this.state = 4;
this.catchState = 9;
 //BA.debugLineNum = 90;BA.debugLine="Dim Topic As  String=\"general\"";
_topic = "general";
 //BA.debugLineNum = 91;BA.debugLine="Dim m As Map=CreateMap(\"to\": $\"/topics/${Topic}\"";
_m = new anywheresoftware.b4a.objects.collections.Map();
_m = anywheresoftware.b4a.keywords.Common.createMap(new Object[] {(Object)("to"),(Object)(("/topics/"+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(_topic))+""))});
 //BA.debugLineNum = 92;BA.debugLine="Dim data As Map = CreateMap(\"title\": \"Hola Mundo";
_data = new anywheresoftware.b4a.objects.collections.Map();
_data = anywheresoftware.b4a.keywords.Common.createMap(new Object[] {(Object)("title"),(Object)("Hola Mundo"),(Object)("body"),(Object)("Buen dia")});
 //BA.debugLineNum = 93;BA.debugLine="m.Put(\"data\",data)";
_m.Put((Object)("data"),(Object)(_data.getObject()));
 //BA.debugLineNum = 95;BA.debugLine="Dim json As JSONGenerator";
_json = new anywheresoftware.b4a.objects.collections.JSONParser.JSONGenerator();
 //BA.debugLineNum = 96;BA.debugLine="json.Initialize(m)";
_json.Initialize(_m);
 //BA.debugLineNum = 97;BA.debugLine="Dim response As String";
_response = "";
 //BA.debugLineNum = 98;BA.debugLine="Dim HttpMenu As HttpJob";
_httpmenu = new ChatHN.com.httpjob();
 //BA.debugLineNum = 99;BA.debugLine="HttpMenu.Initialize(\"HttpMenu\",Me)";
_httpmenu._initialize /*String*/ (processBA,"HttpMenu",menu.getObject());
 //BA.debugLineNum = 100;BA.debugLine="HttpMenu.PostString(\"https://fcm.googleapis.com/";
_httpmenu._poststring /*String*/ ("https://fcm.googleapis.com/fcm/send",_json.ToString());
 //BA.debugLineNum = 101;BA.debugLine="HttpMenu.GetRequest.SetContentType(\"application/";
_httpmenu._getrequest /*anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpRequest*/ ().SetContentType("application/json");
 //BA.debugLineNum = 103;BA.debugLine="HttpMenu.GetRequest.SetHeader(\"Authorization\",\"k";
_httpmenu._getrequest /*anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpRequest*/ ().SetHeader("Authorization","key=AAAAqN4BOMc:APA91bGQ2A8TKizIZMaVmoFBFdrK8q699eGff4j4igdloe-tEQzx1f7QDTTM2LOXbn7AodY5IWhZcLlvk4Zjd1Uv8B2l6hyEklEFtb0OHMiDoues6Ae9JM8JMsAuzQqjfEH6GNTCELaU");
 //BA.debugLineNum = 104;BA.debugLine="HttpMenu.GetRequest.Timeout=20000";
_httpmenu._getrequest /*anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpRequest*/ ().setTimeout((int) (20000));
 //BA.debugLineNum = 105;BA.debugLine="Wait For (HttpMenu) JobDone(JobServer As HttpJob";
anywheresoftware.b4a.keywords.Common.WaitFor("jobdone", processBA, this, (Object)(_httpmenu));
this.state = 11;
return;
case 11:
//C
this.state = 4;
_jobserver = (ChatHN.com.httpjob) result[0];
;
 //BA.debugLineNum = 106;BA.debugLine="If JobServer.Success = True Then";
if (true) break;

case 4:
//if
this.state = 7;
if (_jobserver._success /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
this.state = 6;
}if (true) break;

case 6:
//C
this.state = 7;
 //BA.debugLineNum = 107;BA.debugLine="response=JobServer.GetString";
_response = _jobserver._getstring /*String*/ ();
 if (true) break;

case 7:
//C
this.state = 10;
;
 if (true) break;

case 9:
//C
this.state = 10;
this.catchState = 0;
 //BA.debugLineNum = 110;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("52490390",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 if (true) break;
if (true) break;

case 10:
//C
this.state = -1;
this.catchState = 0;
;
 //BA.debugLineNum = 112;BA.debugLine="End Sub";
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
public static void  _cargar() throws Exception{
ResumableSub_cargar rsub = new ResumableSub_cargar(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_cargar extends BA.ResumableSub {
public ResumableSub_cargar(ChatHN.com.menu parent) {
this.parent = parent;
}
ChatHN.com.menu parent;
String _response = "";
ChatHN.com.httpjob _httpmenu = null;
ChatHN.com.httpjob _jobserver = null;
anywheresoftware.b4a.objects.collections.JSONParser _json = null;
anywheresoftware.b4a.objects.collections.Map _m = null;
String _valorcod = "";
anywheresoftware.b4a.objects.collections.List _lis = null;
int _i = 0;
int step21;
int limit21;

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
 //BA.debugLineNum = 45;BA.debugLine="Try";
if (true) break;

case 1:
//try
this.state = 28;
this.catchState = 27;
this.state = 3;
if (true) break;

case 3:
//C
this.state = 4;
this.catchState = 27;
 //BA.debugLineNum = 46;BA.debugLine="ProgressDialogShow2(\"Espere por favor...\",False)";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence("Espere por favor..."),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 47;BA.debugLine="Dim response As String";
_response = "";
 //BA.debugLineNum = 48;BA.debugLine="Dim HttpMenu As HttpJob";
_httpmenu = new ChatHN.com.httpjob();
 //BA.debugLineNum = 49;BA.debugLine="HttpMenu.Initialize(\"HttpMenu\",Me)";
_httpmenu._initialize /*String*/ (processBA,"HttpMenu",menu.getObject());
 //BA.debugLineNum = 50;BA.debugLine="HttpMenu.PostString(Generales.Url& \"ConsultaMast";
_httpmenu._poststring /*String*/ (parent.mostCurrent._generales._url /*String*/ +"ConsultaMaster","Base=cthTUsuarios&Esquema=INTRANET");
 //BA.debugLineNum = 51;BA.debugLine="HttpMenu.GetRequest.SetContentType(\"application/";
_httpmenu._getrequest /*anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpRequest*/ ().SetContentType("application/x-www-form-urlencoded");
 //BA.debugLineNum = 52;BA.debugLine="HttpMenu.GetRequest.SetHeader(\"Accept\",\"applicat";
_httpmenu._getrequest /*anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpRequest*/ ().SetHeader("Accept","application/json");
 //BA.debugLineNum = 53;BA.debugLine="HttpMenu.GetRequest.SetHeader(\"Authorization\",\"A";
_httpmenu._getrequest /*anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpRequest*/ ().SetHeader("Authorization","AIzaSyDYOkkTJiTPVd2U7EaTOAwhcN9ySH6GoHxOIYOM");
 //BA.debugLineNum = 54;BA.debugLine="HttpMenu.GetRequest.Timeout=20000";
_httpmenu._getrequest /*anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpRequest*/ ().setTimeout((int) (20000));
 //BA.debugLineNum = 55;BA.debugLine="Wait For (HttpMenu) JobDone(JobServer As HttpJob";
anywheresoftware.b4a.keywords.Common.WaitFor("jobdone", processBA, this, (Object)(_httpmenu));
this.state = 29;
return;
case 29:
//C
this.state = 4;
_jobserver = (ChatHN.com.httpjob) result[0];
;
 //BA.debugLineNum = 56;BA.debugLine="If JobServer.Success = True Then";
if (true) break;

case 4:
//if
this.state = 25;
if (_jobserver._success /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
this.state = 6;
}else {
this.state = 24;
}if (true) break;

case 6:
//C
this.state = 7;
 //BA.debugLineNum = 57;BA.debugLine="response=JobServer.GetString";
_response = _jobserver._getstring /*String*/ ();
 //BA.debugLineNum = 58;BA.debugLine="Dim json As JSONParser";
_json = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 59;BA.debugLine="json.Initialize(response)";
_json.Initialize(_response);
 //BA.debugLineNum = 60;BA.debugLine="Dim m As Map = json.NextObject";
_m = new anywheresoftware.b4a.objects.collections.Map();
_m = _json.NextObject();
 //BA.debugLineNum = 61;BA.debugLine="m= m.Get(\"wsrespuesta\")";
_m.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(_m.Get((Object)("wsrespuesta"))));
 //BA.debugLineNum = 62;BA.debugLine="Dim ValorCod As String= m.Get(\"codError\")";
_valorcod = BA.ObjectToString(_m.Get((Object)("codError")));
 //BA.debugLineNum = 63;BA.debugLine="If(ValorCod.EqualsIgnoreCase(\"0\"))Then";
if (true) break;

case 7:
//if
this.state = 22;
if ((_valorcod.equalsIgnoreCase("0"))) { 
this.state = 9;
}else {
this.state = 21;
}if (true) break;

case 9:
//C
this.state = 10;
 //BA.debugLineNum = 64;BA.debugLine="Dim lis As List=m.Get(\"data\")";
_lis = new anywheresoftware.b4a.objects.collections.List();
_lis.setObject((java.util.List)(_m.Get((Object)("data"))));
 //BA.debugLineNum = 65;BA.debugLine="For i=0 To lis.Size-1";
if (true) break;

case 10:
//for
this.state = 19;
step21 = 1;
limit21 = (int) (_lis.getSize()-1);
_i = (int) (0) ;
this.state = 30;
if (true) break;

case 30:
//C
this.state = 19;
if ((step21 > 0 && _i <= limit21) || (step21 < 0 && _i >= limit21)) this.state = 12;
if (true) break;

case 31:
//C
this.state = 30;
_i = ((int)(0 + _i + step21)) ;
if (true) break;

case 12:
//C
this.state = 13;
 //BA.debugLineNum = 66;BA.debugLine="m= lis.Get(i)";
_m.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(_lis.Get(_i)));
 //BA.debugLineNum = 67;BA.debugLine="If(Generales.Usuario <> m.Get(\"pusuario\")) Th";
if (true) break;

case 13:
//if
this.state = 18;
if (((parent.mostCurrent._generales._usuario /*String*/ ).equals(BA.ObjectToString(_m.Get((Object)("pusuario")))) == false)) { 
this.state = 15;
}else {
this.state = 17;
}if (true) break;

case 15:
//C
this.state = 18;
 //BA.debugLineNum = 68;BA.debugLine="ListView1.AddTwoLines2(m.Get(\"pnombre\")&\"  \"";
parent.mostCurrent._listview1.AddTwoLines2(BA.ObjectToCharSequence(BA.ObjectToString(_m.Get((Object)("pnombre")))+"  "+BA.ObjectToString(_m.Get((Object)("pusuario")))),BA.ObjectToCharSequence(_m.Get((Object)("pemail"))),(Object)(_m.getObject()));
 if (true) break;

case 17:
//C
this.state = 18;
 //BA.debugLineNum = 70;BA.debugLine="Generales.NombreUsuario=m.Get(\"pnombre\")";
parent.mostCurrent._generales._nombreusuario /*String*/  = BA.ObjectToString(_m.Get((Object)("pnombre")));
 if (true) break;

case 18:
//C
this.state = 31;
;
 if (true) break;
if (true) break;

case 19:
//C
this.state = 22;
;
 //BA.debugLineNum = 73;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 if (true) break;

case 21:
//C
this.state = 22;
 //BA.debugLineNum = 75;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 76;BA.debugLine="Msgbox(\"Error al iniciar valide sus credencial";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Error al iniciar valide sus credenciales."),BA.ObjectToCharSequence("Notificatión!!!"),mostCurrent.activityBA);
 if (true) break;

case 22:
//C
this.state = 25;
;
 if (true) break;

case 24:
//C
this.state = 25;
 //BA.debugLineNum = 79;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 80;BA.debugLine="Msgbox(\"Error de conexión\"&CRLF&\"Valide sus dat";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Error de conexión"+anywheresoftware.b4a.keywords.Common.CRLF+"Valide sus datos moviles"),BA.ObjectToCharSequence("Notificación!!!"),mostCurrent.activityBA);
 if (true) break;

case 25:
//C
this.state = 28;
;
 if (true) break;

case 27:
//C
this.state = 28;
this.catchState = 0;
 //BA.debugLineNum = 83;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 84;BA.debugLine="Msgbox(\"Error al iniciar valide sus credenciales";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Error al iniciar valide sus credenciales."),BA.ObjectToCharSequence("Notificatión!!!"),mostCurrent.activityBA);
 if (true) break;
if (true) break;

case 28:
//C
this.state = -1;
this.catchState = 0;
;
 //BA.debugLineNum = 86;BA.debugLine="End Sub";
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
public static String  _globals() throws Exception{
 //BA.debugLineNum = 12;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 16;BA.debugLine="Private ListView1 As ListView";
mostCurrent._listview1 = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 17;BA.debugLine="End Sub";
return "";
}
public static String  _listview1_itemclick(int _position,Object _value) throws Exception{
anywheresoftware.b4a.objects.collections.Map _valor = null;
 //BA.debugLineNum = 37;BA.debugLine="Sub ListView1_ItemClick (Position As Int, Value As";
 //BA.debugLineNum = 38;BA.debugLine="Dim valor As Map= Value";
_valor = new anywheresoftware.b4a.objects.collections.Map();
_valor.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(_value));
 //BA.debugLineNum = 39;BA.debugLine="Generales.Destino=valor";
mostCurrent._generales._destino /*anywheresoftware.b4a.objects.collections.Map*/  = _valor;
 //BA.debugLineNum = 40;BA.debugLine="StartActivity(chat)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._chat.getObject()));
 //BA.debugLineNum = 41;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 10;BA.debugLine="End Sub";
return "";
}
}
