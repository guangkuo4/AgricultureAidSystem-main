Get-ChildItem "D:\AgricultureAidSystem\AgricultureAidSystem-main" -Depth 0 | Format-Table Name, Length, LastWriteTime
Get-ChildItem "D:\AgricultureAidSystem\AgricultureAidSystem-main" -Depth 1 | Where-Object { $_.Extension -in ".doc",".docx",".pdf",".txt" } | Format-Table Name, Length, Extension
