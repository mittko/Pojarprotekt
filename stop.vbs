Dim WinScriptHost
Set WinScriptHost = CreateObject("WScript.Shell")
WinScriptHost.Run Chr(34) & "C:/Program1/stop.bat" & Chr(34), 0
Set WinScriptHost = Nothing