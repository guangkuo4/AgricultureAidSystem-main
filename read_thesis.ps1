$word = New-Object -ComObject Word.Application
$word.Visible = $false
$doc = $word.Documents.Open("D:\AgricultureAidSystem\AgricultureAidSystem-main\毕业论文.docx", $false, $true)
$text = $doc.Content.Text
$doc.Close($false)
$word.Quit()
$text | Out-File -FilePath "D:\AgricultureAidSystem\AgricultureAidSystem-main\thesis_content.txt" -Encoding UTF8
Write-Host "Done. Text length: $($text.Length)"
