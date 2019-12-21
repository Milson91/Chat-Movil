B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Service
Version=9.3
@EndOfDesignText@
#Region  Service Attributes 
	#StartAtBoot: False
	
#End Region
Sub Process_Globals
	Private fm As FirebaseMessaging

End Sub

Sub Service_Create
	fm.Initialize("fm")
End Sub

Public Sub SubscribeToTopics
	Try
	    fm.SubscribeToTopic(Generales.Usuario) 'you can subscribe to more topics
	Catch
		Log(LastException)
	End Try

End Sub

Sub Service_Start (StartingIntent As Intent)
	If StartingIntent.IsInitialized Then
		 fm.HandleIntent(StartingIntent)
	End If
	Sleep(0)
	Service.StopAutomaticForeground 'remove if not using B4A v8+.
End Sub

Sub fm_MessageArrived (Message As RemoteMessage)
	Log("Message arrived")
	Log($"Message data: ${Message.GetData}"$)
	Dim n As Notification
	n.Initialize
	n.Icon = "icon"
	n.SetInfo(Message.GetData.Get("title"), Message.GetData.Get("body"), Main)
	n.Notify(1)
End Sub

Sub Service_Destroy

End Sub