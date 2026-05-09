$base = 'd:\AgricultureAidSystem\AgricultureAidSystem-main\1.JavaSpringBoot\src\main\java'
# Fix 2026-04-11 -> 2026-04-30
Get-ChildItem -Path $base -Filter "*.java" -Recurse | ForEach-Object {
    $content = Get-Content $_.FullName -Raw
    if ($content -match '2026-04-11') {
        $content = $content -replace '@date 2026-04-11 \d{2}:\d{2}:\d{2}', '@date 2026-04-30 00:00:00'
        [System.IO.File]::WriteAllText($_.FullName, $content, [System.Text.Encoding]::UTF8)
        Write-Host "Fixed: $($_.FullName)"
    }
}
# Fix 2024-04-08 -> 2026-04-30
Get-ChildItem -Path $base -Filter "*.java" -Recurse | ForEach-Object {
    $content = Get-Content $_.FullName -Raw
    if ($content -match '2024-04-08') {
        $content = $content -replace '@date 2024-04-08 \d{2}:\d{2}:\d{2}', '@date 2026-04-30 00:00:00'
        [System.IO.File]::WriteAllText($_.FullName, $content, [System.Text.Encoding]::UTF8)
        Write-Host "Fixed: $($_.FullName)"
    }
}
Write-Host "All done"
