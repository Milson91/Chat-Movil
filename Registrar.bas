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

	Private TxtUsuario As EditText
	Private TxtNombr As EditText
	Private TxtApellido As EditText
	Private TxtEmail As EditText
	Private TxtClave As EditText
	Dim IME As IME
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Activity.LoadLayout("lytRegistrar")
	IME.Initialize("")
	IME.SetLengthFilter(TxtClave, 4)	
	IME.Initialize("")
	IME.SetLengthFilter(TxtUsuario, 8)
	IME.Initialize("")
	IME.SetLengthFilter(TxtNombr, 30)
	IME.Initialize("")
	IME.SetLengthFilter(TxtApellido,30)
	IME.Initialize("")
	IME.SetLengthFilter(TxtEmail, 30)
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub


Sub BtnRegistrar_Click
	Try
		If(TxtUsuario.Text.EqualsIgnoreCase("")) Then
			If(TxtUsuario.Text.Length<8) Then
				ToastMessageShow("Por favor agregar un numero de telefono valido",False)
				Return
			End If		
			Else
			If(TxtUsuario.Text.Length<8) Then
				ToastMessageShow("Por favor agregar un numero de telefono valido",False)
				Return
			End If
		End If
		If(TxtNombr.Text.EqualsIgnoreCase(""))Then
			ToastMessageShow("Por favor agregar sus nombres",False)
			Return
		End If
		If(TxtApellido.Text.EqualsIgnoreCase(""))Then
			ToastMessageShow("Por favor agregar los apellidos",False)
			Return
		End If
		If(TxtEmail.Text.EqualsIgnoreCase(""))Then
			ToastMessageShow("Por favor agregar un correo valido",False)
			Return
		End If
		If(TxtNombr.Text.EqualsIgnoreCase(""))Then
			If(TxtNombr.Text.Length<4) Then
				ToastMessageShow("Por favor agregar una clave valida",False)
				Return
			End If
			Else
			If(TxtNombr.Text.Length<4) Then
				ToastMessageShow("Por favor agregar una clave valida",False)
				Return
			End If
		End If
		
		
		ProgressDialogShow2("Espere por favor..."& CRLF &"Inciando Registro...",False)
		Dim response As String
		Dim HttpRegistrar As HttpJob
		HttpRegistrar.Initialize("HttpRegistrar",Me)
		HttpRegistrar.PostString(Generales.Url& "InstYtchUsuario","pUsuario="& TxtUsuario.Text.Trim &"&pClave="& TxtClave.Text.Trim & _ 
		"&pNombres="& TxtNombr.Text.Trim &"&pApellidos=" & TxtApellido.Text & "&pEmail="& TxtEmail.Text.Trim)
		HttpRegistrar.GetRequest.SetContentType("application/x-www-form-urlencoded")
		HttpRegistrar.GetRequest.SetHeader("Accept","application/json")
		HttpRegistrar.GetRequest.SetHeader("Authorization","AIzaSyDYOkkTJiTPVd2U7EaTOAwhcN9ySH6GoHxOIYOM")
		HttpRegistrar.GetRequest.Timeout=20000
		Wait For (HttpRegistrar) JobDone(JobServer As HttpJob)
		If JobServer.Success = True Then
			response=JobServer.GetString
			Dim json As JSONParser
			json.Initialize(response)
			Dim m As Map = json.NextObject
			m= m.Get("wsrespuesta")
			Dim ValorCod As String= m.Get("codError")
			If(ValorCod.EqualsIgnoreCase("0"))Then
				ProgressDialogHide
				Msgbox("Exito al Registrarse...","Bienvenido "& TxtNombr.Text &"!!!")
				Activity.Finish
				StartActivity(Main)
			Else
				ProgressDialogHide
				Msgbox("Error al registrar.","Notificatión!!!")
			End If
		Else
			ProgressDialogHide
			Msgbox("Error de conexión"&CRLF&"Valide sus datos moviles","Notificación!!!")
		End If
	Catch
		ProgressDialogHide
		Msgbox("Error al registrar valide sus datos...","Mensaje!!!")
	End Try
End Sub





