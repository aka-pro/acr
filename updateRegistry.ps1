$path = "HKLM:\SOFTWARE\Policies\Microsoft\Windows\System"
$name = "EnableSmartScreen"
$value = 0

if (!(Test-Path $path)) {
    New-Item -Path $path -Force
}

if (!(Test-Path "$path\$name")) {
    New-ItemProperty -Path $path -Name $name -Value $value -PropertyType DWORD -Force
} else {
    Set-ItemProperty -Path $path -Name $name -Value $value -Type DWORD -Force
}
