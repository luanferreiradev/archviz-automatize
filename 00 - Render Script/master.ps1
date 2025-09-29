# Caminho da pasta onde está este script
$scriptDir = Split-Path -Parent $MyInvocation.MyCommand.Definition

# Pasta de logs
$logDir = Join-Path $scriptDir "logs"
New-Item -ItemType Directory -Force -Path $logDir | Out-Null

# Loop em todos os arquivos .bat na pasta atual (exclui o próprio master, se for .bat)
Get-ChildItem -Path "$scriptDir\*.bat" | Where-Object { $_.Name -ne "master.bat" } | ForEach-Object {
    $batFile = $_.FullName
    $logFile = Join-Path $logDir ($_.BaseName + "_log.txt")

    Write-Host "`n>>> Executando $($_.Name)...`n"
    
    # Executa o .bat e salva log
    & cmd.exe /c "call `"$batFile`"" | Tee-Object -FilePath $logFile
}