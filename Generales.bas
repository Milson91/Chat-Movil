B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=StaticCode
Version=7.01
@EndOfDesignText@
'Code module
'Subs in this code module will be accessible from all modules.
Sub Process_Globals
	'credenciales para el portal Tengo
	Dim Usuario As String
	dim NombreUsuario as String
	Dim Pass As String
	Dim Destino As Map
	'These global variables will be declared once when the application starts.
	Dim Url As String ="https://app.astradts.com:8443/PortalIntranet-1.0/service/" 'Produccion
'	Dim Url As String = "http://186.159.116.39:8080/PortalIntranet-1.0/service/"  'Test Desarrollo
'	Dim Url As String="http://10.130.15.72:8084/PortalIntranet/service/"  'Test Local Portal Intranet
	
End Sub


Public Sub ConvertString(Value As Object) As String
	Dim valor As String
	valor =Value
	
	Return valor
	
End Sub

