try {
    $src = "D:\AgricultureAidSystem\AgricultureAidSystem-main\毕业论文.docx"
    $dst = "D:\AgricultureAidSystem\AgricultureAidSystem-main\thesis.docx"
    Copy-Item -Path $src -Destination $dst -Force
    Write-Host "Copy OK: $dst"
    $fi = Get-Item $dst
    Write-Host "Size: $($fi.Length)"
} catch {
    Write-Host "Error: $_"
}
