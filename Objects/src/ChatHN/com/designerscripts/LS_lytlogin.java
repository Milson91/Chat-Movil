package ChatHN.com.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_lytlogin{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
views.get("imageview1").vw.setWidth((int)((110d * scale)));
views.get("imageview1").vw.setHeight((int)((120d * scale)));
views.get("imageview1").vw.setTop((int)((40d * scale)));
views.get("pnllogin").vw.setLeft((int)((10d * scale)));
views.get("pnllogin").vw.setWidth((int)((100d / 100 * width)-(10d * scale) - ((10d * scale))));
views.get("pnllogin").vw.setHeight((int)((300d * scale)));
views.get("pnllogin").vw.setTop((int)(((views.get("imageview1").vw.getTop() + views.get("imageview1").vw.getHeight())+(30d * scale))));
views.get("label1").vw.setLeft((int)((10d * scale)));
views.get("label1").vw.setWidth((int)((views.get("pnllogin").vw.getWidth())-(10d * scale) - ((10d * scale))));
views.get("label1").vw.setTop((int)((10d * scale)));
views.get("label1").vw.setHeight((int)((40d * scale) - ((10d * scale))));
views.get("label2").vw.setLeft((int)((10d * scale)));
views.get("label2").vw.setWidth((int)((70d * scale) - ((10d * scale))));
views.get("label2").vw.setTop((int)((views.get("label1").vw.getTop() + views.get("label1").vw.getHeight())+(3d * scale)));
views.get("label2").vw.setHeight((int)((views.get("label1").vw.getTop() + views.get("label1").vw.getHeight())+(53d * scale) - ((views.get("label1").vw.getTop() + views.get("label1").vw.getHeight())+(3d * scale))));
views.get("txtuser").vw.setLeft((int)((views.get("label2").vw.getLeft() + views.get("label2").vw.getWidth())+(2d * scale)));
views.get("txtuser").vw.setWidth((int)((views.get("pnllogin").vw.getWidth())-(10d * scale) - ((views.get("label2").vw.getLeft() + views.get("label2").vw.getWidth())+(2d * scale))));
views.get("txtuser").vw.setTop((int)((views.get("label1").vw.getTop() + views.get("label1").vw.getHeight())+(3d * scale)));
views.get("txtuser").vw.setHeight((int)((views.get("label1").vw.getTop() + views.get("label1").vw.getHeight())+(53d * scale) - ((views.get("label1").vw.getTop() + views.get("label1").vw.getHeight())+(3d * scale))));
views.get("label3").vw.setLeft((int)((10d * scale)));
views.get("label3").vw.setWidth((int)((70d * scale) - ((10d * scale))));
views.get("label3").vw.setTop((int)((views.get("label2").vw.getTop() + views.get("label2").vw.getHeight())+(3d * scale)));
views.get("label3").vw.setHeight((int)((views.get("label2").vw.getTop() + views.get("label2").vw.getHeight())+(53d * scale) - ((views.get("label2").vw.getTop() + views.get("label2").vw.getHeight())+(3d * scale))));
views.get("txtpass").vw.setLeft((int)((views.get("label2").vw.getLeft() + views.get("label2").vw.getWidth())+(2d * scale)));
views.get("txtpass").vw.setWidth((int)((views.get("pnllogin").vw.getWidth())-(10d * scale) - ((views.get("label2").vw.getLeft() + views.get("label2").vw.getWidth())+(2d * scale))));
views.get("txtpass").vw.setTop((int)((views.get("label2").vw.getTop() + views.get("label2").vw.getHeight())+(3d * scale)));
views.get("txtpass").vw.setHeight((int)((views.get("label2").vw.getTop() + views.get("label2").vw.getHeight())+(53d * scale) - ((views.get("label2").vw.getTop() + views.get("label2").vw.getHeight())+(3d * scale))));
views.get("btnlogin").vw.setLeft((int)((10d * scale)));
views.get("btnlogin").vw.setWidth((int)((views.get("pnllogin").vw.getWidth())-(10d * scale) - ((10d * scale))));
//BA.debugLineNum = 29;BA.debugLine="BtnLogin.SetTopAndBottom(TxtPass.Bottom+10dip,TxtPass.Bottom+60dip)"[lytLogin/General script]
views.get("btnlogin").vw.setTop((int)((views.get("txtpass").vw.getTop() + views.get("txtpass").vw.getHeight())+(10d * scale)));
views.get("btnlogin").vw.setHeight((int)((views.get("txtpass").vw.getTop() + views.get("txtpass").vw.getHeight())+(60d * scale) - ((views.get("txtpass").vw.getTop() + views.get("txtpass").vw.getHeight())+(10d * scale))));
//BA.debugLineNum = 31;BA.debugLine="Chb.SetLeftAndRight(10dip,PnlLogin.Width-150dip)"[lytLogin/General script]
views.get("chb").vw.setLeft((int)((10d * scale)));
views.get("chb").vw.setWidth((int)((views.get("pnllogin").vw.getWidth())-(150d * scale) - ((10d * scale))));
//BA.debugLineNum = 32;BA.debugLine="Chb.SetTopAndBottom(BtnLogin.Bottom+5dip,BtnLogin.Bottom+60dip)"[lytLogin/General script]
views.get("chb").vw.setTop((int)((views.get("btnlogin").vw.getTop() + views.get("btnlogin").vw.getHeight())+(5d * scale)));
views.get("chb").vw.setHeight((int)((views.get("btnlogin").vw.getTop() + views.get("btnlogin").vw.getHeight())+(60d * scale) - ((views.get("btnlogin").vw.getTop() + views.get("btnlogin").vw.getHeight())+(5d * scale))));
//BA.debugLineNum = 34;BA.debugLine="LblRegistre.SetLeftAndRight(Chb.Right,PnlLogin.Width-15dip)"[lytLogin/General script]
views.get("lblregistre").vw.setLeft((int)((views.get("chb").vw.getLeft() + views.get("chb").vw.getWidth())));
views.get("lblregistre").vw.setWidth((int)((views.get("pnllogin").vw.getWidth())-(15d * scale) - ((views.get("chb").vw.getLeft() + views.get("chb").vw.getWidth()))));
//BA.debugLineNum = 35;BA.debugLine="LblRegistre.SetTopAndBottom(BtnLogin.Bottom+5dip,BtnLogin.Bottom+60dip)"[lytLogin/General script]
views.get("lblregistre").vw.setTop((int)((views.get("btnlogin").vw.getTop() + views.get("btnlogin").vw.getHeight())+(5d * scale)));
views.get("lblregistre").vw.setHeight((int)((views.get("btnlogin").vw.getTop() + views.get("btnlogin").vw.getHeight())+(60d * scale) - ((views.get("btnlogin").vw.getTop() + views.get("btnlogin").vw.getHeight())+(5d * scale))));

}
}