[Console]::OutputEncoding = [System.Text.Encoding]::UTF8
$xmlPath = "D:\AgricultureAidSystem\AgricultureAidSystem-main\document.xml"
$content = Get-Content -Path $xmlPath -Raw -Encoding UTF8

$matches = [regex]::Matches($content, '<w:t[^>]*>([^<]*)</w:t>')
$sb = New-Object System.Text.StringBuilder
foreach ($m in $matches) {
    $null = $sb.Append($m.Groups[1].Value)
}

$outputPath = "D:\AgricultureAidSystem\AgricultureAidSystem-main\thesis_text.txt"
$text = $sb.ToString()
$text | Out-File -FilePath $outputPath -Encoding UTF8
Write-Host "Extracted text length: $($text.Length)"
