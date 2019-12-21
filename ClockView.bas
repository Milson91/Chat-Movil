Type=Class
Version=7.3
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
'Custom View class 
#DesignerProperty: Key: ShowSeconds, DisplayName: Show Seconds, FieldType: Boolean, DefaultValue: True
#DesignerProperty: Key: TextColor, DisplayName: Text Color, FieldType: Color, DefaultValue: 0xFFA2A2A2, Description: Text color

Sub Class_Globals
	Private mEventName As String 'ignore
	Private mCallBack As Object 'ignore
	Private mBase As B4XView
	Private mLbl As B4XView
	Private xui As XUI
	Private Timer1 As Timer
	Private showSeconds As Boolean
End Sub

Public Sub Initialize (Callback As Object, EventName As String)
	mEventName = EventName
	mCallBack = Callback
	Timer1.Initialize("Timer1", 1000)
End Sub

'Base type must be Object
Public Sub DesignerCreateView (Base As Object, Lbl As Label, Props As Map)
	mBase = Base
	mLbl = Lbl
    mBase.AddView(mLbl, 0, 0, mBase.Width, mBase.Height)
	mLbl.SetTextAlignment("CENTER", "CENTER")
	mLbl.TextColor = xui.PaintOrColorToColor(Props.Get("TextColor"))
	showSeconds = Props.Get("ShowSeconds")
	Timer1.Enabled = True
	Timer1_Tick
End Sub

Private Sub Base_Resize (Width As Double, Height As Double)
	mLbl.SetLayoutAnimated(0, 0, 0, Width, Height)
End Sub

Sub Timer1_Tick
	Dim t As Long = DateTime.Now
	mLbl.Text = $"$2.0{DateTime.GetHour(t)}:$2.0{DateTime.GetMinute(t)}"$
	If showSeconds Then
		mLbl.Text = mLbl.Text & $":$2.0{DateTime.GetSecond(t)}"$
	End If
End Sub

