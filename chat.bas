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
	Private timer1 As Timer
End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.
	Dim DatosDetalle As List
	Private ScrollView1 As ScrollView
	Private dra As ColorDrawable
	Private TxtMsj As EditText
	Dim IME As IME
	Private LblNombre As Label
	
	'variables utilizadas para pintar el chat
	Private filas As Int=0
	Dim TotalPanelHeightFecha As Int=0
	Dim PanelHeightFecha As Int=0
	Dim TotalPanelHeight As Int=0
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Activity.LoadLayout("lytchat")
	timer1.Initialize("timer1", 2500)
	timer1.Enabled = True
	IME.Initialize("")
	IME.SetLengthFilter(TxtMsj, 50)
End Sub

Sub Activity_Resume
	ProgressDialogShow2("Espere por favor..."& CRLF &"Cargando Msj...",False)
	LblNombre.Text=Generales.Destino.Get("pnombre")
	CargarTransacciones
End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub


Sub Label3_Click
	timer1.Enabled=False
	Activity.Finish
End Sub
Sub Activity_KeyPress (KeyCode As Int) As Boolean
	If KeyCode = KeyCodes.KEYCODE_BACK Then
		timer1.Enabled=False
		Activity.Finish
	End If
	Return True
End Sub

Sub Timer1_Tick
	'This method will raise the event on the device
	CargarTransacciones
End Sub

Sub CargarTransacciones
	Try
		Dim response As String
		Dim HttpLogin As HttpJob
		HttpLogin.Initialize("transDiarias",Me)
		HttpLogin.PostString(Generales.Url& "ConsulYtch","pOrigen="& Generales.Usuario &"&pDestino="& Generales.Destino.Get("pusuario")&"&pCount="&(filas))
		HttpLogin.GetRequest.SetContentType("application/x-www-form-urlencoded")
		HttpLogin.GetRequest.SetHeader("Accept","application/json")
		HttpLogin.GetRequest.SetHeader("Authorization","AIzaSyDYOkkTJiTPVd2U7EaTOAwhcN9ySH6GoHxOIYOM")
		HttpLogin.GetRequest.Timeout=20000
		Wait For (HttpLogin) JobDone(JobServer As HttpJob)
		If JobServer.Success = True Then
			response=JobServer.GetString
			Dim json As JSONParser
			json.Initialize(response)
			Dim m As Map = json.NextObject
			m= m.Get("wsrespuesta")
			Dim ValorCod As String= m.Get("codError")
			Dim mDeta As Map
			mDeta.Initialize
			DatosDetalle.Initialize
			If(ValorCod.EqualsIgnoreCase("0"))Then
'				m= m.Get("Consulta_Transacciones")
				
				Dim ListDatos As List
				ListDatos.Initialize
				ListDatos=m.Get("data")
				For i=0 To ListDatos.Size-1
					Dim Deta As Map=ListDatos.Get(i)
					mDeta.Initialize
					mDeta.Put("fecha",Deta.Get("fecha"))
					mDeta.Put("hora",Deta.Get("hora"))
					mDeta.Put("Secuencia",Deta.Get("Secuencia"))
					mDeta.Put("origen",Deta.Get("origen"))
					mDeta.Put("destino",Deta.Get("destino"))
					mDeta.Put("mensaje",Deta.Get("mensaje"))			
					DatosDetalle.Add(mDeta)
				Next
								
				CargarDetalle
				ProgressDialogHide
			Else
				ProgressDialogHide
'				Msgbox("Error al cargar no hay registros...","Notificatión!!!")
			End If
		Else
			ProgressDialogHide
'			Msgbox("Error de conexión"&CRLF&"Valide sus datos moviles","Notificación!!!")
		End If
	Catch
		ProgressDialogHide
'		Msgbox("No existen transacciones...","Mensaje!!!")
	End Try
End Sub

Sub CargarDetalle
	
'	ScrollView1.Panel.RemoveAllViews
	Dim lbl As Label
	Dim PanelHeight As Int
	PanelHeight=60dip

'	Dim Total As Int
	For p = 0 To  DatosDetalle.Size - 1
		Sleep(0)
		Dim mDeta As Map=DatosDetalle.Get(p)
		Dim panNoVisita As Panel
		PanelHeightFecha=2dip
		
		
		If(mDeta.Get("origen")<>Generales.Usuario) Then
			dra.Initialize(Colors.RGB(244,244,244),20)
			panNoVisita.Initialize("panNoVisita")
			ScrollView1.Panel.AddView(panNoVisita,2dip,(filas*PanelHeight)+TotalPanelHeightFecha+5dip,ScrollView1.Width - 30dip,PanelHeight)
			panNoVisita.Tag	= mDeta.Get("Tag")
			panNoVisita.Background=dra
		Else
			dra.Initialize(Colors.RGB(220,248,198),20)
			panNoVisita.Initialize("panNoVisita")
			ScrollView1.Panel.AddView(panNoVisita,15dip,(filas*PanelHeight)+TotalPanelHeightFecha+5dip,ScrollView1.Width - 20dip,PanelHeight)
			panNoVisita.Tag	= mDeta.Get("Tag")
			panNoVisita.Background=dra
		End If
			
		
		'Panel Contenedor
'		panNoVisita.Initialize("panNoVisita")
'		ScrollView1.Panel.AddView(panNoVisita,2dip,(filas*PanelHeight)+TotalPanelHeightFecha+5dip,ScrollView1.Width - 4dip,PanelHeight)
'		panNoVisita.Tag	= mDeta.Get("Tag")
'		panNoVisita.Background=dra
'		
		'Numero Visita
		lbl.Initialize("lbl")
		panNoVisita.AddView(lbl,10dip, 10dip, panNoVisita.Width , 60dip)
		lbl.Text.ToUpperCase
		lbl.Text= mDeta.Get("origen")
		lbl.TextSize = 15
		lbl.TextColor = Colors.RGB(186,51,220)
		
		lbl.Initialize("lbl")
		panNoVisita.AddView(lbl,10dip, 30dip, ScrollView1.Width -70dip, 60dip)
		lbl.Text.ToUpperCase
		lbl.Text= mDeta.Get("mensaje")
		lbl.TextSize = 14
		lbl.TextColor = Colors.RGB(160,160,160)
		TotalPanelHeightFecha = TotalPanelHeightFecha + PanelHeightFecha
		
		lbl.Initialize("lbl")
		panNoVisita.AddView(lbl,panNoVisita.Width -55dip, 30dip,60dip, 60dip)
		lbl.Text.ToUpperCase
		lbl.Text= mDeta.Get("hora")
		lbl.TextSize = 14
		lbl.TextColor = Colors.RGB(160,160,160)
		TotalPanelHeightFecha = TotalPanelHeightFecha + PanelHeightFecha
		
		'Total de alto de panel
		TotalPanelHeight = TotalPanelHeight + PanelHeight + PanelHeightFecha
		filas=filas+1
	Next
'	ScrollView1.Panel.
	If TotalPanelHeight < ScrollView1.Height Then
		ScrollView1.Panel.Height = ScrollView1.Height -2dip
	Else
		ScrollView1.Panel.Height = TotalPanelHeight+60dip
	End If
	
End Sub

Sub TxtMsj_EnterPressed
	Enviar
End Sub

Sub LblEnviar_Click
	If( TxtMsj.Text.EqualsIgnoreCase("")) Then
		Return
	End If
	Enviar
End Sub


Sub Enviar
	Try
		Dim sms As String=TxtMsj.Text
		TxtMsj.Text=""
		
		ProgressDialogShow("")
		Dim response As String
		Dim HttpInstYtch As HttpJob
		HttpInstYtch.Initialize("InstYtch",Me)
		HttpInstYtch.PostString(Generales.Url& "InstYtch","pOrigen="& Generales.Usuario &"&pDestino="& Generales.Destino.Get("pusuario") &"&pMensaje="& sms)
		HttpInstYtch.GetRequest.SetContentType("application/x-www-form-urlencoded")
		HttpInstYtch.GetRequest.SetHeader("Accept","application/json")
		HttpInstYtch.GetRequest.SetHeader("Authorization","AIzaSyDYOkkTJiTPVd2U7EaTOAwhcN9ySH6GoHxOIYOM")
		HttpInstYtch.GetRequest.Timeout=20000
		Wait For (HttpInstYtch) JobDone(JobServer As HttpJob)
		If JobServer.Success = True Then
			response=JobServer.GetString
			Dim json As JSONParser
			json.Initialize(response)
			Dim m As Map = json.NextObject
			m= m.Get("wsrespuesta")
			Dim ValorCod As String= m.Get("codError")
			Dim mDeta As Map
			mDeta.Initialize
			DatosDetalle.Initialize
			If(ValorCod.EqualsIgnoreCase("0"))Then
				senPush(sms)
			
'				CargarTransacciones
				
			Else
				ProgressDialogHide
				Msgbox("Error al enviar.","Notificatión!!!")
			End If
		Else
			ProgressDialogHide
			Msgbox("Error de conexión"&CRLF&"Valide sus datos moviles","Notificación!!!")
		End If
	Catch
		ProgressDialogHide
		Msgbox("Error al enviar...","Mensaje!!!")
	End Try
End Sub

Sub senPush(sms As String)
	
	Try
		Dim Topic As  String=Generales.Destino.Get("pusuario")
		Dim m As Map=CreateMap("to": $"/topics/${Topic}"$)
		Dim data As Map = CreateMap("title": Generales.NombreUsuario, "body": sms)
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
			TxtMsj.Text="";
			ProgressDialogHide
			Else
			ProgressDialogHide
		End If
	Catch
		Log(LastException)
	End Try
End Sub