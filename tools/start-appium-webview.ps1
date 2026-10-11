param(
    [int]$Port = 4723
)

$ErrorActionPreference = "Stop"

$appiumCommand = Get-Command appium -ErrorAction SilentlyContinue
if (-not $appiumCommand) {
    throw "Appium was not found on PATH. Install Appium and the UiAutomator2 driver first."
}

$listener = Get-NetTCPConnection `
    -LocalPort $Port `
    -State Listen `
    -ErrorAction SilentlyContinue |
    Select-Object -First 1

if ($listener) {
    $process = Get-CimInstance Win32_Process `
        -Filter "ProcessId = $($listener.OwningProcess)"
    $commandLine = if ($process) {
        $process.CommandLine
    } else {
        "Command line unavailable"
    }

    throw "Port $Port is already in use by PID $($listener.OwningProcess): $commandLine. Stop that Appium server, then rerun this script. The existing process was not stopped."
}

& appium server `
    --port $Port `
    --allow-insecure "uiautomator2:chromedriver_autodownload"
exit $LASTEXITCODE
