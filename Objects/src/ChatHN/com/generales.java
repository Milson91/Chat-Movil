package ChatHN.com;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class generales {
private static generales mostCurrent = new generales();
public static Object getObject() {
    throw new RuntimeException("Code module does not support this method.");
}
 public anywheresoftware.b4a.keywords.Common __c = null;
public static String _usuario = "";
public static String _nombreusuario = "";
public static String _pass = "";
public static anywheresoftware.b4a.objects.collections.Map _destino = null;
public static String _url = "";
public ChatHN.com.main _main = null;
public ChatHN.com.chat _chat = null;
public ChatHN.com.starter _starter = null;
public ChatHN.com.menu _menu = null;
public ChatHN.com.registrar _registrar = null;
public ChatHN.com.firebasemessaging _firebasemessaging = null;
public ChatHN.com.httputils2service _httputils2service = null;
public static String  _convertstring(anywheresoftware.b4a.BA _ba,Object _value) throws Exception{
String _valor = "";
 //BA.debugLineNum = 17;BA.debugLine="Public Sub ConvertString(Value As Object) As Strin";
 //BA.debugLineNum = 18;BA.debugLine="Dim valor As String";
_valor = "";
 //BA.debugLineNum = 19;BA.debugLine="valor =Value";
_valor = BA.ObjectToString(_value);
 //BA.debugLineNum = 21;BA.debugLine="Return valor";
if (true) return _valor;
 //BA.debugLineNum = 23;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 3;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 5;BA.debugLine="Dim Usuario As String";
_usuario = "";
 //BA.debugLineNum = 6;BA.debugLine="dim NombreUsuario as String";
_nombreusuario = "";
 //BA.debugLineNum = 7;BA.debugLine="Dim Pass As String";
_pass = "";
 //BA.debugLineNum = 8;BA.debugLine="Dim Destino As Map";
_destino = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 10;BA.debugLine="Dim Url As String =\"https://app.astradts.com:8443";
_url = "https://app.astradts.com:8443/PortalIntranet-1.0/service/";
 //BA.debugLineNum = 14;BA.debugLine="End Sub";
return "";
}
}
