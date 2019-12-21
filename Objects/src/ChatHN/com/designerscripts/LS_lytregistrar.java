package ChatHN.com.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_lytregistrar{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
views.get("pnlsuperior").vw.setLeft((int)(0d));
views.get("pnlsuperior").vw.setWidth((int)((100d / 100 * width) - (0d)));
views.get("pnlsuperior").vw.setTop((int)(0d));
views.get("pnlsuperior").vw.setHeight((int)((70d * scale) - (0d)));
views.get("label1").vw.setLeft((int)(0d));
views.get("label1").vw.setWidth((int)((100d / 100 * width) - (0d)));
views.get("label1").vw.setTop((int)((0d * scale)));
views.get("label1").vw.setHeight((int)((70d * scale) - ((0d * scale))));
views.get("panel1").vw.setLeft((int)((2d * scale)));
views.get("panel1").vw.setWidth((int)((100d / 100 * width)-(4d * scale) - ((2d * scale))));
views.get("panel1").vw.setTop((int)((views.get("pnlsuperior").vw.getTop() + views.get("pnlsuperior").vw.getHeight())));
views.get("panel1").vw.setHeight((int)((100d / 100 * height)-(2d * scale) - ((views.get("pnlsuperior").vw.getTop() + views.get("pnlsuperior").vw.getHeight()))));
views.get("label2").vw.setLeft((int)((5d * scale)));
views.get("label2").vw.setWidth((int)((100d / 100 * width)-(10d * scale) - ((5d * scale))));
views.get("label2").vw.setTop((int)((3d * scale)));
views.get("label2").vw.setHeight((int)((30d * scale) - ((3d * scale))));
views.get("txtusuario").vw.setLeft((int)((5d * scale)));
views.get("txtusuario").vw.setWidth((int)((100d / 100 * width)-(10d * scale) - ((5d * scale))));
views.get("txtusuario").vw.setTop((int)((views.get("label2").vw.getTop() + views.get("label2").vw.getHeight())));
views.get("txtusuario").vw.setHeight((int)((views.get("label2").vw.getTop() + views.get("label2").vw.getHeight())+(45d * scale) - ((views.get("label2").vw.getTop() + views.get("label2").vw.getHeight()))));
views.get("label3").vw.setLeft((int)((5d * scale)));
views.get("label3").vw.setWidth((int)((100d / 100 * width)-(10d * scale) - ((5d * scale))));
views.get("label3").vw.setTop((int)((views.get("txtusuario").vw.getTop() + views.get("txtusuario").vw.getHeight())));
views.get("label3").vw.setHeight((int)((views.get("txtusuario").vw.getTop() + views.get("txtusuario").vw.getHeight())+(30d * scale) - ((views.get("txtusuario").vw.getTop() + views.get("txtusuario").vw.getHeight()))));
views.get("txtnombr").vw.setLeft((int)((5d * scale)));
views.get("txtnombr").vw.setWidth((int)((100d / 100 * width)-(10d * scale) - ((5d * scale))));
views.get("txtnombr").vw.setTop((int)((views.get("label3").vw.getTop() + views.get("label3").vw.getHeight())));
views.get("txtnombr").vw.setHeight((int)((views.get("label3").vw.getTop() + views.get("label3").vw.getHeight())+(45d * scale) - ((views.get("label3").vw.getTop() + views.get("label3").vw.getHeight()))));
views.get("label4").vw.setLeft((int)((5d * scale)));
views.get("label4").vw.setWidth((int)((100d / 100 * width)-(10d * scale) - ((5d * scale))));
views.get("label4").vw.setTop((int)((views.get("txtnombr").vw.getTop() + views.get("txtnombr").vw.getHeight())));
views.get("label4").vw.setHeight((int)((views.get("txtnombr").vw.getTop() + views.get("txtnombr").vw.getHeight())+(30d * scale) - ((views.get("txtnombr").vw.getTop() + views.get("txtnombr").vw.getHeight()))));
views.get("txtapellido").vw.setLeft((int)((5d * scale)));
views.get("txtapellido").vw.setWidth((int)((100d / 100 * width)-(10d * scale) - ((5d * scale))));
views.get("txtapellido").vw.setTop((int)((views.get("label4").vw.getTop() + views.get("label4").vw.getHeight())));
views.get("txtapellido").vw.setHeight((int)((views.get("label4").vw.getTop() + views.get("label4").vw.getHeight())+(45d * scale) - ((views.get("label4").vw.getTop() + views.get("label4").vw.getHeight()))));
views.get("label5").vw.setLeft((int)((5d * scale)));
views.get("label5").vw.setWidth((int)((100d / 100 * width)-(10d * scale) - ((5d * scale))));
views.get("label5").vw.setTop((int)((views.get("txtapellido").vw.getTop() + views.get("txtapellido").vw.getHeight())));
views.get("label5").vw.setHeight((int)((views.get("txtapellido").vw.getTop() + views.get("txtapellido").vw.getHeight())+(30d * scale) - ((views.get("txtapellido").vw.getTop() + views.get("txtapellido").vw.getHeight()))));
views.get("txtemail").vw.setLeft((int)((5d * scale)));
views.get("txtemail").vw.setWidth((int)((100d / 100 * width)-(10d * scale) - ((5d * scale))));
//BA.debugLineNum = 33;BA.debugLine="TxtEmail.SetTopAndBottom(Label5.Bottom+3dip,Label5.Bottom+45dip)"[lytRegistrar/General script]
views.get("txtemail").vw.setTop((int)((views.get("label5").vw.getTop() + views.get("label5").vw.getHeight())+(3d * scale)));
views.get("txtemail").vw.setHeight((int)((views.get("label5").vw.getTop() + views.get("label5").vw.getHeight())+(45d * scale) - ((views.get("label5").vw.getTop() + views.get("label5").vw.getHeight())+(3d * scale))));
//BA.debugLineNum = 35;BA.debugLine="Label6.SetLeftAndRight(5dip,100%x-10dip)"[lytRegistrar/General script]
views.get("label6").vw.setLeft((int)((5d * scale)));
views.get("label6").vw.setWidth((int)((100d / 100 * width)-(10d * scale) - ((5d * scale))));
//BA.debugLineNum = 36;BA.debugLine="Label6.SetTopAndBottom(TxtEmail.Bottom,TxtEmail.Bottom+30dip)"[lytRegistrar/General script]
views.get("label6").vw.setTop((int)((views.get("txtemail").vw.getTop() + views.get("txtemail").vw.getHeight())));
views.get("label6").vw.setHeight((int)((views.get("txtemail").vw.getTop() + views.get("txtemail").vw.getHeight())+(30d * scale) - ((views.get("txtemail").vw.getTop() + views.get("txtemail").vw.getHeight()))));
//BA.debugLineNum = 38;BA.debugLine="TxtClave.SetLeftAndRight(5dip,100%x-10dip)"[lytRegistrar/General script]
views.get("txtclave").vw.setLeft((int)((5d * scale)));
views.get("txtclave").vw.setWidth((int)((100d / 100 * width)-(10d * scale) - ((5d * scale))));
//BA.debugLineNum = 39;BA.debugLine="TxtClave.SetTopAndBottom(Label6.Bottom,Label6.Bottom+45dip)"[lytRegistrar/General script]
views.get("txtclave").vw.setTop((int)((views.get("label6").vw.getTop() + views.get("label6").vw.getHeight())));
views.get("txtclave").vw.setHeight((int)((views.get("label6").vw.getTop() + views.get("label6").vw.getHeight())+(45d * scale) - ((views.get("label6").vw.getTop() + views.get("label6").vw.getHeight()))));
//BA.debugLineNum = 41;BA.debugLine="BtnRegistrar.SetLeftAndRight(5dip,100%x-10dip)"[lytRegistrar/General script]
views.get("btnregistrar").vw.setLeft((int)((5d * scale)));
views.get("btnregistrar").vw.setWidth((int)((100d / 100 * width)-(10d * scale) - ((5d * scale))));
//BA.debugLineNum = 42;BA.debugLine="BtnRegistrar.SetTopAndBottom(TxtClave.Bottom,TxtClave.Bottom+63dip)"[lytRegistrar/General script]
views.get("btnregistrar").vw.setTop((int)((views.get("txtclave").vw.getTop() + views.get("txtclave").vw.getHeight())));
views.get("btnregistrar").vw.setHeight((int)((views.get("txtclave").vw.getTop() + views.get("txtclave").vw.getHeight())+(63d * scale) - ((views.get("txtclave").vw.getTop() + views.get("txtclave").vw.getHeight()))));

}
}