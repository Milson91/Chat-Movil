B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Activity
Version=9.3
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: False
	#IncludeTitle: False
#End Region

Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.

End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.

	Private ListView1 As ListView
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Activity.LoadLayout("lytMenu")
'	ListView1.AddTwoLines2("Milson Espinal","96374514","96374514")
'	ListView1.AddTwoLines2("Saul Alfredo","98346214","98346214")	
'	ListView1.AddTwoLines2("Yovany Ferrera","94685215","94685215")
	cargar
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub


Sub ListView1_ItemClick (Position As Int, Value As Object)
	Dim valor As Map= Value
	Generales.Destino=valor
	StartActivity(chat)
End Sub


Sub cargar
	Try
		ProgressDialogShow2("Espere por favor...",False)
		Dim response As String
		Dim HttpMenu As HttpJob
		HttpMenu.Initialize("HttpMenu",Me)
		HttpMenu.PostString(Generales.Url& "ConsultaMaster","Base=cthTUsuarios&Esquema=INTRANET")
		HttpMenu.GetRequest.SetContentType("application/x-www-form-urlencoded")
		HttpMenu.GetRequest.SetHeader("Accept","application/json")
		HttpMenu.GetRequest.SetHeader("Authorization","AIzaSyDYOkkTJiTPVd2U7EaTOAwhcN9ySH6GoHxOIYOM")
		HttpMenu.GetRequest.Timeout=20000
		Wait For (HttpMenu) JobDone(JobServer As HttpJob)
		If JobServer.Success = True Then
			response=JobServer.GetString
			Dim json As JSONParser
			json.Initialize(response)
			Dim m As Map = json.NextObject
			m= m.Get("wsrespuesta")
			Dim ValorCod As String= m.Get("codError")
			If(ValorCod.EqualsIgnoreCase("0"))Then
				Dim lis As List=m.Get("data")			
				For i=0 To lis.Size-1
					m= lis.Get(i)
					If(Generales.Usuario <> m.Get("pusuario")) Then
						ListView1.AddTwoLines2(m.Get("pnombre")&"  "&m.Get("pusuario"),m.Get("pemail"),m)
						Else
						Generales.NombreUsuario=m.Get("pnombre")
					End If					
				Next		
				ProgressDialogHide
			Else
				ProgressDialogHide
				Msgbox("Error al iniciar valide sus credenciales.","Notificatión!!!")
			End If
		Else
			ProgressDialogHide
			Msgbox("Error de conexión"&CRLF&"Valide sus datos moviles","Notificación!!!")
		End If
	Catch
		ProgressDialogHide
		Msgbox("Error al iniciar valide sus credenciales.","Notificatión!!!")
	End Try
End Sub

Sub Button1_Click
	Try
		Dim Topic As  String="general"
		Dim m As Map=CreateMap("to": $"/topics/${Topic}"$)	
		Dim data As Map = CreateMap("title": "Hola Mundo", "body": "Buen dia")		
		m.Put("data",data)
		
		Dim json As JSONGenerator
		json.Initialize(m)	
		Dim response As String
		Dim HttpMenu As HttpJob
		HttpMenu.Initialize("HttpMenu",Me)
		HttpMenu.PostString("https://fcm.googleapis.com/fcm/send",json.ToString)
		HttpMenu.GetRequest.SetContentType("application/json")
'		HttpMenu.GetRequest.SetHeader("Accept","application/json")
		HttpMenu.GetRequest.SetHeader("Authorization","key=AAAAqN4BOMc:APA91bGQ2A8TKizIZMaVmoFBFdrK8q699eGff4j4igdloe-tEQzx1f7QDTTM2LOXbn7AodY5IWhZcLlvk4Zjd1Uv8B2l6hyEklEFtb0OHMiDoues6Ae9JM8JMsAuzQqjfEH6GNTCELaU")
		HttpMenu.GetRequest.Timeout=20000
		Wait For (HttpMenu) JobDone(JobServer As HttpJob)
		If JobServer.Success = True Then
		response=JobServer.GetString
		End If
	Catch
		Log(LastException)
	End Try
End Sub

