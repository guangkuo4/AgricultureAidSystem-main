$src = Get-ChildItem -LiteralPath "D:\AgricultureAidSystem\AgricultureAidSystem-main" | Where-Object { $_.Length -gt 1000000 }
foreach ($f in $src) {
    Write-Host "Name: $($f.Name)"
    Write-Host "Length: $($f.Length)"
    Write-Host "FullName: $($f.FullName)"
}
