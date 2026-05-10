Add-Type -AssemblyName System.IO.FileSystem
$dir = [System.IO.DirectoryInfo]::new("D:\AgricultureAidSystem\AgricultureAidSystem-main")
$files = $dir.GetFiles()
foreach ($f in $files) {
    Write-Host "$($f.Name) -> $($f.Length)"
}
