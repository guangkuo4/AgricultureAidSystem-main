$fso = New-Object -ComObject Scripting.FileSystemObject
$file = $fso.GetFile("D:\AgricultureAidSystem\AgricultureAidSystem-main\毕业论文.docx")
Write-Host "Short name: $($file.ShortName)"
Write-Host "Path: $($file.Path)"
Write-Host "Size: $($file.Size)"
