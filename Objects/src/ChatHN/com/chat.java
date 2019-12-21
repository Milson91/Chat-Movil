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

public class chat extends Activity implements B4AActivity{
	public static chat mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "ChatHN.com", "ChatHN.com.chat");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (chat).");
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
		activityBA = new BA(this, layout, processBA, "ChatHN.com", "ChatHN.com.chat");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "ChatHN.com.chat", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (chat) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (chat) Resume **");
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
		return chat.class;
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
        BA.LogInfo("** Activity (chat) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            chat mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (chat) Resume **");
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
public static anywheresoftware.b4a.objects.Timer _timer1 = null;
public anywheresoftware.b4a.objects.collections.List _datosdetalle = null;
public anywheresoftware.b4a.objects.ScrollViewWrapper _scrollview1 = null;
public anywheresoftware.b4a.objects.drawable.ColorDrawable _dra = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtmsj = null;
public anywheresoftware.b4a.objects.IME _ime = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblnombre = null;
public static int _filas = 0;
public static int _totalpanelheightfecha = 0;
public static int _panelheightfecha = 0;
public static int _totalpanelheight = 0;
public ChatHN.com.main _main = null;
public ChatHN.com.generales _generales = null;
public ChatHN.com.starter _starter = null;
public ChatHN.com.menu _menu = null;
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
 //BA.debugLineNum = 29;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 31;BA.debugLine="Activity.LoadLayout(\"lytchat\")";
mostCurrent._activity.LoadLayout("lytchat",mostCurrent.activityBA);
 //BA.debugLineNum = 32;BA.debugLine="timer1.Initialize(\"timer1\", 2500)";
_timer1.Initialize(processBA,"timer1",(long) (2500));
 //BA.debugLineNum = 33;BA.debugLine="timer1.Enabled = True";
_timer1.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 34;BA.debugLine="IME.Initialize(\"\")";
mostCurrent._ime.Initialize("");
 //BA.debugLineNum = 35;BA.debugLine="IME.SetLengthFilter(TxtMsj, 50)";
mostCurrent._ime.SetLengthFilter((android.widget.EditText)(mostCurrent._txtmsj.getObject()),(int) (50));
 //BA.debugLineNum = 36;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 53;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 54;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 55;BA.debugLine="timer1.Enabled=False";
_timer1.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 56;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 };
 //BA.debugLineNum = 58;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 59;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 44;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 46;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 38;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 39;BA.debugLine="ProgressDialogShow2(\"Espere por favor...\"& CRLF &";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence("Espere por favor..."+anywheresoftware.b4a.keywords.Common.CRLF+"Cargando Msj..."),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 40;BA.debugLine="LblNombre.Text=Generales.Destino.Get(\"pnombre\")";
mostCurrent._lblnombre.setText(BA.ObjectToCharSequence(mostCurrent._generales._destino /*anywheresoftware.b4a.objects.collections.Map*/ .Get((Object)("pnombre"))));
 //BA.debugLineNum = 41;BA.debugLine="CargarTransacciones";
_cargartransacciones();
 //BA.debugLineNum = 42;BA.debugLine="End Sub";
return "";
}
public static void  _cargardetalle() throws Exception{
ResumableSub_CargarDetalle rsub = new ResumableSub_CargarDetalle(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_CargarDetalle extends BA.ResumableSub {
public ResumableSub_CargarDetalle(ChatHN.com.chat parent) {
this.parent = parent;
}
ChatHN.com.chat parent;
anywheresoftware.b4a.objects.LabelWrapper _lbl = null;
int _panelheight = 0;
int _p = 0;
anywheresoftware.b4a.objects.collections.Map _mdeta = null;
anywheresoftware.b4a.objects.PanelWrapper _pannovisita = null;
int step4;
int limit4;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 124;BA.debugLine="Dim lbl As Label";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 125;BA.debugLine="Dim PanelHeight As Int";
_panelheight = 0;
 //BA.debugLineNum = 126;BA.debugLine="PanelHeight=60dip";
_panelheight = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60));
 //BA.debugLineNum = 129;BA.debugLine="For p = 0 To  DatosDetalle.Size - 1";
if (true) break;

case 1:
//for
this.state = 10;
step4 = 1;
limit4 = (int) (parent.mostCurrent._datosdetalle.getSize()-1);
_p = (int) (0) ;
this.state = 16;
if (true) break;

case 16:
//C
this.state = 10;
if ((step4 > 0 && _p <= limit4) || (step4 < 0 && _p >= limit4)) this.state = 3;
if (true) break;

case 17:
//C
this.state = 16;
_p = ((int)(0 + _p + step4)) ;
if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 130;BA.debugLine="Sleep(0)";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,this,(int) (0));
this.state = 18;
return;
case 18:
//C
this.state = 4;
;
 //BA.debugLineNum = 131;BA.debugLine="Dim mDeta As Map=DatosDetalle.Get(p)";
_mdeta = new anywheresoftware.b4a.objects.collections.Map();
_mdeta.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(parent.mostCurrent._datosdetalle.Get(_p)));
 //BA.debugLineNum = 132;BA.debugLine="Dim panNoVisita As Panel";
_pannovisita = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 133;BA.debugLine="PanelHeightFecha=2dip";
parent._panelheightfecha = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2));
 //BA.debugLineNum = 136;BA.debugLine="If(mDeta.Get(\"origen\")<>Generales.Usuario) Then";
if (true) break;

case 4:
//if
this.state = 9;
if (((_mdeta.Get((Object)("origen"))).equals((Object)(parent.mostCurrent._generales._usuario /*String*/ )) == false)) { 
this.state = 6;
}else {
this.state = 8;
}if (true) break;

case 6:
//C
this.state = 9;
 //BA.debugLineNum = 137;BA.debugLine="dra.Initialize(Colors.RGB(244,244,244),20)";
parent.mostCurrent._dra.Initialize(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (244),(int) (244),(int) (244)),(int) (20));
 //BA.debugLineNum = 138;BA.debugLine="panNoVisita.Initialize(\"panNoVisita\")";
_pannovisita.Initialize(mostCurrent.activityBA,"panNoVisita");
 //BA.debugLineNum = 139;BA.debugLine="ScrollView1.Panel.AddView(panNoVisita,2dip,(fil";
parent.mostCurrent._scrollview1.getPanel().AddView((android.view.View)(_pannovisita.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2)),(int) ((parent._filas*_panelheight)+parent._totalpanelheightfecha+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))),(int) (parent.mostCurrent._scrollview1.getWidth()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),_panelheight);
 //BA.debugLineNum = 140;BA.debugLine="panNoVisita.Tag	= mDeta.Get(\"Tag\")";
_pannovisita.setTag(_mdeta.Get((Object)("Tag")));
 //BA.debugLineNum = 141;BA.debugLine="panNoVisita.Background=dra";
_pannovisita.setBackground((android.graphics.drawable.Drawable)(parent.mostCurrent._dra.getObject()));
 if (true) break;

case 8:
//C
this.state = 9;
 //BA.debugLineNum = 143;BA.debugLine="dra.Initialize(Colors.RGB(220,248,198),20)";
parent.mostCurrent._dra.Initialize(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (220),(int) (248),(int) (198)),(int) (20));
 //BA.debugLineNum = 144;BA.debugLine="panNoVisita.Initialize(\"panNoVisita\")";
_pannovisita.Initialize(mostCurrent.activityBA,"panNoVisita");
 //BA.debugLineNum = 145;BA.debugLine="ScrollView1.Panel.AddView(panNoVisita,15dip,(fi";
parent.mostCurrent._scrollview1.getPanel().AddView((android.view.View)(_pannovisita.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15)),(int) ((parent._filas*_panelheight)+parent._totalpanelheightfecha+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))),(int) (parent.mostCurrent._scrollview1.getWidth()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20))),_panelheight);
 //BA.debugLineNum = 146;BA.debugLine="panNoVisita.Tag	= mDeta.Get(\"Tag\")";
_pannovisita.setTag(_mdeta.Get((Object)("Tag")));
 //BA.debugLineNum = 147;BA.debugLine="panNoVisita.Background=dra";
_pannovisita.setBackground((android.graphics.drawable.Drawable)(parent.mostCurrent._dra.getObject()));
 if (true) break;

case 9:
//C
this.state = 17;
;
 //BA.debugLineNum = 158;BA.debugLine="lbl.Initialize(\"lbl\")";
_lbl.Initialize(mostCurrent.activityBA,"lbl");
 //BA.debugLineNum = 159;BA.debugLine="panNoVisita.AddView(lbl,10dip, 10dip, panNoVisit";
_pannovisita.AddView((android.view.View)(_lbl.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),_pannovisita.getWidth(),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
 //BA.debugLineNum = 160;BA.debugLine="lbl.Text.ToUpperCase";
_lbl.getText().toUpperCase();
 //BA.debugLineNum = 161;BA.debugLine="lbl.Text= mDeta.Get(\"origen\")";
_lbl.setText(BA.ObjectToCharSequence(_mdeta.Get((Object)("origen"))));
 //BA.debugLineNum = 162;BA.debugLine="lbl.TextSize = 15";
_lbl.setTextSize((float) (15));
 //BA.debugLineNum = 163;BA.debugLine="lbl.TextColor = Colors.RGB(186,51,220)";
_lbl.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (186),(int) (51),(int) (220)));
 //BA.debugLineNum = 165;BA.debugLine="lbl.Initialize(\"lbl\")";
_lbl.Initialize(mostCurrent.activityBA,"lbl");
 //BA.debugLineNum = 166;BA.debugLine="panNoVisita.AddView(lbl,10dip, 30dip, ScrollView";
_pannovisita.AddView((android.view.View)(_lbl.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)),(int) (parent.mostCurrent._scrollview1.getWidth()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (70))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
 //BA.debugLineNum = 167;BA.debugLine="lbl.Text.ToUpperCase";
_lbl.getText().toUpperCase();
 //BA.debugLineNum = 168;BA.debugLine="lbl.Text= mDeta.Get(\"mensaje\")";
_lbl.setText(BA.ObjectToCharSequence(_mdeta.Get((Object)("mensaje"))));
 //BA.debugLineNum = 169;BA.debugLine="lbl.TextSize = 14";
_lbl.setTextSize((float) (14));
 //BA.debugLineNum = 170;BA.debugLine="lbl.TextColor = Colors.RGB(160,160,160)";
_lbl.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (160),(int) (160),(int) (160)));
 //BA.debugLineNum = 171;BA.debugLine="TotalPanelHeightFecha = TotalPanelHeightFecha +";
parent._totalpanelheightfecha = (int) (parent._totalpanelheightfecha+parent._panelheightfecha);
 //BA.debugLineNum = 173;BA.debugLine="lbl.Initialize(\"lbl\")";
_lbl.Initialize(mostCurrent.activityBA,"lbl");
 //BA.debugLineNum = 174;BA.debugLine="panNoVisita.AddView(lbl,panNoVisita.Width -55dip";
_pannovisita.AddView((android.view.View)(_lbl.getObject()),(int) (_pannovisita.getWidth()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (55))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
 //BA.debugLineNum = 175;BA.debugLine="lbl.Text.ToUpperCase";
_lbl.getText().toUpperCase();
 //BA.debugLineNum = 176;BA.debugLine="lbl.Text= mDeta.Get(\"hora\")";
_lbl.setText(BA.ObjectToCharSequence(_mdeta.Get((Object)("hora"))));
 //BA.debugLineNum = 177;BA.debugLine="lbl.TextSize = 14";
_lbl.setTextSize((float) (14));
 //BA.debugLineNum = 178;BA.debugLine="lbl.TextColor = Colors.RGB(160,160,160)";
_lbl.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (160),(int) (160),(int) (160)));
 //BA.debugLineNum = 179;BA.debugLine="TotalPanelHeightFecha = TotalPanelHeightFecha +";
parent._totalpanelheightfecha = (int) (parent._totalpanelheightfecha+parent._panelheightfecha);
 //BA.debugLineNum = 182;BA.debugLine="TotalPanelHeight = TotalPanelHeight + PanelHeigh";
parent._totalpanelheight = (int) (parent._totalpanelheight+_panelheight+parent._panelheightfecha);
 //BA.debugLineNum = 183;BA.debugLine="filas=filas+1";
parent._filas = (int) (parent._filas+1);
 if (true) break;
if (true) break;
;
 //BA.debugLineNum = 186;BA.debugLine="If TotalPanelHeight < ScrollView1.Height Then";

case 10:
//if
this.state = 15;
if (parent._totalpanelheight<parent.mostCurrent._scrollview1.getHeight()) { 
this.state = 12;
}else {
this.state = 14;
}if (true) break;

case 12:
//C
this.state = 15;
 //BA.debugLineNum = 187;BA.debugLine="ScrollView1.Panel.Height = ScrollView1.Height -2";
parent.mostCurrent._scrollview1.getPanel().setHeight((int) (parent.mostCurrent._scrollview1.getHeight()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2))));
 if (true) break;

case 14:
//C
this.state = 15;
 //BA.debugLineNum = 189;BA.debugLine="ScrollView1.Panel.Height = TotalPanelHeight+60di";
parent.mostCurrent._scrollview1.getPanel().setHeight((int) (parent._totalpanelheight+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))));
 if (true) break;

case 15:
//C
this.state = -1;
;
 //BA.debugLineNum = 192;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _cargartransacciones() throws Exception{
ResumableSub_CargarTransacciones rsub = new ResumableSub_CargarTransacciones(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_CargarTransacciones extends BA.ResumableSub {
public ResumableSub_CargarTransacciones(ChatHN.com.chat parent) {
this.parent = parent;
}
ChatHN.com.chat parent;
String _response = "";
ChatHN.com.httpjob _httplogin = null;
ChatHN.com.httpjob _jobserver = null;
anywheresoftware.b4a.objects.collections.JSONParser _json = null;
anywheresoftware.b4a.objects.collections.Map _m = null;
String _valorcod = "";
anywheresoftware.b4a.objects.collections.Map _mdeta = null;
anywheresoftware.b4a.objects.collections.List _listdatos = null;
int _i = 0;
anywheresoftware.b4a.objects.collections.Map _deta = null;
int step25;
int limit25;

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
 //BA.debugLineNum = 67;BA.debugLine="Try";
if (true) break;

case 1:
//try
this.state = 22;
this.catchState = 21;
this.state = 3;
if (true) break;

case 3:
//C
this.state = 4;
this.catchState = 21;
 //BA.debugLineNum = 68;BA.debugLine="Dim response As String";
_response = "";
 //BA.debugLineNum = 69;BA.debugLine="Dim HttpLogin As HttpJob";
_httplogin = new ChatHN.com.httpjob();
 //BA.debugLineNum = 70;BA.debugLine="HttpLogin.Initialize(\"transDiarias\",Me)";
_httplogin._initialize /*String*/ (processBA,"transDiarias",chat.getObject());
 //BA.debugLineNum = 71;BA.debugLine="HttpLogin.PostString(Generales.Url& \"ConsulYtch\"";
_httplogin._poststring /*String*/ (parent.mostCurrent._generales._url /*String*/ +"ConsulYtch","pOrigen="+parent.mostCurrent._generales._usuario /*String*/ +"&pDestino="+BA.ObjectToString(parent.mostCurrent._generales._destino /*anywheresoftware.b4a.objects.collections.Map*/ .Get((Object)("pusuario")))+"&pCount="+BA.NumberToString((parent._filas)));
 //BA.debugLineNum = 72;BA.debugLine="HttpLogin.GetRequest.SetContentType(\"application";
_httplogin._getrequest /*anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpRequest*/ ().SetContentType("application/x-www-form-urlencoded");
 //BA.debugLineNum = 73;BA.debugLine="HttpLogin.GetRequest.SetHeader(\"Accept\",\"applica";
_httplogin._getrequest /*anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpRequest*/ ().SetHeader("Accept","application/json");
 //BA.debugLineNum = 74;BA.debugLine="HttpLogin.GetRequest.SetHeader(\"Authorization\",\"";
_httplogin._getrequest /*anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpRequest*/ ().SetHeader("Authorization","AIzaSyDYOkkTJiTPVd2U7EaTOAwhcN9ySH6GoHxOIYOM");
 //BA.debugLineNum = 75;BA.debugLine="HttpLogin.GetRequest.Timeout=20000";
_httplogin._getrequest /*anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpRequest*/ ().setTimeout((int) (20000));
 //BA.debugLineNum = 76;BA.debugLine="Wait For (HttpLogin) JobDone(JobServer As HttpJo";
anywheresoftware.b4a.keywords.Common.WaitFor("jobdone", processBA, this, (Object)(_httplogin));
this.state = 23;
return;
case 23:
//C
this.state = 4;
_jobserver = (ChatHN.com.httpjob) result[0];
;
 //BA.debugLineNum = 77;BA.debugLine="If JobServer.Success = True Then";
if (true) break;

case 4:
//if
this.state = 19;
if (_jobserver._success /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
this.state = 6;
}else {
this.state = 18;
}if (true) break;

case 6:
//C
this.state = 7;
 //BA.debugLineNum = 78;BA.debugLine="response=JobServer.GetString";
_response = _jobserver._getstring /*String*/ ();
 //BA.debugLineNum = 79;BA.debugLine="Dim json As JSONParser";
_json = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 80;BA.debugLine="json.Initialize(response)";
_json.Initialize(_response);
 //BA.debugLineNum = 81;BA.debugLine="Dim m As Map = json.NextObject";
_m = new anywheresoftware.b4a.objects.collections.Map();
_m = _json.NextObject();
 //BA.debugLineNum = 82;BA.debugLine="m= m.Get(\"wsrespuesta\")";
_m.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(_m.Get((Object)("wsrespuesta"))));
 //BA.debugLineNum = 83;BA.debugLine="Dim ValorCod As String= m.Get(\"codError\")";
_valorcod = BA.ObjectToString(_m.Get((Object)("codError")));
 //BA.debugLineNum = 84;BA.debugLine="Dim mDeta As Map";
_mdeta = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 85;BA.debugLine="mDeta.Initialize";
_mdeta.Initialize();
 //BA.debugLineNum = 86;BA.debugLine="DatosDetalle.Initialize";
parent.mostCurrent._datosdetalle.Initialize();
 //BA.debugLineNum = 87;BA.debugLine="If(ValorCod.EqualsIgnoreCase(\"0\"))Then";
if (true) break;

case 7:
//if
this.state = 16;
if ((_valorcod.equalsIgnoreCase("0"))) { 
this.state = 9;
}else {
this.state = 15;
}if (true) break;

case 9:
//C
this.state = 10;
 //BA.debugLineNum = 90;BA.debugLine="Dim ListDatos As List";
_listdatos = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 91;BA.debugLine="ListDatos.Initialize";
_listdatos.Initialize();
 //BA.debugLineNum = 92;BA.debugLine="ListDatos=m.Get(\"data\")";
_listdatos.setObject((java.util.List)(_m.Get((Object)("data"))));
 //BA.debugLineNum = 93;BA.debugLine="For i=0 To ListDatos.Size-1";
if (true) break;

case 10:
//for
this.state = 13;
step25 = 1;
limit25 = (int) (_listdatos.getSize()-1);
_i = (int) (0) ;
this.state = 24;
if (true) break;

case 24:
//C
this.state = 13;
if ((step25 > 0 && _i <= limit25) || (step25 < 0 && _i >= limit25)) this.state = 12;
if (true) break;

case 25:
//C
this.state = 24;
_i = ((int)(0 + _i + step25)) ;
if (true) break;

case 12:
//C
this.state = 25;
 //BA.debugLineNum = 94;BA.debugLine="Dim Deta As Map=ListDatos.Get(i)";
_deta = new anywheresoftware.b4a.objects.collections.Map();
_deta.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(_listdatos.Get(_i)));
 //BA.debugLineNum = 95;BA.debugLine="mDeta.Initialize";
_mdeta.Initialize();
 //BA.debugLineNum = 96;BA.debugLine="mDeta.Put(\"fecha\",Deta.Get(\"fecha\"))";
_mdeta.Put((Object)("fecha"),_deta.Get((Object)("fecha")));
 //BA.debugLineNum = 97;BA.debugLine="mDeta.Put(\"hora\",Deta.Get(\"hora\"))";
_mdeta.Put((Object)("hora"),_deta.Get((Object)("hora")));
 //BA.debugLineNum = 98;BA.debugLine="mDeta.Put(\"Secuencia\",Deta.Get(\"Secuencia\"))";
_mdeta.Put((Object)("Secuencia"),_deta.Get((Object)("Secuencia")));
 //BA.debugLineNum = 99;BA.debugLine="mDeta.Put(\"origen\",Deta.Get(\"origen\"))";
_mdeta.Put((Object)("origen"),_deta.Get((Object)("origen")));
 //BA.debugLineNum = 100;BA.debugLine="mDeta.Put(\"destino\",Deta.Get(\"destino\"))";
_mdeta.Put((Object)("destino"),_deta.Get((Object)("destino")));
 //BA.debugLineNum = 101;BA.debugLine="mDeta.Put(\"mensaje\",Deta.Get(\"mensaje\"))";
_mdeta.Put((Object)("mensaje"),_deta.Get((Object)("mensaje")));
 //BA.debugLineNum = 102;BA.debugLine="DatosDetalle.Add(mDeta)";
parent.mostCurrent._datosdetalle.Add((Object)(_mdeta.getObject()));
 if (true) break;
if (true) break;

case 13:
//C
this.state = 16;
;
 //BA.debugLineNum = 105;BA.debugLine="CargarDetalle";
_cargardetalle();
 //BA.debugLineNum = 106;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 if (true) break;

case 15:
//C
this.state = 16;
 //BA.debugLineNum = 108;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 if (true) break;

case 16:
//C
this.state = 19;
;
 if (true) break;

case 18:
//C
this.state = 19;
 //BA.debugLineNum = 112;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 if (true) break;

case 19:
//C
this.state = 22;
;
 if (true) break;

case 21:
//C
this.state = 22;
this.catchState = 0;
 //BA.debugLineNum = 116;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 if (true) break;
if (true) break;

case 22:
//C
this.state = -1;
this.catchState = 0;
;
 //BA.debugLineNum = 119;BA.debugLine="End Sub";
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
public static void  _enviar() throws Exception{
ResumableSub_Enviar rsub = new ResumableSub_Enviar(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_Enviar extends BA.ResumableSub {
public ResumableSub_Enviar(ChatHN.com.chat parent) {
this.parent = parent;
}
ChatHN.com.chat parent;
String _sms = "";
String _response = "";
ChatHN.com.httpjob _httpinstytch = null;
ChatHN.com.httpjob _jobserver = null;
anywheresoftware.b4a.objects.collections.JSONParser _json = null;
anywheresoftware.b4a.objects.collections.Map _m = null;
String _valorcod = "";
anywheresoftware.b4a.objects.collections.Map _mdeta = null;

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
 //BA.debugLineNum = 207;BA.debugLine="Try";
if (true) break;

case 1:
//try
this.state = 18;
this.catchState = 17;
this.state = 3;
if (true) break;

case 3:
//C
this.state = 4;
this.catchState = 17;
 //BA.debugLineNum = 208;BA.debugLine="Dim sms As String=TxtMsj.Text";
_sms = parent.mostCurrent._txtmsj.getText();
 //BA.debugLineNum = 209;BA.debugLine="TxtMsj.Text=\"\"";
parent.mostCurrent._txtmsj.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 211;BA.debugLine="ProgressDialogShow(\"\")";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 212;BA.debugLine="Dim response As String";
_response = "";
 //BA.debugLineNum = 213;BA.debugLine="Dim HttpInstYtch As HttpJob";
_httpinstytch = new ChatHN.com.httpjob();
 //BA.debugLineNum = 214;BA.debugLine="HttpInstYtch.Initialize(\"InstYtch\",Me)";
_httpinstytch._initialize /*String*/ (processBA,"InstYtch",chat.getObject());
 //BA.debugLineNum = 215;BA.debugLine="HttpInstYtch.PostString(Generales.Url& \"InstYtch";
_httpinstytch._poststring /*String*/ (parent.mostCurrent._generales._url /*String*/ +"InstYtch","pOrigen="+parent.mostCurrent._generales._usuario /*String*/ +"&pDestino="+BA.ObjectToString(parent.mostCurrent._generales._destino /*anywheresoftware.b4a.objects.collections.Map*/ .Get((Object)("pusuario")))+"&pMensaje="+_sms);
 //BA.debugLineNum = 216;BA.debugLine="HttpInstYtch.GetRequest.SetContentType(\"applicat";
_httpinstytch._getrequest /*anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpRequest*/ ().SetContentType("application/x-www-form-urlencoded");
 //BA.debugLineNum = 217;BA.debugLine="HttpInstYtch.GetRequest.SetHeader(\"Accept\",\"appl";
_httpinstytch._getrequest /*anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpRequest*/ ().SetHeader("Accept","application/json");
 //BA.debugLineNum = 218;BA.debugLine="HttpInstYtch.GetRequest.SetHeader(\"Authorization";
_httpinstytch._getrequest /*anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpRequest*/ ().SetHeader("Authorization","AIzaSyDYOkkTJiTPVd2U7EaTOAwhcN9ySH6GoHxOIYOM");
 //BA.debugLineNum = 219;BA.debugLine="HttpInstYtch.GetRequest.Timeout=20000";
_httpinstytch._getrequest /*anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpRequest*/ ().setTimeout((int) (20000));
 //BA.debugLineNum = 220;BA.debugLine="Wait For (HttpInstYtch) JobDone(JobServer As Htt";
anywheresoftware.b4a.keywords.Common.WaitFor("jobdone", processBA, this, (Object)(_httpinstytch));
this.state = 19;
return;
case 19:
//C
this.state = 4;
_jobserver = (ChatHN.com.httpjob) result[0];
;
 //BA.debugLineNum = 221;BA.debugLine="If JobServer.Success = True Then";
if (true) break;

case 4:
//if
this.state = 15;
if (_jobserver._success /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
this.state = 6;
}else {
this.state = 14;
}if (true) break;

case 6:
//C
this.state = 7;
 //BA.debugLineNum = 222;BA.debugLine="response=JobServer.GetString";
_response = _jobserver._getstring /*String*/ ();
 //BA.debugLineNum = 223;BA.debugLine="Dim json As JSONParser";
_json = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 224;BA.debugLine="json.Initialize(response)";
_json.Initialize(_response);
 //BA.debugLineNum = 225;BA.debugLine="Dim m As Map = json.NextObject";
_m = new anywheresoftware.b4a.objects.collections.Map();
_m = _json.NextObject();
 //BA.debugLineNum = 226;BA.debugLine="m= m.Get(\"wsrespuesta\")";
_m.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(_m.Get((Object)("wsrespuesta"))));
 //BA.debugLineNum = 227;BA.debugLine="Dim ValorCod As String= m.Get(\"codError\")";
_valorcod = BA.ObjectToString(_m.Get((Object)("codError")));
 //BA.debugLineNum = 228;BA.debugLine="Dim mDeta As Map";
_mdeta = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 229;BA.debugLine="mDeta.Initialize";
_mdeta.Initialize();
 //BA.debugLineNum = 230;BA.debugLine="DatosDetalle.Initialize";
parent.mostCurrent._datosdetalle.Initialize();
 //BA.debugLineNum = 231;BA.debugLine="If(ValorCod.EqualsIgnoreCase(\"0\"))Then";
if (true) break;

case 7:
//if
this.state = 12;
if ((_valorcod.equalsIgnoreCase("0"))) { 
this.state = 9;
}else {
this.state = 11;
}if (true) break;

case 9:
//C
this.state = 12;
 //BA.debugLineNum = 232;BA.debugLine="senPush(sms)";
_senpush(_sms);
 if (true) break;

case 11:
//C
this.state = 12;
 //BA.debugLineNum = 237;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 238;BA.debugLine="Msgbox(\"Error al enviar.\",\"Notificatión!!!\")";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Error al enviar."),BA.ObjectToCharSequence("Notificatión!!!"),mostCurrent.activityBA);
 if (true) break;

case 12:
//C
this.state = 15;
;
 if (true) break;

case 14:
//C
this.state = 15;
 //BA.debugLineNum = 241;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 242;BA.debugLine="Msgbox(\"Error de conexión\"&CRLF&\"Valide sus dat";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Error de conexión"+anywheresoftware.b4a.keywords.Common.CRLF+"Valide sus datos moviles"),BA.ObjectToCharSequence("Notificación!!!"),mostCurrent.activityBA);
 if (true) break;

case 15:
//C
this.state = 18;
;
 if (true) break;

case 17:
//C
this.state = 18;
this.catchState = 0;
 //BA.debugLineNum = 245;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 246;BA.debugLine="Msgbox(\"Error al enviar...\",\"Mensaje!!!\")";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Error al enviar..."),BA.ObjectToCharSequence("Mensaje!!!"),mostCurrent.activityBA);
 if (true) break;
if (true) break;

case 18:
//C
this.state = -1;
this.catchState = 0;
;
 //BA.debugLineNum = 248;BA.debugLine="End Sub";
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
 //BA.debugLineNum = 15;BA.debugLine="Dim DatosDetalle As List";
mostCurrent._datosdetalle = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 16;BA.debugLine="Private ScrollView1 As ScrollView";
mostCurrent._scrollview1 = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 17;BA.debugLine="Private dra As ColorDrawable";
mostCurrent._dra = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 18;BA.debugLine="Private TxtMsj As EditText";
mostCurrent._txtmsj = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Dim IME As IME";
mostCurrent._ime = new anywheresoftware.b4a.objects.IME();
 //BA.debugLineNum = 20;BA.debugLine="Private LblNombre As Label";
mostCurrent._lblnombre = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Private filas As Int=0";
_filas = (int) (0);
 //BA.debugLineNum = 24;BA.debugLine="Dim TotalPanelHeightFecha As Int=0";
_totalpanelheightfecha = (int) (0);
 //BA.debugLineNum = 25;BA.debugLine="Dim PanelHeightFecha As Int=0";
_panelheightfecha = (int) (0);
 //BA.debugLineNum = 26;BA.debugLine="Dim TotalPanelHeight As Int=0";
_totalpanelheight = (int) (0);
 //BA.debugLineNum = 27;BA.debugLine="End Sub";
return "";
}
public static String  _label3_click() throws Exception{
 //BA.debugLineNum = 49;BA.debugLine="Sub Label3_Click";
 //BA.debugLineNum = 50;BA.debugLine="timer1.Enabled=False";
_timer1.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 51;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 52;BA.debugLine="End Sub";
return "";
}
public static String  _lblenviar_click() throws Exception{
 //BA.debugLineNum = 198;BA.debugLine="Sub LblEnviar_Click";
 //BA.debugLineNum = 199;BA.debugLine="If( TxtMsj.Text.EqualsIgnoreCase(\"\")) Then";
if ((mostCurrent._txtmsj.getText().equalsIgnoreCase(""))) { 
 //BA.debugLineNum = 200;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 202;BA.debugLine="Enviar";
_enviar();
 //BA.debugLineNum = 203;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Private timer1 As Timer";
_timer1 = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 10;BA.debugLine="End Sub";
return "";
}
public static void  _senpush(String _sms) throws Exception{
ResumableSub_senPush rsub = new ResumableSub_senPush(null,_sms);
rsub.resume(processBA, null);
}
public static class ResumableSub_senPush extends BA.ResumableSub {
public ResumableSub_senPush(ChatHN.com.chat parent,String _sms) {
this.parent = parent;
this._sms = _sms;
}
ChatHN.com.chat parent;
String _sms;
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
 //BA.debugLineNum = 252;BA.debugLine="Try";
if (true) break;

case 1:
//try
this.state = 12;
this.catchState = 11;
this.state = 3;
if (true) break;

case 3:
//C
this.state = 4;
this.catchState = 11;
 //BA.debugLineNum = 253;BA.debugLine="Dim Topic As  String=Generales.Destino.Get(\"pusu";
_topic = BA.ObjectToString(parent.mostCurrent._generales._destino /*anywheresoftware.b4a.objects.collections.Map*/ .Get((Object)("pusuario")));
 //BA.debugLineNum = 254;BA.debugLine="Dim m As Map=CreateMap(\"to\": $\"/topics/${Topic}\"";
_m = new anywheresoftware.b4a.objects.collections.Map();
_m = anywheresoftware.b4a.keywords.Common.createMap(new Object[] {(Object)("to"),(Object)(("/topics/"+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(_topic))+""))});
 //BA.debugLineNum = 255;BA.debugLine="Dim data As Map = CreateMap(\"title\": Generales.N";
_data = new anywheresoftware.b4a.objects.collections.Map();
_data = anywheresoftware.b4a.keywords.Common.createMap(new Object[] {(Object)("title"),(Object)(parent.mostCurrent._generales._nombreusuario /*String*/ ),(Object)("body"),(Object)(_sms)});
 //BA.debugLineNum = 256;BA.debugLine="m.Put(\"data\",data)";
_m.Put((Object)("data"),(Object)(_data.getObject()));
 //BA.debugLineNum = 258;BA.debugLine="Dim json As JSONGenerator";
_json = new anywheresoftware.b4a.objects.collections.JSONParser.JSONGenerator();
 //BA.debugLineNum = 259;BA.debugLine="json.Initialize(m)";
_json.Initialize(_m);
 //BA.debugLineNum = 260;BA.debugLine="Dim response As String";
_response = "";
 //BA.debugLineNum = 261;BA.debugLine="Dim HttpMenu As HttpJob";
_httpmenu = new ChatHN.com.httpjob();
 //BA.debugLineNum = 262;BA.debugLine="HttpMenu.Initialize(\"HttpMenu\",Me)";
_httpmenu._initialize /*String*/ (processBA,"HttpMenu",chat.getObject());
 //BA.debugLineNum = 263;BA.debugLine="HttpMenu.PostString(\"https://fcm.googleapis.com/";
_httpmenu._poststring /*String*/ ("https://fcm.googleapis.com/fcm/send",_json.ToString());
 //BA.debugLineNum = 264;BA.debugLine="HttpMenu.GetRequest.SetContentType(\"application/";
_httpmenu._getrequest /*anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpRequest*/ ().SetContentType("application/json");
 //BA.debugLineNum = 266;BA.debugLine="HttpMenu.GetRequest.SetHeader(\"Authorization\",\"k";
_httpmenu._getrequest /*anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpRequest*/ ().SetHeader("Authorization","key=AAAAqN4BOMc:APA91bGQ2A8TKizIZMaVmoFBFdrK8q699eGff4j4igdloe-tEQzx1f7QDTTM2LOXbn7AodY5IWhZcLlvk4Zjd1Uv8B2l6hyEklEFtb0OHMiDoues6Ae9JM8JMsAuzQqjfEH6GNTCELaU");
 //BA.debugLineNum = 267;BA.debugLine="HttpMenu.GetRequest.Timeout=20000";
_httpmenu._getrequest /*anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpRequest*/ ().setTimeout((int) (20000));
 //BA.debugLineNum = 268;BA.debugLine="Wait For (HttpMenu) JobDone(JobServer As HttpJob";
anywheresoftware.b4a.keywords.Common.WaitFor("jobdone", processBA, this, (Object)(_httpmenu));
this.state = 13;
return;
case 13:
//C
this.state = 4;
_jobserver = (ChatHN.com.httpjob) result[0];
;
 //BA.debugLineNum = 269;BA.debugLine="If JobServer.Success = True Then";
if (true) break;

case 4:
//if
this.state = 9;
if (_jobserver._success /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
this.state = 6;
}else {
this.state = 8;
}if (true) break;

case 6:
//C
this.state = 9;
 //BA.debugLineNum = 270;BA.debugLine="response=JobServer.GetString";
_response = _jobserver._getstring /*String*/ ();
 //BA.debugLineNum = 271;BA.debugLine="TxtMsj.Text=\"\";";
parent.mostCurrent._txtmsj.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 272;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 if (true) break;

case 8:
//C
this.state = 9;
 //BA.debugLineNum = 274;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 if (true) break;

case 9:
//C
this.state = 12;
;
 if (true) break;

case 11:
//C
this.state = 12;
this.catchState = 0;
 //BA.debugLineNum = 277;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("51572891",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 if (true) break;
if (true) break;

case 12:
//C
this.state = -1;
this.catchState = 0;
;
 //BA.debugLineNum = 279;BA.debugLine="End Sub";
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
public static String  _timer1_tick() throws Exception{
 //BA.debugLineNum = 61;BA.debugLine="Sub Timer1_Tick";
 //BA.debugLineNum = 63;BA.debugLine="CargarTransacciones";
_cargartransacciones();
 //BA.debugLineNum = 64;BA.debugLine="End Sub";
return "";
}
public static String  _txtmsj_enterpressed() throws Exception{
 //BA.debugLineNum = 194;BA.debugLine="Sub TxtMsj_EnterPressed";
 //BA.debugLineNum = 195;BA.debugLine="Enviar";
_enviar();
 //BA.debugLineNum = 196;BA.debugLine="End Sub";
return "";
}
}
