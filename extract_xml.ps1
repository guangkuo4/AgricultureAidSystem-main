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

$zip = [System.IO.Compression.ZipFile]::OpenRead($docxFile.FullName)
$entry = $null
foreach ($e in $zip.Entries) {
    if ($e.FullName -eq "word/document.xml") {
        $entry = $e
        break
    }
}

if ($entry) {
    Write-Host "Found document.xml, Size: $($entry.Length)"
    $outPath = "D:\AgricultureAidSystem\AgricultureAidSystem-main\document.xml"
    [System.IO.Compression.ZipFileExtensions]::ExtractToFile($entry, $outPath, $true)
    Write-Host "Extracted to: $outPath"
} else {
    Write-Host "document.xml not found"
    foreach ($e in $zip.Entries) {
        Write-Host $e.FullName
    }
}
$zip.Dispose()
