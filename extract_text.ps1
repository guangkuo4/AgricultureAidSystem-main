[Console]::OutputEncoding = [System.Text.Encoding]::UTF8
$xmlPath = "D:\AgricultureAidSystem\AgricultureAidSystem-main\document.xml"
$xml = [xml](Get-Content -Path $xmlPath -Encoding UTF8)

$ns = @{ w = "http://schemas.openxmlformats.org/wordprocessingml/2006/main" }

$texts = @()
$xml.SelectNodes("//w:t", $ns) | ForEach-Object {
    $texts += $_.InnerText
}

$outputPath = "D:\AgricultureAidSystem\AgricultureAidSystem-main\thesis_text.txt"
$texts -join "" | Out-File -FilePath $outputPath -Encoding UTF8
Write-Host "Extracted text length: $(($texts -join '').Length)"
Write-Host "Saved to: $outputPath"
