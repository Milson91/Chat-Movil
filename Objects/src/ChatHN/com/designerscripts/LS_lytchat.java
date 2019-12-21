package ChatHN.com.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_lytchat{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
//BA.debugLineNum = 2;BA.debugLine="AutoScaleAll"[lytchat/General script]
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
//BA.debugLineNum = 4;BA.debugLine="Panel1.SetLeftAndRight(0,100%x)"[lytchat/General script]
views.get("panel1").vw.setLeft((int)(0d));
views.get("panel1").vw.setWidth((int)((100d / 100 * width) - (0d)));
//BA.debugLineNum = 5;BA.debugLine="Panel1.SetTopAndBottom(0,60dip)"[lytchat/General script]
views.get("panel1").vw.setTop((int)(0d));
views.get("panel1").vw.setHeight((int)((60d * scale) - (0d)));
//BA.debugLineNum = 6;BA.debugLine="Label3.SetLeftAndRight(0,30dip)"[lytchat/General script]
views.get("label3").vw.setLeft((int)(0d));
views.get("label3").vw.setWidth((int)((30d * scale) - (0d)));
//BA.debugLineNum = 7;BA.debugLine="Label3.SetTopAndBottom(0,Panel1.Height)"[lytchat/General script]
views.get("label3").vw.setTop((int)(0d));
views.get("label3").vw.setHeight((int)((views.get("panel1").vw.getHeight()) - (0d)));
//BA.debugLineNum = 8;BA.debugLine="Label2.SetLeftAndRight(Label3.Right,Label3.Right+ 50dip)"[lytchat/General script]
views.get("label2").vw.setLeft((int)((views.get("label3").vw.getLeft() + views.get("label3").vw.getWidth())));
views.get("label2").vw.setWidth((int)((views.get("label3").vw.getLeft() + views.get("label3").vw.getWidth())+(50d * scale) - ((views.get("label3").vw.getLeft() + views.get("label3").vw.getWidth()))));
//BA.debugLineNum = 9;BA.debugLine="Label2.SetTopAndBottom(0,Panel1.Height)"[lytchat/General script]
views.get("label2").vw.setTop((int)(0d));
views.get("label2").vw.setHeight((int)((views.get("panel1").vw.getHeight()) - (0d)));
//BA.debugLineNum = 11;BA.debugLine="LblNombre.SetLeftAndRight(Label2.Right,100%x-5dip)"[lytchat/General script]
views.get("lblnombre").vw.setLeft((int)((views.get("label2").vw.getLeft() + views.get("label2").vw.getWidth())));
views.get("lblnombre").vw.setWidth((int)((100d / 100 * width)-(5d * scale) - ((views.get("label2").vw.getLeft() + views.get("label2").vw.getWidth()))));
//BA.debugLineNum = 12;BA.debugLine="LblNombre.SetTopAndBottom(0dip,60dip)"[lytchat/General script]
views.get("lblnombre").vw.setTop((int)((0d * scale)));
views.get("lblnombre").vw.setHeight((int)((60d * scale) - ((0d * scale))));
//BA.debugLineNum = 14;BA.debugLine="pnlPrincipal.SetLeftAndRight(0,100%x)"[lytchat/General script]
views.get("pnlprincipal").vw.setLeft((int)(0d));
views.get("pnlprincipal").vw.setWidth((int)((100d / 100 * width) - (0d)));
//BA.debugLineNum = 15;BA.debugLine="pnlPrincipal.SetTopAndBottom(Panel1.Bottom,100%y)"[lytchat/General script]
views.get("pnlprincipal").vw.setTop((int)((views.get("panel1").vw.getTop() + views.get("panel1").vw.getHeight())));
views.get("pnlprincipal").vw.setHeight((int)((100d / 100 * height) - ((views.get("panel1").vw.getTop() + views.get("panel1").vw.getHeight()))));
//BA.debugLineNum = 21;BA.debugLine="ScrollView1.SetLeftAndRight(10dip,pnlPrincipal.Width-10dip)"[lytchat/General script]
views.get("scrollview1").vw.setLeft((int)((10d * scale)));
views.get("scrollview1").vw.setWidth((int)((views.get("pnlprincipal").vw.getWidth())-(10d * scale) - ((10d * scale))));
//BA.debugLineNum = 22;BA.debugLine="ScrollView1.SetTopAndBottom(2dip,pnlPrincipal.Height-60dip)"[lytchat/General script]
views.get("scrollview1").vw.setTop((int)((2d * scale)));
views.get("scrollview1").vw.setHeight((int)((views.get("pnlprincipal").vw.getHeight())-(60d * scale) - ((2d * scale))));
//BA.debugLineNum = 24;BA.debugLine="PnlChat.SetLeftAndRight(5dip,100%x-55dip)"[lytchat/General script]
views.get("pnlchat").vw.setLeft((int)((5d * scale)));
views.get("pnlchat").vw.setWidth((int)((100d / 100 * width)-(55d * scale) - ((5d * scale))));
//BA.debugLineNum = 25;BA.debugLine="PnlChat.SetTopAndBottom(pnlPrincipal.Height-60dip,pnlPrincipal.Height-5dip)"[lytchat/General script]
views.get("pnlchat").vw.setTop((int)((views.get("pnlprincipal").vw.getHeight())-(60d * scale)));
views.get("pnlchat").vw.setHeight((int)((views.get("pnlprincipal").vw.getHeight())-(5d * scale) - ((views.get("pnlprincipal").vw.getHeight())-(60d * scale))));
//BA.debugLineNum = 27;BA.debugLine="TxtMsj.SetLeftAndRight(10dip,PnlChat.Width-7dip)"[lytchat/General script]
views.get("txtmsj").vw.setLeft((int)((10d * scale)));
views.get("txtmsj").vw.setWidth((int)((views.get("pnlchat").vw.getWidth())-(7d * scale) - ((10d * scale))));
//BA.debugLineNum = 28;BA.debugLine="TxtMsj.SetTopAndBottom(0,PnlChat.Height)"[lytchat/General script]
views.get("txtmsj").vw.setTop((int)(0d));
views.get("txtmsj").vw.setHeight((int)((views.get("pnlchat").vw.getHeight()) - (0d)));
//BA.debugLineNum = 30;BA.debugLine="LblEnviar.SetLeftAndRight(PnlChat.Right,pnlPrincipal.Width)"[lytchat/General script]
views.get("lblenviar").vw.setLeft((int)((views.get("pnlchat").vw.getLeft() + views.get("pnlchat").vw.getWidth())));
views.get("lblenviar").vw.setWidth((int)((views.get("pnlprincipal").vw.getWidth()) - ((views.get("pnlchat").vw.getLeft() + views.get("pnlchat").vw.getWidth()))));
//BA.debugLineNum = 31;BA.debugLine="LblEnviar.SetTopAndBottom(pnlPrincipal.Height-60dip,pnlPrincipal.Height-5dip)"[lytchat/General script]
views.get("lblenviar").vw.setTop((int)((views.get("pnlprincipal").vw.getHeight())-(60d * scale)));
views.get("lblenviar").vw.setHeight((int)((views.get("pnlprincipal").vw.getHeight())-(5d * scale) - ((views.get("pnlprincipal").vw.getHeight())-(60d * scale))));

}
}