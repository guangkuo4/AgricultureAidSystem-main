$bytes = [System.IO.File]::ReadAllBytes("D:\AgricultureAidSystem\AgricultureAidSystem-main\毕业论文.docx")
$bytes[0..9] | ForEach-Object { "{0:X2}" -f $_ }
Write-Host "Total bytes: $($bytes.Length)"
