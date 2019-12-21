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

public class registrar extends Activity implements B4AActivity{
	public static registrar mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "ChatHN.com", "ChatHN.com.registrar");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (registrar).");
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
		activityBA = new BA(this, layout, processBA, "ChatHN.com", "ChatHN.com.registrar");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "ChatHN.com.registrar", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (registrar) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (registrar) Resume **");
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
		return registrar.class;
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
        BA.LogInfo("** Activity (registrar) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            registrar mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (registrar) Resume **");
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
public anywheresoftware.b4a.objects.EditTextWrapper _txtusuario = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtnombr = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtapellido = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtemail = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtclave = null;
public anywheresoftware.b4a.objects.IME _ime = null;
public ChatHN.com.main _main = null;
public ChatHN.com.generales _generales = null;
public ChatHN.com.chat _chat = null;
public ChatHN.com.starter _starter = null;
public ChatHN.com.menu _menu = null;
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
 //BA.debugLineNum = 24;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 26;BA.debugLine="Activity.LoadLayout(\"lytRegistrar\")";
mostCurrent._activity.LoadLayout("lytRegistrar",mostCurrent.activityBA);
 //BA.debugLineNum = 27;BA.debugLine="IME.Initialize(\"\")";
mostCurrent._ime.Initialize("");
 //BA.debugLineNum = 28;BA.debugLine="IME.SetLengthFilter(TxtClave, 4)";
mostCurrent._ime.SetLengthFilter((android.widget.EditText)(mostCurrent._txtclave.getObject()),(int) (4));
 //BA.debugLineNum = 29;BA.debugLine="IME.Initialize(\"\")";
mostCurrent._ime.Initialize("");
 //BA.debugLineNum = 30;BA.debugLine="IME.SetLengthFilter(TxtUsuario, 8)";
mostCurrent._ime.SetLengthFilter((android.widget.EditText)(mostCurrent._txtusuario.getObject()),(int) (8));
 //BA.debugLineNum = 31;BA.debugLine="IME.Initialize(\"\")";
mostCurrent._ime.Initialize("");
 //BA.debugLineNum = 32;BA.debugLine="IME.SetLengthFilter(TxtNombr, 30)";
mostCurrent._ime.SetLengthFilter((android.widget.EditText)(mostCurrent._txtnombr.getObject()),(int) (30));
 //BA.debugLineNum = 33;BA.debugLine="IME.Initialize(\"\")";
mostCurrent._ime.Initialize("");
 //BA.debugLineNum = 34;BA.debugLine="IME.SetLengthFilter(TxtApellido,30)";
mostCurrent._ime.SetLengthFilter((android.widget.EditText)(mostCurrent._txtapellido.getObject()),(int) (30));
 //BA.debugLineNum = 35;BA.debugLine="IME.Initialize(\"\")";
mostCurrent._ime.Initialize("");
 //BA.debugLineNum = 36;BA.debugLine="IME.SetLengthFilter(TxtEmail, 30)";
mostCurrent._ime.SetLengthFilter((android.widget.EditText)(mostCurrent._txtemail.getObject()),(int) (30));
 //BA.debugLineNum = 37;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 43;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 45;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 39;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 41;BA.debugLine="End Sub";
return "";
}
public static void  _btnregistrar_click() throws Exception{
ResumableSub_BtnRegistrar_Click rsub = new ResumableSub_BtnRegistrar_Click(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_BtnRegistrar_Click extends BA.ResumableSub {
public ResumableSub_BtnRegistrar_Click(ChatHN.com.registrar parent) {
this.parent = parent;
}
ChatHN.com.registrar parent;
String _response = "";
ChatHN.com.httpjob _httpregistrar = null;
ChatHN.com.httpjob _jobserver = null;
anywheresoftware.b4a.objects.collections.JSONParser _json = null;
anywheresoftware.b4a.objects.collections.Map _m = null;
String _valorcod = "";

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
 //BA.debugLineNum = 49;BA.debugLine="Try";
if (true) break;

case 1:
//try
this.state = 54;
this.catchState = 53;
this.state = 3;
if (true) break;

case 3:
//C
this.state = 4;
this.catchState = 53;
 //BA.debugLineNum = 50;BA.debugLine="If(TxtUsuario.Text.EqualsIgnoreCase(\"\")) Then";
if (true) break;

case 4:
//if
this.state = 17;
if ((parent.mostCurrent._txtusuario.getText().equalsIgnoreCase(""))) { 
this.state = 6;
}else {
this.state = 12;
}if (true) break;

case 6:
//C
this.state = 7;
 //BA.debugLineNum = 51;BA.debugLine="If(TxtUsuario.Text.Length<8) Then";
if (true) break;

case 7:
//if
this.state = 10;
if ((parent.mostCurrent._txtusuario.getText().length()<8)) { 
this.state = 9;
}if (true) break;

case 9:
//C
this.state = 10;
 //BA.debugLineNum = 52;BA.debugLine="ToastMessageShow(\"Por favor agregar un numero";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Por favor agregar un numero de telefono valido"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 53;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 10:
//C
this.state = 17;
;
 if (true) break;

case 12:
//C
this.state = 13;
 //BA.debugLineNum = 56;BA.debugLine="If(TxtUsuario.Text.Length<8) Then";
if (true) break;

case 13:
//if
this.state = 16;
if ((parent.mostCurrent._txtusuario.getText().length()<8)) { 
this.state = 15;
}if (true) break;

case 15:
//C
this.state = 16;
 //BA.debugLineNum = 57;BA.debugLine="ToastMessageShow(\"Por favor agregar un numero";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Por favor agregar un numero de telefono valido"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 58;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 16:
//C
this.state = 17;
;
 if (true) break;
;
 //BA.debugLineNum = 61;BA.debugLine="If(TxtNombr.Text.EqualsIgnoreCase(\"\"))Then";

case 17:
//if
this.state = 20;
if ((parent.mostCurrent._txtnombr.getText().equalsIgnoreCase(""))) { 
this.state = 19;
}if (true) break;

case 19:
//C
this.state = 20;
 //BA.debugLineNum = 62;BA.debugLine="ToastMessageShow(\"Por favor agregar sus nombres";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Por favor agregar sus nombres"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 63;BA.debugLine="Return";
if (true) return ;
 if (true) break;
;
 //BA.debugLineNum = 65;BA.debugLine="If(TxtApellido.Text.EqualsIgnoreCase(\"\"))Then";

case 20:
//if
this.state = 23;
if ((parent.mostCurrent._txtapellido.getText().equalsIgnoreCase(""))) { 
this.state = 22;
}if (true) break;

case 22:
//C
this.state = 23;
 //BA.debugLineNum = 66;BA.debugLine="ToastMessageShow(\"Por favor agregar los apellid";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Por favor agregar los apellidos"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 67;BA.debugLine="Return";
if (true) return ;
 if (true) break;
;
 //BA.debugLineNum = 69;BA.debugLine="If(TxtEmail.Text.EqualsIgnoreCase(\"\"))Then";

case 23:
//if
this.state = 26;
if ((parent.mostCurrent._txtemail.getText().equalsIgnoreCase(""))) { 
this.state = 25;
}if (true) break;

case 25:
//C
this.state = 26;
 //BA.debugLineNum = 70;BA.debugLine="ToastMessageShow(\"Por favor agregar un correo v";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Por favor agregar un correo valido"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 71;BA.debugLine="Return";
if (true) return ;
 if (true) break;
;
 //BA.debugLineNum = 73;BA.debugLine="If(TxtNombr.Text.EqualsIgnoreCase(\"\"))Then";

case 26:
//if
this.state = 39;
if ((parent.mostCurrent._txtnombr.getText().equalsIgnoreCase(""))) { 
this.state = 28;
}else {
this.state = 34;
}if (true) break;

case 28:
//C
this.state = 29;
 //BA.debugLineNum = 74;BA.debugLine="If(TxtNombr.Text.Length<4) Then";
if (true) break;

case 29:
//if
this.state = 32;
if ((parent.mostCurrent._txtnombr.getText().length()<4)) { 
this.state = 31;
}if (true) break;

case 31:
//C
this.state = 32;
 //BA.debugLineNum = 75;BA.debugLine="ToastMessageShow(\"Por favor agregar una clave";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Por favor agregar una clave valida"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 76;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 32:
//C
this.state = 39;
;
 if (true) break;

case 34:
//C
this.state = 35;
 //BA.debugLineNum = 79;BA.debugLine="If(TxtNombr.Text.Length<4) Then";
if (true) break;

case 35:
//if
this.state = 38;
if ((parent.mostCurrent._txtnombr.getText().length()<4)) { 
this.state = 37;
}if (true) break;

case 37:
//C
this.state = 38;
 //BA.debugLineNum = 80;BA.debugLine="ToastMessageShow(\"Por favor agregar una clave";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Por favor agregar una clave valida"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 81;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 38:
//C
this.state = 39;
;
 if (true) break;

case 39:
//C
this.state = 40;
;
 //BA.debugLineNum = 86;BA.debugLine="ProgressDialogShow2(\"Espere por favor...\"& CRLF";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence("Espere por favor..."+anywheresoftware.b4a.keywords.Common.CRLF+"Inciando Registro..."),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 87;BA.debugLine="Dim response As String";
_response = "";
 //BA.debugLineNum = 88;BA.debugLine="Dim HttpRegistrar As HttpJob";
_httpregistrar = new ChatHN.com.httpjob();
 //BA.debugLineNum = 89;BA.debugLine="HttpRegistrar.Initialize(\"HttpRegistrar\",Me)";
_httpregistrar._initialize /*String*/ (processBA,"HttpRegistrar",registrar.getObject());
 //BA.debugLineNum = 90;BA.debugLine="HttpRegistrar.PostString(Generales.Url& \"InstYtc";
_httpregistrar._poststring /*String*/ (parent.mostCurrent._generales._url /*String*/ +"InstYtchUsuario","pUsuario="+parent.mostCurrent._txtusuario.getText().trim()+"&pClave="+parent.mostCurrent._txtclave.getText().trim()+"&pNombres="+parent.mostCurrent._txtnombr.getText().trim()+"&pApellidos="+parent.mostCurrent._txtapellido.getText()+"&pEmail="+parent.mostCurrent._txtemail.getText().trim());
 //BA.debugLineNum = 92;BA.debugLine="HttpRegistrar.GetRequest.SetContentType(\"applica";
_httpregistrar._getrequest /*anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpRequest*/ ().SetContentType("application/x-www-form-urlencoded");
 //BA.debugLineNum = 93;BA.debugLine="HttpRegistrar.GetRequest.SetHeader(\"Accept\",\"app";
_httpregistrar._getrequest /*anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpRequest*/ ().SetHeader("Accept","application/json");
 //BA.debugLineNum = 94;BA.debugLine="HttpRegistrar.GetRequest.SetHeader(\"Authorizatio";
_httpregistrar._getrequest /*anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpRequest*/ ().SetHeader("Authorization","AIzaSyDYOkkTJiTPVd2U7EaTOAwhcN9ySH6GoHxOIYOM");
 //BA.debugLineNum = 95;BA.debugLine="HttpRegistrar.GetRequest.Timeout=20000";
_httpregistrar._getrequest /*anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpRequest*/ ().setTimeout((int) (20000));
 //BA.debugLineNum = 96;BA.debugLine="Wait For (HttpRegistrar) JobDone(JobServer As Ht";
anywheresoftware.b4a.keywords.Common.WaitFor("jobdone", processBA, this, (Object)(_httpregistrar));
this.state = 55;
return;
case 55:
//C
this.state = 40;
_jobserver = (ChatHN.com.httpjob) result[0];
;
 //BA.debugLineNum = 97;BA.debugLine="If JobServer.Success = True Then";
if (true) break;

case 40:
//if
this.state = 51;
if (_jobserver._success /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
this.state = 42;
}else {
this.state = 50;
}if (true) break;

case 42:
//C
this.state = 43;
 //BA.debugLineNum = 98;BA.debugLine="response=JobServer.GetString";
_response = _jobserver._getstring /*String*/ ();
 //BA.debugLineNum = 99;BA.debugLine="Dim json As JSONParser";
_json = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 100;BA.debugLine="json.Initialize(response)";
_json.Initialize(_response);
 //BA.debugLineNum = 101;BA.debugLine="Dim m As Map = json.NextObject";
_m = new anywheresoftware.b4a.objects.collections.Map();
_m = _json.NextObject();
 //BA.debugLineNum = 102;BA.debugLine="m= m.Get(\"wsrespuesta\")";
_m.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(_m.Get((Object)("wsrespuesta"))));
 //BA.debugLineNum = 103;BA.debugLine="Dim ValorCod As String= m.Get(\"codError\")";
_valorcod = BA.ObjectToString(_m.Get((Object)("codError")));
 //BA.debugLineNum = 104;BA.debugLine="If(ValorCod.EqualsIgnoreCase(\"0\"))Then";
if (true) break;

case 43:
//if
this.state = 48;
if ((_valorcod.equalsIgnoreCase("0"))) { 
this.state = 45;
}else {
this.state = 47;
}if (true) break;

case 45:
//C
this.state = 48;
 //BA.debugLineNum = 105;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 106;BA.debugLine="Msgbox(\"Exito al Registrarse...\",\"Bienvenido \"";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Exito al Registrarse..."),BA.ObjectToCharSequence("Bienvenido "+parent.mostCurrent._txtnombr.getText()+"!!!"),mostCurrent.activityBA);
 //BA.debugLineNum = 107;BA.debugLine="Activity.Finish";
parent.mostCurrent._activity.Finish();
 //BA.debugLineNum = 108;BA.debugLine="StartActivity(Main)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(parent.mostCurrent._main.getObject()));
 if (true) break;

case 47:
//C
this.state = 48;
 //BA.debugLineNum = 110;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 111;BA.debugLine="Msgbox(\"Error al registrar.\",\"Notificatión!!!\"";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Error al registrar."),BA.ObjectToCharSequence("Notificatión!!!"),mostCurrent.activityBA);
 if (true) break;

case 48:
//C
this.state = 51;
;
 if (true) break;

case 50:
//C
this.state = 51;
 //BA.debugLineNum = 114;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 115;BA.debugLine="Msgbox(\"Error de conexión\"&CRLF&\"Valide sus dat";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Error de conexión"+anywheresoftware.b4a.keywords.Common.CRLF+"Valide sus datos moviles"),BA.ObjectToCharSequence("Notificación!!!"),mostCurrent.activityBA);
 if (true) break;

case 51:
//C
this.state = 54;
;
 if (true) break;

case 53:
//C
this.state = 54;
this.catchState = 0;
 //BA.debugLineNum = 118;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 119;BA.debugLine="Msgbox(\"Error al registrar valide sus datos...\",";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Error al registrar valide sus datos..."),BA.ObjectToCharSequence("Mensaje!!!"),mostCurrent.activityBA);
 if (true) break;
if (true) break;

case 54:
//C
this.state = -1;
this.catchState = 0;
;
 //BA.debugLineNum = 121;BA.debugLine="End Sub";
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
public static String  _globals() throws Exception{
 //BA.debugLineNum = 12;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 16;BA.debugLine="Private TxtUsuario As EditText";
mostCurrent._txtusuario = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 17;BA.debugLine="Private TxtNombr As EditText";
mostCurrent._txtnombr = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Private TxtApellido As EditText";
mostCurrent._txtapellido = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private TxtEmail As EditText";
mostCurrent._txtemail = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Private TxtClave As EditText";
mostCurrent._txtclave = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Dim IME As IME";
mostCurrent._ime = new anywheresoftware.b4a.objects.IME();
 //BA.debugLineNum = 22;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 10;BA.debugLine="End Sub";
return "";
}
}
