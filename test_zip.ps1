Add-Type -AssemblyName System.IO.Compression.FileSystem
$srcPath = "D:\AgricultureAidSystem\AgricultureAidSystem-main"
$dir = [System.IO.DirectoryInfo]::new($srcPath)
$docxFile = $null
foreach ($f in $dir.GetFiles()) {
    if ($f.Name -like "*.docx" -and $f.Length -gt 1000000) {
        $docxFile = $f
        break
    }
}
if ($docxFile) {
    Write-Host "Found: $($docxFile.Name), Size: $($docxFile.Length)"
    Write-Host "FullName: $($docxFile.FullName)"
    
    try {
        $zip = [System.IO.Compression.ZipFile]::OpenRead($docxFile.FullName)
        Write-Host "ZIP opened OK, entries: $($zip.Entries.Count)"
        $zip.Dispose()
    } catch {
        Write-Host "ZIP error: $_"
    }
}
