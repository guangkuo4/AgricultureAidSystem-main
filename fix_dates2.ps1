$content = Get-Content 'd:\AgricultureAidSystem\AgricultureAidSystem-main\4.MysqlDatabase\springboot2855f2n2.sql' -Raw
$content = $content -replace "'2024-03-16 17:36:06'", "'2026-04-30 17:36:06'"
$content = $content -replace "'2024-03-16 17:36:29'", "'2026-04-30 17:36:29'"
$content = $content -replace "'2024-03-16 17:44:37'", "'2026-04-30 17:44:37'"
$content = $content -replace "'2024-03-16 17:45:31'", "'2026-04-30 17:45:31'"
[System.IO.File]::WriteAllText('d:\AgricultureAidSystem\AgricultureAidSystem-main\4.MysqlDatabase\springboot2855f2n2.sql', $content, [System.Text.Encoding]::UTF8)
Write-Host "Done"
